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
    public QuestionBuilder user_id(Integer user_id) {
        setUser_id(user_id);
        return this;
    }
    public QuestionBuilder pass(String pass) {
        setPass(pass);
        return this;
    }
    public QuestionBuilder pass_update_mail(String pass_update_mail) {
        setPass_update_mail(pass_update_mail);
        return this;
    }
    public QuestionBuilder recovery_mail(String recovery_mail) {
        setRecovery_mail(recovery_mail);
        return this;
    }
    public QuestionBuilder recovery_code(String recovery_code) {
        setRecovery_code(recovery_code);
        return this;
    }
}
