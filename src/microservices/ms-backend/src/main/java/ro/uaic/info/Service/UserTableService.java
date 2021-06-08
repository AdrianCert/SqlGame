package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.UserTable;
import ro.uaic.info.repository.Repository;
import ro.uaic.info.repository.UserTableRepository;

import java.sql.SQLException;

public class UserTableService extends ServiceAbstract {

    private final UserTableRepository repository = new UserTableRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        UserTable userTable = objectMapper.readValue(body, UserTable.class);
        UserTable dbUserTable = repository.getById(id);
        if(userTable.getDetails() != null) dbUserTable.setDetails(userTable.getDetails());
        if(userTable.getUser_name() != null) dbUserTable.setUser_name(userTable.getUser_name());
        if(userTable.getMail() != null) dbUserTable.setMail(userTable.getMail());
        if(userTable.getSurname() != null) dbUserTable.setSurname(userTable.getSurname());
        if(userTable.getName() != null) dbUserTable.setName(userTable.getName());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbUserTable));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        UserTable userTable = objectMapper.readValue(body, UserTable.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(userTable));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
