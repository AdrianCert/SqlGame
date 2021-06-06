package ro.uaic.info.builder;

import ro.uaic.info.entity.BoardPublish;

import java.sql.ResultSet;
import java.util.Date;

public class BoardPublishBuilder extends BoardPublish {

    public BoardPublishBuilder(){}

    public BoardPublishBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public BoardPublishBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public BoardPublishBuilder publish_at(Date publish_at) {
        setPublish_at(publish_at);
        return this;
    }
    public BoardPublishBuilder user_id(int user_id) {
        setUser_id(user_id);
        return this;
    }
    public BoardPublishBuilder board_id(int board_id) {
        setBoard_id(board_id);
        return this;
    }
    public BoardPublishBuilder question_id(int question_id) {
        setQuestion_id(question_id);
        return this;
    }
    public BoardPublishBuilder post_id(int post_id) {
        setPost_id(post_id);
        return this;
    }
    public BoardPublishBuilder valid_field(String valid_field) {
        setValid_field(valid_field);
        return this;
    }
    public BoardPublishBuilder public_field(String public_field) {
        setPublic_field(public_field);
        return this;
    }
}
