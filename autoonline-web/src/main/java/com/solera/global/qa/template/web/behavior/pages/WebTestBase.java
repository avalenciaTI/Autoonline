package com.solera.global.qa.template.web.behavior.pages;

import com.solera.global.qa.taf.core.config.TafConfig;
import com.solera.global.qa.taf.core.config.TafConfig.AppConfig;
import com.solera.global.qa.taf.core.test.TestBase;
import com.solera.global.qa.taf.web.tools.webdriver.Browser;
import com.solera.global.qa.taf.web.tools.webdriver.BrowserOptions;
import com.solera.global.qa.taf.web.tools.webdriver.BrowserType;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.data.types.Case;
import com.solera.global.qa.template.web.behavior.data.types.Sinister;
import com.solera.global.qa.template.web.behavior.data.types.UserReader;
import com.solera.global.qa.template.web.behavior.data.types.Vehicle;
import com.solera.global.qa.template.web.behavior.data.types.Workshop;
import com.solera.global.qa.template.web.behavior.pages.casecreation.Documents;
import com.solera.global.qa.template.web.behavior.pages.casecreation.IndividualRegistration;
import com.solera.global.qa.template.web.behavior.pages.casecreation.Photos;
import com.solera.global.qa.template.web.behavior.pages.casecreation.RegistrationMenu;
import com.solera.global.qa.template.web.behavior.pages.casecreation.transfercase.GenerationofTransfer;
import com.solera.global.qa.template.web.behavior.pages.casecreation.transfercase.TransferIndividualRegistration;
import com.solera.global.qa.template.web.behavior.pages.casecreation.transfercase.TransfersPage;
import com.solera.global.qa.template.web.behavior.pages.inventory.*;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.search.CaseSearch;
import com.solera.global.qa.template.web.behavior.pages.inventory.InventoryPage;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogInPage;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogOffPage;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import com.solera.global.qa.template.web.behavior.pages.publications.Awardings;
import com.solera.global.qa.template.web.behavior.pages.publications.PublicationCreation;
import com.solera.global.qa.template.web.behavior.pages.publications.PublicationOnline;
import com.solera.global.qa.template.web.behavior.pages.reports.AwardignsBuyer.ReportsAwardings;
import com.solera.global.qa.template.web.behavior.pages.reports.Inventory.ReportsInventory;
import com.solera.global.qa.template.web.behavior.pages.reports.cases.ReportsCases;
import com.solera.global.qa.template.web.behavior.pages.reports.payments.ReportsPayments;
import com.solera.global.qa.template.web.behavior.pages.reports.publications.ReportsPublications;
import com.solera.global.qa.template.web.behavior.pages.reports.transfers.ReportsTransfers;
import com.solera.global.qa.template.web.behavior.pages.usercreation.individualregistration.AdministratorMasterInter;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


@Slf4j
public class WebTestBase extends TestBase {

    protected UserReader users;
    protected Vehicle vehicle;
    protected Workshop workshop;
    protected Sinister sinister;
    protected LogInPage loginPage;
    protected LogOffPage logOffPage;
    protected TransfersPage transfersPage;
    protected InventoryPage inventoryPage;
    protected Case caseData;
    protected static final String DEFAULT_USER = "default";
    protected static final String DEFAULT_VEHICLE = "vehicle";
    protected static final String DEFAULT_WORKSHOP = "workshop";
    protected static final String DEFAULT_SINISTER = "sinister";
    protected static final String PREFS = "prefs";
    protected static final String ALLOW_ACCESS_FROM_FILES = "allow-file-access-from-files";
    private Browser browser;
    private static final String EXEC_ENV_NAME = TafConfig.EXEC_ENV_NAME.getValue();


    @BeforeMethod
    public void initBrowser() {
        log.info("Initializing browser with URL: {}", AppConfig.WEBAPPS_CUSTOM_APP_URL.getValue("autoonlinemx"));
        int initRetry = 0;
        boolean isBrowserInitialized = false;
        while (!isBrowserInitialized && initRetry < 3) {
            try {
                initRetry++;
                String url = AppConfig.WEBAPPS_CUSTOM_APP_URL.getValue("autoonlinemx");
                startBrowser(true).openURL(url);
                
                // Wait explicitly for page to load (URL should not be about:blank)
                WebDriver driver = browser.getDriver();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                
                try {
                    // Wait until URL is not "about:blank" and not empty
                    wait.until((Function<WebDriver, Boolean>) d -> {
                        String currentUrl = d.getCurrentUrl();
                        return currentUrl != null && !currentUrl.equals("about:blank") && !currentUrl.isEmpty();
                    });
                    String currentUrl = driver.getCurrentUrl();
                    log.info("Current URL after navigation: {}", currentUrl);
                log().image("Starting browser ", browser.takeScreenshot());
                isBrowserInitialized = true;
                } catch (Exception e) {
                    log.warn("Page did not load correctly on first attempt, retrying...");
                    // Try to navigate again
                    browser.openURL(url);
                    wait.until((Function<WebDriver, Boolean>) d -> {
                        String currentUrl = d.getCurrentUrl();
                        return currentUrl != null && !currentUrl.equals("about:blank") && !currentUrl.isEmpty();
                    });
                    String currentUrl = driver.getCurrentUrl();
                    log.info("Current URL after retry: {}", currentUrl);
                    log().image("Starting browser after retry", browser.takeScreenshot());
                    isBrowserInitialized = true;
                }
            } catch (Exception e) {
                log.error("An error occurred while initializing the browser: {}", e.getMessage(), e);
                if (initRetry < 3) {
                    initRetry++;
                    log.info("Retry to initialize the browser: {}", initRetry);
                }
            }
        }
    }

    public WebTestBase() {
        users = new UserReader(DEFAULT_USER);
        vehicle = new Vehicle(DEFAULT_VEHICLE);
        workshop = new Workshop(DEFAULT_WORKSHOP);
        sinister = new Sinister(DEFAULT_SINISTER);
        AolWebUser master = this.users.getMasterUser();
        caseData = new Case(master, vehicle, workshop, sinister);
        loginPage = new LogInPage();
        logOffPage = new LogOffPage();
        transfersPage = new TransfersPage();
        inventoryPage = new InventoryPage();
    }

    public Browser startBrowser(boolean isIncognitMode) {
        String downloadDir = CommonComponents.getDownloadDir();
        ensureDownloadDirectoryExists(downloadDir);

        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("intl.accept_languages", "es-MX");
        prefs.put("safebrowsing.enabled", true);                                    // enable safe browsing
        prefs.put("safebrowsing.disable_download_protection", true);                // disable download protection
        prefs.put("download.prompt_for_download", false);                           // disable download prompt
        prefs.put("download.default_directory", downloadDir);                       //set download directory
        prefs.put("download.directory_upgrade", true);                              // keep selected directory
        prefs.put("download.restrictions", 0);                                      // allow all downloads
        prefs.put("profile.default_content_settings.popups", 0);                    //disable popups
        prefs.put("profile.default_content_setting_values.automatic_downloads", 1); //allow automatic downloads
        prefs.put("plugins.always_open_pdf_externally", true);                      // avoid opening files in browser

        log.info("BrowserName {}", TafConfig.Web.BROWSER_NAME.getValue());
        if (TafConfig.Web.BROWSER_NAME.getValue().equalsIgnoreCase("edge")) {
            return startEdgeBrowser(prefs);
        } else if (TafConfig.Web.BROWSER_NAME.getValue().equalsIgnoreCase("chrome")) {
            return startChromeBrowser(prefs, isIncognitMode);
        } else if (TafConfig.Web.BROWSER_NAME.getValue().equalsIgnoreCase("firefox")) {
            return startFirefoxBrowser(prefs);
        } else {
            return startChromeBrowser(prefs, isIncognitMode);
        }
    }

    private BrowserOptions getBrowserOptions(HashMap<String, Object> prefs, boolean isIncognitoMode) {
        BrowserOptions options;

        if (isIncognitoMode) {
            options = BrowserOptions.chrome()
                    .incognitoMode()
                    .disableInfobars()
                    .setAcceptInsecureCerts(true)
                    .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                    .disableSearchEngineSelection()
                    .setExperimentalOption(PREFS, prefs)
                    .addArguments(ALLOW_ACCESS_FROM_FILES)
                    .addArguments("--no-sandbox")
                    .addArguments("--disable-dev-shm-usage")
                    .addArguments("--disable-blink-features=AutomationControlled")
                    .addArguments("--disable-web-security")
                    .build();
        } else {
            options = BrowserOptions.chrome()
                    .disableInfobars()
                    .setAcceptInsecureCerts(true)
                    .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                    .disableSearchEngineSelection()
                    .setExperimentalOption(PREFS, prefs)
                    .addArguments(ALLOW_ACCESS_FROM_FILES)
                    .addArguments("--no-sandbox")
                    .addArguments("--disable-dev-shm-usage")
                    .addArguments("--disable-blink-features=AutomationControlled")
                    .addArguments("--disable-web-security")
                    .build();
        }
        return options;
    }

    private Browser startChromeBrowser(HashMap<String, Object> prefs, boolean isIncognitoMode) {
        BrowserOptions options = getBrowserOptions(prefs, isIncognitoMode);

        log.info("Starting Chrome Browser");
        if (browser == null) {
            if ((EXEC_ENV_NAME.contains("grid"))) {
                log.info("browser contains grid");
                browser = Browser.builder(BrowserType.getByName(TafConfig.Web.BROWSER_NAME.getValue()))
                        .browserOptions(BrowserOptions.hub()
                                .customGrid(TafConfig.Web.HUB_URL.getValue(), options)
                                .build())
                        .build();
                browser.windowFocus()
                        .windowMaximize();
            } else {
                log.info("browser does not contains grid");
                browser = Browser.builder(BrowserType.getByName(TafConfig.Web.BROWSER_NAME.getValue()))
                        .browserOptions(options)
                        .build();
                browser.windowFocus()
                        .windowMaximize();
            }
        }
        return browser;
    }

    private Browser startEdgeBrowser(HashMap<String, Object> prefs) {
        log.info("Starting Edge Browser");

        EdgeOptions options = new EdgeOptions();
        options.addArguments("ie-mode");
        options.addArguments("inprivate");
        options.addArguments(ALLOW_ACCESS_FROM_FILES);
        options.setExperimentalOption(PREFS, prefs);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(EdgeOptions.CAPABILITY, options);

        if (browser == null) {
            if ((EXEC_ENV_NAME.contains("grid"))) {
                browser = Browser.builder(BrowserType.getByName(TafConfig.Web.BROWSER_NAME.getValue()))
                        .browserOptions(BrowserOptions.hub()
                                .customGrid(TafConfig.Web.HUB_URL.getValue(), BrowserOptions.edge()
                                        .setAcceptInsecureCerts(true)
                                        .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                                        .setCapability(EdgeOptions.CAPABILITY, options)
                                        .build())
                                .build())
                        .build();
                browser.windowFocus()
                        .windowMaximize();
            } else {
                browser = Browser.builder(BrowserType.getByName(TafConfig.Web.BROWSER_NAME.getValue()))
                        .browserOptions(BrowserOptions.edge()
                                .setAcceptInsecureCerts(true)
                                .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                                .setCapability(EdgeOptions.CAPABILITY, options)
                                .build())
                        .build();
                browser.windowFocus()
                        .windowMaximize();
            }
        }
        return browser;
    }

    public Browser startFirefoxBrowser(HashMap<String, Object> prefs) {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-private");
        options.addPreference("security.fileuri.strict_origin_policy", false);
        options.addPreference("browser.search.defaultenginename", "");

        for (String key: prefs.keySet()) {
            options.addPreference(key, prefs.get(key));
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
        capabilities.setAcceptInsecureCerts(true);
        capabilities.setCapability("unhandledPromptBehavior", UnexpectedAlertBehaviour.IGNORE);


        log.info("Starting Firefox Browser");
        if (browser == null) {
            if ((EXEC_ENV_NAME.contains("grid"))) {
                browser = Browser.builder(BrowserType.getByName(TafConfig.Web.BROWSER_NAME.getValue()))
                        .browserOptions(BrowserOptions.hub()
                                .customGrid(TafConfig.Web.HUB_URL.getValue(), BrowserOptions.firefox()
                                        .setAcceptInsecureCerts(true)
                                        .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                                        .build())
                                .build())
                        .build();
                browser.windowFocus()
                        .windowMaximize();
            } else {
                browser = Browser.builder(BrowserType.getByName(TafConfig.Web.BROWSER_NAME.getValue()))
                        .browserOptions(BrowserOptions.firefox()
                                .setAcceptInsecureCerts(true)
                                .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE)
                                .build())
                        .build();
                browser.windowFocus()
                        .windowMaximize();
            }
        }
        return browser;
    }

    @AfterMethod
    public void tearDown() {
        closeBrowser();
    }

    // The next block of code is used only to avoid the direct contruction of objects on test classes
    public LogInPage loginPage() {
        return new LogInPage();
    }

    public CaseSearch caseSearch() {
        return new CaseSearch();
    }

    public MenuPage mainMenu() {
        return new MenuPage();
    }

    public RegistrationMenu registrationMenu() {
        return new RegistrationMenu();
    }

    public PublicationCreation publicationCreation() {
        return new PublicationCreation();
    }
    public ReportsPublications reportsPublications() {return new ReportsPublications();}
    public ReportsCases reportsCases(){return new ReportsCases();}
    public ReportsPayments reportsPayments(){return new ReportsPayments();}
    public ReportsAwardings reportsAwardings() {return new ReportsAwardings();}
    public ReportsTransfers reportsTransfers() {return new ReportsTransfers();}
    public ReportsInventory reportsInventory() {return new ReportsInventory();}

    public IndividualRegistration caseIndividualRegistration() {
        return new IndividualRegistration();
    }

    public TransfersPage generalSearchTranfer() {
        return new TransfersPage();
    }
    public Photos photos() {
        return new Photos();
    }

    public Documents documents() {
        return new Documents();
    }

    public AdministratorMasterInter adminMasterInter() {
        return new AdministratorMasterInter();
    }

    public TransferIndividualRegistration transferIndividualRegistration() {
        return new TransferIndividualRegistration();
    }

    public PublicationOnline publicationOnline() {
        return new PublicationOnline();
    }

    public Awardings awardings() {
        return new Awardings();
    }

    public GenerationofTransfer generationofTransfer() {
        return new GenerationofTransfer();
    }



    public InventoryPage caseInventoryPage() {
        return new InventoryPage();
    }

    private void ensureDownloadDirectoryExists(String downloadDir) {
        try {
            Files.createDirectories(Paths.get(downloadDir));
            log.info("Download directory ready: {}", downloadDir);
        } catch (Exception e) {
            log.warn("Could not create download directory '{}': {}", downloadDir, e.getMessage());
        }
    }

    public void closeBrowser() {
        try {
            if (browser != null) {
                log.info("closeBrowser", "Test result");
                // Quit the browser to close all windows and end the process
                try {
                    browser.getDriver().quit();
                } catch (Exception e) {
                    // If quit fails, try close as fallback
                    log.warn("quit() failed, trying close(): {}", e.getMessage());
                browser.close();
                }
                browser = null;
            }
        } catch (Exception e) {
            log.error("An error occurred while closing the browser: {}", e.getMessage(), e);
            log.info("Final screenshot taken");
        }
    }
}

