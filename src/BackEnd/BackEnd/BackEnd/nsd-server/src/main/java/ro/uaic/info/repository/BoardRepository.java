package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.BoardBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.Board;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM Board WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        if(rd.next()) return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new BoardBuilder(rd));
        return "null";
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE UserTable SET name = ?, description = ?, owner = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        Board board = objectMapper.readValue(body, Board.class);

        preparedStatement.setString(1, board.getName());
        preparedStatement.setString(2, board.getName());
        preparedStatement.setString(3, board.getDescription());
        preparedStatement.setInt(4, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO Board VALUES(?, ?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        Board board = objectMapper.readValue(body, Board.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM Board");

        if(rs.next()){
            preparedStatement.setInt(1, rs.getInt(1) + 1);
            preparedStatement.setString(2, board.getName());
            preparedStatement.setString(3, board.getDescription());
            preparedStatement.setInt(4, board.getOwner());
            if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        }

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM Board WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<Board> listOfUsers(){
        List<Board> boards = new ArrayList<>();
        try {
            Statement statement = (Statement) Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Board");
            Board board;
            while(rs.next()) {
                board = new BoardBuilder(rs);
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
