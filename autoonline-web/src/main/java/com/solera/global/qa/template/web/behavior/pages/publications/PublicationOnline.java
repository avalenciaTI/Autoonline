package com.solera.global.qa.template.web.behavior.pages.publications;


import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.exceptions.AutomatedPublicationNotFound;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import com.solera.global.qa.template.web.behavior.pages.componentpages.Buttons;
import com.solera.global.qa.template.web.behavior.pages.loginpage.LogInPage;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;



@Slf4j
public class PublicationOnline extends BrowserPage {
    private static final String PUBLICATION_DIV_ID = "buyer-advert";
    private static final String PUBLICATION_SINISTER_XP = ".//span[@class='ant-descriptions-item-content']";
    private static final String NOTIFICATION_MESSAGE = "//div[@class='ant-notification-notice-message']";

    private static final String NEXT_PAGE_BTB = "//a[@class='ant-pagination-item-link']"
            + "/i[@aria-label='ícono: right' and @class='anticon anticon-right']"
            + "/ancestor::li[not (contains(@class,'ant-pagination-disabled ant-pagination-next'))]";

    private static final String PUBLICATION_PAGE_TITLE = "//h3[text()='Publicaciones en línea']";

    private static final String CURRENT_OFFERT_XP = 
            "//span[@class='ant-descriptions-item-content']/div[@class='flicker']";
    private static final String PUBLICATION_BID_FIELD_XP = ".//input[@id='bidder_bidValue']";

    private static final String PARENT_PUBLICATION_CARD = "//span[contains(text(), 'TEST VEH')]"
            + "/ancestor::div[@id='buyer-advert' and @class='ant-card cards-autoonline ant-card-bordered "
            + "ant-card-hoverable']/parent::div";

    private static final String OFFERT_BUTTON = ".//button[@jest-id='button']";
    public static final String SEARCH_DYNAMIC = "//button[text() ='?']";
    public static final String AWARD_TO = "//a[@href='/awardings/add/APR2410001950/0']";
    private static final String PAYMENT_REFERENCE_ID = "award-form_paymentRef";
    private static final String ADMINISTRATIVE_PAYMENT_REFERENCE_ID = "award-form_marketPaymentRef";
    private static final String COMMENT_ID = "award-form_comment";
    private static final String PUBLICATION_PARTIAL_NAME = "//button[contains(@title,'TESTAUTOMATION DIV')]";

    public static final String GENERIC_AUTOMATION_NAME = "TEST VEH 1JKTS";
    private static final String SINISTER_DESC_PUB = ".//span[contains(text(),'" + GENERIC_AUTOMATION_NAME + "')]";
    private static final String SINISTER_DESC_AWARD = ".//h3[contains(text(),'" + GENERIC_AUTOMATION_NAME + "')]";

    private static final String AUTOMATED_CASE_NUMBER = "//td[@class='ant-table-row-cell-break-word' and "
            + "contains(text(),''TEST VEH 1JKTS')]/preceding::a[contains(@href,'awardings/buyers/detail')]";

    private static final String VIEWS_SELECTOR = "//div[@class='ant-select-selection__rendered']"
            + "/div[@class='ant-select-selection-selected-value' and contains(text(), '10')]";
    private static final String RESULTS_VIEW = "//li[@class='ant-select-dropdown-menu-item' and text()='?']";
    private static final String REPORT_BUTTON = "//button[@jest-id='reportButton']";
    private static final String BUTTON_CHANGE_VIEW = "//button[@class='ant-btn ant-btn-round ant-btn-icon-only']";
    private static final String PENDING_ADJUDICATION = "//tr[@class='ant-table-row ant-table-row-level-0']"
            + "/td[contains(text(), 'Por adjudicar')]/preceding-sibling::td[contains(text(), '30,000,000')]"
            + "/preceding-sibling::td[contains(text(), 'TESTAUTOMATION DIV')]";
    private static final String CURRENT_OFFERT = ".//span[text()='Oferta actual']";
    private static final String CURRENT_BID = ".//div[contains(text(),'?')]";
    private static final String AUTOMATION_OFFERT = "30,000,000";


    private static final String SINISTER = "Sinister: {}";

    private static final String PUBLICATION_VIEW = "//span[contains(@class,'heading-extra')]";
    private static final String PUBLICATION_LIST_VIEW = PUBLICATION_VIEW + "/button[contains(@class,'primary')]";
    private static final String PUBLICATION_MOSAIC_VIEW = PUBLICATION_VIEW + "/button[contains(@class,'round')]";
    private static final String FIRST_PUBLICATION_IMAGES = "(//div[@id='buyer-advert']//div[@class='image-gallery'])";

    private static final String PUBLICATION_DETAIL_BUTTON = "//button[@data-testid='verDetalle1']";
    private static final String INFINITE_CAROUSEL = "//div[@data-testid='infinite-carousel']";
    private static final String SLIDE_IMAGE = "//li[contains(@class,'Slide')]";
    private static final String IMAGE_COMPLETE = "//div[@data-testid='infinite-carousel']//li[contains(@class,'Slide') "
            + "and contains(@style,'opacity: 1')][.//img[@data-testid='image-complete']]";
    private static final String DOT_CAROUSEL = "//ul[@class='InfiniteCarouselDots']/button[@data-index='0']";
    private static final String IMAGES_ON_LIST = "//ul[contains(@class,'list-transition')]/li";

    private final LogInPage login = new LogInPage();

    public PublicationOnline() {
        super();
    }

    public WebElement getOnlinePublication1() {
        log.info("Getting online publication");
        List<WebElement> publications = getDriver().findElements(By.id(PUBLICATION_DIV_ID));
        log.info("publications found: {}", publications.size());
        for (WebElement publication : publications) {
            List<WebElement> pubId = publication.findElements(By.xpath(PUBLICATION_SINISTER_XP));
            for (WebElement pub : pubId) {
                String sinisterId = getText(pub);
                log.info("TXT: {}", sinisterId);
                if (sinisterId.contains(GENERIC_AUTOMATION_NAME)) {
                    return publication;
                }
            }
        }
        return null;
    }

    /**
     * Get the first online publication created with massive load of cases.
     * @return WebElement
     */
    public WebElement getOnlinePublication() throws Exception {
        log.info("Getting online publication");
        int resultsPage = 0;
        while (resultsPage < 10) {
            resultsPage++;
            List<WebElement> publications = getDriver().findElements(By.id(PUBLICATION_DIV_ID));

            log.info("Searching in page: {}", resultsPage);
            WebElement publication = findPublication(publications);
            log.info("Publication found: {}", getText(publication));
            if (publication != null && getText(publication).contains(GENERIC_AUTOMATION_NAME)) {
                log.info("Compliant publication found");
                return publication;
            }

            log.info("Going to next page");
            WebElement nexPageResults = getElement(By.xpath(NEXT_PAGE_BTB));
            click(nexPageResults);

            log.info("Waiting for next page");
            waitForElementVisibility(getElement(By.xpath(PUBLICATION_PAGE_TITLE)), Timeouts.LOAD_HEAVY_PAGE);
            log.info("new page loads");
        }
        return null;
    }

    public String getPubWithPendingAward() {
        waitForElementPresence(By.xpath(REPORT_BUTTON), Timeouts.LOAD_BUTTON);
        try {
            boolean isTableView = waitForElementPresence(By.xpath(BUTTON_CHANGE_VIEW), Timeouts.SHORT_TIME);
            if (isTableView) {
                click(getElement(By.xpath(BUTTON_CHANGE_VIEW)));
                log.info("results table clicked");
            } else {
                log.info("results table already shown");
            }
        } catch (NoSuchElementException ex) {
            log.info("Change view button not found");
        }

        log().image("Table view", takeScreenshot());
        viewResults("100");
        return getText(getElement(By.xpath(PENDING_ADJUDICATION)));
    }

    public void viewResults(String numResults) {
        log.info("View results to", takeScreenshot());
        click(getElement(By.xpath(VIEWS_SELECTOR)));
        String totalResults = RESULTS_VIEW.replace("?", numResults);
        click(getElement(By.xpath(totalResults)));
        log().image("View results", takeScreenshot());
    }

    public WebElement getPulicationCard() {
        log.info("Getting publication card");
        List<WebElement> cards = getElements(By.xpath(PARENT_PUBLICATION_CARD));
        log.info("Publication Cards: {}", cards.size());

        for (int i = 1; i < 20; i++) {
            for (WebElement card : cards) {
                log.info("Reading card content: {}", getText(card));
                WebElement sinister = card.findElement(By.xpath(SINISTER_DESC_PUB));
                log.info(SINISTER, getText(sinister));

                if (isBidOnPublication(card)) {
                    continue;
                }

                if (getText(sinister).contains(GENERIC_AUTOMATION_NAME)) {
                    log.info("Automation Card with no bid found");
                    return card;
                }
            }

            log.info("Automation card not found on page: {}", i);
            WebElement nextPageButton = getElement(By.xpath(NEXT_PAGE_BTB));
            if (waitForElementToBeClickable(nextPageButton, Timeouts.LOAD_ELEMENT)) {
                click(nextPageButton);
                log.info("Waiting for next page to load");
                waitForElementVisibility(getElement(By.xpath(PUBLICATION_PAGE_TITLE)), Timeouts.LOAD_HEAVY_PAGE);
            } else {
                return null;
            }
        }
        return null;
    }

    public boolean isBidOnPublication(WebElement card) {
        try {
            String automatedBid = CURRENT_BID.replace("?", AUTOMATION_OFFERT);
            if (card.findElement(By.xpath(CURRENT_OFFERT)) != null
                    && card.findElement(By.xpath(automatedBid)) != null) {
                log.info("Automation card with bid found: {}", AUTOMATION_OFFERT);
                return true;
            }
        } catch (Exception ex) {
            log.info("Automation card with no bid found: {}", ex.getMessage());
        }
        return false;
    }

    private WebElement findPublication(List<WebElement> publications) throws Exception {
        log.info("Publications: {}", publications.size());
        for (int i = 0; i < publications.size() - 1; i++) {
            WebElement publication = publications.get(i);
            log.info("Publication content: {}  result count: {} elements in result: {}",
                    getText(publication), i, publications.size());
            List<WebElement> pubId = publication.findElements(By.xpath(PUBLICATION_SINISTER_XP));
            log.info("items in result {}", pubId.size());
            if (!pubId.isEmpty()) {
                String sinister = getText(pubId.get(2));
                log.info(SINISTER, sinister);
                if (sinister.contains(GENERIC_AUTOMATION_NAME)) {
                    return publication;
                }
            }
            log.info("Publication not found count: {}", i);
        }
        throw new AutomatedPublicationNotFound("Publication generated by automation process not found");
    }

    public void makeOffert(WebElement publication, String offert) {
        log().image("Making offert", takeScreenshot());
        WebElement element = publication.findElement(By.xpath(PUBLICATION_BID_FIELD_XP));
        log.info("Element field found {}", (element != null));
        if (element != null) {
            log.info("Moving to input field");
            moveToElement(element);
            log().image("in element", takeScreenshot());
            log.info("Element not null");
            click(element);
            sendKeys(element, offert);
            log.info("Bid amount: {}", getText(element));
        }

        click(publication.findElement(By.xpath(OFFERT_BUTTON)));
        new Buttons().clickAcceptButton();
        assertions().assertThat(waitForSuccesNofification()).as("Successful notification message: ").isTrue();
        log().image("Offert made", takeScreenshot());
    }

    public boolean waitForSuccesNofification() {
        boolean succesMessage = false;
        int count = 0;
        do {
            try {
                count++;
                log.info("Waiting for notification)");
                String notification = getDriver().findElements(By.xpath(NOTIFICATION_MESSAGE)).get(0).getText();
                if (notification.contains("La puja por oferta se realizó correctamente")) {
                    succesMessage = true;
                    log.info("Notification successfully found at try: {}", count);
                }
                sleep(250);

            } catch (NoSuchElementException ex) {
                log.info("Catched NoSuchElementException: {}", ex.getLocalizedMessage());
            } catch (StaleElementReferenceException ex) {
                log.info("Catched StaleElementReferenceException: {}", ex.getLocalizedMessage());
            }
        } while (!succesMessage && count < 10);
        return succesMessage;
    }

    public boolean verifyOffert(WebElement publication, String expectedValue) {
        log.info("Verifying offert");
        WebElement element = publication.findElement(By.xpath(CURRENT_OFFERT_XP));
        log.info("Element field found {}", (element != null));
        String readVal = getText(element);

        log.info("READ VAL: {}", readVal);
        String normalicedValue = normalizeCurrencyString(readVal);
        assertions().assertThat(normalicedValue.length()).as("IS 8 DIGITS MAX VALUE? ").isEqualTo(8);
        assertions().assertThat(hasOnlyNumericValues(normalicedValue)).as("IS ONLY NUMERIC VALUES? ").isTrue();
        log().image("Offert verified", takeScreenshot());
        return compareCurrencyStrings(readVal, expectedValue);
    }

    public boolean bidOnPublication() throws Exception {
        log.info("Bidding on publication");
        String nonNumericValue = "$ 300,0A0#0,0C0/000";
        String expectedValue = "30000000";
        WebElement publicationCard = getPulicationCard();
        makeOffert(publicationCard, nonNumericValue);
        return verifyOffert(publicationCard, expectedValue);
    }

    public boolean hasOnlyNumericValues(String val) {
        return val.matches("[0-9]+");
    }

    private boolean compareCurrencyStrings(String current, String expected) {
        BigDecimal currentVal = new BigDecimal(normalizeCurrencyString(current));
        BigDecimal expectedVal = new BigDecimal(normalizeCurrencyString(expected));
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);

        log.info("Offered: {} Expected {}", numberFormat.format(currentVal), numberFormat.format(expectedVal));
        return currentVal.compareTo(expectedVal) == 0;
    }

    private String normalizeCurrencyString(String val) {
        String normalizedVal = val.replaceAll("[$\\s,]", "");
        log.info("input: {} normalized: {}", val, normalizedVal);
        return normalizedVal;
    }

    public String findSinisterInAdjudications(List<WebElement> adjudications) {
        for (WebElement adjudication : adjudications) {
            log.info("Adjudication: {}", getText(adjudication));
            WebElement adjudicationId = adjudication.findElement(By.xpath(SINISTER_DESC_AWARD));
            log.info("Adjudication Sinister {}", getText(adjudicationId));
            if (getText(adjudicationId).contains(GENERIC_AUTOMATION_NAME)) {
                return getText(adjudicationId);
            }
        }
        return null;
    }

    private void change2ListView() {
        try {
            boolean isListView = waitForElementPresence(By.xpath(PUBLICATION_LIST_VIEW), Timeouts.SHORT_TIME);
            if (isListView) {
                log.info("List view already shown");
            } else {
                click(getElement(By.xpath(PUBLICATION_MOSAIC_VIEW)));
                log.info("Change from mosaic to list clicked");
            }
        } catch (NoSuchElementException ex) {
            log.info("list view button not found");
        }
    }

    private void change2MosaicView() {
        try {
            boolean isMosaicView = waitForElementPresence(By.xpath(PUBLICATION_MOSAIC_VIEW), Timeouts.SHORT_TIME);
            if (isMosaicView) {
                log.info("Mosaic view already shown");
            } else {
                click(getElement(By.xpath(PUBLICATION_LIST_VIEW)));
                log.info("Change from list to Mosaic clicked");
            }
        } catch (NoSuchElementException ex) {
            log.info("mosaic view button not found");
        }
    }

    public boolean imagesConsultGeneral() throws Exception {
        //precondition, at least one active publication
        log().image("images on fist view", takeScreenshot());
        int imagesGeneralConsultMosaic = countImagesOnGeneralView();
        int numberImagesOnList = countImagesOnListView(FIRST_PUBLICATION_IMAGES);
        log.info("Number of images on general consult: {}", numberImagesOnList);
        return imagesGeneralConsultMosaic == numberImagesOnList;
    }

    private int countImagesOnGeneralView() {
        change2MosaicView();
        int imagesGeneralConsultMosaic = 1;
        List<WebElement> listOfElements = getDriver().findElements(By
                .xpath(FIRST_PUBLICATION_IMAGES + "[1]//img"));
        int imagesNumberValidation = listOfElements.size();
        log.info("validation of number of images: {}", imagesNumberValidation);
        if (imagesNumberValidation > 1) {
            imagesGeneralConsultMosaic = getElements(By.xpath(FIRST_PUBLICATION_IMAGES
                    + "[1]//button[contains(@class,'image-gallery-bullet')]")).size();
            log.info("Number of images on case general with dots: {}", imagesGeneralConsultMosaic);
        } else {
            log.info("Number of images on case general consult without dots: {}", imagesGeneralConsultMosaic);
        }

        return imagesGeneralConsultMosaic;
    }

    private int countImagesOnListView(String openImageFull) {
        waitForElementPresence(getElement(By.xpath(openImageFull)));
        getElement(By.xpath(openImageFull)).click();
        log().image("Image opened", takeScreenshot());
        waitForElementPresence(getDriver().findElement(By.xpath(IMAGES_ON_LIST)));
        int numberImagesOnList = getElements(By.xpath(IMAGES_ON_LIST)).size();

        log.info("Number of images on list: {}",numberImagesOnList);
        return numberImagesOnList;

    }

    public boolean imagesConsultDetailed() throws Exception {
        log().image("", takeScreenshot());
        final int imagesGeneralConsultMosaic = countImagesOnGeneralView();

        WebElement detailView = getDriver().findElement(By.xpath(PUBLICATION_DETAIL_BUTTON));
        detailView.click();
        waitForElementPresence(getDriver().findElement(By.xpath(INFINITE_CAROUSEL)));
        log().image("Carousel now is present", takeScreenshot());

        int numberOfImageCarousel = getDriver().findElements(By.xpath(SLIDE_IMAGE)).size();
        log.info("Number of images on case carousel: {}", numberOfImageCarousel);
        if (numberOfImageCarousel > 4) {
            waitForElementToBeClickable(getElement(By.xpath(DOT_CAROUSEL)));
            getElement(By.xpath(DOT_CAROUSEL)).click();
        }
        int numberImagesOnList = countImagesOnListView(IMAGE_COMPLETE);

        log().image("Image opened 2", takeScreenshot());
        return imagesGeneralConsultMosaic == numberImagesOnList;
    }

}
