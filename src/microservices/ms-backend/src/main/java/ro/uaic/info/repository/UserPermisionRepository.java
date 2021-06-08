package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.UserPermisionBuilder;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.UserPermision;
import ro.uaic.info.entity.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserPermisionRepository implements Repository<UserPermision>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM UserPermision WHERE id=?";
    private final static String update = "UPDATE UserPermision SET ID = ?, user_id = ?, role_id = ?, expiration = ? WHERE id = ?";
    private final static String add = "INSERT INTO UserPermision VALUES(?, ?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM UserPermision";
    private final static String delete = "DELETE FROM UserPermision WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM UserPermision";

    public UserPermisionRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public UserPermision getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new UserPermisionBuilder(resultSet) : null;
    }

    @Override
    public List<UserPermision> getAll() {
        List<UserPermision> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new UserPermisionBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public UserPermision update(UserPermision userPermision) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        userPermision.prepare(preparedStatement);
        preparedStatement.setInt(5, userPermision.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return userPermision;
    }

    @Override
    public UserPermision add(UserPermision userPermision) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) userPermision.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        userPermision.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return userPermision;
    }

    @Override
    public void delete(UserPermision userPermision) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, userPermision.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
