package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.QuestionsOwned;
import ro.uaic.info.repository.QuestionsOwnedRepository;
import ro.uaic.info.repository.Repository;

import java.sql.SQLException;

public class QuestionsOwnedService extends ServiceAbstract{

    private final QuestionsOwnedRepository repository = new QuestionsOwnedRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        QuestionsOwned questionsOwned = objectMapper.readValue(body, QuestionsOwned.class);
        QuestionsOwned dbEntity = repository.getById(id);
        if(questionsOwned.getID() != 0) dbEntity.setID(questionsOwned.getID());
        if(questionsOwned.getUser_id() != 0) dbEntity.setUser_id(questionsOwned.getUser_id());
        if(questionsOwned.getQuestion_id() != 0) dbEntity.setQuestion_id(questionsOwned.getQuestion_id());
        if(questionsOwned.getSolution() != null) dbEntity.setSolution(questionsOwned.getSolution());
        if(questionsOwned.getSolved() != null) dbEntity.setSolved(questionsOwned.getSolved());
        if(questionsOwned.getPayment_buy() != 0) dbEntity.setPayment_buy(questionsOwned.getPayment_buy());
        if(questionsOwned.getPayment_rew() != 0) dbEntity.setPayment_rew(questionsOwned.getPayment_rew());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        QuestionsOwned questionsOwned = objectMapper.readValue(body, QuestionsOwned.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(questionsOwned));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
