package pl.merbio.charsapi.commands.sub.updater;

import pl.merbio.Main;
import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.other.Lang;

public class UAddSubCommand extends SubCommand{

    public UAddSubCommand() {
        super("add", Lang.CMD_DESC_U_ADD, null);
        setSubSub("updater");
        setMins(1, null, Lang.CMD_ARGS_BUILD);
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
        
        String rest = getRest(0, args);
        
        UpdatersContent.selected_updater.addCharsString(Main.getMainBuilder(), rest);
    
        send(UpdatersContent.upName() + Lang.U_ADD_CHARS_ADDED.replace("%TEXT%", rest));
        return true;
    }
    
}