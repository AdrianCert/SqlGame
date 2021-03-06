package ro.uaic.info.HttpMessage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;
import ro.uaic.info.ConnectionToDB;

/**
 * Builder Class for HttpMessage
 */
public class HttpMessageResponseBuilder extends HttpMessageResponse {

    /**
     * Constructor with HTTP/1.1 version &and; Java NSD Server (c) 2021 - header attached
     */
    public HttpMessageResponseBuilder() {
        setHttpVersion("HTTP/1.1");
        addHeader("Server", "Java NSD Server (c) 2021");
    }

    /**
     * Setter for http version
     *
     * @param httpVersion The value for http version
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder http(String httpVersion) {
        setHttpVersion(httpVersion);
        return this;
    }

    /**
     * Setter for status code
     *
     * @param status The value for status code
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder status(String status) {
        setStatusCode(status);
        return this;
    }

    /**
     * Setter for status code
     *
     * @param status The value for status code
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder status(Integer status) {
        setStatusCode(status);
        return this;
    }

    /**
     * Setter for response phrase
     *
     * @param responsePhrase The value for response phrase
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder responsePhrase(String responsePhrase) {
        setReasonPhrase(responsePhrase);
        return this;
    }

    /**
     * Setter for adding a header to http message
     *
     * @param key   The value for header name
     * @param value The value for header value
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder header(String key, String value) {
        addHeader(key, value);
        return this;
    }

    /**
     * Setter for body message
     *
     * @param body The value for body
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder body(String body) {
        setBody(body);
        return this;
    }

    /**
     * Setter for body message
     *
     * @param body The value for body
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder body(byte[] body) {
        setBody(body);
        return this;
    }

    /**
     * Setter for body message
     *
     * @param body The value for body
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder body(File body) {
        setBody(body);
        return this;
    }

    public HttpMessageResponseBuilder constructResponse(String method, String uri, String body) {
        /*setStatusCode(400);
        if (method != null && uri != null && method.equals("GET"))
            if (uri.equals("/querry")) {
                JSONObject jo = new JSONObject(body);

                if (jo.has("querry") && !jo.getString("querry").isEmpty())
                    try {
                        ConnectionToDB con = new ConnectionToDB("jdbc:oracle:thin:@localhost:1521:XE", "STUDENT", "QWERTY");
                        String querry = con.returnJsonFromSQL(jo.getString("querry"));
                        this.body(querry)
                                .status(200);
                        con.disconnectToDatabase();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
            } else if (uri.equals("/verification")) {
                JSONObject jo = new JSONObject(body);

                if ((jo.has("sendQuerry") && !jo.getString("sendQuerry").isEmpty()) &&
                        (jo.has("correctQuerry") && !jo.getString("correctQuerry").isEmpty()))
                    try {
                        ConnectionToDB con = new ConnectionToDB("jdbc:oracle:thin:@localhost:1521:XE", "STUDENT", "QWERTY");
                        if (con.correctSQL(jo.getString("sendQuerry"), jo.getString("correctQuerry")))
                            this.status(200);
                        con.disconnectToDatabase();
                    } catch (SQLException e) {
                        //System.out.println(e.getMessage());
                    }
            }*/
        return this;
    }
}
