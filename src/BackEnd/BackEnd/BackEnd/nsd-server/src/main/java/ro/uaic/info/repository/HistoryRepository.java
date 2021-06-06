package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.BoardBuilder;
import ro.uaic.info.builder.HistoryBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.History;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM History WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        if(rd.next()) return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new HistoryBuilder(rd));
        return "null";
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE History SET user_id = ?, action = ?  WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        History history = objectMapper.readValue(body, History.class);

        preparedStatement.setInt(1, history.getUser_id());
        preparedStatement.setString(2, history.getAction());
        preparedStatement.setInt(3, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO History VALUES(?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        History history = objectMapper.readValue(body, History.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM History");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setInt(2, history.getUser_id());
        preparedStatement.setString(3, history.getAction());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM History WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<History> listOfUsers(){
        List<History> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM History");
            History board;
            while(rs.next()) {
                board = new HistoryBuilder(rs);
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
