package me.robpizza.econutilsvaulthook;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static Logger log;
    private ServicesManager sm;

    @Override
    public void onEnable() {

        if(Bukkit.getServer().getPluginManager().getPlugin("EcoUtils") != null && Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
            if(Bukkit.getServer().getPluginManager().getPlugin("EcoUtils").isEnabled()) {
                Bukkit.getServicesManager().register(Economy.class, new Economy_EconUtils(this), this, ServicePriority.Normal);
            }
        } else {
            Bukkit.getLogger().log(Level.WARNING, "Missing dependencies! Please make sure Vault and EcoUtils are installed!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {

    }
}
