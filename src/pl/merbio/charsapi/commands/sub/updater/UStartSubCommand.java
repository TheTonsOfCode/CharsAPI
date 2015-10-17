package pl.merbio.charsapi.commands.sub.updater;

import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.other.Lang;

public class UStartSubCommand extends SubCommand{

    public UStartSubCommand() {
        super("start", Lang.CMD_DESC_U_START, null);
        setSubSub("updater");
        setMins(0, 0, "");
    }

    @Override
    protected boolean execute(String[] args) {
        if(UpdatersContent.selected_updater == null){
            send(Message.UPDATER_NON_SELECT);
            return false;
        }
        
        if(UpdatersContent.selected_updater.isRunning()){
            send(UpdatersContent.upName() + Lang.U_START_IS_RUNING);
            return false;
        }
        
        if(UpdatersContent.selected_updater.getLinesInfo().length < 2){
            send(Message.UPDATER_TO_LITTLE_STRINGS);
            return false;
        }
        
        UpdatersContent.selected_updater.start();
        
        send(UpdatersContent.upName() + Lang.U_START_COMPLETE);
        return true;
    }
    
}