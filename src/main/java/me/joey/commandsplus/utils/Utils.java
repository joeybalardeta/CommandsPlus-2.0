package me.joey.commandsplus.utils;

import me.joey.commandsplus.CommandsPlus;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Utils {

    public static void sendMessage(Player p, String message){
        try{
            p.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "Commands" + ChatColor.DARK_RED + "+" + ChatColor.WHITE + "] " + message);
        }
        catch (Exception e){
            CommandsPlus.getInstance().getLogger().log(Level.INFO, "Could not send message to " + p.getName() + "!");
        }

    }


    // get player from a username
    public static Player getPlayer(String name) {
        try {
            return Bukkit.getServer().getPlayer(name);
        } catch (Exception e) {
            return null;
        }

    }

    // fetch all nearby blocks within a certain radius (cube), returns a list of blocks
    public static List<Block> getNearbyBlocks(Location loc, int radius){
        List<Block> blocks = new ArrayList<Block>();

        for (int x = loc.getBlockX() - radius; x <= loc.getBlockX() + radius; x++) {
            for (int z = loc.getBlockZ() - radius; z <= loc.getBlockZ() + radius; z++) {
                blocks.add(loc.getWorld().getHighestBlockAt(x, z).getLocation().subtract(0, 0, 0).getBlock());
            }
        }

        return blocks;
    }

    // get a distance from one location to another (scalar value)
    public static double distanceToLoc(Location l1, Location l2) {
        double x = l2.getX() - l1.getX();
        double y = l2.getY() - l1.getY();
        double z = l2.getZ() - l1.getZ();

        return Math.sqrt(x*x + y*y + z*z);
    }

    public static double getPlayerDirectionFloat(Player player) {
        double rotation = player.getLocation().getYaw() - 180;
        if (rotation < 0) {
            rotation += 360.0;
        }
        return rotation;
    }

    public static void killPhantoms() {
        for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
                if (e instanceof Phantom) {
                    e.remove();
                }
            }
        }
    }

    // check if it is daytime, returns boolean
    public static boolean isDay(Server server, Player p) {
        String worldName = p.getServer().getName();
        long time = server.getWorld(worldName).getTime();

        if(time > 0 && time < 12300) {
            return true;
        } else {
            return false;
        }
    }

    // get server time in ticks, returns long
    public static long getTime(Server server, Player p) {
        String worldName = p.getWorld().getName();
        long time = server.getWorld(worldName).getTime();


        return time % 24000;

    }

    // get server time but formatted like normal time, returns String
    public static String getFormattedTime(Server server, Player p) {
        int time = (int) getTime(server, p);
        String timeAddOn = "am";
        int hours = (time / 1000) + 6;
        int minutes = ((time % 1000) / 167) * 10;

        if (hours > 11) {
            timeAddOn = "pm";
            if (hours > 12) {
                hours -= 12;
            }
        }

        if (hours > 12) {
            timeAddOn = "am";
            hours -= 12;
        }

        String formattedTime = "" + hours + ":" + String.format("%02d", minutes) + timeAddOn;
        return formattedTime;
    }

    // get the nearest entity in someone's sight
    public static Entity getNearestEntityInSight(Player p, int range, int scanRange){
        List<Entity> entities = p.getNearbyEntities(range,range,range);

        Iterator<Entity> iterator = entities.iterator();
        while(iterator.hasNext()){
            Entity next = iterator.next();
            if(!(next instanceof LivingEntity) || next == p){
                iterator.remove();
            }
        }

        List<Block> sight = p.getLineOfSight((Set<Material>) null, range);
        for(Block block : sight){
            if(block.getType() != Material.AIR && block.getType() != Material.CAVE_AIR && block.getType() != Material.WATER && block.getType() != Material.LAVA){
                break;
            }

            for(Entity entity : entities){
                if(entity.getLocation().distance(p.getEyeLocation()) <= range && (entity.getLocation().distance(block.getLocation()) <= scanRange || entity.getLocation().add(0,1,0).distance(block.getLocation()) <= scanRange)) {
                    return entity;
                }
            }
        }


        return null;
    }

    // play a sound for a player
    public static void playSound(Player p, String soundType) {
        if (soundType.equals("actionDenied")){
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0f);
        }

        else if (soundType.equals("actionAccepted")){

        }
    }

    // get amount of item in someone's inventory
    public static int getInvAmount(Player arg0, ItemStack arg1) {
        if (arg1 == null)
            return 0;
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = arg0.getInventory().getItem(i);
            if (slot == null || !slot.isSimilar(arg1))
                continue;
            amount += slot.getAmount();
        }
        return amount;
    }

    // get the head of the specified player
    @SuppressWarnings("deprecation")
    public static ItemStack getPlayerHead(String player) {
        boolean isNewVersion = Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");

        Material type = Material.matchMaterial(isNewVersion? "PLAYER_HEAD" : "SKULL_ITEM");
        ItemStack item = new ItemStack(type, 1);

        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwner(player);
        meta.setDisplayName(ChatColor.DARK_PURPLE + player + "'s " + ChatColor.WHITE + "head");

        item.setItemMeta(meta);

        return item;
    }

    public static void gravityWell(Player p) {
        new BukkitRunnable() {
            int count = 0;
            Location origin = p.getLocation();
            List<Block> blocks = getNearbyBlocks(origin, 20);
            List<FallingBlock> fBlocks = new ArrayList<>();


            public void run() {
                if (count == 200) {
                    cancel();

                    for (FallingBlock b : fBlocks) {
                        b.setGravity(true);
                        b.remove();
                        TNTPrimed tnt = b.getWorld().spawn(b.getLocation(), TNTPrimed.class);
                        tnt.setFuseTicks(100);
                    }

                    return;
                }

                for (int i = 0; i < 1; i++) {
                    Random rand = new Random();
                    Block block = blocks.get(rand.nextInt(blocks.size()) + 0);
                    FallingBlock fBlock = p.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData());
                    fBlock.setVelocity((fBlock.getLocation().toVector().subtract(origin.toVector()).multiply(-10).normalize()));
                    fBlock.setGravity(false);
                    fBlock.setDropItem(false);
                    fBlock.setHurtEntities(true);
                    block.setType(Material.AIR);
                    fBlocks.add(fBlock);
                }



                count++;
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("Commands_Plus"), 0, 0);
    }


    public static void amongus(Player p){
        BukkitScheduler scheduler = CommandsPlus.getInstance().getServer().getScheduler();

        long delay = 0L;
        long normalDelay = 6L;
        long longDelay = 18L;
        long shortDelay = 3L;

        Sound main = Sound.BLOCK_NOTE_BLOCK_PLING;
        Sound snare = Sound.BLOCK_NOTE_BLOCK_SNARE;

        scheduler.scheduleSyncDelayedTask(CommandsPlus.getInstance(), new Runnable() {
            public void run() {

                p.getWorld().playSound(p.getLocation(), main, 1.0f, 1.4f);
            }
        }, delay);

        delay += normalDelay;

        scheduler.scheduleSyncDelayedTask(CommandsPlus.getInstance(), new Runnable() {
            public void run() {

                p.getWorld().playSound(p.getLocation(), main, 1.0f, 1.7f);
            }
        }, delay);

        delay += normalDelay;

        scheduler.scheduleSyncDelayedTask(CommandsPlus.getInstance(), new Runnable() {
            public void run() {

                p.getWorld().playSound(p.getLocation(), main, 1.0f, 1.9f);
            }
        }, delay);

        delay += normalDelay;

        scheduler.scheduleSyncDelayedTask(CommandsPlus.getInstance(), new Runnable() {
            public void run() {

                p.getWorld().playSound(p.getLocation(), main, 1.0f, 2.0f);
            }
        }, delay);

        delay += normalDelay;

        scheduler.scheduleSyncDelayedTask(CommandsPlus.getInstance(), new Runnable() {
            public void run() {

                p.getWorld().playSound(p.getLocation(), main, 1.0f, 1.9f);
            }
        }, delay);

        delay += normalDelay;

        scheduler.scheduleSyncDelayedTask(CommandsPlus.getInstance(), new Runnable() {
            public void run() {

                p.getWorld().playSound(p.getLocation(), main, 1.0f, 1.7f);
            }
        }, delay);

        delay += normalDelay;

        scheduler.scheduleSyncDelayedTask(CommandsPlus.getInstance(), new Runnable() {
            public void run() {

                p.getWorld().playSound(p.getLocation(), main, 1.0f, 1.4f);
            }
        }, delay);

        delay += longDelay - 6;

        scheduler.scheduleSyncDelayedTask(CommandsPlus.getInstance(), new Runnable() {
            public void run() {

                p.getWorld().playSound(p.getLocation(), snare, 1.0f, 1.4f);
            }
        }, delay);

        delay += longDelay - 12;

        scheduler.scheduleSyncDelayedTask(CommandsPlus.getInstance(), new Runnable() {
            public void run() {

                p.getWorld().playSound(p.getLocation(), main, 1.0f, 1.25f);
            }
        }, delay);

        delay += shortDelay;

        scheduler.scheduleSyncDelayedTask(CommandsPlus.getInstance(), new Runnable() {
            public void run() {

                p.getWorld().playSound(p.getLocation(), main, 1.0f, 1.6f);
            }
        }, delay);

        delay += shortDelay;

        scheduler.scheduleSyncDelayedTask(CommandsPlus.getInstance(), new Runnable() {
            public void run() {

                p.getWorld().playSound(p.getLocation(), main, 1.0f, 1.4f);
            }
        }, delay);
    }
}
