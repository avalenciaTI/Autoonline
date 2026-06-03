package com.solera.global.qa.template.web.behavior.pages.menupage;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.casecreation.RegistrationMenu;
import com.solera.global.qa.template.web.behavior.pages.componentpages.submenu.AwardingsOptions;
import com.solera.global.qa.template.web.behavior.pages.componentpages.submenu.PublicationsOptions;
import com.solera.global.qa.template.web.behavior.pages.usercreation.MassiveRegistrationUsers;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class MenuPage extends BrowserPage {
    private static final String USERS_SECTION = "//i[@class='anticon anticon-user-add']";

    private static final String CASES_SECTION = "//i[@class='anticon anticon-car']";
    private static final String CASES_MENU =
            "//li[@class='ant-menu-submenu ant-menu-submenu-vertical ant-menu-submenu-open ant-menu-submenu-active']"
                    + "//li[2]";

    private static final String CATALOGS_SECTION = "//i[@class='anticon anticon-apartment']";
    private static final String TRANSFERS_SECTION = "//span[contains(text(), 'Traslados')]/ancestor::li";
    private static final String PUBLICATIONS_SECTION = "(//div[@class='ant-menu-submenu-title'])[8]";
    private static final String INVENTORY_SECTION = "(//div[@class='ant-menu-submenu-title'])[9]";
    private static final String AWARDINGS_SECTION = "//span[@title='Adjudicaciones']";
    private static final String PAYMENTS_SECTION = "//span[text()='Pagos']";
    private static final String PAYMENTS_SUBMENU = "//a[@href='/payments/search/initial']";
    private static final String REPORTS_SECTION = "(//div[@class='ant-menu-submenu-title'])[12]";
    private static final String LOGBOOK_SECTION = "(//div[@class='ant-menu-submenu-title'])[13]";

    private static final String EXTEND_MENU_ARROW = "//i[@class='anticon anticon-right']";
    private static final String ADJUDICATIONS_MENU_BUYER = "//span[contains(text(),'Adjudicaciones')]";
    private static final String CONSULT_ADJUDICATION = "//span[text()='Adjudicaciones']"
            + "/ancestor::li[contains(@class,'ant-menu-submenu-open')]//span[text()='Consultar']";
    private static final String OPEN_DRAWER = "//button[@data-testid='open-drawer']";
    private static final String VEHICLE_SUBMENU = "//a[@href='/awardings/buyers/search/vehicle']";
    private static final String VEHICLE_MASTER_SUBMENU =
            "//li[@class='ant-select-dropdown-menu-item' and text()='Vehículos']";
    private static final String LINK_XP = "//a[@href='%s']";
    private static final String OPEN_RIGHT_PANEL = "Opening right section";
    private static final String CASE_TYPE_SEL_MASTER =
            "//div[@class='ant-select-selection__placeholder' and contains(text(), 'Seleccionar')]";



    @FindBy(xpath = USERS_SECTION)
    WebElement usersSection;
    @FindBy(xpath = CASES_SECTION)
    WebElement casesSection;
    @FindBy(xpath = CATALOGS_SECTION)
    WebElement catalogsSection;
    @FindBy(xpath = TRANSFERS_SECTION)
    WebElement transfersSection;
    @FindBy(xpath = PUBLICATIONS_SECTION)
    WebElement publicationsSection;
    @FindBy(xpath = INVENTORY_SECTION)
    WebElement inventorySection;
    @FindBy(xpath = AWARDINGS_SECTION)
    WebElement awardingsSection;
    @FindBy(xpath = PAYMENTS_SECTION)
    WebElement paymentsSection;
    @FindBy(xpath = REPORTS_SECTION)
    WebElement reportsSection;
    @FindBy(xpath = LOGBOOK_SECTION)
    WebElement logbookSection;

    public MenuPage() {
        super();
    }

    public MassiveRegistrationUsers clickUsers() {
        click(usersSection);
        return new MassiveRegistrationUsers();
    }

    public RegistrationMenu clickCases() {
        click(casesSection);
        waitForElementVisibility(getElement(By.xpath(CASES_SECTION)), Timeouts.LOAD_ELEMENT);
        return new RegistrationMenu();
    }

    public RegistrationMenu clickCatalogs() {
        click(catalogsSection);
        waitForElementVisibility(getElement(By.xpath(CATALOGS_SECTION)), Timeouts.LOAD_ELEMENT);
        return new RegistrationMenu();
    }

    public void clickTransfers() {
        click(transfersSection);
    }

    public void clickPublications(PublicationsOptions subMenuSelection) {
        click(publicationsSection);
        String submenuXP = String.format(LINK_XP, subMenuSelection.getSubMenuSelection());
        WebElement search = publicationsSection.findElement(By.xpath(submenuXP));
        waitForElementToBeClickable(search, Timeouts.LOAD_ELEMENT);
        switch (subMenuSelection.getSubMenuSelection()) {
            case "/adverts/add":
                click(getDriver().findElement(By.xpath("//a[@href='/adverts/add']")));
                break;
            case "/adverts/search":
                click(getDriver().findElement(By.xpath("//a[@href='/adverts/search']")));
                break;
            default:
        }
    }

    public void clickInventory() {
        click(inventorySection);
    }

    public void clickAwardings(AwardingsOptions subMenuOption) {
        click(awardingsSection);
        String xp = String.format(LINK_XP, subMenuOption.getAwardingSubMenuSelection());
        WebElement search = publicationsSection.findElement(By.xpath(xp));
        waitForElementToBeClickable(search, Timeouts.LOAD_ELEMENT);
        click(search);
    }

    public void clickAwardings(AwardingsOptions subMenuOption, String role) {

        log().image("After click Awardings section", takeScreenshot());
        if (role.equalsIgnoreCase("physical_buyer")) {
            log.info("Clicking on Adjudications menu for buyer");

            log.info(OPEN_RIGHT_PANEL);
            click(getElement(By.xpath(EXTEND_MENU_ARROW)));
            log.info("Right arrow clicked");
            sleep(2000);
            log().image("After click right arrow", takeScreenshot());

            //Adjudications menu
            click(getElement(By.xpath(ADJUDICATIONS_MENU_BUYER)));
            log.info("Adjudications menu clicked");
            sleep(2000);
            log().image("After click Adjudications menu", takeScreenshot());

            click(getElement(By.xpath(CONSULT_ADJUDICATION)));
            sleep(100);

            click(getElement(By.xpath(VEHICLE_SUBMENU)));
            log().image("After click Consult Adjudications menu for buyer", takeScreenshot());

            click(getElement(By.xpath(OPEN_DRAWER)));
            log().image("After click Open Drawer", takeScreenshot());

            log().image("Click on Consulta Adjudications menu for buyer", takeScreenshot());
        } else {
            log.info("Clicking on Awardings section role {}", role);
            awardingsSection = getElement(By.xpath(AWARDINGS_SECTION));
            log().image("Before click Awardings section", takeScreenshot());
            click(awardingsSection);

            log.info("Clicking on Adjudications menu for master");
            String xp = String.format(LINK_XP, subMenuOption.getAwardingSubMenuSelection());
            WebElement search = publicationsSection.findElement(By.xpath(xp));
            waitForElementToBeClickable(search, Timeouts.LOAD_ELEMENT);
            log().image("Before click Adjudications menu for master", takeScreenshot());
            click(search);
            log().image("After click Adjudications menu for master", takeScreenshot());

            click(getElement(By.xpath(CASE_TYPE_SEL_MASTER)));
            click(getElement(By.xpath(VEHICLE_MASTER_SUBMENU)));
            log().image("After click Consult Adjudications menu for master", takeScreenshot());
        }
    }

    public void clickPayments() {
        log.info(OPEN_RIGHT_PANEL);
        click(getElement(By.xpath(EXTEND_MENU_ARROW)));
        click(getElement(By.xpath(PAYMENTS_SECTION)));
        click(getElement(By.xpath(PAYMENTS_SUBMENU)));
    }

    public void extendMenu() {
        log.info(OPEN_RIGHT_PANEL);
        click(getElement(By.xpath(EXTEND_MENU_ARROW)));
        log().image("After click right arrow", takeScreenshot());

    }

    public void clickReports() {
        click(reportsSection);
    }

    public void clickLogbook() {
        click(logbookSection);
    }

}