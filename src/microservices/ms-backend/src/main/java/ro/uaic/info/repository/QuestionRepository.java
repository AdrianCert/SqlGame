package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.QuestionBuilder;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.Question;
import ro.uaic.info.entity.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepository implements Repository<Question>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM Question WHERE id=?";
    private final static String update = "UPDATE Question SET ID = ?, title = ?, description = ?, solution = ?, value = ?, reward = ? WHERE ID = ?";
    private final static String add = "INSERT INTO Question VALUES(?, ?, ?, ?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM Question";
    private final static String delete = "DELETE FROM Question WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM Question";

    public QuestionRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public Question getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new QuestionBuilder(resultSet) : null;
    }

    @Override
    public List<Question> getAll() {
        List<Question> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new QuestionBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public Question update(Question question) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        question.prepare(preparedStatement);
        preparedStatement.setInt(7, question.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return question;
    }

    @Override
    public Question add(Question question) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) question.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        question.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return question;
    }

    @Override
    public void delete(Question question) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, question.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
