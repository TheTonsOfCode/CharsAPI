package pl.merbio.charsapi.commands;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.merbio.charsapi.other.Lang;

public class CommandUtil {

    public static String buildMessageStruct(String[]  
        ... strings){
        String builded = "";
        builded += "&7#===================================================#\n";
        builded += "|| \n";
        for (String[] stringsStr : strings) {
            String main, description, als = null;
            main = stringsStr[0];
            description = stringsStr[1];
            if (stringsStr[2] != null) {
                als = "[&f" + stringsStr[2] + "&7]";
            }
//            for(int i = 2; i < stringsStr.length ;i++){
//                als += stringsStr[i];
//                if(i != stringsStr.length - 1){
//                    als += ", ";
//                }
//            }
            builded += "&7|| > &f" + main + "&7" + (als == null ? "" : als) + " - &e" + description + "\n";
        }
        builded += "&7|| \n";
        builded += "&7#===================================================#";
        return formatMessage(builded);
    }

    public static String subCommandsHelp(ArrayList<SubCommand> subCommands) {
        String[][] ss = new String[subCommands.size()][3];
        for (int i = 0; i < subCommands.size(); i++) {
            SubCommand sc = subCommands.get(i);
            String als = null;
            String[] aliases = sc.getAliases();
            if (aliases != null) {
                als = "";
                for (int j = 0; j < aliases.length; j++) {
                    als += aliases[j];
                    if (j != aliases.length - 1) {
                        als += ", ";
                    }
                }
            }
            ss[i] = new String[]{sc.getName(), sc.getDescription(), als};
        }
        return CommandUtil.buildMessageStruct(ss);
    }

    public static boolean runSubCommand(Player player, String[] args, ArrayList<SubCommand> subCommands) {
        String arg = args[0];

        SubCommand sub = null;

        for (SubCommand sc : subCommands) {
            if (arg.equalsIgnoreCase(sc.getName())) {
                sub = sc;
                break;
            }
            if (sc.getAliases() != null) {
                for (String a : sc.getAliases()) {
                    if (arg.equalsIgnoreCase(a)) {
                        sub = sc;
                        break;
                    }
                }
            }
        }

        if (sub != null) {
            String[] nArgs = new String[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                nArgs[i - 1] = args[i];
            }
            return sub.onCommand(player, nArgs);
        } else {
            send(player, Lang.UNDEFINED_ARGUMENT.replace("%ARGUMENT%", arg));
            return false;
        }
    }

    public static void send(Player player, String message) {
        player.sendMessage(formatMessage("&e&o[&6&oCharsAPI&e&o]&f " + message));
    }

    public static String formatMessage(String toFormat) {
        return ChatColor.translateAlternateColorCodes('&', toFormat);
    }
}
