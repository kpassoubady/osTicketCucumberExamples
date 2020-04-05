package com.kavinschool.osticket.steps;

import com.kavinschool.osticket.hooks.DriverFactory;
import com.kavinschool.osticket.pages.SupportCenterHomePage;
import com.kavinschool.osticket.utils.DriverUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class OsTicketHomePageStepDefs {

    private WebDriver driver;

    public OsTicketHomePageStepDefs(DriverFactory driverFactory) {
        driver = driverFactory.getDriver();
    }

    @When("^I'm on the home page of \"([^\"]*)\"$")
    public void iMOnTheHomePageOf(String baseUrl) throws Throwable {
        DriverUtils.delay(2000);
        driver.get(baseUrl);
        DriverUtils.delay(1000);
    }

    @Then("^I verify that the title is \"([^\"]*)\"$")
    public void iVerifyThatTheTitleIs(String expectedTitle) throws Throwable {
        DriverUtils.delay(2000);
        String pageTitle = driver.getTitle();
        assertEquals(pageTitle, expectedTitle);
    }

    @Then("^I verify that the header shows as \"([^\"]*)\"$")
    public void iVerifyThatTheHeaderShowsAs(String expectedHeaderText) throws Throwable {
        String actualHeaderText = driver.findElement(By.cssSelector("h1")).getText();
        assertEquals(expectedHeaderText, actualHeaderText);

    }

    @Then("^I verify the top right corner shows as \"([^\"]*)\"$")
    public void iVerifyTheTopRightCornerShowsAs(String expectedTopRightCornerText) throws Throwable {
        assertEquals(expectedTopRightCornerText, driver.findElement(By.cssSelector("p")).getText());
    }

    @Then("^I verify the message in the middle shows with \"([^\"]*)\"$")
    public void iVerifyTheMessageInTheMiddleShowsWith(String expectedMiddleMessageText) throws Throwable {
        assertEquals(expectedMiddleMessageText, driver.findElement(By.cssSelector("p.big")).getText());
    }

    @Then("^I verify that the link \"([^\"]*)\" is present$")
    public void i_verify_that_the_link_is_present(String expectedLink) throws Throwable {
        boolean isLinkPresent = new DriverUtils(driver).isElementPresent(By.linkText(expectedLink));
        assertTrue(isLinkPresent);
    }

    @Then("^I verify that the Open New Ticket button is present$")
    public void iVerifyThatTheOpenNewTicketButtonIsPresent() throws Throwable {
        assertTrue(new DriverUtils(driver).isElementPresent(By.cssSelector("input.button2")));

    }

    @Then("^I verify that the Check Status button is present$")
    public void iVerifyThatTheCheckStatusButtonIsPresent() throws Throwable {
        assertTrue(new DriverUtils(driver).isElementPresent(By.xpath("//input[@value='Check Status']")));
    }

    @When("^I clicked on the Open New Ticket button$")
    public void iClickedOnTheOpenNewTicketButton() throws Throwable {
        SupportCenterHomePage supportCenterHomePage = PageFactory.initElements(driver,SupportCenterHomePage.class);
        supportCenterHomePage.openNewTicket();
    }
}
