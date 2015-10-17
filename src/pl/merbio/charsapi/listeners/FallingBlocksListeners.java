package pl.merbio.charsapi.listeners;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import pl.merbio.Main;
import pl.merbio.charsapi.other.LocalException;
import pl.merbio.charsapi.other.Settings;

/**
 * @author Merbio
 */
public class FallingBlocksListeners implements Listener {

    private static ArrayList<FallingBlock> blocks = new ArrayList<>();
    private static boolean isFBProtection;
    private static int fbProtect;

    private static boolean fbInform;

    public FallingBlocksListeners() {
        isFBProtection = Settings.isEnableFallingBlockProtection();
        fbProtect = Settings.getFallingBlockProtect();

        fbInform = true;
    }

    public static void clearBlocks() {
        for (FallingBlock fb : blocks) {
            fb.remove();
        }
        blocks = new ArrayList<>();
    }

    public static void setTimeProtectBlock(final FallingBlock fb, int time) {
        blocks.add(fb);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                blocks.remove(fb);
            }
        }, 20L * time);
    }

    public static void setTimeProtectBlock(FallingBlock fb) {
        setTimeProtectBlock(fb, 5);
    }

    public static void setProtectBlock(final FallingBlock fb) {
        blocks.add(fb);
    }

    @EventHandler
    public void onBlockFall(EntityChangeBlockEvent event) {
        if ((event.getEntityType() == EntityType.FALLING_BLOCK)) {
            FallingBlock fb = (FallingBlock) event.getEntity();

            if (blocks.contains(fb)) {
                blocks.remove(fb);
            }

            Material m = fb.getMaterial();

            if (m != Material.SAND && m != Material.GRAVEL) {
                event.setCancelled(true);
            }
        }
    }

    public static void checkBlocksAreLiving() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                int size = blocks.size();

                if (isFBProtection && size > fbProtect) {
                    if (fbInform) {
                        LocalException.TooMuchFallingBlocksHandlingNotify.notifyException("&bCurrentHandlingBlocks: &f" + size + " &bMaxSuggestingBlocks: &f" + fbProtect);
                    }
                    
                    fbInform = false;
                } else {
                    fbInform = true;
                }

                ArrayList<FallingBlock> toRemove = new ArrayList<>();

                for (FallingBlock f : blocks) {
                    if (f.isDead() || f.isOnGround() || f.getTicksLived() < 10 || f.getFallDistance() < 1 || f.getFallDistance() > 100) {
                        toRemove.add(f);
                    }
                }
                for (FallingBlock f : toRemove) {
                    if (blocks.contains(f)) {
                        f.remove();
                        blocks.remove(f);
                    }
                }
            }
        }, 0, 100L);

    }
}
