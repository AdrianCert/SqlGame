package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Role model
 */
public class Role {
    private int ID;
    private String title;
    private String description;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    /**
     * setter
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setTitle(r.getString(2));
            setDescription(r.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setString(2, title);
        stm.setString(3, description);
    }
}
