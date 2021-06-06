package ro.uaic.info.builder;

import ro.uaic.info.entity.BoardMembership;

import java.sql.ResultSet;

public class BoardMembershipBuilder extends BoardMembership {

    public BoardMembershipBuilder(){}

    public BoardMembershipBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public BoardMembershipBuilder id(Integer ID) {
        setID(ID);
        return this;
    }
    public BoardMembershipBuilder user_id(Integer user_id) {
        setUser_id(user_id);
        return this;
    }
    public BoardMembershipBuilder role_id(Integer role_id) {
        setRole_id(role_id);
        return this;
    }
}
