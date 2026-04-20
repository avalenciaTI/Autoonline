package com.solera.global.qa.template.web.behavior.pages.casecreation;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonSinisterField;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogInPage;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@Slf4j
public class Documents extends BrowserPage {

    public static final String ATTACH_VEHICLE_DOCUMENTS_BUTTON = "//a[contains(@href, 'attachments')]";
    public static final String VALIDATE_VEHICLE_DOCUMENTS_BUTTON = "//a[contains(@href, 'attachments/validate')]";
    public static final String VALIDATE_ALL_VEHICLE_DOCUMENTS
            = "//span[text()='Aprobar todo']/parent::button[@type='submit']";
    // ensure which of both paper_clip_button must remains
    //public static final String PAPER_CLIP_BUTTON = "//i[@aria-label='ícono: paper-clip']/parent::button/input"
    public static final String PAPER_CLIP_BUTTON = "//input[@type='file']";
    public static final String CONTINUE_BUTTON = "//button[@type='submit' and contains(@form,'')]";
    public static final String CASE_STATUS_FIELD = "//input[contains(@id,'caseStatus')]";

    public static final String SECTION_LOCATOR_TEMP =
            "//h5[text()='?']/parent::div/parent::div[contains(@class,'input-documents')]";
    public static final String ATTACH_DOCUMENTS_BUTTON =
            "//h5[text()='?']/parent::div//parent::div[contains(@class,'input-documents')]"
                    + "//i[@class='anticon anticon-plus']/parent::button";
    public static final String DOCUMENTS_COMMENTS =
            "//h5[text()='?']/parent::div//parent::div[contains(@class,'input-documents')]"
                    + "//textarea[@class='ant-input']";
    public static final String INDEX_TAB_SELECTOR = "//div[contains(@class,'ant-steps-navigation')]/child::div[?]";

    public static final String DELETE_IMAGE_BTN = "//div[@class='ant-modal-content']//button[@jest-id='DeleteFile']";

    private static final String NOTIFICATION_MESSAGE = "//div[@class='ant-notification-notice-message']";
    private static final String CLOSE_NOTIFICATION = "//i[@class='anticon anticon-close ant-notification-close-icon']";
    private static final String REPORT_BUTTON = "//button[@jest-id='reportButton']";
    @FindBy(xpath = ATTACH_VEHICLE_DOCUMENTS_BUTTON)
    WebElement attachVehicleDocuments;
    @FindBy(xpath = PAPER_CLIP_BUTTON)
    WebElement paperClipButton;
    @FindBy(xpath = CONTINUE_BUTTON)
    WebElement continueButton;
    @FindBy(xpath = VALIDATE_VEHICLE_DOCUMENTS_BUTTON)
    WebElement validateVehicleDocuments;
    @FindBy(xpath = VALIDATE_ALL_VEHICLE_DOCUMENTS)
    WebElement validateAllVehicleDocuments;
    @FindBy(xpath = CASE_STATUS_FIELD)
    WebElement caseStatusField;

    ///
    private static final String CASE_TYPE_VAL = "vehículos";
    public static final String SEARCH_DYNAMIC = "//td[text()='?']";
    private static final String CLASS_NAME = "Documents";

    ///





    public Documents() {
        super();
    }

    private void attachFile(DocumentTypeVehicleESP docType) {
        WebElement element = new CommonComponents()
                .dynamicWebElement(ATTACH_DOCUMENTS_BUTTON, docType.getDocumentTypeVehicle());
        waitForElementToBeClickable(element, Timeouts.LOAD_PAGE);
        click(element);
        String fileName = "documentsVehicle/";
        String baseFolder = new CommonComponents().getAttachmentsFolderFilePath();

        String fullPath = baseFolder + fileName + docType.getFileLoad();
        log.info("FULL PATH: {}", fullPath);
        //review if it is necessary to add a wait for javascript load

        waitForElementPresence(getElement(By.xpath(PAPER_CLIP_BUTTON)), Timeouts.LOAD_ELEMENT);
        sleep(500);

        WebElement uploadButton = getElement(By.xpath(PAPER_CLIP_BUTTON));
        new CommonComponents().uploadFileToWebApp(uploadButton, fullPath);
        log.info("waiting for elements");

        sleep(500);
        new CommonComponents().getElementWithRetry(DELETE_IMAGE_BTN);
        try {
            waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);
            log.info("{} Notification visible", CLASS_NAME);
            click(getElement(By.xpath(CLOSE_NOTIFICATION)));
            log.info("{} Clicked notification closed", CLASS_NAME);
        } catch (Exception ex) {
            log.error("Error waiting for  notifications: {}", ex.getLocalizedMessage());
            log().image("Waiting for notification error", takeScreenshot());
        }
        new Buttons().clickCloseBtn();
    }

    private void attachComment(DocumentTypeVehicleESP docType) {
        WebElement element = new CommonComponents()
                .dynamicWebElement(DOCUMENTS_COMMENTS,docType.getDocumentTypeVehicle());
        click(element);
        sendKeys(element,docType.getComment());
    }

    public void loadAllFiles() {
        click(attachVehicleDocuments);
        int currentTab = 1;

        for (DocumentTypeVehicleESP document: DocumentTypeVehicleESP.values()) {
            if (currentTab != document.getElementTab()) {
                click(continueButton);
                waitForElementVisibility(new CommonComponents()
                        .dynamicWebElement(SECTION_LOCATOR_TEMP,
                                document.getDocumentTypeVehicle()), Timeouts.LOAD_ELEMENT);
                currentTab++;
            }
            attachFile(document);
            attachComment(document);
        }
        log.info("Waiting for continue button to be visible");
        waitForElementToBeClickable(continueButton, Timeouts.LOAD_ELEMENT);
        log.info("continue button must be visible");
        sleep(5000); //wait is need due to wait for asynchronous responses
        log.info("starting waiting for notifications");
        for (WebElement notification: getElements(By.xpath(NOTIFICATION_MESSAGE))) {
            log.info("WAITING FOR NOTIFICATION GOES OFF");
            log().image("Waiting for notification goes off", takeScreenshot());
            waitForElementInvisibility(notification, Timeouts.NOTIFICATION_FADED);
        }
        log.info("End waiting for notifications");

        boolean succesClick = false;
        do {
            try {
                //review if it is necessary to add a wait for javascript load
                log().image("Before Clicking on continue button", takeScreenshot());
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", continueButton);
                sleep(3000);
                log().image("After Clicking on continue button", takeScreenshot());
                succesClick = true;

            } catch (ElementClickInterceptedException ex) {
                log.info("ELEMENT INTERCEPTED -> CLICK ON CONTINUE: {}", ex.getLocalizedMessage());
            } catch (Exception ex) {
                log.info("ERROR -> CLICK ON CONTINUE: {}", ex.getLocalizedMessage());
            }
        } while (!succesClick);
        log().image("After Clicking on continue button 1", takeScreenshot());
        sleep(5000);
        log().image(CLASS_NAME + " documents attached successfully", takeScreenshot());
    }

    public void validateAllDocuments() {
        log.info("Validating all documents  starting method");
        log().image("Validating all documents", takeScreenshot());
        waitForElementToBeClickable(getElement(By.xpath(VALIDATE_VEHICLE_DOCUMENTS_BUTTON)), Timeouts.LOAD_BUTTON);
        log().image("Validating all documents 1", takeScreenshot());
        click(validateVehicleDocuments);
        waitForElementToBeClickable(validateAllVehicleDocuments, Timeouts.NOTIFICATION_DISPLAYED);
        click(validateAllVehicleDocuments);
        caseStatusField = new CommonSinisterField().getCaseStatusField();
        waitForElementInvisibility(getDriver().findElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.SHORT_TIME);
        validateCaseStatus();
        log().image("Documents validated successfully", takeScreenshot());
    }

    public void validateCaseStatus() {
        WebElement tabCaseStatus = new CommonComponents().dynamicWebElement(INDEX_TAB_SELECTOR,"3");
        click(tabCaseStatus);
        waitForElementVisibility(caseStatusField, Timeouts.LOAD_ELEMENT);
        if (getValue(caseStatusField).equals("Documentado")) {
            log.info("{} Case status is Documentado", CLASS_NAME);
            log().image("Case status is Documentado", takeScreenshot());
        } else {
            log.info("{} Case status is not Documentado", CLASS_NAME);
            log().image("Case status is not Documentado", takeScreenshot());
        }
    }

    ///
    public boolean vehicleLoadFiles(AolWebUser user, String vin) {
        MenuPage menuPage = new MenuPage();
        menuPage.clickCases();
        RegistrationMenu casesMenu = menuPage.clickCases();
        casesMenu.clickSearchCases(CASE_TYPE_VAL, vin);
        new CommonComponents().findHRefElement(new CommonComponents().dynamicWebElement(SEARCH_DYNAMIC,vin));
        Documents documents = new Documents();
        documents.loadAllFiles();
        return true;
    }

    public boolean vehicleValidateAllFiles(String vin) {
        MenuPage menuPage = new MenuPage();
        menuPage.clickCases();
        log().image("Clicking on Cases section", takeScreenshot());
        RegistrationMenu casesMenu = menuPage.clickCases();
        log().image("Clicking on Search Cases 1", takeScreenshot());
        casesMenu.clickSearchCases(CASE_TYPE_VAL, vin);

        try {
            waitForElementVisibility(getElement(By.xpath(REPORT_BUTTON)), Timeouts.LOAD_HEAVY_RESULTS);
            log().image("Report button found", takeScreenshot());
        } catch (Exception e) {
            log.warn("Report button not found, continuing with validation");
            log().image("Report button NOT found", takeScreenshot());
        }
        log().image("Verify case result", takeScreenshot());
        new CommonComponents().findHRefElement(new CommonComponents().dynamicWebElement(SEARCH_DYNAMIC, vin));

        log().image("Verify case result 1", takeScreenshot());
        Documents documents = new Documents();
        documents.validateAllDocuments();
        return true;

    }
}
