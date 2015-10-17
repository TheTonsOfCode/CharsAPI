package pl.merbio.charsapi.objects.letters;

import pl.merbio.charsapi.animations.CharsInputAnimation;
import pl.merbio.charsapi.objects.BlockSettings;
import pl.merbio.charsapi.objects.CharsBuilder;

/**
 * @author Merbio
 */

public class InputAnimationDecoder extends DecodeLetter{

    @Override
    public boolean decode(int at, String string, BlockSettings bs, CharsBuilder cb) {
        Character character = firstChar(bs.getInputAnimationChar(), at, string);
        
        if(character == null){
            return false;
        }

        CharsInputAnimation ca = CharsInputAnimation.getByShortName(character.toString());

        if(ca == null){
            return false;
        }

        cb.setBuilderAnimationIN(ca);
        return true;
    }

}
