package pl.merbio.charsapi.objects;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;
import pl.merbio.Main;
import pl.merbio.charsapi.listeners.FallingBlocksListeners;

/**
 * @author Merbio
 */

public class CharsFallingBlockContainer {
    private static CharsMaterial[] wulcano_blocks = {
        new CharsMaterial(Material.STAINED_CLAY, 1),
        new CharsMaterial(Material.STAINED_CLAY, 4),
        new CharsMaterial(Material.STAINED_CLAY, 14),
        new CharsMaterial(Material.STAINED_CLAY, 15),
        new CharsMaterial(Material.WOOL, 1),
        new CharsMaterial(Material.WOOL, 4),
        new CharsMaterial(Material.WOOL, 14),
        new CharsMaterial(Material.WOOL, 15)
    };
            
    private ArrayList<FallingBlock> blocks;

    public CharsFallingBlockContainer(){
        blocks = new ArrayList<>();
    }
    
    public void throwFallingBlockFromBlock(Location location){
        Block b = location.getBlock();
        FallingBlock fal = location.getWorld().spawnFallingBlock(location, b.getType(), b.getData());
        fal.setVelocity(new Vector(rand(3, 10), Math.abs(rand(1, 4)), rand(3, 10)));
        FallingBlocksListeners.setProtectBlock(fal);
        fal.setDropItem(false);
        blocks.add(fal);
    }
    
    public void wulcanoFallingBlockFromBlock(Location location){
        Block b = location.getBlock();
        CharsMaterial cm = wulcano_blocks[new Random().nextInt(wulcano_blocks.length)];
        FallingBlock fal = location.getWorld().spawnFallingBlock(location, cm.getMaterial(), cm.getData());
        fal.setVelocity(new Vector((double) (rand(10, 50) / 100), (double) (Math.abs(rand(20, 90)) / 100) + 1, (double) (rand(10, 50)) / 100));
        FallingBlocksListeners.setProtectBlock(fal);
        fal.setDropItem(false);
        blocks.add(fal);
    }
    
    // GUN Aniation throw
    
    private int rand(int a, int b) {
        Random r = new Random();
        int c = r.nextInt(b) + a;
        return r.nextBoolean() ? c : -c;
    }
    
    public void dropFallingBlockFormBlock(Location location){
        Block b = location.getBlock();
        dropFallingBlock(location, b.getType(), b.getData());
    }
    
    public void dropFallingBlock(Location location, Material material, Byte data){
        FallingBlock fal = location.getWorld().spawnFallingBlock(location, material, data);
        FallingBlocksListeners.setProtectBlock(fal);
        fal.setDropItem(false);
        blocks.add(fal);
    }
    
    public void spawnFallingBlock(Location location, Location to, CharsMaterial block){
        FallingBlock fal;
        FallingBlocksListeners.setProtectBlock(fal = location.getWorld().spawnFallingBlock(location, block.getMaterial(), block.getData()));
        fal.setDropItem(false);
        blocks.add(fal);
        isEnded(fal, to);      
    }
    
    public void spawnMatrixFallingBlock(Location location, Location to, CharsMaterial ended){
        FallingBlock fal = location.getWorld().spawnFallingBlock(location, Material.GLOWSTONE, (byte) 0);
        FallingBlocksListeners.setProtectBlock(fal);
        isChange(ended, fal, to, 0, 0, 
                new CharsMaterial(Material.STAINED_CLAY, (byte) 5), 
                new CharsMaterial(Material.STAINED_CLAY, (byte) 13),
                new CharsMaterial(Material.WOOL, (byte) 5),
                new CharsMaterial(Material.WOOL, (byte) 13)
        );
        blocks.add(fal);
    }
    
    public void clearBlocks(){
        if(!blocks.isEmpty()){
            for(FallingBlock f: blocks){
                f.remove();
            }
            
            blocks = new ArrayList<>();
        }
    }
    
    private void isChange(final CharsMaterial ended, final FallingBlock fb, final Location to, final int materialIndex, final int change, final CharsMaterial... materials){
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(fb.getLocation().getBlockY() == to.getBlockY() || fb.isDead()){
                    if(fb.getLocation().getBlockY() == to.getBlockY()){
                        fb.remove();
                    }
                    
                    if(blocks.contains(fb)){
                        Block b = to.getBlock();
                        b.setType(ended.getMaterial());
                        b.setData(ended.getData());
                    }
                    
                    blocks.remove(fb);
                    return;
                }
                
                int nn = change + 1;
                if(nn != 2){
                    isChange(ended, fb, to, materialIndex, nn, materials);
                    return;
                }
                
                int next = materialIndex + 1;
                if(next == materials.length){
                    next = 0;
                }

                CharsMaterial material = materials[next];
                
                blocks.remove(fb);
                FallingBlock newFallingBlock = replaceFallingBlock(fb, material.getMaterial(), material.getData(), new Vector(0, -0.4D, 0));
                blocks.add(newFallingBlock);               
                
                isChange(ended, newFallingBlock, to, next, 0, materials);
            }
        }, 1L);
    }
    
    private FallingBlock replaceFallingBlock(FallingBlock fallingBlock, Material material, Byte data){
        return replaceFallingBlock(fallingBlock, material, data, fallingBlock.getVelocity());
    }
    
    private FallingBlock replaceFallingBlock(FallingBlock fallingBlock, Material material, Byte data, Vector vector){
        Location location = fallingBlock.getLocation();
        fallingBlock.remove();

        FallingBlock fal = location.getWorld().spawnFallingBlock(location, material, data);
        FallingBlocksListeners.setProtectBlock(fal);
        fal.setVelocity(vector);
        fal.setDropItem(false);
        return fal;       
    }
    
    private void isEnded(final FallingBlock fb, final Location to){
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(fb.getLocation().getBlockY() == to.getBlockY() || fb.isDead()){
                    if(fb.getLocation().getBlockY() == to.getBlockY()){
                        fb.remove();
                    }
                    
                    if(blocks.contains(fb)){
                        Block b = to.getBlock();
                        b.setType(fb.getMaterial());
                        b.setData(fb.getBlockData());
                    }
                    
                    blocks.remove(fb);
                    return;
                }
                
                isEnded(fb, to);                
            }
        }, 1L);
    }
}
