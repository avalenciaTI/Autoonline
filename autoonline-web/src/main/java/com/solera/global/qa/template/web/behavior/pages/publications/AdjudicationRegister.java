package com.solera.global.qa.template.web.behavior.pages.publications;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.data.tools.TestDateGenerator;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CompleteWebElement;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class AdjudicationRegister extends BrowserPage {

    private static final String ADJUDICATED_BUYER_CHECKBOX = "//input[@type='radio' and @class='ant-radio-input']";
    private static final String PAYMENT_REFERENCE_FIELD = "award-form_paymentRef";
    private static final String ADMIN_PAYMENT_REF_INSURER_FIELD = "award-form_marketPaymentRef";
    private static final String LIMIT_PAYMENT_DATE_FIELD = "award-form_paymentDateLimit";
    private static final String CLEAR_LIMIT_PAYMENT_DATE = "//span[@id='award-form_paymentDateLimit']"
            + "/descendant::i[@aria-label='ícono: close-circle']";
    private static final String PAYMENT_LIMIT_DATE = "//span[@id='award-form_paymentDateLimit']"
            + "/descendant::input[@class='ant-calendar-input ']";



    @FindBy(id = LIMIT_PAYMENT_DATE_FIELD)
    WebElement paymentLimitDate;
    @FindBy(xpath = PAYMENT_LIMIT_DATE)
    WebElement paymentLimitDateInput;
    @FindBy(xpath = CLEAR_LIMIT_PAYMENT_DATE)
    WebElement clearLimitPaymentDate;

    @FindBy(xpath = ADJUDICATED_BUYER_CHECKBOX)
    WebElement adjudicatedBuyerCheckbox;

    @FindBy(id = PAYMENT_REFERENCE_FIELD)
    WebElement paymentReferenceField;

    @FindBy(id = ADMIN_PAYMENT_REF_INSURER_FIELD)
    WebElement adminPaymentRefInsurerField;

    public boolean isRegistrationPageShown() {
        log.info("Verifying if user is on Adjudication Registration page");
        return waitForElementPresence(By.xpath(ADJUDICATED_BUYER_CHECKBOX), Timeouts.LOAD_BUTTON);
    }

    public void registerAdjudication(String paymentReference, String adminPaymentRefInsurer) {
        var register = new CommonComponents();
        List<CompleteWebElement> storedValues = new ArrayList<>();
        log.info("Registering adjudication");
        click(adjudicatedBuyerCheckbox);

        register.fillField(paymentReferenceField, paymentReference, storedValues);
        register.fillField(adminPaymentRefInsurerField, adminPaymentRefInsurer, storedValues);
        log().image("After Filling adminPaymentRefInsurerField", takeScreenshot());


        mouseHover(clearLimitPaymentDate);
        click(clearLimitPaymentDate);
        log().image("Clear limit payment date", takeScreenshot());
        String limitDate = TestDateGenerator.todayPlusDays(1);
        new CommonComponents().setCalendarDatesText(paymentLimitDate, paymentLimitDateInput, limitDate);
        sleep(3000);
        log().image("Register filled fieds", takeScreenshot());
        new Buttons().clickAcceptButton();
    }
}
