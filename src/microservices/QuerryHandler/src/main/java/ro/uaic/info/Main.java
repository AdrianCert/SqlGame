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
        /*try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "STUDENT";
        String pass = "QWERTY";

        String question;
        String solutionSQL = "SELECT * FROM (SELECT m.title FROM (SELECT m.title, g.name FROM (SELECT m.id, m.title, mg.genre_id FROM movies m FULL JOIN movie_genre mg ON m.id=mg.movie_id) m FULL JOIN genres g ON m.genre_id=g.id) m WHERE m.name='Fantasy') m ORDER BY m.title";
        String inputSQL = "SELECT m.title FROM movies m ORDER BY m.title";

        question = "Andrei wants to know all movies that are in IMDb database that are in Fantasy Genre. \nYou are given 3 tables.";
        System.out.println(question);
        LinkedList<String> correctEntries = new LinkedList<>();
        LinkedList<String> inputEntries = new LinkedList<>();

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement pstmt = conn.prepareStatement(solutionSQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                correctEntries.add(rs.getString(1));
                System.out.println(correctEntries.getLast());
            }
            System.out.println();
            pstmt = conn.prepareStatement(inputSQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                inputEntries.add(rs.getString(1));
            }

            if (correctEntries.size() == inputEntries.size() && correctEntries.equals(inputEntries)) {
                System.out.println("Raspuns Corect");
            } else
                System.out.println("Raspuns Gresit");
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }*/

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ServerSocket serverSocket = null;
        Socket socket = null;
        Thread thread;

        try {
            serverSocket = new ServerSocket(8000);
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