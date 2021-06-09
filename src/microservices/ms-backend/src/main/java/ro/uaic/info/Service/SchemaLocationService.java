package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.SchemaLocation;
import ro.uaic.info.repository.Repository;
import ro.uaic.info.repository.SchemaLocationRepository;

import java.sql.SQLException;

public class SchemaLocationService extends ServiceAbstract{

    private final SchemaLocationRepository repository = new SchemaLocationRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        SchemaLocation schemaLocation = objectMapper.readValue(body, SchemaLocation.class);
        SchemaLocation dbEntity = repository.getById(id);
        if(schemaLocation.getCredidential() != null) dbEntity.setCredidential(schemaLocation.getCredidential());
        if(schemaLocation.getSchema_id() != 0) dbEntity.setSchema_id(schemaLocation.getSchema_id());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        SchemaLocation schemaLocation = objectMapper.readValue(body, SchemaLocation.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(schemaLocation));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
