package com.kavinschool.osticket.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

import static cucumber.api.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/open_new_ticket.feature",
        glue = {"classpath:com.kavinschool.osticket.steps",
                "classpath:com.kavinschool.osticket.hooks"},
        plugin = {"pretty","html:target/selenium-reports","json:target/cucumber.json"},
        snippets= CAMELCASE,
        strict = true,
        //dryRun = true,
        monochrome = true,
      //  tags = {"@homepage"},
        name = {"osticket"}
)
public class RunFeaturesTest {
}
