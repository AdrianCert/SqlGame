package ro.uaic.info;

import org.json.JSONObject;
import ro.uaic.info.HttpMessage.HttpMessage;
import ro.uaic.info.HttpMessage.HttpMessageRequest;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.LinkedList;

/**
 * Class contains means to connect to Database
 */
public class ConnectionToDB {

    private Connection conn;
    private HttpMessageRequest request;
    private String statusCode;
    private String body;

    public ConnectionToDB(HttpMessageRequest request) {
        this.request = request;
        this.body = "";
        setStatusCode(400);
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = String.valueOf(statusCode);
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
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
                    if (rs1.getDate(i) == null)
                        return rs2.getDate(i) == null;
                    if (rs2.getDate(i) == null)
                        return false;
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

        if (correctRS.getRow() == 0)
            return false;

        if (sentRS.getRow() == 0) {
            correctRS.next();
            if (correctRS.getRow() != 0)
                return false;
        }

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
                response += "\"" + columnNames.get(i - 1) + "\":";
                switch (rsmd.getColumnType(i)) {
                    case 1:
                    case 12:
                        if (rs.getString(i) == null)
                            response += "\"\"";
                        else
                            response += "\"" + rs.getString(i) + "\"";
                        break;
                    case 2:
                        response += rs.getInt(i);
                        break;
                    case 16:
                        response += rs.getBoolean(i);
                        break;
                    case 93:
                        if (rs.getDate(i) != null)
                            response += rs.getDate(i).toString();
                        break;
                    case 8:
                        response += rs.getDouble(i);
                        break;
                    case 6:
                        response += rs.getFloat(i);
                        break;
                }
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

    public void constructResponse() {
        if (request.getBody().isEmpty())
            return;

        JSONObject jo = new JSONObject(request.getBody());

        if (!queryHasCredentials(jo))
            return;

        if (request.getMethod() != null && request.getUri() != null && request.getMethod().equals("POST"))
            if (request.getUri().equals("/query")) {

                if (jo.has("query") && !jo.getString("query").isEmpty())
                    try {
                        connectToDatabase(jo);
                        this.body = "{\"error\":false, \"entity\":" + returnJsonFromSQL(jo.getString("query")) + "}";
                        setStatusCode(200);
                        disconnectToDatabase();
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                        this.body = "{\"error\":true, \"errorMesage\":\"" + e.getMessage().substring(0, e.getMessage().length() - 1) + "\"}";
                        setStatusCode(200);
                    }
            } else if (request.getUri().equals("/verification")) {

                if ((jo.has("sendQuery") && !jo.getString("sendQuery").isEmpty()) &&
                        (jo.has("correctQuery") && !jo.getString("correctQuery").isEmpty()))
                    try {
                        connectToDatabase(jo);
                        setStatusCode(200);
                        if (correctSQL(jo.getString("sendQuery"), jo.getString("correctQuery"))) {
                            this.body = "{\"accepted\":true, \"error\":false}";
                        } else {
                            this.body = "{\"accepted\":false, \"error\":false}";
                        }
                        disconnectToDatabase();
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                        this.body = "{\"accepted\":false, \"error\":true, \"errorMesage\":\"" + e.getMessage().
                                substring(0, e.getMessage().length() - 1) + "\"}";
                        setStatusCode(200);
                    }
            }
    }

    private Boolean queryHasCredentials(JSONObject jo) {
        return jo.has("sgbd") && !jo.getString("sgbd").isEmpty() && jo.has("credentials")
                && jo.getJSONObject("credentials").has("user")
                && !jo.getJSONObject("credentials").getString("user").isEmpty()
                && jo.getJSONObject("credentials").has("pass")
                && !jo.getJSONObject("credentials").getString("pass").isEmpty();
    }

    private String getDBConnectionURL(String name) {
        switch (name.toUpperCase()) {
            case "ORACLE":
                return "jdbc:oracle:thin:@localhost:1521:XE";
            case "MYSQL":
                return "jdbc:mysql://localhost:3306/usertest";
            default:
                return null;
        }
    }

    public void connectToDatabase(JSONObject jo) throws SQLException {
        this.conn = DriverManager.getConnection(getDBConnectionURL(jo.getString("sgbd")),
                jo.getJSONObject("credentials").getString("user"), jo.getJSONObject("credentials").getString("pass"));
    }

    public void disconnectToDatabase() throws SQLException {
        conn.close();
    }
}
