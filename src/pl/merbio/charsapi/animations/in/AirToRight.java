package pl.merbio.charsapi.animations.in;

import pl.merbio.charsapi.animations.InputAnimation;

public class AirToRight extends InputAnimation {

    int iterator, max, w, h;
    public AirToRight() {
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

        if(w == cs.getWidth()){
            w = 0;
            h++;
        }
        
        setBlocks(w, h);

        w++;
        iterator++;
    } 
}
