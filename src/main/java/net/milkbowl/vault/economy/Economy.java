/* This file is part of Vault.

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
 */

package net.milkbowl.vault.economy;

import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The main economy API
 *
 */
public interface Economy {

    /**
     * Checks if economy method/implementation is enabled.
     *
     * @return Success or Failure
     */
    public boolean isEnabled();

    /**
     * Gets name of economy method or implementation
     * in case of being used in multi-currency environment.
     *
     * @return Name of Economy Method
     */
    public String getName();

    /**
     * Returns true if the given implementation supports banks.
     * 
     * @return true if the implementation supports banks
     */
    public boolean hasBankSupport();

    /*
     * Currency-related methods follow.
     */

    /**
     * Some economy plugins round off after a certain number of digits. This
     * function returns the number of digits the plugin keeps or -1 if no rounding
     * occurs.
     * 
     * @return number of digits after the decimal point kept
     */
    public int fractionalDigits();

    /**
     * Format amount into a human readable String This provides translation into
     * economy specific formatting to improve consistency between plugins.
     *
     * @param amount to format
     * @return Human readable string describing amount
     */
    public String format(double amount);

    /**
     * Returns the name of the currency in plural form. If the economy being used
     * does not support currency names then an empty string will be returned.
     * 
     * @return name of the currency (plural)
     */
    public String currencyNamePlural();

    /**
     * Returns the name of the currency in singular form. If the economy being used
     * does not support currency names then an empty string will be returned.
     * 
     * @return name of the currency (singular)
     */
    public String currencyNameSingular();

    /*
     * Account-related methods follow.
     */

    /**
     * Attempts to create a account for the given uuid
     * 
     * @param uuid associated with the account
     * @param name associated with the account.
     * @return if the account creation was successful
     */
    public boolean createAccount(UUID uuid, String name);

    /**
     * Attempts to create an account for the given UUID on the specified world
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this then
     * false will always be returned.
     * 
     * @param uuid      associated with the account
     * @param name      associated with the account.
     * @param worldName String name of the world
     * @return if the account creation was successful
     */
    public boolean createAccount(UUID uuid, String name, String worldName);

    /**
     * Returns a map that represents all of the UUIDs which have accounts in the
     * plugin, as well as their last-known-name. This is used for Vault's economy
     * converter and should be given every account available.
     * 
     * @return a {@link Map} composed of the accounts keyed by their UUID, along
     *         with their associated last-known-name.
     */
    public Map<UUID, String> getUUIDNameMap();

    /**
     * Gets the last known name of an account owned by the given UUID. Required for
     * messages to be more human-readable than UUIDs alone can provide.
     * 
     * @param uuid UUID to look up.
     */
    public String getAccountName(UUID uuid);

    /**
     * Checks if this uuid has an account yet
     * 
     * @param uuid to check
     * @return if the uuid has an account
     */
    public boolean hasAccount(UUID uuid);

    /**
     * Checks if this player has an account yet
     * @param player to check
     */
    public default boolean hasAccount(OfflinePlayer player){
        return hasAccount(player.getUniqueId());
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #hasAccount(OfflinePlayer)} instead.
     */
    @Deprecated
    public boolean hasAccount(String playerName);

    /**
     * Checks if this uuid has an account yet on the given world
     * 
     * @param uuid      to check
     * @param worldName world-specific account
     * @return if the uuid has an account
     */
    public boolean hasAccount(UUID uuid, String worldName);

    /**
     * Checks if this player has an account yet on the given world
     * @param player to check
     * @param worldName world-specific account
     */
    public default boolean hasAccount(OfflinePlayer player, String worldName){
        return hasAccount(player.getUniqueId(), worldName);
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #hasAccount(OfflinePlayer, String)} instead.
     */
    @Deprecated
    public boolean hasAccount(String playerName, String worldName);

    /**
     * A method which changes the name associated with the given UUID in the
     * Map<UUID, String> received from {@link #getUUIDNameMap()}.
     * 
     * @param uuid which is having a name change.
     * @param name name that will be associated with the UUID in the 
     *             Map<UUID, String> map.
     * @return true if the name change is successful.
     */
    public boolean renameAccount(UUID uuid, String name);

    /**
     * Gets balance of a UUID
     * 
     * @param uuid of the account to get a balance for
     * @return Amount currently held in account associated with the given UUID
     */
    public double getBalance(UUID uuid);

    /**
     * Gets balance of a player
     * @param player to check
     */
    public default double getBalance(OfflinePlayer player){
        return getBalance(player.getUniqueId());
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #getBalance(OfflinePlayer)} instead.
     */
    @Deprecated
    public double getBalance(String playerName);

    /**
     * Gets balance of a UUID on the specified world. IMPLEMENTATION SPECIFIC - if
     * an economy plugin does not support this the global balance will be returned.
     * 
     * @param uuid  of the account to get a balance for
     * @param world name of the world
     * @return Amount currently held in account associated with the given UUID
     */
    public double getBalance(UUID uuid, String world);

    /**
     * Gets balance of a player on the specified world. IMPLEMENTATION SPECIFIC - if
     * an economy plugin does not support this the global balance will be returned.
     * @param player to check
     * @param world name of the world
     */
    public default double getBalance(OfflinePlayer player, String world){
        return getBalance(player.getUniqueId(), world);
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #getBalance(OfflinePlayer, String)} instead.
     */
    @Deprecated
    public double getBalance(String playerName, String world);

    /**
     * Checks if the account associated with the given UUID has the amount - DO NOT
     * USE NEGATIVE AMOUNTS
     * 
     * @param uuid   to check
     * @param amount to check for
     * @return True if <b>UUID</b> has <b>amount</b>, False else wise
     */
    public boolean has(UUID uuid, double amount);

    /**
     * Checks if the account associated with the given player has the amount - DO NOT
     * USE NEGATIVE AMOUNTS
     * @param player to check
     * @param amount to check for
     */
    public default boolean has(OfflinePlayer player, double amount){
        return has(player.getUniqueId(), amount);
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #has(OfflinePlayer, double)} instead.
     */
    @Deprecated
    public boolean has(String playerName, double amount);

    /**
     * Checks if the account associated with the given UUID has the amount in the
     * given world - DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an
     * economy plugin does not support this the global balance will be returned.
     * 
     * @param uuid      to check
     * @param worldName to check with
     * @param amount    to check for
     * @return True if <b>UUID</b> has <b>amount</b> in the given <b>world</b>,
     *         False else wise
     */
    public boolean has(UUID uuid, String worldName, double amount);

    /**
     * Checks if the account associated with the given player has the amount in the
     * given world - DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an
     * economy plugin does not support this the global balance will be returned.
     * @param player to check
     * @param worldName to check with
     * @param amount to check for
     */
    public default boolean has(OfflinePlayer player, String worldName, double amount){
        return has(player.getUniqueId(), worldName, amount);
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #has(OfflinePlayer, String, double)} instead.
     */
    @Deprecated
    public boolean has(String playerName, String worldName, double amount);

    /**
     * Withdraw an amount from an account associated with a UUID - DO NOT USE
     * NEGATIVE AMOUNTS
     * 
     * @param uuid   to withdraw from
     * @param amount Amount to withdraw
     * @return Detailed response of transaction
     */
    public EconomyResponse withdraw(UUID uuid, double amount);

    /**
     * Withdraw an amount from an account associated with a player - DO NOT USE
     * NEGATIVE AMOUNTS
     * @param player to withdraw from
     * @param amount Amount to withdraw
     */
    public default EconomyResponse withdrawPlayer(OfflinePlayer player, double amount){
        return withdraw(player.getUniqueId(), amount);
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #withdrawPlayer(OfflinePlayer, double)} instead.
     */
    @Deprecated
    public EconomyResponse withdrawPlayer(String playerName, double amount);

    /**
     * Withdraw an amount from an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     * 
     * @param uuid      to withdraw from
     * @param worldName - name of the world
     * @param amount    Amount to withdraw
     * @return Detailed response of transaction
     */
    public EconomyResponse withdraw(UUID uuid, String worldName, double amount);

    /**
     * Withdraw an amount from an account associated with a player on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     * @param player to withdraw from
     * @param worldName - name of the world
     * @param amount Amount to withdraw
     */
    public default EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount){
        return withdraw(player.getUniqueId(), worldName, amount);
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #withdrawPlayer(OfflinePlayer, String, double)} instead.
     */
    @Deprecated
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount);

    /**
     * Deposit an amount to an account associated with the given UUID - DO NOT USE
     * NEGATIVE AMOUNTS
     * 
     * @param uuid   to deposit to
     * @param amount Amount to deposit
     * @return Detailed response of transaction
     */
    public EconomyResponse deposit(UUID uuid, double amount);

    /**
     * Deposit an amount to an account associated with the given player - DO NOT USE
     * NEGATIVE AMOUNTS
     * @param player to deposit to
     * @param amount Amount to deposit
     */
    public default EconomyResponse depositPlayer(OfflinePlayer player, double amount){
        return deposit(player.getUniqueId(), amount);
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #depositPlayer(OfflinePlayer, double)} instead.
     */
    @Deprecated
    public EconomyResponse depositPlayer(String playerName, double amount);

    /**
     * Deposit an amount from an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     * 
     * @param uuid      to deposit to
     * @param worldName name of the world
     * @param amount    Amount to deposit
     * @return Detailed response of transaction
     */
    public EconomyResponse deposit(UUID uuid, String worldName, double amount);

    /**
     * Deposit an amount from an account associated with a player on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     * @param player to deposit to
     * @param worldName name of the world
     * @param amount Amount to deposit
     */
    public default EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount){
        return deposit(player.getUniqueId(), worldName, amount);
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #depositPlayer(OfflinePlayer, String, double)} instead.
     */
    @Deprecated
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount);

    /*
     * Bank methods follow.
     */

    /**
     * Creates a bank account with the specified name and the given UUID as the
     * owner
     * 
     * @param name of account
     * @param uuid the account should be linked to
     * @return EconomyResponse Object
     */
    public EconomyResponse createBank(String name, UUID uuid);

    /**
     * Creates a bank account with the specified name and the given player as the
     * owner
     *
     * @param name of account
     * @param player the account should be linked to
     * @return EconomyResponse Object
     */
    public default EconomyResponse createBank(String name, OfflinePlayer player){
        return createBank(name, player.getUniqueId());
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #createBank(String, OfflinePlayer)} instead.
     */
    @Deprecated
    public EconomyResponse createBank(String name, String player);

    /**
     * Deletes a bank account with the specified name.
     * 
     * @param name of the back to delete
     * @return if the operation completed successfully
     */
    public EconomyResponse deleteBank(String name);

    /**
     * Returns the amount the bank has
     * 
     * @param name of the account
     * @return EconomyResponse Object
     */
    public EconomyResponse bankBalance(String name);

    /**
     * Returns true or false whether the bank has the amount specified - DO NOT USE
     * NEGATIVE AMOUNTS
     * 
     * @param name   of the account
     * @param amount to check for
     * @return EconomyResponse Object
     */
    public EconomyResponse bankHas(String name, double amount);

    /**
     * Withdraw an amount from a bank account - DO NOT USE NEGATIVE AMOUNTS
     * 
     * @param name   of the account
     * @param amount to withdraw
     * @return EconomyResponse Object
     */
    public EconomyResponse bankWithdraw(String name, double amount);

    /**
     * Deposit an amount into a bank account - DO NOT USE NEGATIVE AMOUNTS
     * 
     * @param name   of the account
     * @param amount to deposit
     * @return EconomyResponse Object
     */
    public EconomyResponse bankDeposit(String name, double amount);

    /**
     * Check if a uuid is the owner of a bank account
     * 
     * @param name of the account
     * @param uuid to check for ownership
     * @return EconomyResponse Object
     */
    public EconomyResponse isBankOwner(String name, UUID uuid);

    /**
     * Check if a player is the owner of a bank account
     *
     * @param name of the account
     * @param player to check for ownership
     * @return EconomyResponse Object
     */
    public default EconomyResponse isBankOwner(String name, OfflinePlayer player){
        return isBankOwner(name, player.getUniqueId());
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #isBankOwner(String, OfflinePlayer)} instead.
     */
    @Deprecated
    public EconomyResponse isBankOwner(String name, String playerName);

    /**
     * Check if the uuid is a member of the bank account
     * 
     * @param name of the account
     * @param uuid to check membership
     * @return EconomyResponse Object
     */
    public EconomyResponse isBankMember(String name, UUID uuid);

    /**
     * Check if the player is a member of the bank account
     *
     * @param name of the account
     * @param player to check membership
     * @return EconomyResponse Object
     */
    public default EconomyResponse isBankMember(String name, OfflinePlayer player){
        return isBankMember(name, player.getUniqueId());
    }

    /**
     *
     * @deprecated As of VaultAPI 1.4 use {@link #isBankMember(String, OfflinePlayer)} instead.
     */
    @Deprecated
    public EconomyResponse isBankMember(String name, String playerName);

    /**
     * Gets the list of banks
     * 
     * @return the List of Banks
     */
    public List<String> getBanks();

    /**
     * @deprecated As of VaultAPI 1.4 use {{@link #createPlayerAccount(OfflinePlayer)} instead.
     */
    @Deprecated
    public boolean createPlayerAccount(String playerName);

    /**
     * Attempts to create a player account for the given player
     * @param player OfflinePlayer
     * @return if the account creation was successful
     */
    public default boolean createPlayerAccount(OfflinePlayer player){
        return createAccount(player.getUniqueId(),player.getName());
    }

    /**
     * @deprecated As of VaultAPI 1.4 use {{@link #createPlayerAccount(OfflinePlayer, String)} instead.
     */
    @Deprecated
    public boolean createPlayerAccount(String playerName, String worldName);

    /**
     * Attempts to create a player account for the given player on the specified world
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this then false will always be returned.
     * @param player OfflinePlayer
     * @param worldName String name of the world
     * @return if the account creation was successful
     */
    public default boolean createPlayerAccount(OfflinePlayer player, String worldName){
        return createAccount(player.getUniqueId(),player.getName(),worldName);
    }
}
