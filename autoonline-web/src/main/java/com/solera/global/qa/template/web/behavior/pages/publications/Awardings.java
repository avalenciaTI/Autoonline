package com.solera.global.qa.template.web.behavior.pages.publications;

import static com.solera.global.qa.template.web.behavior.pages.publications.PublicationOnline.GENERIC_AUTOMATION_NAME;

import com.solera.global.qa.taf.web.exceptions.ElementNotFoundException;
import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.exceptions.AutomatedPublicationNotFound;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.data.types.Awarding;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.SearchType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.AdjudicationStatus;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.CaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.PaymentTicketType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.WorkFlowElements;
import com.solera.global.qa.template.web.behavior.pages.componentpages.search.AdjudicationSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.submenu.AwardingsOptions;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import com.solera.global.qa.template.web.behavior.pages.payments.Insurers;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Slf4j
public class Awardings extends BrowserPage {

    private static final String PUBLICATION_PAGE_TITLE = "//h3[text()='Publicaciones en línea']";
    private static final String RESULTS_IN_PAGINATION =
            "//li[@class='ant-pagination-total-text' and not(text() = '0 - 0 de 0 elementos')]";
    private static final String RESULTS_TABLE = "//div[@class='ant-table-scroll']//td[contains(text(),'"
            + GENERIC_AUTOMATION_NAME + "')]/preceding-sibling::td[@class='ant-table-row-cell-break-word']//a";
    private static final String AUTOMATED_ADWARDINGS = "//div[@class='ant-table-scroll']"
            + "//td[contains(text(),'" + GENERIC_AUTOMATION_NAME + "')]"
            + "/preceding-sibling::td[@class='ant-table-row-cell-break-word']//a";
    private static final String BID_PAYMENT_TIKET = "//h4[contains(text(),'Monto de oferta')]"
            + "/ancestor::div[@class='ant-card-body']/descendant::button";
    private static final String CARDS_AND_BUTTONS = "//h4[text()='?']/ancestor::div[@class='ant-card-body']"
            + "/descendant::button";
    private static final String UPLOAD_BUTTON =  "//input[contains(@accept,'pdf') and @type='file']";
    private static final String CLOSE_ATTACHMENT_WINDOW = "//i[@class='anticon anticon-close ant-modal-close-icon']";

    private static final String SEND_PAYMENT_TO_VALIDATION = "//span[text()='Enviar pago a validación']/parent::button";
    //send payment for validation buttons
    private static final String SEND_PAYMENT_CONFIRMATION = "//span[@class='ant-modal-confirm-title']"
            + "//h4[@class='ant-typography']";
    private static final String CONFIRM_BUTTON = "//button[@id='ok-delete-bid']";
    private static final String NOTIFICATION_TEXT =
            "El envío a validación de los archivos de referencia de pago se realizó correctamente";
    private static final String PAYMENT_STATUS =
            "//th[text()='Estatus de pago']/following-sibling::td[contains(text(),'Por validar')]";
    private static final String CASE_STATUS =
            "//th[text()='Estatus del caso']/following-sibling::td[contains(text(),'En proceso de pago')]";
    private static final String SENT_REMINDER = "//div[@class='ant-notification-notice-message' and "
            + "contains(text(), 'Recuerde que debe enviar sus comprobantes de pago')]";
    private static final String SEND_PAYMENT_CONFIRMATION_MESSAGE =
            "¿Está seguro que desea enviar los comprobantes de pago a validación?";
    private static final String TICKETS_FOLDER = "paymentTickets/";
    private static final String NEXT_PAGE = "//li[@title='Página siguiente' and @aria-disabled='true']";

    private static final String AUTOMATED_ADJUDICATION_NOT_FOUND = "No automated adjudications found";
    private static final String ADJUDICATION_RESULTS = "Search adjudication results";
    private static final String DELETE_FILE_BUTTON = "//button[@jest-id='DeleteFile']";


    public Awarding adjudicatePublication() {
        searchAwardingByPublicationPartialName();

        // to open publication, the sinister num is not listed
        // for this case we need to open the only one publication listed as result
        // ENSURE YOU SEARCH CONTAINS "QA TESTS AUTOMATION" AS FILTER

        PublicationAdjudication adjudication = new PublicationAdjudication();
        var awarding = adjudication.registerAdjudication("PAY REF 12345", "ADMIN REF 12345");

        //VALIDATE PUBLICATION IS NOT ONLINE
        //VALIDATE PUBLICATION AS ARCHIVED
        //verify adjudication
        return awarding;
    }

    public void doNotAdjudicatePublication() {
        searchAwardingByPublicationPartialName();

        PublicationAdjudication adjudication = new PublicationAdjudication();
        adjudication.doNotAdjudicate();
    }

    private void searchAwardingByPublicationPartialName() {
        new MenuPage().clickAwardings(AwardingsOptions.CONSULT_AWARDINGS);
        AdjudicationSearch search = new AdjudicationSearch();
        search.swapAdvancedSearch(SearchType.ADVANCED_SEARCH);
        search.selectCaseType(CaseType.VEHICLES, WorkFlowElements.AWARDING);
        search.selectViewType("Unitaria");
        search.selectInsurer(Insurers.QA_TEST_AUTOMATION);
        search.selectAdjudicationStatus(AdjudicationStatus.TO_BE_ADJUDICATED);
        log().image("Status selected", takeScreenshot());
        search.setPublicationName("TESTAUTOMATION DIV");
        log().image("Search parameters", takeScreenshot());
        search.search();
        sleep(5000);
        log().image(ADJUDICATION_RESULTS, takeScreenshot());
    }

    private void searchAwardingBySinister(String publication) {
        new MenuPage().clickAwardings(AwardingsOptions.CONSULT_AWARDINGS);
        AdjudicationSearch search = new AdjudicationSearch();
        search.swapAdvancedSearch(SearchType.ADVANCED_SEARCH);
        search.selectCaseType(CaseType.VEHICLES, WorkFlowElements.AWARDING);
        search.selectViewType("Unitaria");
        search.selectInsurer(Insurers.QA_TEST_AUTOMATION);

        search.selectAdjudicationStatus(AdjudicationStatus.TO_BE_ADJUDICATED);
        search.setPublicationName(publication);

        log().image("Search parameters", takeScreenshot());
        search.search();
        sleep(Timeouts.LOAD_ELEMENT);
        log().image(ADJUDICATION_RESULTS, takeScreenshot());
    }

    public void openAwardingBySinister(AolWebUser buyer) throws AutomatedPublicationNotFound {
        log.info("Opening awarding by Sinister");
        searchAwardings("Pendiente por adjuntar", buyer);
        click(searchAutomatedAwarding());
        log().image("Open awarding by Sinister", takeScreenshot());
    }

    public void sendDocuments() {
        log.info("Sending documents for validation");
        log().image("Before sending documents", takeScreenshot());

        waitForReminderGoesOff();
        waitForElementToBeClickable(getElement(By.xpath(SEND_PAYMENT_TO_VALIDATION)), Timeouts.LOAD_HEAVY_PAGE);
        log().image("Sending documents", takeScreenshot());
        click(getElement(By.xpath(SEND_PAYMENT_TO_VALIDATION)));
        log().image("Sending documents", takeScreenshot());

        verifyElementStatus(SEND_PAYMENT_CONFIRMATION, SEND_PAYMENT_CONFIRMATION_MESSAGE,"Send payment confirmation");
        log().image("Send payment confirmation", takeScreenshot());
        click(getElement(By.xpath(CONFIRM_BUTTON)));

        String notificationMessage = new CommonComponents().getNotificationMessage();
        assertions().assertThat(notificationMessage)
                .as("Validating notification message")
                .contains(NOTIFICATION_TEXT);

        log().image("Notification message", takeScreenshot());
        waitForElementVisibility(getElement(By.xpath(PAYMENT_STATUS)), Timeouts.STATUS_VALIDATION);
        verifyElementStatus(PAYMENT_STATUS, "Por validar", "Validating payment status");
        verifyElementStatus(CASE_STATUS, "En proceso de pago", "Validating case status");
        log().image("After sending documents", takeScreenshot());
    }

    public void verifyElementStatus(String xpath, String expected, String description) {
        assertions().assertThat(getText(getElement(By.xpath(xpath))))
                .as(description)
                .contains(expected);
    }

    public void waitForReminderGoesOff() {
        waitForPageToLoad();
        log().image("Before sent reminder", takeScreenshot());
        try {
            waitForElementVisibility(getElement(By.xpath(SENT_REMINDER)), Timeouts.LOAD_HEAVY_PAGE);
            log().image("After sent reminder", takeScreenshot());
            log.info("Reminder message: {}", getText(getElement(By.xpath(SENT_REMINDER))));
            waitForElementInvisibility(getElement(By.xpath(SENT_REMINDER)), Timeouts.LOAD_HEAVY_PAGE);
        } catch (Exception e) {
            log.warn("Reminder message not found");
        }

        log().image("Finally sent reminder", takeScreenshot());
        log.info("Reminder goes off");
    }

    public void searchAwardings(String awardingStatus, AolWebUser user) throws AutomatedPublicationNotFound {
        log.info("Searching for awardings");
        new MenuPage().clickAwardings(AwardingsOptions.CONSULT_AWARDINGS, user.getRole());
        log.info("Returning to searchAwardings method");

        AdjudicationSearch search = new AdjudicationSearch();
        log().image("Before select satus", takeScreenshot());
        search.selectAwardingStatus(awardingStatus, user.getRole());
        log().image("Status selected", takeScreenshot());
        search.selectInsurer(Insurers.QA_TEST_AUTOMATION);

        log.info("Selected insurer");
        new Buttons().clickSearchBtn();
        try {
            waitForElementPresence(getElement(By.xpath(RESULTS_IN_PAGINATION)), Timeouts.LOAD_PAGE);
        } catch (NoSuchElementException | ElementNotFoundException ex) {
            log.info("No results found");
            throw new AutomatedPublicationNotFound(AUTOMATED_ADJUDICATION_NOT_FOUND);
        }
        log().image(ADJUDICATION_RESULTS, takeScreenshot());
    }

    public WebElement searchAutomatedAwarding() throws AutomatedPublicationNotFound {
        List<WebElement> results = getElements(By.xpath(AUTOMATED_ADWARDINGS));
        log.info("Results: {}", results.size());
        if (results.isEmpty()) {
            if (!waitForElementToBeClickable(getElement(By.xpath(NEXT_PAGE)), Timeouts.LOAD_ELEMENT)) {
                click(getElement(By.xpath(NEXT_PAGE)));
                log.info("Waiting for next page");
                waitForElementVisibility(getElement(By.xpath(PUBLICATION_PAGE_TITLE)), Timeouts.LOAD_HEAVY_PAGE);
                log.info("new page loads");
                sleep(7000);
                results = getElements(By.xpath(AUTOMATED_ADWARDINGS));
                return results.get(0);
            } else {
                throw new AutomatedPublicationNotFound(AUTOMATED_ADJUDICATION_NOT_FOUND);
            }
        } else {
            log.info("Automated case found: {}", getText(results.get(0)));
            return results.get(0);
        }
    }

    public void loadPaymentDocuments() {
        log.info("Uploading payment documents");
        WebElement paymentTicket = getElement(By.xpath(BID_PAYMENT_TIKET));

        String baseFolder = new CommonComponents().getAttachmentsFolderFilePath();
        for (PaymentTicketType paymentType: PaymentTicketType.values()) {
            //opening upload window
            String cardAndButton = CARDS_AND_BUTTONS.replace("?", paymentType.getPaymentType());
            click(getElement(By.xpath(cardAndButton)));

            String fullPath = baseFolder + TICKETS_FOLDER + paymentType.getFileName();
            log.info("Cards and buttons xpath: {}", CARDS_AND_BUTTONS);
            log.info("file name: {}", paymentType.getFileName());
            log.info("full path: {}", fullPath);

            //get upload button
            new CommonComponents().uploadFileToWebApp(getElement(By.xpath(UPLOAD_BUTTON)), fullPath);

            //wait for document deletion button
            waitForElementToBeClickable(getElement(By.xpath(DELETE_FILE_BUTTON)), Timeouts.LOAD_ELEMENT);

            log().image("Upload button", takeScreenshot());
            click(getElement(By.xpath(CLOSE_ATTACHMENT_WINDOW)));
        }
        log().image("Uploaded payment documents", takeScreenshot());
    }

    public void registerUploadedFiles() {
        String attachmentsListed = "//div[@data-test='listItemComponent']/a";
        List<WebElement> attachments = getElements(By.xpath(attachmentsListed));

        if (!attachments.isEmpty()) {
            for (WebElement attachment: attachments) {
                log.info("Attachment uploaded: {}", getText(attachment));
            }
            log().image("No attachments found after upload", takeScreenshot());
        }
    }

    public String openAwardings(AolWebUser buyer) throws AutomatedPublicationNotFound {
        searchAwardings("Pendiente por adjuntar", buyer);
        //wait to load results table
        try {
            waitForElementPresence(getElement(By.xpath(RESULTS_IN_PAGINATION)), Timeouts.LOADER);
        } catch (NoSuchElementException | ElementNotFoundException ex) {
            log.info("No results found");
            throw new AutomatedPublicationNotFound(AUTOMATED_ADJUDICATION_NOT_FOUND);
        }

        log().image("Search adjudication results after wait", takeScreenshot());

        click(searchAutomatedAwarding());
        String sinisterElement = "//th[text()='Siniestro']/following-sibling::td[contains(text(),'"
                + GENERIC_AUTOMATION_NAME + "')]";
        String sinister = getText(getElement(By.xpath(sinisterElement)));
        log.info("Sinister: {}", sinister);
        return sinister;
    }
}
