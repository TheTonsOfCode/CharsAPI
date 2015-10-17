package pl.merbio.charsapi.animations.out;

import java.util.ArrayList;
import pl.merbio.charsapi.animations.OutputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsFallingBlockContainer;
import pl.merbio.charsapi.objects.CharsBlock;

/**
 * @author Merbio
 */
public class DropAll extends OutputAnimation {

    private CharsFallingBlockContainer cfbc;
    private ArrayList<Point> list;

    public DropAll() {
        super(10L);
    }

    @Override
    protected void onPrepare() {
        list = new ArrayList<>();
        cfbc = new CharsFallingBlockContainer();

        for (int h = 0; h < cs.getHeight(); h++) {
            for (int w = 0; w < cs.getWidth(); w++) {
                CharsBlock cb = cs.getCharsBlock(w, h);
                if (cb != null) {
                    list.add(new Point(w, h, cb));
                }
            }
        }
    }

    @Override
    protected void onCancel() {
        cfbc.clearBlocks();
    }

    @Override
    public void run() {
        for (Point p : list) {
            if (csbb.front[p.w][p.h] != null) {
                dropFallingBlock(cfbc, p);
            }
        }

        stopTask();
    }

}
