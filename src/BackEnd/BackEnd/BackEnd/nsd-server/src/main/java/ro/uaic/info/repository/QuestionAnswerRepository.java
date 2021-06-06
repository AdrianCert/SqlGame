package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.QuestionAnswerBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.QuestionAnswer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM QuestionAnswer WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        QuestionAnswer question = new QuestionAnswerBuilder(rd);
        return objectMapper.writeValueAsString(question);
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE QuestionAnswer SET value = ?, question_id = ?, user_id = ?, submit_time = ?, status = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        QuestionAnswer questionAnswer = objectMapper.readValue(body, QuestionAnswer.class);

        preparedStatement.setString(1, questionAnswer.getValue());
        preparedStatement.setInt(2, questionAnswer.getQuestion_id());
        preparedStatement.setInt(3, questionAnswer.getUser_id());
        preparedStatement.setDate(4, (Date) questionAnswer.getSubmit_time());
        preparedStatement.setString(5, questionAnswer.getStatus());
        preparedStatement.setInt(6, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO QuestionAnswer VALUES(?, ?, ?, ?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        QuestionAnswer questionAnswer = objectMapper.readValue(body, QuestionAnswer.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM QuestionAnswer");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setString(2, questionAnswer.getValue());
        preparedStatement.setInt(3, questionAnswer.getQuestion_id());
        preparedStatement.setInt(4, questionAnswer.getUser_id());
        preparedStatement.setDate(5, (Date) questionAnswer.getSubmit_time());
        preparedStatement.setString(6, questionAnswer.getStatus());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM QuestionAnswer WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<QuestionAnswer> listOfUsers(){
        List<QuestionAnswer> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM QuestionAnswer");
            QuestionAnswer board;
            while(rs.next()) {
                board = new QuestionAnswerBuilder(rs);
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
