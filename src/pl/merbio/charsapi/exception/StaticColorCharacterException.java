package pl.merbio.charsapi.exception;

/**
 * @author Merbio
 */

public class StaticColorCharacterException extends Exception{   
    public StaticColorCharacterException(char character){
        super("Charcter: " + character + " is a static color charcter. You cannot to replace him.");
    }
}
