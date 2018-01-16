package me.robpizza.ecoutils.utils;

import com.sun.org.apache.xpath.internal.SourceTree;
import me.robpizza.ecoutils.Main;
import me.robpizza.ecoutils.objects.PlayerConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MoneyAPI {
    private Main plugin;
    private static MoneyAPI instance;

    public MoneyAPI(Main plugin) {
        this.plugin = plugin;
        instance = this;
    }
    public static MoneyAPI getInstance() {
        return instance;
    }

    private Player player;
    private PlayerConfig config;

    public File getMoneyFile(Player player) {
        player = player;
        if(playerFileExists(player)) {
            config = new PlayerConfig(player.getUniqueId());
            return config.getPlayerConfigFile();
        } else {
            return null;
        }
    }

    public FileConfiguration getMoneyConfig(Player player) {
        player = player;
        if(playerFileExists(player)) {
            config = new PlayerConfig(player.getUniqueId());
            return config.getPlayerConfig();
        } else {
            return null;
        }
    }

    public boolean playerFileExists(Player player) {
        player = player;
        if(new PlayerConfig(player.getUniqueId()).getPlayerConfigFile() != null && new PlayerConfig(player.getUniqueId()).getPlayerConfig() != null) {
            return true;
        } else {
            return false;
        }
    }

    public double getBalance(Player player) {
        player = player;
        if(playerFileExists(player)) {
            config = new PlayerConfig(player.getUniqueId());
            return config.getMoney();
        } else {
            return 0.0;
        }
    }

    public String getBalanceString(double amount) {
        return "$ " + amount;
    }



    public boolean hasMoney(Player player, double amount) {
        player = player;
        if(playerFileExists(player)) {
            config = new PlayerConfig(player.getUniqueId());
            return config.hasMoney(amount);
        } else {
            return false;
        }
    }

    public void giveMoney(Player player, double amount) {
        player = player;
        if(playerFileExists(player)) {
            config = new PlayerConfig(player.getUniqueId());
            config.giveMoney(amount);
        } else {
            System.out.println("[MoneyAPI => giveMoney();");
            System.out.println(player + " not found.");
        }
    }

    public void takeMoney(Player player, double amount) {
        player = player;
        if(playerFileExists(player)) {
            config = new PlayerConfig(player.getUniqueId());
            config.takeMoney(amount);
        } else {
            System.out.println("[MoneyAPI => takeMoney();");
            System.out.println(player + " not found.");
        }
    }

    public void setMoney(Player player, double amount) {
        player = player;
        if(playerFileExists(player)) {
            config = new PlayerConfig(player.getUniqueId());
            config.setMoney(amount);
        } else {
            System.out.println("[MoneyAPI => setMoney();");
            System.out.println(player + " not found.");
        }
    }





}
