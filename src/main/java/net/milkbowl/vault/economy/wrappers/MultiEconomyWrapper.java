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
     * @return true if registered successfully, false if already registered
     */
    public boolean registerProviders(){
        ServicesManager manager = Bukkit.getServicesManager();
        if (manager.isProvidedFor(MultiEconomy.class))
            return false;
        if (manager.isProvidedFor(IdentityEconomy.class))
            return false;
        if (manager.isProvidedFor(Economy.class))
            return false;
        manager.register(MultiEconomy.class, economy,
                Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        manager.register(IdentityEconomy.class, economy.getDefault(),
                Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        manager.register(Economy.class, economy.getDefault(),
                Bukkit.getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        return true;
    }
}
