package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.UserPermisionBuilder;
import ro.uaic.info.builder.UserSecBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.UserSec;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSecRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM UserSec WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        if(rd.next()) return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new UserSecBuilder(rd));
        return "null";
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE UserSec SET user_id = ?, pass = ?, pass_update_at = ?, recovery_mail = ?, recovery_code = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        UserSec userSec = objectMapper.readValue(body, UserSec.class);

        preparedStatement.setInt(1, userSec.getUser_id());
        preparedStatement.setString(2, userSec.getPass());
        preparedStatement.setDate(3, (Date) userSec.getPass_update_at());
        preparedStatement.setString(4, userSec.getRecovery_mail());
        preparedStatement.setString(5, userSec.getRecovery_code());
        preparedStatement.setInt(6, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO UserSec VALUES(?, ?, ?, ?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        UserSec userSec = objectMapper.readValue(body, UserSec.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM UserSec");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setInt(2, userSec.getUser_id());
        preparedStatement.setString(3, userSec.getPass());
        preparedStatement.setDate(4, (Date) userSec.getPass_update_at());
        preparedStatement.setString(5, userSec.getRecovery_mail());
        preparedStatement.setString(6, userSec.getRecovery_code());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM UserSec WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<UserSec> listOfUsers(){
        List<UserSec> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM UserSec");
            UserSec board;
            while(rs.next()) {
                board = new UserSecBuilder(rs);
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
