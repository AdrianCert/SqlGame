package ro.uaic.info;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The Server class is the starting point for application. Server run and open a connection.Wait for client
 * to connect and establish a client connection. The client communication is handled by a separated thread.
 *
 * @author Adrian-Valentin Panaintescu
 */
public class Server {

    /**
     * Entry point for application
     * @param args arguments from line calls
     */
    public static void main(String[] args) {
        DataInputStream inputStream;
        DataOutputStream outputStream;
        ServerSocket serverSocket = null;
        Socket socket = null;
        Thread thread;

        try {
            serverSocket = new ServerSocket(8787);
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
