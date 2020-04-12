package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BrowserActions;

public class LoginPage {

    private WebDriver driver;
    private By usernameId = By.id("username");
    private By passwordId = By.id("password");
    private By loginBtnId = By.id("login_btn");

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public LoginPage open(String url){
        BrowserActions.loadPage(driver, url);
        return this;
    }

    public LoginPage enterUsername(String username){
        BrowserActions.type(driver, usernameId, username);
        return this;
    }

    public LoginPage enterPassword(String password){
        BrowserActions.type(driver, passwordId, password);
        return this;
    }

    public MainPage clickLogin(){
        BrowserActions.click(driver, loginBtnId);
        return new MainPage(driver);
    }

    public MainPage login(String username, String password){
        this.enterUsername(username);
        this.enterPassword(username);
        return this.clickLogin();

    }

}
