package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.UserWallet;
import ro.uaic.info.repository.Repository;
import ro.uaic.info.repository.UserWalletRepository;

import java.sql.SQLException;

public class UserWalletService extends ServiceAbstract{

    private final UserWalletRepository repository = new UserWalletRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        UserWallet userWallet = objectMapper.readValue(body, UserWallet.class);
        UserWallet dbEntity = repository.getById(id);
        if(userWallet.getUser_id() != 0) dbEntity.setUser_id(userWallet.getUser_id());
        if(userWallet.getWallet_id() != 0) dbEntity.setWallet_id(userWallet.getWallet_id());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        UserWallet userWallet = objectMapper.readValue(body, UserWallet.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(userWallet));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
