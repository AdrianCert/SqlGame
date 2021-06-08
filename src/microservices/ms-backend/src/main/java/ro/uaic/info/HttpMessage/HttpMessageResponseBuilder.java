package ro.uaic.info.HttpMessage;

import java.io.File;

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
     * @param httpVersion The value for http version
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder http(String httpVersion) {
        setHttpVersion(httpVersion);
        return this;
    }

    /**
     * Setter for status code
     * @param status The value for status code
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder status(String status) {
        setStatusCode(status);
        return this;
    }

    /**
     * Setter for status code
     * @param status The value for status code
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder status(Integer status) {
        setStatusCode(status);
        return this;
    }

    /**
     * Setter for response phrase
     * @param responsePhrase The value for response phrase
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder responsePhrase(String responsePhrase) {
        setReasonPhrase(responsePhrase);
        return this;
    }

    /**
     * Setter for adding a header to http message
     * @param key The value for header name
     * @param value The value for header value
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder header(String key, String value) {
        addHeader(key, value);
        return this;
    }

    /**
     * Setter for body message
     * @param body The value for body
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder body(String body) {
        setBody(body);
        return this;
    }

    /**
     * Setter for body message
     * @param body The value for body
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder body(byte[] body) {
        setBody(body);
        return this;
    }

    /**
     * Setter for body message
     * @param body The value for body
     * @return This object for cascade calls
     */
    public HttpMessageResponseBuilder body(File body) {
        setBody(body);
        return this;
    }
}
