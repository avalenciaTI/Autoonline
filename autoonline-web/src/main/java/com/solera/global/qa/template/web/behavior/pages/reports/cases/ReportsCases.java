package com.solera.global.qa.template.web.behavior.pages.reports.cases;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.FilesCompare;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.Reports;
import com.solera.global.qa.template.web.behavior.pages.componentpages.search.CaseSearch;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogInPage;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class ReportsCases extends BrowserPage {
    public static final String TEST_INSURER = "AAPR011";
    public static final String REPORT_BUTTON = "//button[@jest-id = 'reportButton']";
    public static final String DOCUMENTS_REPORT_BUTTON = "//button[@jest-id = 'ReportV2']";
    public static final String START_DATE_FILTER = "search-cases_startDate";
    public static final String GENERIC_INPUT_DATE = "//span[contains(@id,'start')]"
            + "/descendant::input[@class='ant-calendar-input ']";
    private static final String PAYMENT_LIMIT_DATE = "//span[@id='search-cases_startDate']"
            + "/descendant::input[@class='ant-calendar-input ']";
    public static final String END_DATE_FILTER = "search-cases_endDate";


    @FindBy(id = START_DATE_FILTER)
    WebElement startDateCaseCreationFilter;
    @FindBy(id = END_DATE_FILTER)
    WebElement endDateCaseCreationFilter;
    @FindBy(xpath = PAYMENT_LIMIT_DATE)
    WebElement paymentLimitDateInput;
    @FindBy(xpath = GENERIC_INPUT_DATE)
    WebElement genericInputDate;

    private final FilesCompare filesActions = new FilesCompare();

    public ReportsCases() {
        super();
    }

    public boolean validateDocumentsReportVehicle() {
        CaseSearch casesFilter = new CaseSearch();
        casesFilter.advanceSearch(CaseType.VEHICLES);
        casesFilter.fillFilterField("model","2000");
        new Buttons().clickSearchBtn();
        filesActions.waitButtonAndClick(DOCUMENTS_REPORT_BUTTON);
        return filesActions.reportToDownload(Reports.REPORTS_CASES_DOCUMENTS);

    }

    public boolean validateReportVehicle() {
        CaseSearch casesFilter = new CaseSearch();
        casesFilter.advanceSearch(CaseType.VEHICLES);
        casesFilter.fillFilterField("model","2000");
        new Buttons().clickSearchBtn();
        filesActions.waitButtonAndClick(REPORT_BUTTON);
        return filesActions.reportToDownload(Reports.REPORTS_CASES_VEHICLE);

    }

    public boolean validateReportVarious() {
        CaseSearch casesFilter = new CaseSearch();
        casesFilter.advanceSearch(CaseType.VARIOUS);
        new CommonComponents().setCalendarDatesText(startDateCaseCreationFilter,"01/01/2000");

        new Buttons().clickSearchBtn();

        filesActions.waitButtonAndClick(REPORT_BUTTON);
        return filesActions.reportToDownload(Reports.REPORTS_CASES_VARIOUS);

    }



}
