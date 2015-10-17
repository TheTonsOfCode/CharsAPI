package pl.merbio.charsapi.animations.in;

import java.util.ArrayList;
import java.util.Random;
import pl.merbio.charsapi.animations.InputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsFallingBlockContainer;
import pl.merbio.charsapi.objects.CharsBlock;

/**
 * @author Merbio
 */

public class StarFire extends InputAnimation {

    private ArrayList<Point> list;
    private int iterator, max;
    private Random r;
    private CharsFallingBlockContainer cfbc;
            
    public StarFire() {
        super(2L);
    }

    @Override
    protected void onPrepare() {
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
        r = new Random(43535435);
        cfbc = new CharsFallingBlockContainer();
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
        spawnFallingBlock(cfbc, p);
        list.remove(index);
        
        iterator++;
    }
}