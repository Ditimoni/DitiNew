package utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class XHRFileReader {
    private WebDriver driver;

    public XHRFileReader(WebDriver driver) {
        this.driver = driver;
    }

    public Map<Integer, Map<String, String>> fetchXHRFile() {
        Map<Integer, Map<String, String>> genValuesMap = new HashMap<>();
        int i = 0;
        try {
            List<NameValuePair> queryParamsList;
            List<LogEntry> logEntries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
            ListIterator<LogEntry> logEntriesIterator = logEntries.listIterator(logEntries.size());
//            System.out.println("LogEnt : "+logEntries);
//            System.out.println("logEntriesIterator : "+logEntriesIterator);
            while (logEntriesIterator.hasPrevious()) {
                LogEntry le = logEntriesIterator.previous();
                if (le.getMessage().contains("b/ss/") && le.getMessage().contains("Network.requestWillBeSent") && !le.getMessage().contains("Network.requestWillBeSentExtraInfo")) {
//                    System.out.println(("----++++>" + le.getMessage()));
                    JSONObject networkRequestData = new JSONObject(le.getMessage());
                    JSONObject message = networkRequestData.getJSONObject("message");
                    JSONObject params = message.getJSONObject("params");
                    JSONObject request = params.getJSONObject("request");
                    String url = request.getString("url");
                    String methodType = request.getString("method");
                    if (methodType.equals("POST")) {
                        url = url + "?" + request.getString("postData");
                    }
                    if (StringUtils.isNotEmpty(url) && url.contains("events=")) {
                        queryParamsList = URLEncodedUtils.parse(new URI(url), StandardCharsets.UTF_8);
                        Map<String, String> actualStaticValuesMap = new HashMap<>();
                        for (NameValuePair nameValuePair : queryParamsList) {
                            actualStaticValuesMap.put(nameValuePair.getName(), nameValuePair.getValue());
//                            System.out.println(nameValuePair.getName() + " -:- " + nameValuePair.getValue());
                        }
                        i++;
                        genValuesMap.put(i, actualStaticValuesMap);
                        if (i == 4) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genValuesMap;
    }
}