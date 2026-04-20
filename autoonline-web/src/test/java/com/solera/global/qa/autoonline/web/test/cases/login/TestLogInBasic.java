package com.solera.global.qa.autoonline.web.test.cases.login;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.solera.global.qa.taf.core.data.annotations.TmsData;
import com.solera.global.qa.taf.core.data.enums.TcType;
import com.solera.global.qa.template.web.behavior.data.tools.IdGenerator;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.WebTestBase;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CaseType;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class TestLogInBasic extends WebTestBase {
        private static String vin;
        private static String vin2 = "1JKTS1739812346";


        @BeforeClass
        public static void initializeVin() {
                vin = IdGenerator.getNewVin();
        }



        @Test(priority = 0)
        @TmsData.Tc(tcId = 160896, tcName = "LogIn to AutoOnline", tcType = TcType.REGRESSION)
        public void tc001_logInSuccessful() {
                AolWebUser master = this.users.getMasterUser();
                assertions().assertThat(loginPage().logIn(master)).as("USER LOG IN OK").isTrue();
        }




        @Test(priority = 24)
        @TmsData.Tc(tcId = 3936388, tcName = "CP024_Login New User", tcType = TcType.REGRESSION)
        public void tc24_logInSuccessful() {
                AolWebUser master = this.users.getPhysicalBuyerUser3();
                assertions().assertThat(loginPage().logIn(master)).as("USER LOG IN OK").isTrue();
        }

        @Test(priority = 25)
        @TmsData.Tc(tcId = 160927, tcName = "CP025_Various individual case record Diverse", tcType = TcType.REGRESSION)
        public void tc25_caseTransferCreation() {
                AolWebUser master = this.users.getMasterUser();
                String newVin = IdGenerator.getNewVin();
                loginPage().logIn(master);
                assertions().assertThat(transferIndividualRegistration().transferIndividualRegistrationDiverse(newVin))
                                .as("CASE CREATION SUCCESS").isTrue();
                                
                        }

        @Test(priority = 26)
        @TmsData.Tc(tcId = 160927, tcName = "CP026_Record of individual case transfers Transfer", tcType = TcType.REGRESSION)
        public void tc26_caseTransferCreation() {
                AolWebUser master = this.users.getMasterUser();
                String newVin = IdGenerator.getNewVin();
                loginPage().logIn(master);
                assertions().assertThat(transferIndividualRegistration().transferIndividualRegistration(newVin))
                                .as("CASE CREATION SUCCESS").isTrue();
        }

        @Test(priority = 27)
        @TmsData.Tc(tcId = 160928, tcName = "CP027_Vehicle individual case record", tcType = TcType.REGRESSION)
        public void tc27_caseVehicleCreation() {
                log.info("Starting TC Creating vehicle case with VIN: {}", vin);
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(caseIndividualRegistration().vehicleIndividualRegistration(vin, caseData))
                                .as("CASE CREATION SUCCESS").isTrue();
        }
        
        //Passed
        @Test(priority = 28)
        @TmsData.Tc(tcId = 3936394, tcName = "CP028_Miscellaneous general consultation", tcType = TcType.REGRESSION)
        public void tc28_miscellaneousGeneralConsultation() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(caseIndividualRegistration().miscellaneousGeneralConsultation())
                                .as("MISCELLANEOUS CONSULTATION SUCCESS").isTrue();
        }

        @Test(priority = 30)
        @TmsData.Tc(tcId = 160931, tcName = "CP030_General consultation vehicles", tcType = TcType.REGRESSION)
        public void tc30_caseVehicleConsult() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                mainMenu().clickCases();
                registrationMenu().consultCases();
                assertions().assertThat(caseSearch().generalSearch(CaseType.VEHICLES, vin2))
                                .as("CASE SEARCH SUCCESS?").isTrue();
        }

        @Test(priority = 31)
        @TmsData.Tc(tcId = 3936397, tcName = "CP031_Miscellaneous advanced query", tcType = TcType.REGRESSION)
        public void tc31_miscellaneousAdvancedQuery() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(caseIndividualRegistration().miscellaneousAdvancedQuery())
                                .as("MISCELLANEOUS CONSULTATION SUCCESS").isTrue();
        }

        @Test(priority = 38)
        @TmsData.Tc(tcId = 160939, tcName = "CP038_Attach documents to a case", tcType = TcType.REGRESSION)
        public void tc38_caseVehicleLoadFiles() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(documents().vehicleLoadFiles(master, vin)).as("FILES LOADED SUCCESS").isTrue();
        }

        @Test(priority = 39)
        @TmsData.Tc(tcId = 160940, tcName = "CP039_Approve documents", tcType = TcType.REGRESSION)
        public void tc39_caseVehicleValidateFiles() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(documents().vehicleValidateAllFiles(vin))
                                .as("FILES VALIDATED SUCCESS").isTrue();
        }



        @Test(priority = 40)
        @TmsData.Tc(tcId = 160941, tcName = "CP040_Attach images to a case", tcType = TcType.REGRESSION)
        public void tc40_caseVehicleLoadImages() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(photos().vehicleLoadImages(master, vin))
                                .as("CASE LOAD IMAGES SUCCESS").isTrue();
        }

        @Test(priority = 41)
        @TmsData.Tc(tcId = 3936413, tcName = "CP041_Register market catalogs", tcType = TcType.REGRESSION)
        public void tc41_caseRegisterMarketCatalogsInsurer() {
                AolWebUser master = this.users.getMasterUser();
                String newVin = IdGenerator.getNewVin();
                loginPage().logIn(master);
                assertions().assertThat(transferIndividualRegistration().marketRegistrationCatalog(newVin))
                                .as("CASE CREATION SUCCESS").isTrue();
                                
         }


                        


        @Test(priority = 49)
        @TmsData.Tc(tcId = 160944, tcName = "CP049_Register publication of various cases", tcType = TcType.REGRESSION)
        public void tc49_publicationCreationDiverse() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(publicationCreation().fastVariousPublicationValidation(master))
                                .as("PUBLICATION CREATION SUCCESS").isTrue();
        }

        //Passed
        @Test(priority = 53)
        @TmsData.Tc(tcId = 160928, tcName = "CP053_Add images to the Fotografías del caso section", tcType = TcType.REGRESSION)
        public void tc53_caseAddImages() {
                log.info("Starting TC Creating vehicle case with VIN: {}", vin);
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(caseIndividualRegistration().vehicleIndividualRegistration_AddImages(vin, caseData))
                                .as("CASE CREATION SUCCESS").isTrue();
        }
        

        @Test(priority = 76)
        @TmsData.Tc(tcId = 3936389, tcName = "CP076_Login to the system with buyer user", tcType = TcType.REGRESSION)
        public void tc76_logInBuyerUser() {
                AolWebUser master = this.users.getPhysicalBuyerUser3();
                assertions().assertThat(loginPage().logIn(master)).as("USER LOG IN OK").isTrue();
                assertions().assertThat(loginPage().areOnlineButtonsVisible()).as("ONLINE VEHICLE AND DIVERSE BUTTONS ARE VISIBLE").isTrue();
        }
        
        @Test(priority = 77)
        @TmsData.Tc(tcId = 3936389, tcName = "CP077_Consultation of Options as buyer user", tcType = TcType.REGRESSION)
        public void tc77_logInBuyerUser_ValidateButtons() {
                AolWebUser master = this.users.getPhysicalBuyerUser3();
                assertions().assertThat(loginPage().logIn(master)).as("USER LOG IN OK").isTrue();
                assertions().assertThat(loginPage().areOnlineButtonsVisible()).as("ONLINE VEHICLE AND DIVERSE BUTTONS ARE VISIBLE").isTrue();
        }

        @Test(priority = 22)
        @TmsData.Tc(tcId = 3936386, tcName = "CP022_Invite individual buyers", tcType = TcType.REGRESSION)
        public void tc22_inviteIndividualBuyer() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().inviteIndividualBuyer(master))
                                .as("BUYER INVITATION SUCCESS").isTrue();
        }

        @Test(priority = 23)
        @TmsData.Tc(tcId = 3936387, tcName = "CP023_Invite bulk buyers", tcType = TcType.REGRESSION)
        public void tc23_inviteMassiveBuyers() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().inviteMassiveBuyers(master))
                                .as("MASSIVE BUYER INVITATION SUCCESS").isTrue();
        }
}
