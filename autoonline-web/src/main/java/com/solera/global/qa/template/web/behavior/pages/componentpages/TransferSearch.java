package com.solera.global.qa.template.web.behavior.pages.componentpages;

import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.payments.Insurers;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class TransferSearch extends CommonSearch {

    public static final String TRANSFER_STATUS_SELECTOR = "search-transfers_statusId";
    public static final String TRANSFER_DETAILS_TITLE = "//h3[@class='ant-typography' "
            + "and contains(text(), 'Consultar detalle del traslado')]";

    public static final String VIN_FIELD = "search-transfers_vin";

    public static final String TRANSFERS_RESULTS_TABLE =
            "//tbody[@class='ant-table-tbody']"
                    + "/descendant::tr/descendant::td[contains(text(), '?')]"
                    + "/following-sibling::td/a[contains(text(), '1JKTS')]";



        //tranfer consult case
        public static final String SEARCH_TRANSFER = "//a[contains(@href, '/transfers/search/')]";
        public static final String GENERAL_SEARCH_FIELD = "//input[contains(@id,'Wildcard')]";
        public static final String REPORT_BUTTON = "//button[@jest-id='ReportV2']";






        @FindBy(xpath = SEARCH_TRANSFER)
        WebElement searchTranfer;
        @FindBy(xpath = GENERAL_SEARCH_FIELD)
        WebElement generalSearchField;

    public void selectTransferStatus(String status) {
        click(getElement(By.id(TRANSFER_STATUS_SELECTOR)));

        new CommonComponents().selectFromDropdownText(getElement(By.id(TRANSFER_STATUS_SELECTOR)), status);
        log().image("Transfer status selected: " + status, takeScreenshot());
    }

    public void setVinField(String vin) {
        sendKeys(getElement(By.id(VIN_FIELD)), vin);
        log().image("VIN set in search field", takeScreenshot());
    }

    public void clickSearchButton() {
        CommonSearch commonSearch = new CommonSearch();
        commonSearch.selectInsurer(Insurers.QA_TEST_AUTOMATION);
        commonSearch.search();
    }

    public String openFirstTransfer(String insurer) {
        String transfer = TRANSFERS_RESULTS_TABLE.replace("?", insurer);
        waitForElementVisibility(getElement(By.xpath(transfer)), Timeouts.LOAD_RESULTS);
        String serie = getText(getElement(By.xpath(transfer)));
        click(getElement(By.xpath(transfer)));
        waitForElementVisibility(getElement(By.xpath(TRANSFER_DETAILS_TITLE)), Timeouts.LOAD_PAGE);
        log().image("Opened first transfer for insurer: " + insurer, takeScreenshot());
        return serie;
    }





    public void clickSearchTranfer(String searchCriteria) {
        log.info("starting clickSearchCases..");
        click(searchTranfer);
        log.info("search Tranfer clicked");
        sleep(2000);
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


}
