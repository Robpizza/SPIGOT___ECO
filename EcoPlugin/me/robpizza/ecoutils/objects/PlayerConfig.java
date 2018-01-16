package me.robpizza.ecoutils.objects;

import me.robpizza.ecoutils.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerConfig {

    Plugin p = Main.instance;
    UUID uuid;
    File pData;
    FileConfiguration pDataConfig;

    public PlayerConfig(UUID uuid) {
        this.uuid = uuid;

        pData = new File(p.getDataFolder() + "/Players/" + uuid + ".yml");
        pDataConfig = YamlConfiguration.loadConfiguration(pData);
    }

    public void createPlayerConfig() {
        if(!pData.exists()) {
            try {
                pData.createNewFile();
                pDataConfig.set("Money", 0);
                savePlayerConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public FileConfiguration getPlayerConfig() {
        return pDataConfig;
    }

    public void savePlayerConfig() {
        try {
            getPlayerConfig().save(pData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getPlayerConfigFile() {
        return pData;
    }

    public double getMoney() {
        return pDataConfig.getInt("Money");
    }

    public boolean hasMoney(double amount) {
        if(pDataConfig.getInt("Money") >= amount) {
            return true;
        } else {
            return false;
        }
    }

    public void setMoney(double amount) {
        pDataConfig.set("Money", amount);
        savePlayerConfig();
    }

    public void takeMoney(double amount) {
        pDataConfig.set("Money", getMoney() - amount);
        savePlayerConfig();
    }

    public void giveMoney(double amount) {
        pDataConfig.set("Money", getMoney() + amount);
        savePlayerConfig();
    }

    public void resetPlayer() {
        pDataConfig.set("Money", 0);
        savePlayerConfig();
    }
}
