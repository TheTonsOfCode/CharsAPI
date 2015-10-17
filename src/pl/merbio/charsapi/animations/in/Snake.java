package pl.merbio.charsapi.animations.in;

import pl.merbio.charsapi.animations.InputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsBlock;

/**
 * @author Merbio
 */

public class Snake extends InputAnimation{
    
    private Point controler, point;
    private int width, height, ordinal;
    private State state;
    
    public Snake() {
        super(1L);
    }

    @Override
    protected void onPrepare() {
        width = cs.getWidth();
        height = cs.getHeight();
        state = State.RIGHT;
        ordinal = state.ordinal();
        
        controler = new Point(0, 0, null);
        point = new Point(controler.w, controler.h, null);
    }

    @Override
    protected void onCancel() {
    
    }

    @Override
    public void run() {
        if(width == 0 || height == 0){
            stopTask();
            return;
        }
        
        if(ordinal % 2 == 0){           
            controler.w += state.aW;
            point.w += state.aW;      
            
            if(controler.w == -1 || controler.w == width){
                if(controler.w == -1){
                    point.w++;
                }
                if(controler.w == width){
                    controler.w--;
                    point.w--;
                }
                height--;
                appState();
            }
        }
        else {            
            controler.h += state.aH;
            point.h += state.aH;
            
            if(controler.h == -1 || controler.h == height){
                if(controler.h == -1){
                    point.h++;
                }
                if(controler.h == height){
                    controler.h--;
                    point.h--;
                }
                width--;
                appState();
            }
        }
        
        build();
    }
    
    private void appState(){
        ordinal++;
        if(ordinal == State.values().length){
            ordinal = 0;
        }
        state = State.values()[ordinal];
    }
    
    private void build(){       
        CharsBlock cb = cs.getCharsBlock(point.w, point.h);
        
        if(cb == null){
// ========================= Tutaj nw czy to moze nie lagowac
            run();
            return;
        }

        setBlocksInPoint(new Point(point.w, point.h, cb));
    }
    
    private enum State{       
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0),
        UP(0, -1);
        
        public int aW, aH;
        
        private State(int aW, int aH){
            this.aW = aW;
            this.aH = aH;
        }
    }
}
