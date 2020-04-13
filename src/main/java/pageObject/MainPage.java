package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BrowserActions;

public class MainPage {

    private WebDriver driver;
    private By settingsScc = By.cssSelector("a#tour-settings");
    private By popUpCss = By.cssSelector(".onboardingModal-container");
    private By popUpCloseBtnCss = By.cssSelector(".pm-modalClose-icon");

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    public MainPage closePopIfOpen(){

        if(BrowserActions.isVisible(driver, popUpCss)){
            BrowserActions.click(driver, popUpCloseBtnCss);
        }
        return this;
    }

    public SettingsPage clickSettings(){
        BrowserActions.click(driver, settingsScc);
        return new SettingsPage(driver);
    }
}
