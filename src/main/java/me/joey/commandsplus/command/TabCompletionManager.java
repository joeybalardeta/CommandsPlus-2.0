package me.joey.commandsplus.command;

import me.joey.commandsplus.CommandsPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompletionManager implements TabCompleter {
    private CommandsPlus plugin = CommandsPlus.getInstance();


    public void setup(){
        plugin.getCommand(CommandsPlus.getInstance().commandManager.main).setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabComplete = new ArrayList<String>();
        Player p = (Player) sender;


        if (args.length == 1 && label.equalsIgnoreCase(CommandsPlus.getInstance().commandManager.main)){
            ArrayList<SubCommand> subCommands = CommandsPlus.getInstance().commandManager.getSubCommands();
            for (int i = 0; i < subCommands.size(); i++) {
                tabComplete.add(subCommands.get(i).name());
            }
        }



        if (args.length == 2 && label.equalsIgnoreCase(CommandsPlus.getInstance().commandManager.main)){
            ArrayList<SubCommand> subCommands = CommandsPlus.getInstance().commandManager.getSubCommands();
            for (int i = 0; i < subCommands.size(); i++) {
                if (args[0].equalsIgnoreCase(subCommands.get(i).name())){
                    List<String> subSubCommands = subCommands.get(i).subSubCommands();
                    for (int j = 0; j < subSubCommands.size(); j++) {
                        tabComplete.add(subSubCommands.get(j));
                    }
                }
            }
        }


        return tabComplete;
    }
}
