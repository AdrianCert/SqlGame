package httphandler;

import parser.Parser;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpMessageResponse {

    private Parser parser;
    private String URI, querry;

    public HttpMessageResponse(HttpMessageRequest httpMessageRequest){
        this.URI = httpMessageRequest.getUri();
        this.parser = new Parser();
        this.querry = null;
    }

    private String buildQuerry(){
        if(validateRequest()){
            return "{" + "\"querry\":" +
                    "\"" +
                    querry +
                    "\"," +
                    "\"status\":" +
                    "\"" +
                    "ok" +
                    "\"" +
                    "}";
        }
        else{
            System.out.println("fals");
            return "{" + "\"querry\":" +
                    "\"" +
                    querry +
                    "\"," +
                    "\"status\":" +
                    "\"" +
                    "not ok" +
                    "\"" +
                    "}";
        }
    }

    public boolean validateRequest(){
        String[] words = URI.split(" ");
        String[] select = words[0].split("/");
        StringBuilder stringBuilder = new StringBuilder("");

        //verific daca am /query/SELECT
        if(!select[1].equalsIgnoreCase("querry") && select.length != 3) return false;
        if(!select[2].equalsIgnoreCase("select")) return false;

        //construiesc query ul
        if(select[select.length - 1].equalsIgnoreCase("select")){
            stringBuilder.append(select[select.length - 1]);
            stringBuilder.append(" ");
        }
        for(int i = 1; i < words.length; ++i){
            stringBuilder.append(words[i]);
            stringBuilder.append(" ");
        }
        this.querry = stringBuilder.toString();
        return parser.parse(stringBuilder.toString());
    }

    public void sendResponse(Socket socket) throws IOException {
        final String CRTF = "\n\r"; // 13, 10
        String json = buildQuerry();
        String response =
                        "HTTP/1.1 200 OK" + CRTF + //status line : HTTP_version Response_code Response_message
                        "Content-Lenght: " + json.getBytes().length + CRTF + "Content-Type: application/json" + CRTF + //headers
                        CRTF +
                        json +
                        CRTF + CRTF;

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}
