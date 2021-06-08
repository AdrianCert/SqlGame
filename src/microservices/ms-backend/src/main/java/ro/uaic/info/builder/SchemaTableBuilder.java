package ro.uaic.info.builder;

import ro.uaic.info.entity.SchemaTable;

import java.sql.ResultSet;

public class SchemaTableBuilder extends SchemaTable {

    public SchemaTableBuilder(){}

    public SchemaTableBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public SchemaTableBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public SchemaTableBuilder sgdb(String sgdb) {
        setSgdb(sgdb);
        return this;
    }
    public SchemaTableBuilder creationg_script(String creationg_script) {
        setCreation_script(creationg_script);
        return this;
    }
}
