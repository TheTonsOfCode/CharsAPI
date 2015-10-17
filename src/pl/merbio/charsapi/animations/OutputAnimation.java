package pl.merbio.charsapi.animations;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import pl.merbio.Main;
import pl.merbio.charsapi.objects.CharsFallingBlockContainer;
import pl.merbio.charsapi.objects.CharsMaterial;
import pl.merbio.charsapi.objects.CharsString;
import pl.merbio.charsapi.objects.CharsStringBeforeBlocks;

/**
 * @author Merbio
 */
public abstract class OutputAnimation implements Runnable {

    protected abstract void onPrepare();

    protected abstract void onCancel();

    private long time, waitingTime;
    private Integer task;

    protected CharsString cs;
    protected CharsStringBeforeBlocks csbb;

    public OutputAnimation(long time) {
        this.time = time;
        this.waitingTime = 20L;
    }

    public void execute(CharsString cs) {
        if (task != null) {
            return;
        }

        this.cs = cs;
        this.csbb = cs.buildCharsStringBeforeBlocks();

        onPrepare();

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), this, 0, time);
    }

    public void cancel() {
        onCancel();
        stopTask();

        if (isRunning()) {
            setBeforeBlocks();
        }

        this.cs = null;
        this.csbb = null;
    }

    public long getWaitingTime() {
        return this.waitingTime;
    }

    protected void stopTask() {
        if (task == null) {
            return;
        }

        Bukkit.getScheduler().cancelTask(task);
        task = null;
    }

    public boolean isRunning() {
        return task != null;
    }

    protected void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    private void setBeforeBlocks() {
        for (int w = 0; w < cs.getWidth(); w++) {
            for (int h = 0; h < cs.getHeight(); h++) {
                if (cs.getCharsBlock(w, h) != null) {
                    setBeforeBlock(w, h);
                }
            }
        }
    }

    protected void setBeforeBlock(int w, int h) {
        CharsMaterial cm = csbb.front[w][h];
        Block b = cm.getLocation().getBlock();
        b.setType(cm.getMaterial());
        b.setData(cm.getData());

        cm = csbb.over[w][h];
        b = cm.getLocation().getBlock();
        b.setType(cm.getMaterial());
        b.setData(cm.getData());
    }

    protected void dropFallingBlock(CharsFallingBlockContainer cfbc, Point point) {
        dropFallingBlock(cfbc, point.w, point.h);
    }

    protected void dropFallingBlock(CharsFallingBlockContainer cfbc, int w, int h) {
        cfbc.dropFallingBlockFormBlock(csbb.front[w][h].getLocation());
        cfbc.dropFallingBlockFormBlock(csbb.over[w][h].getLocation());
        setBeforeBlock(w, h);
    }

    protected boolean cmNotNull(boolean layer, int w, int h) {
        CharsMaterial cm = layer ? csbb.front[w][h] : csbb.over[w][h];

        if (cm == null) {
            return false;
        } else if (cm.getLocation().getBlock().getType() == Material.AIR) {
            return false;
        }
        
        return true;
    }
}
