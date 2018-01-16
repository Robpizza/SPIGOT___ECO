package me.robpizza.econutilsvaulthook;

import me.robpizza.ecoutils.utils.MoneyAPI;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Economy_EconUtils extends AbstractEconomy {
    private static final Logger log = Bukkit.getLogger();

    private final String name = "Economy_EconUtils";
    private Plugin plugin = null;
    private MoneyAPI moneyAPI = null;

    public Economy_EconUtils(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(new EconomyServerListener(this), plugin);

        //Load Plugin in case it was loaded before
        if(moneyAPI == null) {
            Plugin econ = plugin.getServer().getPluginManager().getPlugin("EconUtils");
            if(econ != null && econ.isEnabled()) {
                moneyAPI = MoneyAPI.getInstance();
                log.info(String.format("[%s][Economy] %s hooked.", plugin.getDescription().getName(), name));
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isEnabled() {
        return moneyAPI != null;
    }

    @Override
    public double getBalance(String playerName) {
        return moneyAPI.getBalance(Bukkit.getPlayer(playerName));
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        double balance = moneyAPI.getBalance(Bukkit.getPlayer(playerName));
        if (amount < 0) {
            return new EconomyResponse(0, balance, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");
        } else if (balance - amount < 0) {
            return new EconomyResponse(0, balance, EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
        }
        moneyAPI.takeMoney(Bukkit.getPlayer(playerName), amount);
        return new EconomyResponse(amount, moneyAPI.getBalance(Bukkit.getPlayer(playerName)), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        double balance = moneyAPI.getBalance(Bukkit.getPlayer(playerName));
        if (amount < 0) {
            return new EconomyResponse(0, balance, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        }
        moneyAPI.giveMoney(Bukkit.getPlayer(playerName), amount);
        return new EconomyResponse(amount, moneyAPI.getBalance(Bukkit.getPlayer(playerName)), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public String currencyNamePlural() {
        return "$";
    }

    @Override
    public String currencyNameSingular() {
        return "$";
    }



    public class EconomyServerListener implements Listener {
        Economy_EconUtils economy = null;

        public EconomyServerListener(Economy_EconUtils economy) {
            this.economy = economy;
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPluginEnable(PluginEnableEvent e) {
            if(economy.moneyAPI == null) {
                Plugin eco = e.getPlugin();

                if(eco.getDescription().getName().equals("EcoUtils")) {
                    economy.moneyAPI = MoneyAPI.getInstance();
                    log.info(String.format("[%s][Economy] %s hooked.", plugin.getDescription().getName(), economy.name));
                }
            }
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPluginDisable(PluginDisableEvent event) {
            if (economy.moneyAPI != null) {
                if (event.getPlugin().getDescription().getName().equals("EcoUtils")) {
                    economy.moneyAPI = null;
                    log.info(String.format("[%s][Economy] %s unhooked.", plugin.getDescription().getName(), economy.name));
                }
            }
        }

    }

    @Override
    public String format(double amount) {
        amount = Math.ceil(amount);
        if (amount == 1) {
            return String.format("%d %s", (int)amount, currencyNameSingular());
        } else {
            return String.format("%d %s", (int)amount, currencyNamePlural());
        }
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EcoUtils does not support bank accounts!");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EcoUtils does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EcoUtils does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EcoUtils does not support bank accounts!");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EcoUtils does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EcoUtils does not support bank accounts!");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EcoUtils does not support bank accounts!");
    }

    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "EcoUtils does not support bank accounts!");
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<String>();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public boolean hasAccount(String playerName) {
        return moneyAPI.playerFileExists(Bukkit.getPlayer(playerName));
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        if (!hasAccount(playerName)) {
            moneyAPI.setMoney(Bukkit.getPlayer(playerName), 0.0);
            return true;
        }
        return false;
    }

    @Override
    public int fractionalDigits() {
        return -1;
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }
}
