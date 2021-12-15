package me.joey.commandsplus;

import me.joey.commandsplus.command.CommandManager;
import me.joey.commandsplus.command.TabCompletionManager;
import me.joey.commandsplus.event.EventManager;
import me.joey.commandsplus.playerplus.PlayerPlus;
import me.joey.commandsplus.sentinel.Sentinel;
import me.joey.commandsplus.vanillaplus.tabmenu.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class CommandsPlus extends JavaPlugin {
    public static String VERSION = "2.0";
    public static String PLUGINNAME = "CommandsPlus";

    private static CommandsPlus instance;

    public static CommandsPlus getInstance(){
        return instance;
    }


    BukkitScheduler scheduler = getServer().getScheduler();

    public CommandManager commandManager;

    public EventManager eventManager;

    public TabCompletionManager tabCompleter;

    public TabManager tab;

    // runs on plugin start, usually when the server starts up
    @Override
    public void onEnable() {
        instance = this;

        Sentinel.init(); // gets all plugin data files ready for reading/writing

        // set up command handlers
        commandManager = new CommandManager();
        commandManager.setup();

        // set up event handlers
        eventManager = new EventManager();
        eventManager.init();

        // set up tab autocomplete for commands
        tabCompleter = new TabCompletionManager();
        tabCompleter.setup();

        // set up custom tab
        tab = new TabManager(this);
        tab.createTab();


        for (Player online : Bukkit.getOnlinePlayers()){
            PlayerPlus playerPlus = new PlayerPlus(online);
        }


        // repeating task that runs every second
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {

            }
        }, 0L, 20L);


        // repeating task that runs every minute
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Sentinel.save(); // save all plugin data files
            }
        }, 0L, 1200L);

        // repeating task that runs every ten minutes
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (!Bukkit.getOnlinePlayers().isEmpty()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm z");
                    Date date = new Date(System.currentTimeMillis());
                    Sentinel.playerLogsConfig.set("Online Players." + formatter.format(date), Bukkit.getOnlinePlayers().size());
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm z");
                    Date date = new Date(System.currentTimeMillis());
                    Sentinel.playerLogsConfig.set("Online Players." + formatter.format(date), 0);
                }
            }
        }, 0L, 12000L);

    }

    // runs on plugin shutdown, usually when the server shuts down
    @Override
    public void onDisable() {
        instance = null;
    }
}
