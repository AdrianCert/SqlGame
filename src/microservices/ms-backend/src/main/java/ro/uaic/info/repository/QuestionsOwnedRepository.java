package ro.uaic.info.repository;

import ro.uaic.info.builder.QuestionsOwnedBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.QuestionsOwned;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class QuestionsOwnedRepository implements Repository<QuestionsOwned> {

    private Connection conn;

    private final static String selectById = "SELECT * FROM QuestionsOwned WHERE ID = ?";
    private final static String update = "UPDATE QuestionsOwned SET ID = ?, user_id = ?, question_id = ?, solution = ?, solved = ?, payment_buy = ?, payment_rew = ? WHERE id = ?";
    private final static String add = "INSERT INTO QuestionsOwned VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM QuestionsOwned";
    private final static String delete = "DELETE FROM QuestionsOwned WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM QuestionsOwned";

    public QuestionsOwnedRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public QuestionsOwned getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new QuestionsOwnedBuilder(resultSet) : null;
    }

    @Override
    public List<QuestionsOwned> getAll() {
        List<QuestionsOwned> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new QuestionsOwnedBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public QuestionsOwned update(QuestionsOwned d) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        d.prepare(preparedStatement);
        preparedStatement.setInt(4, d.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return d;
    }

    @Override
    public QuestionsOwned add(QuestionsOwned d) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) d.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        d.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return d;
    }

    @Override
    public void delete(QuestionsOwned d) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, d.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
