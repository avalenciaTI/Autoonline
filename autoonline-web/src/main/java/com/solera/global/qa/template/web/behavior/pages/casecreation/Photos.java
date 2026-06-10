package com.solera.global.qa.template.web.behavior.pages.casecreation;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.AolWebPropertiesReader;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.data.types.AolWebUser;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonComponents;
import com.solera.global.qa.template.web.behavior.pages.menupage.MenuPage;
import java.io.File;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@Slf4j

public class Photos extends BrowserPage {
    public static final String PHOTOS_BUTTON = "//i[@class='anticon anticon-camera']/parent::button";
    public static final String ATTACH_BUTTON = "//input[@accept='image/png,image/jpeg']";//- //input[@type='file'] - //span[contains(.,'Adjuntar')]
    public static final String PAPER_CLIP = "//i[@aria-label='ícono: paper-clip']";
    public static final String FAVORITE_BUTTONS
            = "//div[@class='ant-tabs-tabpane ant-tabs-tabpane-active']"
            + "//div//img/parent::div/following-sibling::ul//i[@class='anticon anticon-star']";
    public static final String ACTIVE_WINDOW = "//div[contains(@aria-hidden,'false')]";
    public static final String IMAGES_LOADED = ACTIVE_WINDOW + "//div[@class='simage-container']/img";
    public static final String IMAGES_SKELETON = ACTIVE_WINDOW + "//figure[contains(@class,'card-image-skeleton loading-skeleton')]";
    public static final String DYNAMIC_INDEX = "//ul[contains(@class,'pagination')]/li[@title=%s]";
    public static final String MARK_ALL_AS_FAVORITES = "(//input[contains(@class,'checkbox')])[1]";
    public static final String CLOSE_IMGS_BUTTON = "//button[@class='ant-modal-close' and @aria-label='Close']";

    @FindBy(xpath = PHOTOS_BUTTON)
    WebElement photosButton;
    @FindBy(xpath = ATTACH_BUTTON)
    WebElement loadImagesButton;
    @FindBy(xpath = PAPER_CLIP)
    WebElement paperClip;
    @FindBy(xpath = FAVORITE_BUTTONS)
    WebElement favoriteButtons;
    @FindBy(xpath = ACTIVE_WINDOW)
    WebElement activeWindow;
    @FindBy(xpath = MARK_ALL_AS_FAVORITES)
    WebElement markAllFavorites;

    ///
    private static final String CASE_TYPE_VAL = "vehículos";
    public static final String SEARCH_DYNAMIC = "//td[text()='?']";
    private static final String CLASS_NAME = "Photos";
    ///

    public Photos() {
        super();
    }

    private final Random rand = new Random();

    public void loadPhotos() {
        new Buttons().clickUpdateBtn();
        waitForElementVisibility(photosButton);
        click(photosButton);

    }


    public void attachImages(boolean openCamera) {
        if (openCamera) {
            loadPhotos();
            waitForElementVisibility(paperClip);
        }

        String imgsFolder = "images15/";
        String attachmentsFolder = AolWebPropertiesReader.getAttachmentsFolder(imgsFolder);
        log.info("ATTACHMENTS FOLDER: {}", attachmentsFolder);


        File folder = new File(attachmentsFolder);
        File[] files = folder.listFiles();
        int numFiles = files.length;
        log.info("ATTACH IMAGES, images in folder: {}", numFiles);
        final int filesPerIndex = 4;
        StringBuilder files2Send = new StringBuilder();
        String basePath = folder.getAbsolutePath() + File.separator;

        for (int i = 0; i < numFiles; i++) {
            String imgPath = basePath + files[i].getName();
            log.info("IMG PATH: {}", imgPath);

            File image = new File(imgPath);
            if (image.exists()) {
                log.info("IMAGE EXISTS: {}", imgPath);
            }
            files2Send.append(imgPath);
            if (i < numFiles - 1) {
                files2Send.append("\n");
            }
        }

        log.info("TotalFiles: {}", files2Send.toString());
        log.info("Sending files to hidden file input");
        getElement(By.xpath(ATTACH_BUTTON)).sendKeys(files2Send.toString());
        log.info("File uploaded");


        int totalIndex = (int)Math.ceil((double) numFiles / filesPerIndex);
        log.info("Page index: {}", totalIndex);
        int filesIntoIndex = numFiles % filesPerIndex == 0 ? 4 : numFiles % filesPerIndex;
        log.info("filesIntoIndex {}", filesIntoIndex);

        for (int i = 1; i < totalIndex + 1; i++) {
            log.info("starting iteration index {}", i);
            String indexString = String.format(DYNAMIC_INDEX, i);

            clickPaginationIndex(indexString, i);

            log.info("clicked index {}", i);
            waitForElementInvisibility(By.xpath(IMAGES_SKELETON), Timeouts.IMAGE_SKELETON_FADE);
            if (i == totalIndex) {
                sleep(4000);
            }
            log().image("loading images", takeScreenshot());
        }
    }

    public void clickPaginationIndex(String indexString, int pagIndex) {
        List<WebElement> indexes = getElements(By.xpath(indexString));
        for (WebElement index : indexes) {
            if (isVisible(index) && waitForElementToBeClickable(index, Timeouts.LOAD_PAGE)) {
                log.info("Element visibility for index {}", pagIndex);
                click(index);
                break;
            }
        }
    }

    public void attachImagesTransfer(boolean openCamera) {
        if (openCamera) {
            loadPhotos();
            waitForElementVisibility(paperClip);
        }

        String imgsFolder = "images5/";
        String attachmentsFolder = AolWebPropertiesReader.getAttachmentsFolder(imgsFolder);
        log.info("ATTACHMENTS FOLDER: {}", attachmentsFolder);


        File folder = new File(attachmentsFolder);
        File[] files = folder.listFiles();
        int numFiles = files.length;
        log.info("ATTACH IMAGES, images in folder: {}", numFiles);
        final int filesPerIndex = 4;
        StringBuilder files2Send = new StringBuilder();
        String basePath = folder.getAbsolutePath() + File.separator;

        for (int i = 0; i < numFiles; i++) {
            String imgPath = basePath + files[i].getName();
            log.info("IMG PATH: {}", imgPath);

            File image = new File(imgPath);
            if (image.exists()) {
                log.info("IMAGE EXISTS: {}", imgPath);
            }
            files2Send.append(imgPath);
            if (i < numFiles - 1) {
                files2Send.append("\n");
            }
        }

        log.info("TotalFiles: {}", files2Send.toString());
        log.info("Sending files to hidden file input");
        getElement(By.xpath(ATTACH_BUTTON)).sendKeys(files2Send.toString());
        log.info("File uploaded");


        int totalIndex = (int)Math.ceil((double) numFiles / filesPerIndex);
        log.info("Page index: {}", totalIndex);
        int filesIntoIndex = numFiles % filesPerIndex == 0 ? 4 : numFiles % filesPerIndex;
        log.info("filesIntoIndex {}", filesIntoIndex);

        for (int i = 1; i < totalIndex + 1; i++) {
            log.info("starting iteration index {}", i);
            String indexString = String.format(DYNAMIC_INDEX, i);

            clickPaginationIndex(indexString, i);

            log.info("clicked index {}", i);
            waitForElementInvisibility(By.xpath(IMAGES_SKELETON), Timeouts.IMAGE_SKELETON_FADE);
            if (i == totalIndex) {
                sleep(4000);
            }
            log().image("loading images", takeScreenshot());
        }
    }


    public void markAsFavorite() {
        List<WebElement> elements = getDriver()
                .findElements(By.xpath("//div[@class='ant-tabs-tabpane ant-tabs-tabpane-active']"
                        + "//div//img/parent::div/following-sibling::ul//i[@class='anticon anticon-star']"));

        int randVal = rand.nextInt(elements.size());
        log.info("Selecting random image as favorite: {} of {} elements", randVal, elements.size());
        elements.get(randVal).click();
        //this method was working fine, only missing a validation to know if the photo was marked as favorite.
        //this validation is supported with waitforelement to be clickable.

        //new method to mark as favorites all
        if (markAllFavorites.isSelected()) {
            log().image("its already marked", takeScreenshot());
        } else {
            log.info("Marking all images as favorite");
            log.info("Xpath: {}", MARK_ALL_AS_FAVORITES);

            click(getElement(By.xpath(MARK_ALL_AS_FAVORITES)));
            //now days the behavior of the checkbox is that after click on it, the checkbox is marked but inmediatly
            //is dismarked and then marked again to support this behavior is used the 3 following waits
            //to correct this behavior ask to dev team to implement some attribute that may help us to know when
            //-All the pictures are market
            //-individual photos marked as favorites (now its difficult to know)
            waitForElementToBeClickable(getElement(By.xpath(MARK_ALL_AS_FAVORITES
                    + "/parent::span[contains(@class, 'checked')]")), Timeouts.LOAD_PAGE);
            try {
                waitForElementToBeClickable(getElement(By.xpath(MARK_ALL_AS_FAVORITES
                        + "/parent::span[@class='ant-checkbox']")), Timeouts.LOAD_PAGE);
            } catch (Exception e) {
                log.info("Exception while waiting for checkbox to be clickable "
                        + "/parent::span[@class='ant-checkbox']: {}", e.getMessage());
            }
            waitForElementToBeClickable(getElement(By.xpath(MARK_ALL_AS_FAVORITES
                    + "/parent::span[contains(@class, 'checked')]")), Timeouts.LOAD_PAGE);
            log.info("marked");
            log().image("marking all as favorite", takeScreenshot());
        }
        click(getElement(By.xpath(CLOSE_IMGS_BUTTON)));
    }

    ///

    public boolean vehicleLoadImages(AolWebUser user, String vin) {
        MenuPage menuPage = new MenuPage();
        menuPage.clickCases();
        RegistrationMenu casesMenu = menuPage.clickCases();
        casesMenu.clickSearchCases(CASE_TYPE_VAL, vin);
        new CommonComponents().findHRefElement(new CommonComponents().dynamicWebElement(SEARCH_DYNAMIC, vin));
        Photos photos = new Photos();
        photos.attachImages(true);
        photos.markAsFavorite();

        return true;
    }


}
