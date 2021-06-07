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

    private Boolean matchingRow(ResultSet rs1, ResultSet rs2, ResultSetMetaData rsmd, Integer columnNumber) throws SQLException {
        for (int i = 1; i <= columnNumber; i++)
            switch (rsmd.getColumnType(i)) {
                case 1:
                case 12:
                    if (!rs1.getString(i).equals(rs2.getString(i)))
                        return false;
                    break;
                case 2:
                    if (rs1.getInt(i) != rs2.getInt(i))
                        return false;
                    break;
                case 16:
                    if (rs1.getBoolean(i) != rs2.getBoolean(i))
                        return false;
                    break;
                case 93:
                    if (!rs1.getDate(i).toString().equals(rs2.getDate(i).toString()))
                        return false;
                    break;
                case 8:
                    if (rs1.getDouble(i) != rs2.getDouble(i))
                        return false;
                    break;
                case 6:
                    if (rs1.getFloat(i) != rs2.getFloat(i))
                        return false;
                    break;
            }
        return true;
    }

    public Boolean correctSQL(String sentSQL, String correctSQL) throws SQLException {
        LinkedList<String> sentColumnNames = new LinkedList<>();
        LinkedList<String> correctColumnNames = new LinkedList<>();
        Integer sentColumnCount, correctColumnCount;

        PreparedStatement pstmt = conn.prepareStatement(sentSQL);
        ResultSet sentRS = pstmt.executeQuery();
        ResultSetMetaData sentRSMD = sentRS.getMetaData();
        sentColumnCount = sentRSMD.getColumnCount();

        pstmt = conn.prepareStatement(correctSQL);
        ResultSet correctRS = pstmt.executeQuery();
        ResultSetMetaData correctRSMD = correctRS.getMetaData();
        correctColumnCount = correctRSMD.getColumnCount();

        if (sentColumnCount != correctColumnCount)
            return false;

        for (int i = 1; i <= sentColumnCount; i++)
            if (sentRSMD.getColumnType(i) != correctRSMD.getColumnType(i))
                return false;

        while (sentRS.next() && correctRS.next()) {
            if (!matchingRow(sentRS, correctRS, sentRSMD, sentColumnCount))
                return false;
        }

        if (sentRS.next())
            return false;

        if (correctRS.next())
            return false;

        return true;
    }

    public String returnJsonFromSQL(String sql) throws SQLException {
        String response = "";
        LinkedList<String> columnNames = new LinkedList<>();
        Integer columnCount;
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        columnCount = rsmd.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(rsmd.getColumnName(i));
        }

        while (rs.next()) {
            response += "{";
            for (int i = 1; i <= columnCount; i++) {
                response += "\"" + columnNames.get(i - 1) + "\":\"";
                switch (rsmd.getColumnType(i)) {
                    case 1:
                        response += rs.getString(i);
                        break;
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
        if (response.length() > 0) {
            response = response.substring(0, response.length() - 1);
            response = "[" + response + "]";
        }
        return response;
    }

    public void disconnectToDatabase() throws SQLException {
        conn.close();
    }
}
