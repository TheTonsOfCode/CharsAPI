package pl.merbio.charsapi.animations.in;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import pl.merbio.charsapi.animations.InputAnimation;
import pl.merbio.charsapi.animations.Point;
import pl.merbio.charsapi.objects.CharsBlock;

/**
 * @author Merbio
 */
public class ByColors extends InputAnimation {

    private HashMap<Byte, ArrayList<Point>> datas;
    private ArrayList<Byte> bytes;

    public ByColors() {
        super(20L);
    }

    @Override
    protected void onPrepare() {
        datas = new HashMap<>();
        bytes = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            datas.put((byte) i, new ArrayList<Point>());
        }

        for (int h = 0; h < cs.getHeight(); h++) {
            for (int w = 0; w < cs.getWidth(); w++) {
                CharsBlock cb = cs.getCharsBlock(w, h);

                if (cb != null) {
                    byte b = cb.getData();
                    if (!bytes.contains(b)) {
                        bytes.add(b);
                    }
                    datas.get(b).add(new Point(w, h, cb));
                }
            }
        }
    }

    @Override
    protected void onCancel() {

    }

    @Override
    public void run() {
        if (bytes.isEmpty()) {
            stopTask();
            if (hasOverline) {
                for (int h = 0; h < cs.getHeight(); h++) {
                    for (int w = 0; w < cs.getWidth(); w++) {
                        CharsBlock cb = cs.getCharsBlock(w, h);

                        if (cb != null) {
                            Block b = csl.over[w][h].getBlock();
                            b.setType(overMaterial);
                            b.setData(overData);
                        }
                    }
                }
            }
            return;
        }

        int index = bytes.size() - 1;
        Byte b = bytes.get(index);
        bytes.remove(index);

        for (Point p : datas.get(b)) {
            CharsBlock cb = cs.getCharsBlock(p.w, p.h);

            if (cb != null) {
                Block block = csl.front[p.w][p.h].getBlock();
                block.setType(cb.getType());
                block.setData(b);
            }
        }
    }

}
