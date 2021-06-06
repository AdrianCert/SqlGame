package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.BoardMembershipBuilder;
import ro.uaic.info.builder.BoardPublishBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.BoardPublish;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardPublishRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM BoardPublish WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        if(rd.next()) return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new BoardPublishBuilder(rd));
        return "null";
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE BoardPublish SET publish_at = ?, user_id = ?, board_id = ?, question_id = ?, port_id = ?, valid_field = ?, public_field = ?  WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        BoardPublish boardPublish = objectMapper.readValue(body, BoardPublish.class);

        preparedStatement.setDate(1, (Date) boardPublish.getPublish_at());
        preparedStatement.setInt(2, boardPublish.getUser_id());
        preparedStatement.setInt(3, boardPublish.getBoard_id());
        preparedStatement.setInt(4, boardPublish.getQuestion_id());
        preparedStatement.setInt(5, boardPublish.getPost_id());
        preparedStatement.setString(6, boardPublish.getValid_field());
        preparedStatement.setString(7, boardPublish.getPublic_field());
        preparedStatement.setInt(8, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO BoardPublish VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        BoardPublish boardPublish = objectMapper.readValue(body, BoardPublish.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM BoardPublish");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setDate(2, (Date) boardPublish.getPublish_at());
        preparedStatement.setInt(3, boardPublish.getUser_id());
        preparedStatement.setInt(4, boardPublish.getBoard_id());
        preparedStatement.setInt(5, boardPublish.getQuestion_id());
        preparedStatement.setInt(6, boardPublish.getPost_id());
        preparedStatement.setString(7, boardPublish.getValid_field());
        preparedStatement.setString(8, boardPublish.getPublic_field());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM BoardPublish WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<BoardPublish> listOfUsers(){
        List<BoardPublish> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM BoardPublish");
            BoardPublish board;
            while(rs.next()) {
                board = new BoardPublishBuilder(rs);
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
