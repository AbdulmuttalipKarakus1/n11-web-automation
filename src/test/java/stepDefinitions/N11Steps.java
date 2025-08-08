package stepDefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.N11Page;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class N11Steps {
    N11Page n11 = new N11Page();
    private String randomStoreName;

    @Given("User navigates to the N11 store page")
    public void navigateToStorePage() {
        n11.openStorePage();
    }

    @When("User filters stores starting with letter {string}")
    public void filterStores(String letter) {
        n11.filterStoresByLetter(letter);
    }

    @When("User selects a random store")
    public void selectRandomStore() {
        randomStoreName = n11.selectRandomStore();
    }

    @Then("User verifies the store page opens correctly")
    public void verifyStorePageOpened() {
        Assert.assertTrue(n11.isResultListExist());
        Assert.assertTrue(n11.getCurrentUrl().contains(randomStoreName.toLowerCase()));
        Assert.assertEquals(randomStoreName.toLowerCase(), n11.getSearchValue());
    }

    @When("User searches for {string}")
    public void searchKeyword(String keyword) {
        n11.searchProduct(keyword);
    }

    @When("Press enter button")
    public void pressEnterButton() {
        n11.pressEnter();
    }

    @Then("User verifies the autocomplete searching result is correct for {string}")
    public void verifyAutocompleteSearchResult(String keyword) {
        List<String> autocompletes = n11.getAutocompleteSearchResult();
        Assert.assertTrue(autocompletes.stream().allMatch(result -> result.contains(keyword.toLowerCase())));
    }

    @When("Adds the first and last products on the first page to cart")
    public void addToCart() throws InterruptedException {
        n11.addFirstAndLastProductToCart();
    }

    @Then("User verifies the selected {string} products added successfully to cart")
    public void verifyAddedProductOnCart(String keyword) {
        n11.clickCartButton();
        List<String> productDescOnBasket = n11.getProductDescOnBasket();
        Assert.assertTrue(productDescOnBasket.stream().allMatch(result -> result.contains(keyword)));
    }

    @When("User filters by the {string} brand")
    public void filterByBrandOrder(String brandOrder) {
        n11.selectBrandByOrder(brandOrder);
    }

    @When("Sorts results by number of {string}")
    public void sortProductBySelectedOption(String sortBy) {
        n11.sortProductBySelectedOption(sortBy);
    }

    @When("Filters results to show only {string} products")
    public void filterFreeShipping(String deliveryOption) {
        n11.filterDeliveryOptions(deliveryOption);
    }

    @Then("User verifies the result is correct according to filter")
    public void verifyFilterAndSortResult() {
        List<Integer> reviewNumbers = n11.getReviewNumbersForProducts();
        List<Integer> copyReviewNumbers = new ArrayList<>(reviewNumbers);
        copyReviewNumbers.sort(Comparator.reverseOrder());

        Assert.assertEquals(reviewNumbers, copyReviewNumbers);
        Assert.assertTrue(n11.isCargoBadgeFieldExistAsHidden());
    }
}
