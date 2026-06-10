package com.solera.global.qa.template.web.behavior.pages.reports.AwardignsBuyer;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.FilesCompare;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.Reports;
import com.solera.global.qa.template.web.behavior.pages.componentpages.search.CaseSearch;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class ReportsAwardings extends BrowserPage {

    public static final String AWARDING_REPORT = "//button[@jest-id = 'reportButton']";
    public static final String SEARCH_FILTER_GENERAL = "//input[contains(@id,'search-adverts_?')]";
    public static final String START_DATE_FILTER = "search-adverts_start";

    @FindBy(id = START_DATE_FILTER)
    WebElement startDateCaseCreationFilter;

    private final FilesCompare filesActions = new FilesCompare();

    public ReportsAwardings() {
        super();
    }

    public boolean validateReportBuyerAwardingVehicle() {

        String currentURL = getDriver().getCurrentUrl();
        String expectedURL = "/awardings/buyers/search/vehicle";
        currentURL = currentURL.replace("/dashboard",expectedURL);
        getDriver().get(currentURL);

        waitForElementInvisibility(By.xpath("//td[contains(@class,'loading')]"));

        WebElement filter=getElement(By.xpath("//button[@data-testid='open-drawer']"));

        click(filter);

        WebElement elementWeb = new CommonComponents().dynamicWebElement(SEARCH_FILTER_GENERAL,"modelStart");
        sendKeys(elementWeb, "2000");
        WebElement elementWeb2 = new CommonComponents().dynamicWebElement(SEARCH_FILTER_GENERAL,"modelEnd");
        sendKeys(elementWeb2, "2000");

        //Actions actions = new Actions(getDriver());
        //actions.moveToElement(startDateCaseCreationFilter);

        new CommonComponents().setCalendarDatesText(startDateCaseCreationFilter,"05/06/2025");

        new Buttons().clickSearchBtn();

        waitForElementInvisibility(By.xpath("//td[contains(@class,'loading')]"));


        filesActions.waitButtonAndClick(AWARDING_REPORT);

        return filesActions.reportToDownload(Reports.REPORTS_AWARDING_REPORT_BUYER_VEH);

    }

}
