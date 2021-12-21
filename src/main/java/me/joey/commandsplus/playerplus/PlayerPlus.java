package me.joey.commandsplus.playerplus;

import me.joey.commandsplus.sentinel.Sentinel;
import me.joey.commandsplus.vanillaplus.particles.ParticleData;
import me.joey.commandsplus.vanillaplus.particles.ParticleEffects;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerPlus {
    public static HashMap <Player, PlayerPlus> playerPlusHashMap = new HashMap<Player, PlayerPlus>();
    private Player player;
    private String faction;
    private String factionRank;
    private Rank rank;
    private ParticleData particle;
    private ParticleEffects trail;


    public PlayerPlus (Player player) {
        playerPlusHashMap.put(player, this);
        this.player = player;


        this.particle = new ParticleData(player.getUniqueId());
        this.trail = new ParticleEffects(player);

        if (particle.hasID()) {
            particle.endTask();
            particle.removeID();
        }

        if (player.getName().equals("aclownsquad")){
            trail.startPyrokineticParticles();
        }

        readData();
        writeData();
        Sentinel.save();
    }

    public void readData(){
        this.faction = Sentinel.playerDataConfig.getString("Users." + player.getUniqueId() + ".faction");
        this.factionRank = Sentinel.playerDataConfig.getString("Users." + player.getUniqueId() + ".factionRank");
        String rankTemp = Sentinel.playerDataConfig.getString("Users." + player.getUniqueId() + ".rank");
        if (rankTemp != null){
            this.rank = new Rank(rankTemp);
        }
        else{
            this.rank = new Rank();
        }

    }

    public void writeData(){
        Sentinel.playerDataConfig.set("Users." + player.getUniqueId() + ".username", player.getName());
        Sentinel.playerDataConfig.set("Users." + player.getUniqueId() + ".rank", this.getRank().getRankName());
        Sentinel.playerDataConfig.set("Users." + player.getUniqueId() + ".faction", this.getFaction());
        Sentinel.playerDataConfig.set("Users." + player.getUniqueId() + ".factionRank", this.getFactionRank());

    }

    public String getFaction(){
        return faction;
    }

    public String getFactionRank(){
        return factionRank;
    }

    public Rank getRank(){
        return rank;
    }

    public String getName(){
        return player.getName();
    }

    public static PlayerPlus getPlayerPlus(Player p){
        return PlayerPlus.playerPlusHashMap.get(p);
    }

    public void setFaction(String faction){
        this.faction = faction;
    }

    public void setFactionRank(String factionRank){
        this.factionRank = factionRank;
    }

    public void setRank(Rank rank){
        this.rank = rank;
    }




    // stat grabbing functions

    public int getPlayerKills(){
        return this.player.getStatistic(Statistic.PLAYER_KILLS);
    }

    public int getBlocksMined(){
        int total = 0;
        for (Material material : Material.values()){
            try {
                total += this.player.getStatistic(Statistic.MINE_BLOCK, material);
            }
            catch (Exception e){

            }
        }

        return total;

    }

    public int getDiamondsMined(){
        try {
            return (this.player.getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE) + this.player.getStatistic(Statistic.MINE_BLOCK, Material.DEEPSLATE_DIAMOND_ORE));
        }
        catch (Exception e) {
            return 0;
        }

    }

    public int getAncientDebrisMined(){
        try {
            return this.player.getStatistic(Statistic.MINE_BLOCK, Material.ANCIENT_DEBRIS);
        }
        catch (Exception e) {
            return 0;
        }

    }

}