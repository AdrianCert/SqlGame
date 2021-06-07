package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.QuestionSec;
import ro.uaic.info.repository.QuestionSecRepository;
import ro.uaic.info.repository.Repository;

import java.sql.SQLException;

public class QuestionSecService extends ServiceAbstract{

    private final QuestionSecRepository repository = new QuestionSecRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        QuestionSec questionSec = objectMapper.readValue(body, QuestionSec.class);
        QuestionSec dbEntity = repository.getById(id);
        if(questionSec.getQuestion_id() != 0)dbEntity.setQuestion_id(questionSec.getQuestion_id());
        if(questionSec.getUser_id() != 0)dbEntity.setUser_id(questionSec.getUser_id());
        if(questionSec.getSchema_id() != 0)dbEntity.setSchema_id(questionSec.getSchema_id());
        if(questionSec.getDml_permission() != null)dbEntity.setDml_permission(questionSec.getDml_permission());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        QuestionSec questionSec = objectMapper.readValue(body, QuestionSec.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(questionSec));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
