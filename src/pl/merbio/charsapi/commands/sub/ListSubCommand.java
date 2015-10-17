package pl.merbio.charsapi.commands.sub;

import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.other.Lang;

public class ListSubCommand extends SubCommand{

    public ListSubCommand() {
        super("list", Lang.CMD_DESC_LIST, "l");
        setMins(1, 1, Lang.CMD_ARGS_LIST);
    }

    @Override
    protected boolean execute(String[] args) {
        Integer number = num(args[0]);

        if(number == null){
            send(Message.NUMBER);
            return false;
        }

        if(number < 1 || number > FontsContent.fontsList.size()){
            send(Lang.LIST_UNREACHED_PAGE.replace("%MAX_PAGE%", String.valueOf(FontsContent.fontsList.size())));
            return false;
        }

        send(Lang.LIST_PAGE_INFO.replace("%FONTS%", String.valueOf(FontsContent.fonts.size())).replace("%PAGE%", number.toString()) + "\n" + FontsContent.fontsList.get(number - 1));

        return true;
    }
    
}
