package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BrowserActions {

    public static void loadPage(WebDriver driver, String url){
        driver.get(url);
        waitForPageLoaded(driver);
    }

    public static WebElement findElement(WebDriver driver, By by){
        new WebDriverWait(driver, 15).until(ExpectedConditions
                .and(ExpectedConditions.elementToBeClickable(by), ExpectedConditions.visibilityOfElementLocated(by)));
        return driver.findElement(by);
    }

    public static List<WebElement> findElements(WebDriver driver, By by){
        new WebDriverWait(driver, 15).until(ExpectedConditions
                .and(ExpectedConditions.elementToBeClickable(by), ExpectedConditions.visibilityOfElementLocated(by)));
        return driver.findElements(by);
    }

    public static Boolean isVisible(WebDriver driver, By by){
        new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(by));
        return driver.findElement(by).isDisplayed();
    }


    public static void type(WebDriver driver, By by, String text){
        findElement(driver, by).clear();
        findElement(driver, by).sendKeys(text);
    }

    public static Boolean isElementPresent(WebDriver driver, By by){
        return (driver.findElements(by).size() == 0) ? false : true;
    }

    public static void click(WebDriver driver, By by){
        findElement(driver,by).click();
    }

    private static void waitForPageLoaded(WebDriver driver) {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
            //TODO
        }
    }



}
