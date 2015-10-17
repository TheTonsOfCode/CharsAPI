package pl.merbio.charsapi.animations.in;

import pl.merbio.charsapi.animations.InputAnimation;

public class AirToDown  extends InputAnimation {

    int iterator, max, w, h;
    public AirToDown() {
        super(0);
    }
    
    @Override
    protected void onPrepare() {
        iterator = 1;
        w = 0;
        h = 0;
        max = cs.getWidth() * cs.getHeight();
    }

    @Override
    public void onCancel() {
    
    }

    @Override
    public void run() {
        if(iterator == max){
            stopTask();
        }

        if(h == cs.getHeight()){
            h = 0;
            w++;
        }
        
        setBlocks(w, h);

        h++;
        iterator++;
    } 
}