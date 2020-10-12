package net.milkbowl.vault.economy;

import java.util.List;
import java.util.Collections;

/**
* Abstract class that removes the responsibility of implement
* Bank methods for child classes that doesn't support banks
*/
public abstract class NoBankSupportAbstractEconomy extends AbstractEconomy {

    // EconomyResponse class is immutable, it doesn't require the creation
    // of more objects to return the same response
    private final EconomyResponse response;

    public NoBankSupportAbstractEconomy(String unsupportedMessage) {
        this.response = new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, unsupportedMessage);
    }

    public NoBankSupportAbstractEconomy() {
        this("This economy manager doesn't support banks!");
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public EconomyResponse createBank(String name, String playerName) {
        return response;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return response;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return response;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return response;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return response;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return response;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return response;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return response;
    }

    @Override
    public List<String> getBanks() {
        return Collections.emptyList();
    }

}
