package net.milkbowl.vault.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.milkbowl.vault.economy.EconomyResponse;

public class BalanceUpdateEvent extends Event {
    
    private static final HandlerList handlers = new HandlerList();
    
    private double previousBalance; //The balance before update
    private EconomyResponse response;
    private String accountName;
    
    public BalanceUpdateEvent(EconomyResponse response) {
        
        this.response  = response;
        this.previousBalance = response.balance + response.amount;
        this.accountName = response.accountName;
        
    }
       

    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public double getPreviousBalance() {
        return previousBalance;
    }

    public EconomyResponse getEconomyResponse() {  
        return response;    
    }
    
    public String getAccountName() {  
        return accountName;
    }
    
    public boolean isPlayer(String player) {
        return Bukkit.getOfflinePlayer(player).isOnline();
    }

}
