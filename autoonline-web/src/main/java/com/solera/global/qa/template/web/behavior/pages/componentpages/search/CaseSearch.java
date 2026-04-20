package com.solera.global.qa.template.web.behavior.pages.componentpages.search;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CompleteWebElement;
import com.solera.global.qa.template.web.behavior.pages.componentpages.SearchType;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Slf4j
public class CaseSearch extends BrowserPage {

    public static final String SEARCH_CASES = "//a[@href='/cases/search']";
    public static final String CASE_TYPE_FIELD = "//div[contains(@id,'caseType')]";
    public static final String GENERAL_SEARCH_FIELD = "search-cases_wildcard";
    public static final String REPORT_BUTTON = "//button[@jest-id = 'reportButton']";
    public static final String GENERAL_SEARCH_CRITERIA = "//a[contains(text(), '?')]";
    public static final String ADVANCE_SEARCH_CRITERIA= "//button[@id='swap-search-button']";
    public static final String SEARCH_FILTER_GENERAL = "//input[contains(@data-testid,'data_test_?')]";

    //
    ////input[@data-testid='data_test_model'((
    public CaseSearch(){
        super();
    }



    public boolean generalSearch(CaseType caseTypeVal, String searchCriteria) {
        new CommonComponents().selectFromDropdownText(getElement(By.xpath(CASE_TYPE_FIELD)),caseTypeVal.getCaseType());
        log.info("Type of search selected");

        sendKeys(getElement(By.id(GENERAL_SEARCH_FIELD)),searchCriteria);
        log.info("search criteria selected");
        log().image("search criteria selected", takeScreenshot());
        new Buttons().clickSearchBtn();
        log.info("search button clicked");
        waitForElementVisibility(getElement(By.xpath(REPORT_BUTTON)), Timeouts.LOAD_RESULTS);

        log().image("Results", takeScreenshot());
        String criteriaResults = GENERAL_SEARCH_CRITERIA.replace("?", searchCriteria);
        log.info("ending clickSearchCases..");
        return getElement(By.xpath(criteriaResults)).isDisplayed();
    }

    public void advanceSearch(CaseType caseTypeVal){
        new CommonComponents().selectFromDropdownText(getElement(By.xpath(CASE_TYPE_FIELD)),caseTypeVal.getCaseType());
        WebElement searchStatusButton=getDriver().findElement(By.xpath(ADVANCE_SEARCH_CRITERIA+"//span"));
        if(searchStatusButton.getText().equals("Búsqueda avanzada")){
            log.info("Type of search changed from general to advance");
            click(getDriver().findElement(By.xpath(ADVANCE_SEARCH_CRITERIA)));
        }else {
            log.info("Advance search already selected");
        }

    }

    public void fillFilterField(String genericField, String searchCriteria){
        WebElement elementWeb = new CommonComponents().dynamicWebElement(SEARCH_FILTER_GENERAL,genericField);
        sendKeys(elementWeb, searchCriteria);
    }


}
