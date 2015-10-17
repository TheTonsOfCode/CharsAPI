package pl.merbio.charsapi.commands.sub.updater;

import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.other.Lang;

public class USettimeSubCommand extends SubCommand{

    public USettimeSubCommand() {
        super("settime", Lang.CMD_DESC_U_SETTIME, null);
        setSubSub("updater");
        setMins(1, 1, Lang.CMD_U_ARG_UPDATER_TIME);
    }

    @Override
    protected boolean execute(String[] args) {
        if(UpdatersContent.selected_updater == null){
            send(SubCommand.Message.UPDATER_NON_SELECT);
            return false;
        }
        
        if(UpdatersContent.selected_updater.isRunning()){
            send(SubCommand.Message.UPDATER_WHEN_RUNNING);
            return false;
        }
        
        Integer seconds = num(args[0]);
        
        if(seconds == null){
            send(Message.NUMBER);
            return false;
        }

        UpdatersContent.selected_updater.setSeconds(seconds);
                
        send(UpdatersContent.upName() + Lang.U_SETTIME_COMPLETE);
        return true;
    }
    
}