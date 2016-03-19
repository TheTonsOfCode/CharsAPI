package pl.merbio.charsapi.commands.sub;

import pl.merbio.Main;
import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.objects.BlockSettings;
import pl.merbio.charsapi.objects.CharsBuilder;
import pl.merbio.charsapi.objects.CharsVariable;
import pl.merbio.charsapi.other.Lang;

public class VarsSubCommand extends SubCommand {

    private String v;

    public VarsSubCommand() {
        super("vars", Lang.CMD_DESC_VARS, "var");
        setMins(0, 0, "");

        v = "";

        for (CharsVariable variable : CharsBuilder.getVariables()) {
            v += "&f--> &a";
            boolean b = false;
            for (String s : variable.getVarNames()) {
                if (b) {
                    v += "&f, &a";
                }
                v += s;
                b = true;
            }
            v += " &e(Test result: '" + variable.getResult() + "')&a\n";
        }
    }

    @Override
    protected boolean execute(String[] args) {
        BlockSettings s = Main.getMainBuilder().getBlockSettings();
        send(Lang.VARS_EXAMPLE_USE.replaceAll("%VAR_CHAR%", "" + s.getVarChar()));
        send(Lang.VARS_EXAMPLE_PREFIX + "\n" + v);
        return true;
    }
}
