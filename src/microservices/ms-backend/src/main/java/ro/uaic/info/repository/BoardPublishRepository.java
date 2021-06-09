package ro.uaic.info.repository;

import ro.uaic.info.builder.BoardPublishBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.BoardPublish;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardPublishRepository implements Repository<BoardPublish>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM BoardPublish WHERE id=?";
    private final static String update = "UPDATE BoardPublish SET ID = ?, publish_at = ?, user_id = ?, board_id = ?, question_id = ?, post_id = ?, valid_field = ?, pub_field = ?  WHERE id = ?";
    private final static String add = "INSERT INTO BoardPublish VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM BoardPublish";
    private final static String delete = "DELETE FROM BoardPublish WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM BoardPublish";

    public BoardPublishRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public BoardPublish getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new BoardPublishBuilder(resultSet) : null;
    }

    @Override
    public List<BoardPublish> getAll(){
        List<BoardPublish> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new BoardPublishBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public BoardPublish update(BoardPublish boardPublish) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        boardPublish.prepare(preparedStatement);
        preparedStatement.setInt(9, boardPublish.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return boardPublish;
    }

    @Override
    public BoardPublish add(BoardPublish boardPublish) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) boardPublish.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        boardPublish.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return boardPublish;
    }

    @Override
    public void delete(BoardPublish boardPublish) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, boardPublish.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
