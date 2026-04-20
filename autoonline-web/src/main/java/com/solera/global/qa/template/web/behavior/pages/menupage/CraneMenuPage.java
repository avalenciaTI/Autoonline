package com.solera.global.qa.template.web.behavior.pages.menupage;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
public class CraneMenuPage extends BrowserPage {

    private static final String CRANE_MENU = "//span[contains(text(), 'Traslados')]/ancestor::li";
    private static final String SEARCH_OPTION = "//a[contains(@href, '/transfers/search/')]";

    public void openCraneMenu() {
        log.info("Opening Crane Menu");
        click(getElement(By.xpath(CRANE_MENU)));
        log().image("Crane Menu opened successfully", takeScreenshot());
    }

    public void openSearch() {
        log.info("Opening Crane Search");
        click(getElement(By.xpath(SEARCH_OPTION)));
        log().image("Search CLICKED successfully", takeScreenshot());
    }


}
