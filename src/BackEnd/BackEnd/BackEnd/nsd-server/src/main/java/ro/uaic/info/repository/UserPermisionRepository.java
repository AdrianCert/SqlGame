package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.UserPermisionBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.UserPermision;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserPermisionRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM UserPermision WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        UserPermision userPermision = new UserPermisionBuilder(rd);
        return objectMapper.writeValueAsString(userPermision);
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE UserPermision SET user_id = ?, role_id = ?, expiration = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        UserPermision userPermision = objectMapper.readValue(body, UserPermision.class);

        preparedStatement.setInt(1, userPermision.getUser_id());
        preparedStatement.setInt(2, userPermision.getRole_id());
        preparedStatement.setDate(3, (Date) userPermision.getExpiration());
        preparedStatement.setInt(4, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO UserPermision VALUES(?, ?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        UserPermision userPermision = objectMapper.readValue(body, UserPermision.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM UserPermision");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setInt(2, userPermision.getUser_id());
        preparedStatement.setInt(3, userPermision.getRole_id());
        preparedStatement.setDate(4, (Date) userPermision.getExpiration());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM UserPermision WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<UserPermision> listOfUsers(){
        List<UserPermision> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM UserPermision");
            UserPermision board;
            while(rs.next()) {
                board = new UserPermisionBuilder(rs);
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
