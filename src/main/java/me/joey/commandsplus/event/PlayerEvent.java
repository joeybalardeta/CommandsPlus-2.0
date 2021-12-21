package me.joey.commandsplus.event;

import me.joey.commandsplus.CommandsPlus;
import me.joey.commandsplus.playerplus.PlayerPlus;
import me.joey.commandsplus.playerplus.Rank;
import me.joey.commandsplus.sentinel.Sentinel;
import me.joey.commandsplus.utils.Utils;
import me.joey.commandsplus.vanillaplus.particles.ParticleData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerEvent implements Listener {
    private CommandsPlus instance = CommandsPlus.getInstance();

    // run event when player joins the server
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        PlayerPlus playerPlus = new PlayerPlus(event.getPlayer());


        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(CommandsPlus.getPlugin(CommandsPlus.class), new Runnable() {
            public void run() {

                Utils.sendMessage(p, "Welcome " + ChatColor.AQUA + p.getName() + ChatColor.WHITE + "!");
                Utils.sendMessage(p, "The current server time is " + ChatColor.AQUA + Utils.getFormattedTime(instance.getServer(), p));
            }
        }, 10L);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        PlayerPlus.playerPlusHashMap.remove(event.getPlayer());

        ParticleData particle = new ParticleData(event.getPlayer().getUniqueId());

        if (particle.hasID()) {
            particle.endTask();
            particle.removeID();
        }
    }

    @EventHandler
    public void onPlayerPublicMessage(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Player p = event.getPlayer();

        String rankStr = Sentinel.playerDataConfig.getString("Users." + p.getUniqueId() + ".rank");
        Rank rank;
        if (rankStr == null){
            rank = new Rank();
        }
        else{
            rank = new Rank(rankStr);
        }


        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(ChatColor.WHITE + "[" + rank.toString() + ChatColor.WHITE + "] " + ChatColor.AQUA + p.getName() + ChatColor.WHITE + ": " + event.getMessage());
        }

    }
}
