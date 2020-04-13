import config.TestDataProperties;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObject.FoldersAndLabelsPage;
import pageObject.LoginPage;
import pageObject.MainPage;
import pageObject.SettingsPage;
import utils.BrowserManager;
import utils.Colors;

@ContextConfiguration(locations = {"classpath:Beans.xml"})
public class TestLabelsFolders extends AbstractTestNGSpringContextTests {

    @Autowired
    private TestDataProperties properties;
    @Autowired
    private String browser;

    private FoldersAndLabelsPage foldersAndLabelsPage;
    private WebDriver driver;

    @BeforeClass
    public void preconditionFoldersLabels(){
        BrowserManager.createLocalBrowserInstance(browser);
        driver = BrowserManager.getWebDriver();

        LoginPage loginPage = new LoginPage(driver);
        MainPage mainPage = loginPage.open(properties.getUrl())
                .enterUsername(properties.getProtonuser())
                .enterPassword(properties.getProtonpassword())
                .clickLogin();

        SettingsPage settingsPage = mainPage.closePopIfOpen()
                .clickSettings();

        foldersAndLabelsPage = settingsPage.clickFoldersAndLabels();
        foldersAndLabelsPage.deleteAllFoldersLabels();
    }

    @Test(priority = 1)
    public void addNewFolder(){
        foldersAndLabelsPage.refreshFoldersAndLabelsPage()
                .addFolder("Salmon Folder", Colors.SALMON)
                .verifyNotification("Salmon Folder created")
                .verifyFolder("Salmon Folder", Colors.SALMON);
        }

    @Test(priority = 2)
    public void editFolder(){
        foldersAndLabelsPage.refreshFoldersAndLabelsPage()
                .addFolder("Lavander Folder", Colors.LAVANDER)
                .refreshFoldersAndLabelsPage()
                .editFolder("Lavander Folder","Lavander Main Emails", Colors.MINT)
                .verifyNotification("Lavander Main Emails updated")
                .verifyFolder("Lavander Main Emails", Colors.MINT);
    }

    @Test(priority = 3)
    public void deleteFolder(){
        foldersAndLabelsPage.refreshFoldersAndLabelsPage()
                .addFolder("Apricot Folder", Colors.APRICOT)
                .refreshFoldersAndLabelsPage()
                .deleteFolder("Apricot Folder")
                .verifyNotification("Apricot Folder removed")
                .verifyFolderLabelDeleted("Apricot Folder");
    }

    @Test(priority = 4)
     public void addLabel(){
        foldersAndLabelsPage.refreshFoldersAndLabelsPage()
                .addLabel("Sky Label", Colors.SKY)
                .verifyNotification("Sky Label created")
                .verifyLabel("Sky Label", Colors.SKY);

    }

    @Test(priority = 5)
    public void editLabel(){
        foldersAndLabelsPage.refreshFoldersAndLabelsPage()
                .addLabel("Frost Label", Colors.FROST)
                .refreshFoldersAndLabelsPage()
                .editLabel("Frost Label", "Ice Label", Colors.LILAC)
                .verifyNotification("Ice Label updated")
                .verifyLabel("Ice Label", Colors.LILAC);

    }

    @Test(priority = 6)
    public void deleteLabel(){
        foldersAndLabelsPage.refreshFoldersAndLabelsPage()
                .addLabel("Tea Label", Colors.TEA)
                .refreshFoldersAndLabelsPage()
                .deleteLebel("Tea Label")
                .verifyNotification("Tea Label removed")
                .verifyFolderLabelDeleted("Tea Label");

    }

    @Test(priority = 7)
    public void folderLimitReached(){
        foldersAndLabelsPage.deleteAllFoldersLabels()
                .addFolder("1st Folder", Colors.MINT)
                .addFolder("2nd Folder", Colors.LILAC)
                .addFolder("3rd Folder", Colors.CORAL)
                .refreshFoldersAndLabelsPage()
                .addFolder("4th Not Allowed Folder", Colors.ORCHID)
                .verifyNotification("Folder limit reached. Please upgrade to a paid plan to add more folders")
                .clickCancel();
    }

    @Test(priority = 8)
    public void folderSameNameValidation(){
        foldersAndLabelsPage.deleteAllFoldersLabels()
                .addFolder("Same Name Folder", Colors.ORCHID)
                .refreshFoldersAndLabelsPage()
                .addFolder("Same Name Folder", Colors.ORCHID)
                .verifyNotification("A label or folder with this name already exists")
                .clickCancel();
    }

    @Test(priority = 9)
    public void labelSameNameValidation(){
        foldersAndLabelsPage.addLabel("Same Name Label", Colors.BREECE)
                .refreshFoldersAndLabelsPage()
                .addLabel("Same Name Label", Colors.BREECE)
                .verifyNotification("A label or folder with this name already exists")
                .clickCancel();
    }

    @Test(priority = 10)
    public void editFolderEmptyNameValidation(){
        foldersAndLabelsPage.addFolder("Folder For Name Validation", Colors.ORCHID)
                .refreshFoldersAndLabelsPage()
                .clickEdit("Folder For Name Validation")
                .enterName("")
                .selectColor(Colors.MINT)
                .verifyNameValidationMessage("This field is required")
                .clickCancel();

    }

    @Test(priority = 11)
    public void editLabelEmptyNameValidation(){
        foldersAndLabelsPage.addLabel("Label For Name Validation", Colors.DANDELION)
                .refreshFoldersAndLabelsPage()
                .clickEdit("Label For Name Validation")
                .enterName("")
                .selectColor(Colors.SKY)
                .verifyNameValidationMessage("This field is required")
                .clickCancel();
    }

    @Test(priority = 12)
    public void addFolderMaxNameValid(){
        foldersAndLabelsPage.refreshFoldersAndLabelsPage()
                .clickNewFolder()
                .enterName("MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAM")
                .clickSubmit()
                .verifyNotification("MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAM created");
    }

    @Test(priority = 13)
    public void addLabelMaxNameValid(){
        foldersAndLabelsPage.refreshFoldersAndLabelsPage()
                .clickNewLabel()
                .enterName("MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NA1")
                .clickSubmit()
                .verifyNotification("MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NAME MAX ALLOWED NA1 created");
    }

    @Test(priority = 14)
    public void addFolderMaxNameInvalid(){
        foldersAndLabelsPage.refreshFoldersAndLabelsPage()
                .clickNewFolder()
                .enterName("OVER MAX ALLOWED NAME OVER MAX ALLOWED NAME OVER MAX ALLOWED NAME OVER MAX ALLOWED NAME OVER MAX ALLO")
                .clickSubmit()
                .verifyNotification("Name too long")
                .clickCancel();
    }

    @Test(priority = 15)
    public void addLabelMaxNameInvalid(){
        foldersAndLabelsPage.refreshFoldersAndLabelsPage()
                .clickNewLabel()
                .enterName("OVER MAX ALLOWED NAME OVER MAX ALLOWED NAME OVER MAX ALLOWED NAME OVER MAX ALLOWED NAME OVER MAX ALLO")
                .clickSubmit()
                .verifyNotification("Name too long")
                .clickCancel();
    }

    @AfterClass
    public void tearDown(){
        driver.close();
    }



}
