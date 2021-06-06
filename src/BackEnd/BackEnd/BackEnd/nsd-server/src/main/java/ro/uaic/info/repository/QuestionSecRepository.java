package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.QuestionBuilder;
import ro.uaic.info.builder.QuestionSecBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.QuestionSec;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionSecRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM QuestionSec WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        if(rd.next()) return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new QuestionSecBuilder(rd));
        return "null";
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE QuestionSec SET question_id = ?, user_id = ?, schema_id = ?, dml_permission = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        QuestionSec questionSec = objectMapper.readValue(body, QuestionSec.class);

        preparedStatement.setInt(1, questionSec.getQuestion_id());
        preparedStatement.setInt(2, questionSec.getUser_id());
        preparedStatement.setInt(3, questionSec.getSchema_id());
        preparedStatement.setString(4, questionSec.getDml_permission());
        preparedStatement.setInt(5, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO QuestionSec VALUES(?, ?, ?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        QuestionSec questionSec = objectMapper.readValue(body, QuestionSec.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM QuestionSec");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setInt(2, questionSec.getQuestion_id());
        preparedStatement.setInt(3, questionSec.getUser_id());
        preparedStatement.setInt(4, questionSec.getSchema_id());
        preparedStatement.setString(5, questionSec.getDml_permission());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM QuestionSec WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<QuestionSec> listOfUsers(){
        List<QuestionSec> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM QuestionSec");
            QuestionSec board;
            while(rs.next()) {
                board = new QuestionSecBuilder(rs);
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
