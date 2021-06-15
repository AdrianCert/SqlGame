package ro.uaic.info.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionsOwned {
    private int ID;
    private int user_id;
    private int question_id;
    private String solution;
    private String solved;
    private int payment_buy;
    private int payment_rew;

    /**
     * setter pentru variabile
     */
    public void setID(int id){this.ID = id;}
    public void setUser_id(int user_id){this.user_id = user_id;}
    public void setQuestion_id(int question_id){this.question_id = question_id;}
    public void setSolution(String solution){this.solution = solution;}
    public void setSolved(String solved){this.solved = solved;}
    public void setPayment_buy(int payment_buy) {
        this.payment_buy = payment_buy;
    }
    public void setPayment_rew(int payment_rew) {
        this.payment_rew = payment_rew;
    }

    /**
     * getter pentru variabile
     */
    public int getID() {
        return ID;
    }
    public int getUser_id() {
        return user_id;
    }
    public int getQuestion_id() {
        return question_id;
    }
    public String getSolution() {
        return solution;
    }
    public String getSolved() {
        return solved;
    }
    public int getPayment_buy() {
        return payment_buy;
    }
    public int getPayment_rew() {
        return payment_rew;
    }

    public void bind(ResultSet r){
        try{
            setID(r.getInt(1));
            setUser_id(r.getInt(2));
            setQuestion_id(r.getInt(3));
            setSolution(r.getString(4));
            setSolved(r.getString(5));
            setPayment_buy(r.getInt(6));
            setPayment_rew(r.getInt(7));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void prepare(PreparedStatement stm) throws SQLException {
        stm.setInt(1, ID);
        stm.setInt(2, user_id);
        stm.setInt(3, question_id);
        stm.setString(4, solution);
        stm.setString(5, solved);
        stm.setInt(6, payment_buy);
        stm.setInt(7, payment_rew);
    }

}
