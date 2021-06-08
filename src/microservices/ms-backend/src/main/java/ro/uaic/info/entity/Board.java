package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Board model
 */
public class Board {
    private int ID;
    private String name;
    private String description;
    private int owner;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getOwner() {
        return owner;
    }

    /**
     * setter
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setName(r.getString(2));
            setDescription(r.getString(3));
            setOwner(r.getInt(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setString(2, name);
        stm.setString(3, description);
        stm.setInt(4, owner);
    }
}
