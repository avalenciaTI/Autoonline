package com.solera.global.qa.autoonline.web.test.cases.login;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.solera.global.qa.taf.core.data.annotations.TmsData;
import com.solera.global.qa.taf.core.data.enums.TcType;
import com.solera.global.qa.template.web.behavior.data.tools.IdGenerator;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.WebTestBase;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CaseType;
import com.solera.global.qa.template.web.behavior.pages.inventory.InventoryPage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestLogInBasic extends WebTestBase {
        private static String vin ;
        private static String vin3 = "1JKTS177580528129";
        private static String vin2 = "TEST VEH 1JKTS176489151577";
        private static String caseN = "QAT2604001381";
        private static String publicationId = IdGenerator.getNewPublicationId();



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
        @Test(priority = 87)
        @TmsData.Tc(tcId = 160954, tcName = "CP087_Generation of transfers", tcType = TcType.REGRESSION)
        public void tc87_caseTransfersGeneration() {
                AolWebUser master = this.users.getMasterUser();
                String newVin = IdGenerator.getNewVin();
                loginPage().logIn(master);
                assertions().assertThat(caseIndividualRegistration().caseGenerationTransfer(vin2, caseData))
                                .as("CASE CREATION SUCCESS").isTrue();
        }


        //Passed
        @Test(priority = 90)
        @TmsData.Tc(tcId = 160956, tcName = "CP090_Transfer consultation", tcType = TcType.REGRESSION)
        public void tc90_caseTransfersconsultation() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(generalSearchTranfer().generalSearchTranfer(vin2, caseN))
                .as("CASE SEARCH SUCCESS?").isTrue();

                
        }

        

        //Passed
        @Test(priority = 9)
        @TmsData.Tc(tcId = 519374, tcName = "CP009_Initialize transfer", tcType = TcType.REGRESSION)
        public void tc09_caseInitializeTransfer() {
                AolWebUser crane = this.users.getCraneUser();
                loginPage().logIn(crane);
                //mainMenu().clickTransfers();
                transfersPage.openTransfersMenu();
                String transferVin = transfersPage.searchTransferByState("Por validar");
                transfersPage.initializeTransfer();
                assertions().assertThat(transfersPage.validateTransferInit(transferVin))
                .as("WAS SUCCESSFULLY INIT ?").isTrue();

                
        }

        //Passed
        @Test(priority = 88)
        @TmsData.Tc(tcId = 519374, tcName = "CP0888_Upload \"recoleccion de vehiculo\" photos in transfer", tcType = TcType.REGRESSION)
        public void tc88_caseuploadPhotosInTRansfer() {
                AolWebUser crane = this.users.getCraneUser();
                loginPage().logIn(crane);
                //mainMenu().clickTransfers();
                transfersPage.openTransfersMenu();
                transfersPage.searchTransferByState("En tránsito");
                transfersPage.updateTransfer();
                transfersPage.uploadImages();
                assertions().assertThat(transfersPage.verifyLoadedImages())
                .as("WERE ALL IMAGES LOADED? ").isTrue();

                
        } 


        //Passed

        @Test(priority = 107)
        @TmsData.Tc(tcId = 160957, tcName = "CP107_Photographs of vehicle delivery", tcType = TcType.REGRESSION)
        public void tc107_photographsOfVehicleDelivery() {
                AolWebUser crane = this.users.getCraneUser();
                loginPage().logIn(crane);
                transfersPage.openTransfersMenu();
                transfersPage.searchTransferByState("Recolectado");
                assertions().assertThat(transfersPage.validateUploadBlockedAfterTimeLimit(""))
                                .as("UPLOAD SHOULD BE BLOCKED AFTER 5 DAYS IN COLLECTED STATUS").isTrue();
        }

        //Passed
        @Test(priority = 50)
        @TmsData.Tc(tcId = 3936410, tcName = "CP050_Register publication of vehicle cases", tcType = TcType.REGRESSION)
        public void tc50_publicationCreationVehicle() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(publicationCreation().quickPublicationCreationValidation(publicationId))
                                .as("PUBLICATION CREATION SUCCESS").isTrue();
        }



        //Passed
        @Test(priority = 37)
        @TmsData.Tc(tcId = 3936403, tcName = "CP037_Generate compensation", tcType = TcType.REGRESSION)
        public void tc37_caseCompensationGeneration() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(caseIndividualRegistration().caseGenerationCompensation(vin2, caseData))
                                .as("CASE CREATION SUCCESS").isTrue();
        }


        //Passed
        @Test(priority = 53)
        @TmsData.Tc(tcId = 3936423, tcName = "CP053_Inventory consultation", tcType = TcType.REGRESSION)
        public void tc53_caseInventoryConsultation() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(caseInventoryPage().consultByCriteria())
                                .as("INVENTORY CONSULTATION SUCCESS").isTrue();
        }


         //Passed
        @Test(priority = 10)
        @TmsData.Tc(tcId = 3936471, tcName = "CP010_Verify at least 6 images required to entry inventory", tcType = TcType.REGRESSION)
        public void tc10_caseVerifyLeast6Images() {
                AolWebUser master = this.users.getMasterUser();
                String newVin = IdGenerator.getNewVin();
                loginPage().logIn(master);
                caseInventoryPage().consultByCriteria();
                assertions().assertThat(caseInventoryPage().verifyLoadedImagesCase(vin2, newVin))
                                .as("INVENTORY CONSULTATION SUCCESS").isTrue();
        }

        //Passed
        @Test(priority = 84)
        @TmsData.Tc(tcId = 3936426, tcName = "CP084_Exit inventory", tcType = TcType.REGRESSION)
        public void tc84_caseExitInventory() {
                AolWebUser master = this.users.getMasterUser();
                String newVin = IdGenerator.getNewVin();
                loginPage().logIn(master);
                caseInventoryPage().consultByCriteria();
                caseInventoryPage().outputPhotoUpload(vin2);
                //caseInventoryPage().verifyLoadedImagesCase(vin3, newVin);
                assertions().assertThat(caseInventoryPage().valideInventoryRemovalUnit(vin2))
                                .as("Exit inventory success").isTrue();
        }






        //Passed
        @Test(priority = 85)
        @TmsData.Tc(tcId = 3936424, tcName = "CP085_Uploading photos of entrance to corralon", tcType = TcType.REGRESSION)
        public void tc85_caseUploadingPhotoEntryCorralon() {
                AolWebUser master = this.users.getMasterUser();
                 String newVin = IdGenerator.getNewVin();
                loginPage().logIn(master);
                caseInventoryPage().consultByCriteria();
                assertions().assertThat(caseInventoryPage().entryPhotoUpload(vin2, newVin))
                                .as("INVENTORY CONSULTATION SUCCESS").isTrue();
        }

        

        //Passed
        @Test(priority = 86)
        @TmsData.Tc(tcId = 3936425, tcName = "CP086_Corralon output photo upload", tcType = TcType.REGRESSION)
        public void tc86_caseCorralonOutputPhotoUpload() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                caseInventoryPage().consultByCriteria();
                assertions().assertThat(caseInventoryPage().outputPhotoUpload(vin2))
                                .as("INVENTORY CONSULTATION SUCCESS").isTrue();
        }


//Passed
        @Test(priority = 78)
        @TmsData.Tc(tcId = 160963, tcName = "CP078_Display of publications online ", tcType = TcType.REGRESSION)
        public void tc78_caseDisplayPublicationsOnline() throws InterruptedException {
                AolWebUser buyer = this.users.getMasterUser();
                loginPage().logIn(buyer);
                assertions().assertThat(publicationCreation().openPublication(buyer, "En línea"))
                .as("PUBLICATION CONSULT SUCCESS").isTrue();
        }






        //Passed
        @Test(priority = 79)
        @TmsData.Tc(tcId = 70011, tcName = "CP079_Display of publications archived", tcType = TcType.REGRESSION)
        public void tc79_caseDisplayPublicationsArchived() throws InterruptedException {
                AolWebUser buyer = this.users.getMasterUser();
                loginPage().logIn(buyer);
                assertions().assertThat(publicationCreation().openPublication(buyer, "Archivadas"))
                .as("PUBLICATION CONSULT SUCCESS").isTrue();
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


         @Test(priority = 42)
        @TmsData.Tc(tcId = 3936414, tcName = "CP042_Register supplier catalogs", tcType = TcType.REGRESSION)
        public void tc42_caseRegisterSupplierCatalogs() {
                AolWebUser master = this.users.getMasterUser();
                String newVin = IdGenerator.getNewVin();
                loginPage().logIn(master);
                assertions().assertThat(transferIndividualRegistration().supplierRegistrationCatalog(newVin))
                                .as("CASE CREATION SUCCESS").isTrue();
                                
         }


         @Test(priority = 43)
         @TmsData.Tc(tcId = 3936415, tcName = "CP043_Register supplier branch catalogs", tcType = TcType.REGRESSION)
         public void tc43_caseRegisterSupplierBranchCatalogs() {
                 AolWebUser master = this.users.getMasterUser();
                 String newVin = IdGenerator.getNewVin();
                 loginPage().logIn(master);
                 assertions().assertThat(transferIndividualRegistration().supplierBranchRegistrationCatalog(newVin))
                                 .as("CASE CREATION SUCCESS").isTrue();
                                 
          }


        @Test(priority = 49)
        @TmsData.Tc(tcId = 160944, tcName = "CP049_Register publication of various cases", tcType = TcType.REGRESSION)
        public void tc49_publicationCreationDiverse() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(publicationCreation().fastVariousPublicationValidation(master, publicationId))
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
