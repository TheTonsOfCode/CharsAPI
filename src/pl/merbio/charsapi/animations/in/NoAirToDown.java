package pl.merbio.charsapi.animations.in;

import java.util.ArrayList;
import pl.merbio.charsapi.animations.InputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsBlock;

public class NoAirToDown extends InputAnimation {

    ArrayList<Point> list;
    int iterator, max;
            
    public NoAirToDown() {
        super(1L);
    }

    @Override
    protected void onPrepare() {
        list = new ArrayList<>();
        
        for (int w = 0; w < cs.getWidth(); w++) { 
            for (int h = 0; h < cs.getHeight(); h++) {                      
                CharsBlock cb = cs.getCharsBlock(w, h);
                if(cb != null){
                    list.add(new Point(w, h, cb));
                }
            }
        }
                                                                                    
        iterator = 0;
        max = list.size();
    }

    @Override
    protected void onCancel() {
    
    }

    @Override
    public void run() {
        if(iterator == max - 1){    
            stopTask();
        }

        setBlocksInPoint(list.get(iterator));

        iterator++;
    }
}