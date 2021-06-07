package ro.uaic.info;

import com.fasterxml.jackson.databind.ObjectMapper;
import ro.uaic.info.builder.UserTableBuilder;
import ro.uaic.info.entity.UserTable;
import ro.uaic.info.entity.UserWallet;
import ro.uaic.info.repository.UserTableRepository;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        //System.out.println(UserTableRepository.getById("1"));
        UserTable userTable = new UserTableBuilder().name("test-Static").surname("41231234444-")
                                                                        .user_name("123333aw123123dawdawdawd33333123")
                                                                        .mail("yeaye12312a123123sdadawda31444214123a@yahoo.com")
                                                                        .details("ye1231441231234123ssir");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userTable);
        //UserTableRepository.add(json);



    }
}
