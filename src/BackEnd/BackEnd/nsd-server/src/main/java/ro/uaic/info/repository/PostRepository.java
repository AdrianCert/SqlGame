package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.PostBuilder;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.Post;
import ro.uaic.info.entity.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepository implements Repository<Post>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM Post WHERE id=?";
    private final static String update = "UPDATE Post SET ID = ?, user_id = ?, title = ?, content = ?, comments = ? WHERE id = ?";
    private final static String add = "INSERT INTO Post VALUES(?, ?, ?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM Post";
    private final static String delete = "DELETE FROM Post WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM Post";

    public PostRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public Post getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new PostBuilder(resultSet) : null;
    }

    @Override
    public List<Post> getAll() {
        List<Post> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new PostBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public Post update(Post post) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        post.prepare(preparedStatement);
        preparedStatement.setInt(6, post.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return post;
    }

    @Override
    public Post add(Post post) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) post.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        post.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return post;
    }

    @Override
    public void delete(Post post) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, post.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
