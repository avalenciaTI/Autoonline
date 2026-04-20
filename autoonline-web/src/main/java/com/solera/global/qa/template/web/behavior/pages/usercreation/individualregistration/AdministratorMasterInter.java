package com.solera.global.qa.template.web.behavior.pages.usercreation.individualregistration;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.data.tools.IdGenerator;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonUsersFields;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CompleteWebElement;
import com.solera.global.qa.template.web.behavior.pages.componentpages.SearchType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.UsersRolesESP;
import com.solera.global.qa.template.web.behavior.pages.componentpages.InvitationToBuyer;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogInPage;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import com.solera.global.qa.template.web.behavior.pages.usercreation.MassiveRegistrationUsers;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

//dropdown

@Slf4j
public class AdministratorMasterInter extends BrowserPage {
    public static final String EMAIL_SEARCH_DYNAMIC = "//td[text()='?']";
    ///
    private LogInPage login = new LogInPage();
    private static final String CLASS_NAME = "AdministratorMasterInter";
    public static final String SEARCH_DYNAMIC = "//td[text()='?']";
    public static final String SEARCH_ADVANCED = "//td[.//a[contains(.,'?')]]";
    ///
    private static final String TEST_USER = "testautomation123@test.com";
    private static final String TEST_USER_NAME = "Ernesto Automation";
    private static final String EMAIL_PREFIX = "testautomation";
    private static final String EMAIL_DOMAIN = "@test.com";
    private static final String NAME_PREFIX = "Automatizacion";
    private static final String ALPHANUMERIC_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String ALFAPREFIX = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static String lastGeneratedEmail;
    private static String lastGeneratedName;
    private static String lastPrefix;
    private static final String NOTIFICATION_MESSAGE = "//div[@class='ant-notification-notice-message']";
    private static final String CLOSE_NOTIFICATION_XP = "//i[@class='anticon anticon-close ant-notification-close-icon']";
    private static final String EXISTENT_EMAIL_NOT = "//div[contains(text(),'El correo electrónico ingresado "
            + "corresponde a una cuenta de usuario existente. Ingrese un nuevo correo.')]";

    public AdministratorMasterInter() {
        super();
    }

    private static String generateDynamicEmail() {
        StringBuilder suffix = new StringBuilder(4);
        suffix.append(randomDigit());
        suffix.append(randomLetter());
        suffix.append(randomDigit());
        suffix.append(randomLetter());
        lastGeneratedEmail = EMAIL_PREFIX + suffix + EMAIL_DOMAIN;
        return lastGeneratedEmail;
    }

    public static String generateDynamicName() {
        StringBuilder suffix = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            suffix.append(ALPHANUMERIC_UPPERCASE.charAt(RANDOM.nextInt(ALPHANUMERIC_UPPERCASE.length())));
        }
        lastGeneratedName = NAME_PREFIX + " " + suffix;
        return lastGeneratedName;
    }


    public static String generateDynamicPrefix() {
        StringBuilder suffix = new StringBuilder(3);
        for (int i = 0; i < 3; i++) {
            suffix.append(ALFAPREFIX.charAt(RANDOM.nextInt(ALFAPREFIX.length())));
        }
        lastPrefix = " " + suffix;
        return lastPrefix;
    }



    public static String getLastGeneratedName() {
        return lastGeneratedName != null ? lastGeneratedName : generateDynamicName();
    }

    private static String getLastGeneratedEmail() {
        return lastGeneratedEmail != null ? lastGeneratedEmail : generateDynamicEmail();
    }

    private static char randomDigit() {
        return (char) ('0' + RANDOM.nextInt(10));
    }

    private static char randomLetter() {
        // Only lowercase to keep predictable format; adjust if uppercase needed.
        return (char) ('a' + RANDOM.nextInt(26));
    }

    public List<CompleteWebElement> fastAdminMasterUserCreation() {
        CommonUsersFields field = new CommonUsersFields();
        CommonComponents components = new CommonComponents();
        List<CompleteWebElement> storedValues = new ArrayList<>();
        //storedValues = components.fillField(field.getNameField(), "Ernesto Automation", storedValues);
        storedValues = components.fillField(field.getNameField(), generateDynamicName(), storedValues);
        storedValues = components.fillField(field.getSurNameField(), "Zarate Delete", storedValues);
        storedValues = components.fillField(field.getLastNameField(), "Admin Master", storedValues);
        storedValues = components.fillField(field.getCountryField(), "Mexico", storedValues);
        storedValues = components.fillField(field.getLanguageField(), "Español", storedValues);
        storedValues = components.fillField(field.getTimeZoneField(), "Guadalajara, Mexico City, Monterrey",
                storedValues);

        // Must be 13 characters unique
        storedValues = components.fillField(field.getRfcField(), "KTAU051005DD5", storedValues);
        storedValues = components.fillField(field.getCurpField(), "RERE980512hdfrdr05", storedValues);
        // Calendar with the method calendarDatesText() is necessary select an action
        // between them
        new CommonComponents().setCalendarDatesText(field.getStartDateField(), field.getCalendarInputField(),
                "29/06/2023");
        storedValues = components.fillField(field.getPhoneNumberField(), "5544872277", storedValues);
        new CommonComponents().setCalendarDatesText(field.getEndDateField(), field.getCalendarInputField(),
                "05/07/2027");

        storedValues = components.fillField(field.getPhoneNumberField(), "5544872277", storedValues);
        storedValues = components.fillField(field.getCellPhoneNumberField(), "5544778899", storedValues);
        storedValues = components.fillField(field.getOtherNumberField(), "5588996641", storedValues);

        log().image(CLASS_NAME + " Screenshot", takeScreenshot());
        new Buttons().clickContinueBtn();

        storedValues = components.fillField(field.getEmployerNumberField(), "123456", storedValues);
        // Must be unique
        storedValues = components.fillField(field.getEmailField(), generateDynamicEmail(), storedValues);
        storedValues = components.fillField(field.getOfficePhoneNumberField(), "5544112233", storedValues);
        storedValues = components.fillField(field.getDepartmentField(), "AutoOnline", storedValues);
        storedValues = components.fillField(field.getJobField(), "Account Manager", storedValues);
        
        log().image("secondScreen", takeScreenshot());
        new Buttons().clickAcceptButton();
        

        return storedValues;
    }

    public List<CompleteWebElement> fastAdminInternUserCreation() {
        CommonUsersFields field = new CommonUsersFields();
        CommonComponents components = new CommonComponents();
        List<CompleteWebElement> storedValues = new ArrayList<>();
        storedValues = components.fillField(field.getNameField(), generateDynamicName(), storedValues);
        storedValues = components.fillField(field.getSurNameField(), "Zarate Delete", storedValues);
        storedValues = components.fillField(field.getLastNameField(), "Admin Master", storedValues);
        storedValues = components.fillField(field.getCountryField(), "Mexico", storedValues);
        storedValues = components.fillField(field.getLanguageField(), "Español", storedValues);
        storedValues = components.fillField(field.getTimeZoneField(), "Guadalajara, Mexico City, Monterrey",
                storedValues);

        // Must be 13 characters unique
        storedValues = components.fillField(field.getRfcField(), "KTAU051005DD5", storedValues);
        storedValues = components.fillField(field.getCurpField(), "RERE980512hdfrdr05", storedValues);
        // Calendar with the method calendarDatesText() is necessary select an action
        // between them
        new CommonComponents().setCalendarDatesText(field.getStartDateField(), field.getCalendarInputField(),
                "29/06/2023");
        storedValues = components.fillField(field.getPhoneNumberField(), "5544872277", storedValues);
        new CommonComponents().setCalendarDatesText(field.getEndDateField(), field.getCalendarInputField(),
                "05/07/2027");

        storedValues = components.fillField(field.getPhoneNumberField(), "5544872277", storedValues);
        storedValues = components.fillField(field.getCellPhoneNumberField(), "5544778899", storedValues);
        storedValues = components.fillField(field.getOtherNumberField(), "5588996641", storedValues);

        log().image(CLASS_NAME + " Screenshot", takeScreenshot());
        new Buttons().clickContinueBtn();

        storedValues = components.fillField(field.getEmployerNumberField(), "123456", storedValues);
        // Must be unique
        //storedValues = components.fillField(field.getEmailField(), TEST_USER, storedValues);
        storedValues = components.fillField(field.getEmailField(), generateDynamicEmail(), storedValues);
        storedValues = components.fillField(field.getOfficePhoneNumberField(), "5544112233", storedValues);
        storedValues = components.fillField(field.getDepartmentField(), "AutoOnline", storedValues);
        storedValues = components.fillField(field.getJobField(), "Account Manager", storedValues);
        
        log().image("secondScreen", takeScreenshot());
        new Buttons().clickInsuranceCarrierField();
        log().image("thirdScreen", takeScreenshot());
        new Buttons().jsClickAcceptButton();
        

        return storedValues;
    }

    public List<CompleteWebElement> fastInsuranceCompanyInternUserCreation() {
        CommonUsersFields field = new CommonUsersFields();
        CommonComponents components = new CommonComponents();
        List<CompleteWebElement> storedValues = new ArrayList<>();
        storedValues = components.fillField(field.getNameField(), "Ernesto Automation", storedValues);
        storedValues = components.fillField(field.getNameField(), generateDynamicName(), storedValues);
        
        storedValues = components.fillField(field.getSurNameField(), "Zarate Delete", storedValues);
        storedValues = components.fillField(field.getLastNameField(), "Admin Master", storedValues);
        storedValues = components.fillField(field.getCountryField(), "Mexico", storedValues);
        storedValues = components.fillField(field.getLanguageField(), "Español", storedValues);
        storedValues = components.fillField(field.getTimeZoneField(), "Guadalajara, Mexico City, Monterrey",
                storedValues);

        // Calendar with the method calendarDatesText() is necessary select an action
        // between them
        new CommonComponents().setCalendarDatesText(field.getStartDateField(), field.getCalendarInputField(),
                "29/06/2023");
        storedValues = components.fillField(field.getPhoneNumberField(), "5544872277", storedValues);
        new CommonComponents().setCalendarDatesText(field.getEndDateField(), field.getCalendarInputField(),
                "05/07/2027");

        storedValues = components.fillField(field.getPhoneNumberField(), "5544872277", storedValues);
        storedValues = components.fillField(field.getCellPhoneNumberField(), "5544778899", storedValues);
        storedValues = components.fillField(field.getOtherNumberField(), "5588996641", storedValues);

        log().image(CLASS_NAME + " Screenshot", takeScreenshot());
        new Buttons().clickContinueBtn();
        //storedValues = components.fillField(field.getEmployerNumberField(), "123456", storedValues);
        // Must be unique
        storedValues = components.fillField(field.getInsuranceEmailField(), generateDynamicEmail(), storedValues);
        storedValues = components.fillField(field.getInsurancePhoneField(), "5544112233", storedValues);
        storedValues = components.fillField(field.getInsuranceCarrierField(), "ANA SEGUROS", storedValues);
        
       // log().image("secondScreen", takeScreenshot());
      //  new Buttons().clickInsuranceCarrierField();
        log().image("secondScreen", takeScreenshot());
        new Buttons().clickAcceptButton();
        

        return storedValues;
    }


    public List<CompleteWebElement> fastBuyerPhysicalUserCreation() {
        CommonUsersFields field = new CommonUsersFields();
        CommonComponents components = new CommonComponents();
        List<CompleteWebElement> storedValues = new ArrayList<>();
        storedValues = components.fillField(field.getNameField(), getLastGeneratedName(), storedValues);
        storedValues = components.fillField(field.getSurNameField(), "Zarate Delete", storedValues);
        storedValues = components.fillField(field.getLastNameField(), "Admin Master", storedValues);
        storedValues = components.fillField(field.getBusinessPersonalNameField(), "CHAVARRIA", storedValues);
        storedValues = components.fillField(field.getEmailField(), generateDynamicEmail(), storedValues);
        new CommonComponents().setCalendarDatesText(field.getPersonalBirthDateField(), field.getCalendarInputField(),
        "29/06/2000");
        // Must be 13 characters unique
        storedValues = components.fillField(field.getRfcField(), "UJED520318UQA", storedValues);

        //storedValues = components.fillField(field.getRfcField(), IdGenerator.getNewRfc(), storedValues);
        storedValues = components.fillField(field.getCurpField(), "RERE980512hdfrdr05", storedValues);

        storedValues = components.fillField(field.getIdField(), "INE", storedValues);
        storedValues = components.fillField(field.getFolioField(), "123", storedValues);
        
        storedValues = components.fillField(field.getLanguageField(), "Español", storedValues);
        storedValues = components.fillField(field.getTimeZoneField(), "Guadalajara, Mexico City, Monterrey",
                storedValues);

       
        // Calendar with the method calendarDatesText() is necessary select an action
        // between them
        new CommonComponents().setCalendarDatesText(field.getStartDateField(), field.getCalendarInputField(),
                "29/01/2027");
                
        click(field.getEndDateInputField());
        new CommonComponents().setCalendarDatesText(field.getEndDateField(), field.getCalendarInputField(),"05/07/2027");


        log().image(CLASS_NAME + " Screenshot", takeScreenshot());
        new Buttons().clickContinueBtn();
        storedValues = components.fillField(field.getPersonalAddressTypeField(), "Fiscal", storedValues);
        storedValues = components.fillField(field.getPersonalCountryField(), "Mexico", storedValues);
        storedValues = components.fillField(field.getPersonalStateField(), "AGUASCALIENTES", storedValues);
        storedValues = components.fillField(field.getPersonalStreetField(), "Calle test", storedValues);
        storedValues = components.fillField(field.getPersonalNumberExtField(), "123", storedValues);
        storedValues = components.fillField(field.getPersonalPostalCodeField(), "01234", storedValues);
        storedValues = components.fillField(field.getPersonalColonyField(), "Colonia test", storedValues);
        storedValues = components.fillField(field.getPersonalDelegationField(), "Delegacion test", storedValues);
        storedValues = components.fillField(field.getPersonalCityField(), "Ciudad test", storedValues);

        storedValues = components.fillField(field.getPhoneNumberField(), "5544872277", storedValues);
        storedValues = components.fillField(field.getCellPhoneNumberField(), "5544778899", storedValues);
        storedValues = components.fillField(field.getOtherNumberField(), "5588996641", storedValues);
        new CommonComponents().cargarExpediente();
        new CommonComponents().uploadExpedienteFile();
        new CommonComponents().closeModal();

        new Buttons().clickContinueBtn();
        storedValues = components.fillField(field.getPersonalRegimeField(), "616 - SIN OBLIGACIONES FISCALES", storedValues);
        storedValues = components.fillField(field.getPersonalGradeField(), "Grado", storedValues);
        storedValues = components.fillField(field.getPersonalOccupationField(), "Ocupacion", storedValues);
        storedValues = components.fillField(field.getPersonalJobField(), "Trabajo", storedValues);
        new CommonComponents().selectQuestionAnswer(1, "no");
        new CommonComponents().selectQuestionAnswer(2, "no");
        new CommonComponents().selectQuestionAnswer(3, "no");
        new CommonComponents().selectQuestionAnswer(4, "no");
        click(getElement(By.xpath(CommonUsersFields.PERSONAL_INSURANCE.replace("?", "AIG"))));

        new Buttons().clickAcceptButton();

        return storedValues;
    }
    
    public List<CompleteWebElement> fastBuyerMoralUserCreation() {
        CommonUsersFields field = new CommonUsersFields();
        CommonComponents components = new CommonComponents();
        List<CompleteWebElement> storedValues = new ArrayList<>();
        storedValues = components.fillField(field.getBusinessNameField(), "CHAVARRIA", storedValues);
        storedValues = components.fillField(field.getReasonNameField(), "CHAVARRIA", storedValues);
        storedValues = components.fillField(field.getRfcField(), "AOZ260106MI7", storedValues);
        storedValues = components.fillField(field.getMoralActivityTurnField(), "TEST", storedValues);
        storedValues = components.fillField(field.getMoralRegimeTurnField(), "601 - GENERAL DE LEY PERSONAS MORALES", storedValues);
        storedValues = components.fillField(field.getMoralSerieFielField(), "13", storedValues);
        storedValues = components.fillField(field.getMoralFolioMercantilField(), "12", storedValues);
        new CommonComponents().setCalendarDatesText(field.getMoralEndDateField(),field.getCalendarInputField(),"05/08/2026");

        new CommonComponents().setCalendarDatesText(field.getMoralStartDateField(),field.getCalendarInputField(),"29/06/2026");
        
        new CommonComponents().setCalendarDatesText(field.getMoralConstitutionDateField(), field.getCalendarInputField(),"29/06/2023");
       
        storedValues = components.fillField(field.getMoralAddressTypeField(), "Fiscal", storedValues);
        storedValues = components.fillField(field.getMoralCountryField(), "Mexico", storedValues);
        storedValues = components.fillField(field.getMoralStateField(), "AGUASCALIENTES", storedValues);
        storedValues = components.fillField(field.getMoralStreetField(), "Calle test", storedValues);
        storedValues = components.fillField(field.getMoralNumberExtField(), "123", storedValues);
        storedValues = components.fillField(field.getMoralPostalCodeField(), "01234", storedValues);
        storedValues = components.fillField(field.getMoralColonyField(), "Colonia test", storedValues);
        storedValues = components.fillField(field.getMoralDelegationField(), "Delegacion test", storedValues);
        storedValues = components.fillField(field.getMoralCityField(), "Ciudad test", storedValues);


        new Buttons().clickContinueBtn();


        storedValues = components.fillField(field.getNameField(), getLastGeneratedName(), storedValues);
        storedValues = components.fillField(field.getSurNameField(), "Zarate Delete", storedValues);
        storedValues = components.fillField(field.getLastNameField(), "Admin Master", storedValues);
        storedValues = components.fillField(field.getEmailField(), generateDynamicEmail(), storedValues);
        new CommonComponents().setCalendarDatesText(field.getMoralBirthDateField(), field.getCalendarInputField(),
        "29/06/2000");
        // Must be 13 characters unique
        storedValues = components.fillField(field.getRfcField(), "KTAU051005DD5", storedValues);
        storedValues = components.fillField(field.getCurpField(), "RERE980512hdfrdr05", storedValues);

        storedValues = components.fillField(field.getMoralIdField(), "INE", storedValues);
        storedValues = components.fillField(field.getFolioField(), "123", storedValues);
        
        storedValues = components.fillField(field.getLanguageField(), "Español", storedValues);
        storedValues = components.fillField(field.getTimeZoneField(), "Guadalajara, Mexico City, Monterrey",
                storedValues);

        storedValues = components.fillField(field.getMoralAddressTypeField(), "Fiscal", storedValues);
        storedValues = components.fillField(field.getMoralCountryField(), "Mexico", storedValues);
        storedValues = components.fillField(field.getMoralStateField(), "AGUASCALIENTES", storedValues);
        storedValues = components.fillField(field.getMoralStreetField(), "Calle test", storedValues);
        storedValues = components.fillField(field.getMoralNumberExtField(), "123", storedValues);
        storedValues = components.fillField(field.getMoralPostalCodeField(), "01234", storedValues);
        storedValues = components.fillField(field.getMoralColonyField(), "Colonia test", storedValues);
        storedValues = components.fillField(field.getMoralDelegationField(), "Delegacion test", storedValues);
        storedValues = components.fillField(field.getMoralCityField(), "Ciudad test", storedValues);

        storedValues = components.fillField(field.getPhoneNumberField(), "5544872277", storedValues);
        storedValues = components.fillField(field.getCellPhoneNumberField(), "5544778899", storedValues);
        storedValues = components.fillField(field.getOtherNumberField(), "5588996641", storedValues);

        new CommonComponents().cargarExpediente();
        new CommonComponents().uploadExpedienteFile();
        new CommonComponents().closeModal();


        log().image("secondScreen", takeScreenshot());
        new Buttons().clickContinueBtn();

        click(getElement(By.xpath(CommonUsersFields.PERSONAL_INSURANCE.replace("?", "AIG"))));
        new Buttons().clickAcceptButton();
        
        return storedValues;
    }

    public List<CompleteWebElement> fastSupplierCraneUserCreation() {
        CommonUsersFields field = new CommonUsersFields();
        CommonComponents components = new CommonComponents();
        List<CompleteWebElement> storedValues = new ArrayList<>();
        log().image("Page state before waiting for name field", takeScreenshot());
        waitForElementVisibility(getElement(By.xpath(CommonUsersFields.USER_NAME)), Timeouts.LOAD_PAGE);
        //storedValues = components.fillField(field.getNameField(), "Ernesto Automation", storedValues);
        storedValues = components.fillField(field.getNameField(), generateDynamicName(), storedValues);
        storedValues = components.fillField(field.getSurNameField(), "Zarate Delete", storedValues);
        storedValues = components.fillField(field.getLastNameField(), "Admin Master", storedValues);
        storedValues = components.fillField(field.getCountryField(), "Mexico", storedValues);
        storedValues = components.fillField(field.getLanguageField(), "Español", storedValues);
        storedValues = components.fillField(field.getTimeZoneField(), "Guadalajara, Mexico City, Monterrey",
                storedValues);

        // Calendar with the method calendarDatesText() is necessary select an action
        // between them
        new CommonComponents().setCalendarDatesText(field.getStartDateField(), field.getCalendarInputField(),
                "29/06/2023");
        storedValues = components.fillField(field.getPhoneNumberField(), "5544872277", storedValues);
        new CommonComponents().setCalendarDatesText(field.getEndDateField(), field.getCalendarInputField(),
                "05/07/2027");

        storedValues = components.fillField(field.getPhoneNumberField(), "5544872277", storedValues);
        storedValues = components.fillField(field.getCellPhoneNumberField(), "5544778899", storedValues);
        storedValues = components.fillField(field.getOtherNumberField(), "5588996641", storedValues);

        log().image(CLASS_NAME + " Screenshot", takeScreenshot());
        new Buttons().clickContinueBtn();
        // Must be unique
        storedValues = components.fillField(field.getProviderIdField(), "CHAVARRIA", storedValues);
        //storedValues = components.fillField(field.getEmailField(), TEST_USER, storedValues);
        storedValues = components.fillField(field.getEmailField(), generateDynamicEmail(), storedValues);
        log().image("secondScreen", takeScreenshot());
        new Buttons().clickAcceptButton();
        

        return storedValues;
    }

    public List<CompleteWebElement> fastAdminInsuredUserCreation() {
        CommonUsersFields field = new CommonUsersFields();
        CommonComponents components = new CommonComponents();
        List<CompleteWebElement> storedValues = new ArrayList<>();
        storedValues = components.fillField(field.getNameField(), generateDynamicName(), storedValues);
        storedValues = components.fillField(field.getSurNameField(), "Zarate Delete", storedValues);
        storedValues = components.fillField(field.getLastNameField(), "Admin Master", storedValues);
        storedValues = components.fillField(field.getCountryField(), "Mexico", storedValues);
        storedValues = components.fillField(field.getLanguageField(), "Español", storedValues);
        storedValues = components.fillField(field.getTimeZoneField(), "Guadalajara, Mexico City, Monterrey",
                storedValues);

        // Calendar with the method calendarDatesText() is necessary select an action
        // between them
        new CommonComponents().setCalendarDatesText(field.getEndDateField(), field.getCalendarInputField(),"05/07/2027");
        new CommonComponents().setCalendarDatesText(field.getStartDateField(), field.getCalendarInputField(),"29/06/2026");

        storedValues = components.fillField(field.getEmailField(), generateDynamicEmail(), storedValues);

        storedValues = components.fillField(field.getInsuredAccidentSerialField(), "QAT2603001348", storedValues);
        storedValues = components.fillField(field.getInsuredCaseIdField(), "TEST VEH 1JKTS177459622635", storedValues);
        storedValues = components.fillField(field.getInsuredVehicleSerialField(), "1JKTS177459622635", storedValues);
        storedValues = components.fillField(field.getCellPhoneNumberField(), "5544778899", storedValues);

        log().image(CLASS_NAME + " Screenshot", takeScreenshot());
        new Buttons().clickBuscarBtn();
        new Buttons().clickAcceptButton();

        return storedValues;
    }


    public List<Object> validateAllFields(String searchMainElement) {
        int correctValues = 0;
        CommonUsersFields field = new CommonUsersFields();
        new CommonComponents().findHRefElement(new CommonComponents()
                .dynamicWebElement(EMAIL_SEARCH_DYNAMIC, searchMainElement));
        //correctValues = validateFromValue(field.getNameField(), "Ernesto Automation", correctValues);
        correctValues = validateFromValue(field.getNameField(), getLastGeneratedName().toUpperCase(), correctValues);
        correctValues = validateFromValue(field.getSurNameField(), "Zarate Delete", correctValues);
        correctValues = validateFromValue(field.getLastNameField(), "Admin Master", correctValues);

        if (correctValues == 11) {
            List<Object> results = new ArrayList<>();
            results.add(correctValues);
            results.add(11 - correctValues);
            results.add(true);
            return results;
        }
        List<Object> results = new ArrayList<>();
        results.add(correctValues);
        results.add(11 - correctValues);
        results.add(false);
        return results;

    }

    private int validateFromValue(WebElement element2Validate, String expectedValue, Integer correctValues) {
        if (getValue(element2Validate).equals(expectedValue.toUpperCase())) {
            ++correctValues;
        }
        return correctValues;
    }

    
    
    public boolean individualRegistrationAdminIntern(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.ADMINISTRATOR_INTERN);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastAdminInternUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);

        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());


        menuPage.clickUsers();
        
        //Validar con Avanzado
        //new CommonSearch().swapAdvancedSearch(SearchType.ADVANCED_SEARCH);

        userMenu.clickUsersConsult(UsersRolesESP.ADMINISTRATOR_INTERN, getLastGeneratedName());


        click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));

                Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

        log().image("correct: " + correct, takeScreenshot());
        return true;
    }


    public boolean individualRegistrationAdminMaster(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.ADMINISTRATOR_MASTER);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastAdminMasterUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);

        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();

        userMenu.clickUsersConsult(UsersRolesESP.ADMINISTRATOR_MASTER, getLastGeneratedName());

        click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));

        Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

        log().image("correct: " + correct, takeScreenshot());
        return true;
    }


    public boolean individualRegistrationInsuranceCompanyIntern(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.INSURANCE_COMPANY_INTERN);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastInsuranceCompanyInternUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);

        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();

        userMenu.clickUsersConsult(UsersRolesESP.INSURANCE_COMPANY_INTERN, getLastGeneratedName());
        //new CommonComponents().findHRefElement(new CommonComponents()
        //.dynamicWebElement(SEARCH_DYNAMIC, TEST_USER));        
        click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));

        Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

        log().image("correct: " + correct, takeScreenshot());
        return true;
    }

    public boolean individualRegistrationBuyerPhysical(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.BUYER_PHYSICAL);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastBuyerPhysicalUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);


        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();

        
        userMenu.clickUsersConsult(UsersRolesESP.BUYER_PHYSICAL, getLastGeneratedName());

        click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));

        Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

        log().image("correct: " + correct, takeScreenshot());
        return true;
    }

    public boolean individualRegistrationBuyerMoral(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.BUYER_MORAL);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastBuyerMoralUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);

        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();

        userMenu.clickUsersConsult(UsersRolesESP.BUYER_MORAL, getLastGeneratedEmail());

        click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));
        Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

        log().image("correct: " + correct, takeScreenshot());
        return true;
    }

    public boolean individualRegistrationSupplierCrane(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.SUPPLIER_CRANE);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastSupplierCraneUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);


        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();

        userMenu.clickUsersConsult(UsersRolesESP.SUPPLIER_CRANE, getLastGeneratedEmail());
        click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));

                
         Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

        log().image("correct: " + correct, takeScreenshot());
        return true;
    }

    public boolean IndividualRegistrationAdminInsured(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.INSURED);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastAdminInsuredUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);


        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();

          userMenu.clickUsersConsult(UsersRolesESP.INSURED, getLastGeneratedName());
            
          click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));
         Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

        log().image("correct: " + correct, takeScreenshot());
        return true;
    }

    private String getStoredValueFromField(List<CompleteWebElement> storedValues, WebElement field) {
        String fieldId = field.getAttribute("id");
        for (CompleteWebElement element : storedValues) {
            String elementId = element.getWebElement().getAttribute("id");
            if (fieldId != null && elementId != null && fieldId.equals(elementId)) {
                return element.getDesiredValue();
            }
        }
        return null;
    }

    
    public boolean advancedRegistrationSupplierCrane(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.SUPPLIER_CRANE);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastSupplierCraneUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);


        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();


        userMenu.clickUsersConsultAdvance(UsersRolesESP.SUPPLIER_CRANE,getLastGeneratedName() );

        new CommonSearch().swapAdvancedSearch(SearchType.ADVANCED_SEARCH);

        click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));



        
        Integer correct = new CommonComponents().validateCaseCreation(consultSearch);
        log().image("Fields validated correctly: " + correct + "/" + consultSearch.size(), takeScreenshot());
        return true;
    }

    public boolean advancedRegistrationAdministratorMaster(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.ADMINISTRATOR_MASTER);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastAdminMasterUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);


        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();

          userMenu.clickUsersConsultAdvance(UsersRolesESP.ADMINISTRATOR_MASTER, getLastGeneratedName());
  
          new CommonSearch().swapAdvancedSearch(SearchType.ADVANCED_SEARCH);
  
          click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));

         Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

        log().image("correct: " + correct, takeScreenshot());
        return true;
    }
   
    public boolean advancedRegistrationAdminIntern(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.ADMINISTRATOR_INTERN);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastAdminInternUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);


        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();

        userMenu.clickUsersConsultAdvance(UsersRolesESP.ADMINISTRATOR_INTERN, getLastGeneratedName());
            
        new CommonSearch().swapAdvancedSearch(SearchType.ADVANCED_SEARCH);
  
        click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));
        
        Integer correct = new CommonComponents().validateCaseCreation(consultSearch);
        log().image("Fields validated correctly: " + correct + "/" + consultSearch.size(), takeScreenshot());
        return true;
    }
    
    public boolean advancedRegistrationAdminInsured(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.INSURED);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastAdminInsuredUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);


        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();



        userMenu.clickUsersConsultAdvance(UsersRolesESP.INSURED, getLastGeneratedName());

        new CommonSearch().swapAdvancedSearch(SearchType.ADVANCED_SEARCH);
  
        click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));
        Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

        log().image("correct: " + correct, takeScreenshot());
        return true;
    }

    public boolean AdvancedInsuranceCompany(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.INSURANCE_COMPANY_INTERN);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastInsuranceCompanyInternUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);

        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();

        userMenu.clickUsersConsultAdvance(UsersRolesESP.INSURANCE_COMPANY_INTERN, getLastGeneratedName());
        new CommonSearch().swapAdvancedSearch(SearchType.ADVANCED_SEARCH);

        click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));

        Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

        log().image("correct: " + correct, takeScreenshot());
        return true;
    }

    public boolean AdvancedBuyerPhysical(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.BUYER_PHYSICAL);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastBuyerPhysicalUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);


        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();

        
        userMenu.clickUsersConsult(UsersRolesESP.BUYER_PHYSICAL, getLastGeneratedName());
        new CommonSearch().swapAdvancedSearch(SearchType.ADVANCED_SEARCH);

        click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));

        Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

        log().image("correct: " + correct, takeScreenshot());
        return true;
    }

    public boolean AdvancedBuyerMoral(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickIndividualRegister(UsersRolesESP.BUYER_MORAL);
        AdministratorMasterInter newAdminMasterUser = new AdministratorMasterInter();
        final List<CompleteWebElement> consultSearch = newAdminMasterUser.fastBuyerMoralUserCreation();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);

        click(getElement(By.xpath(CLOSE_NOTIFICATION_XP)));

        log().image("Supposed notification faded", takeScreenshot());

        menuPage.clickUsers();

        userMenu.clickUsersConsult(UsersRolesESP.BUYER_MORAL, getLastGeneratedName());
        new CommonSearch().swapAdvancedSearch(SearchType.ADVANCED_SEARCH);

        click(new CommonComponents().dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName().toUpperCase()));
        Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

        log().image("correct: " + correct, takeScreenshot());
        return true;
    }

    public List<CompleteWebElement> fastInviteBuyerIndividual() {
        List<CompleteWebElement> storedValues = new ArrayList<>();
        CommonComponents components = new CommonComponents();
        CommonUsersFields field = new CommonUsersFields();

        // Wait for the page to fully load before interacting with the dropdown
        waitForElementPresence(By.xpath(CommonUsersFields.INVITATION_SEND_TYPE), Timeouts.LOAD_ELEMENT);
        // Click dropdown to open the portal options list
        click(field.getInvitationSendTypeField());
        sleep(Timeouts.SHORT_TIME);
        // Select "Individual" from portal-level dropdown (not a sibling — uses body-level XPath)
        click(new CommonComponents().dynamicWebElement(CommonUsersFields.INVITATION_DROPDOWN_OPTION, "Individual"));
        log().image("Send type selected: Individual", takeScreenshot());


        storedValues = components.fillField(field.getInvitationFirstnameField(), "AutomationBuyer", storedValues, 0);
        storedValues = components.fillField(field.getInvitationSurnameField(), "Automation", storedValues, 0);
        storedValues = components.fillField(field.getInvitationLastnameField(), "QA", storedValues, 0);
        storedValues = components.fillField(field.getInvitationEmailField(), generateDynamicEmail(), storedValues, 0);
        storedValues = components.fillField(field.getInvitationPhoneField(), "5512345678", storedValues, 0);
        log().image("Invitation form filled", takeScreenshot());

        new Buttons().jsClickAcceptButton();


        waitForElementVisibility(field.getInvitationModalField());
        log().image("Invitation preview modal", takeScreenshot());
        new Buttons().jsClickAcceptButtonBuyer();

        return storedValues;
    }

    public void fastInviteBuyerMassive() {
        CommonUsersFields field = new CommonUsersFields();

        // Wait for the page to fully load before interacting with the dropdown
        waitForElementPresence(By.xpath(CommonUsersFields.INVITATION_SEND_TYPE), Timeouts.LOAD_ELEMENT);
        // Select "Masivo" from portal-level dropdown
        click(field.getInvitationSendTypeField());
        sleep(Timeouts.SHORT_TIME);
        click(new CommonComponents().dynamicWebElement(CommonUsersFields.INVITATION_DROPDOWN_OPTION,
                InvitationToBuyer.MASSIVE.getSendType()));
        log().image("Send type selected: Masivo", takeScreenshot());

        // Wait for massive invitation buttons to appear
        waitForElementPresence(By.xpath(CommonUsersFields.INVITATION_ATTACH_BUTTON), Timeouts.LOAD_ELEMENT);
        log().image("Massive invitation buttons visible", takeScreenshot());

        // Send file path directly to hidden input — do NOT click "Adjuntar documento"
        // Clicking the button opens the OS native file dialog which Selenium cannot control
        String absoluteFilePath = new java.io.File(CommonUsersFields.MASSIVE_CSV_FILE_PATH).getAbsolutePath();
        getElement(By.xpath(CommonUsersFields.INVITATION_FILE_INPUT)).sendKeys(absoluteFilePath);
        log().image("CSV file attached", takeScreenshot());



        // Send invitations — Enviar button (type=submit, distinct from Aceptar)
        Buttons buttons = new Buttons();
        waitForElementVisibility(buttons.getSendButton());
        buttons.clickSendButton();
        log().image("Invitations sent", takeScreenshot());

        // Confirm file upload success modal
        waitForElementVisibility(field.getInvitationModalField());
        new Buttons().jsClickAcceptButton();
        log().image("File upload confirmed", takeScreenshot());

        // Confirm file upload success modal
        //waitForElementVisibility(field.getInvitationModalField());
        new Buttons().jsClickAcceptButtonBuyer();
        log().image("Accept", takeScreenshot());

        new Buttons().jsClickAcceptButton();
        log().image("Accept 2", takeScreenshot());

        new Buttons().jsClickAcceptButtonBuyer();
        log().image("Accept 3", takeScreenshot());

        new Buttons().jsClickAcceptButtonBuyer();
        log().image("Accept 4", takeScreenshot());
    }

    public boolean inviteMassiveBuyers(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        menuPage.clickUsers();
        waitForElementPresence(By.xpath(MassiveRegistrationUsers.INVITE_BUYERS), Timeouts.LOAD_ELEMENT);
        click(getElement(By.xpath(MassiveRegistrationUsers.INVITE_BUYERS)));
        sleep(3000);
        log().image("Invite buyers page loaded", takeScreenshot());

        AdministratorMasterInter invitation = new AdministratorMasterInter();
        invitation.fastInviteBuyerMassive();

        //waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);
        log().image("Massive buyer invitation sent successfully", takeScreenshot());

        return true;
    }

    public boolean inviteIndividualBuyer(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        menuPage.clickUsers();
        waitForElementPresence(By.xpath(MassiveRegistrationUsers.INVITE_BUYERS), Timeouts.LOAD_ELEMENT);
        click(getElement(By.xpath(MassiveRegistrationUsers.INVITE_BUYERS)));
        sleep(3000);
        log().image("Invite buyers page loaded", takeScreenshot());

        AdministratorMasterInter invitation = new AdministratorMasterInter();
        invitation.fastInviteBuyerIndividual();

        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);
        log().image("Buyer invitation sent successfully", takeScreenshot());

        return true;
    }
}
