package pl.merbio.charsapi.managers;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.merbio.Main;
import pl.merbio.charsapi.commands.CharsCommand;
import pl.merbio.charsapi.other.Lang;
import pl.merbio.charsapi.other.Serialize;
import pl.merbio.charsapi.objects.CharsBuilder;
import pl.merbio.charsapi.objects.CharsUpdater;
import pl.merbio.charsapi.objects.CharsUpdaterString;
import pl.merbio.charsapi.other.LocalException;
import pl.merbio.charsapi.other.Message;

public class FileManager {

    private static File fileCharsData;
    private static YamlConfiguration ycCharsData;
    private static File fileFonts;

    public FileManager(Main main) {
        fileFonts = new File(main.getDataFolder().getAbsolutePath() + "/Fonts");
        if (!fileFonts.exists()) {
            defaultFonts(main);
        }

        new Lang(main);
        
        if (!new File(main.getDataFolder(), "CharsData").exists()) {
            main.saveResource("CharsData", false);
        }
        fileCharsData = new File(main.getDataFolder(), "CharsData");
        ycCharsData = YamlConfiguration.loadConfiguration(fileCharsData);
        
        Main.getInstance().getServer().getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                loadCharsUpdaters();
                Message.console("CharsData loaded.");
            }
        }, 8 * 20L); 
    }

    public void defaultFonts(Main main) {
        main.saveResource("Fonts/Old English Text MT Regular.ttf", false);
        main.saveResource("Fonts/minecraft.ttf", false);
        main.saveResource("Fonts/SHOWG.TTF", false);
        main.saveResource("Fonts/AGENCYR.TTF", false);
        main.saveResource("Fonts/ALGER.TTF", false);
        main.saveResource("Fonts/LATINWD.TTF", false);

        //Przy tym może być bład jeśli np użytkownik wyłączy na jakiś czas serwer a potem znów go odpali
        fileFonts.mkdirs();
    }

    public static ArrayList<File> getPluginFontsFiles() {
        File[] listOfFiles = fileFonts.listFiles();
        String englarment = "ttf";

        ArrayList<File> list = new ArrayList<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] split = file.getName().split("\\.");
                if (split[1].equalsIgnoreCase(englarment)) {
                    list.add(file);
                }
            }
        }

        return list;
    }

    public static void saveCharsUpdater(String id, CharsUpdater updater) {
        id = id.toUpperCase() + ".";

        ycCharsData.set(id + "location", Serialize.serializeLocation(updater.getLocation()));
        ycCharsData.set(id + "facing", updater.getFacing().ordinal());
        ycCharsData.set(id + "seconds", updater.getSeconds());
        ycCharsData.set(id + "last_autoid", updater.getLastAutoID());

        ArrayList<CharsUpdaterString> cus = updater.getLines();

        for (int i = 0; i < cus.size(); i++) {
            CharsUpdaterString cc = cus.get(i);
            saveCharsString(id, String.valueOf(cc.id), cc);
        }

        try {
            ycCharsData.save(fileCharsData);
        } catch (IOException e) {
        }
    }

    private static void saveCharsString(String updater, String idChars, CharsUpdaterString cus) {
        String font = cus.font == null ? "-" : Serialize.serializeFont(cus.font);
        ycCharsData.set(updater + "list." + idChars + ".font", font);
        ycCharsData.set(updater + "list." + idChars + ".string", cus.cs.getString());
    }

    public static void loadCharsUpdaters() {
        Set<String> idNameSet = null;

        try {
            idNameSet = ycCharsData.getConfigurationSection("").getKeys(false);
        } catch (Exception e) {
        }

        CharsBuilder cb = Main.getMainBuilder();

        for (String id : idNameSet) {
            String s = id + ".";

            int seconds = ycCharsData.getInt(s + "seconds");
            BlockFace bf = BlockFace.values()[ycCharsData.getInt(s + "facing")];
            String locationSerial = ycCharsData.getString(s + "location");
            
            if(!Serialize.isSerialWorldDetected(locationSerial)){
                LocalException.UpdaterNonExistWorldEx.notifyException("Updater: &f" + id);
                continue;
            }
            
            Location location = Serialize.unserializeLocation(locationSerial);
            int last_autoid = ycCharsData.getInt(s + "last_autoid");

            CharsUpdater updater = new CharsUpdater(seconds, location, bf);

            Set<String> idSet = null;

            try {
                idSet = ycCharsData.getConfigurationSection(s + "list").getKeys(false);
            } catch (Exception e) {
            }

            for (String nID : idSet) {
                String f = ycCharsData.getString(s + "list." + nID + ".font");
                Font font = f.equalsIgnoreCase("-") ? null : Serialize.unserializeFont(f);
                updater.addCharsString(
                        font,
                        ycCharsData.getString(s + "list." + nID + ".string")
                );
            }

            updater.setLastAutoID(last_autoid);
            updater.start();
            UpdatersManager.putUpdater(id, updater);
        }
    }

    public static void removeCharsUpdater(String id) {
        ycCharsData.set(id.toUpperCase(), null);

        try {
            ycCharsData.save(fileCharsData);
        } catch (IOException e) {
        }
    }
}
