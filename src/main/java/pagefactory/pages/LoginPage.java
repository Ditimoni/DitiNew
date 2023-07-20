package pagefactory.pages;

import pagefactory.ReusableMethodUI;
import utils.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

import java.io.FileNotFoundException;

public class LoginPage {

    private WebDriver driver;
    Logger log = LoggerFile.getLogger(this.getClass());

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyLoginFunctionality(String UserType, String Brand) throws FileNotFoundException {
        try {
            System.out.println("before facebook launched");
            log.info("before facebook launched");
            driver.get("www.facebook.com");
            System.out.println("facebook launched");
           driver.findElement(By.xpath("")).sendKeys("test@gmail.com");
            driver.findElement(By.xpath("")).sendKeys("automate");
            driver.findElement(By.xpath("")).click();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }
}
