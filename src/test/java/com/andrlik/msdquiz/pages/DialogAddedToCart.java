package com.andrlik.msdquiz.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Michal Andrlik (andrlik@avast.com)
 * @since 9/5/2018
 * <p>
 * This Page Object class uses traditional Selenium annotation
 * which works well for screens with simple static structure without any animation effects.
 **/
public class DialogAddedToCart extends GenericPage {
    private static final String CSS_BACK_TO_SHOP_BUTTON = "div.advanced-order-wrapper a.back-to-shop";

    @FindBy(how = How.CSS, css = CSS_BACK_TO_SHOP_BUTTON)
    private WebElement backToShopLink;

    DialogAddedToCart(WebDriver driver) {
        super(driver);
        new WebDriverWait(driver, DEFAULT_TIMEOUT_IN_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(CSS_BACK_TO_SHOP_BUTTON)));
        PageFactory.initElements(driver, this);
    }

    public ShopPage backToShop() {
        backToShopLink.click();
        return new ShopPage(driver);
    }
}
