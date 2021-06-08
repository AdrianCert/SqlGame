package ro.uaic.info.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.RoleBuilder;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.Role;
import ro.uaic.info.entity.UserTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository implements Repository<Role>{

    private Connection conn;

    private final static String selectById = "SELECT * FROM Role WHERE id=?";
    private final static String update = "UPDATE Role SET ID = ?, title = ?, description = ? WHERE id = ?";
    private final static String add = "INSERT INTO Role VALUES(?, ?, ?)";
    private final static String max = "SELECT MAX(ID) FROM Role";
    private final static String delete = "DELETE FROM Role WHERE ID = ?";
    private final static String selectAll = "SELECT * FROM Role";

    public RoleRepository(){
        try{
            conn = Database.getConnection();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public Role getById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? new RoleBuilder(resultSet) : null;
    }

    @Override
    public List<Role> getAll() {
        List<Role> entities = new ArrayList<>();
        try(PreparedStatement statement =  conn.prepareStatement(selectAll);
            ResultSet resultSet = statement.executeQuery(selectAll);
        ) {
            while(resultSet.next()) {
                entities.add( new RoleBuilder(resultSet));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public Role update(Role role) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(update);
        role.prepare(preparedStatement);
        preparedStatement.setInt(4, role.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
        return role;
    }

    @Override
    public Role add(Role role) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(max);
        if(rs.next()) role.setID(rs.getInt(1) + 1);

        PreparedStatement preparedStatement = conn.prepareStatement(add);
        role.prepare(preparedStatement);
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();

        preparedStatement.close();
        return role;
    }

    @Override
    public void delete(Role role) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(delete);
        preparedStatement.setInt(1, role.getID());
        if(preparedStatement.executeUpdate() == 0) throw new SQLException();
        preparedStatement.close();
    }
}
