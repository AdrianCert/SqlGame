package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.BoardMembership;
import ro.uaic.info.repository.BoardMembershipRepository;
import ro.uaic.info.repository.Repository;

import java.sql.SQLException;

public class BoardMembershipService extends ServiceAbstract{

    private final BoardMembershipRepository repository = new BoardMembershipRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        BoardMembership boardMembership = objectMapper.readValue(body, BoardMembership.class);
        BoardMembership dbEntry = repository.getById(id);
        if(boardMembership.getUser_id() != 0) dbEntry.setUser_id(boardMembership.getUser_id());
        if(boardMembership.getRole_id() != 0) dbEntry.setUser_id(boardMembership.getRole_id());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntry));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        BoardMembership boardMembership = objectMapper.readValue(body, BoardMembership.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(boardMembership));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
