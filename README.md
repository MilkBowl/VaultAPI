# VaultAPI - Abstraction Library API for Bukkit Plugins - [![](https://travis-ci.org/MilkBowl/VaultAPI.svg?branch=master)](https://travis-ci.org/MilkBowl/VaultAPI)

How to include the API with Maven: 
```xml
<dependencies>
    <dependency>
        <groupId>net.milkbowl.vault</groupId>
        <artifactId>vaultapi</artifactId>
        <version>2.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

**Note**: The VaultAPI version has 2 numbers (major.minor), unlike Vault, which has 3. The 2 numbers in the VaultAPI will always correspond to the 2 beginning numbers in a Vault version to make it clear what versions your plugin will for sure work with.

## Why VaultAPI2?
Multiple developers have been waiting for a pull request which
would increase performance (by using UUIDs) while at the same time,
I and a fellow creatorfromhell have been waiting for multicurrency support.
There's a bunch of multicurrency plugins and none of them have a bridge
that allows them to communicate with other plugins without having a hell
of a lot of code to maintain...
I (anjoismysign) took the iniciative to continue a what seems a dead repository.
I am open for new team members as long as the mission is to keep supporting
'legacy' plugins while improving the API.

## What are legacy plugins?
They are plugins which were designed with the old VaultAPI in mind.
The idea is to not hardfork if not needed 👍🏻

## How to implement VaultAPI2?
Here two examples:

```java
    /**
     * Checks if the economy provider is available.
     * If using MultiEconomy, it will return the default economy provider.
     *
     * @return The economy provider, or null if not available.
     */
    public static Economy hasEconomyProvider() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        RegisteredServiceProvider<Economy> economyServiceProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyServiceProvider != null)
            return economyServiceProvider.getProvider();
        RegisteredServiceProvider<MultiEconomy> multiEconomyServiceProvider =
                Bukkit.getServer().getServicesManager().getRegistration(MultiEconomy.class);
        if (multiEconomyServiceProvider == null)
            return null;
        return multiEconomyServiceProvider.getProvider().getDefault();
    }

    /**
     * @return True if the there's no economy provider registered and no multi-economy provider registered.
     */
    public static boolean canRegisterEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> economyServiceProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyServiceProvider != null)
            return false;
        RegisteredServiceProvider<MultiEconomy> multiEconomyServiceProvider =
                Bukkit.getServer().getServicesManager().getRegistration(MultiEconomy.class);
        return multiEconomyServiceProvider == null;
    }
```

## Why Vault?
I have no preference which library suits your plugin and development efforts
best.  Really, I thought a central suite (rather...Vault) of solutions was the
the proper avenue than focusing on a single category of plugin.  That's where
the idea for Vault came into play.

So, what features do I _think_ you'll like the most?

 * No need to include my source code in your plugin
 * Broad range of supported plugins
 * Choice!

## License
Copyright (C) 2011-2018 Morgan Humes <morgan@lanaddict.com>

Vault is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Vault is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with Vault.  If not, see <http://www.gnu.org/licenses/>.

## Building
VaultAPI comes with all libraries needed to build from the current branch.

## Implementing Vault
Implementing Vault is quite simple. It requires getting the Economy, Permission, or Chat service from the Bukkit ServiceManager. See the example below:

```java
package com.example.plugin;

import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            log.info("Only players are supported for this Example Plugin, but you should not do this!!!");
            return true;
        }

        Player player = (Player) sender;

        if (command.getLabel().equals("test-economy")) {
            // Lets give the player 1.05 currency (note that SOME economic plugins require rounding!)
            sender.sendMessage(String.format("You have %s", econ.format(econ.getBalance(player.getUniqueId()))));
            EconomyResponse r = econ.depositPlayer(player.getUniqueId(), 1.05);
            if (r.transactionSuccess()) {
                sender.sendMessage(String.format("You were given %s and now have %s", econ.format(r.amount), econ.format(r.balance)));
            } else {
                sender.sendMessage(String.format("An error occured: %s", r.errorMessage));
            }
            return true;
        } else if (command.getLabel().equals("test-permission")) {
            // Lets test if user has the node "example.plugin.awesome" to determine if they are awesome or just suck
            if (perms.has(player, "example.plugin.awesome")) {
                sender.sendMessage("You are awesome!");
            } else {
                sender.sendMessage("You suck!");
            }
            return true;
        } else {
            return false;
        }
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }
    
    public static Chat getChat() {
        return chat;
    }
}
```
