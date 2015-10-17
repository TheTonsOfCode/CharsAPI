package pl.merbio.charsapi.animations.in;

import pl.merbio.charsapi.animations.InputAnimation;

public class HangingsClose extends InputAnimation {

    int c1, c2;
    int max_columns;

    public HangingsClose() {
        super(2L);
    }

    @Override
    protected void onPrepare() {
        max_columns = cs.getWidth() / 2;
        c1 = 0;
        c2 = cs.getWidth() - 1;
    }

    @Override
    protected void onCancel() {

    }

    @Override
    public void run() {
        if (c1 == max_columns + 1) {
            stopTask();
            return;
        }

        drawInLine(c1);
        drawInLine(c2);

        c1++;
        c2--;
    }

    private void drawInLine(int col) {
        for (int h = 0; h < cs.getHeight(); h++) {
            setBlocks(col, h);
        }
    }

}
