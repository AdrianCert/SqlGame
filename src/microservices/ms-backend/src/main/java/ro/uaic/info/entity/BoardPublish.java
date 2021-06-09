package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * BoardPublish model
 */
public class BoardPublish {
    private int ID;
    private Date publish_at;
    private int user_id;
    private int board_id;
    private int question_id;
    private int post_id;
    private String valid_field;
    private String public_field;

    /**
     * getter
     */
    public int getID() {
        return ID;
    }
    public Date getPublish_at() {
        return publish_at;
    }
    public int getUser_id() {
        return user_id;
    }
    public int getBoard_id() {
        return board_id;
    }
    public int getQuestion_id() {
        return question_id;
    }
    public int getPost_id() {
        return post_id;
    }
    public String getValid_field() {
        return valid_field;
    }
    public String getPublic_field() {
        return public_field;
    }

    /**
     * setter
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setPublish_at(Date publish_at) {
        this.publish_at = publish_at;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }
    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
    public void setValid_field(String valid_field) {
        this.valid_field = valid_field;
    }
    public void setPublic_field(String public_field) {
        this.public_field = public_field;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setPublish_at(r.getDate(2));
            setUser_id(r.getInt(3));
            setBoard_id(r.getInt(4));
            setQuestion_id(r.getInt(5));
            setPost_id(r.getInt(6));
            setValid_field(r.getString(7));
            setPublic_field(r.getString(8));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setDate(2, (java.sql.Date) publish_at);
        stm.setInt(3, user_id);
        stm.setInt(4, board_id);
        stm.setInt(5, question_id);
        stm.setInt(6, post_id);
        stm.setString(7, valid_field);
        stm.setString(8, public_field);
    }

}
