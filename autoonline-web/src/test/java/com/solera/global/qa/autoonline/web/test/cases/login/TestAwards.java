package com.solera.global.qa.autoonline.web.test.cases.login;
import static java.lang.Thread.sleep;
import com.solera.global.qa.taf.core.config.TafConfig.AppConfig;
import com.solera.global.qa.taf.core.data.annotations.TmsData;
import com.solera.global.qa.taf.core.data.enums.TcType;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.data.types.Awarding;
import com.solera.global.qa.template.web.behavior.pages.WebTestBase;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogInPage;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogOffPage;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Slf4j
public class TestAwards extends WebTestBase {
    private static Awarding awarding;
    private static String sinister;

    @Override
    @BeforeMethod
    public void initBrowser() {
        log.info("Initializing browser without incognito mode for Awards tests");
        log.info("URL: {}", AppConfig.WEBAPPS_CUSTOM_APP_URL.getValue("autoonlinemx"));
        int initRetry = 0;
        boolean isBrowserInitialized = false;
        while (!isBrowserInitialized && initRetry < 3) {
            try {
                initRetry++;
                String url = AppConfig.WEBAPPS_CUSTOM_APP_URL.getValue("autoonlinemx");
                // Start without incognito to allow automatic downloads
                startBrowser(false).openURL(url);
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

    @Test(priority = 31)
    @TmsData.Tc(
            tcId = 160969,
            tcName = "CP054_Consult Adjudications",
            tcType = TcType.REGRESSION
    )
    public void tc31_consultAwardings() throws Exception {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        awardings().searchAwardings("Pendiente por adjuntar", master);
    }
    
    @Test(priority = 32)
    @TmsData.Tc(
            tcId = 161017,
            tcName = "CP016_Adjudicate to Buyer",
            tcType = TcType.REGRESSION
    )
    public void tc32_adjudicatePublicationToBuyer() throws Exception {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        awarding = awardings().adjudicatePublication();
    }

    @Test(priority = 33)
    @TmsData.Tc(
            tcId = 160971,
            tcName = "CP056_Do not adjudicate a unit.",
            tcType = TcType.REGRESSION
    )
    public void tc33_doNotAdjudicateAUnit() throws Exception {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        awardings().doNotAdjudicatePublication();
    }

    @Test(priority = 34)
    @TmsData.Tc(
            tcId = 160972,
            tcName = "CP082_Upload documents for payment",
            tcType = TcType.REGRESSION
    )
    public void tc34_uploadDocumentsForPayment() throws Exception {
        AolWebUser buyer = this.users.getPhysicalBuyerUser();
        loginPage().logIn(buyer);
        sinister = awardings().openAwardings(buyer);
        awardings().loadPaymentDocuments();
    }

    @Test(priority = 35)
    @TmsData.Tc(
            tcId = 160973,
            tcName = "CP083_Send documents for payment",
            tcType = TcType.REGRESSION
    )
    public void tc35_sendPaymentDocuments() throws Exception {
        AolWebUser buyer = this.users.getPhysicalBuyerUser();
        loginPage().logIn(buyer);
        awardings().openAwardingBySinister(buyer);
        awardings().sendDocuments();
    }



    @Test(priority = 36)
    @TmsData.Tc(tcId = 3936481, tcName = "[Awards][Vehicle] Bulk references registration", tcType = TcType.REGRESSION)
    public void tc36_bulkReferencesRegistration () throws Exception {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        awardings().bulkVehicleReferencesRegistration();
    }



    @Test(priority = 37)
    @TmsData.Tc(tcId = 3936482, tcName = "[Awards][Vehicle] Bulk awarding registration", tcType = TcType.REGRESSION)
    public void tc37_bulkAwardingRegistration () throws Exception {
        // Phase 1: Master user - upload references, validate, download report, consult payments
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        awardings().bulkVehicleAwardingRegistrationMaster();
        logOffPage.logOff();
        // Phase 2: Buyer user - consult awarded section and compare against master-extracted data
        AolWebUser buyer = this.users.getPhysicalBuyerUser2();
        loginPage().logIn(buyer);
        sleep(10000); // Wait for 10 seconds
        awardings().bulkVehicleAwardingRegistrationBuyer();
    }





 @Test(priority = 55)
    @TmsData.Tc(tcId = 3936434, tcName = "CP055_Adjudicate a unit.", tcType = TcType.REGRESSION)
    public void tc55_adjudicateAUnit () throws Exception {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        awarding = awardings().adjudicatePublication();
    }

    



}