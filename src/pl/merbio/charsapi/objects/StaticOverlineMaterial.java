package pl.merbio.charsapi.objects;

import org.bukkit.Material;

/**
 * @author Merbio
 */

public enum StaticOverlineMaterial {

    WOOL("W", Material.WOOL,(byte) 15),
    CLAY("C", Material.STAINED_CLAY,(byte) 15),
    GLASS("G", Material.STAINED_GLASS,(byte) 15),
    GLASS_PANE("P", Material.STAINED_GLASS_PANE,(byte) 15),
    LIGHT_BLOCK("L", Material.GLOWSTONE,(byte) 0);
    
    private String shortname;
    private Material material;
    private byte data;
    
    private StaticOverlineMaterial(String shortname, Material material, byte data){
        this.shortname = shortname;
        this.material = material;
        this.data = data;
    }
    
    public String getShortName(){
        return shortname;
    }
    
    public Material getMaterial(){
        return material;
    }
    
    public byte getData(){
        return data;
    }
    
    public CharsMaterial getCharsMaterial(){
        return new CharsMaterial(material, data);
    }
}