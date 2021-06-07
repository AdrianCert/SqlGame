package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.BoardBuilder;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.Board;
import ro.uaic.info.entity.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardRepository implements Repository<Board>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM Board WHERE id=?";
    private final static String update = "UPDATE UserTable SET ID = ?, name = ?, description = ?, owner = ? WHERE id = ?";
    private final static String add = "INSERT INTO Board VALUES(?, ?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM Board";
    private final static String delete = "DELETE FROM Board WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM Board";

    public BoardRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Board getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new BoardBuilder(resultSet) : null;
    }

    @Override
    public List<Board> getAll() {
        List<Board> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new BoardBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public Board update(Board board) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        board.prepare(preparedStatement);
        preparedStatement.setInt(5, board.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return board;
    }

    @Override
    public Board add(Board board) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) board.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        board.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return board;
    }

    @Override
    public void delete(Board board) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, board.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
