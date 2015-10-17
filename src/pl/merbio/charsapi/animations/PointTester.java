package pl.merbio.charsapi.animations;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * @author Merbio
 */

public class PointTester {
    
    public Location point;
    
    public PointTester(Location point){
        this.point = point;
        build(0, 0);
    }
    
    public void build(int x, int y){
        this.point.getBlock().setType(Material.DIAMOND_BLOCK);
        this.point.add(-x, -y, 0);
        this.point.getBlock().setType(Material.LAPIS_BLOCK);
    }
}
