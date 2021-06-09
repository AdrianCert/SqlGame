package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.Role;
import ro.uaic.info.repository.Repository;
import ro.uaic.info.repository.RoleRepository;

import java.sql.SQLException;

public class RoleService extends ServiceAbstract{

    private final RoleRepository repository = new RoleRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        Role role = objectMapper.readValue(body, Role.class);
        Role dbEntity = repository.getById(id);
        if(role.getTitle() != null) dbEntity.setTitle(role.getTitle());
        if(role.getDescription() != null) dbEntity.setDescription(role.getDescription());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        Role role = objectMapper.readValue(body, Role.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(role));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
