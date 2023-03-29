package net.milkbowl.vault.economy.wrappers;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.IdentityEconomy;
import net.milkbowl.vault.economy.MultiEconomy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

public class MultiEconomyWrapper {
    private final MultiEconomy economy;

    public MultiEconomyWrapper(MultiEconomy economy){
        this.economy = economy;
    }

    /**
     * Will register MultiEconomy, IdentityEconomy and legacy Economy to Vault
     * @param force if true, will override existing Economy, IdentityEconomy and MultiEconomy providers
     * @return true if registered successfully, false if already registered
     */
    public boolean registerProviders(boolean force){
        ServicesManager manager = Bukkit.getServicesManager();
        if (!force && manager.isProvidedFor(MultiEconomy.class))
            return false;
        if (!force && manager.isProvidedFor(IdentityEconomy.class))
            return false;
        if (!force && manager.isProvidedFor(Economy.class))
            return false;
        manager.register(MultiEconomy.class, economy,
                Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        manager.register(IdentityEconomy.class, economy.getDefault(),
                Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        manager.register(Economy.class, economy.getDefault(),
                Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        return true;
    }
    /**
     * Will register MultiEconomy, IdentityEconomy and legacy Economy to Vault
     * If any provider is already registered, it won't proceed
     * @return true if registered successfully, false if already registered
     */
    public boolean registerProviders(){
        return registerProviders(false);
    }
}
