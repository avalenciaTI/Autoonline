package com.solera.global.qa.template.web.behavior.pages.loginpage;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
public class LogOffPage extends BrowserPage {
    public static final String USER_ICON = "//i[@class='anticon anticon-user']";
    public static final String LOG_OFF = "//li[@data-testid='btn-logout']";

    public static final String USER_LOGIN = "login_email";

    public LogOffPage() {
        super();
    }

    public void logOff() {
        log.info("Logging off the user");
        click(getElement(By.xpath(USER_ICON)));
        click(getElement(By.xpath(LOG_OFF)));
        new Buttons().clickAcceptButton();
        waitForElementPresence(By.id(USER_LOGIN), Timeouts.CLOSE_SESSION);
    }


}
