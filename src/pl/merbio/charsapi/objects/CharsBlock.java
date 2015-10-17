package pl.merbio.charsapi.objects;

import org.bukkit.Material;

public class CharsBlock {
    
    private int x, y;
    private String before;
    private Material material;
    private Byte data;
    
    public CharsBlock(int x, int y, String before, Material material, Byte data){
        this.x = x;
        this.y = y;
        this.before = before;
        this.material = material;
        this.data = data;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setBefore(String before){
        this.before = before;
    }
    
    public String getBefore(){
        return before;
    }
    
    public Material getType(){
        return material;
    }
    
    public Byte getData(){
        return data;
    }
}
