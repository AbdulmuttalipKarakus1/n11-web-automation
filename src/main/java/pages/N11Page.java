package pages;

import base.BasePage;
import org.openqa.selenium.*;
import java.util.List;
import java.util.stream.Collectors;

public class N11Page extends BasePage {

    private static final By ALL_STORES_NAME = By.xpath("//*[@class='tabPanel allSellers']//a");
    private static final By SEARCH_AREA = By.id("searchData");
    private static final By ADD_BASKET_BUTTON_ON_MODAL = By.id("js-addBasketSku");
    private static final By BASKET_HOLDER = By.cssSelector("[class='myBasketHolder']");
    private static final By SORT_OPTIONS = By.cssSelector("[class='custom-select']");
    private static final By DELIVERY_OPTIONS = By.cssSelector("[data-tag-name='Delivery Option']");
    private static final By DELIVERY_OPTIONS_OPEN_STATUS = By.cssSelector("[class='filter cargoFilter acc open clickedFilter']");
    private static final By RESULT_LIST = By.xpath("//section[contains(@class,'resultListGroup')]");
    private static final By HIDDEN_CARGO_BADGE_FIELD = By.cssSelector("[class='cargoBadgeField  hidden  ']");
    private static final By ALL_AUTOCOMPLETE_SEARCH_RESULT = By.xpath("//*[@class='autocomplete-search-item']//a");
    private static final By ALL_REVIEW_NUMBERS = By.xpath("//*[@class='ratingCont']//*[@class='ratingText']");
    private static final By PRODUCT_DESC_ON_BASKET = By.cssSelector("[class='prodDescription']");
    private static final By SELECTION_WARNING = By.cssSelector("[class='selection-warning']");
    private static final By SKU_DEFINITION = By.cssSelector("[class='sku-definitions']");
    private static final By LOADING_SHOW = By.cssSelector("[class='iLoading show']");
    private static final By BASKET_BUTTON = By.cssSelector("[class='btnBasket']");

    public void openStorePage() {
        driver.get("https://www.n11.com/magazalar");
        acceptCookies();
    }

    public void filterStoresByLetter(String letter) {
        var xpath = By.cssSelector("[data-has-seller='" + letter + "']");
        clickElement(xpath);
    }

    public String selectRandomStore() {
        waitForPageLoad();
        var stores = findElements(ALL_STORES_NAME);
        System.out.println("Stores: " + stores.size());

        var randomStoreName = stores.get((int) (Math.random() * stores.size())).getText();
        var xpath = By.xpath("//*[text()='" + randomStoreName + "']");

        executeScrollScript(xpath);
        clickElement(xpath);

        return randomStoreName;
    }

    public void searchProduct(String keyword) {
        fillInputField(SEARCH_AREA, keyword);
    }

    public void pressEnter() {
        clickEnterButton(SEARCH_AREA);
    }

    public String getSearchValue() {
        return getAttributeOfElement(SEARCH_AREA, "value");
    }

    public boolean isResultListExist() {
        return isElementExist(RESULT_LIST);
    }

    public boolean isCargoBadgeFieldExistAsHidden() {
        return isElementExist(HIDDEN_CARGO_BADGE_FIELD, false);
    }

    public List<String> getAutocompleteSearchResult() {
        return findElements(ALL_AUTOCOMPLETE_SEARCH_RESULT).stream()
                .map(e -> e.getAttribute("alt"))
                .collect(Collectors.toList());
    }

    public List<Integer> getReviewNumbersForProducts() {
        return findElements(ALL_REVIEW_NUMBERS).stream()
                .map(WebElement::getText)
                .map(numbers ->
                        Integer.parseInt(
                                numbers.replace("(", "")
                                        .replace(")", "")
                                        .replace(",", "")))
                .collect(Collectors.toList());
    }

    public void addFirstAndLastProductToCart() throws InterruptedException {
        List<WebElement> products = findElements(BASKET_BUTTON);

        var firstItem = By.xpath("//*[@data-position='1']//*[@class='btnBasket']");
        clickElement(firstItem);
        selectSkuDefinition();
        Thread.sleep(1000L);

        var lastItem = By.xpath("//*[@data-position='" + products.size() + "']//*[@class='btnBasket']");
        executeScrollScript(lastItem);
        waitUntilElementDisappears(LOADING_SHOW);
        clickElement(lastItem);
        selectSkuDefinition();
    }

    public List<String> getProductDescOnBasket() {
        return findElements(PRODUCT_DESC_ON_BASKET).stream()
                .map(WebElement::getText).
                collect(Collectors.toList());
    }

    private void selectSkuDefinition() {
        if (isElementExist(SELECTION_WARNING)) {

            var definitionCount = findElements(SKU_DEFINITION).size();

            if (definitionCount > 0) {
                for (int i = 1; i <= definitionCount; i++) {
                    var xpath = By.xpath("(//*[@class='sku-definitions'])[" + i + "]//label[1]");
                    clickElement(xpath);
                }
            }
        }

        clickAddBasketButtonOnModal();
    }

    public void clickAddBasketButtonOnModal() {
        hoverElement(ADD_BASKET_BUTTON_ON_MODAL);
        clickElement(ADD_BASKET_BUTTON_ON_MODAL);
    }

    public void clickCartButton() {
        clickElement(BASKET_HOLDER);
    }

    public void selectBrandByOrder(String brandOrder) {
        var brand = By.xpath("//*[@data-tag-name='Marka']//*[contains(@class,'filterItem customCheckWrap ')][" + brandOrder + "]//label");
        clickElement(brand);
    }

    public void sortProductBySelectedOption(String sortBy) {
        var xpath = By.xpath(" //*[@class='all-items'] //*[text()=' " + sortBy + "']");
        clickElement(SORT_OPTIONS);
        clickElement(xpath);
    }

    public void filterDeliveryOptions(String option) {
        executeScrollScript(DELIVERY_OPTIONS);

        if (!isElementExist(DELIVERY_OPTIONS_OPEN_STATUS))
            clickElement(DELIVERY_OPTIONS);

        var checkbox = waitUntilClickableAndGetElement(By.xpath(" //*[text()='" + option + "']"));
        if (!checkbox.isSelected()) checkbox.click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
