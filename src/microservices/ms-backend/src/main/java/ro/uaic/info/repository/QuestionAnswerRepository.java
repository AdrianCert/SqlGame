package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.QuestionAnswerBuilder;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.controller.QuestionAnswearController;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.QuestionAnswer;
import ro.uaic.info.entity.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerRepository implements Repository<QuestionAnswer>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM QuestionAnswer WHERE id=?";
    private final static String update = "UPDATE QuestionAnswer SET ID = ?, value = ?, question_id = ?, user_id = ?, submit_time = ?, status = ? WHERE id = ?";
    private final static String add = "INSERT INTO QuestionAnswer VALUES(?, ?, ?, ?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM QuestionAnswer";
    private final static String delete = "DELETE FROM QuestionAnswer WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM QuestionAnswer";

    public QuestionAnswerRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public QuestionAnswer getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new QuestionAnswerBuilder(resultSet) : null;
    }

    @Override
    public List<QuestionAnswer> getAll() {
        List<QuestionAnswer> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new QuestionAnswerBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public QuestionAnswer update(QuestionAnswer questionAnswer) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        questionAnswer.prepare(preparedStatement);
        preparedStatement.setInt(7, questionAnswer.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return questionAnswer;
    }

    @Override
    public QuestionAnswer add(QuestionAnswer questionAnswer) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) questionAnswer.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        questionAnswer.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return questionAnswer;
    }

    @Override
    public void delete(QuestionAnswer questionAnswer) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, questionAnswer.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
