package com.kavinschool.osticket.pages;

import com.kavinschool.osticket.utils.DriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class OpenNewTicketPage {
	private WebDriver driver;
	
	@FindBy(how = How.CSS, using = "th")
	@CacheLookup
	private WebElement strNameLabel;

	@FindBy(how = How.XPATH, using = "//div[@id='content']/form/table/tbody/tr[2]/th")
	@CacheLookup
	private WebElement strEmailAdderssLabel;

	@FindBy(how = How.XPATH, using = "//div[@id='content']/form/table/tbody/tr[3]/td")
	@CacheLookup
	private WebElement strTelephoneLabel;

	@FindBy(how = How.XPATH, using = "//div[@id='content']/form/table/tbody/tr[3]/td[2]")
	@CacheLookup
	private WebElement extLabel;

	@FindBy(how = How.XPATH, using = "//div[@id='content']/form/table/tbody/tr[5]/th")
	@CacheLookup
	private WebElement helpTopicLabel;

	@FindBy(how = How.XPATH, using = "//div[@id='content']/form/table/tbody/tr[6]/th")
	@CacheLookup
	private WebElement subjectLabel;

	@FindBy(how = How.XPATH, using = "//div[@id='content']/form/table/tbody/tr[7]/th")
	@CacheLookup
	private WebElement messageLabel;


	@FindBy(how = How.NAME, using = "name")
	@CacheLookup
	private WebElement strNameTextBox;
	
	@FindBy(how = How.NAME, using = "email")
	@CacheLookup
	private WebElement strEmailAdderssTextBox;

	@FindBy(how = How.NAME, using = "phone")
	@CacheLookup
	private WebElement strTelephoneTextBox;

	@FindBy(how = How.NAME, using = "phone_ext")
	@CacheLookup
	private WebElement strExtnTextBox;
	
	@FindBy(how = How.NAME, using = "subject")
	@CacheLookup
	private WebElement strSubjectTextBox;

	@FindBy(how = How.NAME, using = "message")
	@CacheLookup
	private WebElement strMessageTextArea;

	@FindBy(how = How.NAME, using = "message")
	@CacheLookup
	private WebElement strSubmitBtn;

	@FindBy(how = How.XPATH, using = "//input[@value='Reset']")
	@CacheLookup
	private WebElement strResetBtn;

	@FindBy(how = How.NAME, using = "cancel")
	@CacheLookup
	private WebElement strCancelBtn;
	
	@FindBy(how = How.NAME, using = "topicId")
	@CacheLookup
	private WebElement weSelectTopic;
	
	public OpenNewTicketPage(WebDriver driver) {
		this.driver = driver;
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public String getNameLabel() {
		return strNameLabel.getText();
	}
	
	public String getEmailAddressLabel() {
		return strEmailAdderssLabel.getText();
	}
	
	public String getTelephoneLabel() {
		return strTelephoneLabel.getText();
	}

	public String getExtLabel() {
		return extLabel.getText();
	}

	public String getSubjectLabel() {
		return this.subjectLabel.getText();
	}

	public String getMessageLabel() {
		return this.messageLabel.getText();
	}

	public String getHelpTopicLabel() {
		return this.helpTopicLabel.getText();
	}

	public void typeName(final String name) {
		strNameTextBox.clear();
		strNameTextBox.sendKeys(name);
	}
	
	public void typeEmail(final String email) {
		this.strEmailAdderssTextBox.clear();
		strEmailAdderssTextBox.sendKeys(email);
	}
	
	public void typePhone(final String phone) {
		strTelephoneTextBox.clear();
		strTelephoneTextBox.sendKeys(phone);
	}
	
	public void typeExtn(final String extn) {
		//strExtnTextBox.clear();
		//strExtnTextBox.sendKeys(extn);
		new DriverUtils(driver).clearAndType(strExtnTextBox, extn);
	}
	
	public void selectHelpTopicEasily(final String selectOption) {
		new DriverUtils(driver).selectOption(weSelectTopic, selectOption);
	}
	
	public void selectHelpTopic(final String selectOption, final String selectLocator) {
		new DriverUtils(driver).selectOption(driver, selectOption,selectLocator);
	}
	
	public void typeSubject(final String subject) {
		strSubjectTextBox.clear();
		strSubjectTextBox.sendKeys(subject);
	}
	
	public void typeMessage(final String message) {
		strMessageTextArea.clear();
		strMessageTextArea.sendKeys(message);
	}
		
	public SuccessfullyCreatedPage submitTicket() {
		strSubmitBtn.submit();
		return PageFactory.initElements(driver, SuccessfullyCreatedPage.class);
	}
	
	public void reset() {
		strResetBtn.click();
	}
	
	public void cancel() {
		strCancelBtn.click();
	}
		
}
