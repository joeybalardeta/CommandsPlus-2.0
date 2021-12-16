package me.joey.commandsplus.playerplus;


import net.md_5.bungee.api.ChatColor;

public class Rank {
    private String rankName;
    private ChatColor rankColor;

    public Rank (){
        rankName = "Member";
        this.setColor();
    }

    public Rank (String name){
        rankName = name;
        this.setColor();
    }


    public void setColor(){
        if (this.rankName.equalsIgnoreCase("owner")){
            this.rankColor = ChatColor.DARK_RED;
            return;
        }
        if (this.rankName.equalsIgnoreCase("sheriff")){
            this.rankColor = ChatColor.GOLD;
            return;
        }
        if (this.rankName.equalsIgnoreCase("cobble man")){
            this.rankColor = ChatColor.DARK_GRAY;
            return;
        }

        this.rankColor = ChatColor.GREEN;
    }

    public String getRankName(){
        return this.rankName;
    }

    public ChatColor getRankColor(){
        return this.rankColor;
    }

    public String toString(){
        return rankColor + rankName;
    }
}
