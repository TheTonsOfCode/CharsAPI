package pl.merbio.charsapi.other;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.merbio.Main;

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
        boolean air_blocade = yaml.getBoolean("only_air_blocade");

        Main.getMainBuilder().setBlockSettings(
                Main.getMainBuilder().getBlockSettings()
                .setPlayerSpacing(over_player)
                .setLetterSpacing(space_letter)
                .setWordSpacing(space_word)
                .setOnlyAirBlocade(air_blocade)
        );
        
        enableFallingBlocksProtection = yaml.getBoolean("enable_falling_blocks_notify");
        
        protectFallingBlocks = yaml.getInt("ammount_falling_blocks_to_notify");
    }

    public static String getLanguage() {
        return language;
    }

    public static int getMaxFontSizeBlocade() {
        return maxFontSizeBlocade;
    }
    
    public static int getFallingBlockProtect(){
        return protectFallingBlocks;
    }
    
    public static boolean isEnableFallingBlockProtection(){
        return enableFallingBlocksProtection;
    }
}
