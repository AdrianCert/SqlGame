package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.UserSec;
import ro.uaic.info.repository.Repository;
import ro.uaic.info.repository.UserSecRepository;

import java.sql.SQLException;

public class UserSecService extends ServiceAbstract{

    private final UserSecRepository repository = new UserSecRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        UserSec userSec = objectMapper.readValue(body, UserSec.class);
        UserSec dbEntity = objectMapper.readValue(body, UserSec.class);
        if(userSec.getUser_id() != 0) dbEntity.setUser_id(userSec.getUser_id());
        if(userSec.getPass() != null) dbEntity.setPass(userSec.getPass());
        if(userSec.getPass_update_at() != null) dbEntity.setPass_update_at(userSec.getPass_update_at());
        if(userSec.getRecovery_mail() != null) dbEntity.setRecovery_mail(userSec.getRecovery_mail());
        if(userSec.getRecovery_code() != null) dbEntity.setRecovery_code(userSec.getRecovery_code());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        UserSec userSec = objectMapper.readValue(body, UserSec.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(userSec));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
