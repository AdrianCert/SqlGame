package ro.uaic.info.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;
    private static final String ADDR = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "dragos";
    private static final String PASS = "dragos";

    private Database() {

    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(ADDR, USER, PASS);
        }
        return connection;
    }
}
