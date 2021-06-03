package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * QuestionAnswer model
 */
public class QuestionAnswer {
    private int ID;
    private String value;
    private int question_id;
    private int user_id;
    private Date submit_time;
    private String status;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public String getValue() {
        return value;
    }
    public int getQuestion_id() {
        return question_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public Date getSubmit_time() {
        return submit_time;
    }
    public String getStatus() {
        return status;
    }

    /**
     * setter
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setSubmit_time(Date submit_time) {
        this.submit_time = submit_time;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setValue(r.getString(2));
            setQuestion_id(r.getInt(3));
            setUser_id(r.getInt(4));
            setSubmit_time(r.getDate(5));
            setStatus(r.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setString(2, value);
        stm.setInt(3, question_id);
        stm.setInt(4, user_id);
        stm.setDate(5, (java.sql.Date) submit_time);
        stm.setString(6, status);
    }
}
