package com.solera.global.qa.template.web.behavior.pages.usercreation;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.AolWebPropertiesReader;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonUsersFields;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CompleteWebElement;
import com.solera.global.qa.template.web.behavior.pages.componentpages.InvitationToBuyer;
import com.solera.global.qa.template.web.behavior.pages.componentpages.UsersRolesESP;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class MassiveRegistrationUsers extends BrowserPage {
    //Xpath to describe all the option inside data.users selection
    public static final String INDIVIDUAL_REGISTER = "//a[@href='/users/add/individual']";
    public static final String MASSIVE_REGISTER = "//a[@href='/users/add/massive']";
    public static final String INVITE_BUYERS = "//a[@href='/users/buyersInvitation']";
    public static final String SEARCH_USERS = "//a[@href='/users/search/' and text()='Consultar']";
    public static final String VALIDATE_INFORMATION =
            "//a[@href='/users/check/buyer']/parent::*";//To describe, change to tittle
    public static final String CHECK_BUYER =
            "//a[@href='/users/check/buyer']"; //it appears when the VALIDATE_INFORMATION is clicked.

    //RoleField data.users
    public static final String USER_ROLE_FIELD = "addUser_rolUser";
    public static final String USER_ROLE_FIELD_MASSIVE = "addUserMassive_rolUser";
    public static final String USER_ROLE_FIELD_SEARCH = "search-users_groupId";

    //Dropdown user types
    public static final String USER_TYPE_FIELD = "//div[@id='addUser_typeUser']/div";
    public static final String USER_TYPE_FIELD_MASSIVE = "addUserMassive_typeUser";

    public static final String USER_ROLE_OPTIONS_FIELD = "//ul[@role='listbox']";
    public static final String DYNAMIC_USER_ROLE = "//ul[@role='listbox']/li[text()='?']";
    public static final String DYNAMIC_USER_ROLE_TYPE_REGISTER = "//ul[@role='listbox']/li[text()='?']";
    public static final String LOAD_DOCUMENT_MASSIVE_REGISTRATION =
            "//input[@accept='application/vnd.openxmlformats-officedocument.spreadsheetml.sheet']";
    public static final String LOAD_PROOF_OF_ADDRESS =
            "//input[@accept='application / zip,application/x-zip-compressed,multipart / x - zip']";

    //invitation send type dropdown
    public static final String INVITATION_SEND_TYPE_FIELD = "buyersInvitation_sendType"; // TODO: verify id in page
    public static final String DYNAMIC_INVITATION_SEND_TYPE = "//ul[@role='listbox']/li[text()='?']";

    //general or advance search
    public static final String GENERAL_SEARCH_FIELD = "search-users_Wildcard";
    public static final String GENERAL_ADVANCE_SEARCH_FIELD = "swap-search-button";
    public static final String GENERAL_SEARCH_NAME_FIELD = "search-users_name";

    



    @FindBy(id = USER_ROLE_FIELD)
    WebElement userRoleField;
    @FindBy(id = USER_ROLE_FIELD_MASSIVE)
    WebElement userRoleFieldMassive;
    @FindBy(id = USER_ROLE_FIELD_SEARCH)
    WebElement userSearch;
    @FindBy(xpath = USER_ROLE_OPTIONS_FIELD)
    WebElement userOptionsField;
    @FindBy(xpath = USER_TYPE_FIELD)
    WebElement userTypeField;
    @FindBy(id = USER_TYPE_FIELD_MASSIVE)
    WebElement userTypeFieldMassive;
    @FindBy(xpath = INDIVIDUAL_REGISTER)
    WebElement individualRegister;
    @FindBy(xpath = MASSIVE_REGISTER)
    WebElement massiveRegister;
    @FindBy(xpath = INVITE_BUYERS)
    WebElement inviteBuyers;
    @FindBy(id = INVITATION_SEND_TYPE_FIELD)
    WebElement invitationSendTypeField;
    @FindBy(xpath = SEARCH_USERS)
    WebElement usersConsult;
    @FindBy(xpath = VALIDATE_INFORMATION)
    WebElement validateInformation;
    @FindBy(xpath = CHECK_BUYER)
    WebElement checkBuyer;
    @FindBy(xpath = LOAD_DOCUMENT_MASSIVE_REGISTRATION)
    WebElement loadLayout;
    @FindBy(xpath = LOAD_PROOF_OF_ADDRESS)
    WebElement loadProofAddress;
    @FindBy(id = GENERAL_SEARCH_FIELD)
    WebElement generalSearchField;
    @FindBy(id = GENERAL_ADVANCE_SEARCH_FIELD)
    WebElement generalAdvanceSearchField;
    @FindBy(id = GENERAL_SEARCH_NAME_FIELD)
    WebElement generalSearchNameField;

    public MassiveRegistrationUsers() {
        super();
    }

    ///
    private static final String CLASS_NAME = "MassiveRegistrationUsers";

    ///


    //methods to open submenu

    public void clickIndividualRegister(UsersRolesESP userRole) {
        //Using enum, add a user role element to fill automatically the kind of user needed.
        click(individualRegister);
        selectRoleTypeIndividual(userRole);
    }

    public void clickIndividualRegister(InvitationToBuyer inviteType) {
        click(inviteBuyers);
        click(invitationSendTypeField);
        click(dynamicWebElement(DYNAMIC_INVITATION_SEND_TYPE, inviteType.getSendType()));
    }

    public void clickMassiveRegister(UsersRolesESP userRole) {
        click(massiveRegister);
        selectRoleTypeMassive(userRole);
    }

    public void clickInviteBuyers(UsersRolesESP userRole) {
        click(inviteBuyers);
        click(userSearch);
    }

    public void clickUsersConsult(UsersRolesESP userRole,String parameter) {
        log().image("Before UsersConsult", takeScreenshot());
        sleep(1000);
        click(getElement(By.xpath(SEARCH_USERS)));
        log().image("UsersConsult", takeScreenshot());
        userConsult(userRole);
        click(generalSearchField);
        sendKeys(generalSearchField,parameter);
        new Buttons().clickSearchBtn();
    }

    public void clickUsersConsultAdvance(UsersRolesESP userRole,String parameter) {
        log().image("Before UsersConsult", takeScreenshot());
        sleep(10000);  
        log.info("Waiting for the users to be loaded CONSULTAR despues de 10 segundos");
        click(getElement(By.xpath(SEARCH_USERS)));
        log.info("UsersConsult clicked");
        userConsult(userRole);
        log.info("Waiting for the users to be loaded BUSCAR AVANZADO despues de 10 segundos");
        click(generalAdvanceSearchField);
        log.info("GeneralAdvanceSearchField clicked");
        sleep(10000);  
        click(generalSearchNameField);
        log.info("Waiting for click on name field");

        sendKeys(generalSearchNameField, parameter);
        log.info("Parameter '{}' written in search field", parameter);

        new Buttons().clickSearchBtn();
        log.info("SearchBtn clicked");
    }

    /**
     * Busca usuarios por nombre en lugar de seleccionar el rol.
     * Escribe el nombre directamente en el campo de búsqueda general.
     * 
     * @param userName El nombre del usuario a buscar
     */
    public void clickUsersConsultAdvanceByName(String userName) {
        log().image("Before UsersConsult", takeScreenshot());
        sleep(10000);  
        log.info("Waiting for the users to be loaded CONSULTAR despues de 10 segundos");
        click(getElement(By.xpath(SEARCH_USERS)));
        log.info("UsersConsult clicked");
        // En lugar de seleccionar rol, escribir el nombre directamente en el campo de búsqueda
        click(generalSearchField);
        sendKeys(generalSearchField, userName);
        log.info("Name '{}' written in search field", userName);
        log.info("Waiting for the users to be loaded BUSCAR AVANZADO despues de 10 segundos");
        click(generalAdvanceSearchField);
        log.info("GeneralAdvanceSearchField clicked");
        sleep(10000);  
        log.info("Waiting for the users to be loaded");
        log.info("SearchBtn clicked");
    }

    /**
     * Busca usuarios por nombre extraído de la lista de CompleteWebElement.
     * Extrae automáticamente el nombre del campo NameField desde storedValues.
     * 
     * @param storedValues Lista de CompleteWebElement que contiene los valores guardados
     */
    public void clickUsersConsultAdvanceByName(List<CompleteWebElement> storedValues) {
        CommonUsersFields field = new CommonUsersFields();
        String nameFieldValue = getStoredValueFromField(storedValues, field.getNameField());
        if (nameFieldValue == null || nameFieldValue.isEmpty()) {
            log.error("Could not find stored value for getNameField()");
            throw new IllegalArgumentException("Name field value not found in storedValues");
        }
        log.info("Extracted name from storedValues: {}", nameFieldValue);
        clickUsersConsultAdvanceByName(nameFieldValue);
    }

    /**
     * Método helper para extraer el valor guardado de un campo desde la lista de CompleteWebElement.
     * 
     * @param storedValues Lista de CompleteWebElement que contiene los valores guardados
     * @param field El campo WebElement del cual se quiere extraer el valor
     * @return El valor guardado del campo, o null si no se encuentra
     */
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

    public void clickValidateInformation() {
        click(validateInformation);
        click(checkBuyer);
    }

    private void selectRoleTypeIndividual(UsersRolesESP userSelected) {
        click(userRoleField);
        click(dynamicWebElement(DYNAMIC_USER_ROLE,userSelected.getUserSelected()));
        if (userSelected != UsersRolesESP.INSURED) {
            sleep(3000);
            waitForElementVisibility(userTypeField);
            click(userTypeField);
            click(dynamicWebElement(DYNAMIC_USER_ROLE_TYPE_REGISTER,
                    userSelected.getTypeUser()));//Se usa metodo selenium para pruebas
        }
    }

    private void selectRoleTypeMassive(UsersRolesESP userSelected) {
        click(userRoleFieldMassive);
        click(dynamicWebElement(DYNAMIC_USER_ROLE,userSelected.getUserSelected()));
        if (userSelected != UsersRolesESP.INSURED && userSelected != UsersRolesESP.SUPPLIER_CORRALON
                && userSelected != UsersRolesESP.SUPPLIER_CRANE && userSelected != UsersRolesESP.SUPPLIER_MANAGEMENT) {
            click(userTypeFieldMassive);
            click(dynamicWebElement(DYNAMIC_USER_ROLE_TYPE_REGISTER,
                    userSelected.getTypeUser()));//Se usa metodo selenium para pruebas
        }
        WebElement loadLayoutRegistration = getElement(By.xpath(LOAD_DOCUMENT_MASSIVE_REGISTRATION));
        new CommonComponents()
                .uploadFileToWebApp(loadLayoutRegistration, userSelected.getFileMassiveLoad());
        new Buttons().clickRestartBtn();
        new Buttons().clickCloseBtn();
        new Buttons().clickAcceptButton();
    }

    public void selectProofOfAddress(String fileName1, String fileName2) {
        waitForElementVisibility(getDriver().findElement(By.xpath("//i[@aria-label='ícono: paper-clip']")));
        String filePath1 = AolWebPropertiesReader.getAttachmentsFolder(fileName1);
        String filePath2 = AolWebPropertiesReader.getAttachmentsFolder(fileName2);
        String files2Send = filePath1.substring(1) + "\n" + filePath2.substring(1);
        loadProofAddress.sendKeys(files2Send);

        new Buttons().clickSearchBtn();
        waitForElementVisibility(getDriver().findElement(By.xpath("//div[@role='document']")));
        new Buttons().clickAcceptButton();
    }

    private void userConsult(UsersRolesESP userRole) {
        jsClick(userSearch);
        click(dynamicWebElement(DYNAMIC_USER_ROLE,userRole.getUserSelected()));
    }

    public WebElement dynamicWebElement(String xpath,String params) {
        String newXpath = xpath.replace("?",params);
        return getBrowser().getDriver().findElement(By.xpath(newXpath));
    }

    public void uploadFiles(WebElement attachFile, List<String> list) {
        waitForElementVisibility(getDriver().findElement(By.xpath("//i[@aria-label='ícono: paper-clip']")));
        StringBuilder files2Send = new StringBuilder();
        String attachmentsPath = AolWebPropertiesReader.getAttachmentsPath();
        for (int i = 0; i < list.size(); i++) {
            files2Send.append(attachmentsPath.substring(1) + list.get(i) + "\n");
            if ((i + 1) == list.size()) {
                files2Send.append(attachmentsPath.substring(1) + list.get(i));
                break;
            }
        }
        attachFile.sendKeys(files2Send);
    }



    ///////
    public boolean usersDropDownValidation(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();

        for (UsersRolesESP user2: UsersRolesESP.values()) {
            menuPage.clickUsers();
            userMenu.clickUsersConsult(user2,"test");
            log.info("{} Consult", CLASS_NAME);
            log().image(user2.getTypeUser(), takeScreenshot());
        }

        for (UsersRolesESP user2: UsersRolesESP.values()) {
            menuPage.clickUsers();
            userMenu.clickIndividualRegister(user2);
            log.info("{} Individual register", CLASS_NAME);
            log().image(user2.getTypeUser(), takeScreenshot());
        }
        for (UsersRolesESP user2: UsersRolesESP.values()) {
            menuPage.clickUsers();
            userMenu.clickMassiveRegister(user2);
            log.info("{} massive register", CLASS_NAME);
            log().image(user2.getTypeUser(), takeScreenshot());
        }

        return true;
    }



    public boolean massiveMoraBuyer(AolWebUser user) {
        MenuPage menuPage = new MenuPage();
        MassiveRegistrationUsers userMenu = menuPage.clickUsers();
        userMenu.clickMassiveRegister(UsersRolesESP.BUYER_MORAL);
        userMenu.selectProofOfAddress("TETE980511P10.zip", "GEN990714S11.zip");
        log.info("{} massive register-buyer", CLASS_NAME);
        return true;
    }
}