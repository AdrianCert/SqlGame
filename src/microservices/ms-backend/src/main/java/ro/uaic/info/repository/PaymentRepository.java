package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.PaymentBuilder;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.Payment;
import ro.uaic.info.entity.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository implements Repository<Payment>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM Payment WHERE id=?";
    private final static String update = "UPDATE Payment SET ID = ?, wallet_seller = ?, wallet_buyer = ?, valoare = ?, balanta-noua = ?, title = ?  WHERE id = ?";
    private final static String add = "INSERT INTO Payment VALUES(?, ?, ?, ?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM Payment";
    private final static String delete = "DELETE FROM Payment WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM Payment";

    public PaymentRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public Payment getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new PaymentBuilder(resultSet) : null;
    }

    @Override
    public List<Payment> getAll() {
        List<Payment> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new PaymentBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public Payment update(Payment payment) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        payment.prepare(preparedStatement);
        preparedStatement.setInt(7, payment.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return payment;
    }

    @Override
    public Payment add(Payment payment) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) payment.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        payment.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return payment;
    }

    @Override
    public void delete(Payment payment) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, payment.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
