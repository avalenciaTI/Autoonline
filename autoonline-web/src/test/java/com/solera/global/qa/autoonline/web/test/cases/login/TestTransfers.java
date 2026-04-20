package com.solera.global.qa.autoonline.web.test.cases.login;

import com.solera.global.qa.taf.core.data.annotations.TmsData;
import com.solera.global.qa.taf.core.data.enums.TcType;
import com.solera.global.qa.template.web.behavior.data.tools.IdGenerator;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.WebTestBase;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CaseType;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;


@Slf4j
public class TestTransfers extends WebTestBase {

    String caseIdentifier;

    @Test(priority = 1)
    @TmsData.Tc(
            tcId = 160954,
            tcName = "CP087_Generation of transfers",
            tcType = TcType.REGRESSION
    )
    public void tc1_verifyTransferGeneration() {
        AolWebUser user = this.users.getMasterUser();
        loginPage().logIn(user);
        mainMenu().clickCases();
        registrationMenu().consultCases();
        caseSearch().generalSearch(CaseType.VEHICLES, IdGenerator.VIN_PREFIX);

        try {
            caseIdentifier = generationofTransfer().clickCaseResult();
            log.info("Case sucessfully selected.");
        } catch (NoSuchElementException e) {
            assertions().fail("No cases were found for the search criteria.");
        }
    }

    @Test(priority = 2, dependsOnMethods = "tc1_verifyTransferGeneration")
    @TmsData.Tc(
            tcId = 160956,
            tcName = "CP090_Transfer consultation",
            tcType = TcType.REGRESSION
    )
    public void tc2_verifyTransferConsult() {
        AolWebUser user = this.users.getMasterUser();
        loginPage().logIn(user);
        mainMenu().clickCases();
        registrationMenu().consultCases();
        assertions().assertThat(caseSearch().generalSearch(CaseType.VEHICLES, caseIdentifier))
                .as("CASE SEARCH SUCCESS?").isTrue();
    }

    @Test(priority = 3)
    @TmsData.Tc(
            tcId = 519374,
            tcName = "CP009_Initialize transfer",
            tcType = TcType.REGRESSION
    )
    public void tc3_initializeTransfer() {
        AolWebUser user = this.users.getCraneUser();
        loginPage().logIn(user);
        transfersPage.openTransfersMenu();
        String transferVin = transfersPage.searchTransferByState("Por validar");
        transfersPage.initializeTransfer();
        assertions().assertThat(transfersPage.validateTransferInit(transferVin))
                .as("WAS SUCCESSFULLY INIT ?").isTrue();
    }

    @Test(priority = 4)
    @TmsData.Tc(
            tcId = 160955,
            tcName = "CP0888_Upload \"recoleccion de vehiculo\" photos in transfer",
            tcType = TcType.REGRESSION
    )
    public void tc4_uploadPhotosInTRansfer() {
        AolWebUser user = this.users.getCraneUser();
        loginPage().logIn(user);
        transfersPage.openTransfersMenu();
        transfersPage.searchTransferByState("En tránsito");
        transfersPage.updateTransfer();
        transfersPage.uploadImages();
        assertions().assertThat(transfersPage.verifyLoadedImages())
                .as("WERE ALL IMAGES LOADED? ").isTrue();
    }
}