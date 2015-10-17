package pl.merbio.charsapi.animations.in;

import pl.merbio.charsapi.animations.InputAnimation;

public class HangingsHeight extends InputAnimation{
    
    int h, loop;

    public HangingsHeight() {
        super(4L);
    }

    @Override
    protected void onPrepare() {
        h = 0;
        loop = 0;
    }

    @Override
    protected void onCancel() {
    
    }

    @Override
    public void run() {
        if(h == cs.getHeight()){
            h = 0;
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
        
        h++;        
    }
    
    private void drawInLine(boolean parseT){
        for(int w = 0; w < cs.getWidth() ;w++){
            if(parseT){
                if(h % 2 == 0)
                continue;
            }
            else {
                if(h % 2 != 0)
                continue;
            }
            
            setBlocks(w, h);
        }
    }    
}
