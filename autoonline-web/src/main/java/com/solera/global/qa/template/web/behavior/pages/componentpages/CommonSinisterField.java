package com.solera.global.qa.template.web.behavior.pages.componentpages;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommonSinisterField extends BrowserPage {

    //dropdown Common Sinister elements
    public static final String INSURANCE_CARRIED_ID_FIELD = "//div[contains(@id,'insuranceCarrierId')]";

    //input elements
    public static final String COMPENSATION_VALUE_FIELD = "//input[contains(@id,'compensationValue')]";
    public static final String COMMERCIAL_VALUE_FIELD = "//input[contains(@id,'commercialValue')]";
    public static final String BASE_VALUE_FIELD = "//input[contains(@id,'baseValue')]";
    public static final String SPARE_PARTS_COST_FIELD = "//input[contains(@id,'sparedPartsCost')]";
    public static final String REPAIR_COST_FIELD = "//input[contains(@id,'repairCost')]";
    public static final String OBSERVATIONS_FIELD = "//input[contains(@id,'observations')]";
    public static final String CASE_STATUS_FIELD = "//input[contains(@id,'caseStatus')]";
    public static final String C1_FIELD = "//input[contains(@id,'c1')]";
    public static final String C2_FIELD = "//input[contains(@id,'c2')]";
    public static final String C3_FIELD = "//input[contains(@id,'c3')]";

    @FindBy(xpath = INSURANCE_CARRIED_ID_FIELD)
    WebElement insuranceSelected;
    @FindBy(xpath = COMPENSATION_VALUE_FIELD)
    WebElement compensationValue;
    @FindBy(xpath = COMMERCIAL_VALUE_FIELD)
    WebElement commercialValue;
    @FindBy(xpath = BASE_VALUE_FIELD)
    WebElement baseValue;
    @FindBy(xpath = SPARE_PARTS_COST_FIELD)
    WebElement sparePartsCost;
    @FindBy(xpath = REPAIR_COST_FIELD)
    WebElement repairCost;
    @FindBy(xpath = OBSERVATIONS_FIELD)
    WebElement observations;
    @FindBy(xpath = C1_FIELD)
    WebElement c1Field;
    @FindBy(xpath = C2_FIELD)
    WebElement c2Field;
    @FindBy(xpath = C3_FIELD)
    WebElement c3Field;
    @FindBy(xpath = CASE_STATUS_FIELD)
    WebElement caseStatusField;


    public  CommonSinisterField() {
        super();
    }

    public WebElement getInsuranceSelected() {
        return insuranceSelected;
    }

    public WebElement getCompensationValue() {
        return compensationValue;
    }

    public WebElement getCommercialValue() {
        return commercialValue;
    }

    public WebElement getBaseValue() {
        return baseValue;
    }

    public WebElement getSparePartsCost() {
        return sparePartsCost;
    }

    public WebElement getRepairCost() {
        return repairCost;
    }

    public WebElement getObservations() {
        return observations;
    }

    public WebElement getC1Field() {
        return c1Field;
    }

    public WebElement getC2Field() {
        return c2Field;
    }

    public WebElement getC3Field() {
        return c3Field;
    }

    public WebElement getCaseStatusField() {
        return caseStatusField;
    }

}
