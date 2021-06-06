package ro.uaic.info;

import ro.uaic.info.repository.UserTableRepository;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println(UserTableRepository.getById("1"));
    }
}
