package pl.merbio.utilities.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Merbio
 */
public abstract class LiteCommand extends LiteSubCommand {

    public LiteCommand(String name, String description, String... aliases) {
        super(name, description, aliases);
    }

    public void setPermissionPrefix(String permPrefix) {
        if (permPrefix == null) {
            return;
        }

        this.setPermission(permPrefix + "." + getName());
    }

    @Override
    public boolean execute(CommandSender sender, String string, String[] args) {
        String perm = this.getPermission();
        if (perm.length() > 1 && !sender.hasPermission(perm)) {
            send(sender, true, "&cYou dont have permission! &7(&f" + perm + "&7)");
            return false;
        }

        if (!(sender instanceof Player)) {
            send(sender, true, "&cYou must be a player!");
            return false;
        }

        setValues((Player) sender, args);

        if (len == 1) {
            if (checkHelp(this, mainArg)) {
                return true;
            }
        }

        if ((minA != null && len < minA) || (maxA != null && len > maxA)) {
            send(player, false, this.usageMessage);
            return false;
        }

        return execute();
    }

    private Integer minA, maxA;

    protected void setMins(Integer minA, Integer maxA, String correct) {
        this.minA = minA;
        this.maxA = maxA;
        this.usageMessage = "&c/" + getName() + " " + (correct != null ? correct : "");
    }

    public Integer getMinA() {
        return minA;
    }

    public Integer getMaxA() {
        return maxA;
    }
}
