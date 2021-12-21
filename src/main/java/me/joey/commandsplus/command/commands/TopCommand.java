package me.joey.commandsplus.command.commands;

import me.joey.commandsplus.command.SubCommand;
import me.joey.commandsplus.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class TopCommand extends SubCommand {

    @Override
    public void onCommand(Player p, String[] args) {
        // teleport player to top y-coordinate in their location(don't use in the nether please)
        if (p.getWorld().getEnvironment() == World.Environment.NETHER) {
            Utils.sendMessage(p,  ChatColor.RED + "You cannot use /top in the Nether!");
        }

        Location l = new Location(Bukkit.getWorld("world"), p.getLocation().getX(), p.getWorld().getHighestBlockYAt(p.getLocation()) + 1, p.getLocation().getZ());

        // safeguards so people don't get tp'ed into lava


        Location lCheck = new Location(Bukkit.getWorld("world"), p.getLocation().getX(),
                p.getWorld().getHighestBlockYAt(p.getLocation()) - 1, p.getLocation().getZ());
        int xPlus = 0;

        while (lCheck.getBlock().getType() == Material.LAVA) {
            xPlus += 10;
            lCheck = new Location(Bukkit.getWorld("world"), p.getLocation().getX() + xPlus,
                    p.getWorld().getHighestBlockYAt(p.getLocation()) - 1, p.getLocation().getZ());
        }

        l = new Location(Bukkit.getWorld("world"), p.getLocation().getX() + xPlus,
                p.getWorld().getHighestBlockYAt(p.getLocation()) + 1, p.getLocation().getZ());

        p.teleport(l);

        // Commands+ System Message
        Utils.sendMessage(p, ChatColor.GREEN + "Teleported you to the top!");
    }

    @Override
    public String name() {
        return "top";
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
        return null;
    }
}
