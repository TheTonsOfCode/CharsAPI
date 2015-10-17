package pl.merbio.charsapi.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.merbio.charsapi.other.Lang;

public abstract class SubCommand {

    private String name, description;
    private String[] aliases;

    public SubCommand(String name, String description, String... aliases) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
    }

    private Integer minA, maxA;
    private String correct;

    protected void setMins(Integer minA, Integer maxA, String correct) {
        this.minA = minA;
        this.maxA = maxA;
        this.correct = correct;
    }
    
    private String subSub;
    
    protected void setSubSub(String subSub){
        this.subSub = subSub + " ";
    }

    protected abstract boolean execute(String[] args);
    protected int len;
    protected String mainArg;
    protected Player player;

    public boolean onCommand(Player player, String[] args) {
        this.player = player;
        this.len = args.length;

        if ((minA != null && len < minA) || (maxA != null && len > maxA)) {
            send("&c/chars " + (subSub != null ? subSub : "") + name + " " + correct);
            return false;
        }    
        
        if (len != 0) {
            this.mainArg = args[0];
        }

        return execute(args);
    }

    protected void send(String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&o[&6&oCharsAPI&e&o]&f " + message));
    }
    
    protected void send(Message message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&o[&6&oCharsAPI&e&o]&f " + message.get()));
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

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String[] getAliases() {
        return this.aliases;
    }
    
    protected enum Message {
        
        NUMBER(Lang.NO_NUMBER),
        UPDATER_NON_SELECT(Lang.UPDATER_NON_SELECT),
        UPDATER_WHEN_RUNNING(Lang.UPDATER_USE_WHEN_RUNNING),
        UPDATER_NOFIND(Lang.UPDATER_NOFIND),
        UPDATER_TO_LITTLE_STRINGS(Lang.UPDATER_TO_LITTLE_STRINGS_ARGS);
        
        private String message;
        
        private Message(String message){
            this.message = message;
        }
        
        public String get(){
            return message;
        }
        
        @Override
        public String toString(){
            return message;
        }
    }
}
