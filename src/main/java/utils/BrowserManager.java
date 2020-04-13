package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.io.IOException;

public class BrowserManager {
    private static boolean doCloseExistingBrowsers = false;
    private static ThreadLocal<WebDriver> driverBrowserThread = new ThreadLocal();
    private static ThreadLocal<EventFiringWebDriver> driverBrowserEventshread = new ThreadLocal();
    private static WebDriverEventListener handleDriverEvents = null;

    private BrowserManager(){}

    private BrowserManager(BrowserManager.SupportedBrowsers browserName, DesiredCapabilities... capabilities) {
        if(doCloseExistingBrowsers) {
            try {
                this.killBrowserProcess(browserName);
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        switch (browserName) {
            case IE:
                driverBrowserThread.set(capabilities.length > 0 ? new InternetExplorerDriver(new InternetExplorerOptions(capabilities[0])) : new InternetExplorerDriver(getDefaultIEOptions()));
                break;
            case FIREFOX:
                driverBrowserThread.set(capabilities.length > 0 ? new FirefoxDriver(new FirefoxOptions(capabilities[0])) : new FirefoxDriver(getDefaultFirefoxOptions()));
                break;
            case CHROME:
                WebDriverManager.chromedriver().version("81.0.4044.69").setup();
                driverBrowserThread.set(capabilities.length > 0 ? new ChromeDriver(new ChromeOptions().merge((capabilities[0]))) : new ChromeDriver(getDefaultChromeOptions()));
                break;
            default:
                throw new IllegalArgumentException("Un-supported browser: " + browserName.name());
        }
        //TODO: reset implicit wait and other timeouts in case it's needed
    }

    public static WebDriver getWebDriver() {
        if (driverBrowserThread.get() == null) {
            throw new IllegalArgumentException("WebDriver instance has not been created yet.");
        }
        return driverBrowserThread.get(); //driverBrowserEventshread == null ? driverBrowserThread.get() : driverBrowserEventshread.get().getWrappedDriver();
    }

    public static void setWebDriver(WebDriver webDriver) {
        if (webDriver == null) {
            throw new IllegalArgumentException("Webdriver object cannot be null.");
        }
        driverBrowserThread.set(webDriver);
    }

    public static void createLocalBrowserInstance(String browserName, DesiredCapabilities... capabilities){
        new BrowserManager(BrowserManager.SupportedBrowsers.valueOf(browserName.toUpperCase()), capabilities);
    }

    public static void registerEventHandler(WebDriverEventListener eventListener) {
        driverBrowserEventshread.set(new EventFiringWebDriver(getWebDriver()));
        handleDriverEvents = eventListener;
        driverBrowserEventshread.get().register(eventListener);
    }

    public static void unregisterEventHandler() {
        if(driverBrowserEventshread.get() != null && handleDriverEvents != null) {
            driverBrowserEventshread.get().unregister(handleDriverEvents);
            driverBrowserEventshread.get().quit();
            driverBrowserEventshread.set(null);
        }
    }

    public static void setCloseExistingBrowsers(boolean closeOpenRelevantBrowsers) {
        doCloseExistingBrowsers = closeOpenRelevantBrowsers;
    }

    public enum SupportedBrowsers {
        IE {
            public String toString() { return "internet explorer";}
        },
        FIREFOX {
            public String toString() { return "firefox";}
        },
        CHROME {
            public String toString() { return "chrome";}
        }
        // TODO: could be further expanded with other browsers
    }

    //TODO: add some default options
    private InternetExplorerOptions getDefaultIEOptions() {
        return new InternetExplorerOptions();
    }

    private FirefoxOptions getDefaultFirefoxOptions() {
        FirefoxOptions customOption = new FirefoxOptions();
        customOption.addPreference("dom.webnotifications.enabled", false);
        return customOption;
    }

    private ChromeOptions getDefaultChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("enable-automation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-gpu");
        return options;
    }

    private void killBrowserProcess(BrowserManager.SupportedBrowsers browserName) throws IOException {
        switch (browserName) {
            case IE:
                Runtime.getRuntime().exec("taskkill /F /IM iexplorer.exe /T");
                Runtime.getRuntime().exec("taskkill /F /IM ieDriverServer.exe /T");
                break;
            case FIREFOX:
                Runtime.getRuntime().exec("taskkill /F /IM firefox.exe /T");
                break;
            case CHROME:
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe /T");
                Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");
        }
    }
}