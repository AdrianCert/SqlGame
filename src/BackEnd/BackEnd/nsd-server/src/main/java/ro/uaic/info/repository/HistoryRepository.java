package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.HistoryBuilder;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.History;
import ro.uaic.info.entity.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryRepository implements Repository<History>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM History WHERE id=?";
    private final static String update = "UPDATE History SET ID = ?,  user_id = ?, action = ?  WHERE id = ?";
    private final static String add = "INSERT INTO History VALUES(?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM History";
    private final static String delete = "DELETE FROM History WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM History";

    public HistoryRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public History getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new HistoryBuilder(resultSet) : null;
    }

    @Override
    public List<History> getAll() {
        List<History> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new HistoryBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public History update(History history) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        history.prepare(preparedStatement);
        preparedStatement.setInt(4, history.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return history;
    }

    @Override
    public History add(History history) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) history.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        history.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return history;
    }

    @Override
    public void delete(History history) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, history.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
