package me.joey.commandsplus.command.commands;

import me.joey.commandsplus.command.SubCommand;
import me.joey.commandsplus.playerplus.PlayerPlus;
import me.joey.commandsplus.playerplus.Rank;
import me.joey.commandsplus.sentinel.Sentinel;
import me.joey.commandsplus.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class InfoCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {
        if (!PlayerPlus.playerPlusHashMap.get(p).getRank().getRankName().equalsIgnoreCase("owner")){
            Utils.sendMessage(p, org.bukkit.ChatColor.RED + "Only the server owner can use these commands!");
            return;
        }

        // fetch username from command arguments
        String username = args[1];
        String uuid = "";

        // find user id given a username
        uuid = Sentinel.findUserID(username);

        if (uuid.equalsIgnoreCase("")){
            Utils.sendMessage(p, ChatColor.RED + "Player could not be found.");
            return;
        }

        // fetch faction and rank from player data files
        String faction = Sentinel.playerDataConfig.getString("Users." + uuid + ".faction");
        String rankStr = Sentinel.playerDataConfig.getString("Users." + uuid + ".rank");
        Rank rank = new Rank(rankStr == null ? "None" : rankStr);

        if (faction == null){
            faction = "None";
        }

        // print out stats to command sender
        Utils.sendMessage(p, "Here are the stats for " + ChatColor.AQUA + username + ChatColor.WHITE + ":");
        Utils.sendMessage(p, "-----------------------------------------");
        Utils.sendMessage(p, "UUID: " + ChatColor.GOLD + uuid);
        Utils.sendMessage(p, "Faction: " + ChatColor.AQUA + faction);
        Utils.sendMessage(p, "Rank: " + rank.toString());

    }

    @Override
    public String name() {
        return "info";
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

        for (Player p : Bukkit.getOnlinePlayers()){
            subSubCommands.add(p.getName());
        }

        return subSubCommands;
    }
}
