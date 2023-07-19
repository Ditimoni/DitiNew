package utils;

import java.io.*;
import java.util.*;

import org.openqa.selenium.By;
import org.yaml.snakeyaml.Yaml;
import org.junit.Assert;
import org.apache.log4j.Logger;

public class ObjectReader {
  private static Logger log = LoggerFile.getLogger(ObjectReader.class);
  
    /**
     * Author : Priya Bharti
     * Description: Map will contain the values on the basis of key-value pair.
     * Here "?" is provided as wildcard as we dont know that at runtime what the class
     * type of the key and value object of the Map is going to be
     */
    static Map<?, ?> property;
    static String objectFilePath = "ObjectRepository/ElementLocator.yml";

    /**
     * Author : Priya Bharti
     * Description : This method is to initialize the Object repository
     * This will load the Yaml file and store the data into the map object
     */
    public static void initializeObjectRepository() {
        try {
            Reader rd = new FileReader(objectFilePath);
            Yaml yml = new Yaml();
            /** This will initialize the map object by using yml.load method and will cast it to the map object */
            property = (Map<?, ?>) yml.load(rd);
            rd.close(); // close the reader object
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Author : Priya Bharti and Kundan Kumar
     * Description : This method is to get the element locator from the map object
     *
     * @param ele will hold PageName.ElementName value which have given in the class file
     * @return : This method at the end returns a By locator with correct value of Locator type and Locator value.
     */
    public static By getLocator(String ele) {
        By locator = null;
        try {
            String[] elementLocator = ele.split("\\.");
            String pageName = elementLocator[0]; /** Here pageName is the respective page class for the web element. */
            String elementName = elementLocator[1]; /** Here elementName the element you are looking for. */
            /** This map object will hold the values for page. */
            /** This is to get the value of element with Locator Type on the basis of element Name as a key. */
            int index = 0;
            String[] element;
            if (elementName.contains("[")) {
                Map<?, ArrayList> map = (Map<?, ArrayList>) property.get(pageName);
                index = Integer.parseInt(String.valueOf(elementName.charAt(elementName.length() - 2)));
                element = map.get(elementName.substring(0, elementName.length() - 3)).get(index).toString().split(":-");
            } else {
                Map<?, ?> map = (Map<?, ?>) property.get(pageName);
                element = map.get(elementName).toString().split(":-");
            }
            String locatorType = element[0];
            String locatorValue = element[1];
            switch (locatorType.trim().toLowerCase()) {
                case "id":
                    locator = By.id(locatorValue);
                    break;
                case "name":
                    locator = By.name(locatorValue);
                    break;
                case "cssselector":
                    locator = By.cssSelector(locatorValue);
                    break;
                case "linktext":
                    locator = By.linkText(locatorValue);
                    break;
                case "partiallinktext":
                    locator = By.partialLinkText(locatorValue);
                    break;
                case "tagname":
                    locator = By.tagName(locatorValue);
                    break;
                case "xpath":
                    locator = By.xpath(locatorValue);
                    break;
            }
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            Assert.fail("Failed to get the element locator from the map object"+e);
        }
        return locator;
    }

    public static String extractXpathFromLocator(By locator) {
        String temp=locator.toString();
        temp=temp.substring(temp.indexOf(":")+1).trim();
        return temp;
    }

    public static By getLocator(String ele, int xpathIndex) {
        By locator = null;
        try {
            String[] elementLocator = ele.split("\\.");
            String pageName = elementLocator[0]; /** Here pageName is the respective page class for the web element. */
            String elementName = elementLocator[1]; /** Here elementName the element you are looking for. */
            /** This map object will hold the values for page. */
            /** This is to get the value of element with Locator Type on the basis of element Name as a key. */
            int index = 0;
            String[] element;
            if (elementName.contains("[")) {
                Map<?, ArrayList> map = (Map<?, ArrayList>) property.get(pageName);
                index = Integer.parseInt(String.valueOf(elementName.charAt(elementName.length() - 2)));
                element = map.get(elementName.substring(0, elementName.length() - 3)).get(index).toString().split(":-");
            } else {
                Map<?, ?> map = (Map<?, ?>) property.get(pageName);
                element = map.get(elementName).toString().split(":-");
            }
            String locatorType = element[0];
            String locatorValue = element[1];
            locatorValue = "("+locatorValue+")["+xpathIndex+"]";
            switch (locatorType.trim().toLowerCase()) {
                case "id":
                    locator = By.id(locatorValue);
                    break;
                case "name":
                    locator = By.name(locatorValue);
                    break;
                case "cssselector":
                    locator = By.cssSelector(locatorValue);
                    break;
                case "linktext":
                    locator = By.linkText(locatorValue);
                    break;
                case "partiallinktext":
                    locator = By.partialLinkText(locatorValue);
                    break;
                case "tagname":
                    locator = By.tagName(locatorValue);
                    break;
                case "xpath":
                    locator = By.xpath(locatorValue);
                    break;
            }
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            Assert.fail("Failed to get the element locator from the map object"+e);
        }
        return locator;
    }

//    public static void main(String[] args) {
//        initializeObjectRepository();
//        System.out.println(getLocator("LoginPage.UserName"));
//        System.out.println(getLocator("Marlboro.PollHeadingAnswered[1]"));
//    }
}
