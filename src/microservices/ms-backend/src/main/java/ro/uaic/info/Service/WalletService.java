package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.Wallet;
import ro.uaic.info.repository.Repository;
import ro.uaic.info.repository.WalletRepository;

import java.sql.SQLException;

public class WalletService extends ServiceAbstract{

    private final WalletRepository repository = new WalletRepository();

    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        Wallet wallet = objectMapper.readValue(body, Wallet.class);
        Wallet dbEntity = repository.getById(id);
        if(wallet.getBalancing() != 0) dbEntity.setBalancing(wallet.getBalancing());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        Wallet wallet = objectMapper.readValue(body, Wallet.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(wallet));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
