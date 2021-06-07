package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserWallet model
 */
public class UserWallet {
    private int ID;
    private int user_id;
    private int wallet_id;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public int getUser_id() {
        return user_id;
    }
    public int getWallet_id() {
        return wallet_id;
    }

    /**
     * setter
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setWallet_id(int wallet_id) {
        this.wallet_id = wallet_id;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setUser_id(r.getInt(2));
            setWallet_id(r.getInt(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setInt(2, user_id);
        stm.setInt(3, wallet_id);
    }
}
