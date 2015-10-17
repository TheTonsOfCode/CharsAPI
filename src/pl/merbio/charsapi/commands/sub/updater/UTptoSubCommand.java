package pl.merbio.charsapi.commands.sub.updater;

import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.managers.UpdatersManager;
import pl.merbio.charsapi.other.Lang;

public class UTptoSubCommand extends SubCommand{

    public UTptoSubCommand() {
        super("tpto", Lang.CMD_DESC_U_TPTO, null);
        setSubSub("updater");
        setMins(1, 1, Lang.CMD_U_ARG_UPDATER_NAME);
    }

    @Override
    protected boolean execute(String[] args) {
        String name = args[0];
        
        if(!UpdatersManager.existUpdater(name)){
            send(Message.UPDATER_NOFIND);
            return false;
        }

        player.teleport(UpdatersManager.getUpdater(name).getLocation().setDirection(player.getLocation().getDirection()));
        send(Lang.U_TPTO_COMPLETE.replace("%NAME%", name.toUpperCase()));
        return true;
    }
    
}
