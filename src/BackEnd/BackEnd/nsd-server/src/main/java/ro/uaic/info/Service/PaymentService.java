package ro.uaic.info.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ro.uaic.info.entity.Payment;
import ro.uaic.info.repository.PaymentRepository;
import ro.uaic.info.repository.Repository;

import java.sql.SQLException;

public class PaymentService extends ServiceAbstract{

    private final PaymentRepository repository = new PaymentRepository();


    @Override
    public String update(int id, String body) throws SQLException, JsonProcessingException {
        Payment payment = objectMapper.readValue(body, Payment.class);
        Payment dbEntity = repository.getById(id);
        if(payment.getWallet_seller() != 0) dbEntity.setWallet_seller(payment.getWallet_seller());
        if(payment.getWallet_buyer() != 0) dbEntity.setWallet_buyer(payment.getWallet_seller());
        if(payment.getValoare() != 0) dbEntity.setValoare(payment.getValoare());
        if(payment.getBlanta_noua() != 0) dbEntity.setBlanta_noua(payment.getBlanta_noua());
        if(payment.getTitle() != null) dbEntity.setTitle(payment.getTitle());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.update(dbEntity));
    }

    @Override
    public String add(String body) throws SQLException, JsonProcessingException {
        Payment payment = objectMapper.readValue(body, Payment.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.add(payment));
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
