package pl.merbio.charsapi.animations.in;

import pl.merbio.charsapi.animations.InputAnimation;

public class HangingsWidth extends InputAnimation{
    
    int w, loop;

    public HangingsWidth() {
        super(4L);
    }

    @Override
    protected void onPrepare() {
        w = 0;
        loop = 0;
    }

    @Override
    protected void onCancel() {
    
    }

    @Override
    public void run() {
        if(w == cs.getWidth()){
            w = 0;
            loop++;
            if(loop == 2){
                stopTask();
            }
        }
        
        if(loop == 1){
            drawInLine(true);
        }
        else {
            drawInLine(false);
        }
        
        w++;        
    }
    
    private void drawInLine(boolean parseT){
        for(int h = 0; h < cs.getHeight();h++){
            if(parseT){
                if(w % 2 == 0)
                continue;
            }
            else {
                if(w % 2 != 0)
                continue;
            }
            
            setBlocks(w, h);
        }
    }    
}
