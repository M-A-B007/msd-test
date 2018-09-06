package com.andrlik.msdquiz.stepdefs;

import com.andrlik.msdquiz.pages.DialogAddedToCart;
import com.andrlik.msdquiz.pages.ShopPage;
import com.andrlik.msdquiz.pages.ShoppingCart;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

/**
 * @author Michal Andrlik (andrlik@avast.com)
 * @since 9/6/2018
 **/
public class StepDefinitions {
    private final String ESHOP_URL = "http://www.hwkitchen.cz";
    private WebDriver driver;
    private ShopPage lazyShopPage = null;

    private ShopPage getShopPage() {
        if (lazyShopPage == null) {
            lazyShopPage = new ShopPage(driver);
        }
        return lazyShopPage;
    }

    /**
     * Closes the browser after the test finishes.
     */
    @After
    public void killBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("^start Chrome browser$")
    public void startBrowser() {
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    @Given("^HW-kitchen shop page is displayed$")
    public void navigateToHWKitchenShopPage() {
        driver.get(ESHOP_URL);
    }

    @And("^navigate to section \"Robots And Kits > Bot Kit\"$")
    public void navigateToSection() {
        getShopPage()
                .expandMenuItem(ShopPage.MenuItem.ROBOTS_AND_KITS)
                .expandMenuSubItem(ShopPage.MenuSubItem.BOT_KIT);
    }

    @And("^filter only the goodies on stock$")
    public void filterOnlyTheGoodiesOnStock() {
        getShopPage().filterOnlyOnStock(true);
    }

    @And("^sort the goodies from most expensive$")
    public void sortTheGoodiesFromMostExpensive() {
        getShopPage().sortContentByPriceDesc();
    }

    @And("^add (\\d+) most expensive items to shopping cart$")
    public void addXMostExpensiveItemsToShoppingCart(int count) {
        for (int i = 0; i < count; i++) {
            DialogAddedToCart dialogAddedToCart = getShopPage().getProductWidget(i)
                    .hoverOver()
                    .addToCart();
            dialogAddedToCart.backToShop();
        }
    }

    @Then("^verify the shopping cart contains (\\d+) items$")
    public void theShoppingCartContainsItems(int count) {
        ShoppingCart shoppingCart = getShopPage().openShoppingCart();
        Assert.assertEquals(shoppingCart.getNumberOfRowsInTable(), count, "Number of items in cart does not match the expected count.");

    }

}
