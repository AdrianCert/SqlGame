package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Schema model
 */
public class Schema {
    private int ID;
    private String sgdb;
    private String creationg_script;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public String getSgdb() {
        return sgdb;
    }
    public String getCreationg_script() {
        return creationg_script;
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
    public void setCreationg_script(String creationg_script) {
        this.creationg_script = creationg_script;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setSgdb(r.getString(2));
            setCreationg_script(r.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setString(2, sgdb);
        stm.setString(3, creationg_script);

    }

}
