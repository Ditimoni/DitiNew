package pagefactory.pages;

import pagefactory.ReusableMethodUI;
import utils.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.io.FileNotFoundException;

public class LoginPage {

    private WebDriver driver;
    Logger log = LoggerFile.getLogger(this.getClass());
    ReusableMethodUI rm = new ReusableMethodUI(BaseClass.getDriver());
    ConfigReader cr = new ConfigReader();
    private ObjectReader or;

    public static String homePageUrl;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyLoginFunctionality(String UserType, String Brand) throws FileNotFoundException {
        try {
           driver.findElement(By.xpath("")).sendKeys("");
            driver.findElement(By.xpath("")).sendKeys("");
            driver.findElement(By.xpath("")).click();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }
}
