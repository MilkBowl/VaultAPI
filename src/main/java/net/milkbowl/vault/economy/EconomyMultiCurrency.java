package net.milkbowl.vault.economy;

import org.bukkit.OfflinePlayer;

import java.util.List;

public interface EconomyMultiCurrency extends Economy{


    /**
     * Returns a list of all available currencies in the economy plugin.
     * Can be empty.
     * @return List of currency names
     */
    public List<String> getCurrencies();

    /**
     * Some economy plugins round off after a certain number of digits.
     * This function returns the number of digits the plugin keeps
     * or -1 if no rounding occurs.
     *
     * @param currencyName The currency to check the fractional digits for
     * @return number of digits after the decimal point kept
     */
    public int fractionalDigits(String currencyName);

    /**
     * Format amount into a human readable String This provides translation into
     * economy specific formatting to improve consistency between plugins.
     *
     * @param amount to format
     * @param currencyName The currency to format the amount in
     * @return Human readable string describing amount
     */
    public String format(double amount, String currencyName);

    /**
     * Returns the name of the currency in plural form.
     * If the economy being used does not support currency names then an empty string will be returned.
     *
     * @param currencyName The currency to get the name of
     * @return name of the currency (plural)
     */
    public String currencyNamePlural(String currencyName);

    /**
     * Returns the name of the currency in singular form.
     * If the economy being used does not support currency names then an empty string will be returned.
     *
     * @param currencyName The currency to get the name of
     * @return name of the currency (singular)
     */
    public String currencyNameSingular(String currencyName);

    /**
     * Gets balance of a player
     *
     * @param player of the player
     * @param currencyName The currency to get the balance in
     * @return Amount currently held in players account
     */
    public double getBalance(OfflinePlayer player , String currencyName);

    /**
     * Gets balance of a player on the specified world.
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
     * @param player to check
     * @param currencyName The currency to get the balance in
     * @param world name of the world
     * @return Amount currently held in players account
     */
    public double getBalance(OfflinePlayer player, String world, String currencyName);


    /**
     * Checks if the player account has the amount - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player to check
     * @param currencyName The currency to check the amount in
     * @param amount to check for
     * @return True if <b>player</b> has <b>amount</b>, False else wise
     */
    public boolean has(OfflinePlayer player, double amount,String currencyName);

    /**
     * Checks if the player account has the amount in a given world - DO NOT USE NEGATIVE AMOUNTS
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
     *
     * @param player to check
     * @param worldName to check with
     * @param amount to check for
     * @param currencyName The currency to check the amount in
     * @return True if <b>player</b> has <b>amount</b>, False else wise
     */
    public boolean has(OfflinePlayer player, String worldName, double amount, String currencyName);

    /**
     * Withdraw an amount from a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player to withdraw from
     * @param amount Amount to withdraw
     * @param currencyName The currency to withdraw the amount in
     * @return Detailed response of transaction
     */
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount, String currencyName);


    /**
     * Withdraw an amount from a player on a given world - DO NOT USE NEGATIVE AMOUNTS
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
     * @param player to withdraw from
     * @param worldName - name of the world
     * @param amount Amount to withdraw
     * @param currencyName The currency to withdraw the amount in
     * @return Detailed response of transaction
     */
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount, String currencyName);

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player to deposit to
     * @param amount Amount to deposit
     * @param currencyName The currency to deposit the amount in
     * @return Detailed response of transaction
     */
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount, String currencyName);

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
     *
     * @param player to deposit to
     * @param worldName name of the world
     * @param amount Amount to deposit
     * @param currencyName The currency to deposit the amount in
     * @return Detailed response of transaction
     */
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount, String currencyName);

    /**
     * Returns the amount the bank has
     * @param name of the account
     * @param currencyName The currency to get the balance in
     * @return EconomyResponse Object
     */
    public EconomyResponse bankBalance(String name, String currencyName);

    /**
     * Returns true or false whether the bank has the amount specified - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name of the account
     * @param amount to check for
     * @param currencyName The currency to check the amount in
     * @return EconomyResponse Object
     */
    public EconomyResponse bankHas(String name, double amount, String currencyName);

    /**
     * Withdraw an amount from a bank account - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name of the account
     * @param amount to withdraw
     * @param currencyName The currency to withdraw the amount in
     * @return EconomyResponse Object
     */
    public EconomyResponse bankWithdraw(String name, double amount, String currencyName);

    /**
     * Deposit an amount into a bank account - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name of the account
     * @param amount to deposit
     * @param currencyName The currency to deposit the amount in
     * @return EconomyResponse Object
     */
    public EconomyResponse bankDeposit(String name, double amount, String currencyName);

}
