package com.kavinschool.osticket.hooks;

import com.kavinschool.osticket.utils.DriverUtils;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static java.lang.System.*;

/**
 * The type Driver factory.
 */
public class DriverFactory {
    private static final String SERV_PROP_FILE = "src/test/resources/server.properties.txt";
    private static final Boolean LOCAL_CAPS_ON = true;
    private static final Boolean FIREFOX_PROFILE_ENABLED = false;
    private static final String FIREFOX_PROFILE_PATH = "";
    private static boolean isBrowserExist = false;

    /**
     * The constant LOCAL.
     */
    public static final String LOCAL = "local";
    /**
     * The constant REMOTE.
     */
    public static final String REMOTE = "remote";


    /**
     * The constant BROWSER_TYPE.
     */
    public static final String BROWSER_TYPE = "browserType";
    /**
     * The constant RUN_TYPE.
     */
    public static final String RUN_TYPE = "runType";
    /**
     * The constant SELENIUM_GRID_HUB.
     */
    public static final String SELENIUM_GRID_HUB = "selenium_grid_hub";
    /**
     * The constant FF_BROWSER_PATH.
     */
    public static final String FF_BROWSER_PATH = "ff_browser_path";
    /**
     * The constant FF_DRIVER_PATH.
     */
    public static final String FF_DRIVER_PATH = "ff_driver_path";
    /**
     * The constant GC_BROWSER_PATH.
     */
    public static final String GC_BROWSER_PATH = "gc_browser_path";
    /**
     * The constant GC_DRIVER_PATH.
     */
    public static final String GC_DRIVER_PATH = "gc_driver_path";
    /**
     * The constant IE_BROWSER_PATH.
     */
    public static final String IE_BROWSER_PATH = "ie_browser_path";
    /**
     * The constant IE_DRIVER_PATH.
     */
    public static final String IE_DRIVER_PATH = "ie_driver_path";
    /**
     * The constant EDGE_BROWSER_PATH.
     */
    public static final String EDGE_BROWSER_PATH = "edge_browser_path";
    /**
     * The constant EDGE_DRIVER_PATH.
     */
    public static final String EDGE_DRIVER_PATH = "edge_driver_path";


    private WebDriver driver;
    private String browserType;
    private String runType;
    private Scenario scenario;
    private DriverUtils driverUtils;

    private Properties props = new Properties();

    /**
     * Is browser exist boolean.
     *
     * @return the boolean
     */
    public static boolean isBrowserExist() {
        return isBrowserExist;
    }

    /**
     * Sets is browser exist.
     *
     * @param isBrowserExist the is browser exist
     */
    public static void setIsBrowserExist(boolean isBrowserExist) {
        DriverFactory.isBrowserExist = isBrowserExist;
    }

    /**
     * Sets up.
     *
     * @param scenario the scenario
     * @throws Exception the exception
     */
    @Before("@web-driver")
    public void setUp(Scenario scenario) throws Exception {
        System.out.println("scenario:" + scenario.getName());
        this.scenario = scenario;
        props.load(new FileInputStream(SERV_PROP_FILE));
        if (!isBrowserExist || driver == null) {
            driver = createDriver();
            isBrowserExist = true;
        }
        driverUtils = new DriverUtils(driver).setBrowserType(browserType);
    }


    /**
     * Create driver web driver.
     *
     * @return the web driver
     * @throws Exception the exception
     */
    public WebDriver createDriver() throws Exception {
        browserType = getProperty(BROWSER_TYPE);
        runType = getProperty(RUN_TYPE);
        // if the system variables not set,
        // then take it from the property file
        if (browserType == null || browserType.isEmpty()) {
            browserType = props.getProperty(BROWSER_TYPE);
        }

        if (runType == null || runType.isEmpty()) {
            runType = props.getProperty(RUN_TYPE);
        }

        switch (runType.toLowerCase()) {
            case LOCAL:
                driver = createLocalDriver(browserType);
                break;
            case REMOTE:
                driver = createRemoteDriver(browserType);
                break;
            default:
                throw new Exception(RUN_TYPE + " is not correct, "+ "accepted values are:" + LOCAL + " & " + REMOTE);
        }
        return driver;
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
     * Tear down.
     *
     * @throws Exception the exception
     */
    @After("@web-driver")
    public void tearDown() throws Exception {

//        try {
//            String screenShotfileName = driverUtils.getScreenShotFileName(this.getClass().getName(),
//                    scenario.getName().replaceAll(" ", ""));
//            scenario.embed(driverUtils.saveScreenShotTo(screenShotfileName), "image/png");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        //driver.close();
        driver.quit();
    }

    /**
     * Create local driver web driver.
     *
     * @param browserType the browser type
     * @return the web driver
     * @throws FileNotFoundException the file not found exception
     * @throws IOException           the io exception
     */
    public WebDriver createLocalDriver(String browserType) throws FileNotFoundException, IOException {

        out.println(BROWSER_TYPE + " " + browserType);
        switch (browserType.toLowerCase()) {
            case BrowserType
                    .FIREFOX:
                setFirefoxProperties();
                if (LOCAL_CAPS_ON) {
                    driver = new FirefoxDriver(getFirefoxDriverOptions());
                } else {
                    driver = new FirefoxDriver();
                }
                break;
            case BrowserType.IE:
                setIeProperties();
                if (LOCAL_CAPS_ON) {
                    driver = new InternetExplorerDriver(this.getIeDriverOptions());
                } else {
                    driver = new InternetExplorerDriver();
                }
                break;
            case BrowserType.CHROME:
                setChromeProperties();
                if (LOCAL_CAPS_ON) {
                    driver = new ChromeDriver(getChromeDriverOptions());
                } else {
                    driver = new ChromeDriver();
                }
                break;
            case BrowserType.EDGE:
                setEdgeProperties();
                if (LOCAL_CAPS_ON) {
                    driver = new EdgeDriver(getEdgeDriverOptions());
                } else {
                    driver = new EdgeDriver();
                }
                break;
            case BrowserType.SAFARI:
                driver = new SafariDriver();
                break;
            case BrowserType.OPERA_BLINK:
                    driver = new OperaDriver();
                    break;
            case BrowserType.HTMLUNIT:
                driver = new HtmlUnitDriver();
                break;
        }

        if (!browserType.equalsIgnoreCase(BrowserType.HTMLUNIT)) {
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        return driver;
    }

    /**
     * Create remote driver web driver.
     *
     * @param browserType the browser type
     * @return the web driver
     * @throws FileNotFoundException the file not found exception
     * @throws IOException           the io exception
     */
    private WebDriver createRemoteDriver(String browserType) throws
            IOException {

        String seleniumGridHub = props.getProperty(SELENIUM_GRID_HUB);
        out.println(BROWSER_TYPE + " " + browserType);
        switch (browserType.toLowerCase()) {
            case BrowserType.FIREFOX:
                setFirefoxProperties();
                driver = new RemoteWebDriver(new URL(seleniumGridHub), getFirefoxDriverOptions());
                break;
            case BrowserType.IE:
                setIeProperties();
                driver = new RemoteWebDriver(new URL(seleniumGridHub), getIeDriverOptions());
                break;
            case BrowserType.CHROME:
                setChromeProperties();
                driver = new RemoteWebDriver(new URL(seleniumGridHub), getChromeDriverOptions());
                break;
            case BrowserType.EDGE:
                setEdgeProperties();
                driver = new RemoteWebDriver(new URL(seleniumGridHub), getEdgeDriverOptions());
                break;
            case BrowserType.HTMLUNIT:
                //headless browser
                driver = new RemoteWebDriver(new URL(seleniumGridHub), getHtmlUnitOptions());
                break;
        }

        if (!browserType.equalsIgnoreCase(BrowserType.HTMLUNIT)) {
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        return driver;
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
     */
    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    /**
     * Gets run type.
     *
     * @return the run type
     */
    public String getRunType() {
        return runType;
    }

    /**
     * Sets run type.
     *
     * @param runType the run type
     */
    public void setRunType(String runType) {
        this.runType = runType;
    }

    /**
     * Gets scenario.
     *
     * @return the scenario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * Sets scenario.
     *
     * @param scenario the scenario
     */
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Gets driver utils.
     *
     * @return the driver utils
     */
    public DriverUtils getDriverUtils() {
        return driverUtils;
    }

    /**
     * Sets driver utils.
     *
     * @param driverUtils the driver utils
     */
    public void setDriverUtils(DriverUtils driverUtils) {
        this.driverUtils = driverUtils;
    }

    private InternetExplorerOptions getIeDriverOptions() {
        DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
        capability.setPlatform(Platform.WINDOWS);
        capability.setBrowserName(BrowserType.IE);
        capability.setJavascriptEnabled(true);
        capability.setCapability(CapabilityType.LOGGING_PREFS, getLoggingPreferences());
        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capability.setCapability(
                InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                true);
        capability.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, 1);
        // capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        // capability.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        //capability.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
        //capability.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
        //capability.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        //capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        //capability.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
        //capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
        //capability.setCapability("ignoreProtectedModeSettings", true);
        capability.setCapability("nativeEvents", false);
        capability.setCapability("unexpectedAlertBehaviour", "accept");
        capability.setCapability("ignoreProtectedModeSettings", true);
        capability.setCapability("disable-popup-blocking", true);
        capability.setCapability("enablePersistentHover", true);

        return new InternetExplorerOptions(capability).ignoreZoomSettings();
    }

    private EdgeOptions getEdgeDriverOptions() {
        final EdgeOptions edgeOptions = new EdgeOptions();
        DesiredCapabilities capability = DesiredCapabilities.edge();
        capability.setBrowserName(BrowserType.EDGE);
        capability.setJavascriptEnabled(true);
        capability.setCapability(CapabilityType.LOGGING_PREFS, getLoggingPreferences());
        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        capability.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, true);
        capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                true);
        capability.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, 1);
        capability.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
        capability.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
        capability.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        capability.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
        capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
        //capability.setCapability("ignoreProtectedModeSettings", true);
        capability.setCapability("takesElementScreenshot", true);
        capability.setPlatform(org.openqa.selenium.Platform.WIN10);

        edgeOptions.merge(capability);
        return edgeOptions;
    }

    private ChromeOptions getChromeDriverOptions() {
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName(BrowserType.CHROME);
        // capability.setCapability(CapabilityType.LOGGING_PREFS,
        // logs);
        // capability.setCapability("chrome.switches",
        // Arrays.asList("--incognito"));
        // capability.setCapability("chrome.switches",
        // Arrays.asList("--start-maximized"));
        String gcBrowserPath = props.getProperty(GC_BROWSER_PATH);
        capability.setCapability("chrome.binary", gcBrowserPath);
        capability.setPlatform(org.openqa.selenium.Platform.ANY);
        final HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        //chromePrefs.put("download.default_directory", BaseDriver.pdfDownloadDir);
        chromePrefs.put("browser.enable_spellchecking", false);
        chromePrefs.put("browser.enable_autospellcorrect", false);
        chromePrefs.put("spellcheck.use_spelling_service", "");
        chromePrefs.put("spellcheck.dictionary", "");
        chromePrefs.put("translate.enabled", false);
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        final HashMap<String, Object> pluginPref = new HashMap<>();
        pluginPref.put("enabled", false);
        pluginPref.put("name", "Chrome PDF Viewer");
        chromePrefs.put("plugins.plugins_list", Arrays.asList(pluginPref));
        final ChromeOptions options = new ChromeOptions();
        if ("gch".equalsIgnoreCase(this.getBrowserType()) || "googlechrome-headless".equalsIgnoreCase(this.getBrowserType())) {
            options.addArguments("headless");
            options.addArguments("window-size=1200x600");
            options.addArguments("disable-gpu");
            options.addArguments("screenshot");
            options.addArguments("enable-screenshot-testing-with-mode");
            options.addArguments("hide-scrollbars");

            // BaseDriver.writeLog("Google Chrome runs in headless mode", BaseDriver.LOG_INFO);
        } else {
            options.addArguments("--start-maximized");
        }
        //final String inCognitoMode = BaseDriver.serverProperties.getGcIncognitoMode();
        //options.addArguments("--incognito");
        options.addArguments("--test-type");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        // options.setBinary(gcBrowserPath);
        // options.addArguments("--kiosk");
        // options.addArguments("--start-fullscreen");
        options.addArguments("disable-popup-blocking", "true");
        //options.addArguments("download.default_directory", BaseDriver.pdfDownloadDir);
        options.addArguments("download.directory_upgrade", "true");
        options.addArguments("download.prompt_for_download", "false");

        options.setExperimentalOption("prefs", chromePrefs);

        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capability.setCapability(ChromeOptions.CAPABILITY, options);
        capability.setCapability(CapabilityType.LOGGING_PREFS, getLoggingPreferences());

        options.merge(capability);
        return options;
    }

    private FirefoxOptions getFirefoxDriverOptions() {
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        // if ("marionette".equalsIgnoreCase(browserType)) {
        // capability.setBrowserName("marionette");
        // } else {
        // capability.setBrowserName("firefox");
        // }
        capability.setBrowserName(BrowserType.FIREFOX);

        capability.setCapability(CapabilityType.LOGGING_PREFS, getLoggingPreferences());
        capability.setPlatform(org.openqa.selenium.Platform.ANY);
        final FirefoxOptions firefoxOptions = new FirefoxOptions();
        String ffBrowserPath = props.getProperty(FF_BROWSER_PATH);
        final FirefoxBinary binary = new FirefoxBinary(new File(ffBrowserPath));
        firefoxOptions.setBinary(binary);
        firefoxOptions.addPreference("extensions.logging.enabled", false);
        if (DriverFactory.FIREFOX_PROFILE_ENABLED) {
            final File profileDir = new File(FIREFOX_PROFILE_PATH);
            final FirefoxProfile profile = new FirefoxProfile(profileDir);
            firefoxOptions.setProfile(profile);
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.download.manager.showWhenStarting", false);
            // profile.setPreference("browser.download.defaultFolder", BaseDriver.pdfDownloadDir);
            // profile.setPreference("browser.download.dir", BaseDriver.pdfDownloadDir);
            // profile.setPreference("browser.download.lastDir", BaseDriver.pdfDownloadDir);
            profile.setPreference("browser.download.useDownloadDir", true);
            profile.setPreference("browser.download.panel.shown", false);
            profile.setPreference("browser.helperApps.alwaysAsk.force", false);
            profile.setPreference("pref.downloads.disable_button.edit_actions", false);
            profile.setPreference("pdfjs.enabledCache.state", false);
            profile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf");
            profile.setPreference("pdfjs.disabled", true);
            profile.setPreference("plugin.scan.plid.all", false);
            profile.setPreference("plugin.scan.Acrobat", 999);
            setDownloadMimeType(profile, "pdf");
            setDownloadMimeType(profile, "octet");

            capability.setCapability(FirefoxDriver.PROFILE, profile);
            firefoxOptions.setProfile(profile);
            firefoxOptions.merge(capability);
            // firefoxOptions.setLogLevel(Level.INFO);
            // capability =
            // firefoxOptions.addTo(capability);
        }
        return firefoxOptions;
    }


    private DesiredCapabilities getHtmlUnitOptions() {
        final DesiredCapabilities capability = DesiredCapabilities.htmlUnit();
        capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        capability.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        capability.setCapability(CapabilityType.LOGGING_PREFS, getLoggingPreferences());
        capability.setJavascriptEnabled(true);
        return capability;
    }

    private LoggingPreferences getLoggingPreferences() {
        final LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.CLIENT, Level.OFF);
        logs.enable(LogType.DRIVER, Level.OFF);
        logs.enable(LogType.PERFORMANCE, Level.OFF);
        logs.enable(LogType.PROFILER, Level.OFF);
        logs.enable(LogType.SERVER, Level.OFF);
        return logs;
    }

    private void setFirefoxProperties() {
        try {
            String ffBrowserPath = props.getProperty(FF_BROWSER_PATH);
            File browserFile = new File(ffBrowserPath);
            setProperty(FirefoxDriver.SystemProperty.BROWSER_BINARY, browserFile.getAbsolutePath());
            //setProperty("webdriver.firefox.bin", browserFile.getAbsolutePath());

            String ffDriverPath = props.getProperty(FF_DRIVER_PATH);
            File driverFile = new File(ffDriverPath);

            setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, driverFile.getAbsolutePath());
            //setProperty("webdriver.gecko.driver", driverFile.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setIeProperties() {
        String ieDriverPath = props.getProperty(IE_DRIVER_PATH);
        File file = new File(ieDriverPath);
        setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, file.getAbsolutePath());
        //setProperty("webdriver.ie.driver", file.getAbsolutePath());
    }

    private void setChromeProperties() {
        String gcDriverPath = props.getProperty(GC_DRIVER_PATH);
        File file = new File(gcDriverPath);
        setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, file.getAbsolutePath());
        //setProperty("webdriver.chrome.driver", file.getAbsolutePath());
    }

    private void setEdgeProperties() {
        String edgeDriverPath = props.getProperty(EDGE_DRIVER_PATH);
        File file = new File(edgeDriverPath);
        setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, file.getAbsolutePath());
        //setProperty("webdriver.edge.driver", file.getAbsolutePath());
    }
    /**
     * Sets the download mime type.
     *
     * @param profile
     *            the profile
     * @param fileType
     *            the file type
     */
    private void setDownloadMimeType(final FirefoxProfile profile, final String fileType) {

        final String pref = "browser.helperApps.neverAsk.saveToDisk";
        switch (fileType) {
            case "pdf":
                profile.setPreference(pref, "application/pdf");
                break;
            case "xls":
                profile.setPreference(pref, "application/vnd.ms-excel");
                break;
            case "csv":
                profile.setPreference(pref, "text/csv");
                break;
            case "xml":
                profile.setPreference(pref, "text/xml");
                break;
            case "octet":
                profile.setPreference(pref, "application/octet-stream");
                break;
            default:
                System.out.println("MimeType Download is not handled");
                break;
        }
    }
}