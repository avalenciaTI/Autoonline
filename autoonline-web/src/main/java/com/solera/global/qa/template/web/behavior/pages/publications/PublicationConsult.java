package com.solera.global.qa.template.web.behavior.pages.publications;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class PublicationConsult extends BrowserPage {

    private static final String PUBLICATIONS_MENU = "(//div[@class='ant-menu-submenu-title'])[4]";
    public static final String INSURANCE_COST_FIELD = "//input[contains(@id,'insuranceCost')]";
    private static final String CONSULT_SUBMENU = "//span[@title='Buscar compradores' and text()='Consultar']";
    private static final String ONLINE_CONSULT_OPTION = "//span[@title='Buscar compradores' and text()='En línea']";
    private static final String ARCHIVED_CONSULT_OPTION = "//span[@title='Buscar compradores' and text()='Archivadas']";
    private static final String ONLINE_VEHICLE_OPTION = "//a[@href='/adverts/list/online/vehicle' and text()='Vehiculos']";
    private static final String ONLINE_DIVERSE_OPTION = "//a[@href='/adverts/list/online/diverse' and text()='Diversos']";
    private static final String ARCHIVED_VEHICLE_OPTION = "//a[@href='/adverts/list/archived/vehicle' and text()='Vehiculos']";
    private static final String ARCHIVED_DIVERSE_OPTION = "//a[@href='/adverts/list/archived/diverse' and text()='Diversos']";
    private static final String OPEN_DRAWER = "//button[@data-testid='open-drawer']";

    private static final int WAIT_TO_LOAD = 3000;

    @FindBy(xpath = PUBLICATIONS_MENU)
    WebElement publicationsMenu;
    @FindBy(xpath = CONSULT_SUBMENU) WebElement consultSubmenu;
    @FindBy(xpath = ONLINE_CONSULT_OPTION) WebElement onlineConsultOption;
    @FindBy(xpath = ARCHIVED_CONSULT_OPTION) WebElement archivedConsultOption;
    @FindBy(xpath = ONLINE_VEHICLE_OPTION) WebElement onlineVehicleConsult;
    @FindBy(xpath = ONLINE_DIVERSE_OPTION) WebElement onlineDiverseConsult;
    @FindBy(xpath = ARCHIVED_DIVERSE_OPTION) WebElement archivedDiverseConsult;
    @FindBy(xpath = ARCHIVED_VEHICLE_OPTION) WebElement archivedVehicleConsult;

    public PublicationConsult() {
        super();
    }


    public void clickPublicationsMenu() {
        waitForElementPresence(getElement(By.xpath(PUBLICATIONS_MENU)), WAIT_TO_LOAD);
        publicationsMenu = getElement(By.xpath(PUBLICATIONS_MENU));
        click(publicationsMenu);
    }

    public void clickConsultSubmenu() {
        waitForElementPresence(getElement(By.xpath(CONSULT_SUBMENU)), WAIT_TO_LOAD);
        consultSubmenu = getElement(By.xpath(CONSULT_SUBMENU));
        click(consultSubmenu);
    }



    public void clickOnlineConsult() {
        waitForElementPresence(getElement(By.xpath(ONLINE_CONSULT_OPTION)), WAIT_TO_LOAD);
        onlineConsultOption = getElement(By.xpath(ONLINE_CONSULT_OPTION));
        click(onlineConsultOption);
        log().image("Clicked Online Consult Option", takeScreenshot());
    }

    public void clickArchivedOption() {
        waitForElementPresence(getElement(By.xpath(ARCHIVED_CONSULT_OPTION)), WAIT_TO_LOAD);
        archivedConsultOption = getElement(By.xpath(ARCHIVED_CONSULT_OPTION));
        click(archivedConsultOption);
        log().image("CLickedArchived Consult Option", takeScreenshot());
    }

    public void consultPublicationByStatus(String status) {
        if (status.equals("En línea")) {
            log.info("Consulting Online Publications");
            clickOnlineConsult();
        } else {
            log.info("Consulting Archived Publications");
            clickArchivedOption();
        }
    }

    public void clickVehicleByStatus(String status) {
        if (status.equals("En línea")) {
            log.info("Consulting Online Vehicle");
            clickOnlineVehicle();
        } else {
            log.info("Consulting Archived Vehicle");
            clickArchivedVehicle();
        }
    }

    public void clickOnlineVehicle() {
        waitForElementPresence(getElement(By.xpath(ONLINE_VEHICLE_OPTION)), WAIT_TO_LOAD);
        onlineVehicleConsult = getElement(By.xpath(ONLINE_VEHICLE_OPTION));
        log().image("Online Vehicle Option", takeScreenshot());
        click(onlineVehicleConsult);
    }

    public void clickArchivedVehicle() {
        waitForElementPresence(getElement(By.xpath(ARCHIVED_VEHICLE_OPTION)), WAIT_TO_LOAD);
        archivedVehicleConsult = getElement(By.xpath(ARCHIVED_VEHICLE_OPTION));
        log().image("Archived Vehicle Option", takeScreenshot());
        click(archivedVehicleConsult);
    }

    public void clickOnlineDiverse() {
        waitForElementPresence(getElement(By.xpath(ONLINE_DIVERSE_OPTION)), WAIT_TO_LOAD);
        onlineDiverseConsult = getElement(By.xpath(ONLINE_DIVERSE_OPTION));
        click(onlineDiverseConsult);
    }

    public void clickArchivedDiverse() {
        waitForElementPresence(getElement(By.xpath(ARCHIVED_DIVERSE_OPTION)), WAIT_TO_LOAD);
        archivedDiverseConsult = getElement(By.xpath(ARCHIVED_DIVERSE_OPTION));
        click(archivedDiverseConsult);
    }

    public void openDrawer() {
        waitForElementPresence(getElement(By.xpath(OPEN_DRAWER)), WAIT_TO_LOAD);
        WebElement openDrawer = getElement(By.xpath(OPEN_DRAWER));
        click(openDrawer);
    }

}
