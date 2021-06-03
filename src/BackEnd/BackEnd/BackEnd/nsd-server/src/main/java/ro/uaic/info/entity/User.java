package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Model for User
 */
public class User {
    private int ID;
    private String name;
    private String surname;
    private String user_name;
    private String mail;
    private String details;

    /**
     * setter pentru variabile
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String Surname) {this.surname = Surname;}
    public void setUser_name(String username) {this.user_name = username;}
    public void setMail(String mail){this.mail = mail;}
    public void setDetails(String details){this.details = details;}

    /**
     * Getter pentru variabile
     * @return
     */
    public int getID(){return ID;}
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getUser_name() {
        return user_name;
    }
    public String getMail() {
        return mail;
    }
    public String getDetails() {
        return details;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setName(r.getString(2));
            setSurname(r.getString(3));
            setUser_name(r.getString(4));
            setMail(r.getString(5));
            setDetails(r.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setString(2, name);
        stm.setString(3, surname);
        stm.setString(4, user_name);
        stm.setString(5, mail);
        stm.setString(6, details);
    }

}
