package com.andrlik.msdquiz.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

/**
 * @author Michal Andrlik (andrlik@avast.com)
 * @since 9/5/2018
 * <p>
 * This class is an example of some Page Object with custom structure.
 * The related web page reloads it's content on various user's action (for example the DOM tree is thrown away and
 * replaced each time user navigates through the left menu).
 * The related web page also displays number of animations (like spinning wheel overlay when loading, some toasters
 * pops on top/bottom of the screen, or partially opaque overlay covers other content when the page is busy.
 * <p>
 * The traditional approach (using Selenium's @FindBy annotation and PageFactory) will most likely face problems with
 * stale elements in situations like this.
 * So I choose different approach and constructed the Page Object with custom structure.
 * Cons: structure of this class is complex and it's construction takes some effort.
 * Pros: This class can deal with all "tricky" features on the page. The lazy instantiation of the web elements works
 * well with the dynamically changing DOM tree.
 **/
public class ShopPage extends GenericPage {
    private static final By BY_SORT_PRICE_DESC = By.xpath("//span[contains(@class,'tab-single')][.//input[contains(@name,'order')][contains(@value,'-price')]]");
    private static final By BY_OVERLAY_OPAQUE = By.cssSelector("div#cboxOverlay");
    private static final By BY_COOKIES_TOASTER = By.cssSelector("div.site-msg button.CookiesOK");
    private static final By BY_SUCCESS_MESSAGE = By.cssSelector("div.message.success-message.fixed-message");
    private static final By BY_LOADING_SPINNER = By.cssSelector("span#ajax-spinner");
    private static final By BY_SHOPPING_CART_ICON = By.cssSelector("a.header-cart");
    private static final By BY_ON_STOCK_FILTER = By.cssSelector("input[name='stock']");
    private static final By BY_WIDGET_SUBMIT = By.cssSelector("input[type=submit]");

    public ShopPage(WebDriver driver) {
        super(driver);
        acceptCookiesToaster();
        syncScreenReady();
    }

    private void syncScreenReady() {
        waitUntilLoadingDisappear();
        waitUntilOverlayOpaque();
    }

    private void waitUntilOverlayOpaque() {
        WebElement overlay = driver.findElement(BY_OVERLAY_OPAQUE);
        new WebDriverWait(driver, DEFAULT_TIMEOUT_IN_SECONDS)
                .until((Function<WebDriver, Object>) driver -> !overlay.isDisplayed() || overlay.getAttribute("style").contains("opacity: 1;"));
    }

    public void acceptCookiesToaster() {
        List<WebElement> acceptButton = driver.findElements(BY_COOKIES_TOASTER);
        if (!acceptButton.isEmpty() && acceptButton.get(0).isDisplayed()) {
            acceptButton.get(0).click();
        }
    }

    public void waitUntilSuccessMessageAppearAndDisappear() {
        new WebDriverWait(driver, DEFAULT_TIMEOUT_IN_SECONDS)
                .until(driver -> !driver.findElements(BY_SUCCESS_MESSAGE).isEmpty());

        new WebDriverWait(driver, LONG_TIMEOUT_IN_SECONDS)
                .until(driver -> driver.findElements(BY_SUCCESS_MESSAGE).isEmpty());
    }

    public void waitUntilLoadingDisappear() {
        new WebDriverWait(driver, LONG_TIMEOUT_IN_SECONDS)
                .until(driver -> {
                    List<WebElement> spinner = driver.findElements(BY_LOADING_SPINNER);
                    return spinner.isEmpty() || !spinner.get(0).isDisplayed();
                });
    }

    public ShoppingCart openShoppingCart() {
        driver.findElement(BY_SHOPPING_CART_ICON).click();

        return new ShoppingCart(driver);
    }

    public ShopPage sortContentByPriceDesc() {
        driver.findElement(BY_SORT_PRICE_DESC)
                .click();
        syncScreenReady();
        return this;
    }

    public ShopPage filterOnlyOnStock(boolean onStock) {
        new CheckBox(BY_ON_STOCK_FILTER)
                .setValue(onStock);
        syncScreenReady();
        return this;
    }

    public ShopPage expandMenuSubItem(MenuSubItem subItem) {
        driver.findElement(subItem.getBy()).click();
        new WebDriverWait(driver, DEFAULT_TIMEOUT_IN_SECONDS)
                .until(driver -> !driver.findElements(subItem.getByBreadcrumb()).isEmpty());
        return this;
    }

    public ShopPage expandMenuItem(MenuItem item) {
        if (!isMenuItemExpanded(item)) {
            driver.findElement(By.cssSelector(item.getCssSelector() + " div.topic")).click();
        }
        new WebDriverWait(driver, DEFAULT_TIMEOUT_IN_SECONDS)
                .until(driver -> isMenuItemExpanded(item));
        return this;
    }

    private boolean isMenuItemExpanded(MenuItem item) {
        return driver.findElement(By.cssSelector(item.getCssSelector())).getAttribute("class").contains("expanded");
    }

    public ProductWidget getProductWidget(int index) {
        List<WebElement> products = driver.findElements(By.cssSelector("ul.products > li.product"));
        return new ProductWidget(products.get(index));
    }

    public enum MenuItem {
        ARDUINO("div#categories div#cat-685"),
        ROBOTS_AND_KITS("div#categories div#cat-1141");
        //TODO: other menu items can be added here
        private String cssSelector;

        MenuItem(String cssSelector) {
            this.cssSelector = cssSelector;
        }

        public String getCssSelector() {
            return cssSelector;
        }
    }

    public enum MenuSubItem {
        BOT_KIT("/stavebnice-robota/"),
        ELECTRO_KIT("/elektronicke-stavebnice/");
        private static final String XPATH_BREADCRUMB = "//p[@id='navigation']//span[meta[contains(@content,'%s')]]";
        private static final String CSS_SUB_ITEM = "a[href='%s']";
        //TODO: other submenu items can be added here
        private String title;

        MenuSubItem(String title) {
            this.title = title;
        }

        public By getBy() {
            return By.cssSelector(String.format(CSS_SUB_ITEM, title));
        }

        public By getByBreadcrumb() {
            return By.xpath(String.format(XPATH_BREADCRUMB, title));
        }
    }

    public class ProductWidget {

        WebElement root;

        ProductWidget(WebElement root) {
            this.root = root;
        }

        public ProductWidget hoverOver() {
            Actions builder = new Actions(driver);
            builder.moveToElement(root).build().perform();
            return this;
        }

        public DialogAddedToCart addToCart() {
            WebElement submitButton = root.findElement(BY_WIDGET_SUBMIT);
            submitButton.click();

            waitUntilLoadingDisappear();
            waitUntilSuccessMessageAppearAndDisappear();
            return new DialogAddedToCart(driver);
        }
    }

    public class CheckBox {
        private WebElement root;

        CheckBox(By by) {
            root = driver.findElement(by);
        }

        public boolean isSelected() {
            String checked = root.getAttribute("checked");
            return "checked".equals(checked);
        }

        public void setValue(boolean value) {
            if (value != isSelected()) {
                root.click();
            }
        }
    }

}
