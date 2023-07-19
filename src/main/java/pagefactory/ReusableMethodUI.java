package pagefactory;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.emulation.Emulation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.ExcelReader;
import utils.*;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.openqa.selenium.*;
import utils.LoggerFile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ReusableMethodUI {
    private WebDriver driver;
    Logger log = LoggerFile.getLogger(this.getClass());
    ConfigReader cr = new ConfigReader();
    private static SoftAssertions softAssert = new SoftAssertions();
    private static ExcelReader excl;
    private ObjectReader or;

    public List<WebElement> totalRows;

    public ReusableMethodUI(WebDriver driver) {
        this.driver = driver;
    }

    public ReusableMethodUI() {
    }

    /**
     * Author: Tejaswini
     * Description: This function verifies the navigation based on authored tab by taking WebElement as one of the Argument
     * Date: 30/3/2022
     */
    public void verifyPageNavigationbyTab(WebElement element, String tab) throws InterruptedException {
        if (tab.equalsIgnoreCase("Same Tab") || tab.equalsIgnoreCase("Default")) {
            Thread.sleep(5000);
            String href = element.getAttribute("href");
            Assert.assertTrue("Element is not displayed", Waits.checkElementDisplayed(driver, 5, element));
            log.info("Element is Clickable");
            clickWithJS(element);
            log.info("Clicked on the element");
            String currentURL = driver.getCurrentUrl();
            Assert.assertEquals(href, currentURL);
            //org.testng.Assert.assertEquals(href,currentURL);
            driver.navigate().back();
        } else if (tab.equalsIgnoreCase("New Tab")) {
            Thread.sleep(5000);
            String href = element.getAttribute("href");
            Assert.assertTrue("Element is not displayed", Waits.checkElementDisplayed(driver, 5, element));
            log.info("Element is Clickable");
            clickWithJS(element);
            log.info("Clicked on the element");
            focusOnNewTab();
            String currentURL = driver.getCurrentUrl();
            org.testng.Assert.assertEquals(href, currentURL);
            closeNewlyOpenTab();
        }
    }

    /**
     * Author: Shikha Swaroop
     * Description: This function verifies the navigation based on authored tab
     * Date: 22/3/2022
     */
    public void verifyPageNavigationbyTab(By element, String tab) {
        if (tab.equalsIgnoreCase("Same Tab") || tab.equalsIgnoreCase("Default")) {
            if (Waits.visibilityOfElement(driver, 30, element) != null) {
                String href = driver.findElement(element).getAttribute("href");
                clickWithJS(element);
                String currentURL = driver.getCurrentUrl();
                Assert.assertEquals(href + " is not same with " + currentURL, href, currentURL);
                log.info("Navigated to the page " + currentURL);
                driver.navigate().back();
                currentURL = driver.getCurrentUrl();
                log.info("Navigated back to the page " + currentURL);
            } else {
                Assert.fail(element + " is not visible");
            }
        } else if (tab.equalsIgnoreCase("New Tab")) {
            if (Waits.visibilityOfElement(driver, 30, element) != null) {
                String href = driver.findElement(element).getAttribute("href");
                clickWithJS(element);
                focusOnNewTab();
                String currentURL = driver.getCurrentUrl();
                Assert.assertEquals(href + " is not same with " + currentURL, href, currentURL);
                closeNewlyOpenTab();
            } else {
                Assert.fail(element + " is not visible");
            }
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to get the brand name from the URL
     */
    public String getBrand() {
        String brand = null;
        try {
            String url = driver.getCurrentUrl().toLowerCase();
            if (url.contains("marlboropwa")) {
                brand = "PWA";
            } else if (url.contains("chesterfield")) {
                brand = "Chesterfield";
            } else if (url.contains("virginiaslims")) {
                brand = "VS";
            } else if (url.contains("blackandmild")) {
                brand = "BlackAndMild";
            } else if (url.contains("copenhagen") || url.contains("freshcope")) {
                brand = "Copenhagen";
            } else if (url.contains("onnicotine") || url.contains("H2O") || url.contains("h2o")) {
                brand = "ON";
            } else if (url.contains("skoal")) {
                brand = "Skoal";
            } else if (url.contains("myparliament")) {
                brand = "MyParliament";
            } else if (url.contains("redseal")) {
                brand = "RedSeal";
            } else if (url.contains("marlboro")) {
                brand = "Marlboro";
            } else if (url.contains("iqos")) {
                brand = "IQOS";
            } else if (url.contains("lm")) {
                brand = "LM";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brand;
    }

    /**
     * Author: Kundan Kumar
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to select option from dropdown by index
     */
    public WebElement selectByIndex(By locator, int index) {
        if (Waits.visibilityOfElement(driver, 20, locator) != null) {
            Select sel = new Select(driver.findElement(locator));
            sel.selectByIndex(index);
            log.info(sel.getFirstSelectedOption().getText() + " Has been selected from the drop down");
            return sel.getFirstSelectedOption();
        } else {
            Assert.fail(locator + " is not visible");
            return null;
        }
    }

    /**
     * Author: Kundan Kumar
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to select option from dropdown by value
     */
    public void selectByValue(By locator, String value) {
        if (Waits.visibilityOfElement(driver, 20, locator) != null) {
            Select sel = new Select(driver.findElement(locator));
            sel.selectByValue(value);
            log.info(value + "Has been selected from the drop down");
        } else {
            Assert.fail(locator + " is not visible");
        }
    }

    /**
     * Author: Kundan Kumar
     * Date: 25/3/2022 (Modified)
     * Description: This method is to move the mouse to an element
     */
    public void mouseHover(By locator) {
        try {
            if (Waits.visibilityOfElement(driver, 20, locator) != null) {
                Actions act = new Actions(driver);
                act.moveToElement(driver.findElement(locator)).build().perform();
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Kundan Kumar
     * Date: 25/3/2022 (Modified)
     * Description: This method is to move the mouse to an element
     */
    public void mouseHover(WebElement element) {
        try {
            if (Waits.visibilityOfElement(driver, 20, element) != null) {
                Actions act = new Actions(driver);
                act.moveToElement(element).perform();
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Kundan Kumar
     * Date: 25/3/2022 (Modified)
     * Description:  This method is used to select option from dropdown by visible text
     */
    public WebElement selectByVisibleText(By locator, String text) {
        WebElement webElement = null;
        try {
            Waits.waitTillPageIsReady(driver);
            if (Waits.visibilityOfElement(driver, 30, locator) != null) {
                Select sel = new Select(driver.findElement(locator));
                sel.selectByVisibleText(text);
                log.info(text + "Has been selected from the drop down");
                webElement = sel.getFirstSelectedOption();
                return webElement;
            } else {
                Assert.fail(locator + " is not visible");
                return webElement;
            }
        } catch (Exception e) {
            log.error(e);
            Assert.fail("Failed in selectByVisibleText " + e);
        }
        return webElement;
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to scroll into view only if needed to element using javascript, method overloading
     */
    public void scrollInToViewIfNeeded(WebElement ele) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("arguments[0].scrollIntoViewIfNeeded(true)", ele);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to scroll into view only if needed to element using javascript, method overloading
     */
    public void scrollInToViewIfNeeded(By locator) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoViewIfNeeded(true);", driver.findElement(locator));
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to scroll into view to element using javascript
     */
    public void scrollInToView(By locator) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
    }

    /**
     * Author: A.Tejaswini
     * Description: This function clicks on element using Action class(accepting locator argument) , method overloading
     * Date: 16/3/2022
     */
    public void clickWithActionClass(By locator) {
        new Actions(driver).moveToElement(driver.findElement(locator)).click().build().perform();
    }

    /**
     * Author: A.Tejaswini
     * Description: This function clicks on element using Action class(accepting WebElement argument), method overloading
     * Date: 16/3/2022
     */
    public void clickWithActionClass(WebElement ele) {
        new Actions(driver).moveToElement(ele).click().build().perform();
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to click on element using javascript, method overloading
     */
    public void clickWithJS(By locator) {
        try {
            if (Waits.checkElementDisplayed(driver, 20, locator)) {
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
                javascriptExecutor.executeScript("arguments[0].click();", driver.findElement(locator));
            } else {
                Assert.fail(locator + " is not displayed");
            }
        } catch (Exception e) {
            Assert.fail(locator + " is not clicked " + e);
        }
    }

    /**
     * Author: Tejaswini
     * Date: 5/30/2022
     * Description: This method is used to click on element using javascript, method overloading
     */
    public void click(By locator) {
        if (Waits.checkElementDisplayed(driver, 20, locator)) {
            if (Waits.checkElementClickable(driver, 20, locator))
                driver.findElement(locator).click();
            else
                Assert.fail(locator + " Element is not clickable");
        } else {
            Assert.fail(locator + " Element is not displayed");
        }
    }

    /**
     * Author: Tejaswini
     * Date: 5/30/2022
     * Description: This method is used to click on element using javascript, method overloading
     */
    public void click(WebElement element) {
        if (Waits.checkElementDisplayed(driver, 20, element)) {
            if (Waits.checkElementClickable(driver, 20, element))
                element.click();
            else
                Assert.fail(element + " Element is not clickable");
        } else {
            Assert.fail(element + " Element is not displayed");
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to click on element using javascript, method overloading
     */
    public void clickWithJS(WebElement ele) {
        try {
            if (Waits.checkElementDisplayed(driver, 20, ele)) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", ele);
            } else {
                Assert.fail(ele + " not displayed");
            }
        } catch (Exception e) {
            Assert.fail("Failed in clickWithJS " + e);
        }
    }

    public void clickWithJSWithoutWait(WebElement ele) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", ele);
        } catch (Exception e) {
            Assert.fail("Failed in clickWithJS " + e);
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to execute javascript
     */
    public String executeJS(String script) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script).toString();
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to click on element using javascript, method overloading
     */
    public void refreshPageWithJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.location.reload();");
    }

    /**
     * Author: Priya Bharti
     * Date: 30/5/2022 (Modified by Ditimoni to verify the element after url launch)
     * Description: This method is used to launch captcha url
     */
    public void launchCaptchaURL(String brand) throws FileNotFoundException, InterruptedException {
        try {
            log.info("Launching captcha URL::  " + ConfigReader.captchaUrl(brand));
            driver.get(ConfigReader.captchaUrl(brand));
            Waits.waitTillPageIsReady(driver);
            Assert.assertTrue("Failed in Captcha challenge launch", Waits.checkElementDisplayed(driver, 30, or.getLocator("CommonMethodUI.CaptchaError")));
            log.info("captcha challenge launched Successfully");
        } catch (Exception e) {
            log.info("Failed in Launch Captcha Url " + e);
            Assert.fail("Failed in Launch Captcha Url " + e);
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 30/5/2022 (Modified by Ditimoni to verify the element after url launch)
     * Description: This method is used to launch brand url
     */
    public void launchBrandURL(String Brand) {
        try {
            String url = getEnvDomain() + ConfigReader.getURL(Brand);
            log.info("Navigating to brand URL : " + url);
            driver.get(url);
            Waits.waitTillPageIsReady(driver);

            if (Waits.checkElementDisplayed(driver, 10, or.getLocator("LoginPage.NoResourceErr"))) {
                log.info("The current url is :: " + driver.getCurrentUrl());
                driver.get(url);
                Waits.waitTillPageIsReady(driver);
            }
            if (System.getProperty("BrowserName").equalsIgnoreCase("mobile") &&
                    (Brand.equalsIgnoreCase("IQOS") || Brand.equalsIgnoreCase("ON") || Brand.equalsIgnoreCase("PWA") || Brand.equalsIgnoreCase("MYPARLIAMENT") || Brand.equalsIgnoreCase("LM") || Brand.equalsIgnoreCase("VS") || Brand.equalsIgnoreCase("MARLBORO"))) {
                if (Brand.equalsIgnoreCase("PWA") || Brand.equalsIgnoreCase("MYPARLIAMENT") || Brand.equalsIgnoreCase("LM") || Brand.equalsIgnoreCase("VS") || Brand.equalsIgnoreCase("MARLBORO"))
                driver.switchTo().frame(driver.findElement(or.getLocator("LoginPage.IframeLoginPage")));
                if (Waits.checkElementDisplayed(driver, 30, or.getLocator("LoginPage.CommonLandingMob"))) {
                    log.info("Navigated to the brand URL : " + url);
                } else
                {
                    driver.get(url);
                    if (Waits.checkElementDisplayed(driver, 20, or.getLocator("LoginPage.CommonLandingMob"))){
                        log.info("Navigated to the brand URL : " + url);
                    } else {
                        log.info("Navigated to this URL : " + driver.getCurrentUrl());
                        Assert.assertTrue("Brand URL is not loaded properly ", false);
                    }
                }

                driver.switchTo().defaultContent();
            }
            else {
                if (Waits.checkElementDisplayed(driver, 20, or.getLocator("LoginPage.CommonLanding"))) {
                    log.info("Navigated to the brand URL : " + url);
                } else {
                    log.info("Navigated to this URL : " + driver.getCurrentUrl());
                    if (Brand.equalsIgnoreCase("ON")){
                        driver.navigate().refresh();
                        Waits.waitTillPageIsReady(driver);
                        Thread.sleep(1000);
                    }else {
                        Assert.assertTrue("Brand URL is not loaded properly ", false);
                    }

                }

            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Shubham Kumar
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to launch Deeplink url
     */
    public void launchDeeplinkBrandURL(String brandFrom, String brandTo) {
        try {
            String url = getEnvDomain() + ConfigReader.getURL(brandFrom, brandTo);
            log.info("Navigating to brand URL : " + url);
            driver.get(url);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to launch AEM url
     */
    public void launchAEMURL() {
        try {
            String url = getAuthEnvDomain() + ConfigReader.getAEMURL();
            log.info("Navigating to AEM URL : " + url);
            driver.get(url);
            Assert.assertTrue("AEM URL is not launched properly", (Waits.checkElementDisplayed(driver, 20, or.getLocator("LoginPage.CommonAEMLaunch"))));
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            Assert.fail("AEM URL is not launched properly " + e);
        }
    }

    /**
     * Author: Kundan Kumar
     * Date: 08/06/2022
     * Description: This method is used to launch given page's url
     */
    public void launchGivenPgURL(String pageName) {
        try {
            String url = ConfigReader.getPageURL(pageName);
            log.info("Navigating to " + pageName + " Page URL : " + url);
            driver.navigate().to(url);
//            Assert.assertTrue(pageName+" URL is not launched properly", Waits.checkElementDisplayed(driver, 20, or.getLocator("LoginPage.OktaEmail")));
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to launch component url
     */
    public void launchAuthComponentURL(String Component, String Brand) {
        try {
            String url = getAuthEnvDomain() + ConfigReader.getAuthInstCompURL(Component, Brand);
            log.info("I Navigating to URL: " + url);
            driver.get(url);
            Waits.waitTillPageIsReady(driver);
            if (Waits.checkElementDisplayed(driver, 10, or.getLocator("CMC.ErrorPage"))) {
                Assert.fail("Content page is not created " + driver.getCurrentUrl());
            } else if (!Component.contains("IWCVar")) {
                Assert.assertTrue("Component Url is not launched properly " + driver.getCurrentUrl(), Waits.checkElementDisplayed(driver, 30, or.getLocator("CMC.CommonOktaLaunch")));
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            Assert.fail("Component Url is not launched properly  " + e);
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is to launch component author URL
     */
    public void launchPageAuthURL(String AuthorPage, String Brand) {
        try {
            String url = ConfigReader.getAuthorURL(AuthorPage, Brand);
            log.info("I Navigating to URL: " + url);
            driver.get(url);
            refreshPageWithJS();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to launch publish component url
     */
    public void launchPublishComponent(String Component, String Brand) {
        try {
            String url = getEnvDomain() + ConfigReader.getPublishComponentURL(Component, Brand);
            log.info("I Navigating to URL: " + url);
            driver.get(url);
            Thread.sleep(5000);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 30/5/2022 (Modified by Ditimoni with proper Assert messages)
     * Description: This method is used to verify page
     */
    public void verifyPage(String Page) throws InterruptedException {
        Thread.sleep(4000);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        String url = driver.getCurrentUrl().toLowerCase();
        try {
            Assert.assertTrue("Verification of " + Page + " Page is failed. Actual URL is ::" + url, url.contains(Page.toLowerCase()));
            log.info("Verification of " + Page + " Page is Done");
        } catch (Exception e) {
            log.error("Verification of" + Page + " Page is failed");
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to navigate back to previous page
     */
    public void navigateBack() {
        try {
            driver.navigate().back();
            Thread.sleep(2000);
            log.info("Navigated back to previous page");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to close previous tab
     */
    public void closePreviousTab() {
        try {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(0));
            driver.close();
            driver.switchTo().window(tabs.get(1));
            Thread.sleep(2000);
            log.info("Closed newly Previous tab and focus on new tab");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to close original tab
     */
    public void closeOriginalTab() {
        try {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(1));
            Thread.sleep(2000);
            log.info("Closed Original Tab tab and focus on new tab");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to navigate to child tab
     */
    public void focusOnNewTab() {
        try {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            Thread.sleep(2000);
            log.info("Focused on new tab");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description:  This method is used to close child tab
     */
    public void closeNewlyOpenTab() {
        try {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
            Thread.sleep(2000);
            log.info("Closed newly opened tab");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Kundan Kumar
     * Date: 25/3/2022 (Modified)
     * Description: This method is used to upload image
     */
    public void uploadImage(By locator, String path) {
        try {
            String pathArray[]=path.split("\\\\");
            String finalPath="";
            for (int i = 0; i < pathArray.length ; i++) {
                if (i<pathArray.length-1) {
                    finalPath += pathArray[i] + File.separator;
                }else {
                    finalPath += pathArray[i];
                }
            }
            path=new File(finalPath).getAbsolutePath();
            driver.findElement(locator).sendKeys(path);
            Thread.sleep(5000);
            log.info("Uploaded the photo from path " + path);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 30/5/2022 (Modified by Ditimoni and updated the assert with proper message)
     * Description: This method is implemented to send Value to the text field using
     * key provided in class file/feature file
     */
    public void sendValue(By locator, String key) {
        try {
            Assert.assertTrue(locator + " is not available", Waits.checkElementDisplayed(driver, 20, locator));
            driver.findElement(locator).clear();
            log.info("Text Field has been cleared");
            driver.findElement(locator).sendKeys(key);
            log.info("Input value has been Provided for the text Field");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: A.Tejaswini
     * Date: 6/15/2022(Modified)
     * Description: This method verifies Page Navigation by Tab
     */
    public void verifyPageNavigation(By element, String tab) {
        try {
            if (tab.equalsIgnoreCase("Same Tab") || tab.equalsIgnoreCase("Default")) {
                String cta = driver.findElement(element).getAttribute("href");
                clickWithJS(driver.findElement(element));
                Thread.sleep(5000);
                String[] testData = cta.split("/");
                int size = testData.length;
                if (testData[size - 1].contains("qsId")) {
                    testData = testData[size - 1].split("qsId");
                    size = testData.length;
                }
                log.info(testData[size - 1] + " - " + "clicked");
                if (testData[size - 1].equalsIgnoreCase("coupons")) {
                    Assert.assertTrue(driver.getCurrentUrl().contains("dvp"));
                    log.info("Verified Navigating to dvp Page is Pass");
                } else {
                    Assert.assertTrue(driver.getCurrentUrl().contains(testData[size - 1]));
                    log.info("Verified Navigating to " + testData[size - 1] + " Page is Pass");
                }
            } else if (tab.equalsIgnoreCase("New Tab")) {
                String cta = driver.findElement(element).getAttribute("href");
                clickWithJS(driver.findElement(element));
                focusOnNewTab();
                Thread.sleep(5000);
                String[] testData = cta.split("/");
                int size = testData.length;
                if (testData[size - 1].contains("qsId")) {
                    testData = testData[size - 1].split("qsId");
                    size = testData.length;
                }
                log.info(testData[size - 1] + " - " + "clicked");
                if (testData[size - 1].equalsIgnoreCase("coupons")) {
                    Assert.assertTrue(driver.getCurrentUrl().contains("dvp"));
                    log.info("Verified Navigating to dvp Page is Pass");
                } else {
                    Assert.assertTrue(driver.getCurrentUrl().contains(testData[size - 1]));
                    log.info("Verified Navigating to " + testData[size - 1] + " Page is Pass");
                }
                closeNewlyOpenTab();
            }
            driver.navigate().back();
            Thread.sleep(2000);
            log.info("Navigated back to previous page");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: A.Tejaswini
     * Date: 6/15/2022(Modified)
     * Description: This method verifies Page Navigation by Tab
     */
    public void verifyPageNavigation(WebElement element, String tab) {
        try {
            if (tab.equalsIgnoreCase("Same Tab") || tab.equalsIgnoreCase("Default")) {
                String cta = element.getAttribute("href");
                clickWithJS(element);
                Thread.sleep(5000);
                String[] testData = cta.split("/");
                int size = testData.length;
                if (testData[size - 1].contains("qsId")) {
                    testData = testData[size - 1].split("qsId");
                    size = testData.length;
                }
                log.info(testData[size - 1] + " - " + "clicked");
                if (testData[size - 1].equalsIgnoreCase("coupons")) {
                    Assert.assertTrue(driver.getCurrentUrl().contains("dvp"));
                    log.info("Verified Navigating to dvp Page is Pass");
                } else {
                    Assert.assertTrue(driver.getCurrentUrl().contains(testData[size - 1]));
                    log.info("Verified Navigating to " + testData[size - 1] + " Page is Pass");
                }
            } else if
            (tab.equalsIgnoreCase("New Tab")) {
                String cta = element.getAttribute("href");
                String footerName=element.getText();
                clickWithJS(element);
              	Thread.sleep(5000);
                focusOnNewTab();
                Thread.sleep(5000);
              	String url=driver.getCurrentUrl();
                String[] testData = cta.split("/");
                int size = testData.length;
                if (testData[size - 1].contains("qsId")) {
                    testData = testData[size - 1].split("qsId");
                    size = testData.length;
                }
                log.info(testData[size - 1] + " - " + "clicked");
                if (testData[size - 1].equalsIgnoreCase("coupons")) {
                    Assert.assertTrue(driver.getCurrentUrl().contains("dvp"));
                    log.info("Verified Navigating to dvp Page is Pass");
                } else {
                    String footerLink = footerName.toLowerCase();
                    Assert.assertTrue(url+" Url does not contains "+footerLink,url.contains(footerLink));
                    log.info("Verified Navigating to www." + footerLink + " Page is Pass");
                }
                closeNewlyOpenTab();
            }
            driver.navigate().back();
            Thread.sleep(2000);
            log.info("Navigated back to previous page");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: A.Tejaswini
     * Date: 25/3/2022 (Modified)
     * Description: This method verifies Page Navigation by locator
     */
    public void verifyPageNavigation(By element) {
        try {
            String cta = driver.findElement(element).getAttribute("href");
            System.out.println("href is" + driver.findElement(element).getAttribute("href"));
            clickWithJS(driver.findElement(element));
            Thread.sleep(5000);
            String[] testData = cta.split("/");
            int size = testData.length;
            log.info(testData[size - 1] + " - " + "clicked");
            try {
                System.out.println("driver.getCurrentUrl()" + driver.getCurrentUrl());
                System.out.println("testData[size - 1]" + testData[size - 1]);
                Assert.assertTrue(driver.getCurrentUrl().contains(testData[size - 1]));
                log.info("Verified Navigating to " + testData[size - 1] + " Page is Pass");
            } catch (Exception e) {
                log.error("Verified Navigating to " + testData[size - 1] + " Page is failed");
            }
            driver.navigate().back();
            Thread.sleep(2000);
            log.info("Navigated back to Header page");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: A.Tejaswini
     * Date: 25/3/2022 (Modified)
     * Description: This method verifies Page Navigation by Element
     */
    public void verifyPageNavigation(WebElement element) {
        try {
            String cta = element.getAttribute("href");
            System.out.println("href is " + element.getAttribute("href"));
            clickWithJS(element);
            Thread.sleep(5000);
            String[] testData = cta.split("/");
            int size = testData.length;
            log.info(testData[size - 1] + " - " + "clicked");
            try {
                System.out.println("driver.getCurrentUrl()" + driver.getCurrentUrl());
                System.out.println("testData[size - 1]" + testData[size - 1]);
                Assert.assertTrue(driver.getCurrentUrl().contains(testData[size - 1]));
                log.info("Verified Navigating to " + testData[size - 1] + " Page is Pass");
            } catch (Exception e) {
                log.error("Verified Navigating to " + testData[size - 1] + " Page is failed");
            }
            driver.navigate().back();
            Thread.sleep(2000);
            log.info("Navigated back to Header page");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Kundan Kumar
     * Date: 25/3/2022 (Modified)
     * Description:  This method implemented to get object of SoftAssertions class(Singleton design)
     */
    public static SoftAssertions getSoftAssertInstance() {
        return softAssert;
    }

    /**
     * Author: Kundan Kumar
     * Date: 25/3/2022 (Modified)
     * Description:  This method implemented to set object of ExcelReader class(Singleton design)
     */
    public static void setExcelReaderInstance(ExcelReader exl) {
        excl = exl;
    }

    /**
     * Author: Kundan Kumar
     * Date: 25/3/2022 (Modified)
     * Description:  This method implemented to get object of ExcelReader class(Singleton design)
     */
    public static ExcelReader getExcelReaderInstance() {
        return excl;
    }

    /**
     * Author: Kundan Kumar and Priya Bharti
     * Description: This method implemented to Validate Creative testing by providing tag from style guide
     * Modified Date: 5/5/2022(Tejaswini-Updated code for borderColor)
     */
    public void validateCreativeByTag(WebElement element, String tag, String elementName) {
        try {
            String styleSheet = getBrand() + "Style";
            String channel = null;
            String browser = new ConfigReader().getBrowser();
            if (browser.equals("mobile")) {
                channel = "Mobile";
            } else
                channel = "Desktop";
            String fontSize = ("FontSize" + "-" + channel).toLowerCase();
            String letterSpacing = ("LetterSpacing" + "-" + channel).toLowerCase();
            String lineHeight = ("LineHeight" + "-" + channel).toLowerCase();
            String fontFamily = ("FontFamily").toLowerCase();
            String color = ("Color").toLowerCase();
            String backGround = ("Background").toLowerCase();
            String weight = ("Weight").toLowerCase();
            String height = ("Height").toLowerCase();
            String borderWidth = ("BorderWidth").toLowerCase();
            String borderColor = ("BorderColor").toLowerCase();
            String borderStyle = ("BorderStyle").toLowerCase();
            String cornerRadius = ("CornerRadius").toLowerCase();
            String padding = ("PaddingLeftRight").toLowerCase();
            tag = tag.toLowerCase();
            log.info("element under test " + element);
            log.info("styleSheet name is" + styleSheet);
            log.info("tag name is" + tag);
            log.info("fontSize is" + fontSize);

            if (!excl.getCSSValueUsingTag(styleSheet, tag, fontSize).equals("null")) {
                softAssert.assertThat(element.getCssValue("font-size").contains(excl.getCSSValueUsingTag(styleSheet, tag, fontSize)));
                log.info("font-size of " + elementName + " Actual is : " + element.getCssValue("font-size"));
                log.info("font-size of " + elementName + " Expected is : " + excl.getCSSValueUsingTag(styleSheet, tag, fontSize));
            }
            if (!excl.getCSSValueUsingTag(styleSheet, tag, lineHeight).equals("null")) {
                softAssert.assertThat(element.getCssValue("line-height").contains(excl.getCSSValueUsingTag(styleSheet, tag, lineHeight)));
                log.info("line-height of " + elementName + " Actual is : " + element.getCssValue("line-height"));
                log.info("line-height of " + elementName + " Expected is : " + excl.getCSSValueUsingTag(styleSheet, tag, lineHeight));
            }
            if (!excl.getCSSValueUsingTag(styleSheet, tag, letterSpacing).equals("null")) {
                softAssert.assertThat(element.getCssValue("letter-spacing").contains(excl.getCSSValueUsingTag(styleSheet, tag, letterSpacing)));
                log.info("letter-spacing of " + elementName + " Actual is : " + element.getCssValue("letter-spacing"));
                log.info("letter-spacing of " + elementName + " Expected is : " + excl.getCSSValueUsingTag(styleSheet, tag, letterSpacing));
            }
            if (!excl.getCSSValueUsingTag(styleSheet, tag, fontFamily).equals("null")) {
                String fontFamilyKey = excl.getCSSValueUsingTag(styleSheet, tag, fontFamily);
                validateCreativeByKey(element, "font-family", fontFamilyKey);
//                log.info(("font-family of " + elementName + " Actual is : " + element.getCssValue("font-family")));
//                log.info(("font-family of " + elementName + " Expected is : " + excl.getCSSValueUsingTag(styleSheet, tag, fontFamily)));
            }
            if (!excl.getCSSValueUsingTag(styleSheet, tag, color).equals("null")) {
                String colorKey = excl.getCSSValueUsingTag(styleSheet, tag, color);
                validateCreativeByKey(element, "color", colorKey);
//                log.info("color of " + elementName + " Actual is : " + element.getCssValue("color"));
//                log.info("color of " + elementName + " Expected is : " + excl.getCSSValueUsingTag(styleSheet, tag, color));
            }
            if (!excl.getCSSValueUsingTag(styleSheet, tag, backGround).equals("null")) {
                String bgColorKey = excl.getCSSValueUsingTag(styleSheet, tag, backGround);

                validateCreativeByKey(element, "background-color", bgColorKey);
//                log.info("background-color of " + elementName + " Actual is : " + element.getCssValue("background-color"));
//                log.info("background-color of " + elementName + " Expected is : " + excl.getCSSValueUsingTag(styleSheet, tag, backGround));
            }
            if (!excl.getCSSValueUsingTag(styleSheet, tag, weight).equals("null")) {
                softAssert.assertThat(element.getCssValue("weight").contains(excl.getCSSValueUsingTag(styleSheet, tag, weight)));
                log.info("weight of " + elementName + " Actual is : " + element.getCssValue("weight"));
                log.info("background-color of " + elementName + " Expected is : " + excl.getCSSValueUsingTag(styleSheet, tag, weight));
            }
            if (!excl.getCSSValueUsingTag(styleSheet, tag, height).equals("null")) {
                softAssert.assertThat(element.getCssValue("height").contains(excl.getCSSValueUsingTag(styleSheet, tag, height)));
                log.info("height of " + elementName + " Actual is : " + element.getCssValue("height"));
                log.info("height of " + elementName + " Expected is : " + excl.getCSSValueUsingTag(styleSheet, tag, height));
            }
            if (!excl.getCSSValueUsingTag(styleSheet, tag, borderWidth).equals("null")) {
                softAssert.assertThat(element.getCssValue("border-bottom-width").contains(excl.getCSSValueUsingTag(styleSheet, tag, borderWidth)));
                softAssert.assertThat(element.getCssValue("border-top-width").contains(excl.getCSSValueUsingTag(styleSheet, tag, borderWidth)));
                softAssert.assertThat(element.getCssValue("border-right-width").contains(excl.getCSSValueUsingTag(styleSheet, tag, borderWidth)));
                softAssert.assertThat(element.getCssValue("border-left-width").contains(excl.getCSSValueUsingTag(styleSheet, tag, borderWidth)));
                log.info("borderWidth of " + elementName + " Actual is : " + element.getCssValue("rule-color"));
                log.info("borderWidth of " + elementName + " Expected is : " + excl.getCSSValueUsingTag(styleSheet, tag, borderWidth));

            }
            if (!excl.getCSSValueUsingTag(styleSheet, tag, borderColor).equals("null")) {
                String borderClrKey = excl.getCSSValueUsingTag(styleSheet, tag, borderColor);
                validateCreativeByKey(element, "border-bottom-color", borderClrKey);
                validateCreativeByKey(element, "border-top-color", borderClrKey);
                validateCreativeByKey(element, "border-left-color", borderClrKey);
                validateCreativeByKey(element, "border-right-color", borderClrKey);
            }
            if (!excl.getCSSValueUsingTag(styleSheet, tag, cornerRadius).equals("null")) {
                softAssert.assertThat(element.getCssValue("border-bottom-left-radius").contains(excl.getCSSValueUsingTag(styleSheet, tag, cornerRadius)));
                softAssert.assertThat(element.getCssValue("border-bottom-right-radius").contains(excl.getCSSValueUsingTag(styleSheet, tag, cornerRadius)));
                softAssert.assertThat(element.getCssValue("border-top-left-radius").contains(excl.getCSSValueUsingTag(styleSheet, tag, cornerRadius)));
                softAssert.assertThat(element.getCssValue("border-top-right-radius").contains(excl.getCSSValueUsingTag(styleSheet, tag, cornerRadius)));
                log.info("cornerRadius of " + elementName + " Actual is : " + element.getCssValue("border-top-right-radius"));
                log.info("cornerRadius of " + elementName + " Expected is : " + excl.getCSSValueUsingTag(styleSheet, tag, cornerRadius));
            }
            if (!excl.getCSSValueUsingTag(styleSheet, tag, borderStyle).equals("null")) {
                softAssert.assertThat(element.getCssValue("border-bottom-style").contains(excl.getCSSValueUsingTag(styleSheet, tag, borderStyle)));
                softAssert.assertThat(element.getCssValue("border-top-style").contains(excl.getCSSValueUsingTag(styleSheet, tag, borderStyle)));
                softAssert.assertThat(element.getCssValue("border-right-style").contains(excl.getCSSValueUsingTag(styleSheet, tag, borderStyle)));
                softAssert.assertThat(element.getCssValue("border-left-style").contains(excl.getCSSValueUsingTag(styleSheet, tag, borderStyle)));
                log.info("borderStyle of " + elementName + " Actual is : " + element.getCssValue("border-bottom-style"));
                log.info("borderStyle of " + elementName + " Expected is : " + excl.getCSSValueUsingTag(styleSheet, tag, borderStyle));
            }
            if (!excl.getCSSValueUsingTag(styleSheet, tag, padding).equals("null")) {
                softAssert.assertThat(element.getCssValue("padding-left").contains(excl.getCSSValueUsingTag(styleSheet, tag, padding)));
                softAssert.assertThat(element.getCssValue("padding-right").contains(excl.getCSSValueUsingTag(styleSheet, tag, padding)));
                log.info("borderStyle of " + elementName + " Actual is : " + element.getCssValue("padding-left"));
                log.info("borderStyle of " + elementName + " Expected is : " + excl.getCSSValueUsingTag(styleSheet, tag, padding));
            }
        } catch (Exception e) {
            log.error(e);
            softAssert.fail("Null Pointer Exception");
            e.printStackTrace();
        }

    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method implemented to Validate Creative testing by key value of *property name from style guide
     */
    public void validateCreativeByKey(WebElement element, String propertyName, String key) {
        try {
            String brand = getBrand();
            String browser = new ConfigReader().getBrowser();
            String theme = new ConfigReader().getTheme(brand);
            String columnTheme = theme + "_" + browser.toLowerCase();
            if (key.contains("-") || key.contains(":")) {
                key = key.replaceAll("-", "");
                key = key.replaceAll(":", "");
                key = key.replaceAll(" ", "");
            }
            log.info("Brand is " + brand);
            log.info("Key is " + key);
            log.info("ColumnTheme is " + columnTheme);
            softAssert.assertThat(element.getCssValue(propertyName)).contains(excl.getCSSValueUsingKey(brand, key, columnTheme));
            log.info("CSS Value of " + propertyName + " actual is : " + element.getCssValue(propertyName));
            log.info("CSS Value ccc of " + propertyName + " expected is : " + excl.getCSSValueUsingKey(brand, key, columnTheme));
        } catch (Exception e) {
            log.error(e);
            softAssert.fail("Null Pointer Exception");
            e.printStackTrace();
        }
    }

    public static void doAssertAll() {
        softAssert.assertAll();
    }

    /**
     * Author: Ditimoni Borpatra Gohain
     * Date: 25/3/2022 (Modified)
     * Description: This function will return the text of the element
     *
     * @arguments: locator of Type By
     * @return: text of type String
     */
    public String getText(By locator) {
        String text = "";
        try {
            Assert.assertTrue(locator + " is not displayed", Waits.checkElementDisplayed(driver, 20, locator));
            text = driver.findElement(locator).getText();
            Thread.sleep(200);
            log.info(text + " is the text of the element");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        return text;
    }

    /**
     * Author: Tejaswini
     * Date: 7/19/22
     * Description: This function will return the text of the element
     *
     * @arguments: WebElement
     * @return: text of type String
     */
    public String getText(WebElement ele) {
        String text = "";
        try {
            Assert.assertTrue(ele + " is not displayed", Waits.checkElementDisplayed(driver, 20, ele));
            text = ele.getText();
            Thread.sleep(200);
            log.info(text + " is the text of the element");
        } catch (AssertionError | Exception e) {
            log.error(e);
            e.printStackTrace();
            Assert.fail("Failed to return the text of the element" + e);
        }
        return text;
    }

    /**
     * Author: Kundan Kumar
     * Date: 8/26/22
     * Description: This function will return the text of the element using JS
     *
     * @arguments: By Locator
     * @return: text of type String
     */
    public String getTextUsingJS(By locator) {
        String text = "";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            text = js.executeScript("return arguments[0].innerHTML;", driver.findElement(locator)).toString();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        return text;
    }

    // TODO: 3/15/2022 -- Need to check where we can use as a key only

    /**
     * Author: Kundan Kumar and Priya Bharti
     * Description: This function verifies the Creative of Border width in publish Instance
     * Date: 15/3/2022
     */
    public void creativeValidationOfBorderWidth(WebElement element, String key) {
        try {
            validateCreativeByKey(element, "border-bottom-width", key);
            validateCreativeByKey(element, "border-top-width", key);
            validateCreativeByKey(element, "border-right-width", key);
            validateCreativeByKey(element, "border-left-width", key);
            log.info("Creative of Border Width is done");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    // TODO: 3/15/2022 -- Need to check where we can use as a key only

    /**
     * Author: Kundan Kumar and Priya Bharti
     * Description: This function verifies the Creative of Border color in publish Instance
     * Date: 15/3/2022
     */
    public void creativeValidationOfBorderColor(WebElement element, String key) {
        try {
            validateCreativeByKey(element, "border-bottom-color", key);
            validateCreativeByKey(element, "border-top-color", key);
            validateCreativeByKey(element, "border-right-color", key);
            validateCreativeByKey(element, "border-left-color", key);
            log.info("Creative of Border Color is done");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    // TODO: 3/15/2022 -- Need to check where we can use as a key only

    /**
     * Author: Kundan Kumar and Priya Bharti
     * Description: This function verifies the Creative of Border style in publish Instance
     * Date: 15/3/2022
     */
    public void creativeValidationOfBorderStyle(WebElement element, String key) {
        try {
            validateCreativeByKey(element, "border-bottom-style", key);
            validateCreativeByKey(element, "border-top-style", key);
            validateCreativeByKey(element, "border-right-style", key);
            validateCreativeByKey(element, "border-left-style", key);
            log.info("Creative of Border Style is done");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    // TODO: 3/15/2022 -- Need to check where we can use as a key only

    /**
     * Author: Kundan Kumar and Priya Bharti
     * Description: This function verifies the Creative of Border Radius in publish Instance
     * Date: 15/3/2022
     */
    public void creativeValidationOfBorderRadius(WebElement element, String key) {
        try {
            validateCreativeByKey(element, "border-bottom-left-radius", key);
            validateCreativeByKey(element, "border-bottom-right-radius", key);
            validateCreativeByKey(element, "border-top-left-radius", key);
            validateCreativeByKey(element, "border-top-right-radius", key);
            log.info("Creative of Border Radius is done");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    // TODO: 3/15/2022 -- Need to check where we can use as a key only

    /**
     * Author: Kundan Kumar and Priya Bharti
     * Description: This function verifies the Creative of Border Radius using value from properties file in publish Instance
     * Date: 15/3/2022
     */
    public void creativeOfRadiusByFile(WebElement element, String key) {
        try {
            validateCreativeByKey(element, "border-bottom-left-radius", key);
            validateCreativeByKey(element, "border-bottom-right-radius", key);
            validateCreativeByKey(element, "border-top-left-radius", key);
            validateCreativeByKey(element, "border-top-right-radius", key);
            log.info("Creative of Border Radius is done");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Kundan Kumar,Priya Bharti and Tejaswini
     * Description: This function verifies the Creative of different types of CTA in publish Instance
     * Date: 18/4/2022(Modified)
     *
     * @arguments: WebElement and String
     */
    public void creativeCTAAction(WebElement element, String action) {
        String suffixSplit[] = element.getAttribute("class").split(" ");
        String suffix = suffixSplit[suffixSplit.length - 1];
        if (element.getAttribute("class").contains("disabled")) {
            if (suffix.contains("small")) {
                String tag = (suffixSplit[suffixSplit.length - 1] + "-" + suffixSplit[suffixSplit.length - 2] + "-button-" + action);
                System.out.println("tag--" + tag);
                validateCreativeByTag(element, suffixSplit[suffixSplit.length - 1] + "-" + suffixSplit[suffixSplit.length - 2] + "-button-" + action, element.getText());
            } else {
                validateCreativeByTag(element, "button-" + suffix + "-disabled", element.getText());
            }
        } else {
            if (suffix.contains("small")) {
                String tag = (suffixSplit[suffixSplit.length - 1] + "-" + suffixSplit[suffixSplit.length - 2] + "-button-" + action);
                System.out.println("tag--" + tag);
                validateCreativeByTag(element, tag, element.getText());
            } else if (suffix.contains("large")) {
                String tag = "button-" + (suffixSplit[suffixSplit.length - 2] + "-" + action);
                System.out.println("tag--" + tag);
                validateCreativeByTag(element, tag, element.getText());
            } else {
                validateCreativeByTag(element, "button-" + suffix + "-" + action, element.getText());
            }
        }
    }

    /**
     * Author: Kundan Kumar
     * Description: This function is implemented to get date as string
     * Parameter- dateFormat is the pattern or format of string e.g. yyyy-MM-dd, MM-dd-yyyy HH:mm:ss, dd/MM/yyyy HH:mm:ss etc.
     * NumDaysFromToday- add number of day in today's date e.g 0 will return today's date, 1 will return tomorrow's date and -1 will return yesterday's date
     * Date: 21/3/2022
     */
    public String getDate(String dateFormat, int NumDaysFromToday) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, NumDaysFromToday);
        return df.format(c.getTime());
    }

    /*
    Author: Ditimoni Borpatra Gohain
    Description: This function will move the control to the frame
    Param: It takes a locator as argument
    Date: 8/4/2022
    */
    public void switchToFrame(By locator) {
        driver.switchTo().frame(driver.findElement(locator));
    }

    /*
    Author: Ditimoni Borpatra Gohain
    Description: This function will move the control back to the original page
    Date: 8/4/2022
    */
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    /**
     * Author: Shubham Kumar
     * Date: 4/11/2022
     * Description: This method returns the env domains for specific environment.
     */
    public String getEnvDomain() {
        String env = System.getProperty("Env").toUpperCase();
        String envDomain = null;
        switch (env) {
            case "PROD":
                envDomain = "https://www.";
                break;
            case "PREPROD":
                envDomain = "https://stg2-ras.";
                break;
            case "QA1":
                envDomain = "https://qa1-rs.";
                break;
            case "QA2":
                envDomain = "https://qa2-rs.";
                break;
            case "QA3":
                envDomain = "https://qa3-rs.";
                break;
            case "QA4":
                envDomain = "https://qa4-rs.";
                break;
            case "QA5":
                envDomain = "https://qa5-rs.";
                break;
            case "QA6":
                envDomain = "https://qa6-rs.";
                break;
            case "POD1":
                envDomain = "https://pod1-rs.";
                break;
            case "POD2":
                envDomain = "https://pod2-rs.";
                break;
            default:
                log.info("Incorrect ENV Configured");
        }
        return envDomain;
    }

    /**
     * Author: Ditimoni Borptra Gohain
     * Date: 24/5/2022
     * Description: This method is used to select option from dropdown by visible text
     *
     * @argument WebElement, String
     */
    public void selectByVisibleText(WebElement webElement, String value) {
        if (Waits.visibilityOfElement(driver, 10, webElement) != null) {
            Select sel = new Select(webElement);
            sel.selectByVisibleText(value);
            log.info(value + "Has been selected from the drop down");
        } else {
            Assert.fail(webElement + " is not available");
        }
    }

    /**
     * Author: Ditimoni Borpatra Gohain
     * Date: 24/5/2022
     * Description: This method is implemented to enter text in text field
     *
     * @argument Webelement, String
     */
    public void sendValue(WebElement webElement, String key) {
        try {
            if (Waits.visibilityOfElement(driver, 20, webElement) != null) {
                log.info("Text Field is displayed");
                webElement.clear();
                log.info("Text Field has been cleared");
                webElement.sendKeys(key);
                Thread.sleep(200);
                log.info("Input value has been Provided for the text Field");
            } else {
                Assert.fail(webElement + " is not available");
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /* Description: This method will delete a directory/folder from a given location */
    public static void deleteDirectory(File file) {
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            subfile.delete();
        }
    }

    /* Description: This method will return the attribute value of passed attribute*/
    public String getAttribute(By locator, String attr) {
        String str = "";
        try {
            str = driver.findElement(locator).getAttribute(attr);
        } catch (Exception e) {
            return "";
        }
        if (str == null)
            return "";
        else
            return str;
    }

    /* Description: This method will return the attribute value of passed attribute*/
    public String getAttribute(WebElement ele, String attr) {
        String str = "";
        try {
            str = ele.getAttribute(attr);
        } catch (Exception e) {
            return "";
        }

        if (str == null)
            return "";
        else
            return str;
    }

    /* Description: This method will return the attribute value of passed attribute*/
    public boolean isSelected(By locator) {
        boolean flag = false;
        try {
            flag = driver.findElement(locator).isSelected();
        } catch (Exception e) {
            log.error(e);
            Assert.fail("Failed in isSelected " + e);
        }
        return flag;
    }

    /**
     * Author: Kundan Kumar
     * Date: 6/24/2022
     * Description: This method is used to click on element using javascript, method overloading
     */
    public void clear(By locator) {
        try {
            if (Waits.checkElementDisplayed(driver, 10, locator)) {
                driver.findElement(locator).clear();
            } else {
                Assert.fail(locator + " Element is not displayed");
            }
        } catch (Exception e) {
            Assert.fail(locator + " Failed While clearing the field");
        }
    }

    public WebElement getElement(By locator) {
        WebElement webElement = null;
        if (Waits.checkElementDisplayed(driver, 20, locator)) {
            webElement = driver.findElement(locator);
        } else {
            Assert.fail(locator + " is not displayed");
        }
        return webElement;
    }

    public String getDateForWf(int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, +hour);
        calendar.add(Calendar.MINUTE, min);
        Format f = new SimpleDateFormat("MM-dd-yyyy hh:mm");
        String str = f.format(calendar.getTime());
        return str;
    }

    public void launchComponentURL(String Component, String Brand) {
        try {
            String url = getEnvDomain() + ConfigReader.getComponentURL(Component, Brand);
            log.info("I Navigating to URL: " + url);
            driver.get(url);
            Waits.waitTillPageIsReady(driver);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            Assert.fail("Component Url is not launched properly  " + e);
        }
    }

    public void click(String pageElementName) {
        if (Waits.checkElementDisplayed(driver, 20, or.getLocator(pageElementName))) {
            if (Waits.checkElementClickable(driver, 20, or.getLocator(pageElementName)))
                driver.findElement(or.getLocator(pageElementName)).click();
            else
                Assert.fail(pageElementName + " Element is not clickable");
        } else {
            Assert.fail(pageElementName + " Element is not displayed");
        }
    }


    public String getAuthEnvDomain() {
        String env = System.getProperty("Env").toUpperCase();
        String envDomain = null;
        switch (env) {
            case "QA1":
                envDomain = "https://auth-qa1-rs-web.dxide.com/";
                break;
            case "QA2":
                envDomain = "https://auth-qa2-rs-web.dxide.com/";
                break;
            case "QA3":
                envDomain = "https://auth-qa3-rs-web.dxide.com/";
                break;
            case "QA4":
                envDomain = "https://auth-qa4-rs-web.dxide.com/";
                break;
            case "QA5":
                envDomain = "https://auth-qa5-rs-web.dxide.com/";
                break;
            case "QA6":
                envDomain = "https://auth-qa6-rs-web.dxide.com/";
                break;
            case "PROD":
                envDomain = "https://dispauth1-prod-ras.dxide.com/";
                break;
            default:
                log.info("Incorrect ENV Configured");
        }
        return envDomain;
    }

    public void select(String pageElementName, int index) {
        selectByIndex(or.getLocator(pageElementName), index);

    }

    public WebElement getDefaultSelectedOption(By locator) {
        WebElement element = null;
        try {
            if (Waits.visibilityOfElement(driver, 20, locator) != null) {
                Select sel = new Select(driver.findElement(locator));
                element = sel.getFirstSelectedOption();
                log.info("Default selected option in the dropdown : " + element.getText());
            } else {
                Assert.fail(locator + " is not visible");
            }
        } catch (Exception e) {
            log.error(e);
            Assert.fail("Failed to get the default selected option " + e);
        }
        return element;
    }

    /**
     * Author: karthikeshwar raajenthiran
     * Date: 10/26/2022
     * Description: Overloaded clear method using string as an argument
     */
    public void clear(String ele) {
        By locator = or.getLocator(ele);
        try {
            if (Waits.checkElementDisplayed(driver, 10, locator)) {
                driver.findElement(locator).clear();
            } else {
                Assert.fail(locator + " Element is not displayed");
            }
        } catch (Exception e) {
            Assert.fail(locator + " Failed While clearing the field");
        }
    }

    /**
     * Author: chandrasekharReddy
     * Date: 11/02/2022
     * Description: Move to element and double click using webElement as an argument
     */
    public void moveToElementAndDoubleClick(WebElement webElement) {
        log.info("Moving to element..");
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).doubleClick().build().perform();
        log.info("Clicked the given element " + webElement + " successfully..");
    }

    public List<String> handlingWebTable(List<WebElement> elements) {
        ArrayList<String> rowData = new ArrayList<>();
        for (WebElement row : elements) {
            totalRows = row.findElements(By.tagName("td"));
            for (WebElement column : totalRows) {
                rowData.add(column.getText());
            }
        }
        return rowData;
    }

    public void refreshWebPage() {
        driver.navigate().refresh();
    }

    public List<WebElement> getElements(By locator) {
        List<WebElement> elements = null;
        try {
            elements = driver.findElements(locator);

        } catch (Exception e) {
            log.info("Could not find the elements");
            Assert.fail("Could not find the elements");

        }
        return elements;
    }

    public void accessiblityAssertion( String currentUrl, String pageUrl, String pageType)
    {
        softAssert.assertThat(currentUrl).contains(pageUrl).describedAs("Failed url is : "+pageUrl+" Page type is : "+pageType);
    }

    public String getCSSValue(By by, String attribute)
    {
        String CssValue = driver.findElement(by).getCssValue(attribute);
        return CssValue;
    }

    public void launchUrlInGeoLocation(double lat,double lon,WebDriver driver){
        DevTools devTools = ((ChromeDriver)driver).getDevTools();
        devTools.createSession();
        devTools.send(Emulation.setGeolocationOverride(Optional.of(lat), Optional.of(lon), Optional.of(1)));
        devTools.send(Emulation.setDeviceMetricsOverride(412,
                915,
                50,
                true,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));
       // driver.get(url);
        driver.navigate().refresh();
    }

    public void alertHandling() throws InterruptedException {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            if (!(webDriverWait.until(ExpectedConditions.alertIsPresent()) == null)) {
                driver.switchTo().alert().accept();
                log.info("Alert is present");
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Alert is not present");
        }
    }
  
  	public void closeDevTools(WebDriver driver) {
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.send(Emulation.clearDeviceMetricsOverride());
    	devTools.send(Emulation.clearGeolocationOverride());
    	devTools.send(Emulation.resetPageScaleFactor());
    	devTools.close();
    	System.setProperty("BrowserName","chrome");
	}
}
