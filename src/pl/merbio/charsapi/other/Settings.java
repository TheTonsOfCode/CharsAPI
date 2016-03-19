package pl.merbio.charsapi.other;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.merbio.Main;
import pl.merbio.charsapi.exception.AleradyReservedCharacterException;
import pl.merbio.charsapi.exception.StaticColorCharacterException;

/**
 * @author Merbio
 */
public class Settings {

    //File
    private static File file;
    private static YamlConfiguration yaml;

    //Settings
    private static String language;

    private static int maxFontSizeBlocade;
    private static int protectFallingBlocks;

    private static boolean enableFallingBlocksProtection;

    public Settings(Main main) {
        file = new File(main.getDataFolder(), "config.yml");
        if (!file.exists()) {
            main.saveResource("config.yml", false);
            file = new File(main.getDataFolder(), "config.yml");
        }

        yaml = YamlConfiguration.loadConfiguration(file);

        // Getting information
        language = yaml.getString("language");

        maxFontSizeBlocade = yaml.getInt("max_font_size");

        int over_player, space_word, space_letter;
        over_player = yaml.getInt("space_over_player");
        space_word = yaml.getInt("default_font_space.word");
        space_letter = yaml.getInt("default_font_space.letter");
        String variable_character = yaml.getString("variable_character");
        boolean air_blocade = yaml.getBoolean("only_air_blocade");

        Main.getMainBuilder().setBlockSettings(
                Main.getMainBuilder().getBlockSettings()
                .setPlayerSpacing(over_player)
                .setLetterSpacing(space_letter)
                .setWordSpacing(space_word)
                .setOnlyAirBlocade(air_blocade)
        );

        try {
            Main.getMainBuilder().setBlockSettings(Main.getMainBuilder().getBlockSettings().setVarChar(variable_character.charAt(0)));
        } catch (AleradyReservedCharacterException ex) {
            if (variable_character.charAt(0) != '%') {
                Message.console("&cCharacter: &f" + variable_character.charAt(0) + " &cis already reserved in builder configuration!");
            }
        }

        List<String> ctm = yaml.getStringList("char_to_material");
        for (String s : ctm) {
            String[] a = s.split(":");
            if (a.length != 2) {
                Message.console("&cError at charToMaterial> error in pattern: &f" + s);
                continue;
            }

            if (a[0].length() != 1) {
                Message.console("&cError at charToMaterial> length of char != 1: &f" + a[0]);
                continue;
            }

            Material m = Material.getMaterial(a[1]);

            if (m == null) {
                Message.console("&cError at charToMaterial> cannot to find material: &f" + a[1]);
                continue;
            }

            try {
                Main.getMainBuilder().setBlockSettings(
                        Main.getMainBuilder().getBlockSettings()
                        .addMaterialReplacement(a[0].charAt(0), m)
                );
            } catch (StaticColorCharacterException ex) {
                Message.console("&cCharacter: &f" + a[0] + " &cis already color!");
            }
        }

        enableFallingBlocksProtection = yaml.getBoolean("enable_falling_blocks_notify");

        protectFallingBlocks = yaml.getInt("ammount_falling_blocks_to_notify");
    }

    public static String getLanguage() {
        return language;
    }

    public static int getMaxFontSizeBlocade() {
        return maxFontSizeBlocade;
    }

    public static int getFallingBlockProtect() {
        return protectFallingBlocks;
    }

    public static boolean isEnableFallingBlockProtection() {
        return enableFallingBlocksProtection;
    }
}
