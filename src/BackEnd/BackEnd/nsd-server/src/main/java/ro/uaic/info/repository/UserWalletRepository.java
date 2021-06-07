package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.builder.UserWalletBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.UserTable;
import ro.uaic.info.entity.UserWallet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserWalletRepository implements Repository<UserWallet>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM UserWallet WHERE id=?";
    private final static String update = "UPDATE UserWallet SET ID = ?, user_id = ?, wallet_id = ? WHERE id = ?";
    private final static String add = "INSERT INTO UserWallet VALUES(?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM UserWallet";
    private final static String delete = "DELETE FROM UserWallet WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM UserWallet";

    public UserWalletRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public UserWallet getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new UserWalletBuilder(resultSet) : null;
    }

    @Override
    public List<UserWallet> getAll() {
        List<UserWallet> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new UserWalletBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public UserWallet update(UserWallet userWallet) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        userWallet.prepare(preparedStatement);
        preparedStatement.setInt(4, userWallet.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return userWallet;
    }

    @Override
    public UserWallet add(UserWallet userWallet) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) userWallet.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        userWallet.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return userWallet;
    }

    @Override
    public void delete(UserWallet userWallet) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, userWallet.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
