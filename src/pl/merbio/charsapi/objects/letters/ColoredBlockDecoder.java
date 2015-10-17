package pl.merbio.charsapi.objects.letters;

import org.bukkit.Material;
import pl.merbio.charsapi.objects.BlockSettings;
import pl.merbio.charsapi.objects.CharsBuilder;
import pl.merbio.charsapi.objects.ColoredMaterial;

/**
 * @author Merbio
 */

public class ColoredBlockDecoder extends DecodeLetter{
    
    @Override
    public boolean decode(int at, String string, BlockSettings bs, CharsBuilder cb) {
        Character character = firstChar(bs.getColoredBlockChar(), at, string);
        
        if(character == null){
            return false;
        }
        
        String s = character.toString();
        Material material = null;
        boolean equals = false;
        
        for(ColoredMaterial cm: ColoredMaterial.values()){
            if(cm.getShortName().equalsIgnoreCase(s)){
                material = cm.getMaterial();
                equals = true;
                break;
            }
        }

        if(!equals) {
            return false;
        }

        cb.setBuilderMaterial(material);
       
        return true;
    }
}
