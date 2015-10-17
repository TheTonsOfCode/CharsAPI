package pl.merbio.charsapi.commands.sub.updater;

import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.managers.UpdatersManager;
import pl.merbio.charsapi.other.Lang;

/**
 * @author Merbio
 */

public class URemoveSubCommand extends SubCommand{

    public URemoveSubCommand() {
        super("remove", Lang.CMD_DESC_U_REMOVE, null);
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
        
        UpdatersManager.removeUpdater(name);
        send(Lang.U_REMOVE_COMPLETE);
        return true;
    }
    
}
