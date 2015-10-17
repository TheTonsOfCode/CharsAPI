package pl.merbio.charsapi.commands.sub;

import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.managers.CharsManager;
import pl.merbio.charsapi.other.Lang;

public class ClearSubCommand extends SubCommand{

    public ClearSubCommand() {
        super("clear", Lang.CMD_DESC_CLEAR, "c", "cc");
        setMins(1, 1, Lang.CMD_ARGS_CLEAR);
    }

    @Override
    protected boolean execute(String[] args) {
        if(args[0].equalsIgnoreCase("all")){
            CharsManager.clearAll();
            send(Lang.CLEAR_CHARS_ALL);
            return true;
        }

        Integer id = num(args[0]);

        if(id == null){
            send(Message.NUMBER);
            return false;
        }

        if(!CharsManager.existChars(id)){
            send(Lang.ID_UNDEFINED);
            return false;
        }

        CharsManager.clearChars(id);
        send(Lang.CLEAR_CHARS.replace("%ID%", id.toString()));
        return true;
    }   
}
