package pl.merbio.charsapi.commands.sub;

import pl.merbio.Main;
import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.managers.CharsManager;
import pl.merbio.charsapi.objects.CharsBuilder;
import pl.merbio.charsapi.objects.CharsString;
import pl.merbio.charsapi.other.Lang;

public class BuildSubCommand extends SubCommand{

    public BuildSubCommand() {
        super("build", Lang.CMD_DESC_BUILD, "b");
        setMins(1, null, Lang.CMD_ARGS_BUILD);
    }

    @Override
    protected boolean execute(String[] args) {
        CharsBuilder builder = Main.getMainBuilder();
            
        String string = getRest(0, args);

        CharsString cs = builder.replace(string);

        if(cs.getBlocks() == null){
            send(Lang.CHARACTER_UNDEFINED.replace("%CHARACTER%", cs.getString()));
            return false;
        }

        builder.build(player, cs);
        Integer id = CharsManager.addChars(cs);

        send(Lang.BUILD_CHARS.replace("%ID%", id.toString()).replace("%TEXT%", string));
        
        return true;    
    }
    
}
