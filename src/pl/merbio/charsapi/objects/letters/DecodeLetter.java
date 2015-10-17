package pl.merbio.charsapi.objects.letters;

import pl.merbio.charsapi.objects.BlockSettings;
import pl.merbio.charsapi.objects.CharsBuilder;

/**
 * @author Merbio
 */

public abstract class DecodeLetter {
    
    private String recrated;

    public abstract boolean decode(int at, String string, BlockSettings bs, CharsBuilder cb);
    
    protected Character firstChar(char equal, int at, String repl){
        String string = repl.substring(at);
        if(string.length() < 3){
            return null;
        }
        
        String repalcer = String.valueOf(equal);
        Character c = string.charAt(0);
        
        if(!c.toString().equalsIgnoreCase(repalcer)){
            return null;
        }

        return string.charAt(1);
    }
    
    public String getRecreated(){
        return recrated;
    }
}
