package me.joey.commandsplus.sentinel;

import me.joey.commandsplus.CommandsPlus;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Sentinel {


    // initialize plugin data files
    public static File settings;
    public static FileConfiguration settingsConfig;
    public static File playerLogs;
    public static FileConfiguration playerLogsConfig;
    public static File playerData;
    public static FileConfiguration playerDataConfig;
    public static File factionData;
    public static FileConfiguration factionDataConfig;


    // open all plugin data files
    public static void init(){
        // create/open player log file
        settings = new File(CommandsPlus.getPlugin(CommandsPlus.class).getDataFolder(), "settings.yml");
        if (!settings.exists()) {
            settings.getParentFile().mkdirs();
            try {
                settings.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        settingsConfig = YamlConfiguration.loadConfiguration(settings);

        // create/open player log file
        playerLogs = new File(CommandsPlus.getPlugin(CommandsPlus.class).getDataFolder(), "player-logs.yml");
        if (!playerLogs.exists()) {
            playerLogs.getParentFile().mkdirs();
            try {
                playerLogs.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        playerLogsConfig = YamlConfiguration.loadConfiguration(playerLogs);

        // create/open player data file
        playerData = new File(CommandsPlus.getPlugin(CommandsPlus.class).getDataFolder(), "player-data.yml");
        if (!playerData.exists()) {
            playerData.getParentFile().mkdirs();
            try {
                playerData.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        playerDataConfig = YamlConfiguration.loadConfiguration(playerData);


        // create/open chunk faction data file
        factionData = new File(CommandsPlus.getPlugin(CommandsPlus.class).getDataFolder(), "faction-data.yml");
        if (!factionData.exists()) {
            factionData.getParentFile().mkdirs();
            try {
                factionData.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        factionDataConfig = YamlConfiguration.loadConfiguration(factionData);
    }

    public void load() {

    }


    // save all plugin data files
    public static void save(){
        try {
            settingsConfig.save(settings);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            playerLogsConfig.save(playerLogs);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            playerDataConfig.save(playerData);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            factionDataConfig.save(factionData);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String findUserID(String username){
        String uuid = "";
        ConfigurationSection users = Sentinel.playerDataConfig.getConfigurationSection("Users");
        try{
            for (String s : users.getKeys(false)) {

                if (users.get(s) != null) {
                    String usernameTemp = users.get(s + ".username").toString();
                    if (usernameTemp.equals(username)) {
                        uuid = s;
                    }
                }
            }
        }
        catch (Exception e){

        }

        return uuid;
    }



}
