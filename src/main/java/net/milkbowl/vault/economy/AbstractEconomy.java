package net.milkbowl.vault.economy;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

@SuppressWarnings("deprecation")
public abstract class AbstractEconomy implements Economy {

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		if (player.getName() == null) return false;
		return hasAccount(player.getName());
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String worldName) {
		if (player.getName() == null) return false;
		return hasAccount(player.getName(), worldName);
	}
	
	@Override
	public boolean hasAccount(UUID uuid) {
		if (uuid == null) return false;
		return hasAccount(Bukkit.getOfflinePlayer(uuid));
	}
	
	@Override
	public boolean hasAccount(UUID uuid, String worldName) {
		if (uuid == null) return false;
		return hasAccount(Bukkit.getOfflinePlayer(uuid), worldName);
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		return getBalance(player.getName());
	}

	@Override
	public double getBalance(OfflinePlayer player, String worldName) {
		return getBalance(player.getName(), worldName);
	}
	
	@Override
	public double getBalance(UUID uuid) {
		return getBalance(Bukkit.getOfflinePlayer(uuid));
	}
	
	@Override
	public double getBalance(UUID uuid, String worldName) {
		return getBalance(Bukkit.getOfflinePlayer(uuid), worldName);
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		if (player.getName() == null) return false;
		return has(player.getName(), amount);
	}

	@Override
	public boolean has(OfflinePlayer player, String worldName, double amount) {
		if (player.getName() == null) return false;
		return has(player.getName(), worldName, amount);
	}
	
	@Override
	public boolean has(UUID uuid, double amount) {
		if (uuid == null) return false;
		return has(Bukkit.getOfflinePlayer(uuid), amount);
	}
	
	@Override
	public boolean has(UUID uuid, String worldName, double amount) {
		if (uuid == null) return false;
		return has(Bukkit.getOfflinePlayer(uuid), worldName, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		return withdrawPlayer(player.getName(), amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
		return withdrawPlayer(player.getName(), worldName, amount);
	}

	@Override
	public EconomyResponse withdraw(UUID uuid, double amount) {
		return withdrawPlayer(Bukkit.getOfflinePlayer(uuid), amount);
	}
	
	@Override
	public EconomyResponse withdraw(UUID uuid, String worldName, double amount) {
		return withdrawPlayer(Bukkit.getOfflinePlayer(uuid), worldName, amount);
	}
	
	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		return depositPlayer(player.getName(), amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
		return depositPlayer(player.getName(), worldName, amount);
	}

	@Override
	public EconomyResponse deposit(UUID uuid, double amount) {
		return depositPlayer(Bukkit.getOfflinePlayer(uuid), amount);
	}
	
	@Override
	public EconomyResponse deposit(UUID uuid, String worldName, double amount) {
		return depositPlayer(Bukkit.getOfflinePlayer(uuid), worldName, amount);
	}
	
	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player) {
		return createBank(name, player.getName());
	}
	
	@Override
	public EconomyResponse createBank(String name, UUID uuid) {
		return createBank(name, Bukkit.getOfflinePlayer(uuid));
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		return isBankOwner(name, player.getName());
	}
	
	@Override
	public EconomyResponse isBankOwner(String name, UUID uuid) {
		return isBankOwner(name, Bukkit.getOfflinePlayer(uuid));
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
		return isBankMember(name, player.getName());
	}
	
	@Override
	public EconomyResponse isBankMember(String name, UUID uuid ) {
		return isBankMember(name, Bukkit.getOfflinePlayer(uuid));
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		return createPlayerAccount(player.getName());
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
		return createPlayerAccount(player.getName(), worldName);
	}

	@Override
	public boolean createAccount(UUID uuid) {
		return createPlayerAccount(Bukkit.getOfflinePlayer(uuid));
	}

	@Override
	public boolean createAccount(UUID uuid, String worldName) {
		return createPlayerAccount(Bukkit.getOfflinePlayer(uuid), worldName);
	}
}
