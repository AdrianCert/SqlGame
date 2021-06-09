package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.SchemaTableBuilder;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.SchemaTable;
import ro.uaic.info.entity.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchemaTableRepository implements Repository<SchemaTable>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM SchemaTable WHERE id=?";
    private final static String update = "UPDATE SchemaTable SET ID = ?, sgdb = ?, creation_script = ? WHERE id = ?";
    private final static String add = "INSERT INTO SchemaTable VALUES(?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM SchemaTable";
    private final static String delete = "DELETE FROM SchemaTable WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM SchemaTable";

    public SchemaTableRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public SchemaTable getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new SchemaTableBuilder(resultSet) : null;
    }

    @Override
    public List<SchemaTable> getAll() {
        List<SchemaTable> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new SchemaTableBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public SchemaTable update(SchemaTable schemaTable) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        schemaTable.prepare(preparedStatement);
        preparedStatement.setInt(4, schemaTable.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return schemaTable;
    }

    @Override
    public SchemaTable add(SchemaTable schemaTable) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) schemaTable.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        schemaTable.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return schemaTable;
    }

    @Override
    public void delete(SchemaTable schemaTable) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, schemaTable.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
