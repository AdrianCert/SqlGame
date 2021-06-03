package ro.uaic.info;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.LinkedList;

/**
 * Class contains means to connect to Database
 */
public class ConnectionToDB {

    Connection conn;

    public ConnectionToDB(String url, String user, String pass) throws SQLException {
        this.conn = DriverManager.getConnection(url, user, pass);
    }

    public String returnJsonFromSQL(String sql) throws SQLException {
        String response = "[";
        LinkedList<String> columnNames = new LinkedList<>();
        Integer columnCount;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        columnCount = rsmd.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(rsmd.getColumnName(i));
            //System.out.println(rsmd.getColumnType(i));
        }

        while (rs.next()) {
            response += "{";
            for (int i = 1; i <= columnCount; i++) {
                response += "\"" + columnNames.get(i - 1) + "\":\"";
                switch (rsmd.getColumnType(i)) {
                    case 2:
                        response += rs.getInt(i);
                        break;
                    case 16:
                        response += rs.getBoolean(i);
                        break;
                    case 93:
                        response += rs.getDate(i).toString();
                        break;
                    case 8:
                        response += rs.getDouble(i);
                        break;
                    case 6:
                        response += rs.getFloat(i);
                        break;
                    case 12:
                        response += rs.getString(i);
                        break;
                }
                response += "\"";
                if (i != columnCount)
                    response += ",";
            }
            response += "},";
        }
        response = response.substring(0, response.length() - 1);
        response += "]";

        return response;
    }

    public void disconnectToDatabase() throws SQLException {
        conn.close();
    }
}
