package pl.merbio.charsapi.objects;

import java.awt.Font;
import java.util.HashMap;
import java.util.Random;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import pl.merbio.charsapi.exception.AleradyReservedCharacterException;
import pl.merbio.charsapi.exception.StaticColorCharacterException;

public class BlockSettings {

    private int space_word;
    private int space_letter;
    private int over_player;
    private HashMap<String, String> letters;
    private char colorChar;
    private char coloredBlockChar;
    private char inputAnimationChar;
    private char outputAnimationChar;
    private char overlineChar;
    private char varChar;
    private HashMap<Character, DyeColor> colors;
    private HashMap<Character, Material> by_colors_materials;
    private boolean onlyAirBlocade;
    private Font font;

    public BlockSettings() {
        this.space_word = 3;
        this.space_letter = 1;
        this.over_player = 2;
        this.letters = Content.letters;
        this.colorChar = '&';
        this.coloredBlockChar = '#';
        this.inputAnimationChar = '@';
        this.outputAnimationChar = '!';
        this.overlineChar = '$';
        this.varChar = '%';
        this.colors = Content.colors;
        this.by_colors_materials = Content.by_colors_materials;
        this.onlyAirBlocade = true;
        this.font = null;
    }

    public BlockSettings setWordSpacing(int space_word) {
        this.space_word = (space_word < 0) ? 1 : space_word;
        return this;
    }

    public BlockSettings setLetterSpacing(int space_letter) {
        this.space_letter = (space_letter < 0) ? 0 : space_letter;
        return this;
    }

    public BlockSettings setPlayerSpacing(int over_player) {
        this.over_player = over_player;
        return this;
    }

    public BlockSettings setColorChar(char colorChar) throws AleradyReservedCharacterException {
        if (isReservedCharacter(colorChar)) {
            throw new AleradyReservedCharacterException(colorChar);
        }

        this.colorChar = colorChar;
        return this;
    }

    public BlockSettings setColoredBlockChar(char setator) throws AleradyReservedCharacterException {
        if (isReservedCharacter(setator)) {
            throw new AleradyReservedCharacterException(setator);
        }

        this.coloredBlockChar = setator;
        return this;
    }

    public BlockSettings setInputAnimationChar(char inputAnimationChar) throws AleradyReservedCharacterException {
        if (isReservedCharacter(inputAnimationChar)) {
            throw new AleradyReservedCharacterException(inputAnimationChar);
        }

        this.inputAnimationChar = inputAnimationChar;
        return this;
    }

    public BlockSettings setOutputAnimationChar(char outputAnimationChar) throws AleradyReservedCharacterException {
        if (isReservedCharacter(outputAnimationChar)) {
            throw new AleradyReservedCharacterException(outputAnimationChar);
        }

        this.outputAnimationChar = outputAnimationChar;
        return this;
    }
    
    public BlockSettings setOverlineChar(char overlineChar) throws AleradyReservedCharacterException {
        if (isReservedCharacter(overlineChar)) {
            throw new AleradyReservedCharacterException(overlineChar);
        }

        this.overlineChar = overlineChar;
        return this;
    }
    
    public BlockSettings setVarChar(char varChar) throws AleradyReservedCharacterException {
        if (isReservedCharacter(varChar)) {
            throw new AleradyReservedCharacterException(varChar);
        }

        this.varChar = varChar;
        return this;
    }

    public BlockSettings addMaterialReplacement(char replacement, Material material) throws StaticColorCharacterException {
        if (isStaticColorCharacter(replacement)) {
            throw new StaticColorCharacterException(replacement);
        }

        this.by_colors_materials.put(replacement, material);
        return this;
    }

    public BlockSettings setMaterialReplacementList(HashMap<Character, Material> by_colors_materials) throws StaticColorCharacterException {
        for (Character c : by_colors_materials.keySet()) {
            if (isStaticColorCharacter(c)) {
                throw new StaticColorCharacterException(c);
            }
        }

        this.by_colors_materials = by_colors_materials;
        return this;
    }

    public BlockSettings clearMaterialReplacementList() {
        this.by_colors_materials = new HashMap<>();
        return this;
    }

    public BlockSettings setOnlyAirBlocade(boolean onlyAirBlocade) {
        this.onlyAirBlocade = onlyAirBlocade;
        return this;
    }

    public BlockSettings setFont(Font font) {
        this.font = font;
        return this;
    }

    public int getWordSpacing() {
        return space_word;
    }

    public int getLetterSpacing() {
        return space_letter;
    }

    public int getPlayerSpacing() {
        return over_player;
    }

    public char getColorChar() {
        return colorChar;
    }

    public char getColoredBlockChar() {
        return coloredBlockChar;
    }

    public char getInputAnimationChar() {
        return inputAnimationChar;
    }

    public char getOutputAnimationChar() {
        return outputAnimationChar;
    }

    public char getOverlineChar() {
        return overlineChar;
    }
    
    public char getVarChar() {
        return varChar;
    }

    public HashMap<Character, Material> getMaterialReplacementList() {
        return by_colors_materials;
    }

    public String getLetterCode(String letter) {
        return letters.get(letter);
    }

    public DyeColor getDyeColor(Character c) {
        if(c.toString().equalsIgnoreCase("x")){
            return (DyeColor) colors.values().toArray()[new Random().nextInt(colors.size())];
        }
        
        for (Character cK : colors.keySet()) {
            if (cK.toString().equalsIgnoreCase(c.toString())) {
                return colors.get(cK);
            }
        }
        return null;
    }

    public Material getMaterial(Character c) {
        for (Character cK : by_colors_materials.keySet()) {
            if (cK.toString().equalsIgnoreCase(c.toString())) {
                return by_colors_materials.get(cK);
            }
        }
        return null;
    }

    public boolean isOnlyAirBlocade() {
        return onlyAirBlocade;
    }

    public Font getFont() {
        return font;
    }

    public boolean isStaticColorCharacter(Character c) {
        if (getDyeColor(c) != null) {
            return true;
        }
        
        if(c.toString().equalsIgnoreCase("x")) {
            return true;
        }

//        if (c.toString().equalsIgnoreCase(String.valueOf(COLOR_OVERLINE_CHARACTER))) {
//            return true;
//        }

        return false;
    }

    public boolean isReservedCharacter(Character c) {
        if (isResrvC(c, colorChar)) {
            return true;
        }

        if (isResrvC(c, coloredBlockChar)) {
            return true;
        }

        if (isResrvC(c, inputAnimationChar)) {
            return true;
        }

        if (isResrvC(c, outputAnimationChar)) {
            return true;
        }
        
        if (isResrvC(c, overlineChar)) {
            return true;
        }
        
        if (isResrvC(c, varChar)) {
            return true;
        }

        return false;
    }

    private boolean isResrvC(Character c, Character inPlugin) {
        return c.toString().equalsIgnoreCase(inPlugin.toString());
    }
}
