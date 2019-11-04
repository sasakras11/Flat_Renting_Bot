import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Filler {





    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/bot?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static final String USER = "alex";
    static final String PASSWORD = "123";
    public static void main(String[] args) throws ClassNotFoundException, SQLException {


         Connection connection = null;
        Statement statement = null;
        try {
            System.out.println("Registering JDBC driver...");
            Class.forName(JDBC_DRIVER);

            System.out.println("Creating connection to database...");
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

            System.out.println("Creating table in selected database...");
            statement = connection.createStatement();



            String url = "https://developers.ria.com/dom/states?api_key=33xNno164vumCCLyaIXtM1pzKsZR6MDajVa2zHXl&lang_id=4";

            Map<String,Integer> obl = new HashMap<>();                             // назва області - айді області

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            JSONArray array = new JSONArray(response.getBody());
            for (int i = 0; i <array.length() ; i++) {
                JSONObject JsnObject = array.getJSONObject(i);
                obl.put(JsnObject.getString("region_name"),JsnObject.getInt("stateID"));

        String state = "\""+JsnObject.getString("name")+"\"";
        int id = JsnObject.getInt("stateID");
                 String SQL = "INSERT INTO state_stateId (state,stateId) VALUES("+state+","+id+");";
                System.out.println(SQL);


                statement.executeUpdate(SQL);
                System.out.println("Table successfully updated like ");

            }




        }finally {
            if(statement!=null){
                statement.close();
            }
            if(connection!=null){
                connection.close();
            }
        }
    }
}



