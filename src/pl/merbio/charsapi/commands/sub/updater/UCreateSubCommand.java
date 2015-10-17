package pl.merbio.charsapi.commands.sub.updater;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import pl.merbio.Main;
import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.managers.UpdatersManager;
import pl.merbio.charsapi.objects.CharsUpdater;
import pl.merbio.charsapi.other.Lang;

public class UCreateSubCommand extends SubCommand{

    public UCreateSubCommand() {
        super("create", Lang.CMD_DESC_U_CREATE, null);
        setSubSub("updater");
        setMins(2, 2, Lang.CMD_U_ARG_UPDATER_NAME + " " + Lang.CMD_U_ARG_UPDATER_TIME);
    }

    @Override
    protected boolean execute(String[] args) {
        String name = args[0];
        
        if(UpdatersManager.existUpdater(name)){
            send(Lang.U_CREATE_ALERADY_EXIST_UPDATER);
            return false;
        }
        
        Integer seconds = num(args[1]);
        
        if(seconds == null){
            send(Message.NUMBER);
            return false;
        }
        
        Location location = player.getLocation();
        BlockFace face = Main.getMainBuilder().getPlayerFacing(location);
        
        UpdatersContent.selected_updater = new CharsUpdater(seconds, location, face);
        UpdatersContent.selected_updater_name = name;
        send(Lang.U_UPDATER_CREATE.replace("%TIME%", seconds.toString()).replace("%FACING%", face.name()));
        return true;
    }
    
}
