package pl.merbio.utilities.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * @author Merbio
 */
public class SchemeStruct {

    private String[][] scheme;
    private List<SchemeRecipment> recips = new ArrayList<>();

    private String AIR_MATERIAL_RECIP = "X";
    private final Material AIR_MATERIAL = Material.AIR;
    private boolean buildAir;

    private final Material DEFAULT_MATERIAL = Material.SPONGE;
    private final Byte DEFAULT_MATERIAL_DATA = 0;

    private final String START_LOCATION_RECIP = "~";

    private List<B> undoList = new ArrayList<>();
    private boolean canUndo;

    public SchemeStruct(String[][] scheme, SchemeRecipment[] recips) {
        this.scheme = scheme;
        this.recips = Arrays.asList(recips);
        this.buildAir = true;
        this.canUndo = true;
    }

    public SchemeStruct(String airRecip, String[][] scheme, SchemeRecipment[] recips) {
        this(scheme, recips);
        this.AIR_MATERIAL_RECIP = airRecip;
    }

    public SchemeStruct(boolean buildAir, String[][] scheme, SchemeRecipment[] recips) {
        this(scheme, recips);
        this.buildAir = buildAir;
    }

    public SchemeStruct(boolean buildAir, String airRecip, String[][] scheme, SchemeRecipment[] recips) {
        this(scheme, recips);
        this.buildAir = buildAir;
        this.AIR_MATERIAL_RECIP = airRecip;
    }

    public void setBuildAir(boolean buildAir) {
        this.buildAir = buildAir;
    }

    public void build(Location location) {
        int[] stPos = searchLocsPos();

        for (int y = 0; y < scheme.length; y++) {
            Level lev = new Level(y);

            for (int z = 0; z < lev.maxZ; z++) {
                String sub = lev.lvl[z];

                for (int x = 0; x < sub.length(); x++) {
                    Character c = sub.charAt(x);
                    B b = getMaterial(c.toString());

                    if (b.material == AIR_MATERIAL && !buildAir) {
                        continue;
                    }
                    Location v = location.clone().add(-stPos[0] + z, y, -stPos[1] + x);

                    if (canUndo) {
                        B a = b.clone();
                        a.location = v.clone();
                        Block l = v.getBlock();
                        a.material = l.getType();
                        a.data = l.getData();
                        undoList.add(a);
                    }

                    //Tu moze na odwrot x z z
                    Block l = v.getBlock();
                    l.setType(b.material);
                    l.setData(b.data);
                }
            }
        }
    }

    public void undo() {
        if (!canUndo) {
            return;
        }

        for (int i = undoList.size() - 1; i > -1; i--) {
            B bb = undoList.get(i);
            Block b = bb.location.getBlock();
            b.setType(bb.material);
            b.setData(bb.data);
        }

        undoList.clear();
    }

    public B getMaterial(String rec) {
        B b = new B();
        b.material = DEFAULT_MATERIAL;
        b.data = DEFAULT_MATERIAL_DATA;
        if (START_LOCATION_RECIP.equalsIgnoreCase(rec)) {
            b.material = Material.CARPET;
            b.data = 14;
        } else if (AIR_MATERIAL_RECIP.equalsIgnoreCase(rec)) {
            b.material = AIR_MATERIAL;
        } else {
            for (SchemeRecipment sr : recips) {
                if (sr.getRecip().equalsIgnoreCase(rec)) {
                    b.material = sr.getMaterial();
                    b.data = sr.getData();
                    break;
                }
            }
        }

        return b;
    }

    private class B {

        public Material material;
        public Byte data;
        public Location location;

        public B clone() {
            B b = new B();
            b.material = material;
            b.data = data;
            b.location = location;
            return b;
        }
    }

    public int[] searchLocsPos() {
        int[] pos = new int[2];

        Level lev = new Level(0);

        for (int z = 0; z < lev.maxZ; z++) {
            String sub = lev.lvl[z];

            for (int x = 0; x < sub.length(); x++) {
                if (equal(START_LOCATION_RECIP, sub, x)) {
                    pos[0] = z;
                    pos[1] = x;
                    return pos;
                }
            }
        }

        pos[0] = 0;
        pos[1] = 0;
        return pos;
    }

    private boolean equal(String toEqual, String sub, int at) {
        Character c = sub.charAt(at);
        return c.toString().equalsIgnoreCase(toEqual);
    }

    public class Level {

        public String[] lvl;
        int maxZ;
        int maxX = 0;

        public Level(int level) {
            lvl = scheme[level];
            maxZ = lvl.length;
            for (int x = 0; x < maxZ; x++) {
                String sub = lvl[x];
                if (sub.length() > maxX) {
                    maxX = sub.length();
                }
            }
        }
    }
}
