package common;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.searchView.path.SearchViewPath;

import java.time.Duration;
import java.util.List;

public class CommonFunctions {
    private WebDriverWait wait;

    public WebElement findElementByClassName(WebDriver webDriver, String path) {
        return webDriver.findElement(By.className(path));
    }

    public WebElement findElementByID(WebDriver webDriver, String path) {
        return webDriver.findElement(By.id(path));
    }

    public WebElement findElementByCSS(WebDriver webDriver, String path) {
        return webDriver.findElement(By.cssSelector(path));
    }

    public List<WebElement> findElementsByCSS(WebDriver webDriver, String path) {
        return webDriver.findElements(By.cssSelector(path));
    }

    public WebElement findElementTestID(WebDriver webDriver, String path) {
        return webDriver.findElement(By.cssSelector(path));
    }

    public List<WebElement> findElementsTestID(WebDriver webDriver, String path) {
        return webDriver.findElements(By.cssSelector(path));
    }

    public WebElement findElementByXPath(WebDriver webDriver, String path) {
        return webDriver.findElement(By.xpath(path));
    }

    public WebElement findElementByName(WebDriver webDriver, String path) {
        return webDriver.findElement(By.name(path));
    }

    public void waitUntilVisibleByXPath(WebDriver webDriver, String path) {
        new WebDriverWait(webDriver, Duration.ofMillis(Constants.WAIT_TIME_MILLISECOND))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(String.format("//*[text()='%s']", path))));
    }

    public void waitUntilVisibleByTestID(WebDriver webDriver, String path) {
        new WebDriverWait(webDriver, Duration.ofMillis(Constants.WAIT_TIME_MILLISECOND))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(path)));
    }

    public void waitUntilVisibleByID(WebDriver webDriver, String path) {
        new WebDriverWait(webDriver, Duration.ofMillis(Constants.WAIT_TIME_MILLISECOND))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.id(path)));
    }

    public boolean isPlusTagEsists(WebElement webElement) {
        String value = webElement.getCssValue(SearchViewPath.PLUS_ASSET_TEST_ID);
        if (value.isEmpty() || value.isBlank()) {
            return false;
        }
        return true;
    }

    public void hoverOverWebElement(WebDriver webDriver, WebElement element) {
        //Instantiating Actions class
        Actions actions = new Actions(webDriver);

        //Hovering on main menu
        actions.moveToElement(element).perform();
    }

    public void switchToIFrame(WebDriver webDriver) {
        waitUntilPageLoads(webDriver);
        // Switch to the iframe by locating it as a WebElement
        WebElement iframeElement = webDriver.findElement(By.cssSelector("iframe"));
        webDriver.switchTo().frame(iframeElement);
    }

    public void switchToDefaultContent(WebDriver webDriver) {
        waitUntilPageLoads(webDriver);
        // Switch back to the main content
        webDriver.switchTo().defaultContent();
    }

    public void impliciteWait(WebDriver webDriver, long timeInSeconds) {
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
    }

    public void waitUntilPageLoads(WebDriver webDriver) {
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
}
