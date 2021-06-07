package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.repository.Repository;

import java.sql.SQLException;

public abstract class ServiceAbstract {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    public final static String JSON_OK = "{ \"status\": \"OK\" }";

    public String getById(String id) throws JsonProcessingException, SQLException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(getRepository().getById(Integer.parseInt(id)));
    }

    public String getAll() throws JsonProcessingException{
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(getRepository().getAll());
    }

    public abstract String update(int id, String body) throws SQLException, JsonProcessingException;

    public abstract String add(String body) throws SQLException, JsonProcessingException;

    public String delete(Integer id) throws SQLException {
        getRepository().delete(getRepository().getById(id));
        return JSON_OK;
    }

    public abstract Repository getRepository();

}
