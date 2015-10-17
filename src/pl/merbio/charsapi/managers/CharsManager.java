package pl.merbio.charsapi.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import pl.merbio.charsapi.objects.CharsString;

public class CharsManager {
    
    private static HashMap<Integer, CharsString> store = new HashMap<>();
    
    private static Integer getFreeInt(){
        Integer i = 0;
        for(;;i++){
            if(!store.containsKey(i)){
                return i;
            }
        }
    }
    
    public static Integer addChars(CharsString cs){
        Integer i = getFreeInt();
        store.put(i, cs);
        return i;
    }
    
    public static void clearAll(){
        ArrayList<Integer> keys = new ArrayList<>();
        
        for(Integer i: store.keySet()){
            keys.add(i);
        }
        
        for(int i = store.size() - 1; i >= 0 ;i--){
            store.get(i).clearChars(false);
            store.remove(i);
        }
    }
    
    public static boolean existChars(Integer id){
        return store.containsKey(id);
    }
    
    public static void clearChars(Integer id){
        if(!store.containsKey(id)){
            return;
        }
        
        store.get(id).clearChars(true);
        store.remove(id);
    }
}
