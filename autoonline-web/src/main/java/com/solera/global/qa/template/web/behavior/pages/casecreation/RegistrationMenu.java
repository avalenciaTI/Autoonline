package com.solera.global.qa.template.web.behavior.pages.casecreation;

import static com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons.REPORT_BUTTON;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class RegistrationMenu extends BrowserPage {

    public static final String CASE_TLUNIVERSE = "//a[@href='/cases/universePT']";
    public static final String SEARCH_CASES = "//a[@href='/cases/search']";
    public static final String CASE_MASSIVE_REGISTRATION = "//a[@href='/cases/add/massive']";
    public static final String CASE_INDIVIDUAL_REGISTRATION = "//a[@href='/cases/add/individual']";
    public static final String CASE_TYPE_FIELD = "//div[contains(@id,'caseType')]";
    public static final String GENERAL_SEARCH_FIELD = "search-cases_wildcard";
    public static final String SEARCH_TYPE_TOGGLE = "swap-search-button";
    public static final String MARKET_CATALOG_REGISTER = "//a[@href='/catalogs/add/individual']";



    @FindBy(xpath = CASE_TLUNIVERSE)
    WebElement caseTLUniverse;
    @FindBy(xpath = SEARCH_CASES)
    WebElement searchCases;
    @FindBy(xpath = CASE_MASSIVE_REGISTRATION)
    WebElement massiveRegistrationCase;
    @FindBy(xpath = CASE_INDIVIDUAL_REGISTRATION)
    WebElement individualRegistrationCase;
    @FindBy(xpath = CASE_TYPE_FIELD)
    WebElement caseType;
    @FindBy(id = GENERAL_SEARCH_FIELD)
    WebElement generalSearchField;
    //CATALOG
    @FindBy(xpath = MARKET_CATALOG_REGISTER)
    WebElement marketCatalogRegister;


    public RegistrationMenu() {
        super();
    }

    public void clickCaseTlUniverse() {
        click(caseTLUniverse);
    }

    public void consultCases() {
        log.info("starting clickSearchCases..");
        click(searchCases);
        log.info("search cases clicked");
        sleep(2000);
    }

    public void clickSearchCases(String caseTypeVal,String searchCriteria) {
        log.info("starting clickSearchCases..");
        click(searchCases);
        log.info("search cases clicked");
        sleep(2000);
        new CommonComponents().selectFromDropdownText(caseType,caseTypeVal);
        log.info("Type of search selected");

        sendKeys(generalSearchField, searchCriteria);
        log.info("search criteria selected");
        log().image("search criteria", takeScreenshot());
        new Buttons().clickSearchBtn();
        log.info("search button clicked");
        log().image("search criteria results", takeScreenshot());
         try {
            waitForElementPresence(By.xpath(REPORT_BUTTON), Timeouts.LOAD_RESULTS);
            log.info("Report button found after search");
        } catch (Exception e) {
            log.warn("Report button not found after search, continuing anyway: {}", e.getMessage());
            log().image("Report button NOT found", takeScreenshot());
        }
    }

    public void clickMassiveRegistrationCase() {
        click(massiveRegistrationCase);
    }

    public void clickIndividualRegistrationCase() {
        individualRegistrationCase = getElement(By.xpath(CASE_INDIVIDUAL_REGISTRATION));
        jsClick(individualRegistrationCase);
        log().image("Individual Registration Case clicked", takeScreenshot());
        IndividualRegistration caseRegister = new IndividualRegistration();
    }



    public void clickRegisterCatalog() {
        marketCatalogRegister = getElement(By.xpath(MARKET_CATALOG_REGISTER));
        jsClick(marketCatalogRegister);
        log().image("Individual Registration Case clicked", takeScreenshot());
    }
}
