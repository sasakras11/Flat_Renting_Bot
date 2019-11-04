import com.mysql.cj.xdevapi.JsonString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ExtractIDFromJSON_Test {




    public static void main(String[] args) {


        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "https://developers.ria.com/dom/search?category=1&realty_type=2&operation_type=1&state_id=10&city_id=10&district_id=15187&api_key=33xNno164vumCCLyaIXtM1pzKsZR6MDajVa2zHXl";

        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);

         String res = response.getBody();
        assert res != null;
        JSONObject obj = new JSONObject(res);

        JSONArray arrJson = obj.getJSONArray("array");

        int[] arr = new int[arrJson.length()];
        for(int i = 0; i < arrJson.length(); i++) {
            arr[i] = arrJson.getInt(i);
            System.out.println(arr[i]);
        }
    }



}
