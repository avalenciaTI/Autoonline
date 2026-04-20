package com.solera.global.qa.template.web.behavior.pages.componentpages;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.ICaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.WorkFlowElements;
import com.solera.global.qa.template.web.behavior.pages.payments.Insurers;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class CommonSearch extends BrowserPage {

    // search field usefull for publications and awardning
    private static final String SWAP_ADVANCED_SEARCH = "swap-search-button";
    private static final String CASE_TYPE_SELECTOR = "//div[contains(@id,'caseType')]";
    //caseType      -   case        - search-cases_caseType
    //publication   -   advert      - search-adverts_caseType
    //adjudication  -   awarding    - search-adverts_caseType
    //payment       -   payment     - search-payments_caseType
    private static final String ADVERT_STATUS_ID_BUYER = "search-adverts_PaymentStatus";

    //search-adverts_caseStatus     -- awarding
    //search-adverts_advertStatus  -- publication
    private static final String ADVERT_STATUS_ID_MASTER = "//div[@id='search-adverts_caseStatus']";

    private static final String END_DATE_LABEL = "//label[text()='Fecha final']";

    public static final String INSURANCE_COMPANY_SELECTOR =
            "//input[@type='checkbox' and @class='ant-checkbox-input' and @value='?']";

    private static final String START_DATE_ELEMENT = "//span[contains(@id,'start')]";
    //caseType      -   case        - search-cases_startDate
    //publication   -   advert      - search-adverts_startDate
    //adjudication  -   awarding    - search-adverts_start
    //payment       -   payment     - search-payments_paymentStartDate
    private static final String END_DATE_ELEMENT = "//span[contains(@id,'end')]";
    //caseType      -   case        - search-cases_endDate
    //publication   -   advert      - search-adverts_endDate
    //adjudication  -   awarding    - search-adverts_end
    //payment       -   payment     - search-payments_paymentEndDate

    private static final String PUBLICATION_NAME_FIELD = "//input[contains(@id,'search-adverts_advertName')]";
    private static final String SINISTER_FIELD = "search-adverts_caseNumber";




    @FindBy(xpath = END_DATE_LABEL) WebElement endDateLabel;
    @FindBy(xpath = PUBLICATION_NAME_FIELD) WebElement publicationName;

    public CommonSearch() {
        super();
    }

    public void swapAdvancedSearch(SearchType search) {
        WebElement swapper = getElement(By.id(SWAP_ADVANCED_SEARCH));
        WebElement spanInSwapper = swapper.findElement(By.tagName("span"));
        String text = getText(spanInSwapper);
        if (text.equals(search.getSearchingType())) {
            click(swapper);
        }
        log().image("Swapped to " + search.getSearchingType() + " search", takeScreenshot());
    }


    public void selectCaseType(ICaseType type, WorkFlowElements workflowElement) {
        String typeText = type.getType();
        log.info("Selecting case type: " + typeText);
        String caseTypeSelector = CASE_TYPE_SELECTOR.replace("?", workflowElement.getElement());
        WebElement caseTypeSelectorForm = getElement(By.xpath(caseTypeSelector));
        new CommonComponents().selectFromDropdownText(caseTypeSelectorForm, typeText);
    }

    public void selectCaseTypeTst(ICaseType type) {
        String typeText = type.getType();
        log.info("Selecting case type: " + typeText);
        new CommonComponents().selectFromDropdownText(getElement(By.xpath(CASE_TYPE_SELECTOR)), typeText);
    }

    public void selectAwardingStatus(String status, String role) {
        log.info("Selecting awarding status: " + status);
        WebElement statusSelector;
        if (role.equalsIgnoreCase("master")) {
            statusSelector = getElement(By.xpath(ADVERT_STATUS_ID_MASTER));
        } else {
            statusSelector = getElement(By.id(ADVERT_STATUS_ID_BUYER));
        }

        new CommonComponents().selectFromDropdownText(statusSelector, status);
        log.info("Awarding status selected");
    }


    public void selectInsurer(Insurers insurer) {
        String insurerLocator = INSURANCE_COMPANY_SELECTOR.replace("?", insurer.getInsurer());
        WebElement insurerElement = getBrowser().getDriver().findElement(By.xpath(insurerLocator));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", insurerElement);
        log().image("Insurer selected", takeScreenshot());
    }


    public void setPublicationName(String newPublication) {
        publicationName = getElement(By.xpath(PUBLICATION_NAME_FIELD));
        log.info("Publication name field found");
        sendKeys(publicationName, newPublication);

        log().image("Publication name filled", takeScreenshot());
    }

    public void setSinister(String sinister) {
        sendKeys(getElement(By.id(SINISTER_FIELD)), sinister);
    }

    public void setStartDate(String date) {
        //The date field is not visible in page, then is need to scroll down the page
        // may be use JavascriptExecutor executor = getBrowser().getDriver()
        JavascriptExecutor executor = (JavascriptExecutor) getBrowser().getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", getElement(By.xpath(END_DATE_ELEMENT)));

        CommonUsersFields fields = new CommonUsersFields();
        CommonComponents components = new CommonComponents();
        components.setCalendarDatesText(getElement(By.xpath(START_DATE_ELEMENT)), fields.getCalendarInputField(), date);
        click(endDateLabel);
    }

    public void setEndDate(String date) {
        //The date field is not visible in page, then is need to scroll down the page
        // may be use JavascriptExecutor executor = getBrowser().getDriver()
        JavascriptExecutor executor = (JavascriptExecutor) getBrowser().getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", getElement(By.xpath(END_DATE_ELEMENT)));

        CommonUsersFields fields = new CommonUsersFields();
        CommonComponents components = new CommonComponents();
        components.setCalendarDatesText(getElement(By.xpath(END_DATE_ELEMENT)), fields.getCalendarInputField(), date);
        click(getElement(By.xpath(END_DATE_ELEMENT)));
    }

    public void search() {
        new Buttons().clickSearchBtn();
        log.info("Search button clicked");
    }

}
