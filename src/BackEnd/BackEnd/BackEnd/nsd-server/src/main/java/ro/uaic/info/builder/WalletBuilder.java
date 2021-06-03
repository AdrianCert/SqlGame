package ro.uaic.info.builder;

import ro.uaic.info.entity.Wallet;

public class WalletBuilder extends Wallet {
    public WalletBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public WalletBuilder balancing(Integer balancing) {
        setBalancing(balancing);
        return this;
    }
}
