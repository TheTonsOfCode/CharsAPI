package pl.merbio.charsapi.commands.sub.updater;

import pl.merbio.charsapi.commands.CommandUtil;
import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.other.Lang;

public class UInfoSubCommand extends SubCommand{

    public UInfoSubCommand() {
        super("info", Lang.CMD_DESC_U_INFO, null);
        setSubSub("updater");
        setMins(0, 0, "");
    }

    @Override
    protected boolean execute(String[] args) {
        if(UpdatersContent.selected_updater == null){
            send(Message.UPDATER_NON_SELECT);
            return false;
        }
        
        send(
            Lang.U_INFO_UPDATER.replace("%NAME%", UpdatersContent.selected_updater_name.toUpperCase()) + "\n" +         
            Lang.U_INFO_FACING.replace("%FACING%", UpdatersContent.selected_updater.getFacing().name()) + "\n" +
            Lang.U_INFO_TIME.replace("%TIME%", String.valueOf(UpdatersContent.selected_updater.getSeconds())) + "\n" +
            Lang.U_INFO_CHARS_INFO + "\n" 
        );
        
        for(String line: UpdatersContent.selected_updater.getLinesInfo()){
            player.sendMessage(CommandUtil.formatMessage(line));
        }
        return true;
    }
    
}
