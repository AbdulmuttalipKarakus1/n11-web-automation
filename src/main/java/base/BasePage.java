package base;

import io.qameta.allure.Step;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Driver;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected static final int DEFAULT_WAIT = 10;
    protected WebDriver driver = Driver.getDriver();
    protected WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT));
    protected JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();

    @Step("Find elements")
    protected List<WebElement> findElements(By by) {

        waitUntilElementAppears(by);
        return driver.findElements(by);
    }

    @Step("Click to element")
    protected void clickElement(By by) {

        waitUntilClickableAndGetElement(by).click();
    }

    @Step("Hover to element")
    protected void hoverElement(By by) {

        var element = waitUntilAppearsAndGetElement(by);
        new Actions(driver).moveToElement(element).perform();
    }

    @Step("Wait until clickable and get element")
    protected WebElement waitUntilClickableAndGetElement(By by) {

        return wait.ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    @Step("Fill input field element")
    protected void fillInputField(By by, String text) {

        WebElement element = waitUntilClickableAndGetElement(by);

        clearElementField(by);
        element.sendKeys(text);
    }

    @Step("Fill input field element")
    protected void clickEnterButton(By by) {

        WebElement element = waitUntilClickableAndGetElement(by);
        element.sendKeys(Keys.ENTER);
    }

    @Step("Clear element field")
    protected void clearElementField(By by) {

        WebElement element = waitUntilAppearsAndGetElement(by);

        element.click();
        element.clear();
    }

    @Step("Execute scroll script")
    protected void executeScrollScript(By by) {

        executor.executeScript("arguments[0].scrollIntoView(true);", waitUntilAppearsAndGetElement(by));
        executor.executeScript("window.scrollBy(0, -500);");
    }

    @Step("Wait until element appears and get element")
    protected WebElement waitUntilAppearsAndGetElement(By by) {

        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @Step("Wait until element appears")
    protected void waitUntilElementAppears(By by) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @Step("Wait until element disappears")
    protected void waitUntilElementDisappears(By by) {

        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    @Step("Get attribute of element")
    protected String getAttributeOfElement(By by, String attributeName) {

        return waitUntilAppearsAndGetElement(by).getDomAttribute(attributeName);
    }

    @Step("Is element exist")
    protected boolean isElementExist(By by, boolean... checkExistence) {

        if (checkExistence.length == 0 || checkExistence[0]) {

            try {

                waitUntilElementAppears(by);
                return findElements(by).size() == 1;
            } catch (TimeoutException e) {

                return false;
            }
        }

        try {

            waitUntilElementDisappears(by);
            return true;
        } catch (TimeoutException e) {

            return false;
        }
    }

    @Step("Accept Cookies")
    public static void acceptCookies() {

        var executor = (JavascriptExecutor) Driver.getDriver();

        var shadowElementExists = (Boolean) executor.executeScript(
                "return document.querySelector('efilli-layout-dynamic') !== null");

        var acceptCookies =
                "Array.from(document.querySelector('efilli-layout-dynamic')" +
                        ".shadowRoot.querySelectorAll('div'))" +
                        ".find(el => el.textContent.trim() === 'Tümünü Kabul Et').click();";

        if (shadowElementExists) executor.executeScript(acceptCookies);
    }

    @Step("Wait until page loading completed")
    public void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));

        wait.until(webDriver -> (Boolean) ((JavascriptExecutor) webDriver)
                .executeScript("return typeof jQuery != 'function' || jQuery.active == 0"));
    }
}
