package pl.merbio.charsapi.animations.in;

import java.util.ArrayList;
import pl.merbio.charsapi.animations.InputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsFallingBlockContainer;
import pl.merbio.charsapi.objects.CharsBlock;

/**
 * @author Merbio
 */
public class FillToCenter extends InputAnimation {
    private int max_columns;

    private ArrayList<Point> list;
    private int iterator, max;
    private CharsFallingBlockContainer cfbc;

    public FillToCenter() {
        super(1L);
    }

    @Override
    protected void onPrepare() {
        max_columns = cs.getWidth() / 2;

        list = new ArrayList<>();

        for (int h = cs.getHeight() - 1; h >= 0; h--) {
            for (int w = 0; w < max_columns + 1; w++) {
                CharsBlock cb = cs.getCharsBlock(w, h);
                Point add = new Point(w, h, cb);
                if (cb != null) {
                    if (!list.contains(add)) {
                        list.add(add);
                    }
                }
                int ww = cs.getWidth() - 1 - w;
                cb = cs.getCharsBlock(ww, h);
                if (cb != null) {
                    add = new Point(ww, h, cb);
                    if (!list.contains(add)) {
                        list.add(add);
                    }
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
        if (iterator == max - 1) {
            stopTask();
        }

        spawnFallingBlock(cfbc, list.get(iterator));

        iterator++;
    }
}
