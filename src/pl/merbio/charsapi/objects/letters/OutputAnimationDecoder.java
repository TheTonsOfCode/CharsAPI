package pl.merbio.charsapi.objects.letters;

import pl.merbio.charsapi.animations.CharsOutputAnimation;
import pl.merbio.charsapi.objects.BlockSettings;
import pl.merbio.charsapi.objects.CharsBuilder;

/**
 * @author Merbio
 */

public class OutputAnimationDecoder extends DecodeLetter{

    @Override
    public boolean decode(int at, String string, BlockSettings bs, CharsBuilder cb) {
        Character character = firstChar(bs.getOutputAnimationChar(), at, string);
        
        if(character == null){
            return false;
        }

        CharsOutputAnimation ca = CharsOutputAnimation.getByShortName(character.toString());

        if(ca == null){
            return false;
        }

        cb.setBuilderAnimationOUT(ca);
        return true;
    }

}