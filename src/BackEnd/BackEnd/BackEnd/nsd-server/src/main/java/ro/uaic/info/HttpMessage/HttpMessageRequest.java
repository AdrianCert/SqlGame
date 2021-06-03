package ro.uaic.info.HttpMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Http Request Message Class Handler
 *
 * @author Adrian-Valentin Panaintescu
 */
public class HttpMessageRequest {

    /**
     * The method
     */
    private String method;
    /**
     * Http version
     */
    private String httpVersion;
    /**
     * The uri value
     */
    private String uri;
    /**
     * The body content
     */
    private String body;
    /**
     * The headers map
     */
    private Map<String,String> headers;

    /**
     * Read a http request message from a buffer
     * @param inputBuffer A BufferReader for reception http message
     * @return HttpMessageRequest instances with details analysed
     * @throws IOException Failing to read from buffer, incorrect message sent
     */
    public static HttpMessageRequest of(BufferedReader inputBuffer) throws IOException {
        HttpMessageRequest httpMessageRequest = new HttpMessageRequest();
        httpMessageRequest.readRequestLine(inputBuffer.readLine());
        httpMessageRequest.readHeader(inputBuffer);
        httpMessageRequest.readBody(inputBuffer);
        return httpMessageRequest;
    }

    /**
     * Read the request line and bind into object
     * @param input The request line string
     */
    private void readRequestLine(String input) {
        StringTokenizer tokenizer   = new StringTokenizer(input);
        this.method                 = tokenizer.nextToken().toUpperCase();
        this.uri                    = tokenizer.nextToken();
        this.httpVersion            = tokenizer.nextToken().toUpperCase();
    }

    /**
     * Read a header line and append to the headers map
     * @param input The header line
     */
    private void readHeaderLine(String input) {
        StringTokenizer tokenizer   = new StringTokenizer(input, ": ");
        this.headers.put( tokenizer.nextToken(),tokenizer.nextToken());
    }

    /**
     * Read the header part from the request message
     * @param in The buffer where the request message is read
     * @throws IOException Failing to read from buffer
     */
    private void readHeader(BufferedReader in) throws IOException {
        headers = new HashMap<>();
        // read line by line
        while(in.ready()) {
            String line = in.readLine();
            if( line.length() == 0) break;
            readHeaderLine(line);
        }
    }

    /**
     * Read body part from the request message
     * @param in The buffer where the request message is read
     * @throws IOException Failing to read from buffer
     */
    private void readBody(BufferedReader in) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder("");
        int c;
        // reading character by character
        while(in.ready()) {
            if( 0 == (c = in.read())) break;
            bodyBuilder.append((char)c);
        }

        body = bodyBuilder.toString();
    }

    /**
     * Getter for headers map
     * @return The headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Get the specific header
     * @param header The header key
     * @return The header value
     */
    public String getHeader(String header) {
        return headers.get(header);
    }

    /**
     * Get the body
     * @return The body content
     */
    public String getBody() {
        return body;
    }

    /**
     * Get the method
     * @return The method for request message
     */
    public String getMethod() {
        return method;
    }

    /**
     * Get the URI
     * @return The uri for request message
     */
    public String getUri() {
        return uri;
    }

    /**
     * Get the Http Version
     * @return The httpVersion for request message
     */
    public String getHttpVersion() {
        return httpVersion;
    }
}
