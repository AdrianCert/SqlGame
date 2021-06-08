package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.QuestionAnswer;
import ro.uaic.info.repository.QuestionAnswerRepository;
import ro.uaic.info.repository.Repository;

import java.sql.SQLException;

public class QuestionAnswerService extends ServiceAbstract{

    private final QuestionAnswerRepository repository = new QuestionAnswerRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        QuestionAnswer questionAnswer = objectMapper.readValue(body, QuestionAnswer.class);
        QuestionAnswer dbEntity = repository.getById(id);
        if(questionAnswer.getValue() != null) dbEntity.setValue(questionAnswer.getValue());
        if(questionAnswer.getQuestion_id() != 0) dbEntity.setQuestion_id(questionAnswer.getQuestion_id());
        if(questionAnswer.getUser_id() != 0) dbEntity.setUser_id(questionAnswer.getUser_id());
        if(questionAnswer.getSubmit_time() != null) dbEntity.setSubmit_time(questionAnswer.getSubmit_time());
        if(questionAnswer.getStatus() != null) dbEntity.setStatus(questionAnswer.getStatus());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        QuestionAnswer questionAnswer = objectMapper.readValue(body, QuestionAnswer.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(questionAnswer));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
