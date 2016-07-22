package pl.merbio;

import pl.merbio.charsapi.commands.CharsCommand;
import java.awt.Font;
import java.math.BigDecimal;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.merbio.charsapi.commands.DropBlockTestCMD;
import pl.merbio.charsapi.listeners.FallingBlocksListeners;
import pl.merbio.charsapi.managers.FileManager;
import pl.merbio.charsapi.managers.UpdatersManager;
import pl.merbio.charsapi.nms.NMSUtil;
import pl.merbio.charsapi.objects.CharsBuilder;
import pl.merbio.charsapi.objects.CharsVariable;
import pl.merbio.charsapi.other.Message;
import pl.merbio.utilities.Metrics;
import pl.merbio.charsapi.other.Settings;
import pl.merbio.utilities.TpsMeter;
import pl.merbio.utilities.commands.CommandManager;

//Test push commit
public class Main extends JavaPlugin {

    private static CharsBuilder builder;
    private Metrics metrics;

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {
        this.instance = this;
        metrics = new Metrics(this);

        builder = new CharsBuilder();

        new Message(this);
        new Settings(this);
        new FileManager(this);

        registerCharsVariables();

        getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                getCommand("chars").setExecutor(new CharsCommand());
                Message.console("Command loaded.");
            }
        }, 40L);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new FallingBlocksListeners(), this);

        FallingBlocksListeners.checkBlocksAreLiving();

        CommandManager.registerCommand(new DropBlockTestCMD());

        new TpsMeter().runTaskTimer(this, 0, 1);
    }

    public void onDisable() {
        FallingBlocksListeners.clearBlocks();
        try {
            UpdatersManager.stopAllUpdaters();
        } catch (Exception e) {
        }
    }

    private void registerCharsVariables() {
        CharsBuilder.addCharsVariables(
                new CharsVariable("Merbio", "author"),
                new CharsVariable(new CharsVariable.onVarCheck() {
                    @Override
                    public String on() {
                        return String.valueOf(getServer().getMaxPlayers());
                    }
                }, "maxplayers", "maxpl"),
                new CharsVariable(new CharsVariable.onVarCheck() {
                    @Override
                    public String on() {
                        return String.valueOf(new BigDecimal(TpsMeter.tps).setScale(1, BigDecimal.ROUND_HALF_UP)
                                .doubleValue());
                    }
                }, "tps"),
                new CharsVariable(new CharsVariable.onVarCheck() {
                    @Override
                    public String on() {
                        return String.valueOf(new BigDecimal(TpsMeter.tps).setScale(1, BigDecimal.ROUND_HALF_UP)
                                .doubleValue() + " / 19.0");
                    }
                }, "fulltps", "ftps"),
                new CharsVariable("", "rebuildInUpdaterEveryTime", "riuet")
        );
        NMSUtil.registerOnlineCharsVariable();
    }

    public static CharsBuilder getMainBuilder() {
        return builder;
    }

    public static void setBuilderFont(String name, int bold, int size) {
        builder.setBlockSettings(builder.getBlockSettings().setFont(new Font(name, bold, size)));
    }

    public static void setDefaultBuilderFont() {
        builder.setBlockSettings(builder.getBlockSettings().setFont(null));
    }
}

//        UpdateChecker uc = new UpdateChecker(this, 
//                "http://www.mpcforum.pl/topic/1323801-dl-charsapi-buduj-napisy-na-swoim-serwerze/");
//        
//        if(uc.updateNeeded()){
//            this.getLogger().info("Jest dostepna nowa wersja pluginu: " + uc.getVersion());
//            this.getLogger().info("Pobierz z tad: " + uc.getLink());
//        }
//        new CharsVariable(new CharsVariable.onVarCheck() {
//            @Override
//            public String on() {
//                Object online = getServer().getOnlinePlayers();
//                if (online instanceof Collection) {
//                    return String.valueOf(((Collection) online).size());
//                }
//                return String.valueOf(((Player[]) online).length);
//            }
//        }, "online", "on"),
