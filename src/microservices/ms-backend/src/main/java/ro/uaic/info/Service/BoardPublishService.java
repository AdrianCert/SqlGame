package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.BoardPublish;
import ro.uaic.info.repository.BoardPublishRepository;
import ro.uaic.info.repository.Repository;

import java.sql.SQLException;

public class BoardPublishService extends ServiceAbstract{

    private final BoardPublishRepository repository = new BoardPublishRepository();


    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        BoardPublish boardPublish = objectMapper.readValue(body, BoardPublish.class);
        BoardPublish dbEntity = objectMapper.readValue(body, BoardPublish.class);
        if(boardPublish.getPublish_at() != null) dbEntity.setPublish_at(boardPublish.getPublish_at());
        if(boardPublish.getUser_id() != 0) dbEntity.setUser_id(boardPublish.getUser_id());
        if(boardPublish.getBoard_id() != 0) dbEntity.setBoard_id(boardPublish.getBoard_id());
        if(boardPublish.getQuestion_id() != 0) dbEntity.setQuestion_id(boardPublish.getQuestion_id());
        if(boardPublish.getPost_id() != 0) dbEntity.setPost_id(boardPublish.getPost_id());
        if(boardPublish.getValid_field() != null) dbEntity.setValid_field(boardPublish.getValid_field());
        if(boardPublish.getPublic_field() != null) dbEntity.setPublic_field(boardPublish.getPublic_field());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        return null;
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
