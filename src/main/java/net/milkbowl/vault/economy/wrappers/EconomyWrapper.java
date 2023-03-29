package net.milkbowl.vault.economy.wrappers;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.IdentityEconomy;
import net.milkbowl.vault.economy.LegacyEconomy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

public class EconomyWrapper {
    private final Economy economy;
    public EconomyWrapper(Economy economy){
        this.economy = economy;
    }

    /**
     * Will convert the Economy to a new IdentityEconomy
     * which IdentityEconomy#isLegacy() returns true.
     * Methods regarding UUID will fail fast.
     * @return a legacy economy
     */
    public LegacyEconomy legacy(){
        return new LegacyEconomy(economy);
    }

    /**
     * Will register IdentityEconomy and legacy Economy to Vault
     * @param force if true, will override existing Economy and IdentityEconomy providers
     * @return true if registered successfully, false if already registered
     */
    public boolean registerProviders(boolean force){
        ServicesManager manager = Bukkit.getServicesManager();
        if (!force){
            if (manager.isProvidedFor(IdentityEconomy.class))
                return false;
            if (manager.isProvidedFor(Economy.class))
                return false;
        }
        LegacyEconomy legacy = legacy();
        manager.register(IdentityEconomy.class, legacy,
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
