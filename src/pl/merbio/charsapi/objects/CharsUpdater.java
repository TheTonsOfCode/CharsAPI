package pl.merbio.charsapi.objects;

import java.awt.Font;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import pl.merbio.Main;

public class CharsUpdater {

    private ArrayList<CharsUpdaterString> chars = new ArrayList<>();
    private BlockFace bf;
    private Location location;
    private int seconds;

    private int actualChars = -1;
    private Integer task;

    private int auto_inc = 0;

    public CharsUpdater(int seconds, Location location, BlockFace bf) {
        this.seconds = seconds;
        this.location = location;
        this.bf = bf;
    }

    public void start() {
        if (task != null) {
            return;
        }

        taskStringCancel();
    }

    private void taskStringCancel() {
        CharsUpdaterString lcs = null;
        long wait = 20;

        if (actualChars != -1) {
            lcs = chars.get(actualChars);
            boolean b = false;
            if (lcs.cs.hasInputAnimation()) {
                b = !lcs.cs.getInputAnimation().isRunning();
            }
            lcs.cs.clearChars(b);

            if (lcs.cs.hasOutputAnimation()) {
                wait = lcs.cs.getOutputAnimation().getWaitingTime();
            }
        }

        task = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                taskStringBuild();
            }
        }, wait);
    }

    private void taskStringBuild() {
        actualChars++;
        if (actualChars >= chars.size()) {
            actualChars = 0;
        }

        CharsUpdaterString lcs = chars.get(actualChars);

        if (lcs == null) {
            actualChars = 0;
            return;
        }

        CharsBuilder cb = Main.getMainBuilder();
        CharsString cs = lcs.cs;
        if(cs.hasVarsInText()) {
            lcs.cs = cb.replace(bf, cs.getString());
            chars.set(actualChars, lcs);
            cb.build(location, lcs.cs);
        } else {
            cb.build(location, bf, cs);
        }

        task = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                taskStringCancel();
            }
        }, 20L * seconds);
    }

    public void cancel() {
        if (task == null) {
            return;
        }

        Bukkit.getScheduler().cancelTask(task);
        task = null;
        chars.get(actualChars).cs.clearChars(false);
        actualChars = 0;
    }

    public boolean removeCharsString(int id) {
        for (int i = 0; i < chars.size(); i++) {
            CharsUpdaterString lcs = chars.get(i);
            if (id == lcs.id) {
                chars.remove(lcs);
                return true;
            }
        }

        return false;
    }

    public Integer addCharsString(CharsBuilder cb, String s) {
        int id = auto_inc;
        auto_inc++;

        CharsUpdaterString lcs = new CharsUpdaterString();
        lcs.id = id;
        lcs.font = cb.getBlockSettings().getFont();
        lcs.cs = cb.replace(s);
        lcs.cs.setLocation(location);
        chars.add(lcs);

        return id;
    }

    public Integer addCharsString(Font font, String string) {
        int id = auto_inc;
        auto_inc++;

        CharsUpdaterString lcs = new CharsUpdaterString();
        CharsBuilder cb = Main.getMainBuilder();
        BlockSettings bs = cb.getBlockSettings();
        Font before = bs.getFont();
        bs.setFont(font);
        cb.setBlockSettings(bs);

        lcs.id = id;
        lcs.font = font;
        lcs.cs = cb.replace(string);
        lcs.cs.setLocation(location);

        bs.setFont(before);
        cb.setBlockSettings(bs);
        chars.add(lcs);

        return id;
    }

    public String[] getLinesInfo() {
        String[] lines = new String[chars.size()];

        for (int i = 0; i < chars.size(); i++) {
            CharsUpdaterString lcs = chars.get(i);
            String font = lcs.font == null ? "DEFAULT" : lcs.font.getFamily();
            lines[i] = "&1" + lcs.id + " &7- &a" + lcs.cs.getString() + "&7(&e" + font + "&7)";
        }

        return lines;
    }

    public ArrayList<CharsUpdaterString> getLines() {
        return chars;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public Location getLocation() {
        return this.location;
    }

    public BlockFace getFacing() {
        return this.bf;
    }

    public int getLastAutoID() {
        return this.auto_inc;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setFacing(BlockFace bf) {
        this.bf = bf;
    }

    public void setLastAutoID(int auto_inc) {
        this.auto_inc = auto_inc;
    }

    public boolean isRunning() {
        return task != null;
    }
}

//        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
//            @Override
//            public void run() {
//                CharsUpdaterString lcs;
//
//                if (actualChars != -1) {
//                    lcs = chars.get(actualChars);
//                    if (lcs.cs.getInputAnimation().isRunning()) {
//                        lcs.cs.clearChars(false);
//                    } else {
//                        lcs.cs.clearChars(true);
//                    }
//                }
//
//                actualChars++;
//                if (actualChars >= chars.size()) {
//                    actualChars = 0;
//                }
//
//                lcs = chars.get(actualChars);
//                if (lcs == null) {
//                    actualChars = 0;
//                    return;
//                }
//
//                CharsBuilder cb = Main.getMainBuilder();
//                CharsString cs = lcs.cs;
//                cb.build(location, bf, cs);
//            }
//        }, 0L, 20L * seconds);
