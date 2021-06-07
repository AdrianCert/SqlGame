package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Wallet model
 */
public class Wallet {
    private int ID;
    private int balancing;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public int getBalancing() {
        return balancing;
    }

    /**
     * setter
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setBalancing(int balancing) {
        this.balancing = balancing;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setBalancing(r.getInt(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setInt(2, balancing);
    }
}

