package ro.uaic.info.builder;

import ro.uaic.info.entity.QuestionsOwned;

import java.sql.ResultSet;

public class QuestionsOwnedBuilder extends QuestionsOwned {
    public QuestionsOwnedBuilder(){}

    public QuestionsOwnedBuilder(ResultSet resultSet){bind(resultSet);}

    public QuestionsOwnedBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public QuestionsOwnedBuilder user_id(Integer user_id) {
        setUser_id(user_id);
        return this;
    }
    public QuestionsOwnedBuilder question_id(Integer question_id) {
        setQuestion_id(question_id);
        return this;
    }
    public QuestionsOwnedBuilder solution(String solution) {
        setSolution(solution);
        return this;
    }
    public QuestionsOwnedBuilder solved(String solved)  {
        setSolved(solved);
        return this;
    }
    public QuestionsOwnedBuilder payment_buy(Integer payment_buy)  {
        setPayment_buy(payment_buy);
        return this;
    }
    public QuestionsOwnedBuilder payment_rew(Integer payment_rew)  {
        setPayment_rew(payment_rew);
        return this;
    }

}
