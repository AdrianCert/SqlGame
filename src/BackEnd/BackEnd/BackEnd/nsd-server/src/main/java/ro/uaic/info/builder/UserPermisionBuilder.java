package ro.uaic.info.builder;

import ro.uaic.info.entity.UserPermision;

import java.util.Date;

public class UserPermisionBuilder extends UserPermision {
    public UserPermisionBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public UserPermisionBuilder user_id(Integer user_id) {
        setUser_id(user_id);
        return this;
    }
    public UserPermisionBuilder role_id(Integer role_id) {
        setRole_id(role_id);
        return this;
    }
    public UserPermisionBuilder expiration(Date expiration) {
        setExpiration(expiration);
        return this;
    }
}
