package com.kavinschool.osticket.pages;

import com.kavinschool.osticket.utils.DriverUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SupportCenterHomePage {
	private WebDriver driver;

	@FindBy(how = How.CSS, using = "input.button2 ")
	//@CacheLookup
	private WebElement openNewTicketButton;

	public SupportCenterHomePage(WebDriver driver) {
		this.driver = driver;
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public void clickAndOpenNewTicket() {
		openNewTicketButton.click();
	}

	public OpenNewTicketPage openNewTicket() {
		try {
			//DriverUtils.reduceImplicitWait(driver,5);
			DriverUtils.delay(500);
			// click methods are not working properly in IE11
			//openNewTicketButton.click();
            //Work around directly submit it
            //DriverUtils.jsClick(openNewTicketButton,driver);
            openNewTicketButton.sendKeys(Keys.ENTER);
			//openNewTicketButton.submit();
		}
		catch (NoSuchElementException exp) {
            exp.printStackTrace();
        //} catch (InterruptedException exp) {
        //    exp.printStackTrace();
        }
        //DriverUtils.reduceImplicitWait(driver,30);
		return PageFactory.initElements(driver, OpenNewTicketPage.class);
	}
}
