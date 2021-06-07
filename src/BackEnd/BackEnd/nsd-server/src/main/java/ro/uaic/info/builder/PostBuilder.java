package ro.uaic.info.builder;

import ro.uaic.info.entity.Post;

import java.sql.ResultSet;

public class PostBuilder extends Post {

    public PostBuilder(){}

    public PostBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public PostBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public PostBuilder user_id(Integer user_id) {
        setUser_id(user_id);
        return this;
    }
    public PostBuilder title(String title) {
        setTitle(title);
        return this;
    }
    public PostBuilder content(String content) {
        setContent(content);
        return this;
    }
    public PostBuilder comments(String comments) {
        setComments(comments);
        return this;
    }
}
