package ro.uaic.info.builder;

import ro.uaic.info.entity.SchemaLocation;

import java.sql.ResultSet;

public class SchemaLocationBuilder extends SchemaLocation {

    public SchemaLocationBuilder(){}

    public SchemaLocationBuilder(ResultSet resultSet){
        bind(resultSet);
    }

    public SchemaLocationBuilder ID(Integer ID) {
        setID(ID);
        return this;
    }
    public SchemaLocationBuilder schema_id(Integer schema_id) {
        setSchema_id(schema_id);
        return this;
    }
    public SchemaLocationBuilder credidential(String credidential) {
        setCredidential(credidential);
        return this;
    }
}
