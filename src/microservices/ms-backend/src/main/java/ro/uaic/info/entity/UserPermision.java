package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * UserPermision model
 */
public class UserPermision {
    private int ID;
    private int user_id;
    private int role_id;
    private Date expiration;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public int getUser_id() {
        return user_id;
    }
    public int getRole_id() {
        return role_id;
    }
    public Date getExpiration() {
        return expiration;
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
    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setUser_id(r.getInt(2));
            setRole_id(r.getInt(3));
            setExpiration(r.getDate(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setInt(2, user_id);
        stm.setInt(3, role_id);
        stm.setDate(4, (java.sql.Date) expiration);
    }

}
