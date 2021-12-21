package me.joey.commandsplus.command.commands;

import me.joey.commandsplus.command.SubCommand;
import me.joey.commandsplus.playerplus.PlayerPlus;
import me.joey.commandsplus.playerplus.Rank;
import me.joey.commandsplus.sentinel.Sentinel;
import me.joey.commandsplus.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        Rank rank;
        if (rankStr == null){
            rank = new Rank();
        }
        else{
            rank = new Rank(rankStr);
        }

        if (faction == null){
            faction = "None";
        }

        int playerKills = 0;
        int blocksMined = 0;
        int diamondsMined = 0;
        int ancientDebrisMined = 0;

        if (Bukkit.getPlayer(UUID.fromString(uuid)) != null){

            Player target = Bukkit.getPlayer(UUID.fromString(uuid));
            PlayerPlus targetPlus = PlayerPlus.getPlayerPlus(target);

            playerKills = targetPlus.getPlayerKills();
            blocksMined = targetPlus.getBlocksMined();
            diamondsMined = targetPlus.getDiamondsMined();
            ancientDebrisMined = targetPlus.getAncientDebrisMined();
        }
        else{
            OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            playerKills = target.getStatistic(Statistic.PLAYER_KILLS);
            blocksMined = target.getStatistic(Statistic.MINE_BLOCK);
            diamondsMined = target.getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE) + target.getStatistic(Statistic.MINE_BLOCK, Material.DEEPSLATE_DIAMOND_ORE);
            ancientDebrisMined = target.getStatistic(Statistic.PLAYER_KILLS, Material.ANCIENT_DEBRIS);
        }

        // print out stats to command sender
        Utils.sendMessage(p, "Here are the stats for " + ChatColor.AQUA + username + ChatColor.WHITE + ":");
        Utils.sendMessage(p, "-----------------------------------------");
        Utils.sendMessage(p, "UUID: " + ChatColor.GOLD + uuid);
        Utils.sendMessage(p, "Faction: " + ChatColor.AQUA + faction);
        Utils.sendMessage(p, "Rank: " + rank.toString());
        Utils.sendMessage(p, "Players Killed: " + ChatColor.RED + playerKills);
        Utils.sendMessage(p, "Blocks Mined: " + ChatColor.GREEN + blocksMined);
        Utils.sendMessage(p, "Diamond Ore Mined: " + ChatColor.GREEN + diamondsMined);
        Utils.sendMessage(p, "Ancient Debris Mined: " + ChatColor.GREEN + ancientDebrisMined);





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
