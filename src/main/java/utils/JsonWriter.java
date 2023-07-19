package utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonWriter {

    /**
     * Author: A Tejaswini
     * Date: 31/3/2022(Modified)
     * Description:
     */
    public static void addPayload(String value1, String value2) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Map<String, String> map = new HashMap<String, String>();
        map.put("rewardCode", value1);
        map.put("deviceType", value2);
        jsonObject.putAll(map);
        FileWriter file = new FileWriter("./TestData/APITestData/PayLoad/Add_reward_to_wishlist" + value1 + ".json");
        file.write(jsonObject.toJSONString());
        file.close();
    }

    /**
     * Author: A Tejaswini
     * Date: 31/3/2022(Modified)
     * Description:
     */
    public static void UpdatePayload(String resource, String key, String value, String folder) throws Exception {
        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader("./TestData/APITestData/" + folder + "/" + resource + ".json");
        Object obj = jsonparser.parse(reader);
        JSONObject jsonobj = (JSONObject) obj;
        jsonobj.replace(key, value);
        FileWriter file = new FileWriter("./TestData/APITestData/" + folder + "/" + resource + ".json");
        file.write(jsonobj.toJSONString());
        file.close();
    }

    /**
     * Author: A Tejaswini
     * Date: 31/3/2022(Modified)
     * Description:
     */
    public static void UpdatePayloadwithMultiCCN(String resource, String arry, String ele, String value, String value1, String folder) throws Exception {
        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader("./TestData/APITestData/" + folder + "/" + resource + ".json");
        Object obj = jsonparser.parse(reader);

        JSONObject jsonobj = (JSONObject) obj;
        JSONArray jsonArray = (JSONArray) jsonobj.get(arry);
        System.out.println("json array is" + jsonArray);
        JSONObject jsonobj1 = (JSONObject) jsonArray.get(0);
        System.out.println("jsonobj1 is" + jsonobj1);
        JSONObject jsonobj2 = (JSONObject) jsonArray.get(1);
        System.out.println("jsonobj2 is" + jsonobj2);
        System.out.println("ele is" + ele);
        System.out.println("value is" + value);
        System.out.println("value1 is" + value1);
        jsonobj1.replace(ele, value);
        jsonobj2.replace(ele, value1);
        FileWriter file = new FileWriter("./TestData/APITestData/" + folder + "/" + resource + ".json");
        file.write(jsonobj.toJSONString());
        file.close();
    }

    /**
     * Author: A Tejaswini
     * Date: 31/3/2022(Modified)
     * Description:
     */
    public static void UpdatePayloadwithMultiCCN(String resource, String arry, String ele, String value, String value1, String folder, String brand) throws Exception {
        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader("./TestData/APITestData/" + folder + "/" + resource + "_" + brand + ".json");
        Object obj = jsonparser.parse(reader);

        JSONObject jsonobj = (JSONObject) obj;
        JSONArray jsonArray = (JSONArray) jsonobj.get(arry);
        System.out.println("json array is" + jsonArray);
        JSONObject jsonobj1 = (JSONObject) jsonArray.get(0);
        System.out.println("jsonobj1 is" + jsonobj1);
        JSONObject jsonobj2 = (JSONObject) jsonArray.get(1);
        System.out.println("jsonobj2 is" + jsonobj2);
        System.out.println("ele is" + ele);
        System.out.println("value is" + value);
        System.out.println("value1 is" + value1);
        jsonobj1.replace(ele, value);
        jsonobj2.replace(ele, value1);
        FileWriter file = new FileWriter("./TestData/APITestData/" + folder + "/" + resource + "_" + brand + ".json");
        file.write(jsonobj.toJSONString());
        file.close();
    }

}