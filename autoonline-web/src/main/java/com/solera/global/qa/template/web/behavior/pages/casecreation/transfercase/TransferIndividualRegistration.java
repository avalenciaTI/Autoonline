package com.solera.global.qa.template.web.behavior.pages.casecreation.transfercase;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.casecreation.RegistrationMenu;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CompleteWebElement;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogInPage;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import com.solera.global.qa.template.web.behavior.pages.usercreation.individualregistration.AdministratorMasterInter;
import com.solera.global.qa.template.web.behavior.pages.usercreation.MassiveRegistrationUsers;



import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;


@Slf4j
public class TransferIndividualRegistration extends BrowserPage {

    public TransferIndividualRegistration() {
        super();
    }

    //DROPDOWN FIELDS
    public static final String CASE_TYPE_FIELD = "//div[contains(@id,'caseType')]";
    public static final String WRECK_TYPE_FIELD = "//div[contains(@id,'wreckType')]";
    public static final String WRECK_SUBTYPE_FIELD = "//div[contains(@id,'wreckSubType') or "
            + "contains(@id,'wreckSubtype')]";
    public static final String ENGINE_TYPE_FIELD =
            "//div[contains(@id,'engineType')] | //input[contains(@id,'engineType')]";
    public static final String VEHICLE_TYPE_FIELD =
            "//div[contains(@id,'vehicleType')] | //input[contains(@id,'vehicleType')]";
    //INPUT FIELDS
    public static final String SINISTER_FIELD = "//input[contains(@id,'caseNumber')]";
    public static final String REPORT_FIELD = "//input[contains(@data-testid,'data_test_niu')]";
    public static final String INSURANCE_POLICY_FIELD = "//input[contains(@id,'insurancePolicy')]";
    public static final String BRAND_FIELD = "//input[contains(@id,'brand') or contains(@id,'marketName')]";
    public static final String TYPE_FIELD = "//input[contains(@id,'_type')]";
    public static final String VERSION_FIELD = "//input[contains(@id,'_version')]";
    public static final String COLOR_FIELD = "//input[contains(@id,'color')]";
    public static final String MODEL_FIELD = "//input[contains(@id,'model')]";
    public static final String POLICY_SERIAL_FIELD = "//input[contains(@id,'policySerial') or "
            + "contains(@id,'policiyserial')]";
    public static final String CASE_VIN_FIELD = "//input[contains(@id,'vehicleSerial')]";
    public static final String PLATE_FIELD = "//input[contains(@id,'vehiclePlate')]";
    public static final String ENGINE_NUMBER_FIELD = "//input[contains(@id,'engineNumber')]";
    public static final String MILEAGE_FIELD = "//input[contains(@id,'mileage')]";

    //LOCATION
    //DROPDOWN FIELDS
    public static final String LOCATION_FIELD = "//div[contains(@id,'locationTypeId')]";
    public static final String COUNTRY_FIELD = "//div[contains(@id,'countryId')] | //input[contains(@id,'country')]";
    public static final String STATE_FIELD = "//div[contains(@id,'stateId')] | //input[contains(@id,'state')]";
    //INPUT FIELDS
    public static final String DESTINATION_NAME_FIELD =
            "//input[contains(@id,'destinationName') or contains(@id,'nameDestination')]";
    public static final String STREET_FIELD = "//input[contains(@id,'roadName')]";
    public static final String OUT_NUMBER_FIELD = "//input[contains(@id,'outNumber')]";
    public static final String IN_NUMBER_FIELD = "//input[contains(@id,'inNumber')]";
    public static final String ZIP_CODE_FIELD = "//input[contains(@id,'zipCode')]";
    public static final String NEIGHBORHOOD_FIELD = "//input[contains(@id,'neighborhood')]";
    public static final String TOWN_FIELD = "//input[contains(@id,'town')]";
    public static final String CITY_FIELD = "//input[contains(@id,'city')]";
    public static final String OBSERVATIONS_FIELD = "//input[contains(@id,'observations')]";

    public static final String INSURANCE_CARRIED_ID_FIELD =
            "//div[contains(@id,'insuranceCarrier')] | //input[contains(@id,'insuranceName')]";
    public static final String BUYER_ID_FIELD = "//div[contains(@id,'buyer')] | //input[contains(@id,'buyer')]";
    public static final String C1_FIELD = "//input[contains(@id,'c1')]";
    public static final String C2_FIELD = "//input[contains(@id,'c2')]";
    public static final String C3_FIELD = "//input[contains(@id,'c3')]";
    //DESTINATION
    //
    public static final String CRANE_PROVIDER_FIELD =
            "//div[contains(@id,'craneProviderId')] | //input[contains(@id,'providerBusinessName')]";
    public static final String CRANE_BRANCH_FIELD =
            "//div[contains(@id,'craneBranchId')] | //input[contains(@id,'branchName')]";
    public static final String IMPOUNDMENT_PROVIDER_FIELD = "//div[contains(@id,'provider') or "
            + "contains(@id,'carPoundId')]";
    public static final String IMPOUNDMENT_BRANCH_FIELD = "//div[contains(@id,'branch')]";
    public static final String LOCAL_TRANSFER_TYPE_BUTTON = "//input[@value='1']/parent::span";

    public static final String OFFER_TYPE_FIELD = "//div[contains(@id,'offerTypeId')]";
    public static final String DAMAGE_TYPE_FIELD = "//input[contains(@id,'damageType')]";
    public static final String DAMAGE_DESCRIPTION_FIELD = "//textarea[contains(@id,'description')]";
    public static final String OBSERVATIONS_DESCRIPTION_FIELD = "//textarea[contains(@id,'observations')]";

    public static final String NAME_FIELD = "//input[contains(@id,'contactName')] ";
    public static final String SURNAME_FIELD = "//input[contains(@id,'contactSurname')]";
    public static final String LAST_NAME_FIELD = "//input[contains(@id,'contactLastName')]";
    public static final String PHONE_NUMBER_FIELD = "//input[contains(@id,'phoneNumber')]";
    public static final String CELL_PHONE_NUMBER_FIELD = "//input[contains(@id,'cellPhoneNumber')]";
    public static final String OTHER_NUMBER_FIELD = "//input[contains(@id,'faxNumber')]";

    public static final String COMPENSATION_VALUE_FIELD = "//input[contains(@id,'compensationValue')]";
    public static final String COMMERCIAL_VALUE_FIELD = "//input[contains(@id,'commercialValue')]";
    public static final String BASE_VALUE_FIELD = "//input[contains(@id,'baseValue')]";

    public static final String FOREIGN_TRANSFER_TYPE_BUTTON = "//input[@value='2']/parent::span";
    public static final String COST_FIELD = "//input[contains(@id,'number') or contains(@id,'cost')]";
    public static final String CHARGE_FIELD = "step-form_charge";

    public static final String CASE_NUMBER_FIELD = "//input[contains(@id,'caseId') and string-length(@value)>0]";
    private static final String NOTIFICATION_WINDOW = "//div[contains(@class,'bottomRight')]";
    private static final String NOTIFICATION_MESSAGE = "//div[@class='ant-notification-notice-message']";
    private static final String UPDATE_BUTTON = "//button[contains(@data-testid,'update')]";
    public static final String INPUT = "input";


    @FindBy(xpath = CASE_TYPE_FIELD)
    WebElement caseType;
    @FindBy(xpath = WRECK_TYPE_FIELD)
    WebElement wreckTypeField;
    @FindBy(xpath = WRECK_SUBTYPE_FIELD)
    WebElement wreckSubtypeField;
    @FindBy(xpath = ENGINE_TYPE_FIELD)
    WebElement engineTypeField;
    @FindBy(xpath = VEHICLE_TYPE_FIELD)
    WebElement vehicleTypeField;
    @FindBy(xpath = SINISTER_FIELD)
    WebElement sinisterField;
    @FindBy(xpath = REPORT_FIELD)
    WebElement reportField;
    @FindBy(xpath = INSURANCE_POLICY_FIELD)
    WebElement insurancePolicyField;
    @FindBy(xpath = BRAND_FIELD)
    WebElement brandField;
    @FindBy(xpath = TYPE_FIELD)
    WebElement typeField;
    @FindBy(xpath = VERSION_FIELD)
    WebElement versionField;
    @FindBy(xpath = COLOR_FIELD)
    WebElement colorField;
    @FindBy(xpath = MODEL_FIELD)
    WebElement modelField;
    @FindBy(xpath = POLICY_SERIAL_FIELD)
    WebElement policySerialField;
    @FindBy(xpath = CASE_VIN_FIELD)
    WebElement caseVinField;
    @FindBy(xpath = PLATE_FIELD)
    WebElement plateField;
    @FindBy(xpath = ENGINE_NUMBER_FIELD)
    WebElement engineNumberField;
    @FindBy(xpath = MILEAGE_FIELD)
    WebElement mileageField;
    @FindBy(xpath = LOCATION_FIELD)
    WebElement locationField;
    @FindBy(xpath = COUNTRY_FIELD)
    WebElement countryField;
    @FindBy(xpath = STATE_FIELD)
    WebElement stateField;
    @FindBy(xpath = DESTINATION_NAME_FIELD)
    WebElement destinationNameField;
    @FindBy(xpath = STREET_FIELD)
    WebElement streetField;
    @FindBy(xpath = OUT_NUMBER_FIELD)
    WebElement outNumberField;
    @FindBy(xpath = IN_NUMBER_FIELD)
    WebElement inNumberField;
    @FindBy(xpath = ZIP_CODE_FIELD)
    WebElement zipCodeField;
    @FindBy(xpath = NEIGHBORHOOD_FIELD)
    WebElement neighborhoodField;
    @FindBy(xpath = TOWN_FIELD)
    WebElement townField;
    @FindBy(xpath = CITY_FIELD)
    WebElement cityField;
    @FindBy(xpath = OBSERVATIONS_FIELD)
    WebElement observationsField;
    @FindBy(xpath = INSURANCE_CARRIED_ID_FIELD)
    WebElement insuranceSelected;
    @FindBy(xpath = BUYER_ID_FIELD)
    WebElement buyerSelected;
    @FindBy(xpath = C1_FIELD)
    WebElement c1Field;
    @FindBy(xpath = C2_FIELD)
    WebElement c2Field;
    @FindBy(xpath = C3_FIELD)
    WebElement c3Field;
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
    @FindBy(xpath = FOREIGN_TRANSFER_TYPE_BUTTON)
    WebElement foreignTransferTypeButton;
    @FindBy(xpath = COST_FIELD)
    WebElement costField;
    @FindBy(xpath = OFFER_TYPE_FIELD)
    WebElement offerTypeField;

    @FindBy(xpath = DAMAGE_TYPE_FIELD)
    WebElement damageTypeField;

    @FindBy(xpath = DAMAGE_DESCRIPTION_FIELD)
    WebElement damageDescriptionField;
    @FindBy(xpath = OBSERVATIONS_DESCRIPTION_FIELD)
    WebElement observationsDescriptionField;

    @FindBy(xpath = NAME_FIELD)
    WebElement nameField;
    @FindBy(xpath = SURNAME_FIELD)
    WebElement surNameField;
    @FindBy(xpath = LAST_NAME_FIELD)
    WebElement lastNameField;
    @FindBy(xpath = PHONE_NUMBER_FIELD)
    WebElement phoneNumberField;
    @FindBy(xpath = CELL_PHONE_NUMBER_FIELD)
    WebElement cellPhoneNumberField;
    @FindBy(xpath = OTHER_NUMBER_FIELD)
    WebElement otherNumberField;

    @FindBy(xpath = COMPENSATION_VALUE_FIELD)
    WebElement compensationValue;
    @FindBy(xpath = COMMERCIAL_VALUE_FIELD)
    WebElement commercialValue;
    @FindBy(xpath = BASE_VALUE_FIELD)
    WebElement baseValue;

    @FindBy(id = CHARGE_FIELD) WebElement chargeField;
    @FindBy(xpath = CASE_NUMBER_FIELD)
    WebElement caseNumberField;
    @FindBy(xpath = NOTIFICATION_WINDOW)
    WebElement notificationWindow;
    @FindBy(xpath = UPDATE_BUTTON)
    WebElement updateButton;

    ///
    public static final String SEARCH_DYNAMIC = "//td[text()='?']";
    private static final String CLASS_NAME = "TransferIndividualRegistration";
    ///
    /// 
    ///     
    // 

    List<CompleteWebElement> storedValues = new ArrayList<>();

    public List<CompleteWebElement> fillTransfer(String vin, String sinister) {
        log.info("In fillTransfer:");
        WebElement caseTypeSel = getElement(By.xpath(CASE_TYPE_FIELD));
        log.info("Case type selector found");
        new CommonComponents().selectFromDropdownText(caseTypeSel,"Traslados");

        storedValues = fillField(sinisterField,sinister,storedValues);
        storedValues = fillField(reportField,"wreckSubtypeAutomated",storedValues);
        storedValues = fillField(insurancePolicyField,"PLZ1620",storedValues);
        storedValues = fillField(wreckTypeField,"Incendio",storedValues);
        storedValues = fillField(wreckSubtypeField,"Chatarra",storedValues);
        storedValues = fillField(brandField,"NISSAN",storedValues);
        storedValues = fillField(typeField,"SUV",storedValues);
        storedValues = fillField(versionField,"Luxury",storedValues);
        storedValues = fillField(colorField,"Red",storedValues);
        storedValues = fillField(modelField,"2024",storedValues);
        storedValues = fillField(policySerialField,"PLZSERIES1623",storedValues);
        storedValues = fillField(caseVinField,vin,storedValues);
        storedValues = fillField(plateField,"PLZ1620",storedValues);
        storedValues = fillField(engineNumberField,"engineNumberAutomated",storedValues);
        storedValues = fillField(engineTypeField,"Otro",storedValues);
        storedValues = fillField(vehicleTypeField,"Autos",storedValues);
        storedValues = fillField(mileageField,"15000",storedValues);
        log().image("first step of the transfer case creation", takeScreenshot());
        new Buttons().clickContinueBtn();
        storedValues = fillField(locationField,"OTRO",storedValues);
        storedValues = fillField(destinationNameField,"destinationNameAutomated",storedValues);
        storedValues = fillField(countryField,"Mexico",storedValues);
        storedValues = fillField(stateField,"Jalisco",storedValues);
        storedValues = fillField(streetField,"streetAutomated",storedValues);
        storedValues = fillField(outNumberField,"123",storedValues);
        storedValues = fillField(inNumberField,"123",storedValues);
        storedValues = fillField(zipCodeField,"12345",storedValues);
        storedValues = fillField(neighborhoodField,"neighborhoodAutomated",storedValues);
        storedValues = fillField(townField,"townAutomated",storedValues);
        storedValues = fillField(cityField,"cityAutomated",storedValues);
        storedValues = fillField(observationsField,"observationsAutomated",storedValues);
        log().image("second step of the transfer case creation", takeScreenshot());
        new Buttons().clickContinueBtn();
        storedValues = fillField(insuranceSelected,"QA TESTS AUTOMATION",storedValues);
        sleep(2000);
        storedValues = fillField(buyerSelected,"TESTER 1",storedValues);
        storedValues = fillField(c1Field,"c1Automated",storedValues);
        storedValues = fillField(c2Field,"c2Automated",storedValues);
        storedValues = fillField(c3Field,"c3Automated",storedValues);
        log().image("Click Button Acept", takeScreenshot());

        
        new Buttons().clickContinueBtn();
        //new Buttons().jsClickAcceptButton();


        storedValues = fillField(craneProviderField,"FERNANDO REGRESION",storedValues);
        storedValues = fillField(craneBranchField,"QA REGRESION",storedValues);

        fillField(impoundmentProviderField,"FERNANDO REGRESION");   // no identifier at verification
        fillField(impoundmentBranchField,"QA REGRESION");           // no identifier at verification
        log().image("third step of the transfer case creation", takeScreenshot());

        click(localTransferTypeButton);
        storedValues = fillField(costField,"1000",storedValues);
        fillField(chargeField, "1000");
        new Buttons().jsClickAcceptButton();
        waitForElementPresence(notificationWindow.findElement(By.xpath(NOTIFICATION_MESSAGE)));
        return storedValues;
    }

    public List<CompleteWebElement> fillTransferDiverse(String vin, String sinister) {
        log.info("In fillTransfer:");
        WebElement caseTypeSel = getElement(By.xpath(CASE_TYPE_FIELD));
        log.info("Case type selector found");
        //new CommonComponents().selectFromDropdownText(caseTypeSel,"Traslados");
        new CommonComponents().selectFromDropdownText(caseTypeSel,"Diversos");

        //storedValues = fillField(sinisterField, sinister, storedValues);
        storedValues = fillField(sinisterField, AdministratorMasterInter.generateDynamicName(), storedValues);

        storedValues = fillField(insuranceSelected, "ANA SEGUROS", storedValues);
        storedValues = fillField(offerTypeField, "Por lote", storedValues);
        storedValues = fillField(damageTypeField, "Golpe en la puerta", storedValues);
        click(damageDescriptionField);
        storedValues = fillField(damageDescriptionField, "description Automated tEST", storedValues);
        click(observationsDescriptionField);
        storedValues = fillField(observationsDescriptionField,"observations Automated TEST",storedValues);


        
        log().image("first step of the transfer case creation", takeScreenshot());
        new Buttons().clickContinueBtn();
        //storedValues = fillField(locationField,"OTRO",storedValues);
        //storedValues = fillField(destinationNameField,"destinationNameAutomated",storedValues);
        storedValues = fillField(countryField,"Mexico",storedValues);
        storedValues = fillField(stateField,"Jalisco",storedValues);
        storedValues = fillField(streetField,"streetAutomated",storedValues);
        storedValues = fillField(outNumberField,"123",storedValues);
        storedValues = fillField(inNumberField,"123",storedValues);
        storedValues = fillField(zipCodeField,"12345",storedValues);
        storedValues = fillField(neighborhoodField,"neighborhoodAutomated",storedValues);
        storedValues = fillField(townField,"townAutomated",storedValues);
        storedValues = fillField(cityField,"cityAutomated",storedValues);
        //storedValues = fillField(observationsField,"observationsAutomated",storedValues);
        log().image("second step of the transfer case creation", takeScreenshot());
        new Buttons().clickContinueBtn();

        
        storedValues = fillField(nameField, "Ernesto Automation", storedValues);
        storedValues = fillField(surNameField, "Zarate Delete", storedValues);
        storedValues = fillField(lastNameField, "Admin Master", storedValues);
        storedValues = fillField(phoneNumberField, "5544872277", storedValues);
        storedValues = fillField(cellPhoneNumberField, "5544778899", storedValues);
        storedValues = fillField(otherNumberField, "5588996641", storedValues);

        log().image("third step of the transfer case creation", takeScreenshot());
        new Buttons().clickContinueBtn();
        storedValues = fillField(compensationValue, "10000", storedValues);
        storedValues = fillField(commercialValue, "30000", storedValues);
        storedValues = fillField(baseValue, "20000", storedValues);

        storedValues = fillField(c1Field, "c1Automated", storedValues);
        storedValues = fillField(c2Field, "c2Automated", storedValues);
        storedValues = fillField(c3Field, "c3Automated", storedValues);
        //click(localTransferTypeButton);
        //storedValues = fillField(costField,"1000",storedValues);
        //fillField(chargeField, "1000");
        new Buttons().jsClickAcceptButton();
        waitForElementPresence(notificationWindow.findElement(By.xpath(NOTIFICATION_MESSAGE)),Timeouts.NOTIFICATION_DISPLAYED);



        return storedValues;
    }

    private List<CompleteWebElement> fillField(WebElement webElement, String value,
            List<CompleteWebElement> list) {
        if (webElement.getTagName().contains(INPUT) || webElement.getTagName().contains("textarea")) {
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

    private void fillField(WebElement webElement, String value) {
        if (webElement.getTagName().contains(INPUT)) {
            sendKeys(webElement,value);
        } else if (webElement.getTagName().contains("div")) {
            new CommonComponents().selectFromDropdownText(webElement,value);
        }
    }

    private Integer getIndex() {
        List<WebElement> elements = getDriver()
                .findElements(By.xpath("//div[contains(@class,'ant-steps-navigation')]/child::div"));
        WebElement indexElement = getDriver()
                .findElement(By.xpath("//div[contains(@class,'ant-steps-item-active')]"));
        return (elements.indexOf(indexElement) + 1);
    }

    public Integer validateCaseCreation(List<CompleteWebElement> completeWebElements) {
        int numberOfFieldsCorrect = 0;
        int currentTab = 1;
        updateButton.click();
        waitForElementVisibility(caseNumberField);


        for (CompleteWebElement element : completeWebElements) {
            if (currentTab != element.getIndexTab()) {
                //Change if to a wait for element visibility ask for help
                currentTab++;
                WebElement tabElement = getElement(By.xpath(
                        "(//div[contains(@class,'ant-steps-navigation')]/child::div)["
                                + Integer.toString(currentTab) + "]"));
                moveToElement(tabElement);
                click(tabElement);
            } else {
                String valueDOM = getWebElementDOMValues(element.getWebElement());
                String valueStored = element.getDesiredValue();
                if (valueDOM.equalsIgnoreCase(valueStored)) {
                    log.info("VALUE STORED=CONSULTED DOM VALUE: {} STORED VALUE: {}", valueDOM, valueStored);
                    log().image("VALUE STORED=CONSULTED ", takeScreenshot());
                    numberOfFieldsCorrect++;
                } else {
                    log.info("VALUE STORED!=CONSULTED DOM VALUE: {} STORED VALUE: {}", valueDOM, valueStored);
                    log().image("VALUE STORED!=CONSULTED ", takeScreenshot());
                }

            }

        }
        return numberOfFieldsCorrect;
    }

    private String getWebElementDOMValues(WebElement element) {

        if (element.getTagName().contains(INPUT)) {
            return  getSpecificAttributeValue(element,"aria-valuenow") != null
                    ? getSpecificAttributeValue(element,"aria-valuenow")
                    : getValue(element);
        } else if (element.getTagName().contains("div")) {
            new CommonComponents().waitForSpinner();
            WebElement element2find = element.findElement(By.xpath(".//div[@title]"));
            return getSpecificAttributeValue(element2find,"title");
        } else {
            return "";
        }
    }

    public boolean transferIndividualRegistration(String vin) {
        MenuPage menuPage = new MenuPage();
        RegistrationMenu casesMenu = menuPage.clickCases();
        casesMenu.clickIndividualRegistrationCase();
        TransferIndividualRegistration transferIndividualRegistration = new TransferIndividualRegistration();
        final List<CompleteWebElement> values2ValidateTransfer = transferIndividualRegistration
                .fillTransfer(vin,"TESTTRANSFERAUTOAMTED");
        menuPage.clickCases();

        casesMenu.clickSearchCases("Traslados",vin);
        new CommonComponents().findHRefElement(new CommonComponents()
                .dynamicWebElement(SEARCH_DYNAMIC,vin));

        Integer results = transferIndividualRegistration.validateCaseCreation(values2ValidateTransfer);
        log().image(CLASS_NAME + " correct " + results,takeScreenshot());
        return true;

    }

    public boolean transferIndividualRegistrationDiverse(String vin) {
        MenuPage menuPage = new MenuPage();
        RegistrationMenu casesMenu = menuPage.clickCases();
        casesMenu.clickIndividualRegistrationCase();
        TransferIndividualRegistration transferIndividualRegistration = new TransferIndividualRegistration();
        final List<CompleteWebElement> values2ValidateTransfer = transferIndividualRegistration
                .fillTransferDiverse(vin,"TESTTRANSFERAUTOAMTED");
        menuPage.clickCases();

        casesMenu.clickSearchCases("Diversos",AdministratorMasterInter.getLastGeneratedName());
        new CommonComponents().findHRefElement(new CommonComponents()
                .dynamicWebElement(SEARCH_DYNAMIC, AdministratorMasterInter.getLastGeneratedName()));

        Integer results = transferIndividualRegistration.validateCaseCreation(values2ValidateTransfer);
        log().image(CLASS_NAME + " correct " + results,takeScreenshot());
        return true;

    }

}
