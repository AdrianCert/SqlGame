package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.SchemaLocationBuilder;
import ro.uaic.info.builder.SchemaTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.SchemaTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SchemaTableRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM SchemaTable WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        if(rd.next()) return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new SchemaTableBuilder(rd));
        return "null";
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE SchemaTable SET sgdb = ?, creation_script = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        SchemaTable schemaTable = objectMapper.readValue(body, SchemaTable.class);

        preparedStatement.setString(1, schemaTable.getSgdb());
        preparedStatement.setString(2, schemaTable.getCreation_script());
        preparedStatement.setInt(3, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO SchemaTable VALUES(?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        SchemaTable schemaTable = objectMapper.readValue(body, SchemaTable.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM SchemaTable");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setString(2, schemaTable.getSgdb());
        preparedStatement.setString(3, schemaTable.getCreation_script());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM SchemaTable WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<SchemaTable> listOfUsers(){
        List<SchemaTable> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM SchemaTable");
            SchemaTable board;
            while(rs.next()) {
                board = new SchemaTableBuilder(rs);
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
