package ro.uaic.info;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ServerSocket serverSocket = null;
        Socket socket = null;
        Thread thread;

        try {
            serverSocket = new ServerSocket(8100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                assert serverSocket != null;
                thread = new Thread(new ServerThread(serverSocket.accept()));
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

        //conn.closeConnection();
    }
}