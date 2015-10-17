package pl.merbio.charsapi.animations.in;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Location;
import pl.merbio.charsapi.animations.InputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsFallingBlockContainer;
import pl.merbio.charsapi.objects.CharsBlock;
import pl.merbio.charsapi.objects.CharsMaterial;

/**
 * @author Merbio
 */

public class DropColors extends InputAnimation {
    
    private HashMap<Byte, ArrayList<Point>> datas;
    private ArrayList<Byte> bytes;
    private CharsFallingBlockContainer cfbc;
    
    public DropColors() {
        super(20L);
    }
    
    @Override
    protected void onPrepare() {
        cfbc = new CharsFallingBlockContainer();
        datas = new HashMap<>();
        bytes = new ArrayList<>();
        
        for (int i = 0; i < 16; i++) {
            datas.put((byte) i, new ArrayList<Point>());
        }
        
        for (int h = 0; h < cs.getHeight(); h++) {
            for (int w = 0; w < cs.getWidth(); w++) {                
                CharsBlock cb = cs.getCharsBlock(w, h);
                
                if (cb != null) {
                    byte b = cb.getData();
                    if (!bytes.contains(b)) {
                        bytes.add(b);
                    }
                    datas.get(b).add(new Point(w, h, cb));
                }
            }
        }
    }
    
    @Override
    protected void onCancel() {
        cfbc.clearBlocks();
    }
    
    @Override
    public void run() {
        if (bytes.isEmpty()) {
            stopTask();
            for (int h = 0; h < cs.getHeight(); h++) {
                for (int w = 0; w < cs.getWidth(); w++) {                    
                    CharsBlock cb = cs.getCharsBlock(w, h);
                    
                    if (cb != null) {
                        Location l = csl.over[w][h];
                        cfbc.spawnFallingBlock(l.clone().add(0,20,0), l, new CharsMaterial(overMaterial, overData));
                    }
                }
            }
            return;
        }
        
        int index = bytes.size() - 1;
        Byte b = bytes.get(index);
        bytes.remove(index);
        
        for (Point p : datas.get(b)) {
            CharsBlock cb = cs.getCharsBlock(p.w, p.h);
            
            if (cb != null) {
                Location l = csl.front[p.w][p.h];
                cfbc.spawnFallingBlock(l.clone().add(0,20,0), l, new CharsMaterial(cb.getType(), b));
            }
        }
    }
    
}