package com.solera.global.qa.template.web.behavior.pages.componentpages;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommonUsersFields extends BrowserPage {

    public static final String USER_NAME = "//input[contains(@id,'user_givenName')]";
    public static final String USER_SURNAME = "//input[contains(@id,'user_surname')]";
    public static final String USER_LAST_NAME = "//input[contains(@id,'user_lastname')]";
    public static final String USER_REASON_NAME = "//input[contains(@id,'name')]";
    public static final String USER_BUSINESS_NAME = "//input[contains(@id,'add-moral-buyer-user_businessName')]";
    public static final String USER_PERSONAL_BUSINESS_NAME = "//input[@id='add-personal-buyer-user_businessName']";


    public static final String MORAL_ACTIVITY_TURN = "//input[contains(@id,'activityLine')]";
    public static final String MORAL_REGIME_TURN = "//div[contains(@id,'add-moral-buyer-user_regimeId')]";
    public static final String MORAL_SERIE_FIEL = "//input[contains(@id,'add-moral-buyer-user_fielNumber')]";
    public static final String MORAL_FOLIO_MERCANTIL = "//input[contains(@id,'add-moral-buyer-user_commerceSerial')]";
    public static final String MORAL_CONSTITUTION_DATE = "//span[contains(@id,'add-moral-buyer-user_created')]";
    public static final String PERSONAL_BIRTH_DATE = "//span[contains(@id,'add-personal-buyer-user_bornDate')]";
    public static final String MORAL_BIRTH_DATE = "//span[contains(@id,'add-moral-buyer-user_bornDate')]";


    public static final String MORAL_ADDRESS_TYPE = "//div[contains(@id,'add-moral-buyer-user_addressTypeId')]";
    public static final String MORAL_COUNTRY = "//div[contains(@id,'add-moral-buyer-user_countryId')]";
    public static final String MORAL_STATE = "//div[contains(@id,'add-moral-buyer-user_stateId')]";
    public static final String MORAL_STREET = "//input[contains(@id,'add-moral-buyer-user_roadName')]";
    public static final String MORAL_NUMBER_EXT = "//input[contains(@id,'add-moral-buyer-user_outNumber')]";
    public static final String MORAL_POSTAL_CODE = "//input[contains(@id,'add-moral-buyer-user_zipCode')]";
    public static final String MORAL_COLONY = "//input[contains(@id,'add-moral-buyer-user_neighborhood')]";
    public static final String MORAL_DELEGATION = "//input[contains(@id,'add-moral-buyer-user_town')]";
    public static final String MORAL_CITY = "//input[contains(@id,'add-moral-buyer-user_city')]";
    public static final String MORAL_START_DATE = "//span[contains(@id,'add-moral-buyer-user_validFrom')]";
    public static final String MORAL_END_DATE = "//span[contains(@id,'add-moral-buyer-user_validTo')]";

    public static final String PERSONAL_ADDRESS_TYPE = "//div[contains(@id,'add-personal-buyer-user_addressTypeId')]";
    public static final String PERSONAL_COUNTRY = "//div[contains(@id,'add-personal-buyer-user_countryId')]";
    public static final String PERSONAL_STATE = "//div[contains(@id,'add-personal-buyer-user_stateId')]";
    public static final String PERSONAL_STREET = "//input[contains(@id,'add-personal-buyer-user_roadName')]";
    public static final String PERSONAL_NUMBER_EXT = "//input[contains(@id,'add-personal-buyer-user_outNumber')]";
    public static final String PERSONAL_POSTAL_CODE = "//input[contains(@id,'add-personal-buyer-user_zipCode')]";
    public static final String PERSONAL_COLONY = "//input[contains(@id,'add-personal-buyer-user_neighborhood')]";
    public static final String PERSONAL_DELEGATION = "//input[contains(@id,'add-personal-buyer-user_town')]";
    public static final String PERSONAL_CITY = "//input[contains(@id,'add-personal-buyer-user_city')]";
    public static final String PERSONAL_START_DATE = "//span[contains(@id,'add-personal-buyer-user_validFrom')]";
    public static final String PERSONAL_END_DATE = "//span[contains(@id,'add-personal-buyer-user_validTo')]";
    public static final String PERSONAL_REGIME = "//div[contains(@id,'add-personal-buyer-user_regimeId')]";
    public static final String PERSONAL_GRADE = "//input[contains(@id,'add-personal-buyer-user_grade')]";
    public static final String PERSONAL_OCCUPATION = "//input[contains(@id,'add-personal-buyer-user_occupation')]";
    public static final String PERSONAL_JOB = "//input[contains(@id,'add-personal-buyer-user_job')]";
    public static final String PERSONAL_INSURANCE = "//span[contains(text(),'?')]";

    public static final String USER_COUNTRY = "//div[contains(@id,'user_countryId')]";
    public static final String USER_LANGUAGE = "//div[contains(@id,'user_languageId')]";
    public static final String USER_TIMEZONE = "//div[contains(@id,'user_timeZoneId')]";
    public static final String USER_ID = "//div[contains(@id,'add-personal-buyer-user_documentTypeId')]";
    public static final String MORAL_USER_ID = "//div[contains(@id,'add-moral-buyer-user_documentTypeId')]";
    public static final String INSURED_ACCIDENT_SERIAL = "//input[contains(@id,'add-insured-user_accidentSerial')]";
    public static final String INSURED_CASE_ID = "//input[contains(@id,'add-insured-user_caseId')]";
    public static final String INSURED_VEHICLE_SERIAL = "//input[contains(@id,'add-insured-user_vehicleSerial')]";
    

    public static final String USER_RFC = "//input[contains(@id,'user_taxId')]";
    public static final String USER_CURP = "//input[contains(@id,'user_curp')]";
    public static final String USER_CALENDAR_INPUT = "//div[@class='ant-calendar-date-input-wrap']/input";
    public static final String USER_FOLIO = "//input[contains(@id,'documentNumber')]";
    

    public static final String USER_VALID_FROM = "//span[contains(@id,'user_validFrom')]"
            + "/div/i[contains(@class,'calendar')]";

    public static final String USER_END_DATE = "//span[contains(@id,'user_validTo')]"
            + "/div/i[contains(@class,'calendar')]";
    public static final String USER_END_DATE_INPUT = "//span[contains(@id,'user_validTo')]"
            + "//input[contains(@class,'ant-calendar-picker-input')]";
    public static final String USER_PHONE_NUMBER = "//input[contains(@id,'phoneNumber')]";
    public static final String USER_CELL_PHONE_NUMBER = "//input[contains(@id,'cellPhoneNumber')]";
    public static final String USER_OTHER_NUMBER = "//input[contains(@id,'faxNumber')]";

    public static final String USER_EMPLOYER_ID = "//input[contains(@id,'user_employeeId')]";
    public static final String USER_EMAIL = "//input[contains(@id,'user_email')]";
    public static final String USER_OFFICE_PHONE_NUMBER = "//input[contains(@id,'user_officePhonenumber')]";

    public static final String USER_DEPARTMENT = "//div[contains(@id,'user_departmentId')]";
    public static final String USER_JOB = "//div[contains(@id,'user_jobId')]";
    public static final String USER_COMMENTS = "//textarea[contains(@id,'user_comments')]";
    public static final String USER_PROVIDER_ID = "//div[contains(@id,'providerId')]";
    public static final String USER_INSURANCE_EMAIL = "//input[contains(@id,'add-insurances-carriers-user_email')]";
    public static final String USER_INSURANCE_PHONE = "//input[contains(@id,'add-insurances-carriers-user_officePhonenumber')]";
    public static final String USER_INSURANCE_CARRIER = "//input[@value='AANA002']";

    // Invitation fields — Individual
    public static final String INVITATION_SEND_TYPE = "//div[@id='individual_option']";
    public static final String INVITATION_DROPDOWN_OPTION = "//li[contains(@class,'ant-select-dropdown-menu-item') and text()='?']";
    public static final String INVITATION_FIRSTNAME = "//input[@id='individual_firstname']";
    public static final String INVITATION_SURNAME = "//input[@id='individual_surname']";
    public static final String INVITATION_LASTNAME = "//input[@id='individual_lastname']";
    public static final String INVITATION_EMAIL = "//input[@id='individual_email']";
    public static final String INVITATION_PHONE = "//input[@id='individual_phone']";
    public static final String INVITATION_MODAL = "//div[@role='document']";

    // Invitation fields — Massive (CP023)
    public static final String INVITATION_ATTACH_BUTTON = "//span[text()='Adjuntar documento']/parent::button";
    public static final String INVITATION_SEND_BUTTON = "//span[text()='Enviar']/parent::button";
    public static final String INVITATION_DOWNLOAD_LAYOUT = "//span[text()='Descargar layout']/parent::button";
    public static final String INVITATION_FILE_INPUT = "//input[@type='file']";
    public static final String MASSIVE_CSV_FILE_PATH = "src/test/resources/attachments/reportCompare/Registro_Masivo_BuyersInvitation.xlsx";



    @FindBy(xpath = USER_NAME)
    WebElement nameField;
    @FindBy(xpath = USER_SURNAME)
    WebElement surNameField;
    @FindBy(xpath = USER_LAST_NAME)
    WebElement lastNameField;
    @FindBy(xpath = USER_COUNTRY)
    WebElement countryField;
    @FindBy(xpath = USER_LANGUAGE)
    WebElement languageField;
    @FindBy(xpath = USER_TIMEZONE)
    WebElement timeZoneField;
    @FindBy(xpath = USER_RFC)
    WebElement rfcField;
    @FindBy(xpath = USER_CURP)
    WebElement curpField;
    @FindBy(xpath = USER_FOLIO)
    WebElement folioField;
    
    @FindBy(xpath = USER_ID)
    WebElement idField;
    @FindBy(xpath = MORAL_USER_ID)
    WebElement moralIdField;
    @FindBy(xpath = INSURED_ACCIDENT_SERIAL)
    WebElement insuredAccidentSerialField;
    @FindBy(xpath = INSURED_CASE_ID)
    WebElement insuredCaseIdField;
    @FindBy(xpath = INSURED_VEHICLE_SERIAL)
    WebElement insuredVehicleSerialField;

    @FindBy(xpath = USER_VALID_FROM)
    WebElement startDateField;
    @FindBy(xpath = USER_CALENDAR_INPUT)
    WebElement calendarInputField;
    @FindBy(xpath = USER_END_DATE)
    WebElement endDateField;
    @FindBy(xpath = USER_END_DATE_INPUT)
    WebElement endDateInputField;
    @FindBy(xpath = USER_PHONE_NUMBER)
    WebElement phoneNumberField;
    @FindBy(xpath = USER_CELL_PHONE_NUMBER)
    WebElement cellPhoneNumberField;
    @FindBy(xpath = USER_OTHER_NUMBER)
    WebElement otherNumberField;
    @FindBy(xpath = USER_EMPLOYER_ID)
    WebElement employerNumberField;
    @FindBy(xpath = USER_EMAIL)
    WebElement emailField;
    @FindBy(xpath = USER_OFFICE_PHONE_NUMBER)
    WebElement officePhoneNumberField;
    @FindBy(xpath = USER_DEPARTMENT)
    WebElement departmentField;
    @FindBy(xpath = USER_JOB)
    WebElement jobField;
    @FindBy(xpath = USER_COMMENTS)
    WebElement commentsField;
    @FindBy(xpath = USER_PROVIDER_ID)
    WebElement providerIdField;
    @FindBy(xpath = USER_BUSINESS_NAME)
    WebElement businessNameField;
    @FindBy(xpath = USER_PERSONAL_BUSINESS_NAME)
    WebElement businessPersonalNameField;
    @FindBy(xpath = USER_REASON_NAME)
    WebElement reasonNameField;
    @FindBy(xpath = MORAL_ACTIVITY_TURN)
    WebElement moralActivityTurnField;
    @FindBy(xpath = MORAL_REGIME_TURN)
    WebElement moralRegimeTurnField;
    @FindBy(xpath = MORAL_SERIE_FIEL)
    WebElement moralSerieFielField;
    @FindBy(xpath = MORAL_FOLIO_MERCANTIL)
    WebElement moralFolioMercantilField;
    @FindBy(xpath = MORAL_CONSTITUTION_DATE)
    WebElement moralConstitutionDateField;
    @FindBy(xpath = PERSONAL_BIRTH_DATE)
    WebElement personalBirthDateField;
    @FindBy(xpath = MORAL_ADDRESS_TYPE)
    WebElement moralAddressTypeField;
    @FindBy(xpath = MORAL_COUNTRY)
    WebElement moralCountryField;
    @FindBy(xpath = MORAL_STATE)
    WebElement moralStateField;
    @FindBy(xpath = MORAL_STREET)
    WebElement moralStreetField;
    @FindBy(xpath = MORAL_NUMBER_EXT)
    WebElement moralNumberExtField;
    @FindBy(xpath = MORAL_POSTAL_CODE)
    WebElement moralPostalCodeField;
    @FindBy(xpath = MORAL_COLONY)
    WebElement moralColonyField;
    @FindBy(xpath = MORAL_DELEGATION)
    WebElement moralDelegationField;
    @FindBy(xpath = MORAL_CITY)
    WebElement moralCityField;
    @FindBy(xpath = MORAL_START_DATE)
    WebElement moralStartDateField;
    @FindBy(xpath = MORAL_END_DATE)
    WebElement moralEndDateField;
    @FindBy(xpath = MORAL_BIRTH_DATE)
    WebElement moralBirthDate;
    @FindBy(xpath = PERSONAL_ADDRESS_TYPE)
    WebElement personalAddressTypeField;
    @FindBy(xpath = PERSONAL_COUNTRY)
    WebElement personalCountryField;
    @FindBy(xpath = PERSONAL_STATE)
    WebElement personalStateField;
    @FindBy(xpath = PERSONAL_STREET)
    WebElement personalStreetField;
    @FindBy(xpath = PERSONAL_NUMBER_EXT)
    WebElement personalNumberExtField;
    @FindBy(xpath = PERSONAL_POSTAL_CODE)
    WebElement personalPostalCodeField;
    @FindBy(xpath = PERSONAL_COLONY)
    WebElement personalColonyField;
    @FindBy(xpath = PERSONAL_DELEGATION)
    WebElement personalDelegationField;
    @FindBy(xpath = PERSONAL_CITY)
    WebElement personalCityField;
    @FindBy(xpath = PERSONAL_START_DATE)
    WebElement personalStartDateField;
    @FindBy(xpath = PERSONAL_END_DATE)
    WebElement personalEndDateField;
    @FindBy(xpath = PERSONAL_REGIME)
    WebElement personalRegimeField;
    @FindBy(xpath = PERSONAL_GRADE)
    WebElement personalGradeField;
    @FindBy(xpath = PERSONAL_OCCUPATION)
    WebElement personalOccupationField;
    @FindBy(xpath = PERSONAL_JOB)
    WebElement personalJobField;
    @FindBy(xpath = USER_INSURANCE_EMAIL)
    WebElement insuranceEmailField;
    @FindBy(xpath = USER_INSURANCE_PHONE)
    WebElement insurancePhoneField;
    @FindBy(xpath = USER_INSURANCE_CARRIER)
    WebElement insuranceCarrierField;

    @FindBy(xpath = INVITATION_SEND_TYPE)
    WebElement invitationSendTypeField;
    @FindBy(xpath = INVITATION_FIRSTNAME)
    WebElement invitationFirstnameField;
    @FindBy(xpath = INVITATION_SURNAME)
    WebElement invitationSurnameField;
    @FindBy(xpath = INVITATION_LASTNAME)
    WebElement invitationLastnameField;
    @FindBy(xpath = INVITATION_EMAIL)
    WebElement invitationEmailField;
    @FindBy(xpath = INVITATION_PHONE)
    WebElement invitationPhoneField;
    @FindBy(xpath = INVITATION_MODAL)
    WebElement invitationModalField;

    @FindBy(xpath = INVITATION_ATTACH_BUTTON)
    WebElement invitationAttachButtonField;
    @FindBy(xpath = INVITATION_SEND_BUTTON)
    WebElement invitationSendButtonField;
    @FindBy(xpath = INVITATION_DOWNLOAD_LAYOUT)
    WebElement invitationDownloadLayoutField;
    @FindBy(xpath = INVITATION_FILE_INPUT)
    WebElement invitationFileInputField;

    public CommonUsersFields() {
        super();
    }

    public WebElement getNameField() {
        return nameField;
    }

    public WebElement getSurNameField() {
        return surNameField;
    }

    public WebElement getLastNameField() {
        return lastNameField;
    }

    public WebElement getCountryField() {
        return countryField;
    }

    public WebElement getLanguageField() {
        return languageField;
    }

    public WebElement getTimeZoneField() {
        return timeZoneField;
    }

    public WebElement getRfcField() {
        return rfcField;
    }

    public WebElement getCurpField() {
        return curpField;
    }
    public WebElement getFolioField() {
        return folioField;
    }
    
    public WebElement getIdField() {
        return idField;
    }
    public WebElement getMoralIdField() {
        return moralIdField;
    }
    public WebElement getInsuredAccidentSerialField() {
        return insuredAccidentSerialField;
    }
    public WebElement getInsuredCaseIdField() {
        return insuredCaseIdField;
    }
    public WebElement getInsuredVehicleSerialField() {
        return insuredVehicleSerialField;
    }


    public WebElement getStartDateField() {
        return startDateField;
    }

    public WebElement getCalendarInputField() {
        return calendarInputField;
    }

    public WebElement getEndDateField() {
        return endDateField;
    }

    public WebElement getEndDateInputField() {
        return endDateInputField;
    }

    public WebElement getPhoneNumberField() {
        return phoneNumberField;
    }

    public WebElement getCellPhoneNumberField() {
        return cellPhoneNumberField;
    }

    public WebElement getOtherNumberField() {
        return otherNumberField;
    }

    public WebElement getEmployerNumberField() {
        return employerNumberField;
    }

    public WebElement getEmailField() {
        return emailField;
    }

    public WebElement getOfficePhoneNumberField() {
        return officePhoneNumberField;
    }

    public WebElement getDepartmentField() {
        return departmentField;
    }

    public WebElement getJobField() {
        return jobField;
    }

    public WebElement getCommentsField() {
        return commentsField;
    }
    public WebElement getProviderIdField() {
        return providerIdField;
    }
    public WebElement getBusinessNameField() {
        return businessNameField;
    }
    public WebElement getBusinessPersonalNameField() {
        return businessPersonalNameField;
    }
    
    public WebElement getReasonNameField() {
        return reasonNameField;
    }
    public WebElement getMoralActivityTurnField() {
        return moralActivityTurnField;
    }
    public WebElement getMoralRegimeTurnField() {
        return moralRegimeTurnField;
    }
    public WebElement getMoralSerieFielField() {
        return moralSerieFielField;
    }
    public WebElement getMoralFolioMercantilField() {
        return moralFolioMercantilField;
    }
    public WebElement getMoralConstitutionDateField() {
        return moralConstitutionDateField;
    }
    public WebElement getPersonalBirthDateField() {
        return personalBirthDateField;
    }

    public WebElement getMoralBirthDateField() {
        return moralBirthDate;
    }
    
    public WebElement getMoralAddressTypeField() {
        return moralAddressTypeField;
    }
    public WebElement getMoralCountryField() {
        return moralCountryField;
    }
    public WebElement getMoralStateField() {
        return moralStateField;
    }
    public WebElement getMoralStreetField() {
        return moralStreetField;
    }
    public WebElement getMoralNumberExtField() {
        return moralNumberExtField;
    }
    public WebElement getMoralPostalCodeField() {
        return moralPostalCodeField;
    }
    public WebElement getMoralColonyField() {
        return moralColonyField;
    }
    public WebElement getMoralDelegationField() {
        return moralDelegationField;
    }
    public WebElement getMoralCityField() {
        return moralCityField;
    }
    public WebElement getMoralStartDateField() {
        return moralStartDateField;
    }
    public WebElement getMoralEndDateField() {
        return moralEndDateField;
    }
    public WebElement getPersonalAddressTypeField() {
        return personalAddressTypeField;
    }
    public WebElement getPersonalCountryField() {
        return personalCountryField;
    }
    public WebElement getPersonalStateField() {
        return personalStateField;
    }
    public WebElement getPersonalStreetField() {
        return personalStreetField;
    }
    public WebElement getPersonalNumberExtField() {
        return personalNumberExtField;
    }
    public WebElement getPersonalPostalCodeField() {
        return personalPostalCodeField;
    }
    public WebElement getPersonalColonyField() {
        return personalColonyField;
    }
    public WebElement getPersonalDelegationField() {
        return personalDelegationField;
    }
    public WebElement getPersonalCityField() {
        return personalCityField;
    }
    public WebElement getPersonalStartDateField() {
        return personalStartDateField;
    }
    public WebElement getPersonalEndDateField() {
        return personalEndDateField;
    }
    public WebElement getPersonalRegimeField() {
        return personalRegimeField;
    }
    public WebElement getPersonalGradeField() {
        return personalGradeField;
    }
    public WebElement getPersonalOccupationField() {
        return personalOccupationField;
    }
    public WebElement getPersonalJobField() {
        return personalJobField;
    }
    public WebElement getInsuranceEmailField() {
        return insuranceEmailField;
    }
    public WebElement getInsurancePhoneField() {
        return insurancePhoneField;
    }
    public WebElement getInsuranceCarrierField() {
        return insuranceCarrierField;
    }

    public WebElement getInvitationSendTypeField() {
        return invitationSendTypeField;
    }

    public WebElement getInvitationFirstnameField() {
        return invitationFirstnameField;
    }

    public WebElement getInvitationSurnameField() {
        return invitationSurnameField;
    }

    public WebElement getInvitationLastnameField() {
        return invitationLastnameField;
    }

    public WebElement getInvitationEmailField() {
        return invitationEmailField;
    }

    public WebElement getInvitationPhoneField() {
        return invitationPhoneField;
    }

    public WebElement getInvitationModalField() {
        return invitationModalField;
    }

    public WebElement getInvitationAttachButtonField() {
        return invitationAttachButtonField;
    }

    public WebElement getInvitationSendButtonField() {
        return invitationSendButtonField;
    }

    public WebElement getInvitationDownloadLayoutField() {
        return invitationDownloadLayoutField;
    }

    public WebElement getInvitationFileInputField() {
        return invitationFileInputField;
    }
}
