package com.solera.global.qa.template.web.behavior.pages.componentpages;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommonLocationFields extends BrowserPage {

    //dropdownCommonlocation elements
    public static final String STATE_FIELD = "//div[contains(@id,'stateId')]";
    public static final String COUNTRY_FIELD = "//div[contains(@id,'countryId')]";

    //input elemments
    public static final String STREET_FIELD = "//input[contains(@id,'roadName')]";
    public static final String EXTERIOR_NUMBER_FIELD = "//input[contains(@id,'outNumber')]";
    public static final String INTERIOR_NUMBER_FIELD = "//input[contains(@id,'inNumber')]";
    public static final String ZIP_CODE_FIELD = "//input[contains(@id,'zipCode')]";
    public static final String NEIGHBORHOOD_FIELD = "//input[contains(@id,'neighborhood')]";
    public static final String TOWN_FIELD = "//input[contains(@id,'town')]";
    public static final String CITY_FIELD = "//input[contains(@id,'city')]";

    @FindBy(xpath = STATE_FIELD)
    WebElement stateField;
    @FindBy(xpath = COUNTRY_FIELD)
    WebElement countryField;
    @FindBy(xpath = STREET_FIELD)
    WebElement streetField;
    @FindBy(xpath = EXTERIOR_NUMBER_FIELD)
    WebElement exteriorNumberField;
    @FindBy(xpath = INTERIOR_NUMBER_FIELD)
    WebElement interiorNumberField;
    @FindBy(xpath = ZIP_CODE_FIELD)
    WebElement zipCodeField;
    @FindBy(xpath = NEIGHBORHOOD_FIELD)
    WebElement neighborhoodField;
    @FindBy(xpath = TOWN_FIELD)
    WebElement townField;
    @FindBy(xpath = CITY_FIELD)
    WebElement cityField;

    public  CommonLocationFields() {
        super();
    }

    public WebElement getStateField() {
        return stateField;
    }

    public WebElement getCountryField() {
        return countryField;
    }

    public WebElement getStreetField() {
        return streetField;
    }

    public WebElement getExteriorNumberField() {
        return exteriorNumberField;
    }

    public WebElement getInteriorNumberField() {
        return interiorNumberField;
    }

    public WebElement getZipCodeField() {
        return zipCodeField;
    }

    public WebElement getNeighborhoodField() {
        return neighborhoodField;
    }

    public WebElement getTownField() {
        return townField;
    }

    public WebElement getCityField() {
        return cityField;
    }
}
