package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.SchemaLocationBuilder;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.SchemaLocation;
import ro.uaic.info.entity.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchemaLocationRepository implements Repository<SchemaLocation>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM SchemaLocation WHERE id=?";
    private final static String update = "UPDATE SchemaLocation SET ID = ?, schema_id = ?, credidential = ? WHERE id = ?";
    private final static String add = "INSERT INTO SchemaLocation VALUES(?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM SchemaLocation";
    private final static String delete = "DELETE FROM SchemaLocation WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM SchemaLocation";

    public SchemaLocationRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public SchemaLocation getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new SchemaLocationBuilder(resultSet) : null;
    }

    @Override
    public List<SchemaLocation> getAll() {
        List<SchemaLocation> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new SchemaLocationBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public SchemaLocation update(SchemaLocation schemaLocation) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        schemaLocation.prepare(preparedStatement);
        preparedStatement.setInt(4, schemaLocation.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return schemaLocation;
    }

    @Override
    public SchemaLocation add(SchemaLocation schemaLocation) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) schemaLocation.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        schemaLocation.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return schemaLocation;
    }

    @Override
    public void delete(SchemaLocation schemaLocation) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, schemaLocation.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
