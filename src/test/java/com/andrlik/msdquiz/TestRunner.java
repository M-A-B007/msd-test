package com.andrlik.msdquiz;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * @author Michal Andrlik (andrlik@avast.com)
 * @since 9/6/2018
 **/
@CucumberOptions(
        features = "src/test/resources/",
        glue = "com.andrlik.msdquiz.stepdefs",
        tags = {"not @wip"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        })
public class TestRunner extends AbstractTestNGCucumberTests {
}