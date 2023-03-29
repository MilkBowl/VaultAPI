package net.milkbowl.vault.economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

public class IdentityEconomyWrapper {
    private final IdentityEconomy economy;

    public IdentityEconomyWrapper(IdentityEconomy economy){
        this.economy = economy;
    }

    /**
     * Will register both IdentityEconomy and legacy Economy to Vault
     * @return true registered successfully, false already registered
     */
    public boolean registerProviders(){
        ServicesManager manager = Bukkit.getServicesManager();
        if (manager.isProvidedFor(IdentityEconomy.class))
            return false;
        if (manager.isProvidedFor(Economy.class))
            return false;
        manager.register(IdentityEconomy.class, economy,
                Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        manager.register(Economy.class, economy,
                Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        return true;
    }
}
