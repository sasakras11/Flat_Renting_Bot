import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class execute {
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/flat_bot?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static final String USER = "alex";
    static final String PASSWORD = "123";
    public static void main(String[] args) throws SQLException, InterruptedException {

        Connection con = DriverManager.getConnection(DATABASE_URL,USER,PASSWORD);

        ResultSet resultSet = con.createStatement().executeQuery("select *from city_cityID;");

        while(resultSet.next()){

            int cityId = resultSet.getInt("cityId");
            String url = "https://developers.ria.com/dom/cities_districts/"+cityId+"10?api_key=33xNno164vumCCLyaIXtM1pzKsZR6MDajVa2zHXl&lang_id=4";
          RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String res = response.getBody();
            assert res != null;
            JSONArray array = new JSONArray(res);
            for (int i = 0; i <array.length() ; i++) {
                Thread.sleep(3000);
                JSONArray array1 = new JSONArray(array.get(i));
                JSONObject js = new JSONObject(array1.get(i));
                if(js.getString("name").equals("район")){
                    for (int j = 0; j <array1.length() ; j++) {
                        JSONObject obj = array1.getJSONObject(j);
                        String region = "\""+obj.getString("name")+"\",";
                        int region_id = obj.getInt("area_id");
                        con.createStatement().executeUpdate("insert into region_regionID(region, regionId) VALUES ("+region+region_id+");");
                        con.createStatement().executeUpdate("insert into cityID_regionID(cityId, regionId) VALUES ("+cityId+","+region_id+");");


                    }
                }


            }

        }


    }
}
