/*
 * Copyright (c) 2018. Kavin School LLC
 */

package com.kavinschool.osticket.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * The type Driver utils.
 */
public class DriverUtils {

    private WebDriver driver;
    /**
     * The Browser type.
     */
    public String browserType;
    /**
     * The constant SERV_PROP_FILE.
     */
    public static final String SERV_PROP_FILE = "src/test/resources/server.properties.txt";
    private Properties props;

    /**
     * Instantiates a new Driver utils.
     *
     * @param driver the driver
     */
    public DriverUtils(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Is element present boolean.
     *
     * @param by the by
     * @return the boolean
     */
    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Is alert present boolean.
     *
     * @return the boolean
     */
    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    /**
     * Close alert and get its text string.
     *
     * @param acceptNextAlert the accept next alert
     * @return the string
     */
    public String closeAlertAndGetItsText(boolean acceptNextAlert) {
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        if (acceptNextAlert) {
            alert.accept();
        } else {
            alert.dismiss();
        }
        return alertText;
    }

    /**
     * Gets base url.
     *
     * @return the base url
     */
    public String getBaseURL()  {
        props = new Properties();
        try {
            props.load(new FileInputStream(SERV_PROP_FILE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.getProperty("url");
    }

    /**
     * Navigate to base url.
     */
    public void navigateToBaseURL() {
        driver.navigate().to(getBaseURL() + "/osticket/");
    }

    /**
     * Open url.
     */
    public void openURL() {
        driver.get(getBaseURL() + "/osticket/");
    }

    /**
     * Navigate to gmail.
     */
    public void navigateToGmail() {
        driver.navigate().to(getBaseURL() + "/osticket/");
    }

    /**
     * Clear and type.
     *
     * @param field the field
     * @param text  the text
     */
    public void clearAndType(WebElement field, String text) {
        field.clear();
        field.sendKeys(text);
    }

    /**
     * Gets date time stamp.
     *
     * @return the date time stamp
     */
    public String getDateTimeStamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHmmss");
        java.util.Date today = new java.util.Date();
        return formatter.format(new java.sql.Timestamp(today.getTime()));
    }

    /**
     * Is element present boolean.
     *
     * @param driver the driver
     * @param by     the by
     * @return the boolean
     */
    public boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Select option.
     *
     * @param driver        the driver
     * @param selectOption  the select option
     * @param selectLocator the select locator
     */
    public void selectOption(WebDriver driver, final String selectOption,
                             final String selectLocator) {
        Select select = new Select(driver.findElement(By.name(selectLocator)));
        select.selectByVisibleText(selectOption);
    }

    /**
     * Select option.
     *
     * @param selectedElement the selected element
     * @param selectOption    the select option
     */
    public void selectOption(WebElement selectedElement, final String selectOption)
    {
        Select select = new Select(selectedElement);
        select.selectByVisibleText(selectOption);
    }

    /**
     * Is text present boolean.
     *
     * @param driver the driver
     * @param Text   the text
     * @return the boolean
     */
    public boolean isTextPresent(WebDriver driver, String Text) {
        try {
            return driver.findElement(By.tagName("body")).getText()
                    .contains(Text);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Save screen shot to byte [ ].
     *
     * @param screenShotfileName the screen shotfile name
     * @return the byte [ ]
     */
    public byte[] saveScreenShotTo(String screenShotfileName) {
        System.out.println("saveScreenShotTo");
        if (browserType.equalsIgnoreCase("htmlunit"))
            return new byte[0];
        try {
            return writeScreenShot(screenShotfileName);
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
        return new byte[0];
    }

    private byte[] writeScreenShot(String screenShotfileName)
            throws IOException {
        System.out.println("writeScreenShot: " + screenShotfileName);
        File tempScrShotFile = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(tempScrShotFile, new File(screenShotfileName));
        return ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

    }

    /**
     * Gets screen shot file name.
     *
     * @param className  the class name
     * @param methodName the method name
     * @return the screen shot file name
     */
// This method creates a file name with the following format
    // ScreenShot/Date/time_classname_testname.png
    // ScreenShot is a folder
    // Date is a folder
    // time_classname_testname.png is a file
    // Date format is yyyyMMdd
    // time format is HHmmssSSS
    // className and methodName special characters ".][" are replaced with "_"
    public String getScreenShotFileName(String className, String methodName) {
        DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
        DateFormat dateFormat1 = new SimpleDateFormat("HHmmssSSS");
        String now = dateFormat1.format(new Date());
        String today = dateFormat2.format(new Date());
        String fileName;
        System.out.println("Method Name:" + methodName);
        System.out.println("Class Name:" + className);
        if (methodName != null)
            fileName = className + "." + methodName;
        else
            fileName = className;
        fileName = "target/Screenshots/" + today + "/" + now + '_' + browserType + '_'
                + fileName.replaceAll("\\.|\\[|\\]", "_") + ".png";
        System.out.println("name:" + fileName);
        return fileName;
    }

    /**
     * Wait for text.
     *
     * @param text       the text
     * @param by         the by
     * @param timeoutMsg the timeout msg
     * @throws Exception the exception
     */
    public void waitForText(String text, By by,
                               String timeoutMsg) throws Exception {
        for (int second = 0;; second++) {
            if (second >= 60)
                fail(timeoutMsg);
            try {
                if (text.equals(driver.findElement(by).getText()))
                    break;
            } catch (Exception e) {
            }
            delay(1000);
        }
    }

    /**
     * Delay.
     *
     * @param seconds the seconds
     */
    public static void delay(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Click at.
     *
     * @param driver   the driver
     * @param byMethod the by method
     */
    public void clickAt(final WebDriver driver, final By byMethod) {
        final WebElement element = driver.findElement(byMethod);
        final Actions builder = new Actions(driver);
        final Action action = builder.moveToElement(element).click().build();
        action.perform();
    }

    /**
     * Click at.
     *
     * @param driver  the driver
     * @param by      the by
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    public void clickAt(WebDriver driver, By by, int xOffset, int yOffset) {
        WebElement element = driver.findElement(by);
        Actions builder = new Actions(driver);
        Action action = builder.moveToElement(element, 10, 10).click().build();
        action.perform();
    }

    /**
     * Click at.
     *
     * @param driver  the driver
     * @param element the element
     */
    public void clickAt(final WebDriver driver, final WebElement element) {
        final Actions builder = new Actions(driver);
        final Action action = builder.moveToElement(element).click().build();
        action.perform();
    }

    /**
     * Click at.
     *
     * @param driver  the driver
     * @param element the element
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    public void clickAt(final WebDriver driver, final WebElement element, final int xOffset,
                        final int yOffset) {
        final Actions builder = new Actions(driver);
        final Action action = builder.moveToElement(element, xOffset, yOffset).click().build();
        action.perform();
    }

    /**
     * Js click.
     *
     * @param element the variants record
     * @param driver  the driver
     * @throws InterruptedException the interrupted exception
     */
    public static void jsClick(final WebElement element, final WebDriver driver) throws InterruptedException {
        final JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((RemoteWebDriver) executor).executeScript("arguments[0].click();", element);
    }

    /**
     * Js click by id.
     *
     * @param locatorId the locator id
     * @param driver    the driver
     * @throws InterruptedException the interrupted exception
     */
    public static void jsClickById(final String locatorId, final WebDriver driver) throws InterruptedException {
        final JavascriptExecutor executor = (JavascriptExecutor) driver;
        ((RemoteWebDriver) executor).executeScript("return document.getElementById('"+locatorId+"').click();");
    }

    /**
     * Reduce implicit wait.
     *
     * @param driver                the driver
     * @param timeToReduceInSeconds the time to reduce in seconds
     */
    public static void reduceImplicitWait(WebDriver driver, final int timeToReduceInSeconds) {
        driver.manage().timeouts().implicitlyWait(timeToReduceInSeconds, TimeUnit.SECONDS);
    }

    /**
     * Gets driver.
     *
     * @return the driver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Sets driver.
     *
     * @param driver the driver
     */
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Gets browser type.
     *
     * @return the browser type
     */
    public String getBrowserType() {
        return browserType;
    }

    /**
     * Sets browser type.
     *
     * @param browserType the browser type
     * @return the browser type
     */
    public DriverUtils setBrowserType(String browserType) {
        this.browserType = browserType;
        return this;
    }

    /**
     * Gets props.
     *
     * @return the props
     */
    public Properties getProps() {
        return props;
    }

    /**
     * Sets props.
     *
     * @param props the props
     */
    public void setProps(Properties props) {
        this.props = props;
    }
}
