package net.milkbowl.vault.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BalanceUpdateEvent extends Event {
    
    //For implements personnal Economy Class :
    //Call this event BEFORE the update
    //And don't forget "if (!event.isCancelled())"  if you want to can cancel the event
    
    private static final HandlerList handlers = new HandlerList();
    
    private Player player; //The player
    private double previousBalance; //The balance before update
    private double newBalance; //The balance after update
    private double amountDifference; //The difference beetween previous and new balance. For example when /eco give Player 10, amount difference is 10.
    private Cause cause;
    private boolean cancelled;
    
    public BalanceUpdateEvent(Player player, double previousBalance, double newBalance, double amountDifference) {
        
        this.player = player;
        this.previousBalance = previousBalance;
        this.newBalance = newBalance;
        this.amountDifference = amountDifference;
        
    }
    
    
    

    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public Player getPlayer() {
        return player;
    }

    public double getPreviousBalance() {
        return previousBalance;
    }

    public double getNewBalance() {
        return newBalance;
    }
    
    public double getAmountDifference() {
        return amountDifference;
    }
    
    public Cause getCause() {
        return cause;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
    
    
    /**
     * The cause of the balance update.
     */
    public enum Cause {
        WITHDRAW,
        DEPOSIT,
        UNKNOWN
    }

}
