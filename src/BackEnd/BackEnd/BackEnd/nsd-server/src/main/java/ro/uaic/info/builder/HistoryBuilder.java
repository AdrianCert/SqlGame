package ro.uaic.info.builder;

import ro.uaic.info.entity.History;

import java.sql.ResultSet;

public class HistoryBuilder extends History {

    public HistoryBuilder(){}

    public HistoryBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public HistoryBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public HistoryBuilder user_id(Integer user_id) {
        setUser_id(user_id);
        return this;
    }
    public HistoryBuilder action(String action) {
        setAction(action);
        return this;
    }
}
