package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.BoardMembershipBuilder;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.BoardMembership;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardMembershipRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM BoardMembership WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        if(rd.next()) return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new BoardMembershipBuilder(rd));
        return "null";
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE BoardMembership SET user_id = ?, role_id = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        BoardMembership boardMembership = objectMapper.readValue(body, BoardMembership.class);

        preparedStatement.setInt(1, boardMembership.getUser_id());
        preparedStatement.setInt(2, boardMembership.getRole_id());
        preparedStatement.setInt(3, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO BoardMembership VALUES(?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        BoardMembership boardMembership = objectMapper.readValue(body, BoardMembership.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM BoardMembership");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setInt(2, boardMembership.getUser_id());
        preparedStatement.setInt(3, boardMembership.getRole_id());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM BoardMembership WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<BoardMembership> listOfUsers(){
        List<BoardMembership> boards = new ArrayList<>();
        try {
            Statement statement = (Statement) Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM BoardMembership");
            BoardMembership board;
            while(rs.next()) {
                board = new BoardMembershipBuilder(rs);
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
