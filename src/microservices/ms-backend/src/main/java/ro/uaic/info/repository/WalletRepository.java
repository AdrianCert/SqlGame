package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.builder.WalletBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.UserTable;
import ro.uaic.info.entity.Wallet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WalletRepository implements Repository<Wallet>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM Wallet WHERE id=?";
    private final static String update = "UPDATE Wallet SET ID = ?, balancing = ? WHERE id = ?";
    private final static String add = "INSERT INTO Wallet VALUES(?, ?)";
    private final static String max = "SELECT MAX(ID) FROM Wallet";
    private final static String delete = "DELETE FROM Wallet WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM Wallet";

    public WalletRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public Wallet getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new WalletBuilder(resultSet) : null;
    }

    @Override
    public List<Wallet> getAll() {
        List<Wallet> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new WalletBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public Wallet update(Wallet wallet) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        wallet.prepare(preparedStatement);
        preparedStatement.setInt(3, wallet.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return wallet;
    }

    @Override
    public Wallet add(Wallet wallet) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) wallet.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        wallet.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return wallet;
    }

    @Override
    public void delete(Wallet wallet) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, wallet.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
