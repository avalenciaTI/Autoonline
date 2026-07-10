package com.solera.global.qa.template.web.behavior.pages.publications;

import static com.solera.global.qa.template.web.behavior.pages.publications.PublicationOnline.GENERIC_AUTOMATION_NAME;

import com.solera.global.qa.taf.web.exceptions.ElementNotFoundException;
import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.AolWebPropertiesReader;
import com.solera.global.qa.template.web.behavior.data.exceptions.AutomatedPublicationNotFound;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.data.types.Awarding;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.DownloadManager;
import com.solera.global.qa.template.web.behavior.pages.componentpages.FilesCompare;
import com.solera.global.qa.template.web.behavior.pages.componentpages.PublicationSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.SearchType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.AdjudicationStatus;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.CaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.PaymentTicketType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.Reports;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.WorkFlowElements;
import com.solera.global.qa.template.web.behavior.pages.componentpages.search.AdjudicationSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.search.PaymentSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.submenu.AwardingsOptions;
import com.solera.global.qa.template.web.behavior.pages.componentpages.submenu.PublicationsOptions;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import com.solera.global.qa.template.web.behavior.pages.payments.Insurers;
import com.solera.global.qa.template.web.behavior.pages.payments.PaymentStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
    private static final String ATTACH_BUTTON = "//span[text()='Adjuntar documento']/parent::button";

    // Bulk references registration locators
    private static final String XLSX_FILE_INPUT = "//input[@type='file']";
    private static final String SEND_BULK_REFERENCE_BUTTON = "//span[text()='Enviar']/parent::button[@type='submit']";
    private static final String RESUME_VIEW_SECTION =
            "//div[contains(@class, 'ant-modal-content')][.//h4[contains(., 'Resumen del archivo')]"
            + " and .//div[contains(@class, 'ant-modal-body') and contains(., '0 registros tienen errores')]]";
             private static final String RESUME_VIEW_SECTION2 =
            "//div[contains(@class, 'ant-modal-content')][.//div[contains(@class, 'ant-modal-body') and contains(., '0 registros tienen errores')]]";
    private static final String RESUME_TABLE_ROWS = "//table[contains(@class,'ant-table')]//tr[contains(@class,'ant-table-row')]";
    private static final String SUCCESSFUL_UPLOAD_FILE = "//button[contains(@class, 'ant-btn') and .//i[contains(@class, 'anticon-check')]]";
    public static final String PAPER_CLIP_BUTTON = "//input[contains(@accept,'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')]";
    private static final String RESULTS_REPORT = "//button[@jest-id='reportButton' and "
            + "span[text()='Reporte de resultados']]";
    public static final String GENERAL_SEARCH_CRITERIA = "//tr[contains(@class, 'ant-table-row')]//td[normalize-space()='?' or .//a[normalize-space()='?']]";
    private static final String SEARCH_PAYMENT_SINISTER = "//a[normalize-space(.)='?']";
    private static String publicationId;
    private static String caseNumber;
    private static String siniestroNIU;
    private static String publicationId2 = "QAT2606001470";
    private static String sinesterNIU = "TEST VEH 1JKTS178111214975";

    private final FilesCompare filesActions = new FilesCompare();
    private final DownloadManager downloadManager = new DownloadManager();
    private static List<String> resumeFechas = new ArrayList<>();
    private static List<String> resumeHoras = new ArrayList<>();
    private static List<String> resumeMontos = new ArrayList<>();
    private static List<String> resumeCostoAseguradora = new ArrayList<>();
    private static List<String> resumeOfertas = new ArrayList<>();
    
    // Data read from the uploaded Excel file columns for validation against frontend
    private static List<String> fileOfertas = new ArrayList<>();
    private static List<String> fileCostoAdministrativo = new ArrayList<>();
    private static List<String> fileCostoAseguradora = new ArrayList<>();
    private static List<String> fileAccion = new ArrayList<>();


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
        new MenuPage().clickAwardingss(AwardingsOptions.CONSULT_AWARDINGS, user.getRole());
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


    public void bulkVehicleReferencesRegistration() {
        String xlsxFolderName = "bulkReferences";
        navigateToBulkReferencesPage();
        uploadBulkFiles(xlsxFolderName);
        validateUploadedFileAgainstFrontend(xlsxFolderName);
        submitBulkRegistration();
        validateResumeViewData();
        continueBulkProcess();
        downloadReportResult();
    }

    private void navigateToBulkReferencesPage() {
        log.info("Navigating to bulk references page");
        new MenuPage().clickAwardings(AwardingsOptions.MASSIVE_REFERENCE_AWARDINGS);
        waitForElementPresence(getElement(By.xpath(PAPER_CLIP_BUTTON)), Timeouts.LOAD_ELEMENT);

        AdjudicationSearch search = new AdjudicationSearch();
        search.selectCaseTypeTst(CaseType.VEHICLES);
        waitForElementToBeClickable(getElement(By.xpath(ATTACH_BUTTON)), Timeouts.LOAD_ELEMENT);
        log().image("Bulk reference page loaded", takeScreenshot());
    }

    private void uploadBulkFiles(String xlsxFolderName) {
        log.info("Uploading bulk files");
        String xlsxFolder = xlsxFolderName + "/";
        String attachmentsFolder = AolWebPropertiesReader.getAttachmentsFolder(xlsxFolder);
        log.info("XLSX ATTACHMENTS FOLDER: {}", attachmentsFolder);

        File folder = new File(attachmentsFolder);
        File[] files = folder.listFiles();
        int numFiles = files.length;
        log.info("ATTACH FILES FROM FOLDER, files in folder: {}", numFiles);
        StringBuilder files2Send = new StringBuilder();
        String basePath = folder.getAbsolutePath() + File.separator;

        for (int i = 0; i < numFiles; i++) {
            String filePath = basePath + files[i].getName();
            log.info("FILE PATH: {}", filePath);

            File file = new File(filePath);
            if (file.exists()) {
                log.info("FILE EXISTS: {}", filePath);
            }
            files2Send.append(filePath);
            if (i < numFiles - 1) {
                files2Send.append("\n");
            }
        }

        log.info("TotalFiles: {}", files2Send.toString());
        log.info("Sending files to hidden file input");
        waitForElementPresence(By.xpath(PAPER_CLIP_BUTTON), Timeouts.LOAD_ELEMENT);
        sleep(500);
        getElement(By.xpath(PAPER_CLIP_BUTTON)).sendKeys(files2Send.toString());
        log.info("File uploaded");
    }

    private void validateUploadedFileAgainstFrontend(String xlsxFolderName) {
        log.info("Reading uploaded Excel file to extract column data for frontend validation");
        String xlsxFolder = xlsxFolderName + "/";
        String attachmentsFolder = AolWebPropertiesReader.getAttachmentsFolder(xlsxFolder);
        log.info("XLSX ATTACHMENTS FOLDER: {}", attachmentsFolder);

        File folder = new File(attachmentsFolder);
        File[] files = folder.listFiles();
        
        if (files == null || files.length == 0) {
            log.warn("No files found in bulk folder");
            return;
        }

        // Read the first Excel file found
        File excelFile = files[0];
        log.info("Reading file: {}", excelFile.getAbsolutePath());

        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            // Column indices (0-based):
            // I = index 8 (OFERTA)
            // M = index 12 (COSTO ADMINISTRATIVO)
            // O = index 14 (COSTO ASEGURADORA)
            // R = index 17 (ACCIÓN)
            int ofertaCol = 8;       // Column I
            int costoAdmCol = 12;    // Column M
            int costoAsgCol = 14;    // Column O
            int accionCol = 17;      // Column R

            // Try to find columns by header name first
            if (headerRow != null) {
                for (Cell cell : headerRow) {
                    String headerValue = getCellValueAsString(cell).trim().toUpperCase();
                    if (headerValue.contains("OFERTA")) {
                        ofertaCol = cell.getColumnIndex();
                        log.info("Found 'OFERTA' at column index {}", ofertaCol);
                    } else if (headerValue.contains("COSTO ADMINISTRATIVO")) {
                        costoAdmCol = cell.getColumnIndex();
                        log.info("Found 'COSTO ADMINISTRATIVO' at column index {}", costoAdmCol);
                    } else if (headerValue.contains("COSTO ASEGURADORA")) {
                        costoAsgCol = cell.getColumnIndex();
                        log.info("Found 'COSTO ASEGURADORA' at column index {}", costoAsgCol);
                    } else if (headerValue.contains("ACCIÓN") || headerValue.contains("ACCION")) {
                        accionCol = cell.getColumnIndex();
                        log.info("Found 'ACCIÓN' at column index {}", accionCol);
                    }
                }
            }

            // Clear and populate file data lists
            fileOfertas = new ArrayList<>();
            fileCostoAdministrativo = new ArrayList<>();
            fileCostoAseguradora = new ArrayList<>();
            fileAccion = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                String ofertaValue = getCellValueAsString(row.getCell(ofertaCol)).trim();
                String costoAdmValue = getCellValueAsString(row.getCell(costoAdmCol)).trim();
                String costoAsgValue = getCellValueAsString(row.getCell(costoAsgCol)).trim();
                String accionValue = getCellValueAsString(row.getCell(accionCol)).trim();

                if (!ofertaValue.isEmpty() || !costoAdmValue.isEmpty() || !costoAsgValue.isEmpty()) {
                    fileOfertas.add(ofertaValue);
                    fileCostoAdministrativo.add(costoAdmValue);
                    fileCostoAseguradora.add(costoAsgValue);
                    fileAccion.add(accionValue);
                    log.info("File Row {}: OFERTA='{}', COSTO ADMINISTRATIVO='{}', COSTO ASEGURADORA='{}', ACCIÓN='{}'",
                            row.getRowNum() + 1, ofertaValue, costoAdmValue, costoAsgValue, accionValue);
                }
            }

            log.info("File extracted {} data rows", fileOfertas.size());
            log.info("File OFERTA values: {}", fileOfertas);
            log.info("File COSTO ADMINISTRATIVO values: {}", fileCostoAdministrativo);
            log.info("File COSTO ASEGURADORA values: {}", fileCostoAseguradora);
            log.info("File ACCIÓN values: {}", fileAccion);

        } catch (IOException e) {
            log.error("Error reading uploaded Excel file: {}", e.getMessage());
            throw new RuntimeException("Failed to read uploaded Excel file for validation", e);
        }
    }

    private void submitBulkRegistration() {
        log.info("Submitting bulk registration");
        waitForElementToBeClickable(getElement(By.xpath(SEND_BULK_REFERENCE_BUTTON)), Timeouts.LOAD_RESULTS);
        log().image("Before clicking Enviar button", takeScreenshot());
        click(getElement(By.xpath(SEND_BULK_REFERENCE_BUTTON)));
        log().image("Enviar button clicked", takeScreenshot());

        waitForElementPresence(getElement(By.xpath(RESUME_VIEW_SECTION)), Timeouts.LOAD_ELEMENT);
        log().image("Resume view displayed", takeScreenshot());
        new Buttons().jsClickAcceptButton();
    }

    public void validateResumeViewData() {
        log.info("Validating resume view data");
        
        // 1. Validar que todas las celdas de "Monto de oferta" tengan texto
        validateTableColumnNotEmpty("Monto de oferta", "//tr[contains(@class,'ant-table-row')]/td[9]");
        
        // 2. Validar que todas las celdas de "Adjudicado a" tengan texto
        validateTableColumnNotEmpty("Adjudicado a", "//tr[contains(@class,'ant-table-row')]/td[10]");
        
        // 3. Validar Fecha y Hora límite
        List<WebElement> IdPublicaciones = getElements(By.xpath("//table//tbody/tr[contains(@class, 'ant-table-row')]/td[count(ancestor::table/thead/tr/th[contains(., \"Identificador de la publicación\")]/preceding-sibling::th) + 1]"));
        List<WebElement> IdCaseNumber = getElements(By.xpath("//table/tbody/tr/td[count(ancestor::table/thead/tr/th[contains(., \"Número de caso\")]/preceding-sibling::th) + 1]"));
        List<WebElement> IdSiniestroNIU = getElements(By.xpath("//table/tbody/tr[contains(@class, 'ant-table-row')]/td[count(ancestor::table/thead/tr/th[contains(., \"Siniestro/NIU\")]/preceding-sibling::th) + 1]"));
        List<WebElement> fechas = getElements(By.xpath("//tr[contains(@class,'ant-table-row')]/td[13]"));
        List<WebElement> horas = getElements(By.xpath("//tr[contains(@class,'ant-table-row')]/td[14]"));
        List<WebElement> montoOferta = getElements(By.xpath("//tr[contains(@class,'ant-table-row')]/td[9]"));
        List<WebElement> montoAdminstractivo = getElements(By.xpath("//tr[contains(@class,'ant-table-row')]/td[11]"));
        List<WebElement> costoAseguradora = getElements(By.xpath("//tr[contains(@class,'ant-table-row')]/td[12]"));
        
        assertions().assertThat(IdPublicaciones).as("No se encontraron identificadores de publicación").isNotEmpty();
        publicationId = getText(IdPublicaciones.get(0)).trim();
        assertions().assertThat(IdCaseNumber).as("No se encontraron números de caso").isNotEmpty();
        caseNumber = getText(IdCaseNumber.get(0)).trim();
        assertions().assertThat(IdSiniestroNIU).as("No se encontraron siniestros/NIUs").isNotEmpty();
        siniestroNIU = getText(IdSiniestroNIU.get(0)).trim();
        assertions().assertThat(fechas.size()).as("Validating dates and amounts consistency").isEqualTo(montoAdminstractivo.size());
        
        // Store extracted values for later validation against the downloaded report
        resumeFechas = new ArrayList<>();
        resumeHoras = new ArrayList<>();
        resumeMontos = new ArrayList<>();
        resumeCostoAseguradora = new ArrayList<>();
        resumeOfertas = new ArrayList<>();
        for (WebElement fecha : fechas) {
            resumeFechas.add(getText(fecha).trim());
        }
        for (WebElement hora : horas) {
            resumeHoras.add(getText(hora).trim());
        }
        for (WebElement monto : montoAdminstractivo) {
            resumeMontos.add(getText(monto).trim());
        }
        for (WebElement costo : costoAseguradora) {
            resumeCostoAseguradora.add(getText(costo).trim());
        }
        for (WebElement oferta : montoOferta) {
            resumeOfertas.add(getText(oferta).trim());
        }
        
        log.info("Publication ID: {}", publicationId);
        log.info("Case Number: {}", caseNumber);
        log.info("Siniestro/NIU: {}", siniestroNIU);
        log.info("Fechas extracted: {}", resumeFechas);
        log.info("Horas extracted: {}", resumeHoras);
        log.info("Montos extracted: {}", resumeMontos);
        log.info("Costo Aseguradora extracted: {}", resumeCostoAseguradora);
        log.info("Ofertas (Monto de oferta) extracted: {}", resumeOfertas);
        
        // Validate frontend values against the uploaded file data
        validateFrontendAgainstFileData();
        
        log().image("Resume view with valid cases", takeScreenshot());
    }

    private void validateFrontendAgainstFileData() {
        log.info("Validating frontend values against uploaded file data");
        
        // Validate "Monto de oferta" (frontend td[9]) vs "OFERTA" column (I) from file
        if (!fileOfertas.isEmpty() && !resumeOfertas.isEmpty()) {
            log.info("Validating Monto de oferta (frontend) vs OFERTA (file column I)");
            for (String ofertaFrontend : resumeOfertas) {
                boolean found = fileOfertas.stream()
                        .anyMatch(fileOferta -> fileOferta.contains(ofertaFrontend) 
                                || ofertaFrontend.contains(fileOferta)
                                || normalizeNumericValue(fileOferta).equals(normalizeNumericValue(ofertaFrontend)));
                assertions().assertThat(found)
                        .as("Monto de oferta '" + ofertaFrontend + "' (frontend) debe coincidir con columna OFERTA (I) del archivo")
                        .isTrue();
            }
            log.info("Monto de oferta validation passed - all values match file column OFERTA (I)");
        } else {
            log.warn("Skipping OFERTA validation: fileOfertas={}, resumeOfertas={}", 
                    fileOfertas.size(), resumeOfertas.size());
        }
        
        // Validate "montoAdminstractivo" (frontend td[11]) vs "COSTO ADMINISTRATIVO" column (M) from file
        if (!fileCostoAdministrativo.isEmpty() && !resumeMontos.isEmpty()) {
            log.info("Validating Costo Administrativo (frontend) vs COSTO ADMINISTRATIVO (file column M)");
            for (String montoFrontend : resumeMontos) {
                boolean found = fileCostoAdministrativo.stream()
                        .anyMatch(fileMonto -> fileMonto.contains(montoFrontend) 
                                || montoFrontend.contains(fileMonto)
                                || normalizeNumericValue(fileMonto).equals(normalizeNumericValue(montoFrontend)));
                assertions().assertThat(found)
                        .as("Costo Administrativo '" + montoFrontend + "' (frontend) debe coincidir con columna COSTO ADMINISTRATIVO (M) del archivo")
                        .isTrue();
            }
            log.info("Costo Administrativo validation passed - all values match file column COSTO ADMINISTRATIVO (M)");
        } else {
            log.warn("Skipping COSTO ADMINISTRATIVO validation: fileCostoAdministrativo={}, resumeMontos={}", 
                    fileCostoAdministrativo.size(), resumeMontos.size());
        }
        
        // Validate "costoAseguradora" (frontend td[12]) vs "COSTO ASEGURADORA" column (O) from file
        if (!fileCostoAseguradora.isEmpty() && !resumeCostoAseguradora.isEmpty()) {
            log.info("Validating Costo Aseguradora (frontend) vs COSTO ASEGURADORA (file column O)");
            for (String costoFrontend : resumeCostoAseguradora) {
                boolean found = fileCostoAseguradora.stream()
                        .anyMatch(fileCosto -> fileCosto.contains(costoFrontend) 
                                || costoFrontend.contains(fileCosto)
                                || normalizeNumericValue(fileCosto).equals(normalizeNumericValue(costoFrontend)));
                assertions().assertThat(found)
                        .as("Costo Aseguradora '" + costoFrontend + "' (frontend) debe coincidir con columna COSTO ASEGURADORA (O) del archivo")
                        .isTrue();
            }
            log.info("Costo Aseguradora validation passed - all values match file column COSTO ASEGURADORA (O)");
        } else {
            log.warn("Skipping COSTO ASEGURADORA validation: fileCostoAseguradora={}, resumeCostoAseguradora={}", 
                    fileCostoAseguradora.size(), resumeCostoAseguradora.size());
        }
        
        log.info("Frontend vs file data validation completed");
    }

    private String normalizeNumericValue(String value) {
        if (value == null || value.isEmpty()) return "";
        // Remove currency symbols, commas, spaces, and normalize decimal separators
        String normalized = value.replaceAll("[$,€£¥\\s]", "")
                                 .replace(",", ".")
                                 .trim();
        // Try to parse as double and format consistently
        try {
            double d = Double.parseDouble(normalized);
            // Format to remove trailing zeros but keep 2 decimal places
            if (d == Math.floor(d) && !Double.isInfinite(d)) {
                return String.valueOf((long) d);
            }
            return String.format("%.2f", d);
        } catch (NumberFormatException e) {
            return normalized;
        }
    }

    private void validateTableColumnNotEmpty(String columnName, String xpath) {
        List<WebElement> cells = getElements(By.xpath(xpath));
        for (WebElement cell : cells) {
            assertions().assertThat(cell.getText().trim())
                    .as("El campo " + columnName + " no debe estar vacío")
                    .isNotEmpty();
        }
    }

    private void continueBulkProcess() {
        log.info("Continuing bulk process");
        new Buttons().clickContinueBtn();
        waitForElementPresence(getElement(By.xpath(RESUME_VIEW_SECTION2)), Timeouts.LOAD_ELEMENT);
        log().image(ADJUDICATION_RESULTS, takeScreenshot());
    }

    public void downloadReportResult() {
        log.info("Searching for bulk reference process report to download");
        assertions().assertThat(publicationId).as("Publication ID for report download must be set").isNotBlank();
        new Buttons().jsClickAcceptButton();
        new MenuPage().clickPublications(PublicationsOptions.CONSULT);

        PublicationSearch search = new PublicationSearch();
        search.selectPublicationType(com.solera.global.qa.template.web.behavior.pages.componentpages.CaseType.VEHICLES);
        search.setPublicationID(publicationId);

        log().image("Before search publication", takeScreenshot());
        search.search();
        log().image("After search publication", takeScreenshot());
        // Select and open publication from results list
        log.info("Select and open publication from results list");
        String criteriaResults = GENERAL_SEARCH_CRITERIA.replace("?", publicationId);
        click(getElement(By.xpath(criteriaResults)));
        filesActions.waitButtonAndClick(RESULTS_REPORT);
        log().image("After clicking report button", takeScreenshot());
        
        // Wait for the report to download and validate its contents
        // Use DownloadManager which searches in configured dir + user Downloads/Descargas
        // Timeout: 120 seconds for report generation
        File downloadedFile = downloadManager.getDownloadedFileByPartialName("Resultados_", ".xlsx", 120);
        validateDownloadedReport(downloadedFile);
    }

    private void validateDownloadedReport(File downloadedFile) {
        log.info("Validating downloaded report against frontend values");
        assertions().assertThat(downloadedFile)
                .as("Downloaded report file should exist")
                .isNotNull();
        
        log.info("Validating report file: {}", downloadedFile.getAbsolutePath());
        
        try (FileInputStream fis = new FileInputStream(downloadedFile);
             Workbook workbook = WorkbookFactory.create(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            int expectedRows = resumeFechas.size();
            log.info("Expected {} rows of data in the report", expectedRows);
            
            // Find column indices by header names in the first row
            Row headerRow = sheet.getRow(0);
            int idPublicacionCol = -1;
            int ofertaCol = -1;
            int ofertaAdjudicadaCol = -1;
            int costoAdministrativoCol = -1;
            int costoAseguradoraCol = -1;
            
            if (headerRow != null) {
                for (Cell cell : headerRow) {
                    String headerValue = getCellValueAsString(cell).trim().toUpperCase();
                    if (headerValue.contains("ID PUBLICACIÓN") || headerValue.contains("IDENTIFICADOR")) {
                        idPublicacionCol = cell.getColumnIndex();
                        log.info("Found 'ID PUBLICACIÓN' at column index {}", idPublicacionCol);
                    } else if (headerValue.contains("OFERTA ADJUDICADA")) {
                        ofertaAdjudicadaCol = cell.getColumnIndex();
                        log.info("Found 'OFERTA ADJUDICADA' at column index {}", ofertaAdjudicadaCol);
                    } else if (headerValue.contains("OFERTA")) {
                        ofertaCol = cell.getColumnIndex();
                        log.info("Found 'OFERTA' at column index {}", ofertaCol);
                    } else if (headerValue.contains("COSTO ADMINISTRATIVO")) {
                        costoAdministrativoCol = cell.getColumnIndex();
                        log.info("Found 'COSTO ADMINISTRATIVO' at column index {}", costoAdministrativoCol);
                    } else if (headerValue.contains("COSTO ASEGURADORA") || headerValue.contains("COSTO MERCADO")) {
                        costoAseguradoraCol = cell.getColumnIndex();
                        log.info("Found 'COSTO ASEGURADORA' at column index {}", costoAseguradoraCol);
                    }
                }
            }
            
            // Fallback if headers not found: use provided column positions
            if (idPublicacionCol == -1) {
                idPublicacionCol = 0;  // Column A
                log.info("Using default column index {} for 'ID PUBLICACIÓN'", idPublicacionCol);
            }
            if (ofertaCol == -1) {
                ofertaCol = 41;  // Column AP (0-based index 41)
                log.info("Using default column index {} for 'OFERTA'", ofertaCol);
            }
            if (ofertaAdjudicadaCol == -1) {
                ofertaAdjudicadaCol = 43;  // Column AQ (0-based index 43)
                log.info("Using default column index {} for 'OFERTA ADJUDICADA'", ofertaAdjudicadaCol);
            }
            if (costoAdministrativoCol == -1) {
                costoAdministrativoCol = 57;  // Column BF (0-based index 57)
                log.info("Using default column index {} for 'COSTO ADMINISTRATIVO'", costoAdministrativoCol);
            }
            if (costoAseguradoraCol == -1) {
                costoAseguradoraCol = 59;  // Column BH (0-based index 59)
                log.info("Using default column index {} for 'COSTO ASEGURADORA'", costoAseguradoraCol);
            }
            
            List<String> reportIdPublicacion = new ArrayList<>();
            List<String> reportOferta = new ArrayList<>();
            List<String> reportOfertaAdjudicada = new ArrayList<>();
            List<String> reportCostoAdministrativo = new ArrayList<>();
            List<String> reportCostoAseguradora = new ArrayList<>();
            
            for (Row row : sheet) {
                // Skip header row (row 0)
                if (row.getRowNum() == 0) continue;
                
                String idValue = getCellValueAsString(row.getCell(idPublicacionCol)).trim();
                String ofertaValue = getCellValueAsString(row.getCell(ofertaCol)).trim();
                String ofertaAdjudicadaValue = getCellValueAsString(row.getCell(ofertaAdjudicadaCol)).trim();
                String costoAdmValue = getCellValueAsString(row.getCell(costoAdministrativoCol)).trim();
                String costoAsgValue = getCellValueAsString(row.getCell(costoAseguradoraCol)).trim();
                
                if (!idValue.isEmpty()) {
                    reportIdPublicacion.add(idValue);
                    reportOferta.add(ofertaValue);
                    reportOfertaAdjudicada.add(ofertaAdjudicadaValue);
                    reportCostoAdministrativo.add(costoAdmValue);
                    reportCostoAseguradora.add(costoAsgValue);
                    log.info("Report Row {}: ID PUBLICACIÓN='{}', OFERTA='{}', OFERTA ADJUDICADA='{}', COSTO ADMINISTRATIVO='{}', COSTO ASEGURADORA='{}'",
                            row.getRowNum() + 1, idValue, ofertaValue, ofertaAdjudicadaValue, costoAdmValue, costoAsgValue);
                }
            }
            
            log.info("Report extracted {} data rows", reportIdPublicacion.size());
            
            // Validate publication ID is present in the 'ID PUBLICACIÓN' column
            boolean foundPublicationId = reportIdPublicacion.stream()
                    .anyMatch(id -> id.contains(publicationId) || publicationId.contains(id));
            assertions().assertThat(foundPublicationId)
                    .as("Publication ID '" + publicationId + "' should be present in 'ID PUBLICACIÓN' column")
                    .isTrue();
            
            // Validate that the report has data rows with non-empty values
            assertions().assertThat(reportIdPublicacion.size())
                    .as("Report should contain data rows with ID PUBLICACIÓN")
                    .isGreaterThan(0);
            
            assertions().assertThat(reportCostoAdministrativo.size())
                    .as("Report should contain data rows with COSTO ADMINISTRATIVO")
                    .isGreaterThan(0);
            
            // Validate each "Monto de oferta" value from frontend (resumeOfertas) is present in the report "OFERTA" column (AP)
            log.info("Validating Monto de oferta (frontend) vs OFERTA column in downloaded report");
            for (String oferta : resumeOfertas) {
                boolean found = reportOferta.stream()
                        .anyMatch(ro -> ro.contains(oferta) || oferta.contains(ro)
                                || normalizeNumericValue(ro).equals(normalizeNumericValue(oferta)));
                assertions().assertThat(found)
                        .as("Monto de oferta '" + oferta + "' (frontend) should be present in 'OFERTA' column of downloaded report")
                        .isTrue();
            }
            log.info("OFERTA validation passed - all frontend Monto de oferta values match report column OFERTA");
            
            // Validate each "Monto de oferta" value from frontend (resumeOfertas) is present in the report "OFERTA ADJUDICADA" column (AQ)
            // Only validate rows where the uploaded file's ACCIÓN column = "1" (rows with "0" or empty are skipped)
            log.info("Validating Monto de oferta (frontend) vs OFERTA ADJUDICADA column in downloaded report (only rows where uploaded file ACCIÓN='1')");
            int validatedRows = 0;
            int skippedRows = 0;
            for (int i = 0; i < reportOfertaAdjudicada.size() && i < fileAccion.size(); i++) {
                String accion = fileAccion.get(i);
                if ("1".equals(accion)) {
                    String ofertaAdjudicadaValue = reportOfertaAdjudicada.get(i);
                    boolean found = resumeOfertas.stream()
                            .anyMatch(oferta -> oferta.contains(ofertaAdjudicadaValue)
                                    || ofertaAdjudicadaValue.contains(oferta)
                                    || normalizeNumericValue(oferta).equals(normalizeNumericValue(ofertaAdjudicadaValue)));
                    assertions().assertThat(found)
                            .as("Monto de oferta '" + ofertaAdjudicadaValue + "' (report OFERTA ADJUDICADA row " + (i + 1) + ") debe coincidir con frontend: " + resumeOfertas)
                            .isTrue();
                    validatedRows++;
                } else {
                    skippedRows++;
                    log.info("Skipping OFERTA ADJUDICADA validation for row {} (uploaded file ACCIÓN='{}')", i + 1, accion);
                }
            }
            log.info("OFERTA ADJUDICADA validation completed - {} rows validated, {} rows skipped (uploaded file ACCIÓN != '1')", validatedRows, skippedRows);
            
            // Validate each "Costo Administrativo" value from frontend (resumeMontos td[11]) is present in the report "COSTO ADMINISTRATIVO" column (BF)
            log.info("Validating Costo Administrativo (frontend) vs COSTO ADMINISTRATIVO column in downloaded report");
            for (String monto : resumeMontos) {
                boolean found = reportCostoAdministrativo.stream()
                        .anyMatch(rc -> rc.contains(monto) || monto.contains(rc)
                                || normalizeNumericValue(rc).equals(normalizeNumericValue(monto)));
                assertions().assertThat(found)
                        .as("Costo Administrativo '" + monto + "' (frontend) should be present in 'COSTO ADMINISTRATIVO' column of downloaded report")
                        .isTrue();
            }
            log.info("COSTO ADMINISTRATIVO validation passed - all frontend Costo Administrativo values match report column COSTO ADMINISTRATIVO");
            
            // Validate each costo aseguradora value from frontend is present in the report column
            for (String costo : resumeCostoAseguradora) {
                boolean found = reportCostoAseguradora.stream()
                        .anyMatch(rc -> rc.contains(costo) || costo.contains(rc));
                assertions().assertThat(found)
                        .as("Costo Aseguradora '" + costo + "' should be present in the downloaded report")
                        .isTrue();
            }
            
            log.info("Report validated successfully - {} data rows with non-empty values", reportIdPublicacion.size());
            
        } catch (IOException e) {
            log.error("Error reading downloaded report file: {}", e.getMessage());
            throw new RuntimeException("Failed to validate values in downloaded report", e);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                // Check if the cell has a date format
                if (DateUtil.isCellDateFormatted(cell)) {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                    return sdf.format(cell.getDateCellValue());
                }
                // Handle numeric values
                double numericValue = cell.getNumericCellValue();
                if (numericValue == Math.floor(numericValue) && !Double.isInfinite(numericValue)) {
                    return String.valueOf((long) numericValue);
                }
                return String.valueOf(numericValue);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                        return sdf.format(cell.getDateCellValue());
                    }
                    return String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    return cell.getStringCellValue();
                }
            default:
                return "";
        }
    }


    /**
     * Master phase of bulk vehicle awarding registration.
     * Requires master user session. Extracts data into static fields
     * that persist for the subsequent buyer phase.
     */
    public void bulkVehicleAwardingRegistrationMaster() {
        String xlsxFolderName = "bulkAwarding";
        navigateToBulkAwardingsPage();
        uploadBulkFiles(xlsxFolderName);
        validateUploadedFileAgainstFrontend(xlsxFolderName);
        submitBulkRegistration();
        validateResumeViewData();
        continueBulkProcess();
        downloadReportResult();
        consultCasePaymentSection();
    }

    /**
     * Buyer phase of bulk vehicle awarding registration.
     * Requires buyer user session. Uses static data extracted
     * during the master phase for comparison.
     */
    public void bulkVehicleAwardingRegistrationBuyer() {
        consultToBulkAwardingsSection();
        caseAwardedBuyerSectionCompareLayout();
    }


    private void navigateToBulkAwardingsPage() {
        log.info("Navigating to bulk references page");
        new MenuPage().clickAwardings(AwardingsOptions.MASSIVE_AWARDINGS);
        waitForElementPresence(getElement(By.xpath(PAPER_CLIP_BUTTON)), Timeouts.LOAD_ELEMENT);
        AdjudicationSearch search = new AdjudicationSearch();
        search.selectCaseTypeTst(CaseType.VEHICLES);
        waitForElementToBeClickable(getElement(By.xpath(ATTACH_BUTTON)), Timeouts.LOAD_ELEMENT);
        log().image("Bulk reference page loaded", takeScreenshot());
    }


    public void consultCasePaymentSection() {
        new MenuPage().clickPayments();
        PaymentSearch search = new PaymentSearch();
        search.selectCaseTypeTst(CaseType.VEHICLES);
        log().image("Selected vehicle case type", takeScreenshot());
        PublicationSearch publicationSearch = new PublicationSearch();
        publicationSearch.setPaymentCaseID(caseNumber);
        publicationSearch.search();
        String criteriaResults = SEARCH_PAYMENT_SINISTER.replace("?", siniestroNIU);
        waitForElementToBeClickable(getElement(By.xpath(criteriaResults)), Timeouts.LOAD_HEAVY_RESULTS);
        click(getElement(By.xpath(criteriaResults)));
        sleep(1500);
        log.info("Search for status payments completed");
        casePaymentSectionCompareLayout();
    }

    public void casePaymentSectionCompareLayout() {
        log.info("Comparing payment section amounts against resume view data");
        log().image("Payment section before extracting values", takeScreenshot());

        // XPaths for payment amount sections
        String MONTO_OFERTA_XPATH = "//div[contains(@class, \"ant-descriptions\")][.//div[contains(@class, \"ant-descriptions-title\")]//text()[contains(., \"Monto de oferta\")]]//tr[th[contains(., \"Monto\")]]/td";
        String MONTO_ADMINISTRATIVO_XPATH = "//div[contains(@class, \"ant-descriptions\")][.//div[contains(@class, \"ant-descriptions-title\")]//text()[contains(., \"Monto administrativo\")]]//tr[th[contains(., \"Monto\")]]/td";
        String MONTO_ASEGURADORA_XPATH = "//div[contains(@class, \"ant-descriptions\")][.//div[contains(@class, \"ant-descriptions-title\")]//text()[contains(., \"Monto administrativo de mercado (Aseguradora)\")]]//tr[th[contains(., \"Monto\")]]/td";

        // Extract values from payment section
        String paymentMontoOferta = getText(getElement(By.xpath(MONTO_OFERTA_XPATH))).trim();
        String paymentMontoAdministrativo = getText(getElement(By.xpath(MONTO_ADMINISTRATIVO_XPATH))).trim();
        String paymentMontoAseguradora = getText(getElement(By.xpath(MONTO_ASEGURADORA_XPATH))).trim();

        log.info("Payment section values:");
        log.info("  Monto de oferta: '{}'", paymentMontoOferta);
        log.info("  Monto administrativo: '{}'", paymentMontoAdministrativo);
        log.info("  Monto administrativo de mercado (Aseguradora): '{}'", paymentMontoAseguradora);

        log.info("Resume view extracted values:");
        log.info("  Ofertas (Monto de oferta): {}", resumeOfertas);
        log.info("  Montos (Costo administrativo): {}", resumeMontos);
        log.info("  Costo aseguradora: {}", resumeCostoAseguradora);

        // Validation 1: Monto de oferta (resumeOfertas td[9]) vs payment "Monto de oferta"
        boolean montoOfertaCoincide = resumeOfertas.stream()
                .anyMatch(oferta -> oferta.contains(paymentMontoOferta)
                        || paymentMontoOferta.contains(oferta)
                        || normalizeNumericValue(oferta).equals(normalizeNumericValue(paymentMontoOferta)));
        assertions().assertThat(montoOfertaCoincide)
                .as("El Monto de oferta de la sección payment '" + paymentMontoOferta
                        + "' debe coincidir con algún valor de la tabla de referencias masivas: " + resumeOfertas)
                .isTrue();
        log.info("VALIDATION 1 PASSED: Monto de oferta '{}' matches resume view", paymentMontoOferta);

        // Validation 2: Monto administrativo (resumeMontos td[11]) vs payment "Monto administrativo"
        boolean montoAdministrativoCoincide = resumeMontos.stream()
                .anyMatch(monto -> monto.contains(paymentMontoAdministrativo)
                        || paymentMontoAdministrativo.contains(monto)
                        || normalizeNumericValue(monto).equals(normalizeNumericValue(paymentMontoAdministrativo)));
        assertions().assertThat(montoAdministrativoCoincide)
                .as("El Monto administrativo de la sección payment '" + paymentMontoAdministrativo
                        + "' debe coincidir con algún valor de la tabla de referencias masivas: " + resumeMontos)
                .isTrue();
        log.info("VALIDATION 2 PASSED: Monto administrativo '{}' matches resume view", paymentMontoAdministrativo);

        // Validation 3: Costo aseguradora (resumeCostoAseguradora td[12]) vs payment "Monto administrativo de mercado (Aseguradora)"
        boolean montoAseguradoraCoincide = resumeCostoAseguradora.stream()
                .anyMatch(costo -> costo.contains(paymentMontoAseguradora)
                        || paymentMontoAseguradora.contains(costo)
                        || normalizeNumericValue(costo).equals(normalizeNumericValue(paymentMontoAseguradora)));
        assertions().assertThat(montoAseguradoraCoincide)
                .as("El Monto aseguradora de la sección payment '" + paymentMontoAseguradora
                        + "' debe coincidir con algún valor de la tabla de referencias masivas: " + resumeCostoAseguradora)
                .isTrue();
        log.info("VALIDATION 3 PASSED: Monto aseguradora '{}' matches resume view", paymentMontoAseguradora);

        log().image("Payment section after validation", takeScreenshot());
        log.info("All payment section validations completed successfully");
    }


    private void consultToBulkAwardingsSection() {
       log.info("Searching for awardings");
        new MenuPage().clickAwardingsBuyer();
        log.info("Returning to searchAwardings method");
        AdjudicationSearch search = new AdjudicationSearch();
        log().image("Before select satus", takeScreenshot());
        log().image("Status selected", takeScreenshot());
        search.selectCaseTypeTst(CaseType.VEHICLES);
        search.setAdjudicationCaseID(caseNumber);
        search.search();
        String criteriaResults = GENERAL_SEARCH_CRITERIA.replace("?", caseNumber);
        waitForElementToBeClickable(getElement(By.xpath(criteriaResults)), Timeouts.LOAD_RESULTS);
        click(getElement(By.xpath(criteriaResults)));
        sleep(1500);
        log.info("Search for status payments completed");
    }



    public void caseAwardedBuyerSectionCompareLayout() {
        log.info("Comparing buyer section amounts against resume view data");
        log().image("Buyer section before extracting values", takeScreenshot());

        // XPaths for buyer amount sections
        String MONTO_OFERTA_XPATH = "//div[contains(@class, \"ant-descriptions\")][.//div[contains(@class, \"ant-descriptions-title\")]//text()[contains(., \"Monto de oferta\")]]//tr[th[contains(., \"Monto\")]]/td";
        String MONTO_ADMINISTRATIVO_XPATH = "//div[contains(@class, \"ant-descriptions\")][.//div[contains(@class, \"ant-descriptions-title\")]//text()[contains(., \"Monto Administrativo\")]]//tr[th[contains(., \"Monto\")]]/td";
        String MONTO_ASEGURADORA_XPATH = "//div[contains(@class, \"ant-descriptions\")][.//div[contains(@class, \"ant-descriptions-title\")]//text()[contains(., \"Monto administrativo de aseguradora\")]]//tr[th[contains(., \"Monto\")]]/td";

        // Extract values from buyer section
        String buyerMontoOferta = getText(getElement(By.xpath(MONTO_OFERTA_XPATH))).trim();
        String buyerMontoAdministrativo = getText(getElement(By.xpath(MONTO_ADMINISTRATIVO_XPATH))).trim();
        String buyerMontoAseguradora = getText(getElement(By.xpath(MONTO_ASEGURADORA_XPATH))).trim();

        log.info("Buyer section values:");
        log.info("  Monto de oferta: '{}'", buyerMontoOferta);
        log.info("  Monto administrativo: '{}'", buyerMontoAdministrativo);
        log.info("  Monto administrativo de mercado (Aseguradora): '{}'", buyerMontoAseguradora);

        log.info("Resume view extracted values:");
        log.info("  Ofertas (Monto de oferta): {}", resumeOfertas);
        log.info("  Montos (Costo administrativo): {}", resumeMontos);
        log.info("  Costo aseguradora: {}", resumeCostoAseguradora);

        // Validation 1: Monto de oferta (resumeOfertas td[9]) vs buyer "Monto de oferta"
        boolean montoOfertaCoincide = resumeOfertas.stream()
                .anyMatch(oferta -> oferta.contains(buyerMontoOferta)
                        || buyerMontoOferta.contains(oferta)
                        || normalizeNumericValue(oferta).equals(normalizeNumericValue(buyerMontoOferta)));
        assertions().assertThat(montoOfertaCoincide)
                .as("El Monto de oferta de la sección buyer '" + buyerMontoOferta
                        + "' debe coincidir con algún valor de la tabla de referencias masivas: " + resumeOfertas)
                .isTrue();
        log.info("VALIDATION 1 PASSED: Monto de oferta '{}' matches resume view", buyerMontoOferta);

        // Validation 2: Monto administrativo (resumeMontos td[11]) vs buyer "Monto administrativo"
        boolean montoAdministrativoCoincide = resumeMontos.stream()
                .anyMatch(monto -> monto.contains(buyerMontoAdministrativo)
                        || buyerMontoAdministrativo.contains(monto)
                        || normalizeNumericValue(monto).equals(normalizeNumericValue(buyerMontoAdministrativo)));
        assertions().assertThat(montoAdministrativoCoincide)
                .as("El Monto administrativo de la sección buyer '" + buyerMontoAdministrativo
                        + "' debe coincidir con algún valor de la tabla de referencias masivas: " + resumeMontos)
                .isTrue();
        log.info("VALIDATION 2 PASSED: Monto administrativo '{}' matches resume view", buyerMontoAdministrativo);

        // Validation 3: Costo aseguradora (resumeCostoAseguradora td[12]) vs buyer "Monto administrativo de mercado (Aseguradora)"
        boolean montoAseguradoraCoincide = resumeCostoAseguradora.stream()
                .anyMatch(costo -> costo.contains(buyerMontoAseguradora)
                        || buyerMontoAseguradora.contains(costo)
                        || normalizeNumericValue(costo).equals(normalizeNumericValue(buyerMontoAseguradora)));
        assertions().assertThat(montoAseguradoraCoincide)
                .as("El Monto aseguradora de la sección buyer '" + buyerMontoAseguradora
                        + "' debe coincidir con algún valor de la tabla de referencias masivas: " + resumeCostoAseguradora)
                .isTrue();
        log.info("VALIDATION 3 PASSED: Monto aseguradora '{}' matches resume view", buyerMontoAseguradora);

        log().image("Buyer section after validation", takeScreenshot());
        log.info("All buyer section validations completed successfully");
    }


}