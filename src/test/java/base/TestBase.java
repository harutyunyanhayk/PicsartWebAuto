package base;

import common.enums.Drivers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import pageObjects.base.BasePO;

public class TestBase {
    protected static WebDriver driver;

    @BeforeEach
    public void setUpEach(final TestInfo testInfo) {
        final String driverName = testInfo.getDisplayName().split(" ")[1].replace(",", "");
        final int resW = Integer.parseInt(testInfo.getDisplayName().split(",")[1].trim());
        final int resH = Integer.parseInt(testInfo.getDisplayName().split(",")[2].trim());

        BasePO basePO = new BasePO(Drivers.valueOf(driverName));
        driver = basePO.getDriver();
        driver.manage().window().setSize(new Dimension(resW, resH));
    }

    @AfterEach
    public void tearDownEach() {
        driver.quit();
    }
}
