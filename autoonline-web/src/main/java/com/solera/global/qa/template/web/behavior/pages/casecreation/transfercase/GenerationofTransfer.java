package com.solera.global.qa.template.web.behavior.pages.casecreation.transfercase;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.DownloadManager;


import lombok.extern.slf4j.Slf4j;


@Slf4j
public class GenerationofTransfer extends BrowserPage {

    // FIELDS OF ADD CONTACT FORM
    public static final String CONTACT_NAME_FIELD = "//input[contains(@id,'individual_contactName')]";
    public static final String CONTACT_PHONE_FIELD = "//input[contains(@id,'individual_contactPhone')]";

    // DROPDOWN DESTI INFORMATION
    public static final String PROVIDER_FIELD = "//div[contains(@id, 'destinationForm_craneProvider')]";
    public static final String PROVIDER_SUCURSAL_FIELD = "//div[contains(@id, 'destinationForm_craneBranchId')]";
    public static final String PROVIDER_CAR_POUND_FIELD = "//div[contains(@id, 'destinationForm_carPoundProvider')]";
    public static final String PROVIDER_CAR_POUND_SUCURSAL_FIELD = "//div[contains(@id, 'destinationForm_carPoundId')]";
    public static final String CALENDAR_FIELD = "//input[@class='ant-calendar-picker-input ant-input']";
    public static final String CALENDAR_TODAY_FIELD = "//a[contains(@class,'ant-calendar-today-btn ')]";

    // OTHERS
    public static final String IS_OTHER_SELECTED = "//*[@id='destinationForm_isOtherSelected']";
    public static final String DISTANCE_TYPE_FIELD = "//*[@id='destinationForm_distanceType']";
    public static final String COST_FIELD = "//input[contains(@id,'destinationForm_cost')]";
    public static final String CHARGE_FIELD = "//input[contains(@id,'destinationForm_charge')]";
    public static final String COMMENTS_FIELD = "//textarea[contains(@id,'destinationForm_comments')]";

    // BUTTONS
    private static final String ADD_CONTACT = "//span[text()='Agregar contacto']/parent::button[@type='button']";
    private static final String SAVE_ADD_CONTACT = "//span[text()='Guardar']/parent::button[@type='button']";
    private static final String TOTAL_RESULTS_IN_PAGE = "//table[@class='ant-table-fixed' "
            + "and @style='width: max-content;']/tbody[@class='ant-table-tbody']/tr";

    private static final String TOTAL_TRANSFERS_IN_PAGE = "//table[@class='ant-table-fixed' and "
            + "@style='width: max-content;']/tbody[@class='ant-table-tbody']/tr[td[4]//i[contains(@class, 'anticon')]]";

    private static final String CASES_WITHOUT_TRANSFERS = "//table[@class='ant-table-fixed' and "
            + "@style='width: max-content;']/tbody[@class='ant-table-tbody']"
            + "/tr[not(td[4]//i[contains(@class, 'anticon')])]/td[3]/a";

    private static final String SECOND_TABLE = "//tbody[@class='ant-table-tbody']"
            + "/tr[not(td[4]//i[contains(@class, 'anticon')])]/td[9]"   // ensure select the full table
            + "/parent::tr/descendant::a[contains(text(), '?')]"        // find the row for a sprcific case number
            + "/parent::td/following-sibling::td[contains(text(), '1JKTS')]";   // find the VIN in the same row
    private static final String LOCAL_TRANSFER = "//input[@type='radio' and @value='2']";
    private static final String NEXT_BUTTON_PAGINATION = "//li[contains(@class, 'ant-pagination-next') "
            + "and not(contains(@class, 'ant-pagination-disabled'))]";

    private static final String REPORT_BUTTON = "//button[@jest-id = 'reportButton']";
    private static final String NO_RESULTS_LABEL = "//strong[contains(text(), 'No se encontraron resultados')]";
    private static final String NOTIFICATION_MESSAGE = "//div[@class='ant-notification-notice-message']";
    private static final String FINIQUITO_DOWNLOAD_PARTIAL_NAME = "Tabla informativa de finiquito_";
    private static final String FINIQUITO_EXTENSION = ".xlsx";
    private final DownloadManager downloadManager = new DownloadManager();
    // The fields are displayed in read-only mode correctly.
    public static final String INSURANCE_CARRIER_NAME_FIELD = "//div[contains(@id,'insuranceCarrierName')] | //input[contains(@id,'insuranceCarrierName')]";
    public static final String VEHICLE_SERIAL_FIELD = "//div[contains(@id,'vehicleSerial')] | //input[contains(@id,'vehicleSerial')]";
    public static final String STEP_VEHICLE_INFO_TITLE = "//li[contains(@class, 'ant-steps-item-active')][contains(.,'Información del vehículo')]";
    public static final String STEP_LOCATION_INFO_TITLE = "//li[contains(@class, 'ant-steps-item-active')][contains(.,'Información de ubicación')]";
    public static final String WORK_SHOP_NAME_FIELD = "//div[contains(@id,'workshopName')] | //input[contains(@id,'workshopName')]";
    public static final String WORK_SHOP_CODE_FIELD = "//div[contains(@id,'workshopCode')] | //input[contains(@id,'workshopCode')]";



//Compensation field
public static final String INSURED_NAME_FIELD = "//input[contains(@id,'insuredName')]";
public static final String PHONE_NUMBER_FIELD = "//input[contains(@id,'phoneNumber')]";
public static final String INSURED_EMAIL_FIELD = "//input[contains(@id,'step-form_email')]";
public static final String OBSERVATIONS_FIELD = "//input[contains(@id,'observations')]";
public static final String ADD_COVERAGE_FIELD = "(//button[contains(@type,'button')])[6]";
public static final String COVERS_NAME_FIELD = "//input[contains(@id,'covers-name')]";
public static final String COVERS_VALUE_FIELD = "//input[contains(@id,'covers-value')]";
public static final String ADD_DEDUCTIONS_FIELD = "(//button[contains(@type,'button')])[7]";
public static final String DEDUCTIONS_NAME_FIELD = "//input[contains(@id,'deductions-name')]";
public static final String DEDUCTIONS_VALUE_FIELD = "//input[contains(@id,'deductions-value')]";
public static final String ASSURED_SUM_FIELD = "//input[contains(@id,'assuredSum')]";
public static final String NOT_ACCRUED_PREMIUM_FIELD = "//input[contains(@id,'notAccruedPremium')]";
public static final String DEDUCTIBLE_FIELD = "//input[contains(@id,'deductible')]";
public static final String PENDING_PREMIUM_FIELD = "//input[contains(@id,'pendingPremium')]";


    @FindBy(xpath = PROVIDER_FIELD)
    WebElement providerField;
    @FindBy(xpath = PROVIDER_SUCURSAL_FIELD)
    WebElement providerSucursalField;
    @FindBy(xpath = PROVIDER_CAR_POUND_FIELD)
    WebElement providerCarPoundField;
    @FindBy(xpath = PROVIDER_CAR_POUND_SUCURSAL_FIELD)
    WebElement providerCarPoundSucursalField;
    @FindBy(xpath = CALENDAR_FIELD) 
    WebElement calendarField;
    @FindBy(xpath = CALENDAR_TODAY_FIELD) 
    WebElement calendarTodayField;
    @FindBy(xpath = DISTANCE_TYPE_FIELD)
    WebElement distanceTypeField;
    @FindBy(xpath = COST_FIELD)
    WebElement costField;
    @FindBy(xpath = CHARGE_FIELD)
    WebElement chargeField;
    @FindBy(xpath = COMMENTS_FIELD)
    WebElement commentsField;

    @FindBy(xpath = ADD_CONTACT)
    WebElement addContactButton;
    @FindBy(xpath = CONTACT_NAME_FIELD)
    WebElement contactNameField;
    @FindBy(xpath = CONTACT_PHONE_FIELD)
    WebElement contactPhoneField;
    @FindBy(xpath = SAVE_ADD_CONTACT)
    WebElement saveAddContactButton;

    @FindBy(xpath = STEP_VEHICLE_INFO_TITLE)
    WebElement stepVehicleInfoTitle;
    @FindBy(xpath = STEP_LOCATION_INFO_TITLE)
    WebElement stepLocationInfoTitle;
    @FindBy(xpath = INSURANCE_CARRIER_NAME_FIELD)
    WebElement insureCarrierNameField;
    @FindBy(xpath = VEHICLE_SERIAL_FIELD)
    WebElement vehicleSerialField;
    @FindBy(xpath = WORK_SHOP_NAME_FIELD)
    WebElement workshopNameField;
    @FindBy(xpath = WORK_SHOP_CODE_FIELD)
    WebElement workshopCodeField;




    @FindBy(xpath = INSURED_NAME_FIELD)
    WebElement insuredNameField;
    @FindBy(xpath = PHONE_NUMBER_FIELD)
    WebElement phonenumberField;
    @FindBy(xpath = INSURED_EMAIL_FIELD)
    WebElement insuredEmailField;
    @FindBy(xpath = OBSERVATIONS_FIELD)
    WebElement observationsField;
    @FindBy(xpath = ADD_COVERAGE_FIELD)
    WebElement addCoverage;
    @FindBy(xpath = COVERS_NAME_FIELD)
    WebElement coversNameField;
    @FindBy(xpath = COVERS_VALUE_FIELD)
    WebElement coversValueField;
    @FindBy(xpath = ADD_DEDUCTIONS_FIELD)
    WebElement addDeductions;
    @FindBy(xpath = DEDUCTIONS_NAME_FIELD)
    WebElement deductionsNameField;
    @FindBy(xpath = DEDUCTIONS_VALUE_FIELD)
    WebElement deductionsValueField;
    @FindBy(xpath = ASSURED_SUM_FIELD)
    WebElement assuredSumField;
    @FindBy(xpath = NOT_ACCRUED_PREMIUM_FIELD)
    WebElement notAccruedPremiumField;
    @FindBy(xpath = DEDUCTIBLE_FIELD)
    WebElement deductibleField;
    @FindBy(xpath = PENDING_PREMIUM_FIELD)
    WebElement pendingPremiumField;


    private static final String TEXT_CONTACT_NAME = "CONTACT AUTOMATION TEST";
    private static final String TEXT_CONTACT_PHONE = "1234567890";

    private enum ReadOnlyStep {
        VEHICLE_STEP("Vehicle Step", "Los campos del Paso Vehículo no son de solo lectura."),
        LOCATION_STEP("Location Step", "Los campos del Paso Ubicación no son de solo lectura.");

        private final String stepName;
        private final String errorMessage;

        ReadOnlyStep(String stepName, String errorMessage) {
            this.stepName = stepName;
            this.errorMessage = errorMessage;
        }
    }


    public GenerationofTransfer() {
        super();
    }


    private void clickAddContactButton() {
        waitForElementToBeClickable(addContactButton, 5000);
        click(addContactButton);
    }

    public void clickSaveAddContactButton() {
        waitForElementToBeClickable(saveAddContactButton, 5000);
        click(saveAddContactButton);
    }

    public void fillDestinationInformation() {
        // Fill the destination information fields
        var ccomponets = new CommonComponents();
        ccomponets.selectFromDropdownText(providerField, "FERNANDO REGRESION");
        ccomponets.waitForSpinner();

        ccomponets.selectFromDropdownText(providerSucursalField, "QA REGRESION");
        ccomponets.waitForSpinner();
        ccomponets.selectFromDropdownText(providerCarPoundField, "CHAVARRIA");
        ccomponets.waitForSpinner();
        ccomponets.selectFromDropdownText(providerCarPoundSucursalField, "CCAO CHIAPAS");
        ccomponets.waitForSpinner();

        // Fill the radio input distance type
        WebElement radioButton = getElement(By.xpath(LOCAL_TRANSFER));
        if (!radioButton.isSelected()) {
            radioButton.click();
        }
        click(calendarField);
        waitForElementVisibility((calendarTodayField), Timeouts.LOAD_RESULTS);
        click(calendarTodayField);
        sendKeys(costField, "1000");
        sendKeys(chargeField, "500");
        sendKeys(commentsField, "This is a test comment for the transfer case.");
        
        log.info("Destination fields filled.");

        // Click the continue button to proceed
        Buttons buttons = new Buttons();
        log().image("Filling destination information.", takeScreenshot());
        buttons.jsClickAcceptButton();
    }



    public void fillCalculateCompensationAmount() {
        // Fill the Calculate Compensation information fields
        sendKeys(assuredSumField, "12455");
        sendKeys(notAccruedPremiumField, "1200");
        sendKeys(deductibleField, "1255");
        sendKeys(pendingPremiumField, "500");
        log.info("Compensation fields filled.");
        // Click the accept button to proceed
        Buttons buttons = new Buttons();
        log().image("Filling compensation information.", takeScreenshot());
        buttons.jsClickAcceptButton();
        buttons.jsClickAcceptButtonBuyer();
        log.info("Confirmation button pressed");
        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.WAIT_FOR_NOTIFICATION);
        log().image("Publication creation successfull: ", takeScreenshot());
        waitForElementInvisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_FADED);
        downloadManager.assertDownloadedFileByPartialName(FINIQUITO_DOWNLOAD_PARTIAL_NAME, FINIQUITO_EXTENSION, 40);
        downloadManager.openDownloadedFileByPartialName(FINIQUITO_DOWNLOAD_PARTIAL_NAME, FINIQUITO_EXTENSION, 10);
    }







    public String clickCaseResult() {
        log.info("Starting clickCaseResult method.");
        String vinOfCaseNum = "";
        boolean found = false;

        do {
            log.info("In do-while loop");
            int casesInPage = casesWithoutTransfers();
            if (casesInPage > 0) {
                log.info("Cases found in page: " + casesInPage);
                WebElement caseInResults = getElement(By.xpath(CASES_WITHOUT_TRANSFERS));
                String currentCaseNum = getSpecificAttributeValue(caseInResults, "text");
                log.info("First Case number found: " + currentCaseNum);
                log().image("Clicking next button for pagination.", takeScreenshot());

                String caseNumberXPath = SECOND_TABLE.replace("?", currentCaseNum);
                vinOfCaseNum = getText(getElement(By.xpath(caseNumberXPath)));
                log.info("Vin found: " + vinOfCaseNum);

                jsClick(caseInResults);
                found = true;
            } else {
                log.info("Cases found in page: " + casesInPage);
                if (isNextButtonEnabled()) {
                    log().image("Clicking next button for pagination.", takeScreenshot());
                    clickNextButton();
                } else {
                    log.info("Case Found {} exiting do loop", found);
                    break;
                }
            }
            log.info("Case Found {} exiting while loop", found);
        } while (!found);

        if (found) {
            log.info("Case to be clickeable: " + vinOfCaseNum);
            processCase();
        }

        return vinOfCaseNum;
    }

    public int casesWithoutTransfers() {
        int totalResults = getElements(By.xpath(TOTAL_RESULTS_IN_PAGE)).size();
        int totalTransfers = getElements(By.xpath(TOTAL_TRANSFERS_IN_PAGE)).size();
        return totalResults - totalTransfers;
    }

    private boolean isNextButtonEnabled() {
        return waitForElementToBeClickable(getElement(By.xpath(NEXT_BUTTON_PAGINATION)), Timeouts.LOAD_ELEMENT);
    }

    private void clickNextButton() {
        getElement(By.xpath(NEXT_BUTTON_PAGINATION)).click();
        waitForElementVisibility(getElement(By.xpath(REPORT_BUTTON)), Timeouts.LOAD_RESULTS);
    }


   


    private boolean isReadOnlyField(WebElement element) {
        String readonly = element.getDomAttribute("readonly");
        String disabled = element.getDomAttribute("disabled");
        String ariaDisabled = element.getDomAttribute("aria-disabled");

        boolean hasReadonly = readonly != null
                && ("true".equalsIgnoreCase(readonly) || "readonly".equalsIgnoreCase(readonly));
        boolean hasDisabled = disabled != null
                && ("true".equalsIgnoreCase(disabled) || "disabled".equalsIgnoreCase(disabled));
        boolean hasAriaDisabled = "true".equalsIgnoreCase(ariaDisabled);

        return hasReadonly || hasDisabled || hasAriaDisabled || !element.isEnabled();
    }

    


    private void validateFieldsReadOnly(ReadOnlyStep step, List<WebElement> fields) {
        for (WebElement field : fields) {
            waitForElementVisibility(field, Timeouts.LOAD_ELEMENT);
        }

        boolean areReadOnly = fields.stream().allMatch(this::isReadOnlyField);

        if (!areReadOnly) {
            log().image("Read-only validation FAILED for " + step.stepName + ".", takeScreenshot());
            throw new AssertionError(step.errorMessage);
        }

        log().image("Read-only validation PASSED for " + step.stepName + ".", takeScreenshot());
    }


    private void validateReadOnlyStep(ReadOnlyStep step) {
        switch (step) {
            case VEHICLE_STEP:
                validateFieldsReadOnly(step, List.of(insureCarrierNameField, vehicleSerialField));
                break;
            case LOCATION_STEP:
                validateFieldsReadOnly(step, List.of(workshopNameField, workshopCodeField));
                break;
            default:
                throw new IllegalArgumentException("Unsupported read-only step: " + step);
        }
    }




public void processCase() {
    log().image("In case ", takeScreenshot());
    Buttons buttons = new Buttons();
    buttons.clickAssignTransferButton();
    // --- VALIDACIÓN PASO 1 (VEHÍCULO) ---
    validateReadOnlyStep(ReadOnlyStep.VEHICLE_STEP);
    buttons.clickContinueBtn();
    // --- VALIDACIÓN PASO 2 (UBICACIÓN) ---
    validateReadOnlyStep(ReadOnlyStep.LOCATION_STEP);
    clickAddContactButton();
    waitForElementVisibility(contactNameField, Timeouts.LOAD_ELEMENT);
    sendKeys(contactNameField, TEXT_CONTACT_NAME);
    sendKeys(contactPhoneField, TEXT_CONTACT_PHONE);
    clickSaveAddContactButton();
    log().image("Contact information filled.", takeScreenshot());
    buttons.clickContinueBtn();

    fillDestinationInformation();
    waitForElementVisibility(getElement(By.xpath(NO_RESULTS_LABEL)), Timeouts.LOAD_HEAVY_RESULTS);
    
}



public void compensationCase() {
    log().image("In case ", takeScreenshot());
    Buttons buttons = new Buttons();
    buttons.clickCompensationButton();
    waitForElementVisibility(insuredNameField, Timeouts.LOAD_ELEMENT);
    sendKeys(insuredNameField, "Sesbatian Carlos Gomez");
    sendKeys(phonenumberField, "4424567687");
    sendKeys(insuredEmailField, "provedorau@yopmail.com");
    buttons.clickContinueBtn();
    fillObservationsIfEnabled("Este es un comentario QA REGRESIÓN");
    buttons.clickContinueBtn();
    click(addCoverage);
    sendKeys(coversNameField, "Familia");
    sendKeys(coversValueField, "1000");
    click(addDeductions);
    sendKeys(deductionsNameField, "SEGURO VERSA");
    sendKeys(deductionsValueField, "1000");
    fillCalculateCompensationAmount();
    /*clickSaveAddContactButton();
    log().image("Contact information filled.", takeScreenshot());
    clickSaveAddContactButton();

    
    waitForElementVisibility(getElement(By.xpath(NO_RESULTS_LABEL)), Timeouts.LOAD_HEAVY_RESULTS); */
    
}

private void fillObservationsIfEnabled(String comment) {
    List<WebElement> observationElements = getElements(By.xpath(OBSERVATIONS_FIELD));
    if (observationElements.isEmpty()) {
        log.info("Observations field is not present for this case type. Skipping comment.");
        return;
    }

    WebElement observation = observationElements.get(0);
    boolean isReady = observation.isDisplayed() && observation.isEnabled();
    if (!isReady) {
        log.info("Observations field is present but disabled. Skipping comment.");
        return;
    }

    sendKeys(observation, comment);
    log.info("Observations comment added.");
}

}