package com.solera.global.qa.template.web.behavior.pages.reports.payments;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.FilesCompare;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.Reports;
import com.solera.global.qa.template.web.behavior.pages.componentpages.search.CaseSearch;

public class ReportsPayments extends BrowserPage {

    public static final String PAYMENT_REPORT = "//button[@jest-id = 'ReportV2']";

    private final FilesCompare filesActions = new FilesCompare();

    public ReportsPayments() {
        super();
    }

    public boolean validateReportPaymentsVehicle() {
        CaseSearch casesFilter = new CaseSearch();
        casesFilter.advanceSearch(CaseType.VEHICLES);
        casesFilter.fillFilterField("advertId","APRV2506000042");

        new Buttons().clickSearchBtn();

        filesActions.waitButtonAndClick(PAYMENT_REPORT);

        return filesActions.reportToDownload(Reports.REPORTS_PAYMENT_REPORT_ADMIN_VEH);

    }

}
