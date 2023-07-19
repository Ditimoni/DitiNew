package utils;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.asserts.SoftAssert;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class BaseClass {

    /**
     * Author: Priya Bharti
     * Reason To Use ThreadLocal: Java ThreadLocal class enables us to create variables that can only be read and write by the same thread.
     * If two threads are executing the same code and that code has a reference to a ThreadLocal variable then the two threads can't see the local variable of each other.
     * Here the instance type is WebDriver in the ThreadLocal. so that we don't need to typecast the value returned by get() method.
     */
    public static ThreadLocal<WebDriver> tl = new ThreadLocal<>();
    public static ThreadLocal<RequestSpecification> request = new ThreadLocal<>();
    public static ThreadLocal<Response> response = new ThreadLocal<>();
    public static ThreadLocal<String> feature = new ThreadLocal<>();
    public static ThreadLocal<String> resourceUrl = new ThreadLocal<>();
    public static ThreadLocal<Scenario> scenario = new ThreadLocal<>();
    public static ThreadLocal<SoftAssert> softAssert = new ThreadLocal<>();
    public static ThreadLocal<String> chromeEmulator = new ThreadLocal<>();
    ConfigReader config = new ConfigReader();

    /**
     * Author: Priya Bharti and Kundan Kumar
     * Date: 12/05/2022(Modified)
     * Description: This method is to initiate the ThreadLocal driver on the basis of given browser
     *
     * @return : This method will return the tl driver
     */
    public WebDriver initiate_driver() {
        try {
            System.setProperty("webdriver.http.factory", "jdk-http-client");
            String browser = System.getProperty("BrowserName").toLowerCase();
            String device = System.getProperty("DeviceName");
            String osVersion = System.getProperty("OS_Version");
            String url = "https://" + config.getBSUserDetails("userName") + ":" + config.getBSUserDetails("accessKey") + "@hub-cloud.browserstack.com/wd/hub";
            Map<String, String> mobileEmulation = new HashMap<>();
            ChromeOptions chromeOptions = new ChromeOptions();
            DesiredCapabilities caps = new DesiredCapabilities();
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable( LogType.PERFORMANCE, Level.ALL );

            switch (browser) {
                case "chrome":
                    if (System.getProperty("BrowserStack").equalsIgnoreCase("yes") && (!System.getProperty("ValidationType").equalsIgnoreCase("Accessibility"))) {
                        WebDriverManager.chromedriver().setup();
                        caps.setCapability("os", "OS X");
                        caps.setCapability("os_version", osVersion);
                        caps.setCapability("browser", "Chrome");
                        caps.setCapability("browser_version", "latest");
                        if(System.getProperty("BrowserResolution").contains("1024")) {
                            caps.setCapability("resolution", "1600x1200");
                        }
                        caps.setCapability("browserstack.local", "false");
                        tl.set(new RemoteWebDriver(new URL(url), caps));
                    } else {
                        WebDriverManager.chromedriver().setup();
                        chromeOptions.setCapability( "goog:loggingPrefs", logPrefs );
                        tl.set(new ChromeDriver(chromeOptions));
                    }
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    tl.set(new FirefoxDriver());
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    tl.set(new EdgeDriver());
                    break;

                case "mobileheadless":
                    WebDriverManager.chromedriver().setup();
                    mobileEmulation.put("deviceName", device);
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--disable-dev-shm-using");
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("disable-infobars");
                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                    tl.set(new ChromeDriver(chromeOptions));
                    break;

                case "mobile":
                    String bs = (new BaseClass()).getBrowserStack();
                    if(bs==null) bs="null";
                    if (System.getProperty("BrowserStack").equalsIgnoreCase("yes") && !bs.equalsIgnoreCase("no")) {
                        if (device.contains("iPhone")) {
                            WebDriverManager.getInstance(SafariDriver.class).setup();
                            caps.setCapability("os_version", osVersion);
                            caps.setCapability("device", device);
                            caps.setCapability("browser", "Safari");
                            caps.setCapability("real_mobile", "true");
                            tl.set(new RemoteWebDriver(new URL(url), caps));
                        } else {
                            WebDriverManager.chromedriver().setup();
                            chromeOptions.setCapability("os_version", osVersion);
                            chromeOptions.setCapability("device", device);
                            chromeOptions.setCapability("browser", "android");
                            chromeOptions.setCapability("real_mobile", "true");
                            tl.set(new RemoteWebDriver(new URL(url), chromeOptions));
                        }
                    } else {
                        WebDriverManager.chromedriver().setup();
                        mobileEmulation.put("deviceName", device);
                        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                        chromeOptions.setCapability( "goog:loggingPrefs", logPrefs );
                        tl.set(new ChromeDriver(chromeOptions));
                    }
                    break;

                case "headless":
                    WebDriverManager.chromedriver().setup();
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-setuid-sandbox");
                    chromeOptions.addArguments("--remote-debugging-port=9222");
                    chromeOptions.addArguments("--disable-dev-shm-using");
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("disable-infobars");
                    tl.set(new ChromeDriver(chromeOptions));
                    break;

                // TODO: Need to check for VPN setup in remote
                case "safari":
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    caps.setCapability("os", "OS X");
                    caps.setCapability("os_version", osVersion);
                    caps.setCapability("browser", "Safari");
                    caps.setCapability("browser_version", "latest");
                    tl.set(new RemoteWebDriver(new URL(url), caps));
                    break;
            }
            getDriver().manage().deleteAllCookies();
            getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            if (!System.getProperty("BrowserResolution").equalsIgnoreCase("null")) {
                String[] resolution = System.getProperty("BrowserResolution").split("\\*");
                Dimension d = new Dimension(Integer.parseInt(resolution[0]), Integer.parseInt(resolution[1]));
                getDriver().manage().window().setSize(d);
            } else {
                getDriver().manage().window().maximize();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDriver();
    }

    public static Response initiateResponse(Response resp) {
        response.set(resp);
        return getResponse();

    }

    public static RequestSpecification initiateRequest(RequestSpecification req) {
        request.set(req);
        return getRequest();

    }

    public static String setFeature(String featureFile) {
        feature.set(featureFile);
        return getFeature();

    }

    public static String setResourceUrl(String resUrl) {
        resourceUrl.set(resUrl);
        return getResoureUrl();

    }

    public Scenario setScenario(Scenario scen) {
        scenario.set(scen);
        return getScenario();

    }

    public String setBrowserStack(String bs) {
        chromeEmulator.set(bs);
        return getBrowserStack();

    }

    public SoftAssert setSoftAssert() {
        SoftAssert sa = new SoftAssert();
        softAssert.set(sa);
        return getSoftAssert();

    }


    /**
     * Author: Priya Bharti
     * Date: 3/15/2022(Modified)
     * Description: This method is to get the driver with ThreadLocal
     *
     * @return
     */
    public static synchronized WebDriver getDriver() {
        return tl.get();
    }
    public static synchronized Scenario getScenario() {
        return scenario.get();
    }
    public static synchronized Response getResponse() {
        return response.get();
    }
    public static synchronized RequestSpecification getRequest() {
        return request.get();
    }
    public static synchronized String getFeature() {
        return feature.get();
    }
    public static synchronized String getResoureUrl() {
        return resourceUrl.get();
    }
    public synchronized String getBrowserStack() {
        return chromeEmulator.get();
    }

    public static synchronized SoftAssert getSoftAssert()
    {
        return softAssert.get();
    }

}
