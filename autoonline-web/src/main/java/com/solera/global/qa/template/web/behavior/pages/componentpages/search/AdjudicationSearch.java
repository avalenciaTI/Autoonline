package com.solera.global.qa.template.web.behavior.pages.componentpages.search;


import static com.solera.global.qa.template.web.behavior.pages.publications.PublicationOnline.GENERIC_AUTOMATION_NAME;

import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.AdjudicationStatus;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Slf4j
public class AdjudicationSearch extends CommonSearch {
    private static final String SELECT_ADJUDICATION_TYPE = "search-adverts_caseStatus";
    private static final String SELECT_ADJUDICATION_TYPE_XP = "//div[@id='search-adverts_caseStatus']";

    private static final String SEARCH_BY_VIEW_TYPE_UNITARY = "//div[@id='search-adverts_viewType']//span[text()='?']";
    private static final String RESULTS_PAGINATION = "//li[contains(@class,'ant-pagination-item ant-pagination-item')]";
    private static final String AUTOMATED_AWARDING = "//h3[contains(text(),'" + GENERIC_AUTOMATION_NAME + "')]";
    private static final String AWARDING_CONSULT =
            "(//li[@class='ant-menu-submenu ant-menu-submenu-vertical']//*[text()='Consultar'])[2]";

    private static final String ADJUDICATION_CASE_ID_FIELD = "//input[contains(@id,'search-adverts_caseId')]";


    public void selectAdjudicationStatus(AdjudicationStatus status) {
        List<WebElement> adjudicationStatusElements = getElements(By.xpath(SELECT_ADJUDICATION_TYPE_XP));
        log.info("Selecting adjudication status, size of elements: " + adjudicationStatusElements.size());
        for (WebElement adjudicationStatus: adjudicationStatusElements) {
            log.info("in loop");
            if (waitForElementToBeClickable(adjudicationStatus, Timeouts.SHORT_TIME)
                    && isDisplayed(adjudicationStatus)) {
                new CommonComponents().selectFromDropdownText(adjudicationStatus, status.getType());
                log().image("Adjudication status", takeScreenshot());
            }
        }
    }

    public void selectViewType(String type) {
        WebElement viewType = getElement(By.xpath(SEARCH_BY_VIEW_TYPE_UNITARY.replace("?", type)));
        click(viewType);
    }

    public void clickConsultAdjudications() {
        waitForElementPresence(getElement(By.xpath(AWARDING_CONSULT)), Timeouts.LOAD_ELEMENT);
        WebElement awardingsMenu = getElement(By.xpath(AWARDING_CONSULT));
        click(awardingsMenu);
    }

    public WebElement searchAutoCaseInResults() {
        List<WebElement> results = getElements(By.xpath(RESULTS_PAGINATION));
        log.info("Results size: " + results.size());
        //page 0 is the first page by default
        for (int page = 0; page <= results.size(); page++) {
            log.info("searching in results page: {}", page);
            click(results.get(page));
            List<WebElement> autos = getElements(By.xpath(AUTOMATED_AWARDING));
            if (!autos.isEmpty()) {
                //todo review in the following steps
                log.info("Found auto in page: {}", page);
                WebElement automatedSinister = autos.get(0);
            } else {
                log.info("Auto not found in page: {}", page);
            }
        }
        return null;
    }

    public void setAdjudicationCaseID(String adjudicationCaseID) {
        waitForElementToBeClickable(getElement(By.xpath(ADJUDICATION_CASE_ID_FIELD)), Timeouts.LOAD_ELEMENT);
        sendKeys(getElement(By.xpath(ADJUDICATION_CASE_ID_FIELD)), adjudicationCaseID);
    }

    public void search() {
        new Buttons().clickSearchBtn();
    }


}
