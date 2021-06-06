package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.SchemaLocationBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.SchemaLocation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SchemaLocationRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM SchemaLocation WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        SchemaLocation schemaLocation = new SchemaLocationBuilder(rd);
        return objectMapper.writeValueAsString(schemaLocation);
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE SchemaLocation SET schema_id = ?, credidential = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        SchemaLocation schemaLocation = objectMapper.readValue(body, SchemaLocation.class);

        preparedStatement.setInt(1, schemaLocation.getSchema_id());
        preparedStatement.setString(2, schemaLocation.getCredidential());
        preparedStatement.setInt(3, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO SchemaLocation VALUES(?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        SchemaLocation schemaLocation = objectMapper.readValue(body, SchemaLocation.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM SchemaLocation");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setInt(2, schemaLocation.getSchema_id());
        preparedStatement.setString(3, schemaLocation.getCredidential());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM SchemaLocation WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<SchemaLocation> listOfUsers(){
        List<SchemaLocation> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM SchemaLocation");
            SchemaLocation board;
            while(rs.next()) {
                board = new SchemaLocationBuilder(rs);
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
