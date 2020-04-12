package pageObject;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.BrowserActions;
import utils.Colors;

import java.util.List;



public class FoldersAndLabelsPage {

    private WebDriver driver;
    private String pageUrl = "/settings/labels#folderlist";
    private By addFolderBtnSelector = By.cssSelector("button[data-test-id='folders/labels:addFolder']");
    private By addLabelBtnSelector = By.cssSelector("button[data-test-id='folders/labels:addLabel']");
    private By nameSelector = By.cssSelector("input[id='accountName']");
    private By submitBtnSelector = By.cssSelector("button[type='submit']");
    private By cancelBtnSelector = By.cssSelector("button[type='reset']");
    private By folderLabelTableSelector = By.cssSelector("table[class*=pm-simple-table]");
    private By folderRowSelector = By.cssSelector("tr[data-test-id='folders/labels:item-type:folder']");
    private By labelRowSelector = By.cssSelector("tr[data-test-id='folders/labels:item-type:label']");
    private By folderLabelRowSelector = By.cssSelector("tr[data-test-id*='folders/labels:item-type:']");
    private By nameColSelector = By.cssSelector("span[data-test-id='folders/labels:item-name']");
    private By typeColSelector = By.cssSelector("svg[style]");
    private By editBtnColSelector = By.cssSelector("button[data-test-id='folders/labels:item-edit']");
    private By dropDownRowSelector = By.cssSelector("button[data-test-id='dropdown:open']");
    private By deleteBtnSelector = By.cssSelector("div[aria-hidden='false']");
    private By confirmDelBtnSelector= By.xpath("//button[text()='Confirm']");
    private By alertSelector = By.cssSelector("div[role='alert']");



    public FoldersAndLabelsPage(WebDriver driver){this.driver = driver;}


    public FoldersAndLabelsPage clickNewFolder() {
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        BrowserActions.click(driver, addFolderBtnSelector);
        return this;
    }

    public FoldersAndLabelsPage clickNewLabel() {
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        BrowserActions.click(driver, addLabelBtnSelector);
        return this;
    }


    public FoldersAndLabelsPage enterName(String folderName) {
        BrowserActions.type(driver, nameSelector, folderName);
        return this;

    }

    public FoldersAndLabelsPage selectColor(Colors color) {
        BrowserActions.click(driver, By.cssSelector("li[style='"+color+"']"));
        return this;
    }

    public FoldersAndLabelsPage clickSubmit() {
        BrowserActions.click(driver, submitBtnSelector);
        BrowserActions.loadPage(driver, driver.getCurrentUrl());

        return this;
    }

    public FoldersAndLabelsPage clickCancel() {
        BrowserActions.click(driver, cancelBtnSelector);
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        return this;
    }

    public FoldersAndLabelsPage verifyNotification(String message){
        Assert.assertTrue(getAlertText().equals(message));
        return this;
    }

    public FoldersAndLabelsPage addFolder(String folderName, Colors folderColor){
        driver.navigate().refresh();
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        clickNewFolder();
        enterName(folderName);
        selectColor(folderColor);
        clickSubmit();
        return this;
    }

    public FoldersAndLabelsPage addLabel(String labelName, Colors labelColor){
        driver.navigate().refresh();
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        clickNewLabel();
        enterName(labelName);
        selectColor(labelColor);
        clickSubmit();
        return this;
    }


    public FoldersAndLabelsPage editFolder(String oldFolderName, String newFolderName, Colors newColor) {
        driver.navigate().refresh();
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        List<WebElement> tableRows = BrowserActions.findElements(driver,folderRowSelector);
        for (WebElement row : tableRows) {
            String actFolderName = row.findElement(nameColSelector).getText();
            if (actFolderName.equals(oldFolderName)) {
                row.findElement(editBtnColSelector).click();
                BrowserActions.type(driver, nameSelector, newFolderName);
                BrowserActions.click(driver, By.cssSelector("li[style='"+newColor+"']"));
                BrowserActions.click(driver, submitBtnSelector);
                BrowserActions.loadPage(driver, driver.getCurrentUrl());
                break;
            }
        }
        return this;
    }

    public FoldersAndLabelsPage deleteFolder(String folderName) {
        driver.navigate().refresh();
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        List<WebElement> tableRows = BrowserActions.findElements(driver, folderRowSelector);
        for(WebElement row : tableRows){
            String actFolderName = row.findElement(nameColSelector).getText();
            if(actFolderName.equals(folderName)) {
                row.findElement(dropDownRowSelector).click();
                BrowserActions.click(driver, deleteBtnSelector);
                BrowserActions.click(driver, confirmDelBtnSelector);
                break;
            }
        }
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        return this;
    }

    public FoldersAndLabelsPage editLabel(String oldLabelName, String newLabelName, Colors newColor) {
        driver.navigate().refresh();
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        List<WebElement> tableRows = BrowserActions.findElements(driver, labelRowSelector);
        for (WebElement row : tableRows) {
            String actLabelName = row.findElement(nameColSelector).getText();
            if (actLabelName.equals(oldLabelName)) {
                row.findElement(editBtnColSelector).click();
                BrowserActions.type(driver, nameSelector, newLabelName);
                BrowserActions.click(driver, By.cssSelector("li[style='"+newColor+"']"));
                BrowserActions.click(driver, submitBtnSelector);
                BrowserActions.loadPage(driver, driver.getCurrentUrl());
                break;
            }
        }

        return this;
    }

    public FoldersAndLabelsPage deleteLebel(String labelName) {
        driver.navigate().refresh();
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        List<WebElement> tableRows = BrowserActions.findElements(driver, labelRowSelector);
        for(WebElement row : tableRows){
            String actFolderName = row.findElement(nameColSelector).getText();
            if(actFolderName.equals(labelName)) {
                row.findElement(dropDownRowSelector).click();
                BrowserActions.click(driver, deleteBtnSelector);
                BrowserActions.click(driver, confirmDelBtnSelector);
                break;
            }
        }
        return this;
    }

    public FoldersAndLabelsPage deleteAllFoldersLabels() {
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        if(BrowserActions.isElementPresent(driver, folderLabelTableSelector)) {
            WebElement table = driver.findElement(folderLabelTableSelector);
            List<WebElement> tableRows = table.findElements(folderLabelRowSelector);

            while (tableRows.size() > 0) {
                if (tableRows.size() == 1) {
                    tableRows.get(0).findElement(dropDownRowSelector).click();
                    BrowserActions.click(driver, deleteBtnSelector);
                    BrowserActions.click(driver, confirmDelBtnSelector);
                    BrowserActions.loadPage(driver, driver.getCurrentUrl());
                    break;
                } else {
                    tableRows.get(0).findElement(dropDownRowSelector).click();
                    BrowserActions.click(driver, deleteBtnSelector);
                    BrowserActions.click(driver, confirmDelBtnSelector);
                    BrowserActions.loadPage(driver, driver.getCurrentUrl());
                    tableRows = table.findElements(folderLabelRowSelector);
                }
            }
        }
        return this;
    }

    public FoldersAndLabelsPage verifyFolderLabelDeleted(String folderName) {
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        if(BrowserActions.isElementPresent(driver, folderLabelTableSelector)){
            if (driver.findElement(folderLabelTableSelector).getText().contains(folderName)){
                Assert.fail("Folder was not deleted!");
            }
        }
        return this;
    }

    public FoldersAndLabelsPage verifyFolder(String expFolderName, Colors expFolderColor){
        verifyRow(expFolderName, expFolderColor);
        return this;
    }

    public FoldersAndLabelsPage verifyLabel(String expLabelName, Colors expLabelColor){
        verifyRow(expLabelName, expLabelColor);
        return this;
    }

    private void verifyRow(String expName, Colors expColor) {
        BrowserActions.loadPage(driver, driver.getCurrentUrl());
        Boolean elementFound = false;
        if(BrowserActions.isElementPresent(driver, folderLabelTableSelector)){
            List<WebElement> tableRows = driver.findElements(folderLabelRowSelector);
            for(WebElement row : tableRows){
                String actName = row.findElement(nameColSelector).getText();
                if(actName.equals(expName)){
                    elementFound = true;
                    String actColor = row.findElement(typeColSelector).getAttribute("style");
                    Assert.assertTrue(actName.equals(expName));
                    Assert.assertTrue(actColor.equals(expColor.toString()));
                }
            }
            if(!elementFound) {
                Assert.fail(String.format("%s was not found", expName));
            }

        } else {
            Assert.fail(String.format("Table is empty, %s was not added", expName));
        }
    }

    private String getAlertText(){
        return BrowserActions.findElement(driver, alertSelector).getText();
    }



}
