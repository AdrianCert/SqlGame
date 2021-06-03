package ro.uaic.info.builder;

import ro.uaic.info.entity.Role;

public class RoleBuilder extends Role {
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
