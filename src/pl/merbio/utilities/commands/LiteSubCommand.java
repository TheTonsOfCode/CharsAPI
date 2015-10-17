package pl.merbio.utilities.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import static pl.merbio.charsapi.commands.CommandUtil.send;
import pl.merbio.charsapi.other.Lang;

/**
 * @author Merbio
 */
public abstract class LiteSubCommand extends BukkitCommand {

    protected abstract boolean execute();

    @Override
    public boolean execute(CommandSender cs, String string, String[] strings) {
        return false;
    }

    // Main command structs
    String HELP;
    private String messagePrefix;

    private ArrayList<LiteSubCommand> subCommands = new ArrayList<>();
    private ArrayList<CmdMsg> cmdMsgInfo = new ArrayList<>();

    public LiteSubCommand(String name, String description, String... aliases) {
        super(name);
        this.description = description;
        if (aliases != null) {
            this.setAliases(Arrays.asList(aliases));
        }

        setPermission("");
        onLoad();
    }

    public void onLoad() {
    }

    public void setMessagePrefix(String messagePrefix) {
        if (messagePrefix == null) {
            return;
        }

        this.messagePrefix = translate(messagePrefix);
    }

    public void addSubCommand(LiteSubCommand subCommand) {
        this.subCommands.add(subCommand);
    }

    public LiteSubCommand getSubCommand(String name) {
        for (LiteSubCommand liteSubCommand : subCommands) {
            if (name.equalsIgnoreCase(liteSubCommand.getName())) {
                return liteSubCommand;
            } else if (liteSubCommand.getAliases() != null) {
                for (String a : liteSubCommand.getAliases()) {
                    if (name.equalsIgnoreCase(a)) {
                        return liteSubCommand;
                    }
                }
            }
        }

        return null;
    }

    public boolean runSubCommand(LiteSubCommand ext, boolean noFindMsg, String nameCommand) {
        if (nameCommand == null) {
            return false;
        }

        LiteSubCommand sub = getSubCommand(nameCommand);

        if (sub != null) {
            String[] nArgs = new String[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                nArgs[i - 1] = args[i];
            }

            sub.setValues(ext.player, ext.args);
            sub.execute();

            return true;
        } else if (checkHelp(this, nameCommand)) {
            return true;
        }

        if (noFindMsg) {
            send(ext.player, "&cUndefined argument: &f" + nameCommand.toLowerCase() + "&c!");
        }

        return false;
    }

    public boolean checkHelp(LiteSubCommand ex, String name) {
        if (name.equalsIgnoreCase("help") || name.equalsIgnoreCase("?")) {
            if (HELP == null) {
                String a = "&7#============================================#\n";
                String b = "&7#";
                String c = "&7#=========#\n";
                String n = "\n";
                HELP
                        = a
                        + b + " &fHelp of command: &a" + getName() + n
                        + a
                        + b + " &fDescription: &a" + description + n;
                if (getPermission() != null && getPermission().length() > 1) {
                    HELP += b + " &fPermission: &c" + getPermission().toLowerCase() + n;
                }
                List<String> aliases = getAliases();
                if (aliases != null && !aliases.isEmpty()) {
                    HELP += b + " &fAliases: &a" + formatList(aliases) + n;
                }
                if (ex instanceof LiteCommand) {
                    if (usageMessage != null) {
                        HELP += b + " &fUsage: " + usageMessage + n;
                    }
                    LiteCommand lc = (LiteCommand) ex;
                    if (lc.getMinA() != null) {
                        HELP += b + " &fMinimum arguments: &e" + lc.getMinA() + n;
                    }
                    if (lc.getMaxA() != null) {
                        HELP += b + " &fMaximum arguments: &e" + lc.getMaxA() + n;
                    }
                }

                if (!subCommands.isEmpty()) {
                    HELP += c;
                    HELP += b + " &fSubCommands:" + n;
                    HELP += c;
                    for (LiteSubCommand lsc : subCommands) {
                        HELP += b + ">&a" + lsc.getName();

                        List<String> aliases2 = lsc.getAliases();
                        if (aliases2 != null && !aliases2.isEmpty()) {
                            HELP += "&7[&f" + formatList(aliases2) + "&7]";
                        }

                        HELP += " &f- &a" + lsc.getDescription() + n;
                        if (lsc.getPermission() != null && lsc.getPermission().length() > 1) {
                            HELP += b + "    &9Permission: &c" + lsc.getPermission().toLowerCase() + n;
                        }
                    }
                }
                
                if (!cmdMsgInfo.isEmpty()) {
                    HELP += c;
                    HELP += b + " &fSubArguments:" + n;
                    HELP += c;
                    for (CmdMsg cm : cmdMsgInfo) {
                        HELP += b + ">&a" + cm.name + " &f- &a" + cm.desc + n;
                    }
                }

                // Tutaj do tej specjalnej listy dodtakowych argumentow ifa, jezeli tam nie jest empty
                HELP += a;
                HELP = translate(HELP);
            }
            player.sendMessage(HELP);

            return true;
        }

        return false;
    }
    
    public void addArgMsgInfo(String name, String desc) {
        cmdMsgInfo.add(new CmdMsg(name, desc));
    }
    
    private class CmdMsg { 
        public String name;
        public String desc;
        
        public CmdMsg(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }
    }

    public String formatList(List<String> toFormat) {
        String list = "";

        for (int j = 0; j < toFormat.size(); j++) {
            list += toFormat.get(j);
            if (j != toFormat.size() - 1) {
                list += ", ";
            }
        }

        return list;
    }

    // Execute struct
    @Override
    public String getName() {
        return super.getName().toLowerCase();
    }

    protected void setValues(Player player, String[] args) {
        this.player = player;
        this.args = args;
        this.len = args.length;
        this.mainArg = null;
        if (len != 0) {
            this.mainArg = args[0];
        }
    }

    protected Player player;
    protected String[] args;
    protected int len;
    protected String mainArg;

    protected boolean hasSubPermission(String sub) {
        if (sub == null) {
            return true;
        }

        String main = getPermission();

        if (main == null) {
            return player.hasPermission(sub);
        }

        return player.hasPermission(main + "." + sub);
    }

    protected void send(String message) {
        send(player, message);
    }

    protected void send(Player player, String message) {
        send(player, true, message);
    }

    protected void send(CommandSender sender, boolean prefix, String message) {
        sender.sendMessage((prefix && messagePrefix != null ? messagePrefix + " " : "") + translate("&f" + message));
    }

    protected String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    protected boolean arg(String arg) {
        return arg.equalsIgnoreCase(mainArg);
    }

    protected Integer num(String string) {
        Integer number = null;
        try {
            number = Integer.parseInt(string);
        } catch (Exception e) {
        }
        return number;
    }

    protected String getRest(int startIndex, String[] args) {
        String s = "";
        for (int i = startIndex; i < args.length; i++) {
            s += args[i];
            if (i != args.length - 1) {
                s += " ";
            }
        }
        return s;
    }
}
