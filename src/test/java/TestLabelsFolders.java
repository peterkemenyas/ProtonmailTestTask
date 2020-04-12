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
    private FoldersAndLabelsPage foldersAndLabelsPage;
    private WebDriver driver;

    @BeforeClass
    public void preconditionFoldersLabels(){
        BrowserManager.createLocalBrowserInstance("firefox");
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
        foldersAndLabelsPage.addFolder("Salmon Folder", Colors.SALMON)
            .verifyNotification("Salmon Folder created")
            .verifyFolder("Salmon Folder", Colors.SALMON);
        }

    @Test(priority = 2)
    public void editFolder(){
        foldersAndLabelsPage.addFolder("Lavander Folder", Colors.LAVANDER)
            .editFolder("Lavander Folder","Lavander Main Emails", Colors.MINT)
            .verifyNotification("Lavander Main Emails updated")
            .verifyFolder("Lavander Main Emails", Colors.MINT);
    }

    @Test(priority = 3)
    public void deleteFolder(){
        foldersAndLabelsPage.addFolder("Apricot Folder", Colors.APRICOT)
            .deleteFolder("Apricot Folder")
            .verifyNotification("Apricot Folder removed")
            .verifyFolderLabelDeleted("Apricot Folder");
    }

    @Test(priority = 4)
     public void addLabel(){
        foldersAndLabelsPage.addLabel("Sky Label", Colors.SKY)
            .verifyNotification("Sky Label created")
            .verifyLabel("Sky Label", Colors.SKY);

    }

    @Test(priority = 5)
    public void editLabel(){
        foldersAndLabelsPage.addLabel("Frost Label", Colors.FROST)
            .editLabel("Frost Label", "Ice Label", Colors.LILAC)
            .verifyNotification("Ice Label updated")
            .verifyLabel("Ice Label", Colors.LILAC);

    }

    @Test(priority = 6)
    public void deleteLabel(){
        foldersAndLabelsPage.addLabel("Tea Label", Colors.TEA)
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
                .addFolder("4th Not Allowed Folder", Colors.ORCHID)
                .verifyNotification("Folder limit reached. Please upgrade to a paid plan to add more folders")
                .clickCancel();
    }

    @Test(priority = 8)
    @AfterClass
    public void tearDown(){
        driver.close();
    }



}
