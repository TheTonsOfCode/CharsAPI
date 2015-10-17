package pl.merbio.charsapi.other;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.merbio.Main;

/**
 * @author Merbio
 */

public class Message {
    
    private static String prefix = "&e&o[&6&oCharsAPI&e&o]&f ";
    private static String console_prefix = "&1[CharsAPI] ";
    private static Main main;
    private static ConsoleCommandSender console;
    
    public Message(Main main){
        this.main = main;
        this.console = main.getServer().getConsoleSender();
    }
    
    public static void send(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }
    
    public static void broadcast(String message) {
        main.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }
    
    public static void console(String message) {
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', console_prefix + message));
    }
}
