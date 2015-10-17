package pl.merbio.charsapi.animations;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import pl.merbio.Main;
import pl.merbio.charsapi.objects.CharsFallingBlockContainer;
import pl.merbio.charsapi.objects.CharsBlock;
import pl.merbio.charsapi.objects.CharsMaterial;
import pl.merbio.charsapi.objects.CharsString;
import pl.merbio.charsapi.objects.CharsStringLocations;

public abstract class InputAnimation implements Runnable {

    protected abstract void onPrepare();

    protected abstract void onCancel();

    private long time;
    private Integer task;

    protected CharsStringLocations csl;
    protected CharsString cs;
    protected boolean hasOverline;
    protected Material overMaterial = Material.STAINED_CLAY;
    protected byte overData = 15;

    public InputAnimation(long time) {
        this.time = time;
    }

    public void execute(CharsStringLocations csl, CharsString cs) {
        if (task != null) {
            return;
        }

        this.csl = csl;
        this.cs = cs;

        this.hasOverline = cs.hasOverline();
        if (hasOverline) {
            CharsMaterial cm = cs.getOverlineMaterial();
            this.overMaterial = cm.getMaterial();
            this.overData = cm.getData();
        }

        onPrepare();

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), this, 0, time);
    }

    public void cancel() {
        onCancel();
        stopTask();

        this.csl = null;
        this.cs = null;
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

    protected void setBlocks(int w, int h) {
        CharsBlock cb = cs.getCharsBlock(w, h);

        if (cb != null) {
            Block b1 = csl.front[w][h].getBlock();
            Block b2 = csl.over[w][h].getBlock();

            b1.setType(cb.getType());
            b1.setData(cb.getData());

            if (hasOverline) {
                b2.setType(overMaterial);
                b2.setData(overData);
            }
        }
    }

    protected void setBlocksInPoint(Point p) {
        Block b1 = csl.front[p.w][p.h].getBlock();
        Block b2 = csl.over[p.w][p.h].getBlock();

        b1.setType(p.cb.getType());
        b1.setData(p.cb.getData());

        if (hasOverline) {
            b2.setType(overMaterial);
            b2.setData(overData);
        }
    }

    protected void spawnFallingBlock(CharsFallingBlockContainer cfbc, Point p) {
        Location l = csl.front[p.w][p.h];
        cfbc.spawnFallingBlock(l.clone().add(0, 20, 0), l, new CharsMaterial(p.cb.getType(), p.cb.getData()));
        if (hasOverline) {
            l = csl.over[p.w][p.h];
            cfbc.spawnFallingBlock(l.clone().add(0, 20, 0), l, new CharsMaterial(overMaterial, overData));
        }
    }
}
