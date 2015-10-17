package pl.merbio.charsapi.animations.out;

import java.util.ArrayList;
import pl.merbio.charsapi.animations.OutputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsBlock;
import pl.merbio.charsapi.objects.CharsFallingBlockContainer;

/**
 * @author Merbio
 */

public class DropColumns extends OutputAnimation {

    private CharsFallingBlockContainer cfbc;
    private ArrayList<ArrayList<Point>> list;
    private int iterator;

    public DropColumns() {
        super(10L);
    }

    @Override
    protected void onPrepare() {
        this.list = new ArrayList<>();
        cfbc = new CharsFallingBlockContainer();

        for (int w = 0; w < cs.getWidth(); w++) {
            ArrayList<Point> list = new ArrayList<>();
            for (int h = 0; h < cs.getHeight(); h++) {
                CharsBlock cb = cs.getCharsBlock(w, h);
                if (cb != null) {
                    list.add(new Point(w, h, cb));
                }
            }
            
            if (!list.isEmpty()) {
                this.list.add(list);
            }
        }

        iterator = 0;
    }

    @Override
    protected void onCancel() {
        cfbc.clearBlocks();
    }

    @Override
    public void run() {
        if (iterator >= list.size()) {
            stopTask();
            return;
        }

        for (Point p : list.get(iterator)) {
            if (csbb.front[p.w][p.h] != null) {
                dropFallingBlock(cfbc, p);
            }
        }

        iterator++;
    }

}
