package pl.merbio.charsapi.commands.sub;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.objects.CharsFallingBlockContainer;
import pl.merbio.charsapi.objects.CharsMaterial;

/**
 * @author Merbio
 */

public class TestSubCommand extends SubCommand{

    public TestSubCommand() {
        super("test", "test CMD", "t");
        setMins(0, 0, "");
    }

    @Override
    protected boolean execute(String[] args) {  
        Bukkit.dispatchCommand(player, 
                "ch b "
                + "@8#C&c/---\\\\n"
                + "@9#C&c|&d_&3O&d^&2X&d_&c|\\n"
                + "@8#C&d\\&4(_)&d/\\n"
                + "@9#C&1___&d|_|&1___\\n"
                + "@8#C&1|&aM&bE&5R&8B&6I&3O&1|\\n"
                + "@9#C&1\\_|&9___&1|_/\\n"
                + "@8#C&1|_|&9___&1|_|\\n"
                + "@9#C&1\\_|&c==&1|_/");
        send("&aComplete!");
        return true;
    }

}
