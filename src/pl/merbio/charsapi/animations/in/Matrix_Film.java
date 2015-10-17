package pl.merbio.charsapi.animations.in;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Location;
import pl.merbio.charsapi.animations.InputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsFallingBlockContainer;
import pl.merbio.charsapi.objects.CharsBlock;
import pl.merbio.charsapi.objects.CharsMaterial;

public class Matrix_Film extends InputAnimation{
    
    CharsFallingBlockContainer cfbc;
    ArrayList<Point> list;
    int iterator, max;
    Random r;

    public Matrix_Film() {
        super(3L);
    }

    @Override
    protected void onPrepare() {
        cfbc = new CharsFallingBlockContainer();
        list = new ArrayList<>();
        for (int h = 0; h < cs.getHeight(); h++) {
            for (int w = 0; w < cs.getWidth(); w++) {           
                CharsBlock cb = cs.getCharsBlock(w, h);
                if(cb != null){
                    list.add(new Point(w, h, cb));
                }
            }
        }
        
        iterator = 0;
        max = list.size();
        r = new Random(2342);
    }

    @Override
    protected void onCancel() {
        cfbc.clearBlocks();
    }

    @Override
    public void run() {
        if(iterator == max - 1){
            stopTask();
        }
        
        int index = r.nextInt(list.size());
        Point p = list.get(index);
        
        Location location = csl.front[p.w][p.h];        
        CharsBlock cb = cs.getCharsBlock(p.w, p.h);
        cfbc.spawnMatrixFallingBlock(location.clone().add(0, 20, 0), location, new CharsMaterial(cb.getType(), cb.getData()));
        
        if(hasOverline){
            location = csl.over[p.w][p.h];        
            cb = cs.getCharsBlock(p.w, p.h);
            cfbc.spawnMatrixFallingBlock(location.clone().add(0, 20, 0), location, new CharsMaterial(overMaterial, overData));
        }
        
        list.remove(index);
        
        iterator++;
    }
    
}
