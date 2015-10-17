package pl.merbio.charsapi.commands.sub.updater;

import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.managers.UpdatersManager;
import pl.merbio.charsapi.other.Lang;

public class USelectSubCommand extends SubCommand{
    
    private boolean areYouSure = false;
    private String name = "";

    public USelectSubCommand() {
        super("select", Lang.CMD_DESC_U_SELECT, null);
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
        
        if(!this.name.equalsIgnoreCase(name)){
            this.name = name;
            areYouSure = false;
        }
        
        if(UpdatersContent.selected_updater != null){
            if(!areYouSure){
                send(Lang.U_SELECT_CONFIRM_MESSAGE);
                areYouSure = true;
                return false;
            }
        }
        
        UpdatersContent.selected_updater = UpdatersManager.getUpdater(name);
        UpdatersContent.selected_updater_name = name;
        send(UpdatersContent.upName() + Lang.U_SELECT_COMPLETE);
        areYouSure = false;
        return true;
    }
    
}