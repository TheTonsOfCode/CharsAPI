package pl.merbio.charsapi.commands.sub.updater;

import pl.merbio.charsapi.objects.CharsUpdater;

public class UpdatersContent {
    
    public static CharsUpdater selected_updater = null;
    
    public static String selected_updater_name = "<-- NONE -->";
    
    public static String upName(){
        return "&7(&e" + selected_updater_name.toUpperCase() + "&7) "; 
    }
}
