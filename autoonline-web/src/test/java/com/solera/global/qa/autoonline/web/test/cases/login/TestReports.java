package com.solera.global.qa.autoonline.web.test.cases.login;

import static java.lang.Thread.sleep;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.solera.global.qa.taf.core.config.TafConfig.AppConfig;
import com.solera.global.qa.taf.core.data.annotations.TmsData;
import com.solera.global.qa.taf.core.data.enums.TcType;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.WebTestBase;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;

import lombok.extern.slf4j.Slf4j;


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




    @Test(priority = 60)
    @TmsData.Tc(tcId = 3936441, tcName = "CP060_Global transfer report", tcType = TcType.REGRESSION)
    public void tc60_globalTransferReport() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsTransfers().validateGlobalTransferReport(master))
                .as("TRANSFERS GLOBAL REPORT SUCCESS").isTrue();
    }

    @Test(priority = 61)
    @TmsData.Tc(tcId = 3936442, tcName = "CP061_Report of transfers by insurer", tcType = TcType.REGRESSION)
    public void tc61_transferReportByInsurer() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsTransfers().validateTransferReportByInsurer(master))
                .as("TRANSFERS REPORT BY INSURER SUCCESS").isTrue();
    }

    @Test(priority = 62)
    @TmsData.Tc(tcId = 3936443, tcName = "CP062_Report of transfers by provider", tcType = TcType.REGRESSION)
    public void tc62_transferReportByProvider() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsTransfers().validateTransferReportByProvider(master))
                .as("TRANSFERS REPORT BY PROVIDER SUCCESS").isTrue();
    }

    @Test(priority = 63)
    @TmsData.Tc(tcId = 3936444, tcName = "CP063_Report of transfers by branch", tcType = TcType.REGRESSION)
    public void tc63_transferReportByBranch() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsTransfers().validateTransferReportByBranch(master))
                .as("TRANSFERS REPORT BY BRANCH SUCCESS").isTrue();
    }

    @Test(priority = 64)
    @TmsData.Tc(tcId = 3936445, tcName = "CP064_Report of transfers by city", tcType = TcType.REGRESSION)
    public void tc64_transferReportByCity() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsTransfers().validateTransferReportByCity(master))
                .as("TRANSFERS REPORT BY CITY SUCCESS").isTrue();
    }

    @Test(priority = 65)
    @TmsData.Tc(tcId = 3936446, tcName = "CP065_Report of transfers by origin", tcType = TcType.REGRESSION)
    public void tc65_transferReportByOrigin() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsTransfers().validateTransferReportByOrigin(master))
                .as("TRANSFERS REPORT BY ORIGIN SUCCESS").isTrue();
    }

    @Test(priority = 66)
    @TmsData.Tc(tcId = 3936447, tcName = "CP066_Report of transfers by manufacturer", tcType = TcType.REGRESSION)
    public void tc66_transferReportByManufacturer() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsTransfers().validateTransferReportByManufacturer(master))
                .as("TRANSFERS REPORT BY MANUFACTURER SUCCESS").isTrue();
    }

    @Test(priority = 67)
    @TmsData.Tc(tcId = 3936448, tcName = "CP067_Report of transfers by type", tcType = TcType.REGRESSION)
    public void tc67_transferReportByType() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsTransfers().validateTransferReportByType(master))
                .as("TRANSFERS REPORT BY TYPE SUCCESS").isTrue();
    }

    @Test(priority = 68)
    @TmsData.Tc(tcId = 3936449, tcName = "CP068_Report of transfers by model", tcType = TcType.REGRESSION)
    public void tc68_transferReportByModel() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsTransfers().validateTransferReportByModel(master))
                .as("TRANSFERS REPORT BY MODEL SUCCESS").isTrue();
    }

    @Test(priority = 69)
    @TmsData.Tc(tcId = 3936450, tcName = "CP069_Report of transfers by type of unit", tcType = TcType.REGRESSION)
    public void tc69_transferReportByUnitType() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsTransfers().validateTransferReportByUnitType(master))
                .as("TRANSFERS REPORT BY UNIT TYPE SUCCESS").isTrue();
    }

    @Test(priority = 70)
    @TmsData.Tc(tcId = 3936451, tcName = "CP070_Report of transfers by dates", tcType = TcType.REGRESSION)
    public void tc70_transferReportByDates() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsTransfers().validateTransferReportByDates(master))
                .as("TRANSFERS REPORT BY DATES SUCCESS").isTrue();
    }

    @Test(priority = 71)
    @TmsData.Tc(tcId = 3936452, tcName = "CP071_Report of transfers by status", tcType = TcType.REGRESSION)
    public void tc71_transferReportByStatus() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsTransfers().validateTransferReportByStatus(master))
                .as("TRANSFERS REPORT BY STATUS SUCCESS").isTrue();
    }


    @Test(priority = 72)
    @TmsData.Tc(tcId = 3936453, tcName = "CP072_General Ticket Inventory Report", tcType = TcType.REGRESSION)
    public void tc72_GeneralTicketInventoryReport() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsInventory().validateGeneralInventoryReport(master))
                .as("GENERAL INVENTORY REPORT SUCCESS").isTrue();
    }

    @Test(priority = 73)
    @TmsData.Tc(tcId = 3936454, tcName = "CP073_Advanced Inventory Report", tcType = TcType.REGRESSION)
    public void tc73_AdvancedInventoryReport() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsInventory().validateInventoryAdvancedReport(master))
                .as("ADVANCED INVENTORY REPORT SUCCESS").isTrue();
    }

    @Test(priority = 74)
    @TmsData.Tc(tcId = 3936455, tcName = "CP074_Outgoing Inventory Report", tcType = TcType.REGRESSION)
    public void tc74_OutgoingInventoryReport() {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsInventory().validateInventoryOutgoingReport(master))
                .as("INVENTORY OUTGOING REPORT SUCCESS").isTrue();
    }

    @Test(priority = 75)
    @TmsData.Tc(tcId = 3936456, tcName = "CP075_Report of Vehicles to enter", tcType = TcType.REGRESSION)
    public void tc75_InventoryPendingEntryReportVehicles () {
        AolWebUser master = this.users.getMasterUser();
        assertions().assertThat(reportsInventory().validateInventoryPendingEntryReportVehicles(master))
                .as("INVENTORY PENDING ENTRY REPORT VEHICLES  SUCCESS").isTrue();
    }

}
