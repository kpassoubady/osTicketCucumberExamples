package com.kavinschool.osticket.steps;

import com.kavinschool.osticket.hooks.DriverFactory;
import com.kavinschool.osticket.pages.OpenNewTicketPage;
import com.kavinschool.osticket.utils.DriverUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Method;

public class OpenNewTicketPageStepDefs {

    private final DriverUtils driverUtils;
    private WebDriver driver;
    private DriverFactory driverFactory;
    private OpenNewTicketPage openNewTicketPage;

    public OpenNewTicketPageStepDefs(DriverFactory driverFactory) {
        this.driverFactory = driverFactory;
        this.driver = driverFactory.getDriver();
        this.driverUtils = driverFactory.getDriverUtils();

        openNewTicketPage
                = PageFactory.initElements(driver, OpenNewTicketPage.class);
    }

    @Then("^I will wait for \"([^\"]*)\" text to shows$")
    public void iWillWaitForTextToShows(String arg1) throws Throwable {

    }

    @When("^I checked the Full Name \"([^\"]*)\" label exist$")
    public void iCheckedTheFullNameLabelExist(String expectedLabel) throws Throwable {
        String actualLabel = openNewTicketPage.getNameLabel();
        Assert.assertEquals(expectedLabel, actualLabel);

        
        String screenShotfileName = driverUtils.getScreenShotFileName(this.getClass().getName(),
                "iCheckedTheFullNameLabelExist");
         driverFactory.getScenario().embed(driverUtils.saveScreenShotTo(screenShotfileName), "image/png");
    }

    @Then("^I type first name \"([^\"]*)\" value$")
    public void iTypeFirstNameValue(String firstName) throws Throwable {
        openNewTicketPage.typeName(firstName);
    }

    @When("^I checked the Email Address \"([^\"]*)\" label exist$")
    public void iCheckedTheEmailAddressLabelExist(String expectedLabel) throws Throwable {
        String actualLabel = openNewTicketPage.getEmailAddressLabel();
        Assert.assertEquals(expectedLabel, actualLabel);
    }

    @Then("^I type email address \"([^\"]*)\" value$")
    public void iTypeEmailAddressValue(String email) throws Throwable {
        openNewTicketPage.typeEmail(email);
    }

    @When("^I checked the Telephone \"([^\"]*)\" and Extension \"([^\"]*)\" label exist$")
    public void iCheckedTheTelephoneAndExtensionLabelExist(String expectedLabelTel, String expectedLabelExt) throws Throwable {
        String actualLabel = openNewTicketPage.getTelephoneLabel();
        Assert.assertEquals(expectedLabelTel, actualLabel);
        actualLabel = openNewTicketPage.getExtLabel();
        Assert.assertEquals(expectedLabelExt, actualLabel.trim());
    }

    @Then("^I type telephone \"([^\"]*)\" and \"([^\"]*)\" value$")
    public void iTypeTelephoneAndValue(String telephone, String ext) throws Throwable {
        openNewTicketPage.typePhone(telephone);
        openNewTicketPage.typeExtn(ext);
    }

    @When("^I checked the Help Topic \"([^\"]*)\" label exist$")
    public void iCheckedTheHelpTopicLabelExist(String expectedLabel) throws Throwable {
        String actualLabel = openNewTicketPage.getHelpTopicLabel();
        Assert.assertEquals(expectedLabel, actualLabel);
    }

    @Then("^I type select a help topic \"([^\"]*)\" from dropdown$")
    public void iTypeSelectAHelpTopicFromDropdown(String helpTopic) throws Throwable {
        openNewTicketPage.selectHelpTopicEasily(helpTopic);
    }

    @When("^I checked the subject \"([^\"]*)\" label exist$")
    public void iCheckedTheSubjectLabelExist(String expectedLabel) throws Throwable {
        String actualLabel = openNewTicketPage.getSubjectLabel();
        Assert.assertEquals(expectedLabel, actualLabel);
    }

    @Then("^I type a subject \"([^\"]*)\" value$")
    public void iTypeASubjectValue(String subject) throws Throwable {
        openNewTicketPage.typeSubject(subject);
    }

    @When("^I checked the message \"([^\"]*)\" label exist$")
    public void iCheckedTheMessageLabelExist(String expectedLabel) throws Throwable {
        String actualLabel = openNewTicketPage.getMessageLabel();
        Assert.assertEquals(expectedLabel, actualLabel);
    }

    @Then("^I type a message \"([^\"]*)\" value$")
    public void iTypeAMessageValue(String message) throws Throwable {
        openNewTicketPage.typeMessage(message);
    }

    @Then("^I click on the Submit Ticket button$")
    public void iClickOnTheSubmitTicketButton() throws Throwable {
        openNewTicketPage.submitTicket();
    }

}
