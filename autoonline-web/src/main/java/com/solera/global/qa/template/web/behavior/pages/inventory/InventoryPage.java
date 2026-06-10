package com.solera.global.qa.template.web.behavior.pages.inventory;


import javax.xml.soap.AttachmentPart;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.casecreation.Documents;
import com.solera.global.qa.template.web.behavior.pages.casecreation.Photos;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.TransferSearch;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import com.solera.global.qa.template.web.behavior.pages.payments.Insurers;
import com.solera.global.qa.template.web.behavior.pages.payments.Supplier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryPage extends BrowserPage {

    private static final String GENERAL_SEARCH_SECTION = "//a[@href='/inventory/search'][contains(.,'Consultar')]";
    private static final String RESULTS_INVENTORY_ENTRIES = "//*[contains(@class, 'raphael-group') and (contains(@class, 'legend') or contains(@class, 'legendGroup'))]";
    private static final String RESULTS_INVENTORY_CHECKOUT = "//*[contains(@class, 'Label-group') and (contains(@class, 'axis') or contains(@class, 'group'))]";
    //private static final String RESULTS_TABLE = "//div[contains(@class,'PieChartContainer')]";
    //private static final String CLICK_INVENTORY_CHECKOUT = ".raphael-group-58-labels > text:nth-child(3)";
    private static final String CLICK_INVENTORY_CHECKOUT = "text:nth-child(3)";
    private static final String CLICK_INVENTORY_OUTPUT = "text:nth-child(4)";
    private static final String CLICK_INVENTORY_OUTPUT_REMOVAL = ".BarChartContainer .ant-typography";
    private static final String CHECK_INVENTORY_FIELD = "//input[contains(@id,'check-inventory-form_wildcard')]";
    public static final String SEARCH_DYNAMIC = "//a[text()='?']";
    private static final String REGISTER_ENTRY_BUTTON = "//span[contains(text(), 'Registrar entrada')]/ancestor::button";
    private static final String REGISTER_OUT_BUTTON = "//span[contains(text(), 'Registrar salida')]/ancestor::button";
    public static final String STORAGE_NUMBER_FIELD = "//input[contains(@id,'storageNumber')]";
     public static final String LOCATION_CORRALON= "//input[contains(@id,'location')]";
    private static final String CAMERA_BUTTON = "//i[@class='anticon anticon-camera']/parent::button";
    public static final String TAB_NEXT = "//*[contains(@class,'ant-tabs-tab-next') and contains(@class,'ant-tabs-tab-arrow-show')]";
    private static final String UPLOAD_INVENTORY_ENTRY = "//div[@role='tab' and "
    + "contains(text(), 'Fotografías de entrada a inventario')]";
    private static final String CLOSE_MODAL_PDF = "//span[@class='ant-modal-close-x']";
     private static final String CLOSE_MODAL_IMAGES = "//button[@class='ant-modal-close' and @aria-label='Close']";
    private static final String ATTACHMENT_MARKET_DOCUMENT = "//button[contains(@jest-id,'fileUploader')]";
    private static final String NOTIFICATION_MESSAGE = "//div[@class='ant-notification-notice-message']";
    private static final String NOTIFICATION_MESSAGE_LIMIT = "//div[@class='ant-notification-notice-message'][contains(.,'El número mínimo de fotografías permitidas para el registro de entrada a inventario son 6')]";
    public static final String OUTPUT_REASON_ID = "//div[@class='ant-select-selection__placeholder' and contains(text(), 'Seleccionar')]";
    public static final String COMMENTS_INVENTORY = "//textarea[contains(@id,'comments')]";
    private static final String UPLOAD_INVENTORY_OUTPUT = "//div[@role='tab' and "
    + "contains(text(), 'Fotografías de salida de inventario')]";


    @FindBy(xpath = GENERAL_SEARCH_SECTION)
    WebElement generalSearchSection;
    @FindBy(css = CLICK_INVENTORY_CHECKOUT)
    WebElement clickInventoryCheckout;
    @FindBy(css = CLICK_INVENTORY_OUTPUT)
    WebElement clickInventoryOutput;
    @FindBy(css = CLICK_INVENTORY_OUTPUT_REMOVAL)
    WebElement clickInventoryOutputRemoval;
    @FindBy(xpath = CHECK_INVENTORY_FIELD)
    WebElement checkInventoryField;
    @FindBy(xpath = REGISTER_ENTRY_BUTTON)
    WebElement registerEntryButton;
    @FindBy(xpath = REGISTER_OUT_BUTTON)
    WebElement registerOutButton;
    @FindBy(xpath = STORAGE_NUMBER_FIELD)
    WebElement storageNumberField;
    @FindBy(xpath = LOCATION_CORRALON)
    WebElement locationCorralon;
    @FindBy(xpath = COMMENTS_INVENTORY)
    WebElement commentsInventory;
    @FindBy(xpath = CLOSE_MODAL_PDF)
    WebElement closeModalPdf;
    @FindBy(xpath = CLOSE_MODAL_IMAGES)
    WebElement closeModalImages;
    @FindBy(xpath = ATTACHMENT_MARKET_DOCUMENT)
    WebElement attachmentMarketDocument;
    @FindBy(xpath = OUTPUT_REASON_ID)
    WebElement outputReasonId;
  



    


    public InventoryPage() {
        super();
    }

    
    public boolean consultByCriteria() {
        MenuPage menuPage = new MenuPage();
        TransferSearch transfer = new TransferSearch();
        menuPage.clickInventory();

        // 1. Esperar a que la sección de búsqueda esté clickeable
        waitForElementToBeClickable(generalSearchSection, Timeouts.LOAD_ELEMENT);
        click(generalSearchSection);

        // 2. Esperar a que la página de búsqueda de inventario cargue
        sleep(Timeouts.SHORT_TIME);
        log().image("Inventory search page loaded", takeScreenshot());

        
        try {
            // 3. Seleccionar el checkbox de la aseguradora
            transfer.selectInsurer(Insurers.ALL);
            transfer.selectSupplier(Supplier.ALL);
            // 4. Esperar a que los resultados se carguen automáticamente
            waitForElementPresence(By.xpath(RESULTS_INVENTORY_ENTRIES), Timeouts.LOAD_RESULTS);
            waitForElementPresence(By.xpath(RESULTS_INVENTORY_CHECKOUT), Timeouts.LOAD_RESULTS);
            log().image("Inventory results loaded", takeScreenshot());
            log.info("Inventory consultation completed successfully");
            return true;
        } catch (Exception e) {
            log.error("Error loading inventory results: {}", e.getMessage());
            log().image("Error loading inventory results", takeScreenshot());
            return false;
        }
    }


    /**
     * Método genérico reutilizable para buscar un caso por VIN en inventario y abrirlo.
     * El bloque de búsqueda es idéntico tanto para entrada como para salida de inventario.
     *
     * @param VinCase el VIN del caso a buscar
     */
    private void searchAndOpenCaseByVin(String VinCase) {
        waitForElementToBeClickable(checkInventoryField, Timeouts.LOAD_ELEMENT);
        sendKeys(checkInventoryField, VinCase);
    
        String vinXpath = SEARCH_DYNAMIC.replace("?", VinCase);
        // 1. Espera a que el enlace con el VIN esté presente en el DOM
        waitForElementPresence(By.xpath(vinXpath), Timeouts.LOAD_HEAVY_RESULTS);
    
        // 2. Localiza el elemento nuevamente (ahora ya existe)
        WebElement vinLink = getDriver().findElement(By.xpath(vinXpath));
        // Haz clic en el enlace
        click(vinLink);
        log().image("Inventory - Case found and opened for VIN: " + VinCase, takeScreenshot());
    }


    public boolean verifyLoadedImagesCase(String VinCase, String newVin) {
        click(clickInventoryCheckout);
        searchAndOpenCaseByVin(VinCase);
        waitForElementToBeClickable(registerEntryButton, Timeouts.LOAD_ELEMENT);
        click(registerEntryButton);
        sendKeys(storageNumberField, newVin);
        sendKeys(locationCorralon, "CIUDAD DE MEXICO");
        sendKeys(commentsInventory, "Comentario ENTRADA QA");
        new InventoryPage().uploadEntryImages();
        jsClick(closeModalImages);
        waitForElementInvisibility(getElement(By.xpath(NOTIFICATION_MESSAGE_LIMIT)), Timeouts.NOTIFICATION_FADED);
        log().image("Minimum number of photographs allowed for inventory", takeScreenshot());
        return true;
    }


 public boolean entryPhotoUpload(String VinCase, String newVin) {
        Buttons buttons = new Buttons();
        click(clickInventoryCheckout);
        searchAndOpenCaseByVin(VinCase);
        waitForElementToBeClickable(registerEntryButton, Timeouts.LOAD_ELEMENT);
        click(registerEntryButton);
        sendKeys(storageNumberField, newVin);
        sendKeys(locationCorralon, "CIUDAD DE MEXICO");
        sendKeys(commentsInventory, "Comentario ENTRADA QA");
        new InventoryPage().uploadEntryFile();
        jsClick(closeModalPdf);
        new InventoryPage().uploadEntryImages();
        waitForElementToBeClickable(closeModalImages, Timeouts.LOAD_ELEMENT);
        jsClick(closeModalImages);
        //waitForElementInvisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_FADED);
        log().image("Attachment market document", takeScreenshot());
        
        buttons.jsClickAcceptButton();
        // Esperar a que aparezca la notificación (el proceso puede tardar por los archivos adjuntos)
        sleep(50000);
        try {
            waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);
            log().image("Notification visible after accept", takeScreenshot());
        } catch (Exception e) {
            log.warn("Notification did not appear, continuing test: {}", e.getMessage());
        }
        //waitForElementInvisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_FADED);
        log().image("The status of the case is 'entregado'.", takeScreenshot());
        consultByCriteria();
        click(clickInventoryOutput);
        searchAndOpenCaseByVin(VinCase);

        return true;
    }









    public boolean outputPhotoUpload(String VinCase) {
        Buttons buttons = new Buttons();
        click(clickInventoryOutput);
        searchAndOpenCaseByVin(VinCase);
        waitForElementToBeClickable(registerOutButton, Timeouts.LOAD_ELEMENT);
        click(registerOutButton);
        new CommonComponents().selectFromGlobalAntDropdownOption(outputReasonId, "Traslado por devolución");
        sendKeys(commentsInventory, "Comentario SALIDA QA");
        //sendKeys(StorageNumberField, newVin);
        new InventoryPage().uploadOutImages();
        jsClick(closeModalImages);
        buttons.jsClickAcceptButton();
        waitForElementInvisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_FADED);
        log().image("The status of the case is Salida y Images loaded", takeScreenshot());
        return true;
    }






    public void uploadEntryImages() {
        openInventoryEntryPhotosTab();
        Photos photos = new Photos();
        log().image("Before attaching images", takeScreenshot());
        photos.attachImagesTransfer(false);
    }

    /**
     * Helper method that clicks TAB_NEXT repeatedly until the target element becomes clickable,
     * or until the maximum number of attempts is reached.
     * This avoids hardcoding the number of clicks, making the navigation scalable for any scenario.
     *
     * @param targetXpath the xpath of the target tab/element to find
     * @param maxAttempts maximum number of TAB_NEXT clicks before giving up
     */
    private void clickTabNextUntilTargetFound(String targetXpath, int maxAttempts) {
        int attempts = 0;
        By targetBy = By.xpath(targetXpath);
        while (attempts < maxAttempts) {
            if (isDisplayed(getElement(targetBy))) {
                waitForElementToBeClickable(getElement(targetBy), Timeouts.LOAD_BUTTON);
                click(getElement(targetBy));
                return;
            }
            click(getElement(By.xpath(TAB_NEXT)));
            attempts++;
        }
        // If we exit the loop, the element was not found
        throw new RuntimeException(
            "Target element not found after " + maxAttempts + " TAB_NEXT clicks: " + targetXpath);
    }

    public void openInventoryEntryPhotosTab() {
        waitForElementToBeClickable(getElement(By.xpath(CAMERA_BUTTON)), Timeouts.LOAD_BUTTON);
        click(getElement(By.xpath(CAMERA_BUTTON)));
        log().image("Clicked camera button", takeScreenshot());
        clickTabNextUntilTargetFound(UPLOAD_INVENTORY_ENTRY, 10);
        log().image("Clicked upload inventory entry tab", takeScreenshot());
    }





    public void uploadEntryFile() {
        //click(attachmentMarketDocument);
        Documents documents = new Documents();
        log().image("Before attaching file", takeScreenshot());
        documents.attachFilesFromFolder();
        // Esperar a que el modal de documentos se cierre completamente
        // para que no interfiera con el modal de fotos
        waitForElementInvisibility(By.xpath("//div[@class='ant-modal-content']"), Timeouts.LOAD_ELEMENT);
        sleep(1000);
        log().image("After PDF modal closed", takeScreenshot());
    }




    public void uploadOutImages() {
        openInventoryOutPhotosTab();
        Photos photos = new Photos();
        log().image("Before attaching images", takeScreenshot());
        
        photos.attachImages(false);
    }

    public void openInventoryOutPhotosTab() {
        waitForElementToBeClickable(getElement(By.xpath(CAMERA_BUTTON)), Timeouts.LOAD_BUTTON);
        click(getElement(By.xpath(CAMERA_BUTTON)));
        log().image("Clicked camera button", takeScreenshot());
        clickTabNextUntilTargetFound(UPLOAD_INVENTORY_OUTPUT, 10);
        log().image("Clicked upload inventory output tab", takeScreenshot());
    }


     public boolean valideInventoryRemovalUnit(String VinCase) {
        consultByCriteria();
        click(clickInventoryOutputRemoval);
        waitForElementToBeClickable(checkInventoryField, Timeouts.LOAD_ELEMENT);
        sendKeys(checkInventoryField, VinCase);
        String vinXpath = SEARCH_DYNAMIC.replace("?", VinCase);
        // 1. Espera a que el enlace con el VIN esté presente en el DOM
        waitForElementPresence(By.xpath(vinXpath), Timeouts.LOAD_HEAVY_RESULTS);
    
        // 2. Localiza el elemento nuevamente (ahora ya existe)
        WebElement vinLink = getDriver().findElement(By.xpath(vinXpath));
        // Haz clic en el enlace
        click(vinLink);
        log().image("Inventory - Case found and opened for VIN: " + VinCase, takeScreenshot());
        return true;
    }



    
}