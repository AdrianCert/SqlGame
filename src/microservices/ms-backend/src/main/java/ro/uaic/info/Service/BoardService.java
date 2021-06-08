package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.Board;
import ro.uaic.info.repository.BoardRepository;
import ro.uaic.info.repository.Repository;

import java.sql.SQLException;

public class BoardService extends ServiceAbstract{

    private final BoardRepository repository = new BoardRepository();


    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        Board board = objectMapper.readValue(body, Board.class);
        Board dbEntity = repository.getById(id);
        if(board.getName() != null) dbEntity.setName(board.getName());
        if(board.getDescription() != null) dbEntity.setDescription(board.getDescription());
        if(board.getOwner() != 0) dbEntity.setOwner(board.getOwner());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        Board board = objectMapper.readValue(body, Board.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(board));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
