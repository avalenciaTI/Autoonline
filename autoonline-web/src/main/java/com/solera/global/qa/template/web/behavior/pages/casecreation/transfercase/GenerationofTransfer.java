package com.solera.global.qa.template.web.behavior.pages.casecreation.transfercase;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CompleteWebElement;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


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

    @FindBy(xpath = PROVIDER_FIELD)
    WebElement providerField;
    @FindBy(xpath = PROVIDER_SUCURSAL_FIELD)
    WebElement providerSucursalField;
    @FindBy(xpath = PROVIDER_CAR_POUND_FIELD)
    WebElement providerCarPoundField;
    @FindBy(xpath = PROVIDER_CAR_POUND_SUCURSAL_FIELD)
    WebElement providerCarPoundSucursalField;
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

    private static final String TEXT_CONTACT_NAME = "CONTACT AUTOMATION TEST";
    private static final String TEXT_CONTACT_PHONE = "1234567890";


    public GenerationofTransfer() {
        super();
    }


    private void clickAddContactButton() {
        waitForElementToBeClickable(addContactButton, 5000);
        click(addContactButton);
    }

    private void clickSaveAddContactButton() {
        waitForElementToBeClickable(saveAddContactButton, 5000);
        click(saveAddContactButton);
    }

    private void fillDestinationInformation() {
        // Fill the destination information fields
        var ccomponets = new CommonComponents();
        ccomponets.selectFromDropdownText(providerField, "FERNANDO REGRESION");
        ccomponets.waitForSpinner();

        ccomponets.selectFromDropdownText(providerSucursalField, "QA TESTS AUTOMATION TRANSFERS VENDOR");
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

        sendKeys(costField, "1000");
        sendKeys(chargeField, "500");
        sendKeys(commentsField, "This is a test comment for the transfer case.");
        log.info("Destination fields filled.");

        // Click the continue button to proceed
        Buttons buttons = new Buttons();
        log().image("Filling destination information.", takeScreenshot());
        buttons.clickAcceptButton();
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

    private void processCase() {
        log().image("In case ", takeScreenshot());
        Buttons buttons = new Buttons();
        buttons.clickAssignTransferButton();
        buttons.clickContinueBtn();

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

}