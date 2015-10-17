package pl.merbio.charsapi.commands.sub;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;
import pl.merbio.charsapi.managers.FileManager;

public class FontsContent {
    
    public static ArrayList<String> fonts = new ArrayList<>();
    public static ArrayList<String> fontsList = new ArrayList<>();
    
    public static void reg(){
        registerPluginFontFamilyNames();
        fillFonts();  
    }
    
    private static void registerPluginFontFamilyNames(){
        ArrayList<File> alternatives = FileManager.getPluginFontsFiles();
        
        GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for(File f: alternatives){
            try {     
                g.registerFont(Font.createFont(Font.TRUETYPE_FONT, f));
            } catch (Exception e) {}
        }
    } 
    
    private static void fillFonts(){
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        int it = 0;
        String app = "";

        String[] ff = e.getAvailableFontFamilyNames();
        for(int i = 0; i < ff.length ;i++){
            String s = ff[i];
            fonts.add(s);
            app += s;
            if(it == 20 || i == ff.length - 1){
                fontsList.add(app);
                app = "";
                it = 0;
            }
            else {
                app += ", ";
            }
            it++;
        }
    }
    
    public static boolean existFont(String font){
        for (int i = 0; i < fonts.size(); i++) {
            if(fonts.get(i).equalsIgnoreCase(font)){
                return true;
            }
        }
        return false;
    }
}
