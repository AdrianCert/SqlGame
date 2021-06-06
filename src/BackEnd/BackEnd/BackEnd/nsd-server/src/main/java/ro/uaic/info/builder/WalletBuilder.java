package ro.uaic.info.builder;

import ro.uaic.info.entity.Wallet;

import java.sql.ResultSet;

public class WalletBuilder extends Wallet {

    public WalletBuilder(){}

    public WalletBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public WalletBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public WalletBuilder balancing(Integer balancing) {
        setBalancing(balancing);
        return this;
    }
}
