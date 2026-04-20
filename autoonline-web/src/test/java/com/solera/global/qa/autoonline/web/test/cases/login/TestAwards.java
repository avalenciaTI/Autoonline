package com.solera.global.qa.autoonline.web.test.cases.login;

import com.solera.global.qa.taf.core.data.annotations.TmsData;
import com.solera.global.qa.taf.core.data.enums.TcType;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.data.types.Awarding;
import com.solera.global.qa.template.web.behavior.pages.WebTestBase;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogInPage;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogOffPage;
import org.testng.annotations.Test;

public class TestAwards extends WebTestBase {
    private static Awarding awarding;
    private static String sinister;

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



}
