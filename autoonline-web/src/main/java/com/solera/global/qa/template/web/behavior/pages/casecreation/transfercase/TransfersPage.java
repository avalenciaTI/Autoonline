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
import com.solera.global.qa.template.web.behavior.pages.payments.Insurers;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

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
    public static final String DRIVER_FIELD = "destinationForm_driver";

    private static final String NOTIFICATION_MESSAGE = "//div[@class='ant-notification-notice-message']";

    public static final String LOAD_IMAGES = "//input[@type='file' and @accept='image/png,image/jpeg']/parent::button";
    //public static final String TRANSFER_STATUS_SELECTOR = "//input[@id='destinationForm_transferStatus']";





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
        new Buttons().clickAcceptButton();
        log().image("Transfer is ready to be updated", takeScreenshot());
    }

    public void uploadImages() {
        waitForElementToBeClickable(getElement(By.xpath(CAMERA_BUTTON)), Timeouts.LOAD_BUTTON);
        click(getElement(By.xpath(CAMERA_BUTTON)));
        log().image("Clicked camera button", takeScreenshot());

        waitForElementToBeClickable(getElement(By.xpath(UPLOAD_VEHICLE_RECOLLECTION)), Timeouts.LOAD_BUTTON);
        click(getElement(By.xpath(UPLOAD_VEHICLE_RECOLLECTION)));
        log().image("Clicked upload vehicle recollection tab", takeScreenshot());

        Photos photos = new Photos();
        log().image("Before attaching images", takeScreenshot());
        photos.attachImages(false);
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
        sendKeys(getElement(By.id(DRIVER_FIELD)), "QA DRIVER");
        buttons.clickAcceptButton();
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


}
