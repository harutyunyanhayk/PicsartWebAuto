package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AcceptAllCookies {
    public static void AcceptAllCookies(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(Constants.ACCEPTALLCOOKIES)));
            acceptCookiesButton.click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}