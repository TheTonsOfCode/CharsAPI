package pl.merbio.charsapi.objects;

/**
 * @author Merbio
 */

public class CharsStringBeforeBlocks {

    public CharsMaterial[][] front, over;
    
    public CharsStringBeforeBlocks(int width, int heigth) {
        front = new CharsMaterial[width][heigth];
        over = new CharsMaterial[width][heigth];
    }
}
