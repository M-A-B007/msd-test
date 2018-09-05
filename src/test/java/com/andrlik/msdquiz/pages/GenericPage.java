package com.andrlik.msdquiz.pages;

import org.openqa.selenium.WebDriver;

/**
 * @author Michal Andrlik (andrlik@avast.com)
 * @since 9/5/2018
 **/
abstract class GenericPage {
    final static int DEFAULT_TIMEOUT_IN_SECONDS = 3;
    final static int LONG_TIMEOUT_IN_SECONDS = 5 * DEFAULT_TIMEOUT_IN_SECONDS;
    final WebDriver driver;

    GenericPage(WebDriver driver) {
        this.driver = driver;
    }

}
