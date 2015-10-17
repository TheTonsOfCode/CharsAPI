package pl.merbio.charsapi.commands.sub.updater;

import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.other.Lang;

public class UDelSubComand extends SubCommand{

    public UDelSubComand() {
        super("del", Lang.CMD_DESC_U_DEL, null);
        setSubSub("updater");
        setMins(1, 1, Lang.CMD_U_ARG_DEL_ID);
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
        
        Integer id = num(args[0]);
        
        if(id == null){
            send(Message.NUMBER);
            return false;
        }
        
        boolean removed = UpdatersContent.selected_updater.removeCharsString(id);
        
        if(!removed){
            send(Lang.U_DEL_CHARS_UNEDFINED_ID.replace("%ID%", id.toString()));
            send(Lang.U_DEL_UPDATER_LIST_INFO);
        }
        else{
           send(Lang.U_DEL_CHARS_DELETED); 
        }        
        return true;
    }
    
}