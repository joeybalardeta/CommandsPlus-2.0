package me.joey.commandsplus.vanillaplus.particles;

import me.joey.commandsplus.CommandsPlus;
import me.joey.commandsplus.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.Random;


public class ParticleEffects {

    private int taskID;
    private final Player p;


    public ParticleEffects(Player p) {
        this.p = p;
    }


    // basic particle art functions
    public void startFlameParticles() {


        // check every 250ms for potion effects
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CommandsPlus.getPlugin(CommandsPlus.class), new Runnable() {
            double var = 0;
            Location loc, first, second;
            ParticleData particle = new ParticleData(p.getUniqueId());

            @Override
            public void run() {
                if (!particle.hasID()) {
                    particle.setID(taskID);
                }

                var += Math.PI / 16;

                loc = p.getLocation();
                first = loc.clone().add(Math.cos(var), Math.sin(var) + 1, Math.sin(var));
                second = loc.clone().add(Math.cos(var + Math.PI), Math.sin(var) + 1, Math.sin(var + Math.PI));

                p.getWorld().spawnParticle(Particle.FLAME, first, 0);
                p.getWorld().spawnParticle(Particle.FLAME, second, 0);
            }
        }, 0, 1L);
    }


    // Ambient particle effects (repeating)

    // talent particle functions

    // Pyrokinetic particles
    public void startPyrokineticParticles() {


        // check every 250ms for potion effects
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CommandsPlus.getPlugin(CommandsPlus.class), new Runnable() {
            double var = 0;
            Location loc, first;
            ParticleData particle = new ParticleData(p.getUniqueId());
            Random rand = new Random();
            double lookDirDouble = 0;
            double lookDirPi = 0;
            double rotation = 0;
            int distanceTemp = 0;
            double distance = 0;
            int heightTemp = 0;
            double height = 0;

            @Override
            public void run() {
                if (!particle.hasID()) {
                    particle.setID(taskID);
                }

                for (int i = 0; i < 1; i++){
                    lookDirDouble = Utils.getPlayerDirectionFloat(p);
                    lookDirPi = ((lookDirDouble * Math.PI) / 180) + (5 * Math.PI / 6);
                    var = rand.nextInt(240);
                    rotation = ((var * Math.PI) / 180.0) - Math.PI;
                    distanceTemp = rand.nextInt(100) + 50;
                    distance = distanceTemp / 100.0;
                    heightTemp = rand.nextInt(200) + 20;
                    height = heightTemp / 100.0;


                    loc = p.getLocation();
                    first = loc.clone().add(Math.cos(rotation + lookDirPi) * distance, height, Math.sin(rotation + lookDirPi) * distance);

                    p.getWorld().spawnParticle(Particle.FLAME, first, 0);
                }




            }
        }, 0, 5L);
    }


    // Pyrokinetic particles
    public void startHydrokineticParticles() {


        // check every 250ms for potion effects
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CommandsPlus.getPlugin(CommandsPlus.class), new Runnable() {
            double var = 0;
            Location loc, first;
            ParticleData particle = new ParticleData(p.getUniqueId());
            Random rand = new Random();
            double lookDirDouble = 0;
            double lookDirPi = 0;
            double rotation = 0;
            int distanceTemp = 0;
            double distance = 0;
            int heightTemp = 0;
            double height = 0;

            @Override
            public void run() {
                if (!particle.hasID()) {
                    particle.setID(taskID);
                }


                lookDirDouble = Utils.getPlayerDirectionFloat(p);
                lookDirPi = ((lookDirDouble * Math.PI) / 180) + (5 * Math.PI / 6);
                var = rand.nextInt(240);
                rotation = ((var * Math.PI) / 180.0) - Math.PI;
                distanceTemp = rand.nextInt(100) + 50;
                distance = distanceTemp / 100.0;
                heightTemp = rand.nextInt(100) + 150;
                height = heightTemp / 100.0;


                loc = p.getLocation();
                first = loc.clone().add(Math.cos(rotation + lookDirPi) * distance, height, Math.sin(rotation + lookDirPi) * distance);

                p.getWorld().spawnParticle(Particle.FALLING_WATER, first, 0);


            }
        }, 0, 5L);
    }

    // Frostbender particles
    public void startFrostbenderParticles() {


        // check every 250ms for potion effects
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CommandsPlus.getPlugin(CommandsPlus.class), new Runnable() {
            double var = 0;
            Location loc, first; //, second, third;
            ParticleData particle = new ParticleData(p.getUniqueId());
            int r = 0;
            int g = 153;
            int b = 255;


            @Override
            public void run() {
                if (!particle.hasID()) {
                    particle.setID(taskID);
                }

                var += Math.PI / 16;


                loc = p.getLocation();
                first = loc.clone().add(Math.cos(var), 0.0, Math.sin(var));
                //second = loc.clone().add(Math.cos(var + (Math.PI) * (2.0 / 3)), 1.0, Math.sin(var + (Math.PI) * (2.0 / 3)));
                //third = loc.clone().add(Math.cos(var + (Math.PI) * (4.0 / 3)), 2.0, Math.sin(var + (Math.PI) * (4.0 / 3)));

                // create particle color variable
                Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(r, g, b), 1);


                p.getWorld().spawnParticle(Particle.REDSTONE, first, 0, 0, 0, 0, dust);
                //p.getWorld().spawnParticle(Particle.FLAME, second, 0);
                //p.getWorld().spawnParticle(Particle.FLAME, third, 0);
            }
        }, 0, 1L);
    }

    // Shaman particles
    public void startShamanParticles() {


        // check every 250ms for potion effects
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CommandsPlus.getPlugin(CommandsPlus.class), new Runnable() {
            double var = 0;
            Location loc, first;
            ParticleData particle = new ParticleData(p.getUniqueId());
            Random rand = new Random();
            double lookDirDouble = 0;
            double lookDirPi = 0;
            double rotation = 0;
            int distanceTemp = 0;
            double distance = 0;
            int heightTemp = 0;
            double height = 0;

            @Override
            public void run() {
                if (!particle.hasID()) {
                    particle.setID(taskID);
                }


                lookDirDouble = Utils.getPlayerDirectionFloat(p);
                lookDirPi = ((lookDirDouble * Math.PI) / 180) + (5 * Math.PI / 6);
                var = rand.nextInt(240);
                rotation = ((var * Math.PI) / 180.0) - Math.PI;
                distanceTemp = rand.nextInt(50) + 75;
                distance = distanceTemp / 100.0;
                heightTemp = rand.nextInt(50) + 200;
                height = heightTemp / 100.0;


                loc = p.getLocation();
                first = loc.clone().add(Math.cos(rotation + lookDirPi) * distance, height, Math.sin(rotation + lookDirPi) * distance);

                p.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, first, 0, 2 * (Math.cos(rotation + lookDirPi) * (distance + 0.25)), -height, 2 * (Math.sin(rotation + lookDirPi) * (distance + 0.25)));


            }
        }, 0, 2L);
    }

    // Shaman particles
    public void startEnderianParticles() {


        // check every 250ms for potion effects
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CommandsPlus.getPlugin(CommandsPlus.class), new Runnable() {
            double var = 0;
            Location loc, first;
            ParticleData particle = new ParticleData(p.getUniqueId());
            Random rand = new Random();
            double lookDirDouble = 0;
            double lookDirPi = 0;
            double rotation = 0;
            int distanceTemp = 0;
            double distance = 0;
            int heightTemp = 0;
            double height = 0;

            @Override
            public void run() {
                if (!particle.hasID()) {
                    particle.setID(taskID);
                }


                lookDirDouble = Utils.getPlayerDirectionFloat(p);
                lookDirPi = ((lookDirDouble * Math.PI) / 180) + (5 * Math.PI / 6);
                var = rand.nextInt(240);
                rotation = ((var * Math.PI) / 180.0) - Math.PI;
                distanceTemp = rand.nextInt(80);
                distance = distanceTemp / 100.0;
                heightTemp = rand.nextInt(150) + 20;
                height = heightTemp / 100.0;


                loc = p.getLocation();
                first = loc.clone().add(Math.cos(rotation + lookDirPi) * distance, height, Math.sin(rotation + lookDirPi) * distance);

                p.getWorld().spawnParticle(Particle.PORTAL, first, 0, Math.cos(rotation + lookDirPi) * distance, 0, Math.sin(rotation + lookDirPi) * distance);


            }
        }, 0, 2L);
    }


    // Wing particles
    public void startWingParticles() {


        // check every 250ms for potion effects
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CommandsPlus.getPlugin(CommandsPlus.class), new Runnable() {
            double var = 1;
            Location loc, first; //, second, third;
            ParticleData particle = new ParticleData(p.getUniqueId());


            @Override
            public void run() {
                if (!particle.hasID()) {
                    particle.setID(taskID);
                }

                double lookDirDouble = Utils.getPlayerDirectionFloat(p);
                double lookDirPi = ((lookDirDouble * Math.PI) / 180) + Math.PI / 2;

                loc = p.getLocation();
                for (int i = -10; i < 10; i++) {
                    for (int j = 0; j < 1; j++) {
                        first = loc.clone().add((Math.cos(lookDirPi + i / 5.0) * var), (i / 10.0) + 1, (Math.sin(lookDirPi + i / 5.0) * var));
                        p.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, first, 0);
                        first = loc.clone().add((Math.cos(lookDirPi - i / 5.0) * var), (i / 10.0) + 1, (Math.sin(lookDirPi - i / 5.0) * var));
                        p.getWorld().spawnParticle(Particle.FLAME, first, 0);
                    }
                }
            }
        }, 0, 1L);
    }

}
