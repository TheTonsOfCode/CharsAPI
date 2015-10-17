package pl.merbio.charsapi.other;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.merbio.Main;

/**
 * @author Merbio
 */
public class Lang {

    private static File file;
    private static YamlConfiguration yaml;
    private static Main main;
    private static boolean loaded;

    public Lang(Main main) {
        this.main = main;
        
        if (createEmptyTranslationFile()) {
            main.saveResource("Language/EN.yml", false);
            main.saveResource("Language/PL.yml", false);
        }

        selectLanguageFile(Settings.getLanguage());
        if (!file.exists()) {
            Message.console("No detected language file: " + file.getName());
            selectLanguageFile("EN");
            Message.console("Set language file to: " + file.getName());
        }

        Message.console("Selected language: &f" + file.getName().split("\\.")[0]);

        Field[] fields = getClass().getDeclaredFields();

            for (Field f : fields) {
                try {
                    if (f.getGenericType() == java.lang.String.class) {
                        f.set(f.get(f), getTranslation(f.getName()));
                    }
                } catch (Exception e) {
                    Message.console("Load field name error!");
                }
            }

            loaded = true;

    }
    
    public static boolean isLoaded(){
        return loaded;
    }

    private static void selectLanguageFile(String nameFile) {
        file = new File(main.getDataFolder(), "Language/" + nameFile + ".yml");
        yaml = YamlConfiguration.loadConfiguration(file);
    }

    public boolean createEmptyTranslationFile() {
        if (new File(main.getDataFolder(), "Language/EmptyLanguageFile.yml").exists()) {
            return false;
        }

        main.saveResource("Language/EmptyLanguageFile.yml", false);
        File file = new File(main.getDataFolder(), "Language/EmptyLanguageFile.yml");

        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        for (Field f : getClass().getDeclaredFields()) {
            try {
                if (f.getGenericType() == java.lang.String.class) {
                    yaml.set(f.getName().toLowerCase(), "");
                }
            } catch (Exception e) {
            }
        }

        try {
            yaml.save(file);
        } catch (IOException e) {
        }

        return true;
    }

    public static String getTranslation(String translateFieldName) {
        translateFieldName = translateFieldName.toLowerCase();

        if (!yaml.contains(translateFieldName)) {
            Message.console("No translation detected for field: &f" + translateFieldName);
            return "No translation detected!";
        }

        return yaml.getString(translateFieldName);
    }

    public static String NO_CONSOLE,
            NO_PERMISSION,
            NO_NUMBER,
            HELP,
            UPDATER_HELP,
            UNDEFINED_ARGUMENT,
            UPDATER_NON_SELECT,
            UPDATER_USE_WHEN_RUNNING,
            UPDATER_NOFIND,
            UPDATER_TO_LITTLE_STRINGS_ARGS,
            ANIMATIONS_IN,
            ANIMATIONS_OUT,
            ANIMATIONS_TYPES,
            CHARACTER_UNDEFINED,
            BUILD_CHARS,
            CLEAR_CHARS_ALL,
            CLEAR_CHARS,
            ID_UNDEFINED,
            FONT_NUMBER_SIZE,
            FONT_NUMBER_TYPE,
            FONT_TOO_BIG,
            FONT_UNDEFINDED,
            FONT_SET,
            FONT_SET_DEFAULT,
            FONT_TYPE_NORMAL,
            FONT_TYPE_BOLD,
            FONT_TYPE_ITALIC,
            LIST_UNREACHED_PAGE,
            LIST_PAGE_INFO;

    public static String U_ADD_CHARS_ADDED,
            U_CREATE_ALERADY_EXIST_UPDATER,
            U_UPDATER_CREATE,
            U_DEL_CHARS_UNEDFINED_ID,
            U_DEL_UPDATER_LIST_INFO,
            U_DEL_CHARS_DELETED,
            U_FLUSH_CONFIRM_MESSAGE,
            U_FLUSH_COMPLETE,
            U_INFO_UPDATER,
            U_INFO_FACING,
            U_INFO_TIME,
            U_INFO_CHARS_INFO,
            U_LIST_EMPTY,
            U_LIST_INFO,
            U_REMOVE_COMPLETE,
            U_SAVE_COMPLETE,
            U_SELECT_CONFIRM_MESSAGE,
            U_SELECT_COMPLETE,
            U_SETLOC_COMPLETE,
            U_SETTIME_COMPLETE,
            U_START_IS_RUNING,
            U_START_COMPLETE,
            U_STOP_IS_STOPED,
            U_STOP_COMPLETE,
            U_STOPALL_COMPLETE,
            U_TPTO_COMPLETE;

    public static String CMD_DESC_ANIMATION,
            CMD_DESC_BUILD,
            CMD_DESC_CLEAR,
            CMD_DESC_FONT,
            CMD_DESC_LIST,
            CMD_DESC_UPDATER;

    public static String CMD_DESC_U_ADD,
            CMD_DESC_U_CREATE,
            CMD_DESC_U_DEL,
            CMD_DESC_U_FLUSH,
            CMD_DESC_U_INFO,
            CMD_DESC_U_LIST,
            CMD_DESC_U_REMOVE,
            CMD_DESC_U_SAVE,
            CMD_DESC_U_SELECT,
            CMD_DESC_U_SETLOC,
            CMD_DESC_U_SETTIME,
            CMD_DESC_U_START,
            CMD_DESC_U_STOP,
            CMD_DESC_U_STOPALL,
            CMD_DESC_U_TPTO;

    public static String CMD_ARGS_ANIMATION,
            CMD_ARGS_BUILD,
            CMD_ARGS_CLEAR,
            CMD_ARGS_FONT,
            CMD_ARGS_FONT_TYPES,
            CMD_ARGS_LIST;

    public static String CMD_U_ARG_UPDATER_NAME,
            CMD_U_ARG_UPDATER_TIME,
            CMD_U_ARG_DEL_ID;

    public static String IN_A_DESC_AIR_TO_RIGHT,
            IN_A_DESC_AIR_TO_DOWN,
            IN_A_DESC_NOAIR_TO_DOWN,
            IN_A_DESC_NOAIR_TO_RIGHT,
            IN_A_DESC_HANGINGS_HEIGHT,
            IN_A_DESC_HANGINGS_WIDTH,
            IN_A_DESC_RANDOMIZE,
            IN_A_DESC_HANGINGS_OPEN,
            IN_A_DESC_HANGINGS_CLOSE,
            IN_A_DESC_CHESS_TO_RIGHT,
            IN_A_DESC_CHESS_TO_DOWN,
            IN_A_DESC_MATRIX_FILM,
            IN_A_DESC_SNAKE,
            IN_A_DESC_BY_COLORS,
            IN_A_DESC_DROP_COLORS,
            IN_A_DESC_STARFIRE,
            IN_A_DESC_FILL_TO_RIGHT,
            IN_A_DESC_FILL_TO_CENTER;

    public static String OUT_A_DESC_DROP_ALL,
            OUT_A_DESC_DROP_COLUMNS,
            OUT_A_DESC_DROP_LINES,
            OUT_A_DESC_WULCANO,
            OUT_A_DESC_THROW,
            OUT_A_DESC_THROW_ALL;
}
