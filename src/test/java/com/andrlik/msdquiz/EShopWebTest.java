package com.andrlik.msdquiz;

import com.andrlik.msdquiz.pages.DialogAddedToCart;
import com.andrlik.msdquiz.pages.ShopPage;
import com.andrlik.msdquiz.pages.ShoppingCart;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Michal Andrlik (andrlik@avast.com)
 * @since 9/5/2018
 * <p>
 * The test visits e-shop <a href="https://www.hwkitchen.cz/">hwkitchen.cz</a>,
 * navigates to category with mBots and add two most expensive items to the cart.
 * Last step asserts if the cart contains expected number of items.
 **/
public class EShopWebTest {
    private final String ESHOP_URL = "http://www.hwkitchen.cz";
    private WebDriver driver;

    @BeforeTest(alwaysRun = true)
    public void initializeDriver() {
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    @Test
    public void addTwoMostExpensiveItemsToCart() {
        final int desiredItemsCount = 2;

        driver.get(ESHOP_URL);

        ShopPage shopPage = new ShopPage(driver)
                .expandMenuItem(ShopPage.MenuItem.ROBOTS_AND_KITS)
                .expandMenuSubItem(ShopPage.MenuSubItem.BOT_KIT)
                .filterOnlyOnStock(true)
                .sortContentByPriceDesc();

        for (int i = 0; i < desiredItemsCount; i++) {
            addItemToCart(shopPage, i);
        }

        ShoppingCart shoppingCart = shopPage.openShoppingCart();

        Assert.assertEquals(shoppingCart.getNumberOfRowsInTable(), desiredItemsCount, "Number of items in cart does not match the expected count.");
    }

    private void addItemToCart(ShopPage shopPage, int index) {
        DialogAddedToCart dialogAddedToCart = shopPage.getProductWidget(index)
                .hoverOver()
                .addToCart();
        dialogAddedToCart.backToShop();
    }

    @AfterTest(alwaysRun = true)
    public void closeDriver() {
        driver.quit();
    }
}
