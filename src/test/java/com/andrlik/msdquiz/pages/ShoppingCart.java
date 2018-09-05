package com.andrlik.msdquiz.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Michal Andrlik (andrlik@avast.com)
 * @since 9/5/2018
 * <p>
 * This Page Object class uses traditional Selenium annotation
 * which works well for screens with simple static structure without any animation effects.
 **/
public class ShoppingCart extends GenericPage {
    private static final String CSS_TABLE = "div#cart table";
    private static final By BY_ROWS_IN_TABLE = By.cssSelector("tbody tr.single-merchandise");

    @FindBy(how = How.CSS, css = CSS_TABLE)
    private WebElement table;

    public ShoppingCart(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public int getNumberOfRowsInTable() {
        return table.findElements(BY_ROWS_IN_TABLE).size();
    }

    //TODO: Other methods can be added to work with the shopping cart....

}
