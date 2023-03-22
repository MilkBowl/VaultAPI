package net.milkbowl.vault.economy;

public class EconomyWrapper {
    private final Economy economy;
    public EconomyWrapper(Economy economy){
        this.economy = economy;
    }

    /**
     * Will convert the Economy to a new IdentityEconomy
     * which IdentityEconomy#isLegacy() returns true.
     * Methods regarding UUID will fail fast.
     * @return a legacy economy
     */
    public LegacyEconomy legacy(){
        return new LegacyEconomy(economy);
    }
}
