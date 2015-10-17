package pl.merbio.charsapi.animations.in;

import pl.merbio.charsapi.animations.InputAnimation;

public class Matrix_Minecraft extends InputAnimation{
    
    String randomLetters = "!@#$%^&*()-_=+{}:<>?/.,;][\\|'\"";

    public Matrix_Minecraft() {
        super(6L);
    }

    @Override
    protected void onPrepare() {
    
    }

    @Override
    protected void onCancel() {
    
    }

    @Override
    public void run() {
        //tutaj to fsdfsdfs zmieniajace sie jak w minecrafcie tylko nie all na raz 
        //zrobic obiczenia ze jak np. co 1000 blokow dluzej trwa, lub nastepne 
        //zapetlenia schedulerami
    }
    
}