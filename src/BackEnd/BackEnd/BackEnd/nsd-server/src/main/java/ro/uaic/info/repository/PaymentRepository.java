package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.HistoryBuilder;
import ro.uaic.info.builder.PaymentBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM Payment WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        if(rd.next()) return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new PaymentBuilder(rd));
        return "null";
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE Payment SET wallet_seller = ?, wallet_buyer = ?, valoare = ?, balanta-noua = ?, title = ?  WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        Payment payment = objectMapper.readValue(body, Payment.class);

        preparedStatement.setInt(1, payment.getWallet_seller());
        preparedStatement.setInt(2, payment.getWallet_buyer());
        preparedStatement.setInt(3, payment.getValoare());
        preparedStatement.setInt(4, payment.getBlanta_noua());
        preparedStatement.setString(5, payment.getTitle());
        preparedStatement.setInt(6, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO Payment VALUES(?, ?, ?, ?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        Payment payment = objectMapper.readValue(body, Payment.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM Payment");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setInt(2, payment.getWallet_seller());
        preparedStatement.setInt(3, payment.getWallet_buyer());
        preparedStatement.setInt(4, payment.getValoare());
        preparedStatement.setInt(5, payment.getBlanta_noua());
        preparedStatement.setString(6, payment.getTitle());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM Payment WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<Payment> listOfUsers(){
        List<Payment> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Payment");
            Payment board;
            while(rs.next()) {
                board = new PaymentBuilder(rs);
                boards.add(board);
            }
            statement.close();
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }
}
