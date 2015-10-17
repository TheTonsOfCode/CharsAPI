package pl.merbio.charsapi.commands.sub;

import java.util.ArrayList;
import pl.merbio.charsapi.commands.CommandUtil;
import pl.merbio.charsapi.commands.SubCommand;
import pl.merbio.charsapi.commands.sub.updater.UAddSubCommand;
import pl.merbio.charsapi.commands.sub.updater.UCreateSubCommand;
import pl.merbio.charsapi.commands.sub.updater.UInfoSubCommand;
import pl.merbio.charsapi.commands.sub.updater.UListSubCommand;
import pl.merbio.charsapi.commands.sub.updater.UDelSubComand;
import pl.merbio.charsapi.commands.sub.updater.UFlushSubCommand;
import pl.merbio.charsapi.commands.sub.updater.URemoveSubCommand;
import pl.merbio.charsapi.commands.sub.updater.USaveSubCommand;
import pl.merbio.charsapi.commands.sub.updater.USelectSubCommand;
import pl.merbio.charsapi.commands.sub.updater.USetlocSubCommand;
import pl.merbio.charsapi.commands.sub.updater.USettimeSubCommand;
import pl.merbio.charsapi.commands.sub.updater.UStartSubCommand;
import pl.merbio.charsapi.commands.sub.updater.UStopAllSubCommand;
import pl.merbio.charsapi.commands.sub.updater.UStopSubCommand;
import pl.merbio.charsapi.commands.sub.updater.UTptoSubCommand;
import pl.merbio.charsapi.other.Lang;

public class UpdaterSubCommand extends SubCommand{
    
    private ArrayList<SubCommand> subCommands = new ArrayList<SubCommand>();
    private String helpMsg;

    public UpdaterSubCommand() {
        super("updater", Lang.CMD_DESC_UPDATER, "update", "u", "up");
        
        subCommands.add(new UCreateSubCommand());
        subCommands.add(new URemoveSubCommand());
        subCommands.add(new UTptoSubCommand());
        subCommands.add(new USetlocSubCommand()); 
        subCommands.add(new USettimeSubCommand());
        subCommands.add(new UAddSubCommand());
        subCommands.add(new UDelSubComand());
        subCommands.add(new UInfoSubCommand());
        subCommands.add(new UListSubCommand());
        subCommands.add(new USelectSubCommand());
        subCommands.add(new UFlushSubCommand());
        subCommands.add(new UStartSubCommand());
        subCommands.add(new UStopSubCommand());
        subCommands.add(new UStopAllSubCommand());
        subCommands.add(new USaveSubCommand());

        helpMsg = CommandUtil.subCommandsHelp(subCommands);
    }

    @Override
    protected boolean execute(String[] args) {
        if(args.length == 0){
            send(Lang.UPDATER_HELP + " \n" + helpMsg);
            return false;
        }
        
        return CommandUtil.runSubCommand(player, args, subCommands);
    }    
}
