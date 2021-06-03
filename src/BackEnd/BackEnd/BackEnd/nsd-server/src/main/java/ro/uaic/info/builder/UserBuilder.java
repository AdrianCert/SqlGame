package ro.uaic.info.builder;

import ro.uaic.info.entity.User;

public class UserBuilder extends User {
    public UserBuilder id(Integer ID) {
        setID(ID);
        return this;
    }
    public UserBuilder name(String name) {
        setName(name);
        return this;
    }
    public UserBuilder surname(String surname) {
        setSurname(surname);
        return this;
    }
    public UserBuilder user_name(String user_name) {
        setUser_name(user_name);
        return this;
    }
    public UserBuilder mail(String mail) {
        setMail(mail);
        return this;
    }
    public UserBuilder details(String details) {
        setDetails(details);
        return this;
    }
}
