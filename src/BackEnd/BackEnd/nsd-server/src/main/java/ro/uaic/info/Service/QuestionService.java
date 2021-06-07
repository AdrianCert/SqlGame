package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.Question;
import ro.uaic.info.repository.QuestionRepository;
import ro.uaic.info.repository.Repository;

import java.sql.SQLException;

public class QuestionService extends ServiceAbstract{

    private final QuestionRepository repository = new QuestionRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        Question question = objectMapper.readValue(body, Question.class);
        Question dbEntity = repository.getById(id);
        if(question.getTitle() != null) dbEntity.setTitle(question.getTitle());
        if(question.getDescription() != null) dbEntity.setDescription(question.getDescription());
        if(question.getSolution() != null) dbEntity.setSolution(question.getSolution());
        if(question.getValue() != 0) dbEntity.setValue(question.getValue());
        if(question.getReward() != 0) dbEntity.setReward(question.getReward());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        Question question = objectMapper.readValue(body, Question.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(question));

    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
