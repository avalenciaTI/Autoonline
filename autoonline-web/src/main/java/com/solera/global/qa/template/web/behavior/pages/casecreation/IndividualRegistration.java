package com.solera.global.qa.template.web.behavior.pages.casecreation;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.AolWebPropertiesReader;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.data.types.Case;
import com.solera.global.qa.template.web.behavior.data.types.Sinister;
import com.solera.global.qa.template.web.behavior.data.types.Vehicle;
import com.solera.global.qa.template.web.behavior.data.types.Workshop;
import com.solera.global.qa.template.web.behavior.pages.casecreation.transfercase.GenerationofTransfer;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonLocationFields;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonSinisterField;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CompleteWebElement;
import com.solera.global.qa.template.web.behavior.pages.componentpages.search.CaseSearch;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IndividualRegistration extends BrowserPage {

    CommonLocationFields locationFields = new CommonLocationFields();
    CommonSinisterField sinisterFields = new CommonSinisterField();

    //DROPDOWN elements or similar
    public static final String CASE_TYPE_FIELD = "//div[contains(@id,'caseType')]";
    public static final String MANAGEMENT_TYPE_FIELD = "//div[contains(@id,'caseToSell')]";
    public static final String CASE_WRECK_TYPE_FIELD = "//div[contains(@id,'wreckTypeId')]";
    public static final String CASE_WRECK_SUB_TYPE_FIELD = "//div[contains(@id,'wreckSubtypeId')]";
    public static final String CASE_ENGINE_TYPE_FIELD = "//div[contains(@id,'engineTypeId')]";
    public static final String CASE_UNIT_TYPE_FIELD = "//div[contains(@id,'vehicleTypeId')]";
    //input elements
    public static final String CASE_NUMBER_FIELD = "//input[contains(@id,'caseNumber')]";
    public static final String CASE_REPORT_FIELD = "//input[contains(@data-testid,'data_test_niu')]";
    public static final String CASE_INSURANCE_POLICY_FIELD = "//input[contains(@id,'insurancePolicy')]";
    public static final String CASE_BRAND_FIELD = "//input[contains(@id,'brand')]";
    public static final String CASE_VEHICLE_TYPE_FIELD = "//input[contains(@id,'_type')]";
    public static final String CASE_VEHICLE_VERSION = "//input[contains(@id,'_version')]";
    public static final String CASE_COLOR_FIELD = "//input[contains(@id,'color')]";
    public static final String CASE_MODEL_FIELD = "//input[contains(@id,'model')]";
    public static final String CASE_POLICY_SERIAL_FIELD = "//input[contains(@id,'policySerial')]";
    public static final String CASE_VIN_FIELD = "//input[contains(@id,'vehicleSerial')]";
    public static final String CASE_VEHICLE_PLATE = "//input[contains(@id,'vehiclePlate')]";
    public static final String CASE_ENGINE_NUMBER_FIELD = "//input[contains(@id,'engineNumber')]";

    //location
    //dropdown elements
    public static final String CASE_LOCATION_TYPE_FIELD = "//div[contains(@id,'locationTypeId')]";
    //input elements
    public static final String CASE_WORKSHOP_NUMBER_FIELD = "//input[contains(@id,'workshopNumber')]";
    public static final String CASE_WORKSHOP_NAME_FIELD = "//input[contains(@id,'workshopName')]";
    public static final String SPINNER = "//i[contains(@aria-label,'loading') and contains(@class,'loading')]";
    //ASSIGN_TRANSFER
    public static final String BTN_ASSIGN_TRANSFER = "//button[@type='button'][contains(.,'Asignar traslado')]";
    public static final String CRANE_PROVIDER_FIELD =
    "//div[contains(@id,'craneProviderId')] | //input[contains(@id,'providerBusinessName')]";
    public static final String CRANE_BRANCH_FIELD =
    "//div[contains(@id,'craneBranchId')] | //input[contains(@id,'branchName')]";
    public static final String IMPOUNDMENT_PROVIDER_FIELD = "//div[contains(@id,'provider') or "
    + "contains(@id,'carPoundId')]";
    public static final String IMPOUNDMENT_BRANCH_FIELD = "//div[contains(@id,'branch')]";
    public static final String LOCAL_TRANSFER_TYPE_BUTTON = "//input[@value='1']/parent::span";
    public static final String COST_FIELD = "//input[contains(@id,'number') or contains(@id,'cost')]";
    public static final String CHARGE_FIELD = "step-form_charge";
    public static final String CALENDAR_FIELD = "//input[contains(@class,'ant-calendar-picker-input ant-input')]";
    public static final String COMMENTS_FIELD =
    "//textarea[@autocomplete='off'][contains(@id,'comments')]";
    public static final String ADD_CONTACT = "//button[@type='button'][contains(.,'Agregar contacto')]";
    public static final String CONTACT_NAME_FIELD = "//div[contains(@id,'contactName')] | //input[contains(@id,'contactName')]";
    public static final String CONTACT_PHONE_FIELD = "//div[contains(@id,'contactPhone')] | //input[contains(@id,'contactPhone')]";

    public static final String PAPER_CLIP_BUTTON = "//input[contains(@accept,'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')]";
    public static final String ZIP_CLIP_BUTTON = "//input[@type='file' and contains(@accept,'zip')]";
    private static final String SEND_MASSIVE_BUTTON = "//span[text()='Enviar']/parent::button[@type='submit']";
    private static final String RESUME_VIEW_SECTION =
            "//div[contains(@class, 'ant-modal-content')][.//div[contains(@class, 'ant-modal-body') and contains(., '0 registros tienen errores')]]";
    private static final String RESUME_VIEW_SECTION_ZIP = "//p[contains(., \"exitosamente\") and not(contains(., \"0 registros\")) and not(contains(., \"0 zips\"))]";
    public static final String CHECK_MASSIVE_ZIP_BUTTON  = "//button[@jest-id='UploadInputJest' and .//i[contains(@class, 'anticon-check')]]";
    public static final String LOADING_MASSIVE_ZIP_BUTTON  = "//button[contains(@class, 'ant-btn-loading') and .//span[text()='Espere...']]";
     public static final String ACCEPT_BUTTON_ZIP  = "//div[contains(@class, 'ant-modal-footer')]//button[normalize-space(.)='Aceptar']";

    @FindBy(xpath = CASE_TYPE_FIELD)
    WebElement caseType;
    @FindBy(xpath = CASE_WRECK_TYPE_FIELD)
    WebElement wreckType;
    @FindBy(xpath = CASE_WRECK_SUB_TYPE_FIELD)
    WebElement wreckSubType;
    @FindBy(xpath = CASE_ENGINE_TYPE_FIELD)
    WebElement engineType;
    @FindBy(xpath = CASE_UNIT_TYPE_FIELD)
    WebElement unitType;
    @FindBy(xpath = CASE_NUMBER_FIELD)
    WebElement numberSinister;
    @FindBy(xpath = CASE_REPORT_FIELD)
    WebElement report;
    @FindBy(xpath = CASE_INSURANCE_POLICY_FIELD)
    WebElement insurancePolicy;
    @FindBy(xpath = CASE_BRAND_FIELD)
    WebElement brandVehicle;
    @FindBy(xpath = CASE_VEHICLE_TYPE_FIELD)
    WebElement vehicleType;

    @FindBy(xpath = CASE_VEHICLE_VERSION)
    WebElement vehicleVersion;
    @FindBy(xpath = CASE_COLOR_FIELD)
    WebElement color;
    @FindBy(xpath = CASE_MODEL_FIELD)
    WebElement modelYear;
    @FindBy(xpath = CASE_POLICY_SERIAL_FIELD)
    WebElement policySerial;
    @FindBy(xpath = CASE_VIN_FIELD)
    WebElement vin;
    @FindBy(xpath = CASE_VEHICLE_PLATE)
    WebElement vehiclePlate;
    @FindBy(xpath = CASE_ENGINE_NUMBER_FIELD)
    WebElement engineNumber;
    @FindBy(xpath = CASE_LOCATION_TYPE_FIELD)
    WebElement locationType;
    @FindBy(xpath = CASE_WORKSHOP_NUMBER_FIELD)
    WebElement workshopNumber;
    @FindBy(xpath = CASE_WORKSHOP_NAME_FIELD)
    WebElement workshopName;
    //section assign transfer
    @FindBy(xpath = BTN_ASSIGN_TRANSFER)
    WebElement btnAssignTransfer;
    @FindBy(xpath = CRANE_PROVIDER_FIELD)
    WebElement craneProviderField;
    @FindBy(xpath = CRANE_BRANCH_FIELD)
    WebElement craneBranchField;
    @FindBy(xpath = IMPOUNDMENT_PROVIDER_FIELD)
    WebElement impoundmentProviderField;
    @FindBy(xpath = IMPOUNDMENT_BRANCH_FIELD)
    WebElement impoundmentBranchField;
    @FindBy(xpath = LOCAL_TRANSFER_TYPE_BUTTON)
    WebElement localTransferTypeButton;
    @FindBy(xpath = COST_FIELD)
    WebElement costField;
    @FindBy(id = CHARGE_FIELD) 
    WebElement chargeField;
    @FindBy(xpath = ADD_CONTACT) 
    WebElement addContact;
    @FindBy(xpath = CONTACT_NAME_FIELD) 
    WebElement contactNameField;
    @FindBy(xpath = CONTACT_PHONE_FIELD) 
    WebElement contactPhoneField;
    @FindBy(xpath = CALENDAR_FIELD) 
    WebElement calendarField;
    @FindBy(xpath = COMMENTS_FIELD) 
    WebElement commentsField;
    @FindBy(xpath = ACCEPT_BUTTON_ZIP) 
    WebElement acceptButtonZip;





    //Common Elements
    WebElement country = locationFields.getCountryField();
    WebElement state = locationFields.getStateField();
    WebElement street = locationFields.getStreetField();
    WebElement exteriorNumber = locationFields.getExteriorNumberField();
    WebElement interiorNumber = locationFields.getInteriorNumberField();
    WebElement zipCode = locationFields.getZipCodeField();
    WebElement neighborhoodField = locationFields.getNeighborhoodField();
    WebElement town = locationFields.getTownField();
    WebElement city = locationFields.getCityField();

    WebElement insuranceSelected = sinisterFields.getInsuranceSelected();
    WebElement compensationValue = sinisterFields.getCompensationValue();
    WebElement commercialValue = sinisterFields.getCommercialValue();
    WebElement baseValue = sinisterFields.getBaseValue();
    WebElement sparePartsCost = sinisterFields.getSparePartsCost();
    WebElement repairCost = sinisterFields.getRepairCost();
    WebElement observations = sinisterFields.getObservations();
    WebElement c1Field = sinisterFields.getC1Field();
    WebElement c2Field = sinisterFields.getC2Field();
    WebElement c3Field = sinisterFields.getC3Field();
    List<CompleteWebElement> storedValues = new ArrayList<>();

    ///
    private static final String CASE_TYPE_VAL = "vehículos";
    public static final String SEARCH_DYNAMIC = "//td[text()='?']";
    private static final String CLASS_NAME = "IndividualRegistration";
    private static final String CURRENT_DOM_VALUE = "VALUE DOM: ";
    private static final String DOM_STORED_VALUE = " VALUE STORED: ";

    private static final String NOTIFICATION_MESSAGE = "//div[@class='ant-notification-notice-message']";
    public static final String INPUT = "input";
    
    // XPath to extract Siniestro/Número de Lote column from the resume view table
    private static final String SINIESTRO_NIU_COLUMN_XPATH = 
        "//table/tbody/tr[contains(@class, 'ant-table-row')]/td[count(ancestor::table/thead/tr/th[contains(., \"Siniestro/Número de Lote\")]/preceding-sibling::th) + 1]";

    private static final String VISUAL_SERIE_COLUMN_XPATH = 
        "//table/tbody/tr[contains(@class, 'ant-table-row')]/td[count(ancestor::table/thead/tr/th[contains(., \"Serie visual\")]/preceding-sibling::th) + 1]";
    
    // Static list to store extracted Siniestro/NIU values after massive registration
    private static List<String> extractedSiniestroValues = new ArrayList<>();

     // Static list to store extracted Visual Serie values after massive registration
    private static List<String> extractedVisualSerieValues = new ArrayList<>();

    ///

    public IndividualRegistration() {
        super();
    }

    public List<CompleteWebElement> quickVehicleCaseTypeCreation(String vin, String nameCase, Case caseData) {
        log.info("In quickVehicleCaseTypeCreation");
        new CommonComponents().selectFromDropdownText(caseType, CASE_TYPE_VAL);
        log.info("After select dropdown");
        Vehicle vehicle = caseData.getVehicle();
        storedValues = fillField(numberSinister, nameCase, storedValues);
        storedValues = fillField(report,vehicle.getFieldReport(), storedValues);
        storedValues = fillField(insurancePolicy, vehicle.getPolicy(), storedValues);
        storedValues = fillField(wreckType,vehicle.getWreckType(), storedValues);
        storedValues = fillField(wreckSubType,vehicle.getWreckSubtype(), storedValues);
        storedValues = fillField(brandVehicle,vehicle.getVehicleBrand(), storedValues);
        storedValues = fillField(vehicleType,vehicle.getVehicleType(), storedValues);
        storedValues = fillField(vehicleVersion,vehicle.getVehicleVersion(), storedValues);
        storedValues = fillField(color,vehicle.getVehicleColor(), storedValues);
        storedValues = fillField(modelYear,vehicle.getModelYear(), storedValues);
        storedValues = fillField(policySerial,vehicle.getPolicySerial(), storedValues);
        storedValues = fillField(this.vin,vin, storedValues);
        storedValues = fillField(vehiclePlate,vehicle.getPlate(), storedValues);
        storedValues = fillField(engineNumber,vehicle.getEngineNumber(), storedValues);
        storedValues = fillField(engineType,vehicle.getEngineType(), storedValues);
        storedValues = fillField(unitType,vehicle.getUnitType(), storedValues);

        log().image("IndividualRegistration - Vehicle Case Creation 1", takeScreenshot());
        new Buttons().clickContinueBtn();

        Workshop workshop = caseData.getWorkshop();
        storedValues = fillField(locationType,workshop.getLocation(), storedValues);
        storedValues = fillField(workshopNumber,workshop.getWorkshopNumber(), storedValues);
        storedValues = fillField(workshopName,workshop.getName(), storedValues);
        storedValues = fillField(country,workshop.getCountry(), storedValues);
        storedValues = fillField(state,workshop.getState(), storedValues);
        storedValues = fillField(street, workshop.getStreet(), storedValues);
        storedValues = fillField(exteriorNumber, workshop.getExternalNumber(), storedValues);
        storedValues = fillField(interiorNumber, workshop.getInternalNumber(), storedValues);
        storedValues = fillField(zipCode, workshop.getZipCode(), storedValues);
        storedValues = fillField(neighborhoodField, workshop.getNeighborhood(), storedValues);
        storedValues = fillField(town,workshop.getTown(), storedValues);
        storedValues = fillField(city,"CDMX", storedValues);

        log().image("IndividualRegistration - Vehicle Case Creation 2", takeScreenshot());
        new Buttons().clickContinueBtn();

        Sinister sinister = caseData.getSinister();
        storedValues = fillField(insuranceSelected,sinister.getInsurer(), storedValues);
        storedValues = fillField(compensationValue,sinister.getCompensationValue(), storedValues);
        storedValues = fillField(commercialValue,sinister.getCommertialValue(), storedValues);
        storedValues = fillField(baseValue,sinister.getBaseValue(), storedValues);
        storedValues = fillField(sparePartsCost,sinister.getSpareCost(), storedValues);
        storedValues = fillField(repairCost,sinister.getRepairCost(), storedValues);
        storedValues = fillField(observations,sinister.getObservations(), storedValues);
        storedValues = fillField(c1Field,sinister.getC1Field(), storedValues);
        storedValues = fillField(c2Field,sinister.getC2Field(), storedValues);
        storedValues = fillField(c3Field,sinister.getC3Field(), storedValues);

        log().image("IndividualRegistration - Vehicle Case Creation 3", takeScreenshot());
        new Buttons().jsClickAcceptButton();
        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.WAIT_FOR_NOTIFICATION);
        log().image("Clicked accept button to initialize transfer and notificadion displayed", takeScreenshot());
        log.info("Transfer initialized successfully");
        waitForElementInvisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_FADED);
        log().image("Clicked accept button to initialize transfer and notificadion faded", takeScreenshot());
        return storedValues;

    }


//tc87_caseTransfersGeneration
public List<CompleteWebElement> fillVehicleCaseTypeGeneration(String vin, String numbersinister, Case caseData) {
    log.info("In fillVehicleCaseTypeGeneration:");
    WebElement caseTypeSel = getElement(By.xpath(CASE_TYPE_FIELD));
    log.info("Case type selector found");
    new CommonComponents().selectFromDropdownText(caseTypeSel,"Vehículos");
    log.info("After select dropdown");
    Vehicle vehicle = caseData.getVehicle();
        storedValues = fillField(numberSinister, numbersinister, storedValues);
        storedValues = fillField(report,vehicle.getFieldReport(), storedValues);
        storedValues = fillField(insurancePolicy, vehicle.getPolicy(), storedValues);
        storedValues = fillField(wreckType,vehicle.getWreckType(), storedValues);
        storedValues = fillField(wreckSubType,vehicle.getWreckSubtype(), storedValues);
        storedValues = fillField(brandVehicle,vehicle.getVehicleBrand(), storedValues);
        storedValues = fillField(vehicleType,vehicle.getVehicleType(), storedValues);
        storedValues = fillField(vehicleVersion,vehicle.getVehicleVersion(), storedValues);
        storedValues = fillField(color,vehicle.getVehicleColor(), storedValues);
        storedValues = fillField(modelYear,vehicle.getModelYear(), storedValues);
        storedValues = fillField(policySerial,vehicle.getPolicySerial(), storedValues);
        storedValues = fillField(this.vin,vin, storedValues);
        storedValues = fillField(vehiclePlate,vehicle.getPlate(), storedValues);
        storedValues = fillField(engineNumber,vehicle.getEngineNumber(), storedValues);
        storedValues = fillField(engineType,vehicle.getEngineType(), storedValues);
        storedValues = fillField(unitType,vehicle.getUnitType(), storedValues);

        log().image("IndividualRegistration - Vehicle Case Creation 1", takeScreenshot());
        new Buttons().clickContinueBtn();

        Workshop workshop = caseData.getWorkshop();
        storedValues = fillField(locationType,workshop.getLocation(), storedValues);
        storedValues = fillField(workshopNumber,workshop.getWorkshopNumber(), storedValues);
        storedValues = fillField(workshopName,workshop.getName(), storedValues);
        storedValues = fillField(country,workshop.getCountry(), storedValues);
        storedValues = fillField(state,workshop.getState(), storedValues);
        storedValues = fillField(street, workshop.getStreet(), storedValues);
        storedValues = fillField(exteriorNumber, workshop.getExternalNumber(), storedValues);
        storedValues = fillField(interiorNumber, workshop.getInternalNumber(), storedValues);
        storedValues = fillField(zipCode, workshop.getZipCode(), storedValues);
        storedValues = fillField(neighborhoodField, workshop.getNeighborhood(), storedValues);
        storedValues = fillField(town,workshop.getTown(), storedValues);
        storedValues = fillField(city,"CDMX", storedValues);

        log().image("IndividualRegistration - Vehicle Case Creation 2", takeScreenshot());
        new Buttons().clickContinueBtn();

        Sinister sinister = caseData.getSinister();
        storedValues = fillField(insuranceSelected,sinister.getInsurer(), storedValues);
        storedValues = fillField(compensationValue,sinister.getCompensationValue(), storedValues);
        storedValues = fillField(commercialValue,sinister.getCommertialValue(), storedValues);
        storedValues = fillField(baseValue,sinister.getBaseValue(), storedValues);
        storedValues = fillField(sparePartsCost,sinister.getSpareCost(), storedValues);
        storedValues = fillField(repairCost,sinister.getRepairCost(), storedValues);
        storedValues = fillField(observations,sinister.getObservations(), storedValues);
        storedValues = fillField(c1Field,sinister.getC1Field(), storedValues);
        storedValues = fillField(c2Field,sinister.getC2Field(), storedValues);
        storedValues = fillField(c3Field,sinister.getC3Field(), storedValues);

        log().image("IndividualRegistration - Vehicle Case Creation 3", takeScreenshot());
        new Buttons().jsClickAcceptButton();
        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.WAIT_FOR_NOTIFICATION);
        log().image("Clicked accept button to initialize transfer and notificadion displayed", takeScreenshot());
        log.info("Transfer initialized successfully");
        waitForElementInvisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_FADED);
        log().image("Clicked accept button to initialize transfer and notificadion faded", takeScreenshot());
    return storedValues;

}


//tc87_caseTransfersGeneration
public List<CompleteWebElement> fillVehicleSectionAssign() {
    log.info("In fillVehicleSectionAssign:");
    new Buttons().clickContinueBtn();
    click(addContact);
    storedValues = fillField(contactNameField,"JORGE QA REGRESION",storedValues);
    storedValues = fillField(contactPhoneField,"4425663456",storedValues);
    new GenerationofTransfer().clickSaveAddContactButton();
    new GenerationofTransfer().processCase();
    new Buttons().clickContinueBtn();
    new GenerationofTransfer().fillDestinationInformation();
    waitForElementInvisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_FADED);
    log().image("Clicked accept button to initialize transfer and notificadion faded", takeScreenshot());
    return storedValues;

}









    public Integer validateCaseCreation(List<CompleteWebElement> completeWebElements) {
        log.info("In validateCaseCreation");
        waitForElementVisibility(getDriver().findElement(By
                .xpath("//input[contains(@id,'caseNumber') and string-length(@value)>0]")), Timeouts.SHORT_TIME);

        int numberOfFieldsCorrect = 0;
        int currentTab = 1;

        for (CompleteWebElement element : completeWebElements) {
            if (currentTab != element.getIndexTab()) {
                //Change if to a wait for element visibility ask for help
                currentTab++;
                click(getDriver().findElement(By.xpath(
                        "(//div[contains(@class,'ant-steps-navigation')]/child::div)[" + Integer
                                .toString(currentTab) + "]//div[@role='button']")));
            } else {
                String valueDOM = getWebElementDOMValues(element.getWebElement());
                String valueStored = element.getDesiredValue();
                if (valueDOM.equalsIgnoreCase(valueStored)) {
                    log.info("VALUE STORED=CONSULTED {} = {} {} = {}",
                            CURRENT_DOM_VALUE, valueDOM, DOM_STORED_VALUE, valueStored);
                    log().image("VALUE STORED=CONSULTED", takeScreenshot());
                } else {
                    log.error("VALUE STORED!=CONSULTED CURRENT: {} = {}, STORED {} = {}",
                            CURRENT_DOM_VALUE, valueDOM, DOM_STORED_VALUE, valueStored);
                    log().image("VALUE STORED!=CONSULTED", takeScreenshot());
                }
                numberOfFieldsCorrect++;
            }
        }
        return numberOfFieldsCorrect;
    }

    private String getWebElementDOMValues(WebElement element) {
        if (element.getTagName().contains("input")) {
            return getSpecificAttributeValue(element, "aria-valuenow") != null
                    ? getSpecificAttributeValue(element, "aria-valuenow")
                    : getValue(element);

        } else if (element.getTagName().contains("div")) {
            waitForSpinner();
            WebElement element2find = element.findElement(By.xpath(".//div[@title]"));
            return getSpecificAttributeValue(element2find, "title");
        } else {
            return "";
        }
    }

    private void waitForSpinner() {
        By loadingLocator = By.xpath(SPINNER);

        FluentWait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(3))
                .pollingEvery(Duration.ofMillis(5L))
                .ignoring(NoSuchElementException.class);

        try {
            getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            boolean isPresent = wait.until(loader -> getDriver().findElement(loadingLocator).isDisplayed());
            if (isPresent) {
                log.info("SPINNER FOUND");
                waitForElementInvisibility(getDriver().findElement(loadingLocator), Timeouts.ELEMENT_INVISIBILITY);
            }
        } catch (Exception ex) {
            log.info("Not Spinner: {}", ex.getMessage());
        } finally {
            getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            log.info("In Finally");
        }
    }

    private List<WebElement> elementsInActualView(Map<WebElement,String> dictionary) {
        List<WebElement> elementsMainView = new ArrayList<>();
        for (WebElement element:dictionary.keySet()) {
            if (element.isDisplayed()) {
                elementsMainView.add(element);
            }
        }
        return elementsMainView;
    }


    private List<CompleteWebElement> fillField(WebElement webElement, String value,
            List<CompleteWebElement> list) {
        if (webElement.getTagName().contains("input")) {
            sendKeys(webElement,value);
        } else if (webElement.getTagName().contains("div")) {
            new CommonComponents().selectFromDropdownText(webElement,value);
        }
        getDriver().findElements(By.xpath("//div[contains(@class,'ant-steps-navigation')]/child::div"));

        CompleteWebElement completeWebElement = new CompleteWebElement(webElement);
        completeWebElement.setDesiredValue(value);
        completeWebElement.setIndexTab(getIndex());
        list.add(completeWebElement);
        return list;
    }

    private List<WebElement> getWebElements() {
        List<WebElement> webElementsList = new ArrayList<>();
        webElementsList = getDriver().findElements(By.xpath("//input[contains(@id,'form')]"));
        return webElementsList;
    }

    private Integer getIndex() {
        List<WebElement> elements = getDriver()
                .findElements(By.xpath("//div[contains(@class,'ant-steps-navigation')]/child::div"));
        WebElement indexElement = getDriver()
                .findElement(By.xpath("//div[contains(@class,'ant-steps-item-active')]"));
        return (elements.indexOf(indexElement) + 1);
    }


    private void fillField(WebElement webElement, String value) {
        if (webElement.getTagName().contains(INPUT)) {
            sendKeys(webElement,value);
        } else if (webElement.getTagName().contains("div")) {
            new CommonComponents().selectFromDropdownText(webElement,value);
        }
    }



    /////
    public boolean vehicleIndividualRegistration(String vin, Case caseData) {
        MenuPage menuPage = new MenuPage();//menu managment.
        RegistrationMenu casesMenu = menuPage.clickCases();
        casesMenu.clickIndividualRegistrationCase();
        IndividualRegistration vehicleRegistration = new IndividualRegistration();
        final List<CompleteWebElement> values2Validate = vehicleRegistration
                .quickVehicleCaseTypeCreation(vin,"Test veh " + vin, caseData);

        log.info("Case created, searching case");
        sleep(5000);
        menuPage.clickCases();
        casesMenu.clickSearchCases(CASE_TYPE_VAL, vin);

        new CommonComponents().findHRefElement(new CommonComponents()
                .dynamicWebElement(SEARCH_DYNAMIC, vin));

        log.info("Searching case in results");
        Integer results = vehicleRegistration.validateCaseCreation(values2Validate);
        log().image(CLASS_NAME + " correct " + results, takeScreenshot());
        return true;
    }

    public boolean miscellaneousGeneralConsultation() {
        MenuPage menuPage = new MenuPage();
        RegistrationMenu casesMenu = menuPage.clickCases();
        log.info("Navigating to Cases > Consulta");
        casesMenu.consultCases();
        log().image("Consult cases page loaded", takeScreenshot());
        log.info("Running general search with type DIVERSOS");
        new CaseSearch().generalSearch(CaseType.VARIOUS, "ANA2604001377");
        log().image("Miscellaneous consultation results", takeScreenshot());
        return true;
    }


    public boolean miscellaneousAdvancedQuery() {
        MenuPage menuPage = new MenuPage();
        RegistrationMenu casesMenu = menuPage.clickCases();
        log.info("Navigating to Cases > Consulta");
        casesMenu.consultCases();
        log().image("Consult cases page loaded", takeScreenshot());
        log.info("Running Advance Query");
        new CaseSearch().advanceSearchQuery(CaseType.VARIOUS, "ANA2604001377");
        log().image("Miscellaneous consultation results", takeScreenshot());
        return true;
    }

    public boolean vehicleIndividualRegistration_AddImages(String vin, Case caseData) {
        MenuPage menuPage = new MenuPage();//menu managment.
        RegistrationMenu casesMenu = menuPage.clickCases();
        casesMenu.clickIndividualRegistrationCase();
        IndividualRegistration vehicleRegistration = new IndividualRegistration();
        final List<CompleteWebElement> values2Validate = vehicleRegistration
                .quickVehicleCaseTypeCreation(vin,"Test veh " + vin, caseData);

        log.info("Case created, searching case");
        sleep(5000);
        menuPage.clickCases();
        casesMenu.clickSearchCases(CASE_TYPE_VAL, vin);

        new CommonComponents().findHRefElement(new CommonComponents()
                .dynamicWebElement(SEARCH_DYNAMIC, vin));

        log.info("Case found, navigated to case detail");
        Integer results = vehicleRegistration.validateCaseCreation(values2Validate);
        log().image(CLASS_NAME + " case created correctly: " + results, takeScreenshot());

        log.info("Adding images to Fotografías del caso section");
        Photos photos = new Photos();
        photos.attachImages(true);
        photos.markAsFavorite();
        log().image(CLASS_NAME + " images attached and marked as favorite", takeScreenshot());

        return true;
    }




    public boolean caseGenerationTransfer(String vin, Case caseData) {
        
        MenuPage menuPage = new MenuPage();//menu managment.
        RegistrationMenu casesMenu = menuPage.clickCases();
        /*casesMenu.clickIndividualRegistrationCase();
        IndividualRegistration vehicleRegistration = new IndividualRegistration();

        IndividualRegistration IndividualRegistration = new IndividualRegistration();
       // final List<CompleteWebElement> values2Validate = vehicleRegistration
            //    .fillVehicleCaseTypeGeneration(vin,"TESTVEHAUTOAMTED"+ vin, caseData);
        IndividualRegistration.fillVehicleCaseTypeGeneration(vin,"TESTVEHAUTOAMTED"+ vin, caseData); */
        log.info("Case created, searching case");
        sleep(5000);
        menuPage.clickCases();
        casesMenu.clickSearchCases("Vehículos",vin);

        new CommonComponents().findHRefElement(new CommonComponents()
                .dynamicWebElement(SEARCH_DYNAMIC, vin));

        log.info("Searching case in results");
        new GenerationofTransfer().processCase();
       // click(btnAssignTransfer);
        //final List<CompleteWebElement> values3Validate = vehicleRegistration
       // .fillVehicleSectionAssign();
    
        //Integer results = vehicleRegistration.validateCaseCreation(values2Validate);
       // Integer results2 = vehicleRegistration.validateCaseCreation(values3Validate);
       // log().image(CLASS_NAME + " correct " + results, takeScreenshot());
       // log().image(CLASS_NAME + " correct " + results2, takeScreenshot());

        return true;
    }



    public boolean caseGenerationCompensation(String vin, Case caseData) {
        
        MenuPage menuPage = new MenuPage();//menu managment.
        RegistrationMenu casesMenu = menuPage.clickCases();
        /*casesMenu.clickIndividualRegistrationCase();
        IndividualRegistration vehicleRegistration = new IndividualRegistration();

        IndividualRegistration IndividualRegistration = new IndividualRegistration();
       // final List<CompleteWebElement> values2Validate = vehicleRegistration
            //    .fillVehicleCaseTypeGeneration(vin,"TESTVEHAUTOAMTED"+ vin, caseData);
        IndividualRegistration.fillVehicleCaseTypeGeneration(vin,"TESTVEHAUTOAMTED"+ vin, caseData); */
        log.info("Case created, searching case");
        sleep(5000);
        menuPage.clickCases();
        casesMenu.clickSearchCases("Vehículos",vin);

        new CommonComponents().findHRefElement(new CommonComponents()
                .dynamicWebElement(SEARCH_DYNAMIC, vin));

        log.info("Searching case in results");
        new GenerationofTransfer().compensationCase();
       // click(btnAssignTransfer);
        //final List<CompleteWebElement> values3Validate = vehicleRegistration
       // .fillVehicleSectionAssign();
    
        //Integer results = vehicleRegistration.validateCaseCreation(values2Validate);
       // Integer results2 = vehicleRegistration.validateCaseCreation(values3Validate);
       // log().image(CLASS_NAME + " correct " + results, takeScreenshot());
       // log().image(CLASS_NAME + " correct " + results2, takeScreenshot());

        return true;
    }





     public boolean miscellaneousMassRecord() {
        MenuPage menuPage = new MenuPage();
        RegistrationMenu casesMenu = menuPage.clickCases();
        casesMenu.clickMassiveRegistrationCase();
        log.info("In massive registration:");
        WebElement caseTypeSel = getElement(By.xpath(CASE_TYPE_FIELD));
        log.info("Case type selector found");
        new CommonComponents().selectFromDropdownText(caseTypeSel,"Diversos");
        log.info("After select dropdown");
        String xlsxFolderName = "miscellaneousMassiveRecord";
        String zipFolderName = "miscellaneousMassiveRecordZIP";
        uploadMassiveFiles(xlsxFolderName);
        submitMassiveRegistration();
        attachImagesWithZip(zipFolderName);
        submitMassiveZipRegistration();

        log.info("Cases created via massive registration, now extracting Siniestro/NIU values from resume view");
        

        log.info("Extracted Siniestro/NIU values: {}", extractedSiniestroValues);

        log.info("Cases created via massive registration, now searching for created case");
        sleep(5000);
        //menuPage.clickCases();
       // casesMenu.consultCases();
        log().image("Navigated to Cases consultation", takeScreenshot());

        boolean caseFound = false;
        if (!extractedSiniestroValues.isEmpty()) {
            String firstSiniestro = extractedSiniestroValues.get(0);
            log.info("Running general search with first Siniestro/NIU: {}", firstSiniestro);
            caseFound = new CaseSearch().generalSearch(CaseType.VARIOUS, firstSiniestro);
        } else {
            log.warn("No Siniestro/NIU values extracted, using fallback search");
            caseFound = new CaseSearch().generalSearch(CaseType.VARIOUS, "TESTTRANSFERAUTOMATEDQA26");
        }

        if (caseFound) {
            log.info("Case found successfully after massive registration");
        } else {
            log.warn("Case not found after massive registration");
        }

        log().image("Case search after massive registration", takeScreenshot());
        return caseFound;
    }




 public boolean massiveVehicleRegistrationPublication() {
        MenuPage menuPage = new MenuPage();
        RegistrationMenu casesMenu = menuPage.clickCases();
        casesMenu.clickMassiveRegistrationCase();
        log.info("Filled vehicle case type generation");
        WebElement caseTypeSel = getElement(By.xpath(CASE_TYPE_FIELD));
        log.info("Case type selector found");
        new CommonComponents().selectFromDropdownText(caseTypeSel,"Vehículos");

        WebElement managementTypeSel = getElement(By.xpath(MANAGEMENT_TYPE_FIELD));
        log.info("Management type selector found");
        new CommonComponents().selectFromDropdownText(managementTypeSel,"Publicación");
        log.info("After select dropdown");

        String xlsxFolderName = "massiveVehicleRegistrationPublication";
        String zipFolderName = "massiveVehicleRegistrationPublicationZIP";
        uploadMassiveFiles(xlsxFolderName);
        submitmassiveVehicleRegistration();
        attachImagesWithZip(zipFolderName);
        submitMassiveZipRegistration();

        log.info("Cases created via massive registration, now extracting Siniestro/NIU values from resume view");
        log.info("Extracted Visual Serie values: {}", extractedVisualSerieValues);
        log.info("Cases created via massive registration, now searching for created case");
        sleep(5000);
        //menuPage.clickCases();
       // casesMenu.consultCases();
        log().image("Navigated to Cases consultation", takeScreenshot());

        boolean caseFound = false;
        if (!extractedVisualSerieValues.isEmpty()) {
            String firstVisualSerie = extractedVisualSerieValues.get(0);
            log.info("Running general search with first Visual Serie: {}", firstVisualSerie);
            caseFound = new CaseSearch().generalSearch(CaseType.VEHICLES, firstVisualSerie);
        } else {
            log.warn("No Siniestro/NIU values extracted, using fallback search");
            caseFound = new CaseSearch().generalSearch(CaseType.VEHICLES, "N1SCIVICQA3");
        }

        if (caseFound) {
            log.info("Case found successfully after massive registration");
        } else {
            log.warn("Case not found after massive registration");
        }

        log().image("Case search after massive registration", takeScreenshot());
        return caseFound;
    }






 public boolean massiveVehicleRegistrationManagement() {
        MenuPage menuPage = new MenuPage();
        RegistrationMenu casesMenu = menuPage.clickCases();
        casesMenu.clickMassiveRegistrationCase();
        log.info("Filled vehicle case type generation");
        WebElement caseTypeSel = getElement(By.xpath(CASE_TYPE_FIELD));
        log.info("Case type selector found");
        new CommonComponents().selectFromDropdownText(caseTypeSel,"Vehículos");

        WebElement managementTypeSel = getElement(By.xpath(MANAGEMENT_TYPE_FIELD));
        log.info("Management type selector found");
        new CommonComponents().selectFromDropdownText(managementTypeSel,"Gestión");
        log.info("After select dropdown");

        String xlsxFolderName = "massiveVehicleRegistrationManagement";
        String zipFolderName = "massiveVehicleRegistrationManagementZIP";
        uploadMassiveFiles(xlsxFolderName);
        submitmassiveVehicleRegistration();
        attachImagesWithZip(zipFolderName);
        submitMassiveZipRegistration();

        log.info("Cases created via massive registration, now extracting Siniestro/NIU values from resume view");
        log.info("Extracted Visual Serie values: {}", extractedVisualSerieValues);
        log.info("Cases created via massive registration, now searching for created case");
        sleep(5000);
        //menuPage.clickCases();
       // casesMenu.consultCases();
        log().image("Navigated to Cases consultation", takeScreenshot());

        boolean caseFound = false;
        if (!extractedVisualSerieValues.isEmpty()) {
            String firstVisualSerie = extractedVisualSerieValues.get(0);
            log.info("Running general search with first Visual Serie: {}", firstVisualSerie);
            caseFound = new CaseSearch().generalSearch(CaseType.VEHICLES, firstVisualSerie);
        } else {
            log.warn("No Siniestro/NIU values extracted, using fallback search");
            caseFound = new CaseSearch().generalSearch(CaseType.VEHICLES, "N1SCIVICQA3");
        }

        if (caseFound) {
            log.info("Case found successfully after massive registration");
        } else {
            log.warn("Case not found after massive registration");
        }

        log().image("Case search after massive registration", takeScreenshot());
        return caseFound;
    }






    /**
     * Extracts Siniestro/Número de Lote values from the resume view table
     * and stores them in the static list extractedSiniestroValues.
     */
    private void extractSiniestroValuesFromResumeView() {
        extractedSiniestroValues = new ArrayList<>();
        try {
            List<WebElement> siniestroCells = getElements(By.xpath(SINIESTRO_NIU_COLUMN_XPATH));
            log.info("Found {} Siniestro/NIU cells in resume view", siniestroCells.size());
            for (WebElement cell : siniestroCells) {
                String value = getText(cell).trim();
                if (!value.isEmpty()) {
                    extractedSiniestroValues.add(value);
                    log.info("Extracted Siniestro/NIU: '{}'", value);
                }
            }
        } catch (Exception e) {
            log.warn("Could not extract Siniestro/NIU values from resume view: {}", e.getMessage());
        }
    }




      /**
     * Extracts Visual Serie values from the resume view table
     * and stores them in the static list extractedVisualSerieValues.
     */
    private void extractVisualSerieValuesFromResumeView() {
        extractedVisualSerieValues = new ArrayList<>();
        try {
            List<WebElement> visualSerieCells = getElements(By.xpath(VISUAL_SERIE_COLUMN_XPATH));
            log.info("Found {} Visual Serie cells in resume view", visualSerieCells.size());
            for (WebElement cell : visualSerieCells) {
                String value = getText(cell).trim();
                if (!value.isEmpty()) {
                    extractedVisualSerieValues.add(value);
                    log.info("Extracted Visual Serie: '{}'", value);
                }
            }
        } catch (Exception e) {
            log.warn("Could not extract Visual Serie values from resume view: {}", e.getMessage());
        }
    }





    private void uploadMassiveFiles(String xlsxFolderName) {
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



     private void submitMassiveRegistration() {
        log.info("Submitting bulk registration");
        waitForElementToBeClickable(getElement(By.xpath(SEND_MASSIVE_BUTTON)), Timeouts.LOAD_RESULTS);
        log().image("Before clicking Enviar button", takeScreenshot());
        jsClick(getElement(By.xpath(SEND_MASSIVE_BUTTON)));
        log().image("Enviar button clicked", takeScreenshot());

        waitForElementPresence(getElement(By.xpath(RESUME_VIEW_SECTION)), Timeouts.LOAD_ELEMENT);
        log().image("Resume view displayed", takeScreenshot());
        new Buttons().jsClickAcceptButton();
        extractSiniestroValuesFromResumeView();
        new Buttons().clickContinueBtn();
    }

 private void attachImagesWithZip(String zipFolderName) {
     log.info("Uploading bulk files");
        String zipFolder = zipFolderName + "/";
        String attachmentsFolder = AolWebPropertiesReader.getAttachmentsFolder(zipFolder);
        log.info("ZIP ATTACHMENTS FOLDER: {}", attachmentsFolder);

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
        waitForElementPresence(By.xpath(ZIP_CLIP_BUTTON), Timeouts.LOAD_ELEMENT);
        sleep(500);
        getElement(By.xpath(ZIP_CLIP_BUTTON)).sendKeys(files2Send.toString());
        log.info("File uploaded");
       
    }

     private void submitMassiveZipRegistration() {
        log.info("Submitting bulk registration");
        sleep(1000);
        waitForElementPresence(getElement(By.xpath(CHECK_MASSIVE_ZIP_BUTTON)), Timeouts.LOAD_RESULTS);
        sleep(1000);
        new Buttons().jsClickAcceptButton();
        log().image("Check button clicked", takeScreenshot());
        waitForElementPresence(getElement(By.xpath(LOADING_MASSIVE_ZIP_BUTTON)), Timeouts.LOAD_RESULTS);
        waitForElementPresence(By.xpath(RESUME_VIEW_SECTION_ZIP), 60000);
        log().image("Resume view displayed", takeScreenshot());
        sleep(500);
        waitForElementPresence(By.xpath(RESUME_VIEW_SECTION_ZIP), 60000);
        waitForElementToBeClickable(getElement(By.xpath(ACCEPT_BUTTON_ZIP)), Timeouts.LOAD_RESULTS);
        jsClick(acceptButtonZip);
    }



     private void submitmassiveVehicleRegistration() {
        log.info("Submitting bulk registration");
        waitForElementToBeClickable(getElement(By.xpath(SEND_MASSIVE_BUTTON)), Timeouts.LOAD_RESULTS);
        log().image("Before clicking Enviar button", takeScreenshot());
        jsClick(getElement(By.xpath(SEND_MASSIVE_BUTTON)));
        log().image("Enviar button clicked", takeScreenshot());

        waitForElementPresence(getElement(By.xpath(RESUME_VIEW_SECTION)), Timeouts.LOAD_ELEMENT);
        log().image("Resume view displayed", takeScreenshot());
        new Buttons().jsClickAcceptButton();
        extractVisualSerieValuesFromResumeView();
        new Buttons().clickContinueBtn();
    }




}
 