package ro.uaic.info.builder;

import ro.uaic.info.entity.Schema;

public class SchemaBuilder extends Schema {
    public SchemaBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public SchemaBuilder sgdb(String sgdb) {
        setSgdb(sgdb);
        return this;
    }
    public SchemaBuilder creationg_script(String creationg_script) {
        setCreationg_script(creationg_script);
        return this;
    }
}
