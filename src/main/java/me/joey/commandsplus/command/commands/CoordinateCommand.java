package me.joey.commandsplus.command.commands;

import me.joey.commandsplus.command.SubCommand;
import me.joey.commandsplus.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CoordinateCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {

        // get player's current coordinates
        if (args.length == 1) {
            Location loc = p.getLocation();
            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            // Commands+ System Message
            p.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "Commands" + ChatColor.DARK_RED + "+"
                    + ChatColor.WHITE + "] " + "Your coordinates" + ": " + ChatColor.GREEN + x + ChatColor.WHITE + ", " + ChatColor.GREEN + y + ChatColor.WHITE + ", " + ChatColor.GREEN + z);
            return;

        }

        // send player's coordinates to everyone on the server
        else if (args[1].equals("send")) {
            // Commands+ System Message
            Utils.sendMessage(p, "Sending your coordinates to everyone!");
            Location loc = p.getLocation();
            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();
            for (Player online : Bukkit.getOnlinePlayers()) {
                Utils.sendMessage(online,ChatColor.AQUA + p.getName() + ChatColor.WHITE + "'s coordinates are: " + ChatColor.GREEN + x + ChatColor.WHITE + ", " + ChatColor.GREEN + y + ChatColor.WHITE + ", " + ChatColor.GREEN + z);
            }

            return;
        }

    }

    @Override
    public String name() {
        return "cords";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    @Override
    public List<String> subSubCommands() {
        List<String> subSubCommands = new ArrayList<String>();

        subSubCommands.add("send");

        return subSubCommands;
    }
}
