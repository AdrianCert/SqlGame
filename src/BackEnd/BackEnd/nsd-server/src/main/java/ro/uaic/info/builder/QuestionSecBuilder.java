package ro.uaic.info.builder;

import ro.uaic.info.entity.QuestionSec;

import java.sql.ResultSet;
import java.util.Date;

public class QuestionSecBuilder extends QuestionSec {

    public QuestionSecBuilder(){}

    public QuestionSecBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public QuestionSecBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public QuestionSecBuilder question_id(Integer question_id) {
        setQuestion_id(question_id);
        return this;
    }
    public QuestionSecBuilder user_id(Integer user_id) {
        setUser_id(user_id);
        return this;
    }
    public QuestionSecBuilder schema_id(Integer schema_id) {
        setSchema_id(schema_id);
        return this;
    }
    public QuestionSecBuilder dml_permission(String dml_permission)  {
        setDml_permission(dml_permission);
        return this;
    }

}
