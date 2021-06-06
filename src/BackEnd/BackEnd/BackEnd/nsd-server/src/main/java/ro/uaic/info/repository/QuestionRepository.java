package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.QuestionAnswerBuilder;
import ro.uaic.info.builder.QuestionBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.Question;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    public static String getById(String id) throws SQLException, JsonProcessingException {
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM Question WHERE id=?");
        statement.setInt(1, Integer.parseInt(id));
        ResultSet rd = statement.executeQuery();
        ObjectMapper objectMapper = new ObjectMapper();
        if(rd.next()) return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new QuestionBuilder(rd));
        return "null";
    }

    public static String update(String id, String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE Question SET user_id = ?, pass = ?, pass_update_mail = ?, recovery_mail = ?, recovery_code = ? WHERE id = ?");

        ObjectMapper objectMapper = new ObjectMapper();
        Question question = objectMapper.readValue(body, Question.class);

        preparedStatement.setInt(1, question.getUser_id());
        preparedStatement.setString(2, question.getPass());
        preparedStatement.setString(3, question.getPass_update_mail());
        preparedStatement.setString(4, question.getRecovery_mail());
        preparedStatement.setString(5, question.getRecovery_code());
        preparedStatement.setInt(6, Integer.parseInt(id));
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();

        return body;
    }

    public static String add(String body) throws SQLException, JsonProcessingException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO Question VALUES(?, ?, ?, ?, ?, ?)");

        ObjectMapper objectMapper = new ObjectMapper();
        Question question = objectMapper.readValue(body, Question.class);

        Statement statement = Database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT MAX(ID) FROM Question");

        preparedStatement.setInt(1, rs.getInt(1) + 1);
        preparedStatement.setInt(2, question.getUser_id());
        preparedStatement.setString(3, question.getPass());
        preparedStatement.setString(4, question.getPass_update_mail());
        preparedStatement.setString(5, question.getRecovery_mail());
        preparedStatement.setString(6, question.getRecovery_code());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();

        preparedStatement.close();
        rs.close();

        return body;
    }

    public static String delete(int id) throws SQLException {
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM Question WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
        return "deleted";
    }

    public static String getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOfUsers());
    }

    public static List<Question> listOfUsers(){
        List<Question> boards = new ArrayList<>();
        try {
            Statement statement = Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Question");
            Question board;
            while(rs.next()) {
                board = new QuestionBuilder(rs);
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
