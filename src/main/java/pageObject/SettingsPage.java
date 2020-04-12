package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BrowserActions;

public class SettingsPage {

    private WebDriver driver;
    private By folderAndLabelsCss = By.linkText("Folders and labels");

    public SettingsPage(WebDriver driver){
        this.driver = driver;
    }

    public FoldersAndLabelsPage clickFoldersAndLabels(){
        BrowserActions.click(driver, folderAndLabelsCss);
        return new FoldersAndLabelsPage(driver);

    }
}
