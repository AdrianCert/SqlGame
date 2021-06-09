package httphandler;

import parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class HttpMessageRequest {

    private String method;
    private String httpVersion;
    private String uri;

    /**
     * getter pentru metoda, httpversion, uri
     * @return
     */
    public String getMethod(){return method;}
    public String getHttpVersion(){return httpVersion;}
    public String getUri(){return uri;}


    /**
     * setter pentru metoda, httpversion, uri
     * @param method
     */
    public void setMethod(String method){this.method = method;}
    public void setHttpVersion(String httpVersion){this.httpVersion = httpVersion;}
    public void setUri(String uri){this.uri = uri;}


    public static HttpMessageRequest parseRequest(BufferedReader request) throws IOException {
        HttpMessageRequest demo = new HttpMessageRequest();
        demo.readRequestLine(request.readLine());
        return demo;
    }
    private void readRequestLine(String input){
        StringTokenizer tokenizer = new StringTokenizer(input);
        this.method = tokenizer.nextToken().toUpperCase();
        this.uri =tokenizer.nextToken().replace("%20", " ");
        this.httpVersion = tokenizer.nextToken().toUpperCase();
    }
}

