package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.PostBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.Post;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM Post WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        if(rd.next()) return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new PostBuilder(rd));
        return "null";
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE Post SET user_id = ?, title = ?, content = ?, comments = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        Post post = objectMapper.readValue(body, Post.class);

        preparedStatement.setInt(1, post.getUser_id());
        preparedStatement.setString(2, post.getTitle());
        preparedStatement.setString(3, post.getContent());
        preparedStatement.setString(4, post.getComments());
        preparedStatement.setInt(5, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO Post VALUES(?, ?, ?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        Post post = objectMapper.readValue(body, Post.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM Post");

        if(rs.next()){
            preparedStatement.setInt(1, rs.getInt(1) + 1);
            preparedStatement.setInt(2, post.getUser_id());
            preparedStatement.setString(3, post.getTitle());
            preparedStatement.setString(4, post.getContent());
            preparedStatement.setString(5, post.getComments());
            if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        }

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM Post WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<Post> listOfUsers(){
        List<Post> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Post");
            Post board;
            while(rs.next()) {
                board = new PostBuilder(rs);
                boards.add(board);
            }
            statement.close();
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }
}
