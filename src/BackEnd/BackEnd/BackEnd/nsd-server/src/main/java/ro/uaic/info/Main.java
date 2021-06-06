package ro.uaic.info;

import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.database.Database;
import ro.uaic.info.entity.UserTable;
import ro.uaic.info.repository.UserTableRepository;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static List<UserTable> getAll(){
        List<UserTable> persons = new ArrayList<>();
        try {
            Statement statement = (Statement) Database.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM UserTable");
            UserTable userTable;
            while(rs.next()) {
                userTable = new UserTableBuilder().id(rs.getInt(1))
                                                  .name(rs.getString(2))
                                                  .surname(rs.getString(3))
                                                  .user_name(rs.getString(4))
                                                  .mail(rs.getString(5))
                                                  .details(rs.getString(6));
                persons.add(userTable);
            }
            statement.close();
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public static UserTable getById(int id) throws SQLException{
        PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM UserTable WHERE id=?");
        statement.setInt(1, id);
        ResultSet rd = statement.executeQuery();
        return new UserTableBuilder(rd);
    }
    // ultimu max(id) + 1
    //adaug sqlex
    public static void add(UserTable userTable) throws SQLException{
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("INSERT INTO UserTable VALUES(?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, userTable.getID());
        preparedStatement.setString(2, userTable.getName());
        preparedStatement.setString(3, userTable.getSurname());
        preparedStatement.setString(4, userTable.getUser_name());
        preparedStatement.setString(5, userTable.getMail());
        preparedStatement.setString(6, userTable.getDetails());
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
    }

    public static void update(UserTable userTable) throws SQLException{
        int id = userTable.getID();
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("UPDATE UserTable SET name = ?, surname = ?, details = ? WHERE id = ?");
        preparedStatement.setString(1, userTable.getName());
        preparedStatement.setString(2, userTable.getSurname());
        preparedStatement.setString(3, userTable.getDetails());
        preparedStatement.setInt(4, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
    }

    public static void  delete(int  id) throws SQLException{
        PreparedStatement preparedStatement = Database.getConnection()
                .prepareStatement("DELETE FROM UserTable WHERE ID = ?");
        preparedStatement.setInt(1, id);
        if(preparedStatement.executeUpdate() != 0) throw new SQLException();
        preparedStatement.close();
    }

    static void Afiseaza(List<UserTable>d) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(d);
        System.out.println("\n" + jsonInString);
    }

    public static void main(String[] args) throws IOException, SQLException {
        UserTable d = new UserTableBuilder().id(10)
                                            .name("salut")
                                            .surname("test")
                                            .user_name("te")
                                            .mail("res.com")
                                            .details("wow");
        System.out.println(UserTableRepository.getAll());
    }
}
