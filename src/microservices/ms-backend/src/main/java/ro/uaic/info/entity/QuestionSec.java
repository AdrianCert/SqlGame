package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * QuestionSec model
 */
public class QuestionSec {
    private int ID;
    private int question_id;
    private int user_id;
    private int schema_id;
    private String dml_permission;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public int getQuestion_id() {
        return question_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public int getSchema_id() {
        return schema_id;
    }
    public String getDml_permission() {
        return dml_permission;
    }

    /**
     * setter
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setSchema_id(int schema_id) {
        this.schema_id = schema_id;
    }
    public void setDml_permission(String dml_permission) {
        this.dml_permission = dml_permission;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setQuestion_id(r.getInt(2));
            setUser_id(r.getInt(3));
            setSchema_id(r.getInt(4));
            setDml_permission(r.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setInt(2, question_id);
        stm.setInt(3, user_id);
        stm.setInt(4, schema_id);
        stm.setString(5, dml_permission);
    }
}
