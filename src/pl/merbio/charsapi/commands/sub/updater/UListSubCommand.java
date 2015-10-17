package pl.merbio.charsapi.commands.sub.updater;

import java.util.ArrayList;
import java.util.HashMap;
import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.managers.UpdatersManager;
import pl.merbio.charsapi.objects.CharsUpdater;
import pl.merbio.charsapi.other.Lang;

public class UListSubCommand extends SubCommand{

    public UListSubCommand() {
        super("list", Lang.CMD_DESC_U_LIST, null);
        setSubSub("updater");
        setMins(0, 0, "");
    }

    @Override
    protected boolean execute(String[] args) {
        HashMap<String, CharsUpdater> map = UpdatersManager.getMap();
        ArrayList<String> keys = new ArrayList<>();
        for(String s: map.keySet()){
            keys.add(s);
        }
        
        if(keys.isEmpty()){
            send(Lang.U_LIST_EMPTY);
            return false;
        }
        
        String ups = "";
        
        for(int i = 0; i < keys.size() ;i++){
            String name = keys.get(i);
            ups += map.get(name).isRunning() ? "&a" : "&c";
            ups += name;
            if(i != keys.size() - 1){
                ups += "&7, ";
            }
        }
        
        send(Lang.U_LIST_INFO + " \n" + ups);
        return true;
    }
    
}