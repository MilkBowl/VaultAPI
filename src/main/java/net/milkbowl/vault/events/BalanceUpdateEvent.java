package net.milkbowl.vault.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.milkbowl.vault.economy.EconomyResponse;

public class BalanceUpdateEvent extends Event {
    
    private static final HandlerList handlers = new HandlerList();
    
    private double previousBalance; //The balance before update
    private EconomyResponse response;
    
    public BalanceUpdateEvent(EconomyResponse response) {
        
        this.previousBalance = response.balance + response.amount;
        this.response  = response;
        
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

}
