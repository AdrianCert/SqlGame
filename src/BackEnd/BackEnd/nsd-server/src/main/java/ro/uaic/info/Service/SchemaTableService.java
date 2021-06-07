package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.SchemaTable;
import ro.uaic.info.repository.Repository;
import ro.uaic.info.repository.SchemaTableRepository;

import java.sql.SQLException;

public class SchemaTableService extends ServiceAbstract {

    private final SchemaTableRepository repository = new SchemaTableRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        SchemaTable schemaTable = objectMapper.readValue(body, SchemaTable.class);
        SchemaTable dbEntity = objectMapper.readValue(body, SchemaTable.class);
        if(schemaTable.getCreation_script() != null) dbEntity.setCreation_script(schemaTable.getCreation_script());
        if(schemaTable.getSgdb() != null) dbEntity.setSgdb(schemaTable.getSgdb());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        SchemaTable schemaTable = objectMapper.readValue(body, SchemaTable.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(schemaTable));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
