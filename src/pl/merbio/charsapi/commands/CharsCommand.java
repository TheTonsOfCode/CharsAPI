package pl.merbio.charsapi.commands;

import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.merbio.charsapi.commands.sub.AnimationSubCommand;
import pl.merbio.charsapi.commands.sub.BuildSubCommand;
import pl.merbio.charsapi.commands.sub.ClearSubCommand;
import pl.merbio.charsapi.commands.sub.FontSubCommand;
import pl.merbio.charsapi.commands.sub.FontsContent;
import pl.merbio.charsapi.commands.sub.ListSubCommand;
import pl.merbio.charsapi.commands.sub.TestSubCommand;
import pl.merbio.charsapi.commands.sub.UpdaterSubCommand;
import pl.merbio.charsapi.other.Lang;

public class CharsCommand implements CommandExecutor{
    
    private String helpMsg;
    private ArrayList<SubCommand> subCommands = new ArrayList<SubCommand>();
    
    public CharsCommand(){
        FontsContent.reg();
        
        subCommands.add(new AnimationSubCommand());
        subCommands.add(new ListSubCommand());
        subCommands.add(new BuildSubCommand());
        subCommands.add(new ClearSubCommand());
        subCommands.add(new FontSubCommand());
        subCommands.add(new UpdaterSubCommand());
        subCommands.add(new TestSubCommand());
        
        helpMsg = CommandUtil.subCommandsHelp(subCommands);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Lang.NO_CONSOLE);
            return false;
        }
        
        Player player = (Player) sender;
        
        if(!player.hasPermission("chars")){
            CommandUtil.send(player, Lang.NO_PERMISSION);
            return false;
        }
        
        if(args.length == 0){
            CommandUtil.send(player, Lang.HELP + " \n" + helpMsg);  
            return false;
        }
        
        return CommandUtil.runSubCommand(player, args, subCommands);
    }
}
