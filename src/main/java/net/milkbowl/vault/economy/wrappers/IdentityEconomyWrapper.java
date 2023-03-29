package net.milkbowl.vault.economy.wrappers;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.IdentityEconomy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

public class IdentityEconomyWrapper {
    private final IdentityEconomy economy;

    public IdentityEconomyWrapper(IdentityEconomy economy){
        this.economy = economy;
    }

    /**
     * Will register IdentityEconomy and legacy Economy to Vault
     * @param force if true, will override existing Economy and IdentityEconomy providers
     * @return true if registered successfully, false if already registered
     */
    public boolean registerProviders(boolean force){
        ServicesManager manager = Bukkit.getServicesManager();
        if (!force && manager.isProvidedFor(IdentityEconomy.class))
            return false;
        if (!force && manager.isProvidedFor(Economy.class))
            return false;
        manager.register(IdentityEconomy.class, economy,
                Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        manager.register(Economy.class, economy,
                Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        return true;
    }

    /**
     * Will register IdentityEconomy and legacy Economy to Vault
     * If any provider is already registered, it won't proceed
     * @return true if registered successfully, false if already registered
     */
    public boolean registerProviders(){
        return registerProviders(false);
    }
}
