package com.solera.global.qa.template.web.behavior.pages.componentpages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.payments.Insurers;
import com.solera.global.qa.template.web.behavior.pages.payments.Supplier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventorySearch extends CommonSearch {

    public static final String TRANSFER_STATUS_SELECTOR = "//div[contains(@id,'report-transfers_transferStatusId')]";
    public static final String TRANSFER_DETAILS_TITLE = "//h3[@class='ant-typography' "
            + "and contains(text(), 'Consultar detalle del traslado')]";

    public static final String VIN_FIELD = "search-transfers_vin";

    public static final String TRANSFERS_RESULTS_TABLE =
            "//tbody[@class='ant-table-tbody']"
                    + "/descendant::tr/descendant::td[contains(text(), '?')]"
                    + "/following-sibling::td/a[contains(text(), '1JKTS')]";

    public static final String INVENTORY_RESULTS_ROW =
            "//tbody[@class='ant-table-tbody']/tr[not(contains(@class,'ant-table-placeholder'))]";

    private static final String CITY_OR_TOWN_FIELD = "//input[contains(@id,'search-report-transfers_town')]";
    private static final String REPORT_FILTER_DROPDOWN = "//div[contains(@id,'ReportType')]";
    private static final String MANUFACTURER_FIELD= "//input[contains(@id,'search-report-transfers_brand')]";
    private static final String TYPE_FIELD= "//input[contains(@id,'transfers_type')]";
    private static final String MODEL_FIELD= "//input[contains(@id,'transfers_model')]";
    private static final String TYPE_UNIT_FILTER_DROPDOWN = "//div[contains(@id,'vehicleTypeId')]";

        //tranfer consult case
        public static final String SEARCH_TRANSFER = "//a[contains(@href, '/transfers/search/')]";
        public static final String GENERAL_SEARCH_FIELD = "//input[contains(@id,'Wildcard')]";
        public static final String REPORT_BUTTON = "//button[@jest-id='ReportV2']";






        @FindBy(xpath = SEARCH_TRANSFER)
        WebElement searchTranfer;
        @FindBy(xpath = GENERAL_SEARCH_FIELD)
        WebElement generalSearchField;


        public InventorySearch() {
            super();
        }


     public void selectTransferStatus(String status) {
        WebElement dropdown = getElement(By.xpath(TRANSFER_STATUS_SELECTOR));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        click(dropdown);
        new CommonComponents().selectFromDropdownText(dropdown, status);
        log().image("Transfer status selected: " + status, takeScreenshot());
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




     public void selectReportType(String fieldName, String value) {
        WebElement dropdown = getElement(By.xpath(String.format(REPORT_FILTER_DROPDOWN, fieldName)));
        new CommonComponents().selectFromDropdownText(dropdown, value);
        log().image("Report dropdown " + fieldName + " selected: " + value, takeScreenshot());
    }

    public void selectInsurer(String insurer) {
        String insurerLocator = INSURANCE_COMPANY_SELECTOR.replace("?",insurer);
        WebElement insurerElement = getBrowser().getDriver().findElement(By.xpath(insurerLocator));
        jsClick(insurerElement);
    }

    public void selectInsurerMarket(Insurers insurer) {
        String insurerLocator = INSURANCE_MARKET_SELECTOR.replace("?", insurer.getInsurer());
        WebElement insurerElement = getBrowser().getDriver().findElement(By.xpath(insurerLocator));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", insurerElement);
        log().image("Insurer selected", takeScreenshot());
    }


    public void selectSupplierCrane(Supplier supplier) {
        String supplierLocator = SUPPLIER_CRANE_SELECTOR.replace("?", supplier.getSupplier());
        WebElement supplierElement = getBrowser().getDriver().findElement(By.xpath(supplierLocator));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", supplierElement);
        log().image("Insurer selected", takeScreenshot());
    }


    public void selectCraneSupplierBranch(Supplier supplier) {
        String supplierLocator = CRANE_SUPPLIER_BRANCH_SELECTOR.replace("?", supplier.getSupplier());
        WebElement supplierElement = getBrowser().getDriver().findElement(By.xpath(supplierLocator));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", supplierElement);
        log().image("Insurer selected", takeScreenshot());
    }

    public void selectSupplierCorralon(Supplier supplier) {
        String supplierLocator = SUPPLIER_CORRALON_SELECTOR.replace("?", supplier.getSupplier());
        WebElement supplierElement = getBrowser().getDriver().findElement(By.xpath(supplierLocator));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", supplierElement);
        log().image("Insurer selected", takeScreenshot());
    }

    public void selectCorralonSupplierBranch(Supplier supplier) {
        String supplierLocator = CORRALON_SUPPLIER_BRANCH_SELECTOR.replace("?", supplier.getSupplier());
        WebElement supplierElement = getBrowser().getDriver().findElement(By.xpath(supplierLocator));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", supplierElement);
        log().image("Insurer selected", takeScreenshot());
    }


    public boolean waitForInventoryResults() {
        waitForElementPresence(By.xpath(INVENTORY_RESULTS_ROW), Timeouts.LOAD_RESULTS);
        boolean hasResults = !getElements(By.xpath(INVENTORY_RESULTS_ROW)).isEmpty();
        log().image("Inventory results displayed: " + hasResults, takeScreenshot());
        return hasResults;
    }

    public void setCityTextFilter(String fieldName, String value) {
        sendKeys(getElement(By.xpath(String.format(CITY_OR_TOWN_FIELD, fieldName))), value);
        log().image("Report filter " + fieldName + " set to " + value, takeScreenshot());
    }

    public void selectOriginReportDropdown(String fieldName, String value) {
        WebElement dropdown = getElement(By.xpath(String.format(REPORT_FILTER_DROPDOWN, fieldName)));
        new CommonComponents().selectFromDropdownText(dropdown, value);
        log().image("Report dropdown " + fieldName + " selected: " + value, takeScreenshot());
    }

    public void setManufacturerTextFilter(String fieldName, String value) {
        sendKeys(getElement(By.xpath(String.format(MANUFACTURER_FIELD, fieldName))), value);
        log().image("Report filter " + fieldName + " set to " + value, takeScreenshot());
    }

    public void setTypeTextFilter(String fieldName, String value) {
        sendKeys(getElement(By.xpath(String.format(TYPE_FIELD, fieldName))), value);
        log().image("Report filter " + fieldName + " set to " + value, takeScreenshot());
    }

    public void setModelTextFilter(String fieldName, String value) {
        sendKeys(getElement(By.xpath(String.format(MODEL_FIELD, fieldName))), value);
        log().image("Report filter " + fieldName + " set to " + value, takeScreenshot());
    }

     public void selectTypeUnitReportDropdown(String fieldName, String value) {
        WebElement dropdown = getElement(By.xpath(String.format(TYPE_UNIT_FILTER_DROPDOWN, fieldName)));
        new CommonComponents().selectFromDropdownText(dropdown, value);
        log().image("Report dropdown " + fieldName + " selected: " + value, takeScreenshot());
    }

    public void setReportDateRange(String startDate, String endDate) {
        setStartDate(startDate);
        setEndDate(endDate);
        log().image("Report date range set: " + startDate + " - " + endDate, takeScreenshot());
    }

}
