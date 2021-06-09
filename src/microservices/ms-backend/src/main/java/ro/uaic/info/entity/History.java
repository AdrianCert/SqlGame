package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * History model
 */
public class History {
    private int ID;
    private int user_id;
    private String action;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public int getUser_id() {
        return user_id;
    }
    public String getAction() {
        return action;
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
    public void setAction(String action) {
        this.action = action;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setUser_id(r.getInt(2));
            setAction(r.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setInt(2, user_id);
        stm.setString(3, action);

    }
}
