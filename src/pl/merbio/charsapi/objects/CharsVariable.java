package pl.merbio.charsapi.objects;

public class CharsVariable {

    private String[] varNames;
    private String result;
    private onVarCheck varCheck;

    public CharsVariable(String result, String... varNames) {
        this.varNames = varNames;
        this.result = result;
    }

    public CharsVariable(onVarCheck varCheck, String... varNames) {
        this.varNames = varNames;
        this.varCheck = varCheck;
    }

    public String[] getVarNames() {
        return varNames;
    }

    public String getResult() {
        if (varCheck != null) {
            return varCheck.on();
        }
        return result;
    }

    public static abstract class onVarCheck {

        public abstract String on();
    }
}
