package me.joey.commandsplus.event;

import me.joey.commandsplus.CommandsPlus;

public class EventManager {
    private CommandsPlus instance;

    public void init(){
        instance = CommandsPlus.getInstance();

        instance.getServer().getPluginManager().registerEvents(new PlayerEvent(), instance);
    }

}
