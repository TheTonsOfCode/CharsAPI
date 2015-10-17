package pl.merbio.utilities.generators;

import org.bukkit.Material;

/**
 * @author Merbio
 */

public class SchemeRecipment {
    
    private String string;
    private Material material;
    private Byte data;
    
    public SchemeRecipment(String string, Material material) {
        Character c = string.charAt(0);
        this.string = c.toString();
        this.material = material;
        this.data = 0;
    }
    
    public SchemeRecipment(String string, Material material, Byte data) {
        this(string, material);
        this.data = data;
    }
    
    public SchemeRecipment(String string, Material material, int data) {
        this(string, material);
        this.data = (byte) data;
    }
    
    public String getRecip() {
        return string;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public Byte getData() {
        return data;
    }
}
