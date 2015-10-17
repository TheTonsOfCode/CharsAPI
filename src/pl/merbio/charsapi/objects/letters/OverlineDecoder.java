package pl.merbio.charsapi.objects.letters;

import pl.merbio.charsapi.objects.BlockSettings;
import pl.merbio.charsapi.objects.CharsBuilder;
import pl.merbio.charsapi.objects.CharsMaterial;
import pl.merbio.charsapi.objects.StaticOverlineMaterial;

/**
 * @author Merbio
 */

public class OverlineDecoder extends DecodeLetter{
    
    @Override
    public boolean decode(int at, String string, BlockSettings bs, CharsBuilder cb) {
        Character character = firstChar(bs.getOverlineChar(), at, string);
        
        if(character == null){
            return false;
        }
        
        String s = character.toString();
        CharsMaterial cm = null;
        boolean equals = false;
        
        for(StaticOverlineMaterial som: StaticOverlineMaterial.values()){
            if(som.getShortName().equalsIgnoreCase(s)){
                cm = som.getCharsMaterial();
                equals = true;
                break;
            }
        }

        if(!equals) {
            return false;
        }

        cb.setBuilderOverlineMaterial(cm);
       
        return true;
    }
}
