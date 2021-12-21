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

public class RankCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {
        if (!PlayerPlus.playerPlusHashMap.get(p).getRank().getRankName().equalsIgnoreCase("owner")){
            Utils.sendMessage(p, ChatColor.RED + "Only the server owner can use these commands!");
            return;
        }

        // fetch username from command arguments
        String username = args[1];
        String uuid;


        // find user id given a username
        uuid = Sentinel.findUserID(username);


        if (uuid.equals("")){
            return;
        }

        if (args.length < 3){
            Utils.sendMessage(p, ChatColor.RED + "Please specify rank name in command!");
            return;
        }

        Sentinel.playerDataConfig.set("Users." + uuid + ".rank", args[2]);

        Rank rank = new Rank(args[2]);

        Utils.sendMessage(p, "Set " + ChatColor.AQUA + username + ChatColor.WHITE + "'s rank to " + rank.toString());

        if (Utils.getPlayer(username) != null){
            Utils.sendMessage(Utils.getPlayer(username), "Your rank has been set to " + rank.toString());
        }
    }

    @Override
    public String name() {
        return "setrank";
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
