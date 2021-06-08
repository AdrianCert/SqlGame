package ro.uaic.info.HttpMessage;

import org.apache.commons.io.FileUtils;
import ro.uaic.info.HttpMessage.HttpMessage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Http Response Message Class Handler
 *
 * @author Adrian-Valentin Panaintescu
 */
public class HttpMessageResponse {

    /**
     * The http version
     */
    private String httpVersion;
    /**
     * The status code
     */
    private String statusCode;
    /**
     * The reason phrase
     */
    private String reasonPhrase;

    /**
     * Header map
     */
    private Map<String, String> headers;
    /**
     * Body value
     */
    private byte[] body;

    /**
     * Constructor for HttpMessageResponse
     */
    public HttpMessageResponse() {
        setHeaders(new HashMap<>());
    }

    /**
     * Constructor for HttpMessageResponse
     * @param httpVersion The html version
     */
    public HttpMessageResponse(String httpVersion) {
        this();
        setHttpVersion(httpVersion);
    }

    /**
     * Sent the response through a socket
     * @param socket The socket connected to client
     */
    public void send(Socket socket) {
        try(PrintWriter controlBuffer = new PrintWriter(socket.getOutputStream());
            BufferedOutputStream dataBuffer = new BufferedOutputStream(socket.getOutputStream())
            ) {
            sendStatusLine(controlBuffer);
            sendHeaders(controlBuffer);
            controlBuffer.flush();

            dataBuffer.write(body, 0, body.length);
            dataBuffer.flush();

        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    /**
     * Send the status line
     * @param outputBuffer The buffer where the status line is send
     */
    private void sendStatusLine(PrintWriter outputBuffer) {
        outputBuffer.println(httpVersion + " " + statusCode + " " + reasonPhrase);
    }

    /**
     * Send the headers
     * @param outputBuffer The buffer where the status line is send
     */
    private void sendHeaders(PrintWriter outputBuffer) {
        addHeader("Date", new Date().toString());
        for(Map.Entry<String, String> header : headers.entrySet()) {
            outputBuffer.println(header.getKey() + ": " + header.getValue());
        }
        outputBuffer.println();
    }

    /**
     * Setter for http version
     * @param httpVersion The value for http version
     */
    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    /**
     * Setter for status code
     * @param statusCode The status code value
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        setReasonPhrase(HttpMessage.getStandardReasonPhrase(Integer.valueOf(statusCode)));
    }

    /**
     * Setter for status code
     * @param statusCode The status code value
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = String.valueOf(statusCode);
        setReasonPhrase(HttpMessage.getStandardReasonPhrase(statusCode));
    }

    /**
     * Setter for reason phrase
     * @param reasonPhrase The reason phrase value
     */
    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    /**
     * Setter for headers map
     * @param headers The headers map value
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * Add a header to header map
     * @param key The header key
     * @param value The header value
     */
    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    /**
     * Setter for body content
     * @param content The body content
     */
    public void setBody(String content) {
        // if the Content-type header is present don't overwrite value
        if (!headers.containsKey(HttpMessage.HEADER.CONTENT_TYPE)) {
            addHeader(HttpMessage.HEADER.CONTENT_TYPE, "text/plain");
        }
        setBody(content.getBytes());
    }

    /**
     * Setter for body content
     * @param path The file path with the content
     */
    public void setBody(Path path) {
        setBody(path.toFile());
    }

    /**
     * Setter for body content
     * @param body The body value
     */
    public void setBody(byte[] body) {
        assert body != null;
        this.body = body;
        addHeader("Content-length", String.valueOf(body.length));
    }

    /**
     * Setter for body content
     * @param file The file with body content
     */
    public void setBody(File file) {
        try {
            setBody(FileUtils.readFileToByteArray(file));
            addHeader("Content-type", URLConnection.guessContentTypeFromName(file.getName()));
        } catch (IOException e) {
            setBody(new byte[] {});
        }
    }

    /**
     * Getter for http version
     * @return The value for http version
     */
    public String getHttpVersion() {
        return httpVersion;
    }

    /**
     * Getter for status code
     * @return The value for status code
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Getter for reason phrase
     * @return The value for reason phrase
     */
    public String getReasonPhrase() {
        return reasonPhrase;
    }

    /**
     * Getter for headers map
     * @return The value for headers map
     */
    protected Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Getter for http body message
     * @return The value for http body message
     */
    public byte[] getBody() {
        return body;
    }
}
