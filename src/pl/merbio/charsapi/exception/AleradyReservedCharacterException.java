package pl.merbio.charsapi.exception;

/**
 * @author Merbio
 */

public class AleradyReservedCharacterException extends Exception{   
    public AleradyReservedCharacterException(char character){
        super("Charcter: " + character + " is alerady reserved by other field. You cannot to replace him.");
    }
}