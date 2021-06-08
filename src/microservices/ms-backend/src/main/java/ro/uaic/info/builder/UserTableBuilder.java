package ro.uaic.info.builder;

import ro.uaic.info.entity.UserTable;

import java.sql.ResultSet;

public class UserTableBuilder extends UserTable {

    public UserTableBuilder(){

    }
    public UserTableBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public UserTableBuilder id(Integer ID) {
        setID(ID);
        return this;
    }
    public UserTableBuilder name(String name) {
        setName(name);
        return this;
    }
    public UserTableBuilder surname(String surname) {
        setSurname(surname);
        return this;
    }
    public UserTableBuilder user_name(String user_name) {
        setUser_name(user_name);
        return this;
    }
    public UserTableBuilder mail(String mail) {
        setMail(mail);
        return this;
    }
    public UserTableBuilder details(String details) {
        setDetails(details);
        return this;
    }
}
