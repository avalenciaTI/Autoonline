package com.solera.global.qa.template.web.behavior.pages.componentpages;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.TypeOfDate;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PublicationSearch extends BrowserPage {
    private static final String PUBLICATION_TYPE_FORM_ID = "search-adverts_caseType";
    private static final String INSURERS_CONTAINER_LABEL = "//label[text()='Estatus de publicación']";
    private static final String DRAWER_CONTENT = "//div[@class='ant-drawer-content']";
    private static final String END_DATE_LABEL = "//label[text()='Fecha final']";
    private static final String NOT_FOCUS_ON_YOUR_OFFER = "//span[text()='No']";

    public static final String INSURANCE_COMPANY_SELECTOR =
            "//input[@type='checkbox' and @class='ant-checkbox-input' and @value='?']";

    private static final String START_DATE_ELEMENT = "search-adverts_start";
    private static final String END_DATE_ELEMENT = "search-adverts_end";
    private static final String PUBLICATION_NAME_FIELD = "search-adverts_name";
    private static final String PUBLICATION_ID_FIELD = "search-adverts_publicationid";

    public static final String USER_CALENDAR_INPUT = "//div[@class='ant-calendar-date-input-wrap']/input";
    public static final String SEARCH_PUBLICATION_IN_RESULTS = "//td[text()='%s']";



    @FindBy(id = PUBLICATION_TYPE_FORM_ID)
    WebElement publicationTypeSelectorForm;

    @FindBy(xpath = NOT_FOCUS_ON_YOUR_OFFER) WebElement notFocusOnYourOffer;

    @FindBy(id = START_DATE_ELEMENT) WebElement startDateElement;

    @FindBy(xpath = USER_CALENDAR_INPUT) WebElement calendarInputField;

    @FindBy(xpath = INSURERS_CONTAINER_LABEL) WebElement insurersContainerLabel;
    @FindBy(xpath = END_DATE_LABEL) WebElement endDateLabel;
    @FindBy(id = PUBLICATION_NAME_FIELD) WebElement publicationName;
    @FindBy(id = PUBLICATION_ID_FIELD) WebElement publicationIDField;

    public PublicationSearch() {
        super();
    }

    public void selectPublicationType(CaseType caseType) {
        new CommonComponents().selectFromDropdownText(publicationTypeSelectorForm, caseType.getCaseType());
    }

    public void setNotFocusOnYourOffer() {
        click(notFocusOnYourOffer);
    }

    public void selectInsurer(String insurer) {
        String insurerLocator = INSURANCE_COMPANY_SELECTOR.replace("?",insurer);
        WebElement insurerElement = getBrowser().getDriver().findElement(By.xpath(insurerLocator));
        jsClick(insurerElement);
    }


    public void setPublicationName(String newPublication) {
        sendKeys(publicationName, newPublication);
    }

    public void setPublicationID (String publicationID) {
        sendKeys(publicationIDField, publicationID);
    }


    public void setDateAndType(String date, TypeOfDate dateType) {
        //The date field is not visible in page, then is need to scroll down the page
        // may be use JavascriptExecutor executor = getBrowser().getDriver()
        JavascriptExecutor executor = (JavascriptExecutor) getBrowser().getDriver();

        executor.executeScript("arguments[0].scrollIntoView(true);", endDateLabel);

        CommonUsersFields fields = new CommonUsersFields();
        CommonComponents components = new CommonComponents();

        WebElement startDate = new CommonComponents().dynamicWebElement("//span[contains(@id,'?')]",
                dateType.getDateType());

        components.setCalendarDatesText(startDate, fields.getCalendarInputField(), date);
        click(getElement(By.xpath(DRAWER_CONTENT)));
    }

    public void search() {
        new Buttons().clickSearchBtn();
    }


}
