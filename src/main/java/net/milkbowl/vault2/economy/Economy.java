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

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

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
     * @return Success or Failure
     */
    public boolean isEnabled();

    /**
     * Gets name of economy plugin.
     * 
     * @return Name of Economy plugin.
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
     * plugin, as well as their last-known-name whether it is null or not. This is
     * used for Vault's economy converter and should be given every account
     * available.
     * 
     * @return a {@link Map} composed of the accounts keyed by their UUID, along
     *         with their associated last-known-name (null or not.)
     */
    public Map<UUID, @Nullable String> getUUIDNameMap();

    /**
     * Gets the last known name of an account owned by the given UUID. Required for
     * messages to be more human-readable than UUIDs alone can provide.
     * 
     * @param uuid UUID to look up.
     * @return name of the account owner or null.
     */
    @Nullable
    public String getAccountName(UUID uuid);

    /**
     * Checks if this uuid has an account yet
     * 
     * @param uuid to check
     * @return if the uuid has an account
     */
    public boolean hasAccount(UUID uuid);

    /**
     * Checks if this uuid has an account yet on the given world
     * 
     * @param uuid      to check
     * @param worldName world-specific account
     * @return if the uuid has an account
     */
    public boolean hasAccount(UUID uuid, String worldName);

    /**
     * Gets balance of a UUID
     * 
     * @param uuid of the account to get a balance for
     * @return Amount currently held in account associated with the given UUID
     */
    public double getBalance(UUID uuid);

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
     * Checks if the account associated with the given UUID has the amount - DO NOT
     * USE NEGATIVE AMOUNTS
     * 
     * @param uuid   to check
     * @param amount to check for
     * @return True if <b>UUID</b> has <b>amount</b>, False else wise
     */
    public boolean has(UUID uuid, double amount);

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
     * Withdraw an amount from an account associated with a UUID - DO NOT USE
     * NEGATIVE AMOUNTS
     * 
     * @param uuid   to withdraw from
     * @param amount Amount to withdraw
     * @return Detailed response of transaction
     */
    public EconomyResponse withdraw(UUID uuid, double amount);

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
     * Deposit an amount to an account associated with the given UUID - DO NOT USE
     * NEGATIVE AMOUNTS
     * 
     * @param uuid   to deposit to
     * @param amount Amount to deposit
     * @return Detailed response of transaction
     */
    public EconomyResponse deposit(UUID uuid, double amount);

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
     * Check if the uuid is a member of the bank account
     * 
     * @param name of the account
     * @param uuid to check membership
     * @return EconomyResponse Object
     */
    public EconomyResponse isBankMember(String name, UUID uuid);

    /**
     * Gets the list of banks
     * 
     * @return the List of Banks
     */
    public List<String> getBanks();
}
