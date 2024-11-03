package pageObjects.editor.page;

import common.CommonFunctions;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import pageObjects.editor.path.EditorViewPath;

public class EditorViewPage {
    CommonFunctions commonFunctions = new CommonFunctions();

    public boolean verifyEditorViewVisible(WebDriver driver) {
        return commonFunctions.findElementTestID(driver, EditorViewPath.EDITOR_CANVAS_TEST_ID).isDisplayed();
    }

    public boolean verifyEditorViewVisibleWait(WebDriver driver) {
        try {
            commonFunctions.waitUntilVisibleByTestID(driver, EditorViewPath.EDITOR_CANVAS_TEST_ID);
        } catch (NotFoundException e) {
            return false;
        }
        return true;
    }
}
