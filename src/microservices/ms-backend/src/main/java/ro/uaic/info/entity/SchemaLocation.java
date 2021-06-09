package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SchemaLocation model
 */
public class SchemaLocation {
    private int ID;
    private int schema_id;
    private String credidential;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public int getSchema_id() {
        return schema_id;
    }
    public String getCredidential() {
        return credidential;
    }

    /**
     * setter
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setSchema_id(int schema_id) {
        this.schema_id = schema_id;
    }
    public void setCredidential(String credidential) {
        this.credidential = credidential;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setSchema_id(r.getInt(2));
            setCredidential(r.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setInt(2, schema_id);
        stm.setString(3, credidential);
    }
}
