package pl.merbio.charsapi.objects;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import pl.merbio.charsapi.animations.CharsInputAnimation;
import pl.merbio.charsapi.animations.InputAnimation;
import pl.merbio.charsapi.animations.CharsOutputAnimation;
import pl.merbio.charsapi.animations.OutputAnimation;
import pl.merbio.charsapi.other.Message;

public class CharsString {

    private BlockFace facing;
    private String string;
    private CharsBlock[][] blocks;
    private Location location;
    private InputAnimation inputAnimation;
    private OutputAnimation outputAnimation;
    private ArrayList<CharsString> subChars;
    private CharsMaterial overlineMaterial;

    public CharsString(BlockFace facing, String string, CharsBlock[][] blocks) {
        this.facing = facing;
        this.string = string;
        this.blocks = blocks;
        this.subChars = new ArrayList<>();
    }

    public BlockFace getFacing() {
        return facing;
    }

    public String getString() {
        return string;
    }

    public CharsBlock[][] getBlocks() {
        return blocks;
    }

    public int getWidth() {
        return blocks.length;
    }

    public int getHeight() {
        return blocks[0].length;
    }

    public Location getLocation() {
        return location;
    }

    public CharsBlock getCharsBlock(int x, int y) {
        return blocks[x][y];
    }

    public InputAnimation getInputAnimation() {
        return inputAnimation;
    }

    public OutputAnimation getOutputAnimation() {
        return outputAnimation;
    }

    public ArrayList<CharsString> getSubChars() {
        return this.subChars;
    }

    public CharsMaterial getOverlineMaterial() {
        return overlineMaterial;
    }

    public boolean hasInputAnimation() {
        return inputAnimation != null;
    }

    public boolean hasOutputAnimation() {
        return outputAnimation != null;
    }

    public boolean hasSubCharsStrings() {
        return !subChars.isEmpty();
    }

    public boolean hasOverline() {
        return overlineMaterial != null;
    }

    public void addSubCharsString(CharsString cs) {
        this.subChars.add(cs);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setFacing(BlockFace facing) {
        this.facing = facing;
    }

    public void setInputAnimation(CharsInputAnimation cia) {
        this.inputAnimation = cia.getAnimation();
    }

    public void setOutputAnimation(CharsOutputAnimation coa) {
        this.outputAnimation = coa.getAnimation();
    }

    public void setOverlineMaterial(CharsMaterial overlineMaterial) {
        this.overlineMaterial = overlineMaterial;
    }

    public void setOverlineMaterial(Material material, byte data) {
        this.overlineMaterial = new CharsMaterial(material, data);
    }

    public void setBeforeBlock(int x, int y, Location loc, Location over) {
        String before = loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ();
        Block b = loc.getBlock();
        before += "/" + b.getType() + "/" + b.getData();
        //       blocks[x][y].setBefore(before);

        before += ";";

        before += over.getBlockX() + "/" + over.getBlockY() + "/" + over.getBlockZ();
        b = over.getBlock();
        before += "/" + b.getType() + "/" + b.getData();
        blocks[x][y].setBefore(before);
    }

    public void clearChars(boolean playOut) {
        boolean canOutput = true;

        if (!subChars.isEmpty()) {
            for (CharsString subCs : subChars) {
                subCs.clearChars(playOut);
            }
        }

        if (hasInputAnimation()) {
            if (inputAnimation.isRunning()) {
                canOutput = false;
            }
            inputAnimation.cancel();
        }

        if (hasOutputAnimation()) {
            if (playOut && canOutput) {
                outputAnimation.execute(this);
                return;
            } else {
                /**
                 
                 UWAGA TUTAJ MOZE BYC BLAD - TRZEBA BY SUUNAC IF'A I ZASTAPIC CZYMS INNYM
                 
                 */
                if (outputAnimation.isRunning()) {
                    outputAnimation.cancel();
                }
            }
        }

        Location loc;
        Block b;
        for (int w = 0; w < getWidth(); w++) {
            for (int h = 0; h < getHeight(); h++) {
                CharsBlock cb = blocks[w][h];

                if (cb == null) {
                    continue;
                }

                String ss = cb.getBefore();

                if (ss == null) {
                    continue;
                }

                String[] locs = ss.split(";");
                for (String s : locs) {
                    String[] tokens = s.split("/");

                    loc = new Location(
                            location.getWorld(),
                            Double.parseDouble(tokens[0]),
                            Double.parseDouble(tokens[1]),
                            Double.parseDouble(tokens[2]),
                            0, 0
                    );

                    b = loc.getBlock();
                    b.setType(Material.getMaterial(tokens[3]));
                    b.setData((byte) Byte.parseByte(tokens[4]));
                }
                blocks[w][h].setBefore(null);
            }
        }
    }

    public CharsStringBeforeBlocks buildCharsStringBeforeBlocks() {
        CharsStringBeforeBlocks csbb = new CharsStringBeforeBlocks(getWidth(), getHeight());

        Location loc;
        Block b;
        for (int w = 0; w < getWidth(); w++) {
            for (int h = 0; h < getHeight(); h++) {
                CharsBlock cb = blocks[w][h];

                if (cb == null) {
                    continue;
                }

                String ss = cb.getBefore();

                if (ss == null) {
                    continue;
                }

                String[] locs = ss.split(";");
                csbb.front[w][h] = getBeforeBlockMaterial(locs[0]);
                csbb.over[w][h] = getBeforeBlockMaterial(locs[1]);
            }
        }

        return csbb;
    }

    private CharsMaterial getBeforeBlockMaterial(String beforeBlock) {
        CharsMaterial cm;

        String[] tokens = beforeBlock.split("/");

        return new CharsMaterial(Material.getMaterial(tokens[3]), (byte) Byte.parseByte(tokens[4]),
                new Location(
                        location.getWorld(),
                        Double.parseDouble(tokens[0]),
                        Double.parseDouble(tokens[1]),
                        Double.parseDouble(tokens[2]),
                        0, 0
                ));
    }

    public void flushChars() {
        Location loc;
        Block b;
        for (int w = 0; w < getWidth(); w++) {
            for (int h = 0; h < getHeight(); h++) {
                CharsBlock cb = blocks[w][h];

                if (cb == null) {
                    continue;
                }

                String ss = cb.getBefore();

                if (ss == null) {
                    continue;
                }

                String[] locs = ss.split(";");
                for (String s : locs) {
                    String[] tokens = s.split("/");

                    loc = new Location(
                            location.getWorld(),
                            Double.parseDouble(tokens[0]),
                            Double.parseDouble(tokens[1]),
                            Double.parseDouble(tokens[2]),
                            0, 0
                    );

                    b = loc.getBlock();
                    b.setType(Material.AIR);
                }

                cb.setBefore(null);
            }
        }
    }
}
