package pageObjects.signIn.page;

import common.CommonFunctions;
import org.openqa.selenium.WebDriver;
import pageObjects.signIn.path.SignInViewPath;

public class SignInViewPage {
    CommonFunctions commonFunctions = new CommonFunctions();

    public boolean isSignInViewPageVisible(WebDriver webDriver) {
        commonFunctions.switchToDefaultContent(webDriver);
        boolean isVisible = false;
        try {
            isVisible = commonFunctions.findElementTestID(webDriver, SignInViewPath.REGISTRATION_TEST_ID).isDisplayed();
        }catch (Exception e){}
        commonFunctions.switchToIFrame(webDriver);

        return isVisible;
    }

    public void closeSignInView(WebDriver webDriver) {
        commonFunctions.switchToDefaultContent(webDriver);
        commonFunctions.findElementTestID(webDriver, SignInViewPath.CLOSE_BUTTON_TEST_ID).click();
        commonFunctions.switchToIFrame(webDriver);
    }
}
