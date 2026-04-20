package com.solera.global.qa.autoonline.web.test.cases.login;

import static java.lang.Thread.sleep;

import com.solera.global.qa.taf.core.config.TafConfig.AppConfig;
import com.solera.global.qa.taf.core.data.annotations.TmsData;
import com.solera.global.qa.taf.core.data.enums.TcType;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.WebTestBase;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Slf4j
public class TestReports extends WebTestBase {

    @BeforeMethod
    public void initBrowser() {
        int initRetry = 0;
        boolean isBrowserInitialized = false;
        while (!isBrowserInitialized && initRetry < 3) {
            try {
                initRetry++;
                startBrowser(false).openURL(AppConfig.WEBAPPS_CUSTOM_APP_URL.getValue("autoonlinemx"));
                isBrowserInitialized = true;
            } catch (Exception e) {
                log.error("An error occurred while initializing the browser: {}", e.getMessage(), e);
                if (initRetry < 3) {
                    initRetry++;
                    log.info("Retry to initialize the browser: {}", initRetry);
                }
            }
        }
    }

    @Test(priority = 11)
    @TmsData.Tc(
            tcId = 989811,
            tcName = "[Publication] [Detail] [Vehicle] Results Report",
            tcType = TcType.REGRESSION
    )
    public void tc1_resultsReportDownloadAndCompare() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsPublications().validateResultReport(master))
                .as("NO CHANGES ON REPORT BEHAVIOR").isTrue();

    }

    @Test(priority = 12)
    @TmsData.Tc(
            tcId = 989812,
            tcName = "[Publication] [Detail] [Vehicle] Offers Report",
            tcType = TcType.REGRESSION
    )
    public void tc2_offersReportDownloadAndCompare() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsPublications().validateOffersReport(master))
                .as("NO CHANGES ON REPORT BEHAVIOR").isTrue();

    }

    @Test(priority = 13)
    @TmsData.Tc(
            tcId = 1271180,
            tcName = "[Publication] [Detail] [Vehicle] Views Report",
            tcType = TcType.REGRESSION
    )
    public void tc3_viewsReportDownloadAndCompare() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsPublications().validateViewsReport(master))
                .as("NO CHANGES ON REPORT BEHAVIOR").isTrue();

    }

    @Test(priority = 14)
    @TmsData.Tc(
            tcId = 692963,
            tcName = "[Cases][Consult][Vehicle] Documents Report",
            tcType = TcType.REGRESSION
    )
    public void tc4_casesDocumentsReportDownloadAndCompare() throws Exception {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        mainMenu().clickCases();
        registrationMenu().consultCases();

        assertions().assertThat(reportsCases().validateDocumentsReportVehicle())
                .as("NO CHANGES ON REPORT BEHAVIOR").isTrue();

    }

    @Test(priority = 15)
    @TmsData.Tc(
            tcId = 692962,
            tcName = "[Cases][Consult][Vehicle] Report",
            tcType = TcType.REGRESSION
    )
    public void tc5_casesReportDownloadAndCompare() throws Exception {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        mainMenu().clickCases();
        registrationMenu().consultCases();
        //update method to delete file previously created on download file error when casesDocumentsReport is downloaded
        //first
        assertions().assertThat(reportsCases().validateReportVehicle())
                .as("NO CHANGES ON REPORT BEHAVIOR").isTrue();

    }

    @Test(priority = 16)
    @TmsData.Tc(
            tcId = 1036791,
            tcName = "[Cases][Consult][Various] Report",
            tcType = TcType.REGRESSION
    )
    public void tc6_casesReportDownloadAndCompare() throws Exception {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        mainMenu().clickCases();
        registrationMenu().consultCases();
        assertions().assertThat(reportsCases().validateReportVarious())
                .as("NO CHANGES ON REPORT BEHAVIOR").isTrue();
    }

    @Test(priority = 17)
    @TmsData.Tc(
            tcId = 1115673,
            tcName = "[Payment][Consult][Vehicle] Payment Report",
            tcType = TcType.REGRESSION
    )
    public void tc7_paymentReportDownloadAndCompare() throws Exception {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        new MenuPage().clickPayments();
        sleep(5000);
        assertions().assertThat(reportsPayments().validateReportPaymentsVehicle())
                .as("NO CHANGES ON REPORT BEHAVIOR").isTrue();
    }

    @Test(priority = 18)
    @TmsData.Tc(
            tcId = 1300181,
            tcName = "[Awarding][Consult][Vehicle] Report",
            tcType = TcType.REGRESSION
    )
    public void tc8_awardingReportDownloadAndCompare() throws Exception {
        AolWebUser buyer2 = this.users.getPhysicalBuyerUser2();
        loginPage().logIn(buyer2);
        sleep(5000);
        assertions().assertThat(reportsAwardings().validateReportBuyerAwardingVehicle())
                .as("NO CHANGES ON REPORT BEHAVIOR").isTrue();
    }
}
