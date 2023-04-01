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

import net.milkbowl.vault.economy.wrappers.IdentityEconomyWrapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Adds UUID support to the Economy interface.
 * In case of {@link #supportsAllRecordsOperation()} or {@link #supportsAllOnlineOperation()}
 * returning true, methods such as
 * {@link #getAllRecords()} and {@link #getAllOnline()}
 * should not be expected to work.
 * <p>
 * In order to register/provide it, you should use {@link IdentityEconomyWrapper#registerProviders()}
 */
public interface IdentityEconomy extends Economy{
    /**
     * Used to determine if IdentityEconomy was built through
     * the EconomyWrapper as a LegacyEconomy.
     * If false, method {@link #getAllRecords()} will not be work.
     * This was made in order to support plugins which use older versions of VaultAPI/Vault.
     * You can also use it / override it to disable previous mentioned methods!
     * @return true if operation is supported
     */
    public boolean supportsAllRecordsOperation();

    /**
     * Used to determine if IdentityEconomy was built through
     * the EconomyWrapper as a LegacyEconomy.
     * If false, the method {@link #getAllOnline()} (UUID)} will not be work.
     * This was made in order to support plugins which use older versions of VaultAPI/Vault.
     * You can also use it / override it to disable previous mentioned methods!
     * @return true if operation is supported
     */
    public boolean supportsAllOnlineOperation();

    /**
     * Used to determine if IdentityEconomy was built through
     * the EconomyWrapper as a LegacyEconomy.
     * If false, all operations must be done with players that
     * are online/connected to the server in real time.
     * If true, you should expect to call these operations
     * asynchronously.
     * @return true if operation is supported
     */
    public boolean supportsOfflineOperations();

    /**
     * Used to determine if IdentityEconomy was built through
     * the EconomyWrapper as a LegacyEconomy.
     * If false, you should expect UnsupportedOperationException
     * being thrown when calling these methods.
     * @return true if operation is supported
     */
    public boolean supportsUUIDOperations();

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
     * Returns a map that represents all of the stored UUIDs which have accounts in the
     * plugin (in the database, not memory), as well as their last-known-name. This is used for Vault's economy
     * converter and should be given every account available.
     * Needs IdentityEconomy#hasUUIDSupport() to be true.
     * 
     * @return a {@link Map} composed of the accounts keyed by their UUID, along
     *         with their associated last-known-name.
     */
    public Map<UUID, String> getAllRecords();

    /**
     * Returns a collection of all UUIDs which have accounts in the plugin and are currently
     * online/connected to the server.
     * This is used for Vault's economy converter and should
     * Needs IdentityEconomy#hasUUIDSupport() to be true.
     * @return a {@link Collection} of all UUIDs which have accounts in the plugin
     */
    public Collection<UUID> getAllOnline();

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
     * Checks if this uuid has an account yet on the given world
     * 
     * @param uuid      to check
     * @param worldName world-specific account
     * @return if the uuid has an account
     */
    public boolean hasAccount(UUID uuid, String worldName);

    /**
     * A method which changes the name associated with the given UUID in the
     * Map<UUID, String> received from {@link #getAllRecords()}
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
