package pl.merbio.utilities.commands;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

/**
 * @author Merbio
 */
public class CommandManager {

    public static void registerCommand(LiteCommand command) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(command.getName(), command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void registerCommands(Plugin plugin, String commandsPackagePath) {
        int classesNumber = commandsPackagePath.split("\\.").length;

        ArrayList<String> classes = new ArrayList();

        String path = commandsPackagePath.replaceAll("\\.", "/");

        String jarName = plugin.getDataFolder().getAbsolutePath() + ".jar";

        try {
            JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
            JarEntry jarEntry;

            while (true) {
                jarEntry = jarFile.getNextJarEntry();
                if (jarEntry == null) {
                    break;
                }
                if ((jarEntry.getName().startsWith(path)) && (jarEntry.getName().endsWith(".class"))) {
                    classes.add(jarEntry.getName().replaceAll("/", "\\."));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String name : classes) {
            try {
                Class c = CommandManager.class.getClassLoader().loadClass(new StringBuilder().append(commandsPackagePath + ".")
                        .append(name.split("\\.")[classesNumber]).toString());

                if(c.getGenericSuperclass() == LiteCommand.class){
                    registerCommand((LiteCommand) c.newInstance());
                }
            } catch (Exception ex) {
                Logger.getLogger(ex.getMessage());
            }
        }
    }

    private String permPrefix;
    private String messagePrefix;

    public CommandManager(String permPrefix, String messagePrefix) {
        setPermPrefix(permPrefix);
        setMessagePrefix(messagePrefix);
    }

    public void setPermPrefix(String permPrefix) {
        this.permPrefix = permPrefix;
    }

    public void setMessagePrefix(String messagePrefix) {
        this.messagePrefix = messagePrefix;
    }

    public void loadCommand(LiteCommand command) {
        registerCommand(command);
        command.setPermissionPrefix(permPrefix);
        command.setMessagePrefix(messagePrefix);
    }
}
