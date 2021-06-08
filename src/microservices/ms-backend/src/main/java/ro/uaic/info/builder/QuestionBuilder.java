package ro.uaic.info.builder;

import ro.uaic.info.entity.Question;

import java.sql.ResultSet;

public class QuestionBuilder extends Question {

    public QuestionBuilder(){}

    public QuestionBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public QuestionBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public QuestionBuilder user_id(String user_id) {
        setTitle(user_id);
        return this;
    }
    public QuestionBuilder pass(String pass) {
        setDescription(pass);
        return this;
    }
    public QuestionBuilder pass_update_mail(String pass_update_mail) {
        setSolution(pass_update_mail);
        return this;
    }
    public QuestionBuilder recovery_mail(Integer recovery_mail) {
        setValue(recovery_mail);
        return this;
    }
    public QuestionBuilder recovery_code(Integer recovery_code) {
        setReward(recovery_code);
        return this;
    }
}
