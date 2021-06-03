package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Model for Question
 */
public class Question {
    private int ID;
    private int user_id;
    private String pass;
    private String pass_update_mail;
    private String recovery_mail;
    private String recovery_code;

    /**
     * getter
     * @return
     */
    public int getID() {
        return ID;
    }
    public int getUser_id() {
        return user_id;
    }
    public String getPass() {
        return pass;
    }
    public String getPass_update_mail() {
        return pass_update_mail;
    }
    public String getRecovery_mail() {
        return recovery_mail;
    }
    public String getRecovery_code() {
        return recovery_code;
    }

    /**
     * Setter
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setPass_update_mail(String pass_update_mail) {
        this.pass_update_mail = pass_update_mail;
    }
    public void setRecovery_mail(String recovery_mail) {
        this.recovery_mail = recovery_mail;
    }
    public void setRecovery_code(String recovery_code) {
        this.recovery_code = recovery_code;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setUser_id(r.getInt(2));
            setPass(r.getString(3));
            setPass_update_mail(r.getString(4));
            setRecovery_mail(r.getString(5));
            setRecovery_code(r.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setInt(2, user_id);
        stm.setString(3, pass);
        stm.setString(4, pass_update_mail);
        stm.setString(5, recovery_mail);
        stm.setString(6, recovery_code);
    }

}
