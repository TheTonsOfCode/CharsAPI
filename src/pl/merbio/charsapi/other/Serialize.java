package pl.merbio.charsapi.other;

import java.awt.Font;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Serialize {
    
    private static String key = ":";
    
    public static String serializeFont(Font font){
        return font.getSize() + key + font.getStyle() + key + font.getFamily();
    }
    
    public static Font unserializeFont(String serial){
        String tokens[] = serial.split(key);
        if(tokens.length != 3){
            return null;
        }
        return new Font(tokens[2], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[0]));
    }
    
    public static String serializeLocation(Location location){
        if(location == null){
            return null;
        }
        return location.getWorld().getName() + key + location.getBlockX() + key + location.getBlockY() + key + location.getBlockZ();
    }
    
    public static boolean isSerialWorldDetected(String serial){
        return Bukkit.getServer().getWorld(serial.split(key)[0]) != null;
    }
    
    public static Location unserializeLocation(String serial){
        String tokens[] = serial.split(key);
        if(tokens.length != 4){
            return null;
        }

        return new Location(
                Bukkit.getServer().getWorld(tokens[0]), 
                Integer.parseInt(tokens[1]), 
                Integer.parseInt(tokens[2]), 
                Integer.parseInt(tokens[3])
        );
    }
}
