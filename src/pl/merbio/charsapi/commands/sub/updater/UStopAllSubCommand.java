package pl.merbio.charsapi.commands.sub.updater;

import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.managers.UpdatersManager;
import pl.merbio.charsapi.other.Lang;

/**
 * @author Merbio
 */

public class UStopAllSubCommand extends SubCommand{

    public UStopAllSubCommand() {
        super("stopall", Lang.CMD_DESC_U_STOPALL, null);
        setSubSub("updater");
        setMins(0, 0, "");
    }

    @Override
    protected boolean execute(String[] args) {
        int updts = UpdatersManager.stopAllUpdaters();
                
        send(Lang.U_STOPALL_COMPLETE.replace("%NUMBER%", String.valueOf(updts)));       
        return true;
    }
    
}