package pl.merbio.charsapi.other;

/**
 * @author Merbio
 */

public enum LocalException {
    
    UpdaterNonExistWorldEx("Error exist when world of location yours updater is not detected", "Remove updater with command /ch u remove <name>, or select and move to existing world"),
    TooMuchFallingBlocksHandlingNotify("Thats exception notify you about plugin must factory too much information on one time and operating about falling blocks, what can make lags", "Minimalize numbers of animations who use falling blocks in your chars");
    
    private String name;
    private String description;
    private String fix;
    
    private LocalException(String description, String fix){
        this.name = name();
        this.description = description;
        this.fix = fix;
    }
    
    public void notifyException(String what){
        Message.console("&bplugin exception detected: \n" +
                "       " + "&1ErrorName: &f" + name + "\n" +
                "       " + "&1ErrorDescription: &f" + description + "\n" +
                "       " + "&1Where / When / Who: &f" + what + "\n" +
                "       " + "&1How to fix: &f" + fix
        );
    }
}
