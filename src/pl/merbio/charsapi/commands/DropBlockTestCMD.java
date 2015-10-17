package pl.merbio.charsapi.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import pl.merbio.charsapi.objects.CharsFallingBlockContainer;
import pl.merbio.charsapi.objects.CharsMaterial;
import pl.merbio.utilities.commands.LiteCommand;
import pl.merbio.utilities.commands.LiteSubCommand;

/**
 * @author Merbio
 */
public class DropBlockTestCMD extends LiteCommand {

    public DropBlockTestCMD() {
        super("dropblock", "dropping charsApi bedrock block", "db", "drp");
        setMins(0, 1, "");
    }

    @Override
    protected boolean execute() {
        Location loc = player.getLocation();
        new CharsFallingBlockContainer().spawnFallingBlock(
                loc,
                loc.clone().add(0, -15, 0),
                new CharsMaterial(Material.BEDROCK, (byte) 0)
        );

        send("&aCompleted!");

        return true;
    }

}
