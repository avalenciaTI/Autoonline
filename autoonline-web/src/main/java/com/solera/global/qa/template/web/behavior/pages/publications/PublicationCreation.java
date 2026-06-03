package com.solera.global.qa.template.web.behavior.pages.publications;


import static com.solera.global.qa.template.web.behavior.pages.publications.PublicationOnline.GENERIC_AUTOMATION_NAME;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.data.tools.TestDateGenerator;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.data.types.CurrentDateTime;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CompleteWebElement;
import com.solera.global.qa.template.web.behavior.pages.componentpages.PublicationSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.TypeOfDate;
import com.solera.global.qa.template.web.behavior.pages.componentpages.submenu.PublicationsOptions;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import com.solera.global.qa.template.web.behavior.pages.payments.Insurers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class PublicationCreation extends BrowserPage {
    //Main Screen
    //DROPDOWN elements or similar
    public static final String CASE_TYPE_FIELD = "//div[contains(@id,'caseType')]";
    public static final String CASE_TYPE_SELECTED = CASE_TYPE_FIELD
            + "/descendant::i[@aria-label='ícono: down']";
    public static final String INSURANCE_COMPANY_FIELD = "//div[contains(@id,'insuranceCarrier')]";

    //Input elements or similar
    public static final String CASE_NUMBER_FIELD = "//input[contains(@id,'name')]";
    public static final String END_DATE_FIELD = "//span[contains(@id,'endDate')]";
    public static final String END_HOUR_FIELD = "//input[contains(@placeHolder,'HH:MM:SS')]";
    public static final String INSURANCE_COST_FIELD =
            "//input[contains(@id,'insuranceCost') or contains(@id,'insCarrierCost')]";
    //publication-form_insuranceCost  -> Registration
    //publication-form_insCarrierCost -> Consult

    public static final String FOCUS_ON_YOU_OFFER_SWITCH = "publication-form_focusOffer";

    //todo review if publication name needs to be modified according to automation
    private static final String PUBLICATION_NAME_XP = "//input[@id='publication-form_name' "
            + "and contains(@value,'TESTAUTOMATION DIV')]";
    public static final String MANAGEMENT_COST_FIELD = "//input[contains(@id,'managementCost')]";
    public static final String PUBLICATION_COMMENTS_FIELD = "//textarea[contains(@id,'comments')]";
    public static final String SHOW_BASE_VALUE_BUTTON = "//button[contains(@id,'showBaseValue')]";
    public static final String SHOW_COMPLETE_VIN_BUTTON = "//button[contains(@id,'showCompleteNumber')]";
    public static final String SHOW_DOCUMENTS_BUTTON = "//button[contains(@id,'showDocs')]";
    public static final String SHOW_MAX_OFFER_BUTTON = "//button[contains(@id,'showMaxOffer')]";
    public static final String SHOW_POSITION_BUTTON = "//button[contains(@id,'showPosition')]";
    //selection screen
    public static final String SEARCH_CASES_FIELD = "//input[contains(@id,'search-cases_wildcard')]";
    public static final String CASE_REVIEW_TAB = "//div[@role='button' and .//div[text()='Casos']]";
    public static final String CASE_RESULTS_TABLE = "//tbody[@class='ant-table-tbody']";
    public static final String PUBLICATION_RESULTS_XP = "//tbody[@class='ant-table-tbody']/descendant::tr"
            + "/descendant::td[text()='?']/following-sibling::td[contains(text(), 'status')]/preceding-sibling::td/a";

    // pre publication and publication list
    public static final String PRE_PUBLICATION_TABLE = ".//table[@class='ant-table-fixed']";
    public static final String SELECT_CASES_TABLE = "//thead[@class='ant-table-thead']";
    private static final String AUTOMATION_CASES = "//tbody[@class='ant-table-tbody']/descendant::tr/descendant::"
            + "td[contains(text(), '" + GENERIC_AUTOMATION_NAME + "')]/following-sibling::td[contains(text(), '?')]"
            + "/following-sibling::td[text()='0']/preceding-sibling::td/descendant::input[@type='checkbox']";

    public static final String SEARCH_DYNAMIC = "//td[contains(text(),'?')]";    ///
    public static final String SEARCH_DYNAMIC_EQUALS = "//td[text()='?']";
    private static final String NOTIFICATION_MESSAGE = "//div[@class='ant-notification-notice-message']";
    private static final String VIEW_BUTTON = "//button[@class='ant-btn ant-btn-primary ant-btn-icon-only' "
            + "or @class='ant-btn ant-btn-round ant-btn-icon-only']";

    private static final String ALL_CASES_XPATH = SELECT_CASES_TABLE + "/descendant::input[@type='checkbox']";

    private static final String RESULTS_REPORT = "//button[@jest-id='reportButton' and span[text()='Reporte de resultados']]";

    @FindBy(xpath = CASE_TYPE_FIELD)
    WebElement caseType;
    @FindBy(xpath = INSURANCE_COMPANY_FIELD)
    WebElement insuranceCompany;
    @FindBy(xpath = CASE_NUMBER_FIELD)
    WebElement publicationName;
    @FindBy(xpath = END_DATE_FIELD)
    WebElement endOfPublicationDate;
    @FindBy(xpath = END_HOUR_FIELD)
    WebElement endOfPublicationHour;
    @FindBy(xpath = INSURANCE_COST_FIELD)
    WebElement insuranceCost;
    @FindBy(xpath = MANAGEMENT_COST_FIELD)
    WebElement managementCost;
    @FindBy(xpath = PUBLICATION_COMMENTS_FIELD)
    WebElement publicationComments;
    @FindBy(xpath = SHOW_BASE_VALUE_BUTTON)
    WebElement toggleButtonBaseValue;
    @FindBy(xpath = SHOW_COMPLETE_VIN_BUTTON)
    WebElement toggleButtonVIN;
    @FindBy(xpath = SHOW_DOCUMENTS_BUTTON)
    WebElement toggleButtonDocuments;
    @FindBy(xpath = SHOW_MAX_OFFER_BUTTON)
    WebElement toggleButtonMaxOffer;
    @FindBy(xpath = SHOW_POSITION_BUTTON)
    WebElement toggleButtonPosition;
    @FindBy(xpath = SEARCH_CASES_FIELD)
    WebElement searchCases;

    @FindBy(xpath = CASE_REVIEW_TAB)
    WebElement caseReview;

    @FindBy(id = FOCUS_ON_YOU_OFFER_SWITCH) WebElement focusOnYourOfferSwitch;

    @FindBy(xpath = SELECT_CASES_TABLE) WebElement selectCasesTable;
    @FindBy(xpath = PRE_PUBLICATION_TABLE) WebElement prePublicationTable;

    ////

    public PublicationCreation() {
        super();
    }

    public List<CompleteWebElement> quickVehicleCaseTypeCreation(String newPublication) {
        CommonComponents components = new CommonComponents();
        List<CompleteWebElement> storedValues = new ArrayList<>();

        //storedValues = components.fillField(caseType, CaseType.VARIOUS.getCaseType(), storedValues);
        storedValues = components.fillField(caseType,CaseType.VEHICLES.getCaseType(), storedValues);
        storedValues = components.fillField(publicationName, newPublication, storedValues);
        //storedValues = components.fillField(publicationName, "TestAutomation DIV 2", storedValues);
        storedValues = components.fillField(insuranceCompany, "ANA SEGUROS", storedValues);

        LocalDate endDate = LocalDate.now();
        endDate = endDate.plusDays(8);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String endDateFormatted = endDate.format(dateFormat);

        new CommonComponents().setCalendarDatesText(endOfPublicationDate,endDateFormatted);
        storedValues = components.fillField(insuranceCost,"1000", storedValues);

        String focurOnYourOffert = getText(focusOnYourOfferSwitch);
        log.info("FOCUS ON YOUR OFFER STATE: " + focurOnYourOffert, true);
        if (focurOnYourOffert.equalsIgnoreCase("Sí")) {
            click(focusOnYourOfferSwitch);
        }
        new CommonComponents().setTimeText(endOfPublicationHour,"08:30:00",managementCost);
        storedValues = components.fillField(managementCost,"1002", storedValues);
        storedValues = components.fillField(publicationComments,"Comentarios Publicación", storedValues);
        click(toggleButtonBaseValue);

        new Buttons().clickSearchBtn();

        searchCase("ANA2403000769");
        searchCase("ANA2403000771");
        new Buttons().clickSearchBtn();
        activatePrePublication("ANA2403000769", "ANA2403000771");
        return storedValues;
    }



    public List<CompleteWebElement> quickVehicleCaseVariousCreation(String newPublication) {
        CommonComponents components = new CommonComponents();
        List<CompleteWebElement> storedValues = new ArrayList<>();
storedValues = components.fillField(caseType, CaseType.VARIOUS.getCaseType(), storedValues);
        storedValues = components.fillField(publicationName, newPublication, storedValues);
        //storedValues = components.fillField(publicationName, "TestAutomation DIV 2", storedValues);
        storedValues = components.fillField(insuranceCompany, "ANA SEGUROS", storedValues);
        LocalDate endDate = LocalDate.now();
        endDate = endDate.plusDays(8);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String endDateFormatted = endDate.format(dateFormat);

        new CommonComponents().setCalendarDatesText(endOfPublicationDate,endDateFormatted);
        storedValues = components.fillField(insuranceCost,"1000", storedValues);

        String focurOnYourOffert = getText(focusOnYourOfferSwitch);
        log.info("FOCUS ON YOUR OFFER STATE: " + focurOnYourOffert, true);
        if (focurOnYourOffert.equalsIgnoreCase("Sí")) {
            click(focusOnYourOfferSwitch);
        }
        new CommonComponents().setTimeText(endOfPublicationHour,"08:30:00",managementCost);
        storedValues = components.fillField(managementCost,"1002", storedValues);
        storedValues = components.fillField(publicationComments,"Comentarios Publicación", storedValues);
        click(toggleButtonBaseValue);
        new Buttons().clickSearchBtn();
        searchCase("ANA2403000769");
        searchCase("ANA2403000771");
        new Buttons().clickSearchBtn();
        return storedValues;
    }

    public List<CompleteWebElement> quickPublicationCreation(String newPublication) {
        log.info("quickPublicationCreation starting");
        CommonComponents components = new CommonComponents();
        List<CompleteWebElement> storedValues = new ArrayList<>();

        storedValues = components.fillField(caseType,CaseType.VEHICLES.getCaseType(), storedValues);
        storedValues = components.fillField(publicationName, newPublication, storedValues);
        storedValues = components.fillField(insuranceCompany,"ANA SEGUROS", storedValues);

        CurrentDateTime currentDateTime = TestDateGenerator.getCUrrentDateTime();
        new CommonComponents().setCalendarDatesText(endOfPublicationDate, currentDateTime.getDate());
        sleep(1000);
        log().image("After setting end date", takeScreenshot());

        new CommonComponents().setTimeText(endOfPublicationHour, currentDateTime.getTime(), publicationName);
        log().image("After setting end hour", takeScreenshot());



        storedValues = components.fillField(publicationComments,"COMENTARIOS PUBLICACION", storedValues);
        click(toggleButtonBaseValue);

        log.info("quick publication created");
        new Buttons().clickSearchBtn();
        log.info("quickPublicationCreation Click on Save and Continue button");

        try {
            waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.WAIT_FOR_NOTIFICATION);
            log().image("Publication creation successfull: ", takeScreenshot());
            waitForElementInvisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_FADED);
            waitForElementVisibility(selectCasesTable, Timeouts.LOAD_HEAVY_RESULTS);
        } catch (Exception e) {
            log.error("Error while waiting for notification message: {}", e.getMessage());
            log().image("Error in publication creation: ", takeScreenshot());
        }
        log.info("quickPublicationCreation finished successfully");
        return storedValues;
    }

    public boolean validateCaseInPublication() {
        log.info("Validating publication");
        sleep(3000);
        click(caseReview);
        sleep(3000);
        log().image("Validating", takeScreenshot());
        List<WebElement> results = getElement(By.xpath(CASE_RESULTS_TABLE)).findElements(By.tagName("tr"));
        for (WebElement result: results) {
            log.info("Result {}", result.getText());
            List<WebElement> resultData = result.findElements(By.tagName("td"));
            String caseStatus = getSpecificAttributeValue(resultData.get(8), "innerText");
            if (!caseStatus.equals("Publicado")) {
                return false;
            }
        }
        return true;
    }


    public void activatePrePublication(String vinCase1, String vinCase2) {
        log.info("Activating pre-publication");

        //Select case
        //waitForCaseTypeLoad();
        log().image("Before search case", takeScreenshot());
        log.info("Activating pre-publication -> searchCase");
        searchCase(vinCase1);
        searchCase(vinCase2);

        //1st Guardar y continuar  // button type submit
        log.info("Activating pre-publication -> clickSearchBtn");
        //new Buttons().clickSearchBtn();

        // -> wait for update publication-pre-publication page
        //waitForTableResultFadeAndReload();
        // -> Seleccionar caso
        // also can use this method selectCaseInList()

        // selecting all cases in publication
        //click(getElement(By.xpath(ALL_CASES_XPATH)));


        new Buttons().waitForPreCaseActivationLoad();
        // -> Activate button
        new Buttons().clickActivateButton();
        new Buttons().jsClickAcceptButtonBuyer();
        log.info("Activating pre-publication -> Activated button pressed");
        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.WAIT_FOR_NOTIFICATION);
        log().image("Publication creation successfull: ", takeScreenshot());
        waitForElementInvisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_FADED);
    }



    public boolean waitForTableResultFadeAndReload() {
        log.info("waitForTableResultFadeAndReload");
        boolean isResultVisible = false;
        boolean isResultTableInvisible =
                waitForElementInvisibility(By.xpath(PRE_PUBLICATION_TABLE), Timeouts.LOAD_ELEMENT);
        log.info("Is table invisible? {}", isResultTableInvisible);
        if (isResultTableInvisible) {
            boolean isResultPresent = waitForElementPresence(By.xpath(PRE_PUBLICATION_TABLE), Timeouts.LOAD_RESULTS);
            log.info("Is table present? {}", isResultPresent);
        }
        WebElement resultsTable = getBrowser().getDriver().findElement(By.xpath(PRE_PUBLICATION_TABLE));
        isResultVisible = waitForElementVisibility(resultsTable, Timeouts.LOAD_ELEMENT);
        log.info("Is table visible? {}", isResultVisible);
        log().image("After Result table is visible", takeScreenshot());
        return isResultVisible;
    }

    public void selectCasesInList() {
        // search by "Automation" in search
        String vendorEnvironment = "AUTOMATION LOCAL";
        searchCases(vendorEnvironment);
        // get a checkbox which td contains TEST VEH 1JKST and next td contains "AUTOMATION"
        String automationSearch = AUTOMATION_CASES.replace("?", vendorEnvironment);
        // wait for results table is visible
        waitForElementVisibility(getElement(By.xpath(SELECT_CASES_TABLE)), Timeouts.LOAD_HEAVY_RESULTS);

        List<WebElement> cases = getElements(By.xpath(automationSearch));
        log.info("Automation cases in list: {}", cases.size());

        if (cases.size() > 1) {
            click(cases.get(0)); // Select first case
            click(cases.get(1)); // Select second case
        } else if (cases.size() == 1) {
            click(cases.get(0)); // Select only case
        } else {
            log.warn("No cases found with vendor name: {}", vendorEnvironment);
        }
    }

    public void selectPublication(String publicationId, String status) {
        log.info("selectPublication method starting searching: {}", publicationId);
        String publication = PUBLICATION_RESULTS_XP.replace("?", publicationId.toUpperCase());
        publication = publication.replace("status", status);
        log().image("In selectPublication method -> Before wait for results", takeScreenshot());
        waitForElementToBeClickable(getElement(By.xpath(publication)), Timeouts.LOAD_HEAVY_RESULTS);
        log().image("In selectPublication method -> Before open publication", takeScreenshot());
        click(getElement(By.xpath(publication)));
    }

    private void searchCase(String case2Search) {
        log.info("searchCase method starting");
        waitForElementToBeClickable(searchCases, Timeouts.LOAD_HEAVY_RESULTS);
        searchCases.clear();
        sendKeys(searchCases, case2Search);
        CommonComponents components = new CommonComponents();
        components.selectCheckBoxCase(case2Search.toUpperCase()).click();//framework click is too slow to be used
        log().image("selected case", takeScreenshot());
        log.info("searchCase method ends successfully");
    }

    private void searchCases(String vendorName) {
        log.info("searchCase method starting");
        waitForElementToBeClickable(searchCases, Timeouts.LOAD_HEAVY_RESULTS);
        searchCases.clear();
        sendKeys(searchCases, vendorName);
        log().image("searchCases method -> searching for vendor: " + vendorName, takeScreenshot());
    }
    ///


    public Boolean fastVariousPublicationValidation(AolWebUser user, String publicationId) {
        new MenuPage().clickPublications(PublicationsOptions.REGISTER);
        PublicationCreation publicationVarious = new PublicationCreation();
        List<CompleteWebElement> values2Validate = publicationVarious.quickVehicleCaseVariousCreation(publicationId);

        return true;
    }

    public boolean quickPublicationCreationValidation(String publicationId) {
        new MenuPage().clickPublications(PublicationsOptions.REGISTER);
        PublicationCreation publicationVarious = new PublicationCreation();
        List<CompleteWebElement> values2Validate = publicationVarious.quickVehicleCaseTypeCreation(publicationId);
        return true;
    }




    public List<CompleteWebElement> createPublication(String newPublication) {
        MenuPage menu = new MenuPage();
        menu.clickPublications(PublicationsOptions.REGISTER);
        sleep(1000);
        log().image("In register publication", takeScreenshot());
        PublicationCreation publicationVarious = new PublicationCreation();
        return publicationVarious.quickPublicationCreation(newPublication);
    }

    public boolean validatePublication(String newPublication, List<CompleteWebElement> values) {
        log.info("Validating publication starting");
        new MenuPage().clickPublications(PublicationsOptions.CONSULT);
        PublicationSearch search = new PublicationSearch();
        search.selectPublicationType(CaseType.VEHICLES);
        search.setNotFocusOnYourOffer();
        search.setPublicationName(newPublication);
        search.selectInsurer(Insurers.QA_TEST_AUTOMATION.getInsurer());  // QA TESTS AUTOMATION  AQAT223
        String startDate = TestDateGenerator.todayPlusDays(0);
        search.setDateAndType(startDate, TypeOfDate.START_DATE_ELEMENT);
        search.search();
        log.info("Filling search data and click search");
        String searchResults = SEARCH_DYNAMIC_EQUALS.replace("?", newPublication.toUpperCase());
        waitForElementPresence(By.xpath(searchResults), Timeouts.LOAD_HEAVY_RESULTS);
        log().image("After search", takeScreenshot());
        CommonComponents common = new CommonComponents();
        common.findHRefElement(common.dynamicWebElement(SEARCH_DYNAMIC_EQUALS, newPublication.toUpperCase()));
        int correctValues = new CommonComponents().validateCaseCreation(values);
        boolean areValidationOk = correctValues == values.size();
        assertions().assertThat(areValidationOk).as("All fields were correct? ").isTrue();
        log.info("End of  Validating publication");
        return areValidationOk;
    }

    public boolean activatePublication(String publicationId, String caseVin1, String caseVin2) {
        log.info("activatePublication method starting");
        final String activatePublicationMethod = "In activatePublication method";

        //Search publication
        new MenuPage().clickPublications(PublicationsOptions.CONSULT);

        PublicationSearch search = new PublicationSearch();
        search.selectPublicationType(CaseType.VEHICLES);
        search.setNotFocusOnYourOffer();
        search.setPublicationName(publicationId);

        search.selectInsurer(Insurers.QA_TEST_AUTOMATION.getInsurer());
        String startDate = TestDateGenerator.todayPlusDays(-10);
        search.setDateAndType(startDate, TypeOfDate.START_DATE_ELEMENT);

        log().image("Before search publication", takeScreenshot());
        search.search();
        log().image("After search publication", takeScreenshot());

        // Select and open publication from results list
        log.info("Select and open publication from results list");
        selectPublication(publicationId, "En proceso");
        log.info("After select publication {}", activatePublicationMethod);
        log().image("After select publication", takeScreenshot());


        waitForCaseTypeLoad();
        //Update publication
        log.info("{} Before update publication", activatePublicationMethod);
        log().image("Before update publication", takeScreenshot());
        Buttons buttons = new Buttons();
        buttons.clickUpdateBtn();  //click update to update publication

        log.info(" {} After update button publication", activatePublicationMethod);
        log().image("After update button publication", takeScreenshot());
        waitForCaseTypeLoad();
        //save publication changes
        buttons.clickSearchBtn();  // Guardar y Continuar button
        log.info("After save and publish changes {}", activatePublicationMethod);
        log().image("After save and publish changes", takeScreenshot());

        //todo instead of case id use the two generated vins

        // activate publication
        activatePrePublication(caseVin1, caseVin2);

        log.info("Wait for activation process to finish");
        waitForTableResultFadeAndReload();
        selectPublication(publicationId, "Activa");
        // ---> case status in validation
        log.info("activatePublication method ending");
        return validateCaseInPublication();
    }

    private void waitForCaseTypeLoad() {
        log.info("Waiting for page information load");
        waitForElementPresence(By.xpath(CASE_TYPE_SELECTED), Timeouts.LOADER);
        log.info("CASE TYPE: {}", getText(getElement(By.xpath(CASE_TYPE_FIELD))));
        waitForElementPresence(By.xpath(PUBLICATION_NAME_XP), Timeouts.LOADER);
        log.info("Exiting Wait for page information load");
    }

    public boolean openPublication(AolWebUser user, String status) throws InterruptedException {
        PublicationConsult consult = new PublicationConsult();
        log.info("Clicking on Publications -> Consult -> {} Consult -> Online Vehicle", status);
        new MenuPage().extendMenu();
        consult.clickPublicationsMenu();
        consult.clickConsultSubmenu();
        consult.consultPublicationByStatus(status);
        consult.clickVehicleByStatus(status);
        consult.openDrawer();

        //selecting QA TESTS AUTOMATION
        PublicationSearch search = new PublicationSearch();
        search.selectInsurer(Insurers.QA_TEST_AUTOMATION.getInsurer());
        log().image("Before search insurer", takeScreenshot());

        search.search();

        waitForElementToBeClickable(getElement(By.xpath(VIEW_BUTTON)), Timeouts.LOAD_HEAVY_RESULTS);
        log().image("Searching online publication", takeScreenshot());
        return true;
    }
}
