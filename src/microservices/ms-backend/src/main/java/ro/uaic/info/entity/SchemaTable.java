package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Schema model
 */
public class SchemaTable {
    private int ID;
    private String sgdb;
    private String creation_script;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public String getSgdb() {
        return sgdb;
    }
    public String getCreation_script() {
        return creation_script;
    }

    /**
     * setter
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setSgdb(String sgdb) {
        this.sgdb = sgdb;
    }
    public void setCreation_script(String creation_script) {
        this.creation_script = creation_script;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setSgdb(r.getString(2));
            setCreation_script(r.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setString(2, sgdb);
        stm.setString(3, creation_script);

    }

}
