package pl.merbio.charsapi.objects;

import org.bukkit.Location;

public class CharsStringLocations {
    
    public Location[][] front, over;
    public Location begin;
    
    public CharsStringLocations(int width, int heigth) {
        front = new Location[width][heigth];
        over = new Location[width][heigth];
    }
}
