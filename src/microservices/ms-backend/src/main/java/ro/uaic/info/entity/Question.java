package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Model for Question
 */
public class Question {
    private int ID;
    private String title;
    private String description;
    private String solution;
    private int value;
    private int reward;

    /**
     * getter
     * @return
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
    public String getSolution() {
        return solution;
    }
    public int getValue() {
        return value;
    }
    public int getReward() {
        return reward;
    }

    /**
     * Setter
     * @param ID
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
    public void setSolution(String solution) {
        this.solution = solution;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public void setReward(int reward) {
        this.reward = reward;
    }

    public void bind(ResultSet r) {
        try {
            setID(r.getInt(1));
            setTitle(r.getString(2));
            setDescription(r.getString(3));
            setSolution(r.getString(4));
            setValue(r.getInt(5));
            setReward(r.getInt(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setString(2, title);
        stm.setString(3, description);
        stm.setString(4, solution);
        stm.setInt(5, value);
        stm.setInt(6, reward);
    }

}
