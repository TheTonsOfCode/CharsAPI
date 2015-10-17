package pl.merbio.charsapi.commands.sub.updater;

import org.bukkit.Bukkit;
import pl.merbio.Main;
import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.managers.UpdatersManager;
import pl.merbio.charsapi.other.Lang;

/**
 * @author Merbio
 */

public class UFlushSubCommand extends SubCommand{
    
    boolean areYouSure = false;

    public UFlushSubCommand(){
        super("flush", Lang.CMD_DESC_U_FLUSH, null);
        setSubSub("updater");
        setMins(0, 0, "");
    }
    @Override
    protected boolean execute(String[] args) {
        if(!areYouSure){
            send(Lang.U_FLUSH_CONFIRM_MESSAGE);
            areYouSure = true;

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    areYouSure = false;
                }
            }, 20L * 10);
            return false;
        }
        
        UpdatersManager.flushAllUpdaters();
        
        areYouSure = false;
        send(Lang.U_FLUSH_COMPLETE);
        return true;    
    }

}
