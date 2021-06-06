package ro.uaic.info.builder;

import ro.uaic.info.entity.Role;

import java.sql.ResultSet;

public class RoleBuilder extends Role {

    public RoleBuilder(){}

    public RoleBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public RoleBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public RoleBuilder title(String title) {
        setTitle(title);
        return this;
    }
    public RoleBuilder description(String description) {
        setDescription(description);
        return this;
    }
}
