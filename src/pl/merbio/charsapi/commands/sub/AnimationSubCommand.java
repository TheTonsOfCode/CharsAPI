package pl.merbio.charsapi.commands.sub;

import pl.merbio.charsapi.animations.CharsInputAnimation;
import pl.merbio.charsapi.animations.CharsOutputAnimation;
import pl.merbio.charsapi.commands.CommandUtil;
import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.other.Lang;

public class AnimationSubCommand extends SubCommand{
    
    private String in, out;

    public AnimationSubCommand() {
        super("animation", Lang.CMD_DESC_ANIMATION, "a", "anim");
        setMins(1, 1, Lang.CMD_ARGS_ANIMATION);

        String[][] ss = new String[CharsInputAnimation.getShortNameMap().size()][3];
        for(String s: CharsInputAnimation.getShortNameMap().keySet()){
            CharsInputAnimation ca = CharsInputAnimation.getByShortName(s);
            ss[ca.ordinal()] = new String[]{s, ca.getDescription(), ca.name()};
        }
        in = CommandUtil.buildMessageStruct(ss);
        
        ss = new String[CharsOutputAnimation.getShortNameMap().size()][3];
        for(String s: CharsOutputAnimation.getShortNameMap().keySet()){
            CharsOutputAnimation ca = CharsOutputAnimation.getByShortName(s);
            ss[ca.ordinal()] = new String[]{s, ca.getDescription(), ca.name()};
        }
        out = CommandUtil.buildMessageStruct(ss);
    }

    @Override
    protected boolean execute(String[] args) {
        if(arg("in")){
            send(Lang.ANIMATIONS_IN + "\n" + in);
        }
        else if(arg("out")){
            send(Lang.ANIMATIONS_OUT + "\n" + out);
        }
        else {
            send(Lang.ANIMATIONS_TYPES);
        }
        return true;
    }    
}
