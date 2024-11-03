import base.TestBase;
import common.AcceptAllCookies;
import common.CommonFunctions;
import common.Constants;
import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageObjects.editor.page.EditorViewPage;
import pageObjects.searchView.page.SearchViewPage;
import pageObjects.signIn.page.SignInViewPage;

import java.util.List;

public class SearchFilterTest extends TestBase {
    private final CommonFunctions commonFunctions = new CommonFunctions();

    @ParameterizedTest
    @CsvSource({
//            "FIREFOX_DRIVER, 1024, 768",
//            "EDGE_DRIVER, 1440, 900",
//            "CHROME_DRIVER, 1024, 768",
            "CHROME_DRIVER, 1366, 768",
    })
    public void searchFilterTest(String driverName, int width, int height) {
        driver.get(Constants.PAGE_URL);

        commonFunctions.waitUntilPageLoads(driver);

        AcceptAllCookies.AcceptAllCookies(driver);

        commonFunctions.switchToIFrame(driver);
        commonFunctions.impliciteWait(driver, 5);

        SearchViewPage searchViewPage = new SearchViewPage();
        searchViewPage.closeFilter(driver);

        Assert.isTrue(!searchViewPage.isFilterOpen(driver), "Filter is open, but should be closed.");

        searchViewPage.openFilter(driver);

        searchViewPage.checkPersonalCheckBox(driver, true);

        commonFunctions.impliciteWait(driver, 10);

        //Get all items and
        Assert.isTrue(!searchViewPage.hasPlusAssets(driver),
                "After Checking Personal checkbox there is/are items with Plus assets.");

        //Get element with Plus asset
        WebElement elementWithPlus = searchViewPage.getListFromMainGrid(driver).get(0);
        Actions action = new Actions(driver);
        action.moveToElement(elementWithPlus).perform();
        commonFunctions.impliciteWait(driver, 5);

        //Check if Like, save and try buttons available
        Assert.isTrue(searchViewPage.isLikeButtonVisible(driver, elementWithPlus),
                "Like button should be visible");
        Assert.isTrue(searchViewPage.isSaveButtonVisible(driver, elementWithPlus),
                "Save button should be visible");
        Assert.isTrue(searchViewPage.isTryButtonVisible(driver, elementWithPlus),
                "Try button should be visible");

        //Click on first item's Like button
        searchViewPage.likeButtonClick(driver);

        //Verify that sign-in popup appears
        SignInViewPage signInViewPage = new SignInViewPage();

        commonFunctions.impliciteWait(driver, 5);

        Assert.isTrue(signInViewPage.isSignInViewPageVisible(driver), "SignIn popup is not visible");

        signInViewPage.closeSignInView(driver);

        commonFunctions.impliciteWait(driver, 5);

        Assert.isTrue(!signInViewPage.isSignInViewPageVisible(driver), "SignIn popup is not visible");

        searchViewPage.clearAllFilters(driver);

        //Verify that "Try now" button is visible after hovering "PLUS" asset
        commonFunctions.impliciteWait(driver, 5);
        List<WebElement> plusAssets = searchViewPage.getListItemsWithPlusAssets(driver);
        if (plusAssets.size() > 0) {
            elementWithPlus = plusAssets.get(0);
            commonFunctions.hoverOverWebElement(driver, elementWithPlus);

            //Verify there is no Like button
            Assert.isTrue(!searchViewPage.isLikeButtonVisible(driver, elementWithPlus),
                    "Like button is visible, but shouldn't");
            //Verify there is no Save button
            Assert.isTrue(!searchViewPage.isSaveButtonVisible(driver, elementWithPlus),
                    "Like button is visible, but shouldn't");
        }

        //Click on first item's "Try now" button
        WebElement elementWithTry = searchViewPage.getListFromMainGrid(driver).get(0);
        commonFunctions.hoverOverWebElement(driver, elementWithTry);
        commonFunctions.impliciteWait(driver, 5);

        searchViewPage.clickTryNowButton(elementWithTry);

        try {
            commonFunctions.impliciteWait(driver, 5);
            signInViewPage.closeSignInView(driver);
        } catch (Exception e) {
        }

        commonFunctions.switchToDefaultContent(driver);

        EditorViewPage editorViewPage = new EditorViewPage();
        Assert.isTrue(editorViewPage.verifyEditorViewVisible(driver), "Editor view page is not visible.");
    }
}
