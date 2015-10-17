package pl.merbio.charsapi.commands.sub.updater;

import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.other.Lang;

public class UStopSubCommand extends SubCommand{

    public UStopSubCommand() {
        super("stop", Lang.CMD_DESC_U_STOP, null);
        setSubSub("updater");
        setMins(0, 0, "");
    }

    @Override
    protected boolean execute(String[] args) {
        if(UpdatersContent.selected_updater == null){
            send(Message.UPDATER_NON_SELECT);
            return false;
        }
        
        if(!UpdatersContent.selected_updater.isRunning()){
            send(UpdatersContent.upName() + Lang.U_STOP_IS_STOPED);
            return false;
        }
        
        UpdatersContent.selected_updater.cancel();
        
        send(UpdatersContent.upName() + Lang.U_STOP_COMPLETE);       
        return true;
    }
    
}