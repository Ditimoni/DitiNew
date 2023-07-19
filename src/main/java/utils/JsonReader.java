package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.bs.A;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

public class JsonReader {

    static ConfigReader cr = new ConfigReader();


    /**
     * Author: A Tejaswini
     * Date: 31/3/2022(Modified)
     * Description:
     * @return
     */
    public static String getBaseUrl(String... arry) throws Exception {
        String Env = cr.getSystemEnv();
        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader("./TestData/APITestData/JsonFiles/Url.json");
        Object obj = jsonparser.parse(reader);
        JSONObject jsonobj = (JSONObject) obj;
        String str = null;
        if (Arrays.stream(arry).anyMatch(Predicate.isEqual("UGC"))) {
            str = (String) jsonobj.get(arry[0] + "_" + Env + "_" + arry[1]);
        } else {
            for (int i = 0; i < arry.length; i++) {
                if (i == arry.length - 1) {
                    str = (String) jsonobj.get(arry[i] + "_" + Env);
                } else {
                    jsonobj = (JSONObject) jsonobj.get(arry[i]);
                }
            }
        }
        return str;
    }

    /**
     * Author: A Tejaswini
     * Date: 31/3/2022(Modified)
     * Description:
     * @return
     */
    public static String getParams(String... arry) throws Exception {

        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader("./TestData/APITestData/JsonFiles/Params.json");
        Object obj = jsonparser.parse(reader);
        JSONObject jsonobj = (JSONObject) obj;
        JSONObject jsonobj1 = null;
        String str = null;
        for (int i = 0; i < arry.length; i++) {
            if (i == arry.length - 1) {
                str = (String) jsonobj.get(arry[i]);
            } else {
                jsonobj1 = (JSONObject) jsonobj.get(arry[i]);
                jsonobj = jsonobj1;
            }
        }
        return str;
    }

    public static String getResourceValue(String... arry) throws Exception {

        String Env = cr.getSystemEnv();
        ArrayList<String> list = new ArrayList<>(Arrays.asList(arry));
        list.add(0, Env);
        String[] myArray = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            myArray[i] = list.get(i);
        }
        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader("./TestData/APITestData/JsonFiles/Resources.json");
        Object obj = jsonparser.parse(reader);
        JSONObject jsonobj = (JSONObject) obj;
        JSONObject jsonobj1 = null;
        String str = null;
        for (int i = 0; i < myArray.length; i++) {
            if (i == myArray.length - 1) {
                str = (String) jsonobj.get(myArray[i]);
            } else {
                jsonobj1 = (JSONObject) jsonobj.get(myArray[i]);
                jsonobj = jsonobj1;
            }
        }
        return str;
    }

    /**
     * Author: A Tejaswini
     * Date: 31/3/2022(Modified)
     * Description:
     */
    public static String getCommonResourceValue(String... arry) throws Exception {
        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader("./TestData/APITestData/JsonFiles/CommonResources.json");
        Object obj = jsonparser.parse(reader);
        JSONObject jsonobj = (JSONObject) obj;
        JSONObject jsonobj1 = null;
        String str = null;
        for (int i = 0; i < arry.length; i++) {
            if (i == arry.length - 1) {
                str = (String) jsonobj.get(arry[i]);
            } else {
                jsonobj1 = (JSONObject) jsonobj.get(arry[i]);
                jsonobj = jsonobj1;
            }
        }
        return str;
    }


    /**
     * Author: A Tejaswini
     * Date: 31/3/2022(Modified)
     * Description:
     */
    public static JSONObject readJson(String folder, String filename) throws Exception {
        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader("./TestData/APITestData/" + folder + "/" + filename + ".json");
        Object obj = jsonparser.parse(reader);
        JSONObject jsonobj = (JSONObject) obj;
        return jsonobj;
    }

    /**
     * Author: Kundan Kumar
     */
    public static Map<String, Map<String, String>> getJsonGenericVal() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        File fileObj =new File("./TestData/Analytics/GenericValues.json");
        return mapper.readValue(fileObj, new TypeReference<Map<String, Map<String, String>>>(){});
    }

    public static String getPayloadValue(String ... keyArray) throws Exception {
        String value = null;
        JSONObject jsonObj = readJson("PayLoad", BaseClass.getFeature().toString());
        for (int i = 0; i < keyArray.length; i++) {
            if (i == keyArray.length - 1) {
                value = jsonObj.get(keyArray[i]).toString();
            } else {
                jsonObj = (JSONObject) jsonObj.get(keyArray[i]);
            }
        }
        return value;
    }


}
