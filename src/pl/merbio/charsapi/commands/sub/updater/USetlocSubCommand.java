package pl.merbio.charsapi.commands.sub.updater;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import pl.merbio.Main;
import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.other.Lang;

public class USetlocSubCommand extends SubCommand{

    public USetlocSubCommand() {
        super("setloc", Lang.CMD_DESC_U_SETLOC, null);
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
        
        Location location = player.getLocation();
        BlockFace face = Main.getMainBuilder().getPlayerFacing(location);
        
        UpdatersContent.selected_updater.setLocation(location);
        UpdatersContent.selected_updater.setFacing(face);
                
        send(UpdatersContent.upName() + Lang.U_SETLOC_COMPLETE);
        return true;
    }
    
}
