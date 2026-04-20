package com.solera.global.qa.template.web.behavior.pages.componentpages.search;

import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.CaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.ICaseType;
import com.solera.global.qa.template.web.behavior.pages.payments.PaymentStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class PaymentSearch extends CommonSearch {

    private static final String PAYMENT_STATUS = "//input[@type='checkbox' and @value='?']";
    private static final String PAYMENT_STATUS_LABEL = "//label[contains(text(),'Estatus de pago')]";

    public void setCaseType(ICaseType caseType) {
        this.selectCaseTypeTst(CaseType.VEHICLES);
    }


    public void selectPaymentStatus(PaymentStatus paymentStatus) {
        String statusLocator = PAYMENT_STATUS.replace("?", paymentStatus.getStatus());
        WebElement paymentStatusElement = getElement(By.xpath(statusLocator));

        scrollTo(getElement(By.xpath(PAYMENT_STATUS_LABEL)));

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", paymentStatusElement);
        log().image("Selected payment status", takeScreenshot());
    }
}
