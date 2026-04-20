package com.solera.global.qa.template.web.behavior.pages.componentpages;

import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.payments.Insurers;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

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


}
