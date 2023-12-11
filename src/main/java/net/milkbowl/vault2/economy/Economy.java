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

package net.milkbowl.vault2.economy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.milkbowl.vault2.economy.EconomyResponse.ResponseType;

/**
 * The main economy API
 *
 */
public interface Economy {

    /*
     * Economy plugin-related methods follow.
     */

    /**
     * Checks if economy plugin is enabled.
     * 
     * @return true if the server's economy plugin has properly enabled.
     */
    public boolean isEnabled();

    /**
     * Gets name of the economy plugin.
     * 
     * @return Name of the active economy plugin on the server.
     */
    public String getName();

    /**
     * Returns true if the economy plugin supports banks.
     * 
     * @return true if the economy plugin supports banks.
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
     * @return number of digits after the decimal point this plugin supports or -1
     *         if no rounding occurs.
     */
    public int fractionalDigits();

    /**
     * Plugins use this method to format a given BigDecimal amount into a human
     * readable amount using your economy plugin's currency names/conventions.
     *
     * @param amount to format.
     * @return Human readable string describing amount, ie 5 Dollars or 5.55 Pounds.
     */
    public String format(BigDecimal amount);

    /**
     * Returns the name of the currency in plural form. If the economy being used
     * does not support currency names then an empty string will be returned.
     * 
     * @return name of the currency (plural) ie: Dollars or Pounds.
     */
    public String currencyNamePlural();

    /**
     * Returns the name of the currency in singular form. If the economy being used
     * does not support currency names then an empty string will be returned.
     * 
     * @return name of the currency (singular) ie: Dollar or Pound.
     */
    public String currencyNameSingular();

    /*
     * Account-related methods follow.
     */

    /**
     * Attempts to create a account for the given UUID.
     * 
     * @param uuid UUID associated with the account.
     * @param name UUID associated with the account.
     * @return true if the account creation was successful.
     */
    public boolean createAccount(UUID uuid, String name);

    /**
     * Attempts to create an account for the given UUID on the specified world
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this then
     * false will always be returned.
     * 
     * @param uuid      UUID associated with the account.
     * @param name      UUID associated with the account.
     * @param worldName String name of the world.
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
     * @param uuid UUID associated with the account.
     * @return name of the account owner.
     */
    public String getAccountName(UUID uuid);

    /**
     * Checks if this UUID has an account yet.
     * 
     * @param uuid UUID to check for an existing account.
     * @return true if the UUID has an account.
     */
    public boolean hasAccount(UUID uuid);

    /**
     * Checks if this UUID has an account yet on the given world.
     * 
     * @param uuid      UUID to check for an existing account.
     * @param worldName world-specific account.
     * @return if the UUID has an account.
     */
    public boolean hasAccount(UUID uuid, String worldName);

    /**
     * A method which changes the name associated with the given UUID in the
     * Map<UUID, String> received from {@link #getUUIDNameMap()}.
     * 
     * @param uuid UUID whose account is having a name change.
     * @param name String name that will be associated with the UUID in the 
     *             Map<UUID, String> map.
     * @return true if the name change is successful.
     */
    public boolean renameAccount(UUID uuid, String name);

    /*
     * Account balance related methods follow.
     */

    /**
     * Gets balance of an account associated with a UUID.
     * 
     * @param uuid UUID of the account to get a balance for.
     * @return Amount currently held in account associated with the given UUID.
     */
    public BigDecimal getBalance(UUID uuid);

    /**
     * Gets balance of a UUID on the specified world. IMPLEMENTATION SPECIFIC - if
     * an economy plugin does not support this the global balance will be returned.
     * 
     * @param uuid  UUID of the account to get a balance for.
     * @param world name of the world.
     * @return Amount currently held in account associated with the given UUID.
     */
    public BigDecimal getBalance(UUID uuid, String world);

    /**
     * Checks if the account associated with the given UUID has the amount - DO NOT
     * USE NEGATIVE AMOUNTS.
     * 
     * @param uuid   the UUID associated with the account to check the balance of.
     * @param amount the amount to check for.
     * @return True if <b>UUID</b> has <b>amount</b>, False else wise.
     */
    public boolean has(UUID uuid, BigDecimal amount);

    /**
     * Checks if the account associated with the given UUID has the amount in the
     * given world - DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an
     * economy plugin does not support this the global balance will be returned.
     * 
     * @param uuid      the UUID associated with the account to check the balance of.
     * @param worldName the name of the world to check in.
     * @param amount    the amount to check for.
     * @return True if <b>UUID</b> has <b>amount</b> in the given <b>world</b>,
     *         False else wise.
     */
    public boolean has(UUID uuid, String worldName, BigDecimal amount);

    /**
     * Withdraw an amount from an account associated with a UUID - DO NOT USE
     * NEGATIVE AMOUNTS.
     * 
     * @param uuid   the UUID associated with the account to withdraw from.
     * @param amount Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    public EconomyResponse withdraw(UUID uuid, BigDecimal amount);

    /**
     * Withdraw an amount from an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     * 
     * @param uuid      the UUID associated with the account to withdraw from.
     * @param worldName the name of the world to check in.
     * @param amount    Amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    public EconomyResponse withdraw(UUID uuid, String worldName, BigDecimal amount);

    /**
     * Deposit an amount to an account associated with the given UUID - DO NOT USE
     * NEGATIVE AMOUNTS.
     * 
     * @param uuid   the UUID associated with the account to deposit to.
     * @param amount Amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    public EconomyResponse deposit(UUID uuid, BigDecimal amount);

    /**
     * Deposit an amount to an account associated with a UUID on a given world -
     * DO NOT USE NEGATIVE AMOUNTS IMPLEMENTATION SPECIFIC - if an economy plugin
     * does not support this the global balance will be returned.
     * 
     * @param uuid      the UUID associated with the account to deposit to.
     * @param worldName the name of the world to check in.
     * @param amount    Amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    public EconomyResponse deposit(UUID uuid, String worldName, BigDecimal amount);

    /*
     * Bank methods follow.
     */

    /**
     * Creates a bank account with the specified name and the given UUID as the
     * owner.
     * 
     * @param name Name of account.
     * @param uuid UUID of the account should be linked to.
     * @return true if bank creation is successful.
     */
    public boolean createBank(String name, UUID uuid);

    /**
     * Deletes a bank account with the specified UUID.
     * 
     * @param uuid UUID of the bank to be deleted.
     * @return true if the operation completed successfully
     */
    public boolean deleteBank(UUID uuid);

    /**
     * Returns a map that represents all of the UUIDs which have banks in the
     * plugin, as well as their last-known-name. This is used for Vault's economy
     * converter and should be given every account available.
     * 
     * @return a {@link Map} composed of the accounts keyed by their UUID, along
     *         with their associated last-known-name.
     */
    public Map<UUID, String> getBankUUIDNameMap();

    /**
     * Gets the last known name of an bank with the given UUID. Required for
     * messages to be more human-readable than UUIDs alone can provide.
     * 
     * @param uuid UUID to look up.
     * @return name of the bank.
     */
    public String getBankAccountName(UUID uuid);

    /**
     * Checks if this UUID has a bank yet.
     * 
     * @param uuid UUID to check.
     * @return true if the UUID has an account.
     */
    public boolean hasBankAccount(UUID uuid);

    /**
     * A method which changes the name associated with the given UUID in the
     * Map<UUID, String> received from {@link #getBankUUIDNameMap()}.
     * 
     * @param uuid UUID which is having a name change.
     * @param name name that will be associated with the UUID in the 
     *             Map<UUID, String> map.
     * @return true if the name change is successful.
     */
    public boolean renameBankAccount(UUID uuid, String name);

    /**
     * Returns the amount the bank has.
     * 
     * @param uuid UUID of the account.
     * @return amount which the bank holds as a balance.
     */
    public BigDecimal bankBalance(UUID uuid);

    /**
     * Returns true or false whether the bank has the amount specified - DO NOT USE
     * NEGATIVE AMOUNTS.
     * 
     * @param uuid   UUID of the account.
     * @param amount to check for
     * @return true if the bank has the given amount.
     */
    public boolean bankHas(UUID uuid, BigDecimal amount);

    /**
     * Withdraw an amount from a bank account - DO NOT USE NEGATIVE AMOUNTS.
     * 
     * @param uuid   UUID of the account.
     * @param amount to withdraw.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    public EconomyResponse bankWithdraw(String name, BigDecimal amount);

    /**
     * Deposit an amount into a bank account - DO NOT USE NEGATIVE AMOUNTS.
     * 
     * @param uuid   UUID of the account.
     * @param amount to deposit.
     * @return {@link EconomyResponse} which includes the Economy plugin's
     *         {@link ResponseType} as to whether the transaction was a Success,
     *         Failure, Unsupported.
     */
    public EconomyResponse bankDeposit(String name, BigDecimal amount);

    /**
     * Check if a UUID is the owner of a bank account.
     * 
     * @param uuid     UUID of the player/object who might be an owner.
     * @param bankUUID UUID of the bank account to check ownership of.
     * @return true if the uuid is the owner of the bank associated with bankUUID.
     */
    public boolean isBankOwner(UUID uuid, UUID bankUUID);

    /**
     * Check if the UUID is a member of the bank account
     * 
     * @param uuid     UUID of the player/object who might be a member..
     * @param bankUUID UUID of the bank account to check membership of.
     * @return @return true if the uuid is a member of the bank associated with bankUUID.
     */
    public boolean isBankMember(UUID uuid, UUID bankUUID);

    /**
     * Gets the list of banks' UUIDs.
     * 
     * @return the List of Banks' UUIDs.
     */
    public List<UUID> getBanks();
}
