package pl.merbio.charsapi.commands.sub.updater;

import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.managers.UpdatersManager;
import pl.merbio.charsapi.other.Lang;

public class USaveSubCommand extends SubCommand{

    public USaveSubCommand() {
        super("save", Lang.CMD_DESC_U_SAVE, null);
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
            send(Message.UPDATER_WHEN_RUNNING);
            return false;
        }
        
        if(UpdatersContent.selected_updater.getLinesInfo().length < 2){
            send(Message.UPDATER_TO_LITTLE_STRINGS);
            return false;
        }
        
        UpdatersContent.selected_updater.start();
        
        send(UpdatersContent.upName() + Lang.U_START_COMPLETE);       
        send(UpdatersContent.upName() + Lang.U_SAVE_COMPLETE); 
        
        UpdatersManager.putUpdater(UpdatersContent.selected_updater_name, UpdatersContent.selected_updater);
        
        UpdatersContent.selected_updater = null;
        return true;
    }
    
}