package pl.merbio.charsapi.objects;

import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import pl.merbio.charsapi.animations.CharsInputAnimation;
import pl.merbio.charsapi.animations.CharsOutputAnimation;
import pl.merbio.charsapi.objects.letters.InputAnimationDecoder;
import pl.merbio.charsapi.objects.letters.ColorDecoder;
import pl.merbio.charsapi.objects.letters.ColoredBlockDecoder;
import pl.merbio.charsapi.objects.letters.DecodeLetter;
import pl.merbio.charsapi.objects.letters.OutputAnimationDecoder;
import pl.merbio.charsapi.objects.letters.OverlineDecoder;

public class CharsBuilder {
    
    private static ArrayList<CharsVariable> VARIABLES = new ArrayList<>();
    
    public static void addCharsVariables(CharsVariable... vars) {
        for (CharsVariable variable : vars) {
            VARIABLES.add(variable);
        }
    }
    
    public static ArrayList<CharsVariable> getVariables() {
        return new ArrayList<>(VARIABLES);
    }
    
    private BlockSettings settings;
    private Material material;
    private Byte data;
    private CharsInputAnimation inputAnimation;
    private CharsOutputAnimation outputAnimation;
    private CharsMaterial overlineMaterial;
    
    public CharsBuilder() {
        this.settings = new BlockSettings();
    }
    
    public CharsBuilder(BlockSettings settings) {
        this.settings = settings;
    }
    
    public BlockSettings getBlockSettings() {
        return this.settings;
    }
    
    public void setBlockSettings(BlockSettings settings) {
        this.settings = settings;
    }
    
    private CharsBlock[][] fontStringByLetter(String letter) {
        BufferedImage im = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        FontMetrics fm = g.getFontMetrics(settings.getFont());
        
        int width = fm.stringWidth(letter);
        int height = fm.getHeight();
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        
        g2.setFont(settings.getFont());
        g2.drawString(letter, 0, height - fm.getDescent());
        
        CharsBlock[][] cb = new CharsBlock[width][height];
        
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                if (!isAlpha(image, w, h)) {
                    cb[w][h] = new CharsBlock(w, h, null, material, data);
                }
            }
        }
        
        return cb;
    }
    
    private CharsBlock[][] approveList(CharsBlock[][] oldList, CharsBlock[][] appList) {
        CharsBlock[][] newList = new CharsBlock[oldList.length + appList.length][oldList[0].length];
        for (int w = 0; w < oldList.length; w++) {
            for (int h = 0; h < oldList[0].length; h++) {
                newList[w][h] = oldList[w][h];
            }
        }
        for (int w = 0; w < appList.length; w++) {
            for (int h = 0; h < appList[0].length; h++) {
                newList[w + oldList.length][h] = appList[w][h];
            }
        }
        return newList;
    }
    
    private CharsBlock[][] defaultStringLetter(String letter) {
        String letterCode = settings.getLetterCode(letter.toUpperCase());
        
        if (letterCode == null) {
            return null;
        }
        
        String[] tokens1 = letterCode.split("/");
        
        CharsBlock[][] cb = new CharsBlock[tokens1.length][9];
        
        int w = 0;
        for (String s : tokens1) {
            String[] tokens2 = s.split(",");
            for (String sc : tokens2) {
                Integer i = Integer.parseInt(sc);
                cb[w][i] = new CharsBlock(w, i, null, material, data);
            }
            w++;
        }
        
        return cb;
    }
    
    private String getCharAt(int index, String string) {
        Character c = string.charAt(index);
        return c.toString();
    }
    
    public void setBuilderOverlineMaterial(CharsMaterial overlineMaterial) {
        this.overlineMaterial = overlineMaterial;
    }
    
    public void setBuilderMaterial(Material material) {
        this.material = material;
    }
    
    public void setBuilderDataBlock(Byte data) {
        this.data = data;
    }
    
    public Byte getBuilderDataBlock() {
        return this.data;
    }
    
    public void setBuilderAnimationIN(CharsInputAnimation inputAnimation) {
        this.inputAnimation = inputAnimation;
    }
    
    public void setBuilderAnimationOUT(CharsOutputAnimation outputAnimation) {
        this.outputAnimation = outputAnimation;
    }
    
    public CharsString replace(BlockFace facing, String toBuild) {
        material = Material.WOOL;
        overlineMaterial = null;
        data = 0;
        inputAnimation = null;
        outputAnimation = null;
        
        String[] subs = null;
        String realToBuild = toBuild;
        
        char varChar = settings.getVarChar();
        int varsInText = 0;
        if (toBuild.contains("" + varChar)) {
            for (CharsVariable variable : VARIABLES) {
                for (String name : variable.getVarNames()) {
                    String s = varChar + name + varChar;
                    if (toBuild.contains(s)) {
                        varsInText++;
                        toBuild = toBuild.replaceAll(s, variable.getResult());
                    }
                }
            }
        }
        
        if (toBuild.contains("\\n")) {
            subs = toBuild.split("\\\\n");
            toBuild = subs[0];
        }
        
        CharsBlock[][] blocks = new CharsBlock[1][1];
        boolean first = true;
        
        ArrayList<DecodeLetter> decoders = new ArrayList<>();
        decoders.add(new ColorDecoder());
        decoders.add(new ColoredBlockDecoder());
        decoders.add(new OverlineDecoder());
        decoders.add(new InputAnimationDecoder());
        decoders.add(new OutputAnimationDecoder());
        
        for (int i = 0; i < toBuild.length(); i++) {
            boolean reps = true, rr;
            while (reps) {
                rr = false;
                for (DecodeLetter dl : decoders) {
                    if (dl.decode(i, toBuild, settings, this)) {
                        i += 2;
                        rr = true;
                    }
                }
                reps = rr;
            }
            
            String character = getCharAt(i, toBuild);
            
            if (settings.getFont() != null) {
                blocks = first ? fontStringByLetter(character)
                        : approveList(blocks, fontStringByLetter(character));
            } else {
                //if(!first && character.equalsIgnoreCase(" ")){
                if (character.equalsIgnoreCase(" ")) {
                    blocks = approveList(blocks, new CharsBlock[settings.getWordSpacing()][1]);
                    continue;
                }
                
                CharsBlock[][] app = defaultStringLetter(character);
                if (app == null) {
                    return new CharsString(null, character, null);
                }
                blocks = first ? app : approveList(blocks, app);
                
                if (i != toBuild.length() - 1 && !getCharAt(i + 1, toBuild).equalsIgnoreCase(" ")) {
                    blocks = approveList(blocks, new CharsBlock[settings.getLetterSpacing()][1]);
                }
            }
            
            first = false;
        }
        
        CharsString cs = new CharsString(facing, realToBuild, blocks);
        cs.varsInText = varsInText;
        if (inputAnimation != null) {
            cs.setInputAnimation(inputAnimation);
        }
        if (outputAnimation != null) {
            cs.setOutputAnimation(outputAnimation);
        }
        if (overlineMaterial != null) {
            cs.setOverlineMaterial(overlineMaterial);
//            overlineMaterial = null;
        }
        if (subs != null) {
            for (int i = 1; i < subs.length; i++) {
                cs.addSubCharsString(replace(facing, subs[i]));
            }
        }
        
        return cs;
    }
    
    public CharsString replace(String toBuild) {
        return replace((BlockFace) null, toBuild);
    }
    
    public CharsString replace(Location location, Location facingLocation, String toBuild) {
        return replace(getLocationsFacing(location, facingLocation), toBuild);
    }
    
    public CharsString replace(Player player, String toBuild) {
        return replace(getPlayerFacing(player.getLocation()), toBuild);
    }
    
    public CharsStringLocations getBuildLocations(Location center, BlockFace facing, CharsString cs) {
        CharsStringLocations csl = new CharsStringLocations(cs.getWidth(), cs.getHeight());
        
        csl.begin = beginLoc(center, facing, cs.getWidth() / 2);
        csl.begin.add(0, cs.getHeight() + settings.getPlayerSpacing(), 0);
        center = csl.begin;
        
        for (int w = 0; w < cs.getWidth(); w++) {
            for (int h = 0; h < cs.getHeight(); h++) {
                CharsBlock cb = cs.getCharsBlock(w, h);
                
                if (cb == null) {
                    continue;
                }
                
                Location app = center.clone().add(0, -h, 0);
                app = moveLoc(app, facing, w);
                
                csl.front[w][h] = app;
                csl.over[w][h] = layerLoc(app, facing, 1);
            }
        }
        
        return csl;
    }
    
    public CharsString setBeforeBlocks(CharsStringLocations csl, CharsString cs) {
        for (int w = 0; w < cs.getWidth(); w++) {
            for (int h = 0; h < cs.getHeight(); h++) {
                CharsBlock cb = cs.getCharsBlock(w, h);
                
                if (cb == null) {
                    continue;
                }
                
                Location loc1 = csl.front[w][h], loc2 = csl.over[w][h];
                cs.setBeforeBlock(w, h, loc1, loc2);
            }
        }
        
        return cs;
    }
    
    public CharsString build(Location location, BlockFace facing, CharsString cs) {
        int heightTimesApprove = 0;
        int heightApprove = cs.getHeight();
        
        for (int i = cs.getSubChars().size() - 1; i > -1; i--) {
            build(location.clone().add(0, heightTimesApprove * heightApprove, 0), facing, cs.getSubChars().get(i));
            heightTimesApprove++;
        }
        
        CharsStringLocations csl = getBuildLocations(location.clone().add(0, heightTimesApprove * heightApprove, 0), facing, cs);
        boolean oab = settings.isOnlyAirBlocade();
        
        cs.setLocation(location);
        cs = setBeforeBlocks(csl, cs);
        
        if (cs.hasInputAnimation()) {
            cs.getInputAnimation().execute(csl, cs);
            return cs;
        }
        
        boolean ov = cs.hasOverline();
        
        for (int w = 0; w < cs.getWidth(); w++) {
            for (int h = 0; h < cs.getHeight(); h++) {
                CharsBlock cb = cs.getCharsBlock(w, h);
                
                if (cb == null) {
                    continue;
                }
                
                Block b1 = csl.front[w][h].getBlock(), b2 = csl.over[w][h].getBlock();
                if (canBuild(oab, b1)) {
                    b1.setType(cb.getType());
                    b1.setData(cb.getData());
                }
                
                if (ov && canBuild(oab, b2)) {
                    CharsMaterial cm = cs.getOverlineMaterial();
                    b2.setType(cm.getMaterial());
                    b2.setData(cm.getData());
                }
            }
        }
        
        return cs;
    }
    
    private boolean canBuild(boolean oab, Block b) {
        return (b.getType() == Material.AIR) ? true : oab ? false : true;
    }
    
    public CharsString build(CharsString cs) {
        if (cs.getLocation() == null) {
            return null;
        }
        return build(cs.getLocation(), cs);
    }
    
    public CharsString build(Location location, CharsString cs) {
        BlockFace bf = cs.getFacing() == null ? BlockFace.NORTH : cs.getFacing();
        return build(location, bf, cs);
    }
    
    public CharsString build(Location location, Location facingLocation, CharsString cs) {
        return build(location, getLocationsFacing(location, facingLocation), cs);
    }
    
    public CharsString build(Player player, CharsString cs) {
        Location playerLocation = player.getLocation();
        return build(playerLocation, getPlayerFacing(playerLocation), cs);
    }

    /*
     Build with using replace ...
     */
    public CharsString build(String toBuild) {
        return build(replace(toBuild));
    }
    
    public CharsString build(Location location, BlockFace facing, String toBuild) {
        return build(location, facing, replace(toBuild));
    }
    
    public CharsString build(Location location, Location facingLocation, String toBuild) {
        return build(location, facingLocation, replace(toBuild));
    }
    
    public CharsString build(Player player, String toBuild) {
        return build(player, replace(toBuild));
    }
    
    private Location beginLoc(Location loc, BlockFace bf, int i) {
        if (bf == null) {
            return loc;
        }
        Location l = loc.clone();
        
        if (bf == BlockFace.NORTH) {
            l.add(-i, 0, 0);
        } else if (bf == BlockFace.SOUTH) {
            l.add(i, 0, 0);
        } else if (bf == BlockFace.WEST) {
            l.add(0, 0, i);
        } else {
            l.add(0, 0, -i);
        }
        return l;
    }
    
    private Location moveLoc(Location loc, BlockFace bf, int i) {
        if (bf == null) {
            return loc;
        }
        Location l = loc.clone();
        
        if (bf == BlockFace.NORTH) {
            l.add(i, 0, 0);
        } else if (bf == BlockFace.SOUTH) {
            l.add(-i, 0, 0);
        } else if (bf == BlockFace.WEST) {
            l.add(0, 0, -i);
        } else {
            l.add(0, 0, i);
        }
        return l;
    }
    
    private Location layerLoc(Location loc, BlockFace bf, int i) {
        Location l = loc.clone();
        
        if (bf == BlockFace.NORTH) {
            l.add(0, 0, -i);
        } else if (bf == BlockFace.SOUTH) {
            l.add(0, 0, i);
        } else if (bf == BlockFace.WEST) {
            l.add(-i, 0, 0);
        } else {
            l.add(i, 0, 0);
        }
        
        return l;
    }
    
    public BlockFace getPlayerFacing(Location playerLocation) {
        double yaw = (playerLocation.getYaw() + 22.5D) % 360.0D;
        if (yaw < 0.0D) {
            yaw += 360.0D;
        }
        BlockFace bf = null;
        if ((yaw > 65.0D) && (yaw <= 155.0D)) {
            bf = BlockFace.EAST;
        } else if ((yaw > 155.0D) && (yaw <= 245.0D)) {
            bf = BlockFace.SOUTH;
        } else if ((yaw > 245.0D) && (yaw <= 335.0D)) {
            bf = BlockFace.WEST;
        } else if ((yaw > 335.0D) || (yaw <= 65.0D)) {
            bf = BlockFace.NORTH;
        }
        return bf;
    }
    
    public BlockFace getLocationsFacing(Location location, Location facingLocation) {
        double sftlX = facingLocation.getX();
        double sftlZ = facingLocation.getZ();
        
        double lX = location.getX();
        double lZ = location.getZ();
        
        if (lZ >= sftlZ && sftlX - (lZ - sftlZ) <= lX && sftlX + (lZ - sftlZ) >= lX) {
            return BlockFace.SOUTH;
        } else if (lZ <= sftlZ && sftlX - (sftlZ - lZ) <= lX && sftlX + (sftlZ - lZ) >= lX) {
            return BlockFace.NORTH;
        } else if (lX >= sftlX && sftlZ - (lX - sftlX) <= lZ && sftlZ + (lX - sftlX) >= lZ) {
            return BlockFace.EAST;
        } else {
            return BlockFace.WEST;
        }
    }
    
    private boolean isAlpha(BufferedImage bufImg, int posX, int posY) {
        int alpha = (bufImg.getRGB(posX, posY) >> 24) & 0xFF;
        return alpha == 0;
    }
}
