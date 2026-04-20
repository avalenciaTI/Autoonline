package com.solera.global.qa.template.web.behavior.pages.loginpage;

import com.solera.global.qa.taf.web.exceptions.ElementNotFoundException;
import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@Slf4j
public class LogInPage extends BrowserPage {

    private static final String CLASS_NAME = "LogInPage";

    //CONSTANTS
    private static final String BUTTON_LOGIN = "btnLogin";
    private static final String USER_NAME = "login_email";
    private static final String USER_PASSWORD = "login_password";
    private static final String TERMS_CONDITIONS_BOX = "//input[@class='ant-checkbox-input']";
    private static final String ACCEPT_ACTIVE_SESSION = "(//button[@class='ant-btn ant-btn-primary'])[1]";
    private static final String ACTIVE_SESSION_MSG_ID = "rcDialogTitle0";

    private static final String ACTIVE_SESSION_WINDOW = "(//div[@role='document'])[1]";
    private static final String NOTIFICATION_WINDOW = "//div[contains(@class,'bottomRight')]";
    private static final String NOTIFICATION_MESSAGE = "//div[@class='ant-notification-notice-message']";
    private static final String USER_ICON = "//*[@data-icon='user']";
    private static final String SIDE_MENU = "//li[@class='ant-menu-submenu ant-menu-submenu-vertical']";
    private static final String ONLINE_VEHICLE_BUTTON = "//a[@href='/adverts/list/online/vehicle']";
    private static final String ONLINE_DIVERSE_BUTTON = "//a[@href='/adverts/list/online/diverse']";

    //selectors
    @FindBy(id = USER_NAME)
    WebElement userName;
    @FindBy(id = USER_PASSWORD)
    WebElement password;
    @FindBy(id = BUTTON_LOGIN)
    WebElement buttonLogin;
    @FindBy(xpath = TERMS_CONDITIONS_BOX)
    WebElement termsConditions;
    @FindBy(xpath = ACCEPT_ACTIVE_SESSION)
    WebElement acceptActiveSession;
    @FindBy(xpath = ACTIVE_SESSION_WINDOW)
    WebElement acceptSessionWindow;
    @FindBy(xpath = NOTIFICATION_WINDOW)
    WebElement notificationWindow;



    public LogInPage() {
        super();
    }




    /**
     * Login with parameter.
     *
     * @param user name to login.
     */
    public void setUserName(String user) {
        log.info("Before wait for user name: {}", user);
        waitForElementToBeClickable(getElement(By.id(USER_NAME)), Timeouts.LOAD_ELEMENT);
        log.info("Before get user name element");
        userName = getElement(By.id(USER_NAME));
        log.info("Before sendkeys; Set user name: {}", user);
        sendKeys(userName, user);
        log.info("After sendkeys Set user name: {}", user);
    }

    /**
     * Set a password.
     *
     * @param pwd to login.
     */
    public void setPassword(String pwd) {
        password = getElement(By.id(USER_PASSWORD));
        sendKeys(password, pwd);
    }

    /**
     * Click Login Button.
     */
    public void clickLoginBtn() {
        log.info("Login button process");
        buttonLogin = getElement(By.id(BUTTON_LOGIN));
        click(buttonLogin);
        log.info("Click on login button");
        //After every click button a notification window appears, both success login and session active in other device
        String currentURL = getDriver().getCurrentUrl();
        boolean wasLoginDone = false;
        while (getDriver().getCurrentUrl().equals(currentURL) && !wasLoginDone) {
            try {
                log.info("Validating for active session alert");
                boolean isAlertVisible = waitForElementPresence(getElement(By.xpath(ACCEPT_ACTIVE_SESSION)),
                        Timeouts.LOAD_ELEMENT);
                log.info("Is session alert visible: {}", isAlertVisible);
                if (isAlertVisible) {
                    log.info("Alert is Visible");
                    log().image("Active session alert", takeScreenshot());
                    acceptActiveSession = getElement(By.xpath(ACCEPT_ACTIVE_SESSION));
                    click(acceptActiveSession);
                    log.info("Active session alert clicked");
                    wasLoginDone = true;
                    waitForElementInvisibility(acceptActiveSession, Timeouts.NOTIFICATION_CLOSED);
                } else {
                    log.info("Alert not visible");
                    wasLoginDone = true;
                }
            } catch (TimeoutException | ElementNotFoundException | StaleElementReferenceException
                     | NoSuchSessionException ex) {
                log.info("LOGIN no active session alert: {}", ex.getLocalizedMessage());
            }
            log.info("Login button clicked");

            //After every click button a notification window appears,
            // both success login and session active in other device
            try {
                notificationWindow = getElement(By.xpath(NOTIFICATION_WINDOW));
                if (!notificationWindow.isDisplayed()) {
                    log.info("Notification window not displayed");
                    waitForElementPresence(notificationWindow.findElement(By.xpath(NOTIFICATION_MESSAGE)));
                    log.info("Notification window displayed");
                    log().image("Notification window", takeScreenshot());
                }
            } catch (Exception ex) {
                log.info("No notification window found: {}", ex.getLocalizedMessage());
            }

            log().image("On login", takeScreenshot());
            if (isDisplayed(buttonLogin)) {
                waitForElementInvisibility(buttonLogin, Timeouts.NOTIFICATION_CLOSED);
            }
            if (getDriver().getCurrentUrl().contains("/dashboard")) {
                break;
            }
        }
        log.info("Wait for spinner goes off");
        waitForElementPresence(getElement(By.xpath(USER_ICON)), Timeouts.LOAD_RESULTS);
        log().image("After wait for spinner goes off", takeScreenshot());
    }

    /**
     *To accept terms & conditions.
     */
    public void clickCheckBox() {
        termsConditions = getElement(By.xpath(TERMS_CONDITIONS_BOX));
        try {
            log.info("Select Terms & Conditions checkbox");
            if (!isDisplayed(termsConditions)) {
                log.info("Terms & Conditions checkbox not displayed");
            }
            waitForElementToBeClickable(getElement(By.xpath(TERMS_CONDITIONS_BOX
                    + "/parent::span[@class='ant-checkbox']")), Timeouts.LOAD_ELEMENT);
            log.info("Terms & Conditions checkbox is clickable");
            termsConditions.click();
            waitForElementToBeClickable(getElement(By.xpath(TERMS_CONDITIONS_BOX
                    + "/parent::span[contains(@class, 'checked')]")), Timeouts.LOAD_PAGE);
        } catch (Exception ex) {
            log.info("Terms & Conditions checkbox error: {}", ex.getLocalizedMessage());
        }
        log().image("Terms & Conditions checkbox", takeScreenshot());
    }
    public boolean logIn(AolWebUser user){
        log.info("{} logIn", CLASS_NAME);
        int loginAttempts = 0;
        buttonLogin = getElement(By.id(BUTTON_LOGIN));
        while (!isDisplayed(buttonLogin) && loginAttempts < 3) {
            try {
                loginAttempts++;
                log.info("Login page not loaded yet. Waiting for login button to be displayed, retry: {} ",
                        loginAttempts);
                waitForElementPresence(buttonLogin, Timeouts.LOAD_ELEMENT);
            } catch (Exception ex) {
                log.warn("LOGIN no active session alert: {}", ex.getLocalizedMessage());
            }
        }
        if (!isDisplayed(buttonLogin)) {
            log.info("Login page not loaded");
        } else {
            log.info("Login page loaded");
        }
        log().image("On login page", takeScreenshot());
        setUserName(user.getUsername());
        log.info("User name set: {}", user.getUsername());
        setPassword(user.getPassword());
        log.info("Password set: {}", user.getPassword());
        clickCheckBox();
        log().image("All fieds filled", takeScreenshot());
        clickLoginBtn();

        waitForElementToBeClickable(getElement(By.xpath(SIDE_MENU)), Timeouts.LOAD_ELEMENT);
        sleep(2000);
        log().image("After full waits login", takeScreenshot());
        log.info(" {} logIn", CLASS_NAME);
        return true;
    }


    public boolean userConsult(AolWebUser user, AolWebUser userToConsult) {
        setUserName(user.getUsername());
        setPassword(user.getPassword());
        clickCheckBox();
        clickLoginBtn();
        return true;
    }

    /**
     * Validates if the online vehicle and diverse buttons are displayed after login.
     * 
     * @return true if both buttons are visible, false otherwise
     */
    public boolean areOnlineButtonsVisible() {
        try {
            log.info("Validating online vehicle and diverse buttons visibility");
            
            // Wait for and validate vehicle button
            WebElement vehicleButton = getElement(By.xpath(ONLINE_VEHICLE_BUTTON));
            boolean isVehiclePresent = waitForElementPresence(vehicleButton, Timeouts.LOAD_ELEMENT);
            boolean isVehicleVisible = isVehiclePresent && vehicleButton.isDisplayed();
            
            // Wait for and validate diverse button
            WebElement diverseButton = getElement(By.xpath(ONLINE_DIVERSE_BUTTON));
            boolean isDiversePresent = waitForElementPresence(diverseButton, Timeouts.LOAD_ELEMENT);
            boolean isDiverseVisible = isDiversePresent && diverseButton.isDisplayed();
            
            if (isVehicleVisible && isDiverseVisible) {
                log.info("Both online buttons are visible");
                log().image("Online vehicle and diverse buttons displayed", takeScreenshot());
                return true;
            } else {
                log.info("Buttons visibility - Vehicle: {}, Diverse: {}", isVehicleVisible, isDiverseVisible);
                log().image("Online buttons validation failed", takeScreenshot());
                return false;
            }
        } catch (Exception ex) {
            log.info("Online buttons not found: {}", ex.getLocalizedMessage());
            log().image("Online buttons validation failed", takeScreenshot());
            return false;
        }
    }
}