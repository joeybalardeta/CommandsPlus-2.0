package me.joey.commandsplus.vanillaplus.weather;

import me.joey.commandsplus.CommandsPlus;
import me.joey.commandsplus.utils.Utils;
import org.bukkit.scheduler.BukkitScheduler;

public class WeatherTweaks {
    CommandsPlus instance = CommandsPlus.getInstance();
    BukkitScheduler scheduler = instance.getServer().getScheduler();
    public void init(){
        // run task to set weather to rain every hour
        scheduler.scheduleSyncRepeatingTask(CommandsPlus.getInstance(), new Runnable() {
            @Override
            public void run() {

                if (Utils.randomInt(1, 10) == 3 && !instance.getServer().getWorld("world").hasStorm()) {
                    instance.getServer().getWorld("world").setStorm(true);
                }
            }
        }, 0L, 12000L);
    }

}
