package me.joey.commandsplus.command;


import me.joey.commandsplus.CommandsPlus;
import me.joey.commandsplus.command.commands.*;
import me.joey.commandsplus.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CommandManager implements CommandExecutor {
    private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();
    private CommandsPlus plugin = CommandsPlus.getInstance();

    // root master command
    public String main = "c+";


    public void setup(){
        plugin.getCommand(main).setExecutor(this);
        this.commands.add(new UtilityCommand());
        this.commands.add(new InfoCommand());
        this.commands.add(new RankCommand());
        this.commands.add(new FactionCommand());
        this.commands.add(new CoordinateCommand());
        this.commands.add(new TopCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run Commands+ commands!");
            return false;
        }

        Player p = (Player) sender;

        if (command.getName().equalsIgnoreCase(main)){
            if (args.length == 0){
                Utils.sendMessage(p, ChatColor.RED + "Commands+ command format is /c+ <command category> <command>!");
                return false;
            }

            SubCommand target = this.get(args[0]);

            if (target == null){
                Utils.sendMessage(p, ChatColor.RED + "Invalid subcommand!");
                return false;
            }

            ArrayList<String> arrayList = new ArrayList<String>();

            arrayList.addAll(Arrays.asList(args));
            arrayList.remove(0);

            try{
                target.onCommand(p, args);
            }
            catch (Exception e){
                p.sendMessage();
            }

        }
        return false;
    }


    private SubCommand get(String name){
        Iterator<SubCommand> subcommands = this.commands.iterator();

        while (subcommands.hasNext()){
            SubCommand sc = (SubCommand) subcommands.next();

            if (sc.name().equalsIgnoreCase(name)){
                return sc;
            }


            String[] aliases;
            int length = (aliases = sc.aliases()).length;

             for (int i = 0; i < length; i++){
                 String alias = aliases[i];

                 if (name.equalsIgnoreCase(alias)){
                     return sc;
                 }
             }
        }

        return null;
    }


    public ArrayList<SubCommand> getSubCommands(){
        return commands;
    }


}
