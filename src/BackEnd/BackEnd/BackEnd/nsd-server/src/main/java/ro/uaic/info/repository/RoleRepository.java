package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.RoleBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM Role WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        Role role = new RoleBuilder(rd);
        return objectMapper.writeValueAsString(role);
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE Role SET title = ?, description = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        Role role = objectMapper.readValue(body, Role.class);

        preparedStatement.setString(1, role.getTitle());
        preparedStatement.setString(2, role.getDescription());
        preparedStatement.setInt(3, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO Role VALUES(?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        Role role = objectMapper.readValue(body, Role.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM Role");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setString(2, role.getTitle());
        preparedStatement.setString(3, role.getDescription());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM Role WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<Role> listOfUsers(){
        List<Role> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Role");
            Role board;
            while(rs.next()) {
                board = new RoleBuilder(rs);
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
