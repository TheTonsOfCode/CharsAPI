package pl.merbio.charsapi.objects.letters;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import pl.merbio.charsapi.objects.BlockSettings;
import pl.merbio.charsapi.objects.CharsBuilder;
import pl.merbio.charsapi.other.Message;

/**
 * @author Merbio
 */

public class ColorDecoder extends DecodeLetter{
    
    @Override
    public boolean decode(int at, String string, BlockSettings bs, CharsBuilder cb) {
        Character character = firstChar(bs.getColorChar(), at, string);
        
        if(character == null){
            return false;
        }
        
        Material m = bs.getMaterial(character);
        if(m == null){
            DyeColor dc = a(character, cb.getBuilderDataBlock(), bs);
            if(dc != null){
                cb.setBuilderDataBlock(dc.getData());
            }
            else {
                return false;
            }
        }
        else {
            cb.setBuilderMaterial(m);
        }
        
        return true;
    }
    
    private DyeColor a(Character c, Byte data, BlockSettings bs) {
        DyeColor dc = bs.getDyeColor(c);
//        Message.broadcast("&7- &a" + c + " &7- &b" + dc.getData() + "&7/&b" + data);
        
        if(c.toString().equalsIgnoreCase("x")){
            if(dc.getData() == data) {
                return a(c, data, bs);
            }
        }
        
        return dc;
    }
}
