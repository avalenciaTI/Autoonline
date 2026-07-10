package com.solera.global.qa.template.web.behavior.pages.payments;

import static com.solera.global.qa.template.web.behavior.pages.publications.PublicationOnline.GENERIC_AUTOMATION_NAME;

import com.amazonaws.services.s3.transfer.Download;
import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.DownloadManager;
import com.solera.global.qa.template.web.behavior.pages.componentpages.ZipValidator;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.CaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.search.PaymentSearch;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@Slf4j
public class PaymentPage extends BrowserPage {

    private static final String PAYMENT_DETAIL_PAGE = "//div[text()='Informacion de comprobante de pago']";
    private static final String UPLOAD_PAYMENT_TICKETS =
            "//span[text()='Adjuntar comprobantes de pago']/ancestor::button";
    private static final String DOWNLOAD_PAYMENT_FOLDER =
            "//span[text()='Carpeta de pagos']/ancestor::button";
    private static final String PENDING_PAYMENT_VALIDATION = "//tr[@class='ant-table-row ant-table-row-level-0']"
            + "/td[contains(text(), '30,000')]/preceding-sibling::td[contains(text(), 'Por validar')]"
            + "/preceding-sibling::td/a[contains(text(), '" + GENERIC_AUTOMATION_NAME + "')]";

    private static final String COMPLETE_PAYMENT_CARDS = "//span[contains(text(), 'Comprobante de pago')]"
            + "/ancestor::div[@class='ant-card-body']";
    private static final String PAYMENT_BUTTONS = ".//descendant::button/span[contains(text(), 'Comprobante de pago')]";


    // gets only payment cards that are not verified
    private static final String NON_VERIFIED_PAYMENT_CARDS = "//div[@style='overflow-wrap: anywhere;' and "
            + "not(descendant::button[contains(@style, 'rgb(59, 181, 74)')])]/ancestor::div[@class='ant-card-body']";
    private static final String CARD_TITLE = ".//h4[contains(text(), 'Monto')]";

    /// PAYMENT DOCUMENTS
    private static final String PAYMENT_DOCUMENTS_WINDOW = "//div[@class='ant-modal-content']";
    private static final String VERIFIED_DOCUMENTS = "//button[contains(@style,'rgb(59, 181, 74)')]"
            + "/ancestor::div[@data-test='listItemComponent']/descendant::a[contains(text(),'?')]";
    private static final String PAYMENT_ONLY_DOCUMENT = "//div[@class='ant-modal preview-payment']";
    private static final String PAYMENT_WINDOW_CLOSE = "//span[@class='ant-modal-close-x']";


    private static final String DOCUMENT_LINKS = "//a[@class='ant-btn ant-btn-link']";
    private static final String PENDING_DOCUMENT_LINKS = "//div[@data-test='listItemComponent' "
            + "and not(descendant::button[contains(@style,'rgb(59, 181, 74)')])]/descendant::a";
    private static final String DOWNLOAD_BUTTON = "//button[@jest-id='DownloadFile' and @title='Descargar archivos']";
    private static final String COMMENT_APPROVAL = "//textarea[contains(@data-testid,'data_test_comments')]";
    private static final String SELECT_DROP_DOWN_LIST  = "((//*[@id='main-container']//div[contains(@class, 'ant-select-selection--single')])[2])";
    private static final String SELECT_DROP_DOWN_LIST_OPTION = "//li[contains(@class, 'ant-select-dropdown-menu-item') and text()='100']";


    // Case ID extraction from the consult results table
    private static final String CONSULT_RESULT_CASE_LINKS = "//tr[contains(@class, 'ant-table-row-level-0')]/td[3]";
    public static final String INSURANCE_COMPANY_SELECTOR_PAYMENT =
    "//div[@id='search-payments_insuranceCarrierId']//input[@type='checkbox' and @value='?']";


   

    ///
    public List<String> getConsultResultCaseIds() {
        waitForElementPresence(By.xpath(CONSULT_RESULT_CASE_LINKS), Timeouts.LOAD_RESULTS);
        List<WebElement> caseLinkElements = getElements(By.xpath(CONSULT_RESULT_CASE_LINKS));
        if (caseLinkElements.isEmpty()) {
            log.warn("No case ID links found in payment consult results");
            return new ArrayList<>();
        }
        List<String> caseIds = caseLinkElements.stream()
                .map(el -> getText(el))
                .collect(Collectors.toList());
        log.info("Found {} case IDs in consult results: {}", caseIds.size(), caseIds);
        log().image("Consult results with case IDs", takeScreenshot());
        return caseIds;
    }

    public boolean validateDownloadedPaymentFolderZip(List<String> expectedCaseIds) {
        log.info("Starting payment folder ZIP validation with {} expected case IDs", expectedCaseIds.size());
        ZipValidator zipValidator = new ZipValidator();
        boolean isValid = zipValidator.validateZipDownloadAndContents("Carpeta de pagos", 60, expectedCaseIds);
        if (isValid) {
            log.info("Payment folder ZIP validated successfully - all {} case IDs found", expectedCaseIds.size());
        } else {
            log.error("Payment folder ZIP validation failed");
        }
        return isValid;
    }

    public void searchPendingPayment() {
        PaymentSearch search = new PaymentSearch();
        search.selectCaseTypeTst(CaseType.VEHICLES);
        log().image("Selected vehicle case type", takeScreenshot());

        search.selectInsurer(Insurers.QA_TEST_AUTOMATION);
        search.selectPaymentStatus(PaymentStatus.PENDING_VALIDATION);
        search.search();
        waitForElementToBeClickable(getElement(By.xpath(UPLOAD_PAYMENT_TICKETS)), Timeouts.LOAD_RESULTS);
    }

    public boolean arePaymentValidationElements() {
        waitForElementPresence(By.xpath(PENDING_PAYMENT_VALIDATION), Timeouts.LOAD_RESULTS);
        List<WebElement> elements = getElements(By.xpath(PENDING_PAYMENT_VALIDATION));
        log().image("Payment validation elements", takeScreenshot());
        return !elements.isEmpty();
    }

    public void openAwarding() {
        click(getElement(By.xpath(PENDING_PAYMENT_VALIDATION)));
        waitForElementPresence(By.xpath(PAYMENT_DETAIL_PAGE), Timeouts.LOAD_RESULTS);
        log.info("Opened awarding from payment list");
    }

    public void getAllPaymentCards() {
        List<WebElement> paymentCards = getElements(By.xpath(COMPLETE_PAYMENT_CARDS));
        if (paymentCards.isEmpty()) {
            log.warn("No payment cards found on the page.");
        } else {
            log.info("Found {} payment cards.", paymentCards.size());
            int downloadCount = 0;

            while (paymentCards.size() > downloadCount) {
                log.info("Downloading payment card: {}", downloadCount + 1);
                WebElement card = getElements(By.xpath(COMPLETE_PAYMENT_CARDS)).get(downloadCount);
                log.info("Payment card: {}", getText(card.findElement(By.xpath(CARD_TITLE))));
                scrollTo(card);
                log.info("Scrolled to card");
                jsClick(card.findElement(By.xpath(PAYMENT_BUTTONS)));
                log.info("Clicked on payment card");
                downloadDocuments();
                downloadCount++;
            }
        }
    }

    public void verifyPayments() {
        List<WebElement> pendingPaymentCards = getElements(By.xpath(NON_VERIFIED_PAYMENT_CARDS));
        if (pendingPaymentCards.isEmpty()) {
            log.warn("No pending verified payment found on the page.");
        } else {
            while (!getElements(By.xpath(NON_VERIFIED_PAYMENT_CARDS)).isEmpty()) {
                WebElement paymentCard = getElement(By.xpath(NON_VERIFIED_PAYMENT_CARDS));
                log.info("Payment card: {}", getText(paymentCard.findElement(By.xpath(CARD_TITLE))));
                WebElement paymentButton = paymentCard.findElement(By.xpath(PAYMENT_BUTTONS));
                scrollTo(paymentButton);
                waitForElementToBeClickable(paymentButton, Timeouts.LOAD_ELEMENT);
                log().image("Payment card before click", takeScreenshot());
                jsClick(paymentButton);
                verifyDocuments();
                log().image("Payment card atfer click", takeScreenshot());
            }
            log.info("All payment cards have been processed.");
        }
    }

    public void verifyDocuments() {
        waitForElementPresence(By.xpath(PAYMENT_DOCUMENTS_WINDOW), Timeouts.LOAD_RESULTS);
        List<WebElement> documentLinks = getElements(By.xpath(PENDING_DOCUMENT_LINKS));
        if (documentLinks.isEmpty()) {
            log.warn("No document links found in the payment documents window.");
        } else {
            log.info("Document links found: " + documentLinks.size());
            log().image("Document links", takeScreenshot());
            int totalDocuments = documentLinks.size();
            int verifiedDocuments = 0;

            while (totalDocuments > verifiedDocuments) {
                log.info("link to be clicked");
                String paymentFile = getText(getElement(By.xpath(PENDING_DOCUMENT_LINKS)));
                log.info(paymentFile);
                jsClick(getElement(By.xpath(PENDING_DOCUMENT_LINKS)));

                log.info("Waiting for document window");
                waitForElementVisibility(getElement(By.xpath(PAYMENT_ONLY_DOCUMENT)), Timeouts.LOAD_RESULTS);
                log.info("Before call approval method");
                approveDocument();

                String verifiedFile  = VERIFIED_DOCUMENTS.replace("?", paymentFile);
                waitForElementPresence(By.xpath(verifiedFile), Timeouts.LOAD_ELEMENT);
                log().image("Document verificated", takeScreenshot());
                verifiedDocuments++;
            }
        }
        closeDocumentsWindow();
    }

    public void downloadDocuments() {
        waitForElementPresence(By.xpath(PAYMENT_DOCUMENTS_WINDOW), Timeouts.LOAD_RESULTS);
        List<WebElement> documentLinks = getElements(By.xpath(DOCUMENT_LINKS));

        if (documentLinks.isEmpty()) {
            log.warn("No document links found in the payment documents window.");
        } else {
            log.info("Document links found: " + documentLinks.size());
            log().image("Document links", takeScreenshot());
            int totalDocuments = documentLinks.size();
            int clickedDocuments = 0;
            do {
                log.info("In download documents loop");
                String fullFileName = getText(getElements(By.xpath(DOCUMENT_LINKS)).get(clickedDocuments));
                log.info("FileName to download {}", fullFileName);

                log.info("Download button");
                jsClick(getElements(By.xpath(DOWNLOAD_BUTTON)).get(clickedDocuments));
                waitForDownload(fullFileName);

                log().image("Document download", takeScreenshot());
                clickedDocuments++;
            } while (totalDocuments != clickedDocuments);
        }
        closeDocumentsWindow();
    }

    private void waitForDownload(String fileName) {
        String downloadDir = CommonComponents.getDownloadDir();
        String fuleNamePlusSecondExtension = fileName + ".pdf";

        File file = new File(downloadDir + '/' + fuleNamePlusSecondExtension);
        int waiting = 0;
        while (!file.exists() && waiting < 10) {
            log.info("Waiting for file to be downloaded: {}", file.getAbsolutePath());
            try {
                Thread.sleep(1000); // Wait for 1 second before checking again
            } catch (InterruptedException e) {
                log.error("Interrupted while waiting for file download", e);
            }
            waiting++;
        }
    }
    private void waitForDownload2(String fileName) {
        String downloadDir = CommonComponents.getDownloadDir();
        String fullFileName = downloadDir + '/' + fileName + ".pdf";
        String tempFileName = fullFileName + ".crdownload"; // Para Chrome, por ejemplo

        // Esperar hasta que el archivo no sea un archivo temporal y se haya descargado completamente
        int waiting = 0;
        while (true) {
            File file = new File(fullFileName);
            File tempFile = new File(tempFileName);

            if (file.exists() && !tempFile.exists()) {
                // Verificar si el tamaño del archivo se ha estabilizado
                long initialSize = file.length();
                try {
                    Thread.sleep(1000); // Esperar 1 segundo
                } catch (InterruptedException e) {
                    log.error("Interrupted while waiting for file download", e);
                }

                long newSize = file.length();
                if (initialSize == newSize) {
                    log.info("File downloaded successfully: {}", fullFileName);
                    break; // La descarga está completa
                }
            }

            // Si el archivo no existe, o el archivo temporal sigue existiendo
            log.info("Waiting for file to be downloaded: {}", fullFileName);
            if (waiting >= 10) {
                log.warn("Timed out waiting for file: {}", fullFileName);
                break; // Salir si se excede el tiempo de espera
            }

            waiting++;
            try {
                Thread.sleep(1000); // Esperar 1 segundo antes de verificar de nuevo
            } catch (InterruptedException e) {
                log.error("Interrupted while waiting for file download", e);
            }
        }
    }


    public void approveDocument() {
        log.info("Before document approval");
        sendKeys(getElement(By.xpath(COMMENT_APPROVAL)), "Comentario APPROVAL QA");
        new Buttons().clickApprovalButton();
        log().image("After document approval", takeScreenshot());
    }

    private void closeDocumentsWindow() {
        log.info("Closing validation window");
        click(getElement(By.xpath(PAYMENT_WINDOW_CLOSE)));
        log.info("Closed validation window");
        waitForElementInvisibility(getElement(By.xpath(PAYMENT_WINDOW_CLOSE)), Timeouts.LOAD_BUTTON);
        log.info("Invisible window");
    }



 public void DocumentedDownloadPaymentFolder () {
        searchStatusPayments();
        clickDownloadPaymentFolderButton();
    }

    public boolean downloadPaymentFolder() {
        searchStatusPayments();

        List<String> caseIds = getConsultResultCaseIds();
        if (caseIds.isEmpty()) {
            log.warn("No case IDs found in consult results - cannot validate ZIP");
            return false;
        }
        log.info("Case IDs found in consult results: {}", caseIds);

        clickDownloadPaymentFolderButton();

        boolean zipValid = validateDownloadedPaymentFolderZip(caseIds);
        if (zipValid) {
            log.info("Payment folder downloaded and validated successfully");
        } else {
            log.error("Payment folder ZIP validation failed");
        }
        return zipValid;
    }

    public void searchStatusPayments() {
        PaymentSearch search = new PaymentSearch();
        search.selectCaseTypeTst(CaseType.VEHICLES);
        log().image("Selected vehicle case type", takeScreenshot());

        search.selectInsurerPayment(Insurers.ALL);
        search.selectPaymentStatus(PaymentStatus.APPROVED);
        search.search();
        sleep(1500);
        selectPageSize100();
        waitForElementToBeClickable(getElement(By.xpath(DOWNLOAD_PAYMENT_FOLDER)), Timeouts.LOAD_RESULTS);
        log.info("Search for status payments completed");
    }

    private void selectPageSize100() {
        WebElement dropdown = getElement(By.xpath(SELECT_DROP_DOWN_LIST));
        waitForElementToBeClickable(dropdown, Timeouts.LOAD_ELEMENT);
        jsClick(dropdown);
        sleep(1500);
        jsClick(getElement(By.xpath(SELECT_DROP_DOWN_LIST_OPTION)));
        log.info("Selected 100 items per page");
    }

    public void clickDownloadPaymentFolderButton() {
        jsClick(getElement(By.xpath(DOWNLOAD_PAYMENT_FOLDER)));
        log.info("Clicked on download payment folder button");
    }


    public void selectInsurerPayment(Insurers insurer) {
        String insurerLocator = INSURANCE_COMPANY_SELECTOR_PAYMENT.replace("?", insurer.getInsurer());
        WebElement insurerElement = getBrowser().getDriver().findElement(By.xpath(insurerLocator));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", insurerElement);
        log().image("Insurer selected", takeScreenshot());
    }

     

}