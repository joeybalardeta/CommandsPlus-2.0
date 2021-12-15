package me.joey.commandsplus.command.commands;

import me.joey.commandsplus.command.SubCommand;
import me.joey.commandsplus.playerplus.PlayerPlus;
import me.joey.commandsplus.sentinel.Sentinel;
import me.joey.commandsplus.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FactionCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {
        if (args[1].equalsIgnoreCase("create")){

            if (PlayerPlus.getPlayerPlus(p).getFaction() != null && !PlayerPlus.getPlayerPlus(p).getFaction().equalsIgnoreCase("None")){
                p.sendMessage(PlayerPlus.getPlayerPlus(p).getFaction());
                Utils.sendMessage(p, ChatColor.RED + "You are already in a faction! Leave your current faction to create one!");
                return;
            }


            if (args.length < 3){
                Utils.sendMessage(p, ChatColor.RED + "Please specify a faction name!");
                return;
            }

            String factionName = args[2];
            boolean factionTaken = false;


            try{
                ConfigurationSection factions = Sentinel.factionDataConfig.getConfigurationSection("Factions");
                for (String s : factions.getKeys(false)) {

                    if (s.equalsIgnoreCase(factionName)){
                        factionTaken = true;
                    }
                }
            }
            catch (Exception e){

            }


            if (factionTaken){
                Utils.sendMessage(p, ChatColor.RED + "Faction name is taken!");
                return;
            }

            Sentinel.factionDataConfig.set("Factions." + factionName + ".owner", p.getName());
            PlayerPlus.getPlayerPlus(p).setFaction(factionName);
            PlayerPlus.getPlayerPlus(p).setFactionRank("Owner");
            PlayerPlus.getPlayerPlus(p).writeData();
            Sentinel.save();


            Utils.sendMessage(p, "The faction, " + ChatColor.AQUA + factionName + ChatColor.WHITE + ", has been created!");

            return;
        }

        if (args[1].equalsIgnoreCase("invite")){
            if (PlayerPlus.getPlayerPlus(p).getFaction() == null || PlayerPlus.getPlayerPlus(p).getFaction().equalsIgnoreCase("None")){
                Utils.sendMessage(p, ChatColor.RED + "You are not in a faction! Create a faction to invite players!");
                return;
            }
            if (PlayerPlus.getPlayerPlus(p).getFactionRank() == null || !PlayerPlus.getPlayerPlus(p).getFactionRank().equalsIgnoreCase("owner")){
                Utils.sendMessage(p, ChatColor.RED + "You need to be the owner of the faction to invite players!");
                return;
            }

            if (args.length < 3){
                Utils.sendMessage(p, ChatColor.RED + "Please specify a player name!");
                return;
            }

            String uuid = Sentinel.findUserID(args[2]);

            if (uuid.equals("")){
                Utils.sendMessage(p, ChatColor.RED + "User does not exist!");
                return;
            }


            if (Sentinel.playerDataConfig.getString("Users." + uuid + ".faction") != null){
                Utils.sendMessage(p, ChatColor.RED + "Player is already in a faction!");
            }

            if (Sentinel.playerDataConfig.getString("Users." + uuid + ".invitedtofaction") != null){
                Utils.sendMessage(p, ChatColor.RED + "Player is already invited to a faction!");
            }

            Utils.sendMessage(p, "Invited " + ChatColor.AQUA + args[2] + ChatColor.WHITE + " to " + ChatColor.AQUA + PlayerPlus.getPlayerPlus(p).getFaction() + ChatColor.WHITE + "!");

            Sentinel.playerDataConfig.set("Users." + uuid + ".invitedtofaction", PlayerPlus.getPlayerPlus(p).getFaction());
            return;
        }

        if (args[1].equalsIgnoreCase("decline")){
            if (Sentinel.playerDataConfig.getString("Users." + p.getUniqueId() + ".invitedtofaction") == null){
                Utils.sendMessage(p, ChatColor.RED + "You are not invited to any factions!");
            }
            Sentinel.playerDataConfig.set("Users." + p.getUniqueId() + ".invitedtofaction", null);
            return;
        }

        if (args[1].equalsIgnoreCase("join")){
            if (PlayerPlus.getPlayerPlus(p).getFaction() != null && !PlayerPlus.getPlayerPlus(p).getFaction().equalsIgnoreCase("None")){
                Utils.sendMessage(p, ChatColor.RED + "You are already in a faction! Leave your current faction to join one!");
                return;
            }

            String factionInviteName = Sentinel.playerDataConfig.getString("Users." + p.getUniqueId() + ".invitedtofaction");

            if (factionInviteName == null){
                Utils.sendMessage(p, ChatColor.RED + "You are not invited to any factions!");
                return;
            }

            Utils.sendMessage(p, "Joined " + ChatColor.AQUA + factionInviteName + ChatColor.WHITE + "!");

            Sentinel.playerDataConfig.set("Users." + p.getUniqueId() + ".invitedtofaction", null);
            Sentinel.playerDataConfig.set("Users." + p.getUniqueId() + ".faction", factionInviteName);
            Sentinel.playerDataConfig.set("Users." + p.getUniqueId() + ".factionRank", "Member");
            Sentinel.save();
            return;
        }

        if (args[1].equalsIgnoreCase("leave")){
            if (PlayerPlus.getPlayerPlus(p).getFaction() == null || PlayerPlus.getPlayerPlus(p).getFaction().equalsIgnoreCase("None")){
                Utils.sendMessage(p, ChatColor.RED + "You are not in a faction! You must be in a faction to leave one!");
                return;
            }

            if (Sentinel.playerDataConfig.getString("Users." + p.getUniqueId() + ".factionRank").equalsIgnoreCase("Owner")){
                Utils.sendMessage(p, ChatColor.RED + "Cannot leave faction! You are the owner of it! If you want to disband the faction, please use /c+ faction disband!");
                return;
            }

            Utils.sendMessage(p, "Left " + ChatColor.AQUA + PlayerPlus.getPlayerPlus(p).getFaction() + ChatColor.WHITE + "!");

            Sentinel.playerDataConfig.set("Users." + p.getUniqueId() + ".faction", null);
            Sentinel.playerDataConfig.set("Users." + p.getUniqueId() + ".factionRank", null);

            return;
        }

        if (args[1].equalsIgnoreCase("disband")){
            if (PlayerPlus.getPlayerPlus(p).getFaction() == null || PlayerPlus.getPlayerPlus(p).getFaction().equalsIgnoreCase("None")){
                Utils.sendMessage(p, ChatColor.RED + "You are not in a faction! You must be in a faction to disband one!");
                return;
            }
            String factionName = PlayerPlus.getPlayerPlus(p).getFaction();

            try{
                ConfigurationSection users = Sentinel.playerDataConfig.getConfigurationSection("Users");
                for (String s : users.getKeys(false)) {

                    if (users.get(s) != null) {
                        String faction = users.get(s + ".faction").toString();
                        if (faction.equals(factionName)) {
                            users.set(s + ".faction", null);
                            users.set(s + ".factionRank", null);
                            if (Utils.getPlayer(users.getString(s + ".username")) != null){
                                Player online = Utils.getPlayer(users.getString(s + ".username"));
                                PlayerPlus onlinePlus = PlayerPlus.getPlayerPlus(p);

                                onlinePlus.setFaction(null);
                                onlinePlus.setFactionRank(null);
                            }
                        }
                    }
                }
            }
            catch (Exception e){

            }

            Utils.sendMessage(p, "Disbanded " + ChatColor.AQUA + factionName + ChatColor.WHITE + "!");
            Sentinel.factionDataConfig.set("Factions." + factionName, null);
            Sentinel.save();


            return;
        }


        if (args[1].equalsIgnoreCase("info")){
            String factionName = PlayerPlus.getPlayerPlus(p).getFaction();

            ArrayList<String> factionMembers = new ArrayList<String>();

            try{
                ConfigurationSection users = Sentinel.playerDataConfig.getConfigurationSection("Users");
                for (String s : users.getKeys(false)) {

                    if (users.get(s) != null) {
                        String faction = users.get(s + ".faction").toString();
                        if (faction.equals(factionName)) {
                            if (users.getString(s + ".username") != null){
                                factionMembers.add(users.getString(s + ".username"));
                            }
                        }
                    }
                }
            }
            catch (Exception e){

            }

            Utils.sendMessage(p, "Info for faction " + ChatColor.AQUA + factionName);
            Utils.sendMessage(p, "Owner: " + ChatColor.AQUA + Sentinel.factionDataConfig.getString("Factions." + factionName + ".owner"));
            Utils.sendMessage(p, "Members: " + ChatColor.AQUA + String.join(ChatColor.WHITE + ", " + ChatColor.AQUA, factionMembers));
        }

    }

    @Override
    public String name() {
        return "faction";
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

        subSubCommands.add("create");
        subSubCommands.add("invite");
        subSubCommands.add("decline");
        subSubCommands.add("join");
        subSubCommands.add("leave");
        subSubCommands.add("disband");

        return subSubCommands;
    }
}
