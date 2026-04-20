package com.solera.global.qa.template.web.behavior.pages.publications;

import static com.solera.global.qa.template.web.behavior.pages.publications.PublicationOnline.GENERIC_AUTOMATION_NAME;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.data.types.Awarding;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Slf4j
public class PublicationAdjudication extends BrowserPage {

    private static final String PUBLICATIONS_PAGE_TITLE = "//h3[@class='ant-typography text-left' "
            + "and text()='Consultar adjudicaciones por unidad']";

    private static final String INSURER_NAME = "//h3[text()='Mercado (Aseguradora)']/following-sibling::h3";
    private static final String PUBLICATION_NAME = "//h3[text()='Nombre de la publicación']/following-sibling::h3";
    private static final String SINISTER = "//h3[text()='Siniestro']/following-sibling::h3";
    private static final String STATUS = "//h3[text()='Estatus del caso']/following-sibling::h3";
    private static final String OFFERED_AMOUNT = "//div[@class='amount-text text-center' and contains(text(), '$')]";

    private static final String ADJUDICATION_BUTTON = "//a[@class='ant-btn ant-btn-default ant-btn-round btn-link ' "
            + "and text()='Adjudicar']";
    private static final String DETAILS_BUTTON = "//a[@class='ant-btn ant-btn-default ant-btn-round btn-link ' "
            + "and text()='Ver detalle']";

    private static final String DO_NOT_ADJUDICATE = "//span[text()='No adjudicar']/parent::button[@type='button']";

    private static final String NOT_ADJUCICATION_ALERT = "//h4[@class='ant-typography modal-title']";
    private static final String NOT_ADJUCICATION_ALERT_INPUT = "//input[@id='not_award_modal_comment']";

    private static final String NOTIFICATION_MESSAGE =
            "//div[@class='ant-notification-notice ant-notification-notice-closable "
                    + "notification-autoonline notification-success']";
    //La opción no adjudicar caso se realizó correctamente



    public boolean isOnAdjudicationPage() {
        log.info("Verifying if user is on Adjudication page");
        return waitForElementPresence(By.xpath(ADJUDICATION_BUTTON), Timeouts.LOAD_BUTTON);
    }

    public String verifyInsurerName(String insurer) {
        WebElement insurerElement = getElement(By.xpath(INSURER_NAME));
        assertions().assertThat(getText(insurerElement)).as("Verify Insurer name").isEqualTo(insurer);
        return getText(insurerElement);
    }

    public String verifyPublicationName(String publication) {
        WebElement publicationElement = getElement(By.xpath(PUBLICATION_NAME));
        assertions().assertThat(getText(publicationElement)).as("Verify Publication name")
                .contains(publication);
        return getText(publicationElement);
    }

    public String verifySinister(String sinister) {
        WebElement sinisterElement = getElement(By.xpath(SINISTER));
        assertions().assertThat(getText(sinisterElement)).as("Verify Sinister").contains(sinister);
        return getText(sinisterElement);
    }

    public String verifyStatus(String status) {
        waitForElementPresence(getElement(By.xpath(STATUS)), Timeouts.LOAD_HEAVY_RESULTS);
        WebElement statusElement = getElement(By.xpath(STATUS));
        assertions().assertThat(getText(statusElement)).as("Verify Status").isEqualTo(status);
        return getText(statusElement);
    }

    public String verifyOfferedAmount(String amount) {
        WebElement amountElement = getElement(By.xpath(OFFERED_AMOUNT));
        assertions().assertThat(getText(amountElement))
                .as("Verify Offered Amount")
                .isEqualToNormalizingPunctuationAndWhitespace(amount);
        return getText(amountElement);
    }


    public void clickAdjudicationButton() {
        log.info("In adjudication page, clicking on adjudication button");

        click(getElement(By.xpath(ADJUDICATION_BUTTON)));
    }

    public Awarding registerAdjudication(String paymentReference, String adminPaymentRefInsurer) {
        log.info("Registering adjudication");
        final var awarding = verifyAdjudication();
        clickAdjudicationButton();
        log().image("Adjudication button clicked", takeScreenshot());
        var registration = new AdjudicationRegister();
        registration.isRegistrationPageShown();
        log().image("Adjudication Registration Page", takeScreenshot());
        registration.registerAdjudication(paymentReference, adminPaymentRefInsurer);
        //verifyStatus("Adjudicado");
        return awarding;
    }

    public void doNotAdjudicate() {
        log.info("Do not register adjudication");
        var awarding = verifyAdjudication();

        click(getElement(By.xpath(DO_NOT_ADJUDICATE)));
        sleep(1000);

        WebElement alertTitle = getElement(By.xpath(NOT_ADJUCICATION_ALERT));
        assertions().assertThat(getText(alertTitle)).as("Alert title verifying")
                .contains("¿Está seguro que desea cancelar la adjudicación del caso?");

        WebElement justification = getElement(By.xpath(NOT_ADJUCICATION_ALERT_INPUT));
        sendKeys(justification, "TEST AUTOMATION");
        log().image("Do not adjudicate button clicked", takeScreenshot());

        new Buttons().clickAcceptButton();

        verifyNotAdjudicateNotification();
    }

    public void doNotAdjudicate(String sinister) {
        log.info("Do not register adjudication");
        verifyPublicationDetails(sinister);

        click(getElement(By.xpath(DO_NOT_ADJUDICATE)));
        sleep(1000);

        WebElement alertTitle = getElement(By.xpath(NOT_ADJUCICATION_ALERT));
        assertions().assertThat(getText(alertTitle)).as("Alert title verifying")
                .contains("¿Está seguro que desea cancelar la adjudicación del caso?");

        WebElement justification = getElement(By.xpath(NOT_ADJUCICATION_ALERT_INPUT));
        sendKeys(justification, "TEST AUTOMATION");
        log().image("Do not adjudicate button clicked", takeScreenshot());

        new Buttons().clickAcceptButton();

        verifyNotAdjudicateNotification();
    }

    public void verifyNotAdjudicateNotification() {
        String expectedNotificationMessage = "La opción no adjudicar caso se realizó correctamente";
        String notificationMessage = "";
        int count = 0;

        while (!notificationMessage.contains(expectedNotificationMessage) && count < 5) {
            sleep(1000);
            try {
                notificationMessage = getText(getElement(By.xpath(NOTIFICATION_MESSAGE)));
                log.info("Notification message: {}  counter: {}", notificationMessage, count);
                break;
            } catch (Exception e) {
                log.warn("Waiting getting notification message: {}", e.getLocalizedMessage());
            }
            count++;
        }


        assertions().assertThat(notificationMessage).as("Notification message verifying")
                .contains(expectedNotificationMessage);
        log().image("Notification message", takeScreenshot());
    }


    public Awarding verifyAdjudication() {
        isOnAdjudicationPage();
        Awarding awarding = new Awarding();
        log().image("Adjudication page", takeScreenshot());
        awarding.setInsuranceCompany(verifyInsurerName("QA TESTS AUTOMATION"));
        awarding.setPublicationName(verifyPublicationName("TESTAUTOMATION DIV"));
        awarding.setSinister(verifySinister(GENERIC_AUTOMATION_NAME));
        awarding.setCaseStatus(verifyStatus("Por adjudicar"));
        awarding.setBidAmount(verifyOfferedAmount("30000000"));
        return awarding;
    }

    public void verifyPublicationDetails(String sinister) {
        //isOnAdjudicationPage();
        log().image("Adjudication page", takeScreenshot());
        verifyInsurerName("QA TESTS AUTOMATION");
        verifyPublicationName("TESTAUTOMATION DIV");
        verifySinister(sinister);
        verifyStatus("Por adjudicar");
    }





}
