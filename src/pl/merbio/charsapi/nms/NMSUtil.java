package pl.merbio.charsapi.nms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.merbio.charsapi.objects.CharsBuilder;
import pl.merbio.charsapi.objects.CharsVariable;

public class NMSUtil {

    private static String VERSION;

    static {
        try {
            VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            System.out.println("\n\nERROR CHARSAPI When try to catch bukkit server VERSION!!!\n");
        }
    }

    public static void registerOnlineCharsVariable() {
//        if (VERSION.startsWith("v1_9")) {
//            CharsBuilder.addCharsVariables(
//                    new CharsVariable(new CharsVariable.onVarCheck() {
//                        @Override
//                        public String on() {
//                            return String.valueOf(((org.bukkit.craftbukkit.v1_9_R1.CraftServer) Bukkit.getServer()).getOnlinePlayers().size());
//                        }
//                    }, "online", "on")
//            );
//        } else {
//            CharsBuilder.addCharsVariables(
//                    new CharsVariable(new CharsVariable.onVarCheck() {
//                        @Override
//                        public String on() {
//                            return String.valueOf(((org.bukkit.craftbukkit.v1_7_R4.CraftServer) Bukkit.getServer()).getOnlinePlayers().length);
//                        }
//                    }, "online", "on")
//            );
//        }
        CharsBuilder.addCharsVariables(
                new CharsVariable(new CharsVariable.onVarCheck() {
                    @Override
                    public String on() {
                        return String.valueOf(getOnlinePlayersSize());
                    }
                }, "online", "on")
        );
    }

    public static int getOnlinePlayersSize() {
        try {
            Method method = Bukkit.class.getMethod("getOnlinePlayers");
            Object players = method.invoke(null);

            if (players instanceof Player[]) {

                Player[] oldPlayers = (Player[]) players;
                return oldPlayers.length;

            } else {
                Collection<Player> newPlayers = (Collection<Player>) players;
                return newPlayers.size();
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
 
        return 0;
    }
}
