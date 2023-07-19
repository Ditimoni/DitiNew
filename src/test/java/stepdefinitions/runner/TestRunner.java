package stepdefinitions.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import pagefactory.ReusableMethodUI;
import pagefactory.pages.LoginPage;
import utils.BaseClass;
import utils.ExtractURL;
import utils.LoggerFile;
import utils.ObjectReader;


import java.io.File;

@CucumberOptions(
        plugin = {"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        },
        tags = "@diti",
        monochrome = true,
        glue = {"stepdefinitions"},
        features = {"src/test/resources/FeatureFiles"}
)

public class TestRunner extends AbstractTestNGCucumberTests {
    Logger log = LoggerFile.getLogger(this.getClass());

    /**
     * Description: To Execute Sequentially, change the parallel flag to false
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    /**
     * Author: Priya Bharti and Kundan Kumar
     * Description: This method is implemented to fetch all element locator from Yaml file and
     *  control the Invocation or browser instance count in parallel execution
     */
    @BeforeSuite()
    public static void beforeSuitSetup(ITestContext context) {
        int invocationCount=Integer.parseInt(System.getProperty("InvocationCount"));
        if (System.getProperty("BrowserStack").equalsIgnoreCase("yes")){
            invocationCount=1;
        }
        System.out.println("Invocation Count : "+invocationCount);
        context.getCurrentXmlTest().getSuite().setDataProviderThreadCount(invocationCount);
        ObjectReader.initializeObjectRepository();
    }

    /**
     * Author: Kundan Kumar
     * Date: 08/06/2022
     * Description: This method is implemented to fetch Urls from Confluence page before launching the scenario
     */
    @BeforeSuite(dependsOnMethods = "beforeSuitSetup" )
    public void extractUrlFromPg() {
        String browser = System.getProperty("BrowserName").toLowerCase();
        if (System.getProperty("ValidationType").equalsIgnoreCase("accessibility") ||
                System.getProperty("ValidationType").equalsIgnoreCase("linkchecker")) {
            if (browser.contains("mobile")) {
                System.setProperty("BrowserName", "chrome");
            }
            WebDriver driver = new BaseClass().initiate_driver();
            new ReusableMethodUI(driver).launchGivenPgURL("Confluence");
            if (driver.getPageSource().contains("can't reach this page") ||  driver.getPageSource().contains("can’t be reached")){
                log.error("Confluence Page not accessible due to network or VPN issue");
                ExtractURL.brandMap=null;
                driver.quit();
            }
            System.setProperty("BrowserName", browser);
        }else if (System.getProperty("ValidationType").equalsIgnoreCase("api")) {
            File theDir = new File("./Report/Logs/APILogs");
            if (!theDir.exists()){
                theDir.mkdir();
            }
        }
    }

    /**
     * In aftersuite, all the files generated in temp folder via cucumber run are deleted
     */
    @AfterSuite
    public void deleteFromTemp(){
        String defaultBaseDir = System.getProperty("java.io.tmpdir");
        File directoryToDelete = new File(defaultBaseDir);
        for (File file : directoryToDelete.listFiles()) {
            try {
                if (file.getName().contains("cucumber") || file.getName().contains(".com.google.Chrome")) {
                    if (file.isDirectory()) {
                        ReusableMethodUI.deleteDirectory(file);
                    }
                    file.delete();
                    log.info(file.getName() + " deleted");
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
