package pl.merbio.charsapi.objects;

import org.bukkit.Material;

/**
 * @author Merbio
 */

public enum ColoredMaterial {

    WOOL("W", Material.WOOL),
    CLAY("C", Material.STAINED_CLAY),
    GLASS("G", Material.STAINED_GLASS),
    GLASS_PANE("P", Material.STAINED_GLASS_PANE),
    LOG_1("L", Material.LOG),
    LOG_2("I", Material.LOG_2);
    
    private String shortname;
    private Material material;
    
    private ColoredMaterial(String shortname, Material material){
        this.shortname = shortname;
        this.material = material;
    }
    
    public String getShortName(){
        return shortname;
    }
    
    public Material getMaterial(){
        return material;
    }
}
