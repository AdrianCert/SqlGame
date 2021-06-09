import httphandler.HttpMessageRequest;
import httphandler.HttpMessageResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ServerThread implements Runnable{

    private Socket socket;
    private HttpMessageRequest httpRequest;

    public ServerThread(Socket socket){this.socket = socket;}

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            httpRequest = HttpMessageRequest.parseRequest(bufferedReader);
            System.out.println("----DETAILS-----");
            System.out.println(httpRequest.getUri() + " ~~~~ " + httpRequest.getMethod() + " ~~~ " + httpRequest.getHttpVersion());
            HttpMessageResponse httpMessageResponse = new HttpMessageResponse(httpRequest);
            httpMessageResponse.sendResponse(socket);

        } catch (IOException e) {
            System.err.println("[ERROR] Server Client Thread:" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("[ERROR] Socket Close: " + e.getMessage());
            }
        }
    }
}
