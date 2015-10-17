package pl.merbio.charsapi.animations.in;

import java.util.ArrayList;
import pl.merbio.charsapi.animations.InputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsFallingBlockContainer;
import pl.merbio.charsapi.objects.CharsBlock;

/**
 * @author Merbio
 */

public class FillToRight extends InputAnimation {

    private ArrayList<Point> list;
    private int iterator, max;
    private CharsFallingBlockContainer cfbc;
            
    public FillToRight() {
        super(1L);
    }

    @Override
    protected void onPrepare() {
        list = new ArrayList<>();
        
        for (int h = cs.getHeight() - 1; h >= 0; h--) {
            for (int w = 0; w < cs.getWidth(); w++) {           
                CharsBlock cb = cs.getCharsBlock(w, h);
                if(cb != null){
                    list.add(new Point(w, h, cb));
                }
            }
        }
        
        iterator = 0;
        max = list.size();
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
        
        spawnFallingBlock(cfbc, list.get(iterator));
        
        iterator++;
    }
}