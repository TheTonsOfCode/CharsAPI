package pl.merbio.charsapi.objects;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * @author Merbio
 */

public class CharsMaterial {
    
    private Material material;
    private Byte data;
    private Location location;
    
    public CharsMaterial(Material material, Byte data){
        this.material = material;
        this.data = data;
    }
    
    public CharsMaterial(Material material, int data){
        this.material = material;
        this.data = (byte) data;
    }
    
    public CharsMaterial(Material material, Byte data, Location location){
        this(material, data);
        this.location = location;
    }
    
    public Material getMaterial(){
        return material;
    }
    
    public Byte getData(){
        return data;
    }
    
    public Location getLocation(){
        return location;
    }
}
