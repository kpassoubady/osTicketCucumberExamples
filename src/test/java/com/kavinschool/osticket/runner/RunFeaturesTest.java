package com.kavinschool.osticket.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

import static cucumber.api.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",

        glue = {"classpath:com.kavinschool.osticket.steps",
                "classpath:com.kavinschool.osticket.hooks"},

        plugin = {
                "pretty",
                "html:target/cucumber-reports",
                "json:target/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml",
                "rerun:target/cucumber-reports/rerun.txt"
        },

        snippets= CAMELCASE,
        strict = true,
        //dryRun = true,
        monochrome = true,
        tags = {"@home-page"}
        //name = {"osticket"}
)
public class RunFeaturesTest {

}
