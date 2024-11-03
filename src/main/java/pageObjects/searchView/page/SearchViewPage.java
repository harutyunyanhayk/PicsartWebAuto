package pageObjects.searchView.page;

import common.CommonFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.base.BasePO;
import pageObjects.searchView.path.SearchViewPath;

import java.util.List;

public class SearchViewPage {
    CommonFunctions commonFunctions = new CommonFunctions();

    public void closeFilter(WebDriver driver) {
        if (isFilterOpen(driver)) {
            commonFunctions.findElementByID(driver, SearchViewPath.FILTER_BUTTON_ID).click();
        }
    }

    public void openFilter(WebDriver driver) {
        if (!isFilterOpen(driver)) {
            commonFunctions.findElementByID(driver, SearchViewPath.FILTER_BUTTON_ID).click();
        }
    }

    public boolean isFilterOpen(WebDriver driver) {
        try {
            commonFunctions.waitUntilVisibleByID(driver, SearchViewPath.FILTER_BUTTON_ID);
            var filterButton = commonFunctions.findElementByID(driver, SearchViewPath.FILTER_BUTTON_ID);
            var attr = filterButton.getAttribute("data-automation");
            return attr.equals("open");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFiltersShown(WebDriver driver) {
        return commonFunctions.findElementByCSS(driver, SearchViewPath.FILTERS_TEST_ID).isDisplayed();
    }

    public WebElement findPersonalCheckBox(WebDriver driver) {
        return commonFunctions.findElementByID(driver, SearchViewPath.PERSONAL_CHECKBOX_ID);
    }

    public void checkPersonalCheckBox(WebDriver driver, boolean check) {
        WebElement checkBox = findPersonalCheckBox(driver);

        commonFunctions.impliciteWait(driver, 10);

        if (!(checkBox.isSelected() && check)) {
            checkBox.click();
        }
    }

    public List<WebElement> getListFromMainGrid(WebDriver driver) {
        commonFunctions.waitUntilVisibleByTestID(driver, SearchViewPath.CONTENT_GRID_TEST_ID);
        WebElement items = commonFunctions.findElementTestID(driver, SearchViewPath.CONTENT_GRID_TEST_ID);
        List<WebElement> listOfItems = items.findElements(By.tagName("div"));
        return listOfItems;
    }

    public boolean hasPlusAssets(WebDriver driver) {
        List<WebElement> listOfItems = getListFromMainGrid(driver);
        for (WebElement we : listOfItems) {
            if (commonFunctions.isPlusTagEsists(we)) {
                return true;
            }
        }

        return false;
    }

    public List<WebElement> getListItemsWithLikeButton(WebDriver driver) {
        return commonFunctions.findElementsTestID(driver, SearchViewPath.LIKE_TEST_ID);
    }

    public List<WebElement> getListItemsWithTryNowButton(WebDriver driver) {
        return commonFunctions.findElementsTestID(driver, SearchViewPath.TRY_NOW_TEST_ID);
    }

    public void clickTryNowButton(WebElement webElement) {
        webElement.findElement(By.cssSelector(SearchViewPath.TRY_NOW_TEST_ID)).click();
    }

    public void clearAllFilters(WebDriver driver) {
        commonFunctions.findElementTestID(driver, SearchViewPath.CLEAR_ALL_FILTER_TEST_ID).click();
    }

    public List<WebElement> getListItemsWithPlusAssets(WebDriver driver) {
        return commonFunctions.findElementsTestID(driver, SearchViewPath.PLUS_ASSET_TEST_ID);
    }

    public boolean isLikeButtonVisible(WebDriver driver, WebElement webElement) {
        return driver.findElement(By.id(SearchViewPath.LIKE_ID)).isDisplayed();
    }

    public void likeButtonClick(WebDriver driver) {
        driver.findElement(By.id(SearchViewPath.LIKE_ID)).click();
    }

    public boolean isSaveButtonVisible(WebDriver driver, WebElement webElement) {
//        return webElement.findElement(By.id(SearchViewPath.SAVE_ID)).isDisplayed();
        return driver.findElement(By.id(SearchViewPath.SAVE_ID)).isDisplayed();

//        return webElement.findElement(By.cssSelector(SearchViewPath.SAVE_TEST_ID)).isDisplayed();
    }


    public boolean isTryButtonVisible(WebDriver driver, WebElement webElement) {
//        return webElement.findElement(By.id(SearchViewPath.TRY_NOW_ID)).isDisplayed();
        return driver.findElement(By.id(SearchViewPath.TRY_NOW_ID)).isDisplayed();

//        return webElement.findElement(By.cssSelector(SearchViewPath.TRY_BUTTON_TEST_ID)).isDisplayed();

    }
}
