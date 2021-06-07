package ro.uaic.info.repository;

import ro.uaic.info.builder.UserSecBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.UserSec;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSecRepository implements Repository<UserSec>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM UserSec WHERE id=?";
    private final static String update = "UPDATE UserSec SET ID = ?, user_id = ?, pass = ?, pass_update_at = ?, recovery_mail = ?, recovery_code = ? WHERE id = ?";
    private final static String add = "INSERT INTO UserSec VALUES(?, ?, ?, ?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM UserSec";
    private final static String delete = "DELETE FROM UserSec WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM UserSec";

    public UserSecRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public UserSec getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new UserSecBuilder(resultSet) : null;
    }

    @Override
    public List<UserSec> getAll() {
        List<UserSec> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new UserSecBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public UserSec update(UserSec userSec) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        userSec.prepare(preparedStatement);
        preparedStatement.setInt(7, userSec.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return userSec;
    }

    @Override
    public UserSec add(UserSec userSec) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) userSec.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        userSec.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return userSec;
    }

    @Override
    public void delete(UserSec userSec) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, userSec.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
