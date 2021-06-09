package ro.uaic.info.repository;

import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserTableRepository implements Repository<UserTable> {

    private Connection conn;

    private final static String selectById = "SELECT * FROM UserTable WHERE ID = ?";
    private final static String update = "UPDATE UserTable SET ID = ?, name = ?, surname = ?, user_name = ?, mail = ?, details = ? WHERE ID = ?";
    private final static String add = "INSERT INTO UserTable VALUES(?, ?, ?, ?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM USERTABLE";
    private final static String delete = "DELETE FROM UserTable WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM UserTable";

    public UserTableRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public UserTable getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new UserTableBuilder(resultSet) : null;
    }

    @Override
    public List<UserTable> getAll() {
        List<UserTable> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
                ResultSet resultSet = statement.executeQuery(selectAll);
                ) {
            while(resultSet.next()) {
                entities.add( new UserTableBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public UserTable update(UserTable userTable) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        userTable.prepare(preparedStatement);
        preparedStatement.setInt(7, userTable.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return userTable;
    }

    @Override
    public UserTable add(UserTable userTable) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) userTable.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        userTable.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return userTable;
    }

    @Override
    public void delete(UserTable userTable) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, userTable.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
