package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.History;
import ro.uaic.info.repository.HistoryRepository;
import ro.uaic.info.repository.Repository;

import java.sql.SQLException;

public class HistoryService extends ServiceAbstract{

    private final HistoryRepository repository = new HistoryRepository();


    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        History history = objectMapper.readValue(body, History.class);
        History dbEntity = repository.getById(id);
        if(history.getUser_id() != 0) dbEntity.setUser_id(history.getUser_id());
        if(history.getAction() != null) dbEntity.setAction(history.getAction());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        History history = objectMapper.readValue(body, History.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(history));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
