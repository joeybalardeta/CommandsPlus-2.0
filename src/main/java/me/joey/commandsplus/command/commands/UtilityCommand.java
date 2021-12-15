package me.joey.commandsplus.command.commands;

import com.sun.management.OperatingSystemMXBean;
import me.joey.commandsplus.command.SubCommand;
import me.joey.commandsplus.playerplus.PlayerPlus;
import me.joey.commandsplus.sentinel.Sentinel;
import me.joey.commandsplus.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class UtilityCommand extends SubCommand {

    private OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    @Override
    public void onCommand(Player p, String[] args) {

        // exits if the command sender is not the owner of the server
        if (!PlayerPlus.playerPlusHashMap.get(p).getRank().getRankName().equalsIgnoreCase("owner")){
            Utils.sendMessage(p, ChatColor.RED + "Only the server owner can use these commands!");
            return;
        }
        if (args[1].equalsIgnoreCase("info")){
            Utils.sendMessage(p, "Commands+ is a plugin created by Joey Balardeta");
            Utils.sendMessage(p, "Currently this is the second official version of the plugin.");
            return;
        }

        if (args[1].equalsIgnoreCase("serverstats")){
            Utils.sendMessage(p, "Here are the server stats:");
            Utils.sendMessage(p, ChatColor.YELLOW + "Version: " + ChatColor.AQUA + Bukkit.getVersion());
            Utils.sendMessage(p, ChatColor.YELLOW + "Number of players: " + ChatColor.AQUA + Bukkit.getOnlinePlayers().size());
            Utils.sendMessage(p, ChatColor.YELLOW + "Render Distance | Simulation Distance: " + ChatColor.AQUA + Bukkit.getViewDistance() + ChatColor.WHITE + " | " + ChatColor.AQUA + Bukkit.getSimulationDistance());

            return;
        }

        if (args[1].equalsIgnoreCase("save")){
            Utils.sendMessage(p, ChatColor.AQUA + "Saving server data files.");
            Sentinel.save();
            return;
        }

        if (args[1].equalsIgnoreCase("amongus")){
            Utils.sendMessage(p, ChatColor.RED + "SUS");
            Utils.amongus(p);
            return;
        }


        Utils.sendMessage(p, ChatColor.RED + "Invalid command!");
    }

    @Override
    public String name() {
        return "util";
    }

    @Override
    public String info() {
        return "Utility commands for Commands+";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    @Override
    public List<String> subSubCommands() {
        List<String> subSubCommands = new ArrayList<String>();

        subSubCommands.add("info");
        subSubCommands.add("serverstats");
        subSubCommands.add("save");
        subSubCommands.add("amongus");

        return subSubCommands;
    }
}
