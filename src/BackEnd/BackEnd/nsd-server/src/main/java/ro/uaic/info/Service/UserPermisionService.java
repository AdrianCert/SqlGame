package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.UserPermision;
import ro.uaic.info.repository.Repository;
import ro.uaic.info.repository.UserPermisionRepository;

import java.sql.SQLException;

public class UserPermisionService extends ServiceAbstract{

    private final UserPermisionRepository repository = new UserPermisionRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        UserPermision userPermision = objectMapper.readValue(body, UserPermision.class);
        UserPermision dbEntity = repository.getById(id);
        if(userPermision.getUser_id() != 0) dbEntity.setUser_id(userPermision.getUser_id());
        if(userPermision.getExpiration() != null) dbEntity.setExpiration(userPermision.getExpiration());
        if(userPermision.getRole_id() != 0) dbEntity.setRole_id(userPermision.getRole_id());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        UserPermision userPermision = objectMapper.readValue(body, UserPermision.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(userPermision));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
