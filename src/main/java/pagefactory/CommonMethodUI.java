package pagefactory;

import org.openqa.selenium.WebElement;
import utils.*;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class CommonMethodUI {

    private WebDriver driver;
    Logger log = LoggerFile.getLogger(this.getClass());
    ReusableMethodUI rm = new ReusableMethodUI(BaseClass.getDriver());
    ConfigReader config = new ConfigReader();
    SoftAssertions softAssert = ReusableMethodUI.getSoftAssertInstance();
    private ObjectReader or;

    public CommonMethodUI(WebDriver driver) {
        this.driver = driver;
    }


    /***
     Author: Priya Bharti
     Date: 25/3/2022 (Modified)
     Description: This method to get theme of the brand
     */
    public String getTheme() {
        String theme = null;
        try {
//            TODO it is giving nullpointer exception
            Waits.visibilityOfElement(driver, 30, or.getLocator("CommonMethodUI.BrandTheme"));
            WebElement googleSearchBtn = driver.findElement(or.getLocator("CommonMethodUI.BrandTheme"));
            String test = googleSearchBtn.getAttribute("class");
            System.out.println(test);
            if (test.contains("dark"))
                theme = "dark";
            else theme = "light";
            System.out.println(theme);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        return theme;
    }

    /***
     Author: Kundan Kumar
     Date: 25/3/2022 (Modified)
     Description: This method Verifies Header
     */
    public void verifyHeader() {
        try {
            if (config.getValidationType().equalsIgnoreCase("creative")) {

                rm.validateCreativeByKey(driver.findElement(or.getLocator("CommonMethodUI.Header")), "background-color", "headercolorsbackground");

                rm.validateCreativeByKey(driver.findElement(or.getLocator("CommonMethodUI.RewardPts")), "color", "colorstextbodyprimary");
                rm.validateCreativeByKey(driver.findElement(or.getLocator("CommonMethodUI.RewardPts")), "font-family", "typographyfontbrandtertiary");

                rm.validateCreativeByKey(driver.findElement(or.getLocator("CommonMethodUI.RewardPts")), "border-left-width", "border-width-unselected");
                rm.validateCreativeByKey(driver.findElement(or.getLocator("CommonMethodUI.RewardPts")), "height", "border-height");
                rm.validateCreativeByKey(driver.findElement(or.getLocator("CommonMethodUI.Divider")), "border-left-color", "headercolorslineprimary");

                rm.validateCreativeByKey(driver.findElement(or.getLocator("CommonMethodUI.HamburgerMenuIcon")), "background-color", "header-colors-element-hamb");
                log.info("Header Creative Validation is completed.");

            } else {
                Assert.assertTrue("Header is not displayed",Waits.checkElementDisplayed(driver, 5, or.getLocator("CommonMethodUI.Header")));
                log.info("Verification Done for header");
            }
        } catch (Exception e) {
            Assert.fail("Header is not verified"+e);
            log.error(e);
            e.printStackTrace();
        }
    }

    /***
     Author: Kundan Kumar
     Date: 25/3/2022 (Modified)
     Description: This method Verifies Heading
     */
    public void verifyHeading() {
        try {
            Assert.assertTrue("Heading is not available",Waits.checkElementDisplayed(driver,20,or.getLocator("CommonMethodUI.Heading")));
            log.info("Heading is available on the page");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /***
     Author: Kundan Kumar
     Date: 30/5/2022 (Modified by Ditimoni with assert message)
     Description: This method Verifies SQW Image
     */
    public void verifySgwImage() {
        try {
// For PMUSA SGW is image(no need to do creative validation) but for other opco its a text confirmed by Mathew
           if (config.getValidationType().equalsIgnoreCase("creative")) {
                if (!Waits.visibilityOfElement(driver, 5, or.getLocator("CommonMethodUI.CrCopyRight")).getText().contains("PHILIP MORRIS USA")) {
                    // TODO: 2/23/2022 -- need to add Tag for SGW text
                    rm.validateCreativeByTag(driver.findElement(or.getLocator("CommonMethodUI.SGWImage")), "", "SGWImage");
                    log.info("SGW Image Creative Validation is completed.");
                } else {
                    log.info("SGW Image Creative Validation not applicable for PMUSA Opco. Since SGW is image.");
                }
            } else {
                Assert.assertTrue("SGW Image is not displayed",Waits.checkElementDisplayed(driver, 15, or.getLocator("CommonMethodUI.SGWImage")));
                log.info("SGW Image Displayed");
            }
        } catch (Exception e) {
            Assert.fail("SgwImage is not verified");
            log.error(e);
            e.printStackTrace();
        }
    }


    /**
     * Author: Kundan Kumar
     * Date: 25/3/2022 (Modified)
     * Description: This method clicks on MyAccount
     */
    public void iClickOnMyAccount() {
        try {
            rm.clickWithJS(or.getLocator("CommonMethodUI.MyAccount"));
            log.info("My Account link is clicked");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 25/3/2022 (Modified)
     * Description: This method clicks on LoadMore CTA
     */
    public void loadMoreCTA() {
        try {
            if (config.getValidationType().equalsIgnoreCase("creative")) {
                if (Waits.checkElementDisplayed(driver, 5, or.getLocator("CommonMethodUI.LoadMoreCTA"))) {
                    while (Waits.checkElementDisplayed(driver, 5, or.getLocator("CommonMethodUI.LoadMoreCTA"))) {
                        rm.validateCreativeByTag(driver.findElement(or.getLocator("CommonMethodUI.LoadMoreCTA")), "button-primary-Normal", "load more CTA button");
                        rm.mouseHover(or.getLocator("CommonMethodUI.LoadMoreCTA"));
                        rm.validateCreativeByTag(driver.findElement(or.getLocator("CommonMethodUI.LoadMoreCTA")), "button-primary-Hover", "Load More button Hover");
                    }
                }
            } else {
                    rm.scrollInToViewIfNeeded(driver.findElement(or.getLocator("CommonMethodUI.LoadMoreCTA")));
                if (Waits.checkElementDisplayed(driver, 5, or.getLocator("CommonMethodUI.LoadMoreCTA"))) {
                    int i = 1;
                    while (Waits.checkElementDisplayed(driver, 5, or.getLocator("CommonMethodUI.LoadMoreCTA"))) {
                        Assert.assertTrue(Waits.checkElementDisplayed(driver, 5, or.getLocator("CommonMethodUI.LoadMoreCTA")));
                        rm.clickWithJS(or.getLocator("CommonMethodUI.LoadMoreCTA"));
                        log.info("Clicked on Load More comment");
                        if (new ConfigReader().getBrowser().equals("mobile")) {
                            String xpath;
                            int j = i + 1;
                            xpath = "(//*[@id='SGW_Image'] | //*[@class='SGW-PAD'])[" + j + "]";
                            Assert.assertTrue(Waits.checkElementDisplayed(driver, 20, By.xpath(xpath)));
                            log.info("Load More SGW verified in mobile view");
                        } else {
                            log.info("SGW image is not available in desktop view");
                        }
                        i++;
                        if (i == 4) {
                            break;
                        }
                    }
                } else {
                    log.info("load more button is not available");
                }
            }
        }catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Kundan Kumar
     * Date: 25/3/2022 (Modified)
     * Description: This method checks availability of Load More SGW Image
     */
    public void LoadMoreSGWImage() {
        try {
            if (new ConfigReader().getBrowser().equals("mobile")) {
                // For PMUSA SGW is image(no need to do creative validation) but for other opco its a text confirmed by Mathew
                if (config.getValidationType().equalsIgnoreCase("creative")) {
                    if (!Waits.visibilityOfElement(driver, 5, or.getLocator("CommonMethodUI.CrCopyRight")).getText().contains("PHILIP MORRIS USA")) {
                        // TODO: 2/23/2022 -- need to add Tag for SGW text
                        rm.validateCreativeByTag(driver.findElement(or.getLocator("CommonMethodUI.LoadMoreSGW")), "", "SGWImage");
                        log.info("SGW Image Creative Validation is completed.");
                    } else {
                        log.info("SGW Image Creative Validation not applicable for PMUSA Opco. Since SGW is image.");
                    }
                } else {
                    Assert.assertTrue("Load more CTA is not displayed",Waits.checkElementDisplayed(driver, 5, or.getLocator("CommonMethodUI.LoadMoreSGW")));
                    log.info("Load More SGW verified in mobile view");
                }
            } else {
                log.info("SGW image is not available in desktop view");
            }
        } catch (NoSuchElementException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Kundan Kumar
     * Date: 25/3/2022 (Modified)
     * Description: This method verifies Brand Logo
     */
    public void verifyBrandLogo() {
        try {
            Assert.assertTrue("BrandLogo is not Displayed", Waits.checkElementDisplayed(driver, 5, or.getLocator("CommonMethodUI.BrandLogo")));
            log.info("BrandLogo is  Displayed");
        } catch (Exception e) {
            Assert.fail("BrandLogo is not displayed"+e);
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Author: Ditimoni Borpatra Gohain
     * Date: 27/5/2022
     * Description: This method verifies the captcha url whether it is loaded properly
     */
    public void verifyCaptchaLoad() {
        try {
            Assert.assertTrue("Captcha is not loaded properly ",Waits.checkElementDisplayed(driver , 10 , driver.findElement(or.getLocator("CommonMethodUI.CaptchaError"))));
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public void clickOnLogOut() {
        try {
            Assert.assertTrue("Logout is not clickable" , Waits.elementToBEClickable(driver, 10, or.getLocator("LogOutCTA.LogoutCTA"))!=null);
            rm.clickWithJS(driver.findElement(or.getLocator("LogOutCTA.LogoutCTA")));
            log.info("Logout CTA Clicked");
            if(!driver.getCurrentUrl().contains("auth")) {
                if (System.getProperty("BrowserName").equalsIgnoreCase("mobile") && (rm.getBrand().equalsIgnoreCase("PWA") || rm.getBrand().equalsIgnoreCase("MYPARLIAMENT") || rm.getBrand().equalsIgnoreCase("LM")))
                    rm.switchToFrame(or.getLocator("LoginPage.IframeLoginPage"));
                log.info("Switched to ContentFrame");
                if (!Waits.checkElementDisplayed(driver, 30, or.getLocator("LogOutCTA.SignInButton"))) {
                    rm.clickWithJS(or.getLocator("LogOutCTA.LogoutCTA"));
                    Assert.assertTrue("Login Page is not Displayed", Waits.checkElementDisplayed(driver, 15, or.getLocator("LogOutCTA.SignInButton")));
                    log.info("Login Page is Displayed");
                }
            }
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            Assert.fail("click on Logout is not successful");
            log.error(e);
            e.printStackTrace();
        }
    }
}
