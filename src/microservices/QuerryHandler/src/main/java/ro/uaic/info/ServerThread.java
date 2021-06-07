package ro.uaic.info;

import ro.uaic.info.HttpMessage.HttpMessageRequest;
import ro.uaic.info.HttpMessage.HttpMessageResponse;
import ro.uaic.info.HttpMessage.HttpMessageResponseBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Base64;

public class ServerThread implements Runnable {

    private Socket socket;
    private HttpMessageRequest httpRequest;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            httpRequest = HttpMessageRequest.of(bufferedReader);
            HttpMessageResponseBuilder httpMessageResponseBuilder = new HttpMessageResponseBuilder();
            httpMessageResponseBuilder
                    .body("")
                    .constructResponse(httpRequest.getMethod(), httpRequest.getUri(), httpRequest.getBody())
                    .send(socket);

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
