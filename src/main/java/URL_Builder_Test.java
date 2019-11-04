import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class URL_Builder_Test {
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/bot?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static final String USER = "alex";
    static final String PASSWORD = "123";




    public static void main(String[] args) throws SQLException {

      try(Connection con = DriverManager.getConnection(DATABASE_URL,USER,PASSWORD)){
          System.out.println("CONNECTION SUCCESSFUL");
          Statement statement = con.createStatement();
          ResultSet resultSet = statement.executeQuery("select *from state_stateId");
          while (resultSet.next()){

              RestTemplate restTemplate = new RestTemplate();
               String url = "https://developers.ria.com/dom/cities/"+resultSet.getInt(2)+":?api_key=33xNno164vumCCLyaIXtM1pzKsZR6MDajVa2zHXl";
              ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
              String res = response.getBody();
              assert res != null;

              JSONArray arrJson = new JSONArray(res);
              for (int i = 0; i < arrJson.length(); i++) {
                  JSONObject jso = arrJson.getJSONObject(i);
                  String cityName = "\""+jso.getString("name")+"\"";
                  int cityId = jso.getInt("cityID");
                  statement.executeUpdate("insert into city_cityId (city,cityId) VALUES("+cityName+","+cityId+");");
                  statement.executeUpdate("insert into stateId_cityId(city_id, state_id) VALUES("+cityId+","+resultSet.getInt(2)+");");

              }
          }


      }





    }


}
