package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.UserTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserTableRepository {

    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM UserTable WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        UserTable userTable = new UserTableBuilder(rd);
        return objectMapper.writeValueAsString(userTable);
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE UserTable SET name = ?, surname = ?, details = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        UserTable userTable = objectMapper.readValue(body, UserTable.class);

        preparedStatement.setString(1, userTable.getName());
        preparedStatement.setString(2, userTable.getSurname());
        preparedStatement.setString(3, userTable.getDetails());
        preparedStatement.setInt(4, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO UserTable VALUES(?, ?, ?, ?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        UserTable userTable = objectMapper.readValue(body, UserTable.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM USERTABLE");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setString(2, userTable.getName());
        preparedStatement.setString(3, userTable.getSurname());
        preparedStatement.setString(4, userTable.getUser_name());
        preparedStatement.setString(5, userTable.getMail());
        preparedStatement.setString(6, userTable.getDetails());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM UserTable WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<UserTable> listOfUsers(){
        List<UserTable> persons = new ArrayList<>();
        try {
            Statement statement = (Statement) Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM UserTable");
            UserTable userTable;
            while(rs.next()) {
                userTable = new UserTableBuilder().id(rs.getInt(1))
                        .name(rs.getString(2))
                        .surname(rs.getString(3))
                        .user_name(rs.getString(4))
                        .mail(rs.getString(5))
                        .details(rs.getString(6));
                persons.add(userTable);
            }
            statement.close();
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

}
