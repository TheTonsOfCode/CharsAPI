package pl.merbio.charsapi.managers;

import java.util.HashMap;
import java.util.Set;
import pl.merbio.Main;
import pl.merbio.charsapi.objects.CharsBuilder;
import pl.merbio.charsapi.objects.CharsUpdater;
import pl.merbio.charsapi.objects.CharsUpdaterString;

/**
 * @author Merbio
 */

public class UpdatersManager {

    private static HashMap<String, CharsUpdater> store = new HashMap<>();
    
    public static boolean existUpdater(String idName){
        return store.containsKey(idName.toUpperCase());
    }
    
    public static void putUpdater(String idName, CharsUpdater updater){
        idName = idName.toUpperCase();
        store.put(idName, updater);
        
        FileManager.removeCharsUpdater(idName);
        FileManager.saveCharsUpdater(idName, updater);
    }
    
    public static void removeUpdater(String idName){
        idName = idName.toUpperCase();
        store.get(idName).cancel();
        store.remove(idName);
        
        FileManager.removeCharsUpdater(idName);
    }
    
    public static CharsUpdater getUpdater(String idName){
        return store.get(idName.toUpperCase());
    }
    
    public static Set<String> getKeys(){
        return store.keySet();
    }
    
    public static HashMap<String, CharsUpdater> getMap(){
        HashMap<String, CharsUpdater> map = (HashMap<String, CharsUpdater>) store.clone();
        return map;
    }
    
    public static int stopAllUpdaters(){
        int num = 0;
        
        for(CharsUpdater cu: store.values()){
            if(cu.isRunning()){
                cu.cancel();
                num++;
            }
        }
        
        return num;
    }
    
    public static void flushAllUpdaters(){
        CharsBuilder cb = Main.getMainBuilder();
        for(CharsUpdater cu: store.values()){
            cu.cancel();
            for(CharsUpdaterString cus: cu.getLines()){
                cus.cs = cb.setBeforeBlocks(cb.getBuildLocations(cu.getLocation(), cu.getFacing(), cus.cs), cus.cs);
                cus.cs.flushChars();
            }
            cu.start();
        }
    }
}
