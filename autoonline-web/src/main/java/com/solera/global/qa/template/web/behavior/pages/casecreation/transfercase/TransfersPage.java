package com.solera.global.qa.template.web.behavior.pages.casecreation.transfercase;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.casecreation.Photos;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.SearchType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.TransferSearch;
import com.solera.global.qa.template.web.behavior.pages.menupage.CraneMenuPage;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import com.solera.global.qa.template.web.behavior.pages.payments.Insurers;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@Slf4j
public class TransfersPage extends BrowserPage {

    private static final String UPDATE_BUTTON = "//span[contains(text(), 'Actualizar')]"
            + "/ancestor::button | //button[@data-testid='update']";

    private static final String INIT_TRANSFER_BUTTON = "//span[contains(text(), 'Iniciar traslado')]/ancestor::button";
    private static final String ACTIVE_DEST_TAB = "//div[@class='ant-steps-item-title' and contains(text(), 'estino')]"
            + "/ancestor::div[@class='ant-steps-item ant-steps-item-process ant-steps-item-active']";
    private static final String DEST_TAB = "//div[@class='ant-steps-item-title' and contains(text(), 'Destino') "
            + "or contains(text(), 'Información de destino')]";
    private static final String DEST_INFO_TAB = "//div[@class='ant-steps-item-title' and "
            + "contains(text(), 'Información de destino')]";
    private static final String COMMENT = "destinationForm_comments";
    private static final String CAMERA_BUTTON = "//i[@class='anticon anticon-camera']/parent::button";
    private static final String UPLOAD_VEHICLE_RECOLLECTION = "//div[@role='tab' and "
            + "contains(text(), 'Fotografías de recolección de vehículo')]";

    public static final String TRANSFER_STATUS_SELECTOR = "destinationForm_transferStatus";
    public static final String DRIVER_FIELD = "//input[contains(@id,'driver')]";

    private static final String NOTIFICATION_MESSAGE = "//div[@class='ant-notification-notice-message']";

    public static final String LOAD_IMAGES = "//input[@type='file' and @accept='image/png,image/jpeg']/parent::button";
    //public static final String TRANSFER_STATUS_SELECTOR = "//input[@id='destinationForm_transferStatus']";

    public static final String SEARCH_DYNAMIC = "//td[text()='?']";

    public static final String VALIDATE_CASE_NUMBER= "//tr[contains(@class, 'ant-table-row')]/td[3][normalize-space()]";


    public TransfersPage() {
        super();
    }


    public void updateTransfer() {
        waitForElementToBeClickable(getElement(By.xpath(UPDATE_BUTTON)), Timeouts.LOAD_BUTTON);
        log.info("Clicking update button for transfer");
        click(getElement(By.xpath(UPDATE_BUTTON)));

        goToDestinationInfoTab();
        setComment("TEST COMMENT");
        setTransferState("Recolectado");
        confirmUpdate();
        log().image("Transfer updated successfully", takeScreenshot());
        click(getElement(By.xpath(UPDATE_BUTTON)));
        waitForElementVisibility(getElement(By.xpath(DEST_TAB)), Timeouts.LOAD_BUTTON);
        log().image("Clicked update button for transfer", takeScreenshot());
    }

    public void goToDestinationInfoTab() {
        waitForElementVisibility(getElement(By.xpath(DEST_INFO_TAB)), Timeouts.LOAD_PAGE);
        log().image("Before click destino tab", takeScreenshot());
        click(getElement(By.xpath(DEST_INFO_TAB)));
        log().image("Clicked destino tab", takeScreenshot());
        waitForElementVisibility(getElement(By.xpath(ACTIVE_DEST_TAB)), Timeouts.LOAD_PAGE);
        log().image("Switched to destination info tab", takeScreenshot());
    }

    public void setComment(String comment) {
        getElement(By.id(COMMENT)).clear();
        getElement(By.id(COMMENT)).sendKeys(comment);
        log().image("Set comment for transfer: " + comment, takeScreenshot());
    }

    public void setTransferState(String status) {
        //ant-select-selection__rendered  div/id
        new CommonComponents().selectFromDropdownText(getElement(By.id(TRANSFER_STATUS_SELECTOR)), status);
        log().image("Transfer status selected: " + status, takeScreenshot());
    }

    public void confirmUpdate() {
        // page title Actualizar traslado
        new Buttons().jsClickAcceptButton();
        log().image("Transfer is ready to be updated", takeScreenshot());
    }

    public void uploadImages() {
        openVehicleRecollectionPhotosTab();

        Photos photos = new Photos();
        log().image("Before attaching images", takeScreenshot());
        photos.attachImages(false);
    }

    public void openVehicleRecollectionPhotosTab() {
        waitForElementToBeClickable(getElement(By.xpath(CAMERA_BUTTON)), Timeouts.LOAD_BUTTON);
        click(getElement(By.xpath(CAMERA_BUTTON)));
        log().image("Clicked camera button", takeScreenshot());

        waitForElementToBeClickable(getElement(By.xpath(UPLOAD_VEHICLE_RECOLLECTION)), Timeouts.LOAD_BUTTON);
        click(getElement(By.xpath(UPLOAD_VEHICLE_RECOLLECTION)));
        log().image("Clicked upload vehicle recollection tab", takeScreenshot());
    }

    public boolean isAttachInputAvailableAndEnabled() {
        List<WebElement> attachInputs = getElements(By.xpath(LOAD_IMAGES));
        if (attachInputs.isEmpty()) {
            log.info("Attach input is not available, upload is blocked");
            return false;
        }
        boolean enabled = attachInputs.get(0).isEnabled();
        log.info("Attach input enabled status: {}", enabled);
        return enabled;
    }

    public String getUploadRestrictionNotificationMessage() {
        try {
            waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.WAIT_FOR_NOTIFICATION);
            String notification = getText(getElement(By.xpath(NOTIFICATION_MESSAGE)));
            log.info("Upload restriction notification: {}", notification);
            return notification;
        } catch (Exception ex) {
            log.info("No upload restriction notification found: {}", ex.getMessage());
            return "";
        }
    }

    public void openTransferUpdateAndImageInformation() {
        waitForElementToBeClickable(getElement(By.xpath(UPDATE_BUTTON)), Timeouts.LOAD_BUTTON);
        click(getElement(By.xpath(UPDATE_BUTTON)));
        log().image("Clicked update button for transfer", takeScreenshot());
        openVehicleRecollectionPhotosTab();
    }

    public boolean validateUploadBlockedAfterTimeLimit(String expectedMessage) {
        openTransferUpdateAndImageInformation();
        boolean attachEnabled = isAttachInputAvailableAndEnabled();
        String notification = getUploadRestrictionNotificationMessage();
        boolean messageMatches = expectedMessage != null
                && !expectedMessage.isEmpty()
                && notification != null
                && notification.contains(expectedMessage);
        return !attachEnabled || messageMatches;
    }

    public boolean verifyLoadedImages() {
        log.info("Verifying loaded images in transfer");
        int expectedImages = 15; // Expected number of images to be loaded
        int imageCount = new CommonComponents().countImagesOnGeneralView();
        return imageCount == expectedImages;
    }

    public void openTransfersMenu() {
        CraneMenuPage craneMenu = new CraneMenuPage();
        craneMenu.openCraneMenu();
        craneMenu.openSearch();
    }

    public String searchTransferByState(String state) {
        TransferSearch transfer = new TransferSearch();
        transfer.swapAdvancedSearch(SearchType.ADVANCED_SEARCH);
        transfer.selectTransferStatus(state);
        transfer.selectInsurer(Insurers.QA_TEST_AUTOMATION);
        transfer.search();
        return transfer.openFirstTransfer("QA TESTS AUTOMATION");
    }

    public void initializeTransfer() {
        waitForElementToBeClickable(getElement(By.xpath(INIT_TRANSFER_BUTTON)), Timeouts.LOAD_BUTTON);
        log.info("Clicking initialize transfer");
        click(getElement(By.xpath(INIT_TRANSFER_BUTTON)));
        log().image("Clicked update button for transfer", takeScreenshot());
        Buttons buttons = new Buttons();
        log.info("Clicking continue button Vehicle Information tab");
        buttons.clickContinueBtn();
        log.info("Clicking continue button Location Information tab");
        buttons.clickContinueBtn();

        //todo add wait for an element in destination info tab
        setTransferState("En tránsito");
        sendKeys(getElement(By.xpath(DRIVER_FIELD)), "QA DRIVER");
        buttons.jsClickAcceptButton();
        waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.WAIT_FOR_NOTIFICATION);
        log().image("Clicked accept button to initialize transfer and notificadion displayed", takeScreenshot());
        log.info("Transfer initialized successfully");
        waitForElementInvisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_FADED);
        log().image("Clicked accept button to initialize transfer and notificadion faded", takeScreenshot());
        waitForPageToLoad();
    }

    public boolean validateTransferInit(String vin) {
        openTransfersMenu();
        log().image("Opened transfers menu", takeScreenshot());
        TransferSearch transfer = new TransferSearch();
        transfer.swapAdvancedSearch(SearchType.ADVANCED_SEARCH);
        transfer.selectTransferStatus("En tránsito");
        transfer.selectInsurer(Insurers.QA_TEST_AUTOMATION);
        transfer.setVinField(vin);
        transfer.search();
        String openedVin = transfer.openFirstTransfer("QA TESTS AUTOMATION");
        log().image("Opened transfer with VIN: " + openedVin, takeScreenshot());
        return vin.equals(openedVin);


    }



    public boolean generalSearchTranfer(String vin4) {
        
        MenuPage menuPage = new MenuPage();//menu managment.
        TransferSearch casesMenu = new TransferSearch();
        log.info("searching case");
        menuPage.clickTransfers();
        sleep(5000);
        casesMenu.clickSearchTranfer(vin4);
        log.info("Searching case in results");
        log().image("Results", takeScreenshot());
        return getElement(By.xpath(VALIDATE_CASE_NUMBER)).isDisplayed();
    }


}
