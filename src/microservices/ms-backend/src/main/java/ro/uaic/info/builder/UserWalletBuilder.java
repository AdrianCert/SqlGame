package ro.uaic.info.builder;

import ro.uaic.info.entity.UserWallet;

import java.sql.ResultSet;
import java.util.Date;

public class UserWalletBuilder extends UserWallet {

    public UserWalletBuilder(){}

    public UserWalletBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public UserWalletBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public UserWalletBuilder user_id(Integer user_id) {
        setUser_id(user_id);
        return this;
    }
    public UserWalletBuilder wallet_id(Integer wallet_id) {
        setWallet_id(wallet_id);
        return this;
    }

}
