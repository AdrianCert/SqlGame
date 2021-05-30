import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        Thread thread;

        try {
            serverSocket = new ServerSocket(2021);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (true) {
                assert serverSocket != null;
                thread = new Thread( new ServerThread(serverSocket.accept()));
                thread.start();
            }

        } catch (IOException e) {
            try {
                assert socket != null;
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
