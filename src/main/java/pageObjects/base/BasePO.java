package pageObjects.base;

import common.enums.Drivers;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.ArrayList;
import java.util.List;

public final class BasePO {
    private BasePO basePO = null;
    private WebDriver driver;
    private List<WebDriver> allDrivers = new ArrayList<>();

//    private BasePO(WebDriver webDriver) {
//        driver = webDriver;
//    }

    public BasePO(Drivers driverIn) {
        driver = getDriver(driverIn);
    }

    public WebDriver getDriver() {
        return driver;
    }

    private WebDriver getDriver(Drivers driverIn) {
        WebDriver driver = null;
        switch (driverIn) {
            case CHROME_DRIVER -> {
                // Set up ChromeDriver using WebDriverManager
                WebDriverManager.chromedriver().setup();

                // Configure ChromeOptions
                ChromeOptions options = new ChromeOptions();

                // Disable "Chrome is being controlled by automated test software" info bar
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                // Disable save password prompt
                options.setExperimentalOption("prefs", new java.util.HashMap<String, Object>() {{
                    put("credentials_enable_service", false);
                    put("profile.password_manager_enabled", false);
                }});

                driver = new ChromeDriver(options);
            }
            case SAFARI_DRIVER -> {
//                    throw new ExecutionControl.NotImplementedException("Need to implement");
                break;
            }
            case FIREFOX_DRIVER -> {
                WebDriverManager.firefoxdriver().setup();
                // Configure FirefoxOptions
                FirefoxOptions options = new FirefoxOptions();

                // Disable save password prompt
                options.addPreference("signon.rememberSignons", false);

                // Disable notifications
                options.addPreference("dom.webnotifications.enabled", false);

                // Disable "Firefox is being controlled by automated test software" notification
                // options.setCapability("marionette", true);
                driver = new FirefoxDriver(options);
            }
            case EDGE_DRIVER -> {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
        }
        return driver;
    }


    public List<WebDriver> getAllDrivers() {
        return allDrivers;
    }

    public void reSetDriver() {
        basePO = null;
        driver = null;
    }
}
