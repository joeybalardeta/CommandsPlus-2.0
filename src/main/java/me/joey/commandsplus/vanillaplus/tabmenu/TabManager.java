package me.joey.commandsplus.vanillaplus.tabmenu;

import me.joey.commandsplus.CommandsPlus;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TabManager {
    private List<ChatComponentText> headers = new ArrayList<>();
    private List<ChatComponentText> footers = new ArrayList<>();

    private CommandsPlus plugin;

    public TabManager(CommandsPlus plugin) {
        this.plugin = plugin;
    }

    public void showTab() {
        if (headers.isEmpty() && footers.isEmpty()) {
            return;
        }

        IChatBaseComponent tabTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"Commands+\"}");
        IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer.a("{\"text\": \"Made by Joey Balardeta\"}");

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabTitle, tabFooter);
            int count1 = 0;
            int count2 = 0;

            @Override
            public void run() {
                try {
                    Field header = packet.getClass().getDeclaredField("a");
                    header.setAccessible(true);
                    Field footer = packet.getClass().getDeclaredField("b");
                    footer.setAccessible(true);

                    if (count1 >= headers.size()) {
                        count1 = 0;
                    }
                    if (count2 >= footers.size()) {
                        count2 = 0;
                    }

                    header.set(packet, headers.get(count1));
                    footer.set(packet, new ChatComponentText(format("&bPlayers Online: " + Bukkit.getOnlinePlayers().size())));

                    if (!Bukkit.getOnlinePlayers().isEmpty()) {
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer) online).getHandle().b.a(packet);
                        }
                    }

                    count1++;
                    count2++;


                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

        }, 10, 40L);
    }


    public void addHeader(String header) {
        headers.add(new ChatComponentText(format(header)));
    }

    public void addFooter(String footer) {
        footers.add(new ChatComponentText(format(footer)));
    }

    public void createTab() {
        this.addHeader("&cCommands&4+\n&fMade by &aJoey Balardeta");
        this.addFooter("&bPlayers Online: " + Bukkit.getOnlinePlayers().size());
        this.showTab();
    }

    private String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}

