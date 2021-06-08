package ro.uaic.info.repository;

import ro.uaic.info.builder.BoardMembershipBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.BoardMembership;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardMembershipRepository implements Repository<BoardMembership>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM BoardMembership WHERE id=?";
    private final static String update = "UPDATE BoardMembership SET ID = ?, user_id = ?, role_id = ? WHERE id = ?";
    private final static String add = "INSERT INTO BoardMembership VALUES(?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM BoardMembership";
    private final static String delete = "DELETE FROM BoardMembership WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM BoardMembership";

    public BoardMembershipRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public BoardMembership getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new BoardMembershipBuilder(resultSet) : null;
    }

    @Override
    public List<BoardMembership> getAll(){
        List<BoardMembership> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new BoardMembershipBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public BoardMembership update(BoardMembership boardMembership) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        boardMembership.prepare(preparedStatement);
        preparedStatement.setInt(4, boardMembership.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return boardMembership;
    }

    @Override
    public BoardMembership add(BoardMembership boardMembership) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) boardMembership.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        boardMembership.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return boardMembership;
    }

    @Override
    public void delete(BoardMembership boardMembership) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, boardMembership.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
