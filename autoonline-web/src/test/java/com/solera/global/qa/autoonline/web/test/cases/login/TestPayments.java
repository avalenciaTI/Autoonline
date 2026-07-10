package com.solera.global.qa.autoonline.web.test.cases.login;


import static java.lang.Thread.sleep;

import com.solera.global.qa.taf.core.config.TafConfig.AppConfig;
import com.solera.global.qa.taf.core.data.annotations.TmsData;
import com.solera.global.qa.taf.core.data.enums.TcType;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.WebTestBase;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogInPage;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import com.solera.global.qa.template.web.behavior.pages.payments.PaymentPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Slf4j
public class TestPayments extends WebTestBase {

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

    @Test(priority = 41)
    @TmsData.Tc(
            tcId = 160974,
            tcName = "CP057_consult payments",
            tcType = TcType.REGRESSION
    )
    public void tc41_consultPayments() throws Exception {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        new MenuPage().clickPayments();
        sleep(5000);
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.searchPendingPayment();
        assertions().assertThat(paymentPage.arePaymentValidationElements())
                .as("Exist Pending Validation Elements").isTrue();

    }

    @Test(priority = 42)
    @TmsData.Tc(
            tcId = 160975,
            tcName = "CP058_Download payment document",
            tcType = TcType.REGRESSION
    )
    public void tc42_downloadPayments() throws Exception {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        new MenuPage().clickPayments();
        sleep(5000);
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.searchPendingPayment();
        assertions().assertThat(paymentPage.arePaymentValidationElements())
                .as("Exist Pending Validation Elements").isTrue();
        paymentPage.openAwarding();
        paymentPage.getAllPaymentCards();

    }

    @Test(priority = 43)
    @TmsData.Tc(
            tcId = 160976,
            tcName = "CP059_Payment validation",    //todo review fail at TR register
            tcType = TcType.REGRESSION
    )
    public void tc33_paymentsValidation() throws Exception {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        new MenuPage().clickPayments();
        sleep(5000);
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.searchPendingPayment();
        assertions().assertThat(paymentPage.arePaymentValidationElements())
                .as("Exist Pending Validation Elements").isTrue();
        paymentPage.openAwarding();
        paymentPage.verifyPayments();
    }





     @Test(priority = 44)
    @TmsData.Tc(tcId = 3936494, tcName = "CP0XX_toBeDocumented [ManagerUser][Download payment Folder]", tcType = TcType.REGRESSION)
    public void tc44_downloadPaymentFolder () {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        new MenuPage().clickPayments();
        PaymentPage paymentPage = new PaymentPage();
        assertions().assertThat(paymentPage.downloadPaymentFolder())
                .as("Payment folder should be downloaded and ZIP validated successfully").isTrue();
    }

}
