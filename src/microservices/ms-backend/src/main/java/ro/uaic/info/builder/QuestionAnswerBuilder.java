package ro.uaic.info.builder;

import ro.uaic.info.entity.QuestionAnswer;

import java.sql.ResultSet;
import java.util.Date;

public class QuestionAnswerBuilder extends QuestionAnswer {

    public QuestionAnswerBuilder(){}

    public QuestionAnswerBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public QuestionAnswerBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public QuestionAnswerBuilder value(String value) {
        setValue(value);
        return this;
    }
    public QuestionAnswerBuilder question_id(Integer question_id) {
        setQuestion_id(question_id);
        return this;
    }
    public QuestionAnswerBuilder user_id(Integer user_id) {
        setUser_id(user_id);
        return this;
    }
    public QuestionAnswerBuilder submit_time(Date submit_time)  {
        setSubmit_time(submit_time);
        return this;
    }
    public QuestionAnswerBuilder status(String status) {
        setStatus(status);
        return this;
    }
}
