package pl.merbio.charsapi.commands.sub;

import pl.merbio.Main;
import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.other.Lang;
import pl.merbio.charsapi.other.Settings;

public class FontSubCommand extends SubCommand{

    public FontSubCommand() {
        super("font", Lang.CMD_DESC_FONT, "f");
    }

    @Override
    protected boolean execute(String[] args) {
        if(len < 3){
            if(len == 1 && args[0].equalsIgnoreCase("default")){
                Main.setDefaultBuilderFont();
                send(Lang.FONT_SET_DEFAULT);
                return true;
            }
            send("&c/chars font " + Lang.CMD_ARGS_FONT);
            send(Lang.CMD_ARGS_FONT_TYPES);
            send("&c/chars font default");
            return false;
        }

        Integer size = num(args[0]);

        if(size == null){
            send(Message.NUMBER + " " + Lang.FONT_NUMBER_SIZE);
            return false;
        }
        
        int maxSize = Settings.getMaxFontSizeBlocade();
        
        if(size > maxSize){
            send(Lang.FONT_TOO_BIG.replace("%MAXSIZE%", String.valueOf(maxSize)));
            return false;
        }

        Integer type = num(args[1]);

        if(type == null){
            send(Message.NUMBER + " " + Lang.FONT_NUMBER_TYPE);
            return false;
        }

        if(type < 0 || type > 2){
            send(Lang.CMD_ARGS_FONT_TYPES);
            return false;
        }

        String font = getRest(2, args);

        if(!FontsContent.existFont(font)){
            send(Lang.FONT_UNDEFINDED.replace("%FONT%", font));
            return false;
        }
        
        String sType = FontType.values()[type].name;

        Main.setBuilderFont(font, type, size);
        send(Lang.FONT_SET.replace("%FONT%", font).replace("%SIZE%", size.toString()).replace("%TYPE%", sType));
        
        return true;
    }
    
    private enum FontType {
        
        NORMAL(0, Lang.FONT_TYPE_NORMAL),
        BOLD(1, Lang.FONT_TYPE_BOLD),
        ITALIC(2, Lang.FONT_TYPE_ITALIC);
        
        public int type;
        public String name;
        
        private FontType(int type, String name){
            this.type = type;
            this.name = name;
        }
    }
}
