package pl.merbio.charsapi.objects;

import java.util.HashMap;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class Content {
    
    public static HashMap<String, String> letters = new HashMap<>();
    public static HashMap<Character, DyeColor> colors = new HashMap<>();
    public static HashMap<Character, Material> by_colors_materials = new HashMap<>();
    
    static {
        letters.put("A", "1,2,3,4,5/1,3/1,3/1,2,3,4,5");
        letters.put("B", "1,2,3,4,5/1,3,5/1,3,5/2,4");
        letters.put("C", "1,2,3,4,5/1,5/1,5/1,5");
        letters.put("D", "1,2,3,4,5/1,5/1,5/2,3,4");
        letters.put("E", "1,2,3,4,5/1,3,5/1,3,5/1,5");
        letters.put("F", "1,2,3,4,5/1,3/1,3/1");
        letters.put("G", "1,2,3,4,5/1,5/1,3,5/1,3,4,5");
        letters.put("H", "1,2,3,4,5/3/3/1,2,3,4,5");
        letters.put("I", "1,5/1,2,3,4,5/1,5");
        letters.put("J", "1,4,5/1,5/1,2,3,4,5");
        letters.put("K", "1,2,3,4,5/3/3/1,2,4,5");
        letters.put("L", "1,2,3,4,5/5/5");
        letters.put("M", "1,2,3,4,5/1/1,2,3/1/1,2,3,4,5");
        letters.put("N", "1,2,3,4,5/2/3/1,2,3,4,5");
        letters.put("O", "1,2,3,4,5/1,5/1,5/1,2,3,4,5");
        letters.put("P", "1,2,3,4,5/1,3/1,3/1,2,3");
        letters.put("Q", "1,2,3,4,5/1,5/1,4/1,2,3,5");
        letters.put("R", "1,2,3,4,5/1,3/1,3/1,2,4,5");
        letters.put("S", "1,2,3,5/1,3,5/1,3,5/1,3,4,5");
        letters.put("T", "1/1/1,2,3,4,5/1/1");
        letters.put("U", "1,2,3,4,5/5/5/1,2,3,4,5");
        letters.put("V", "1,2,3/4/5/4/1,2,3");
        letters.put("W", "1,2,3,4,5/5/3,4,5/5/1,2,3,4,5");
        letters.put("X", "1,2,4,5/3/3/1,2,4,5");
        letters.put("Y", "1/2/3,4,5/2/1");
        letters.put("Z", "1,4,5/1,3,5/1,3,5/1,3,5/1,2,5");
        letters.put("0", "1,2,3,4,5/1,5/1,2,3,4,5");
        letters.put("1", "2,5/1,2,3,4,5/5");
        letters.put("2", "1,3,4,5/1,3,5/1,2,3,5");
        letters.put("3", "1,5/1,3,5/1,2,3,4,5");
        letters.put("4", "1,2,3/3/1,2,3,4,5");
        letters.put("5", "1,2,3,5/1,3,5/1,3,4,5");
        letters.put("6", "1,2,3,4,5/1,3,5/1,3,4,5");
        letters.put("7", "1/1/1,2,3,4,5");
        letters.put("8", "1,2,3,4,5/1,3,5,5/1,2,3,4,5");
        letters.put("9", "1,2,3,5/1,3,5/1,2,3,4,5");
        letters.put("?", "1/1,3,5/1,2");
        letters.put("!", "1,2,3,5");
        letters.put(":", "2,4");
        letters.put(";", "5/2,4");
        letters.put("[", "1,2,3,4,5/1,5");
        letters.put("]", "1,5/1,2,3,4,5");
        letters.put("#", "2,4/1,2,3,4,5/2,4/1,2,3,4,5/2,4");
        letters.put("@", "1,2,3,4/0,5/0,2,3,6/0,2,3,6/0,4,6/1,2,3");
        letters.put("$", "2/1,3,5/0,1,2,3,4,5,6/1,3,5/4");
        letters.put("%", "5/1,4/3/2,5/1");
        letters.put("^", "2/1/2");
        letters.put("(", "2,3,4/1,5");
        letters.put(")", "1,5/2,3,4");
        letters.put("-", "3/3/3");
        letters.put("_", "5/5/5");
        letters.put("=", "2,4/2,4/2,4");
        letters.put("+", "3/2,3,4/3");
        letters.put("|", "1,2,4,5");
        letters.put("/", "5/4/3/2/1");
        letters.put("\\", "1/2/3/4/5");
        letters.put("*", "1,3/2/2/1,3");
        letters.put("'", "1,2");
        letters.put(".", "5");
        letters.put(",", "6/5");
        letters.put("{", "3/1,2,4,5/1,5");
        letters.put("}", "1,5/1,2,4,5/3");
        letters.put("<", "3/2,4/1,5");
        letters.put(">", "1,5/2,4/3");
        letters.put("~", "1,2,3,4,5,6,7,8/1,8/1,8/1,4,5,8/1,4,5,8/1,4,5,8/1,4,5,8/1,8/1,8/1,2,3,4,5,6,7,8");

        colors.put('1', DyeColor.BLUE);
        colors.put('2', DyeColor.GREEN);
        colors.put('3', DyeColor.CYAN);
        colors.put('4', DyeColor.RED);
        colors.put('5', DyeColor.PURPLE);
        colors.put('6', DyeColor.ORANGE);
        colors.put('7', DyeColor.SILVER);
        colors.put('8', DyeColor.GRAY);
        colors.put('9', DyeColor.MAGENTA);
        colors.put('0', DyeColor.BLACK);
        colors.put('A', DyeColor.LIME);
        colors.put('B', DyeColor.LIGHT_BLUE);
        colors.put('C', DyeColor.BROWN);
        colors.put('D', DyeColor.PINK);
        colors.put('E', DyeColor.YELLOW);
        colors.put('F', DyeColor.WHITE);
        
        by_colors_materials.put('G', Material.BEDROCK);
        by_colors_materials.put('H', Material.BEACON);
        by_colors_materials.put('I', Material.IRON_BLOCK);
        by_colors_materials.put('J', Material.GOLD_BLOCK);
        by_colors_materials.put('K', Material.DIAMOND_BLOCK);
        by_colors_materials.put('L', Material.EMERALD_BLOCK);
        by_colors_materials.put('M', Material.REDSTONE_BLOCK);
    }
}
