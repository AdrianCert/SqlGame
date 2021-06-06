package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.UserWalletBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.UserWallet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserWalletRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM UserWallet WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        if(rd.next()) return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new UserWalletBuilder(rd));
        return "null";
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE UserWallet SET user_id = ?, wallet_id = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        UserWallet userWallet = objectMapper.readValue(body, UserWallet.class);

        preparedStatement.setInt(1, userWallet.getUser_id());
        preparedStatement.setInt(2, userWallet.getWallet_id());
        preparedStatement.setInt(3, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO UserWallet VALUES(?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        UserWallet userWallet = objectMapper.readValue(body, UserWallet.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM UserWallet");

        if(rs.next()){
            preparedStatement.setInt(1, rs.getInt(1) + 1);
            preparedStatement.setInt(2, userWallet.getUser_id());
            preparedStatement.setInt(3, userWallet.getWallet_id());
            if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        }

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM UserWallet WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<UserWallet> listOfUsers(){
        List<UserWallet> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM UserWallet");
            UserWallet board;
            while(rs.next()) {
                board = new UserWalletBuilder(rs);
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
