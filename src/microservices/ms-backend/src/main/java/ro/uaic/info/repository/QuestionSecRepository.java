package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.Service.QuestionSecService;
import ro.uaic.info.builder.QuestionSecBuilder;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.controller.QuestionSecController;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.QuestionSec;
import ro.uaic.info.entity.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionSecRepository implements Repository<QuestionSec>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM QuestionSec WHERE id=?";
    private final static String update = "UPDATE QuestionSec SET ID = ?, question_id = ?, user_id = ?, schema_id = ?, dml_permission = ? WHERE id = ?";
    private final static String add = "INSERT INTO QuestionSec VALUES(?, ?, ?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM QuestionSec";
    private final static String delete = "DELETE FROM QuestionSec WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM QuestionSec";

    public QuestionSecRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public QuestionSec getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new QuestionSecBuilder(resultSet) : null;
    }

    @Override
    public List<QuestionSec> getAll() {
        List<QuestionSec> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new QuestionSecBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public QuestionSec update(QuestionSec questionSec) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        questionSec.prepare(preparedStatement);
        preparedStatement.setInt(6, questionSec.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return questionSec;
    }

    @Override
    public QuestionSec add(QuestionSec questionSec) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) questionSec.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        questionSec.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return questionSec;
    }

    @Override
    public void delete(QuestionSec questionSec) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, questionSec.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
