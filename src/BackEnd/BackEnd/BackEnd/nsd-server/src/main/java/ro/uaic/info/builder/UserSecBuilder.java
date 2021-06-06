package ro.uaic.info.builder;

import ro.uaic.info.entity.UserSec;

import java.sql.ResultSet;
import java.util.Date;

public class UserSecBuilder extends UserSec {

    public UserSecBuilder(){}

    public UserSecBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public UserSecBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public UserSecBuilder user_id(Integer user_id) {
        setUser_id(user_id);
        return this;
    }
    public UserSecBuilder pass(String pass) {
        setPass(pass);
        return this;
    }
    public UserSecBuilder pass_update_at(Date pass_update_at) {
        setPass_update_at(pass_update_at);
        return this;
    }
    public UserSecBuilder recovery_mail(String recovery_mail) {
        setRecovery_mail(recovery_mail);
        return this;
    }
    public UserSecBuilder recovery_code(String recovery_code) {
        setRecovery_code(recovery_code);
        return this;
    }
}
