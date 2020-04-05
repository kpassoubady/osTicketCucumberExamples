package com.kavinschool.osticket.pages;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


public class SuccessfullyCreatedPage {
    private final String PAGE_TITLE = "KavinSchool:: Support Ticket System";

    private WebDriver driver;

    @FindBy (id = "infomessage")
    //@FindBy(how = How.ID, using = "infomessage")
    @CacheLookup
    private WebElement strSuccessMessageHeaderLabel;

    @FindBy(xpath = "//div[@id='content']/div[2]")
    //@FindBy(how = How.XPATH, using = "//div[@id='content']/div[2]")
    @CacheLookup
    private WebElement strSuccessMessageLabel;

    @FindBy(how = How.CSS, using = "b")
    @CacheLookup
    private WebElement strSuccessEmailLabel;

    public SuccessfullyCreatedPage(WebDriver driver) {
        this.driver = driver;
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(
                30, TimeUnit.SECONDS).pollingEvery(2, TimeUnit.SECONDS);

        wait.until(ExpectedConditions.titleIs(PAGE_TITLE));
        assertEquals(PAGE_TITLE, driver.getTitle());
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getSuccessMessageHeaderLabel() {
        return strSuccessMessageHeaderLabel.getText();
    }

    public void waitForMessageHeaderLabel(String text) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(
                30, TimeUnit.SECONDS).pollingEvery(2, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.textToBePresentInElement(strSuccessMessageHeaderLabel,text));
    }

    public String getSuccessMessageLabel() {
        return strSuccessMessageLabel.getText();
    }

    public String getSuccessEmailLabel() {
        return strSuccessEmailLabel.getText();
    }

}
