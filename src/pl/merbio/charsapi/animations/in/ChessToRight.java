package pl.merbio.charsapi.animations.in;

import java.util.ArrayList;
import pl.merbio.charsapi.animations.InputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsBlock;

/**
 * @author Merbio
 */

public class ChessToRight extends InputAnimation{
    
    ArrayList<Point> list;
    private int loop;
    private int block;
    private int max;                

    public ChessToRight() {
        super(1L);
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
        
        loop = 0;
        block = 0;
        max = list.size();
    }

    @Override
    protected void onCancel() {
    
    }

    @Override
    public void run() {
        if(loop == 2){
            stopTask();
            return;
        }
        if(block == max){
            block = 0;
            loop++;
        }
        
        Point p = list.get(block);
                                                                                                                                                                                            
        int w = p.w;
        int h = p.h;
                                            
        if(loop == 0){                                                                                                                                                                                                                                                                                                                                                                          
            if((w + h) % 2 == 0)
            setBlocks(w, h);
        }
        else {
            if((w + h) % 2 != 0)
            setBlocks(w, h);
        }
               
        block++;
    }

}