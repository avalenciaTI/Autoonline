package com.solera.global.qa.autoonline.web.test.cases.login;


import com.solera.global.qa.taf.core.data.annotations.TmsData;
import com.solera.global.qa.taf.core.data.enums.TcType;
import com.solera.global.qa.template.web.behavior.data.tools.IdGenerator;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.WebTestBase;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CompleteWebElement;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Slf4j
public class TestPublication extends WebTestBase {
    // CASE STATUS
    //En proceso        -> just created case
    //En validacion de documentos   -> caso vehiculo: al crear el caso se adjuntan documentos y al aceptar
    //*Documentado       -> al documentar / o si no se adjudica
    //Pre-publicado     -> al adjuntarse a pub

    //publicado         -> al activar los casos de la publicacion
    //*Documentado       -> al documentar / o si no se adjudica
    //por adjudicar     -> hubo ofertas pero el admin no ha adjudicado al comprador
    //Por validar adjudicacion  -> Cuando un admin interno adjudica una unidad y le cambia el costo de oferta
    // o administrativo y el admin master lo valida
    //Adjudicado        -> al adjudicar
    //En proceso de pago    -> cuando se comienzan a subir comprobantes de pago o validar comprobantes de pago
    //Pagado            -> cuando se validan todos los comprobantes
    //Entregado         -> cuando el auto esta en ubicación diferente a taller u otro y esta concluido el proceso
    // de publicacion
    //      en detalle de pago -> upload documents
    //Cerrado

    private static List<CompleteWebElement> values2Validate;
    private static String vin;
    private static String secondVin;
    private static String publicationId;



    @BeforeClass
    public static void initializeVin() {
        //TestAutomation DIV  + ID
        vin = IdGenerator.getNewVin();
        publicationId = IdGenerator.getNewPublicationId();
    }


    public void prepareCase(String currentVin, AolWebUser user) throws Exception {
        assertions().assertThat(photos().vehicleLoadImages(user, currentVin))
                .as("CASE LOAD IMAGES SUCCESS").isTrue();
        assertions().assertThat(documents().vehicleLoadFiles(user, currentVin))
                .as("FILES LOADED SUCCESS").isTrue();
        assertions().assertThat(documents().vehicleValidateAllFiles(currentVin))
                .as("FILES VALIDATED SUCCESS").isTrue();
    }

    @Test(priority = 20)
    public void tc0_createPrerequisite0() throws Exception {
        initializeVin();
        AolWebUser master = users.getMasterUser();
        loginPage.logIn(master);

        assertions().assertThat(caseIndividualRegistration().vehicleIndividualRegistration(vin, caseData))
                .as("CASE 1 CREATION SUCCESS").isTrue();
        prepareCase(vin, master);
    }

    @Test(priority = 21)
    public void tc1_createPrerequisite1() throws Exception {
        AolWebUser master = users.getMasterUser();
        loginPage.logIn(master);
        secondVin = IdGenerator.getNewVin();
        log.info("VIN COMPARATION vin: {}, second vin:  {}", vin, secondVin);
        assertions().assertThat(caseIndividualRegistration().vehicleIndividualRegistration(secondVin, caseData))
                .as("CASE 2 CREATION SUCCESS").isTrue();
        prepareCase(secondVin, master);
    }

    @Test(priority = 22)
    @TmsData.Tc(
            tcId = 161014,
            tcName = "CP012_Creation of publication",
            tcType = TcType.REGRESSION
    )
    public void tc2_publicationCreation() throws Exception {
        loginPage.logIn(users.getMasterUser());////
        log.info("vin1 {}", vin);
        log.info("vin2 {}", secondVin);
        log.info("publicationId {}", publicationId);
        values2Validate = publicationCreation().createPublication(publicationId);
        assertions().assertThat(publicationCreation().validatePublication(publicationId, values2Validate))
                .as("PUBLICATION CREATION SUCCESS").isTrue();

    }

    @Test(priority = 23)
    @TmsData.Tc(
            tcId = 161015,
            tcName = "CP013_Activate publication",
            tcType = TcType.REGRESSION
    )
    public void tc3_publicationActivation() throws InterruptedException {
        AolWebUser master = this.users.getMasterUser();
        loginPage.logIn(master);
        log.info("publicationId {}", publicationId);
        assertions().assertThat(publicationCreation().activatePublication(publicationId, vin, secondVin))
                .as("PUBLICATION CREATION SUCCESS").isTrue();
    }


    @Test(priority = 24)
    @TmsData.Tc(
            tcId = 160963,
            tcName = "CP078_Display of publications online",
            tcType = TcType.REGRESSION
    )
    public void tc4_consultPublicationOnline() throws InterruptedException {
        AolWebUser buyer = this.users.getPhysicalBuyerUser();
        loginPage().logIn(buyer);
        assertions().assertThat(publicationCreation().openPublication(buyer, "En línea"))
                .as("PUBLICATION CONSULT SUCCESS").isTrue();
    }

    @Test(priority = 25)
    @TmsData.Tc(
            tcId = 70011,
            tcName = "CP079_Display of publications archived.",
            tcType = TcType.REGRESSION
    )
    public void tc5_consultArchivedPublication() throws Exception {
        AolWebUser buyer = this.users.getPhysicalBuyerUser();
        loginPage().logIn(buyer);
        assertions().assertThat(publicationCreation().openPublication(buyer, "Archivadas"))
                .as("PUBLICATION CONSULT SUCCESS").isTrue();

    }

    @Test(priority = 26)
    @TmsData.Tc(
            tcId = 160967,
            tcName = "CP091_favorite photographs at auction general section",
            tcType = TcType.REGRESSION
    )
    public void tc6_viewPhotosGeneralSection() throws Exception {
        AolWebUser buyer = this.users.getPhysicalBuyerUser();
        loginPage().logIn(buyer);
        assertions().assertThat(publicationCreation().openPublication(buyer, "En línea"))
                .as("PUBLICATION CONSULT SUCCESS").isTrue();

        assertions().assertThat(publicationOnline().imagesConsultGeneral())
                .as("IMAGES MARKED AS FAVORITES SUCCESS").isTrue();

    }

    @Test(priority = 27)
    @TmsData.Tc(
            tcId = 160968,
            tcName = "CP092_favorite photographs at auction detail section",
            tcType = TcType.REGRESSION
    )
    public void tc7_viewPhotosDetailedSection() throws Exception {
        AolWebUser buyer = this.users.getPhysicalBuyerUser();
        loginPage().logIn(buyer);
        assertions().assertThat(publicationCreation().openPublication(buyer, "En línea"))
                .as("PUBLICATION CONSULT SUCCESS").isTrue();

        assertions().assertThat(publicationOnline().imagesConsultDetailed())
                .as("IMAGES MARKED AS FAVORITES SUCCESS").isTrue();

    }

    @Test(priority = 28)
    @TmsData.Tc(
            tcId = 21848008,
            tcName = "CP014_Bid on active publication",
            tcType = TcType.REGRESSION
    )
    public void tc8_bidPublicationOnline() throws Exception {
        AolWebUser buyer = this.users.getPhysicalBuyerUser();
        loginPage().logIn(buyer);
        assertions().assertThat(publicationCreation().openPublication(buyer, "En línea"))
                .as("PUBLICATION CONSULT SUCCESS").isTrue();

        assertions().assertThat(publicationOnline().bidOnPublication())
                .as("BID ON PUBLICATION SUCCESS").isTrue();

        assertions().assertThat(publicationOnline().bidOnPublication())
                .as("BID ON PUBLICATION SUCCESS").isTrue();
    }
}
