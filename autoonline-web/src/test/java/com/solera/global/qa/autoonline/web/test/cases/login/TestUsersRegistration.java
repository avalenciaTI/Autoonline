package com.solera.global.qa.autoonline.web.test.cases.login;

import com.solera.global.qa.taf.core.data.annotations.TmsData;
import com.solera.global.qa.taf.core.data.enums.TcType;
import com.solera.global.qa.template.web.behavior.data.tools.IdGenerator;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.WebTestBase;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CaseType;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Slf4j

public class TestUsersRegistration extends WebTestBase {
        private static String vin;

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
        
        //Completado
        @Test(priority = 1)
        @TmsData.Tc(tcId = 160900, tcName = "CP001_Internal user registration", tcType = TcType.REGRESSION)
        public void tc01_userCreateUser() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().individualRegistrationAdminIntern(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }
        
        //Completado
        @Test(priority = 2)
        @TmsData.Tc(tcId = 160901, tcName = "CP002_Master administrator user registration", tcType = TcType.REGRESSION)
        public void tc02_userCreateUser() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().individualRegistrationAdminMaster(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }

        //Completado
        @Test(priority = 3)
        @TmsData.Tc(tcId = 160929, tcName = "CP003_Secured admin user registration", tcType = TcType.REGRESSION)
        public void tc03_caseTransferCreation() {
                log.info("Starting TC Creating vehicle case with VIN: {}", vin);
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(caseIndividualRegistration().vehicleIndividualRegistration(vin, caseData))
                                .as("CASE CREATION SUCCESS").isTrue();
        }
        //Completado
        @Test(priority = 4)
        @TmsData.Tc(tcId = 3936371, tcName = "CP004_Insurance administrator(Intern) user registration", tcType = TcType.REGRESSION)
        public void tc04_userInsuranceCreateUser() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().individualRegistrationInsuranceCompanyIntern(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }
        
        //Completado
        @Test(priority = 5)
        @TmsData.Tc(tcId = 3936369, tcName = "CP005_Buyer administrator user registration", tcType = TcType.REGRESSION)
        public void tc05_userBuyerCreateUser() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().individualRegistrationBuyerPhysical(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }
        
        //Completado
        @Test(priority = 6)
        @TmsData.Tc(tcId = 3936370, tcName = "CP006_Moral buyer user registration", tcType = TcType.REGRESSION)
        public void tc06_userMoralBuyerCreateUser() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().individualRegistrationBuyerMoral(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }
        
        //Completado
        @Test(priority = 7)
        @TmsData.Tc(tcId = 3936372, tcName = "CP007_Vendor Administrator user registration", tcType = TcType.REGRESSION)
        public void tc07_userVendorCreateUser() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().individualRegistrationSupplierCrane(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }

        //Completado
        @Test(priority = 8)
        @TmsData.Tc(tcId = 3936373, tcName = "CP008_Master Administrator general query", tcType = TcType.REGRESSION)
        public void tc08_userMasterAdministratorGeneralQuery() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().individualRegistrationAdminMaster(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }


        //Completado
        @Test(priority = 9)
        @TmsData.Tc(tcId = 160900, tcName = "CP009_Internal Administrator general inquery", tcType = TcType.REGRESSION)
        public void tc09_userCreateUser() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().individualRegistrationAdminIntern(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }
        //Completado (Requiere actualizar el Vin, Numero de caso y Numero de Serie para ejecutarse)
        @Test(priority = 10)
        @TmsData.Tc(tcId = 3936380, tcName = "CP010_Insured general consultation", tcType = TcType.REGRESSION)
        public void tc10_insuredAdvancedConsultation() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().IndividualRegistrationAdminInsured(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }

        //Completado
        @Test(priority = 11)
        @TmsData.Tc(tcId = 3936371, tcName = "CP011_General insurance consultation", tcType = TcType.REGRESSION)
        public void tc11_userInsuranceCreateUser() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().individualRegistrationInsuranceCompanyIntern(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }

        //Completado
        @Test(priority = 12)
        @TmsData.Tc(tcId = 3936369, tcName = "CP012_General Buyer inquiry", tcType = TcType.REGRESSION)
        public void tc12_userBuyerCreateUser() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().individualRegistrationBuyerPhysical(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }
        
        //Completado
        @Test(priority = 13)
        @TmsData.Tc(tcId = 3936370, tcName = "CP013_General Moral buyer inquiry ", tcType = TcType.REGRESSION)
        public void tc13_userMoralBuyerCreateUser() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().individualRegistrationBuyerMoral(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }

        //COMPLETADO
        @Test(priority = 14)
        @TmsData.Tc(tcId = 3936372, tcName = "CP14_General Supplier Crane", tcType = TcType.REGRESSION)
        public void tc14_craneAdministratorGeneralQuery() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().individualRegistrationSupplierCrane(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }
        
        //COMPLETADO
        @Test(priority = 15)
        @TmsData.Tc(tcId = 3936379, tcName = "CP015_Master Administrator advanced Query", tcType = TcType.REGRESSION)
        public void tc15_masterAdministratorGeneralQuery() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().advancedRegistrationAdministratorMaster(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }

        //COMPLETADO
        @Test(priority = 16)
        @TmsData.Tc(tcId = 3936380, tcName = "CP016_Internal Administrator advanced Query", tcType = TcType.REGRESSION)
        public void tc16_internalAdministratorGeneralQuery() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().advancedRegistrationAdminIntern(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }
        /////////// Advanced Consultation ///////////
         //COMPLETADO (Requiere actualizar el Vin, Numero de caso y Numero de Serie para ejecutarse)
        @Test(priority = 17)
        @TmsData.Tc(tcId = 3936380, tcName = "CP017_Insured advanced Query", tcType = TcType.REGRESSION)
        public void tc17_insuredAdvancedConsultation() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().advancedRegistrationAdminInsured(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }
        //COMPLETADO
        @Test(priority = 18)
        @TmsData.Tc(tcId = 3936380, tcName = "CP018_Insurance advanced Consultation", tcType = TcType.REGRESSION)
        public void tc18_insuranceAdvancedConsultation() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().AdvancedInsuranceCompany(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }
        //COMPLETADO *(Requieren Nuevo RFC)
        @Test(priority = 19)
        @TmsData.Tc(tcId = 3936380, tcName = "CP019_Buyer Pysical advanced Query", tcType = TcType.REGRESSION)
        public void tc19_BuyerAdvancedPysicalConsultation() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().AdvancedBuyerPhysical(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }
        //COMPLETADO (Requieren Nuevo RFC Moral para ejecutarse)
        @Test(priority = 20)
        @TmsData.Tc(tcId = 3936380, tcName = "CP020_Buyer Moral advanced Query", tcType = TcType.REGRESSION)
        public void tc20_BuyerAdvancedMoralConsultation() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().AdvancedBuyerMoral(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }

        //COMPLETADO
        @Test(priority = 21)
        @TmsData.Tc(tcId = 3936372, tcName = "CP21_Advanced query Supplier Crane", tcType = TcType.REGRESSION)
        public void tc21_craneAdministratorGeneralQuery() {
                AolWebUser master = this.users.getMasterUser();
                loginPage().logIn(master);
                assertions().assertThat(adminMasterInter().advancedRegistrationSupplierCrane(master))
                                .as("USER CREATION SUCCESS").isTrue();
        }

        
}
