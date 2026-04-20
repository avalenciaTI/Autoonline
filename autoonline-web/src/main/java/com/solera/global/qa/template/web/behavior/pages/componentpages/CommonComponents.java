package com.solera.global.qa.template.web.behavior.pages.componentpages;

import com.google.common.collect.Lists;
import com.solera.global.qa.taf.core.config.TafConfig.Web;
import com.solera.global.qa.taf.web.exceptions.ElementNotFoundException;
import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.FluentWait;


@Slf4j
public class CommonComponents extends BrowserPage {

    public CommonComponents() {
        super();
    }

    private static final String INPUT = "input";
    private static final String VALUE_STORED = " VALUE STORED: ";
    private static final String GET_BY_FROM_ELEMENT = "getByFromWebElement";
    private static final String FORMAT_ONE = "] -> ";
    private static final String FORMAT_TWO = " 'By.";

    private static final String SPINNER_LOADER = "//i[@aria-label='ícono: down']/ascendant::div[contains(@id,'?')]";

    public static final String SEARCH_DYNAMIC = "//td[text()='?']";

    private static final String NOTIFICATION_WINDOW = "//div[contains(@class,'bottomRight')]";
    private static final String NOTIFICATION_MESSAGE = "//div[@class='ant-notification-notice-message']";

    public static final String SPINNER = "//i[contains(@aria-label,'loading') and contains(@class,'loading')]";
    public static final String INPUT_HOUR_FIELD = "(//input[contains(@placeHolder,'HH:MM:SS')])[2]";
    public static final String COMBO_VISIBLE_VALIDATION = "Is combo visible? ";
    private static final String UPLOADED_IMAGE = "//img[@data-testid='image-complete']";
    private static final String GALERY_VIEW = "//div[@class='image-gallery-slide  image-gallery-center ']";
    private static final String UPLOAD_VIEW = "//h4[@class='ant-typography' and text()='Galería']";
    private static final String BULLETS_IN_GALERY = "//button[contains(@class,'image-gallery-bullet')]";




    /**
     * This method manage the dropdownlist adding text strait on it.
     **/
    public void selectFromDropdownTextBk(WebElement dropDownElement, String text) {
        click(dropDownElement);
        //Wait for the input to be clickable
        waitForElementToBeClickable(dropDownElement.findElement(By.xpath(".//input")));
        sendKeys(dropDownElement.findElement(By.xpath(".//input")), text);
        Actions action = new Actions(getDriver());
        action.moveToElement(dropDownElement).sendKeys(Keys.ENTER).perform();
    }

    public void selectFromDropdownText(WebElement dropDownElement, String text) {
        log.info("selectFromDropdownText starting search for: {}", text);

        jsClick(updateElement(dropDownElement));
        log.info("Click on drop down");
        //Wait for the input to be clickable
        WebElement divWithList;
        log.info("selectFromDropdownText: {}", text);
        List<WebElement> options = null;
        int counter = 0;
        while (options == null || options.isEmpty() || (getText(options.get(0))).contains("No hay datos")) {
            sleep(Timeouts.SHORT_TIME);
            counter++;
            if (counter == 30) {
                log.error("Timeout waiting for options to load in dropdown: {}", text);
                log().image("Timeout waiting for options to load", takeScreenshot());
                throw new NoSuchElementException("Options not found in dropdown after 30 attempts");
            }
            try {
                log.info("waiting to load options");
                divWithList = dropDownElement.findElement(By.xpath("following-sibling::div"));
                options = divWithList.findElements(By.tagName("li"));
                log.info("options in list: {}", options.size());
            } catch (StaleElementReferenceException ex) {
                log.info("Catched StaleElementException");
            } catch (NoSuchElementException ex) {
                log.info("Catched NoSuchElementException");
            }
        }
        log.info("Out of while");
        log.info("Found items in list: {}", options.size());
        for (WebElement option : options) {
            if (getText(option).equalsIgnoreCase(text)) {
                log.info("Text: " + text);
                click(option);
                return;
            }
        }
        log().image("Option not found in list: " + text, takeScreenshot());
    }

    private By getByFromLocalWebElement(WebElement element) {
        String byType;
        String byValue;

        if (element == null) {
            log.error("Null element {}", GET_BY_FROM_ELEMENT);
        } else {
            String data = element.toString();
            if (data == null) {
                log.error("Null element.toString() in {}", GET_BY_FROM_ELEMENT);
            } else {
                if (data.contains(FORMAT_ONE)) {
                    data = data.split(FORMAT_ONE)[1];
                } else if (data.contains(FORMAT_TWO)) {
                    data = data.split(FORMAT_TWO)[1];
                } else {
                    log.error("Invalid By extracted: '{}'", data);
                }

                int delimiterIndex = data.indexOf(": ");
                byType = data.substring(0, delimiterIndex);
                byValue = data.substring(delimiterIndex + ": ".length(), data.length() - 1);
                switch (byType) {
                    case "xpath":
                        return By.xpath(byValue);
                    case "css selector":
                        return By.cssSelector(byValue);
                    case "id":
                        return By.id(byValue);
                    case "tag name":
                        return By.tagName(byValue);
                    case "name":
                        return By.name(byValue);
                    case "link text":
                        return By.linkText(byValue);
                    case "class name":
                        return By.className(byValue);
                    default:
                        log.error("Undefined or not well transformed By from {} to {}", data, byType);
                }
            }
        }
        return (By) element;
    }

    public WebElement updateElement(WebElement element) {
        int retryCount = 0;
        boolean success = false;
        By by = null;
        WebElement searchedElement = null;

        while (!success && retryCount < 3) {
            try {
                by = getByFromLocalWebElement(element);
                searchedElement = getElement(by);
                success = true;
                log.info("Element updated successfully found");
            } catch (StaleElementReferenceException ex) {
                retryCount++;
                log.info("Managed StaleElementReferenceException Retry {} for element {}",
                        retryCount, element.toString());
                sleep(1000);
            } catch (NoSuchElementException ex) {
                retryCount++;
                log.info("Managed NoSuchElementException Retry {} for element: {}", retryCount, element.toString());
                sleep(1000);
            }
        }
        if (searchedElement == null) {
            throw new NoSuchElementException("Can not found element: " + element.toString() + " after 3 attempts");
        }
        return searchedElement;
    }

    public void uploadFileToWebApp(@NonNull WebElement element, String fileName) {
        boolean success = false;
        int retries = 0;
        if (element == null) {
            throw new NullPointerException("element is marked non-null but is null");
        } else {
            while (!success && retries < 3) {
                try {
                    retries++;
                    log.info("Full name of file " + fileName);
                    log.info("Uploading file try number: {}", retries);
                    element = new CommonComponents().updateElement(element);
                    ((RemoteWebElement) element).setFileDetector(new LocalFileDetector());
                    log.info("Before sendKeys");
                    element.sendKeys(fileName);
                    success = true;
                    log().image("Upload of file " + fileName + " finished", takeScreenshot());
                } catch (StaleElementReferenceException ex) {
                    log.info("Uploading file exception StaleElementReferenceException Error: {}",
                            ex.getLocalizedMessage());
                } catch (NoSuchElementException ex) {
                    log.info("Uploading file NoSuchElementException Error: " + ex.getLocalizedMessage());
                }
            }
        }
    }

    public void selectQuestionAnswer(int questionNumber, String answer) {
        //jsClick(getElement(By.xpath("//div[contains(@id,'question-" + questionNumber + "')]//input[@value='" + answer + "']")));
        jsClick(getElement(By.xpath("(//span[contains(text(),'" + answer + "') and not(contains(text(),'Ver más notificaciones'))])[" + questionNumber + "]")));
    }

    public void closeModal() {
        click(getElement(By.xpath("//span[@class='ant-modal-close-x']")));
    }

    public void cargarExpediente() {
        WebElement uploadInput = getElement(By.xpath(
                "//button[@type='button'][contains(.,'Cargar expediente')]"));
        click(uploadInput);
    }

    public void uploadExpedienteFile() {
        //String fullPath = getAttachmentsFolderFilePath() + "documentsVehicle/" + fileName;
        String fullPath = "C:\\testPDF.pdf";
        waitForElementPresence(By.xpath("//input[@type='file']"), Timeouts.LOAD_ELEMENT);
        WebElement uploadInput = getElement(By.xpath("//input[@type='file']"));
        //uploadFileToWebApp(uploadInput, fullPath);
        uploadInput.sendKeys(fullPath);
    }

    public int countImagesOnGeneralView() {
        changeToGaleryVIew();
        List<WebElement> listOfBullets = getDriver().findElements(By.xpath(BULLETS_IN_GALERY));
        int imagesNumberValidation = listOfBullets.size();
        log.info("Number of images on galery: {}", imagesNumberValidation);
        return imagesNumberValidation;
    }

    private void changeToGaleryVIew() {
        try {
            if (waitForElementPresence(By.xpath(UPLOAD_VIEW), Timeouts.LOAD_ELEMENT)) {
                log.info("Changing to galery view");
                List<WebElement> images = getElements(By.xpath(UPLOADED_IMAGE));
                log.info("Number of images found: {}", images.size());
                for (WebElement image : images) {
                    if (image.isDisplayed()) {
                        log.info("Clickable Image found: {}", image.getDomAttribute("src"));
                        click(image);
                        return;
                    }
                    log.info("Image found: {}", image.getDomAttribute("src"));
                }
            }
        } catch (Exception ex) {
            log.info("Gallery view already shown");
        }
    }


    public String getAttachmentsFolderFilePath() {
        ClassLoader var10000 = Thread.currentThread().getContextClassLoader();
        return ((URL) Objects.requireNonNull(var10000.getResource(""))).getPath() + "attachments/";
    }


    public void setCalendarDatesText(WebElement dateCalendar, WebElement inputElement, String date2Set) {
        System.out.println("Click boton Date Calendar"+ dateCalendar);
        click(dateCalendar);
        //sendKeys(inputElement, date2Set); // version anterior: sin espera al popup del calendario
        sleep(2000);

        waitForElementPresence(By.xpath("//input[contains(@class,'ant-calendar-input')]"), Timeouts.LOAD_ELEMENT);
        sendKeys(inputElement, date2Set);
        sleep(Timeouts.SHORT_TIME);
        Actions actions = new Actions(getDriver());
        actions.moveToElement(inputElement).sendKeys(Keys.ENTER).perform();
        log().image("Actions send keys and enter was executed", takeScreenshot());
        //Should be implemented an new way to manage the calendar with text,
        // now is necessary to perform another action to select the second date
    }

    public void setCalendarDatesText(WebElement dateCalendar, String dateOrTime) {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dateCalendar);

        waitForElementToBeClickable(dateCalendar);


        waitForElementToBeClickable(dateCalendar);
        System.out.println("Click boton Date Calendar"+ dateCalendar);

        click(dateCalendar);

        String xpathInput = "//div[contains(@id,'')]/descendant::input[@class='ant-calendar-input ']";
        //String xpathInput = "//input[@class='ant-calendar-input ']";

        sendKeys(getElement(By.xpath(xpathInput)),dateOrTime);
        sleep(Timeouts.SHORT_TIME);


        log().image("Actions send keys and enter was executed", takeScreenshot());
    }

    public void setTimeText(WebElement element, String dateOrTime, WebElement nextElement) {
        int tries = 0;
        Boolean comboClosed;
        Boolean finalCondition;
        do {
            click(element);
            waitForElementPresence(getDriver().findElement(By.xpath("//div[contains(@class,'combo')]")));
            WebElement secondElement = element
                    .findElement(By.xpath(INPUT_HOUR_FIELD));


            secondElement.sendKeys(dateOrTime);
            log().image(COMBO_VISIBLE_VALIDATION, takeScreenshot());
            secondElement.sendKeys(Keys.ENTER);
            log().image(COMBO_VISIBLE_VALIDATION, takeScreenshot());

            sleep(500);

            log.info(COMBO_VISIBLE_VALIDATION + element.getDomAttribute("value"));
            log().image("Try no. " + tries, takeScreenshot());
            tries++;
            int outLoop = 0;
            do {
                click(nextElement);
                log.info("aun hay dos inputs");
                outLoop++;
                log().image("outLoop no. " + outLoop, takeScreenshot());
            } while (getDriver().findElements(By.xpath(
                    ("//input[contains(@placeHolder,'HH:MM:SS')]"))).size() != 1 && outLoop < 10);

            int inputs = getDriver().findElements(By.xpath(("//input[contains(@placeHolder,'HH:MM:SS')]"))).size();
            boolean valueValidation = element.getDomAttribute("value").equals(dateOrTime);
            comboClosed = inputs == 1;

            finalCondition = valueValidation && comboClosed;



        } while (!finalCondition && tries < 10);
        log.info("Times tried to set time: " + tries);
        log().image("time set", takeScreenshot());
    }

    public void setTimeText(WebElement element, String dateOrTime) {
        click(element);
        sendKeys(element
                .findElement(By.xpath(INPUT_HOUR_FIELD)), dateOrTime);
        element.sendKeys(Keys.ENTER);
        WebElement parent = element
                .findElement(By.xpath(INPUT_HOUR_FIELD))
                .findElement(By.xpath("./parent::*"))
                .findElement(By.xpath("./parent::*"));
        parent.click();
    }

    public WebElement dynamicWebElement(String xpath, String params) {
        String newxPath = xpath.replace("?", params);
        return getBrowser().getDriver().findElement(By.xpath(newxPath));
    }

    public void findHRefElement(WebElement searchCriteria) {
        //STEPS DUE INTO THE SYSTEM EXIST SOMETIMES 2 TABLES WITH THE INFORMATION THE SOLUTION IS FIND FIRST
        // THE ID OR THE NAME OF THE FIELD THAT HAS THE HREF
        //TO OUR LUCK THIS IS IN BOTH TABLES, IF EXIST.
        //STEP1 FIND THE ELEMENT AND GET THE VALUE.
        //STEP2 dUE THE CLICKABLE ELEMENT WITH THE CORRECT HREF IS INTO THE FIRST TABLE WE WILL FIND THE ELEMENT
        // RESTRICTING OUR SEARCH INTO IT
        WebElement href2Select2 = searchCriteria.findElement(By.xpath("preceding-sibling::td//a"));
        String valueHref = href2Select2.getText();
        //Now search the number of tables tbody and will be sustituted into the xpath "(//tBODY)[1]//td//a[text()='?']"
        //if the number of tables are two will use the second table to click into the element if just has one,
        // click into the size of the numer of elements
        Integer index = getDriver().findElements(By.xpath("//tBody")).size();
        WebElement hrefTable1 = new CommonComponents()
                .dynamicWebElement("(//tBODY)[" + index + "]//td//a[contains(text(),'?')]", valueHref);
        click(hrefTable1);
    }

    public WebElement selectCheckBoxCase(String searchCase) {
        String xpath = "//td[contains(text(),'?')]/preceding-sibling::td//input";
        return dynamicWebElement(xpath, searchCase);

    }

    public List<Object> elements2Validate(WebElement element, String value) {
        List<Object> object2Validate = new ArrayList<>();
        object2Validate.add(Lists.newArrayList(element, value));
        return object2Validate;
    }

    public static String getDownloadDir() {
        return Paths.get("target/test-classes/downloads").toAbsolutePath().toString();
    }

    public List<CompleteWebElement> fillField(WebElement webElement, String value,
            List<CompleteWebElement> list) {
        if (webElement.getTagName().contains(INPUT) || webElement.getTagName().contains("textarea")) {
            sendKeys(webElement, value);
        } else if (webElement.getTagName().equals(INPUT)
                && getSpecificAttributeValue(webElement,"class").equals("ant-input-number-input")) {
            jsExecuteScript("arguments[0].value = '" + value + "';", webElement);
        } else if (webElement.getTagName().contains("div")) {
            new CommonComponents().selectFromDropdownText(webElement, value);
        }
        CompleteWebElement completeWebElement = new CompleteWebElement(webElement);
        completeWebElement.setDesiredValue(value);
        completeWebElement.setIndexTab(getIndex());
        list.add(completeWebElement);
        return list;
    }

    public List<CompleteWebElement> fillField(WebElement webElement, String value,
            List<CompleteWebElement> list, int indexTab) {
        sendKeys(webElement, value);
        CompleteWebElement completeWebElement = new CompleteWebElement(webElement);
        completeWebElement.setDesiredValue(value);
        completeWebElement.setIndexTab(indexTab);
        list.add(completeWebElement);
        return list;
    }


    private Integer getIndex() {
        List<WebElement> elements = getDriver()
                .findElements(By.xpath("//div[contains(@class,'ant-steps-navigation')]/child::div"));
        List<WebElement> activeElements = getDriver()
                .findElements(By.xpath("//div[contains(@class,'ant-steps-item-active')]"));
        if (elements.isEmpty() || activeElements.isEmpty()) {
            return 0;
        }
        return (elements.indexOf(activeElements.get(0)) + 1);
    }

    public Integer validateCaseCreation(List<CompleteWebElement> completeWebElements) {
        int numberOfFieldsCorrect = 0;
        int currentTab = 1;
        List<String> errors = new ArrayList<>();

        for (CompleteWebElement element : completeWebElements) {
            if (currentTab != element.getIndexTab()) {
                //Change if to a wait for element visibility ask for help
                currentTab++;
                click(getDriver().findElement(By.xpath(
                        "(//div[contains(@class,'ant-steps-navigation')]/child::div)[" + Integer
                                .toString(currentTab) + "]//div[@role='button']")));
            } else {
                //review if it needs to add a kind of wait

                String valueDOM = getWebElementDOMValues(element.getWebElement());
                String valueStored = element.getDesiredValue();
                if (valueDOM.equalsIgnoreCase(valueStored)) {
                    String comparison = "VALUE STORED=CONSULTED VALUE DOM: " + valueDOM
                            + VALUE_STORED + valueStored;
                    log().image("Result" + comparison, takeScreenshot());
                    numberOfFieldsCorrect++;
                } else {
                    String error = "VALUE STORED!=CONSULTED VALUE DOM: " + valueDOM + VALUE_STORED + valueStored;
                    errors.add(error);
                    log().image("VALUE STORED!=CONSULTED VALUE DOM: " + valueDOM + VALUE_STORED + valueStored,
                            takeScreenshot());
                }
            }
        }
        if (!errors.isEmpty()) {
            log.error("Something went wrong validating values: " + errors.toString());
        }
        return numberOfFieldsCorrect;
    }

    // Get the string value of an element in dom based in its tag name and its current state
    public String getWebElementDOMValues(WebElement element) {
        if (element.getTagName().contains(INPUT)) {
            return getSpecificAttributeValue(element,"aria-valuenow") != null
                    ? getSpecificAttributeValue(element,"aria-valuenow")
                    : getValue(element);
        } else if (element.getTagName().contains("div")) {
            WebElement element2find = element.findElement(By.xpath(".//div[@title]"));
            waitForElementVisibility(element2find, Timeouts.LOAD_ELEMENT);

            if (element.findElement(By.xpath("//span[@class='ant-select-arrow']")) != null) {
                waitForSpinner();
            }

            return getSpecificAttributeValue(element2find,"title").isEmpty()
                    ? getSpecificAttributeValue(element2find, "outerText")
                    : getSpecificAttributeValue(element2find, "title");


        } else if (element.getTagName().equals("textarea")) {
            return getSpecificAttributeValue(element, "textContent");
        } else {
            return "false";
        }
    }

    public WebElement getElementWithRetry(String xpath) {
        boolean wasElementFound = false;
        WebElement element = null;
        int retryCount = 0;
        do {
            retryCount++;
            try {
                log.info("Searching for lement xpath: {}", xpath);
                element = getElement(By.xpath(xpath));
                wasElementFound = true;
                sleep(Timeouts.SHORT_TIME);
                log().image("File loaded successfully", takeScreenshot());
            } catch (ElementNotFoundException | NoSuchElementException ex) {
                log.warn("Waiting for element error: {}", ex.getLocalizedMessage());
            }
        } while (!wasElementFound && retryCount < 2);
        return element;
    }

    public void waitForSelectorSpinner(String parent) {
        log.info("Waiting for selector to load");
        String loader = StringUtils.replace(SPINNER_LOADER, "?", parent);
        waitForElementPresence(getElement(By.xpath(loader)), Timeouts.LOADER);
        log.info("Exiting Wait for selector to load");
    }

    public void waitForSpinner() {
        By loadingLocator = By.xpath(SPINNER);

        FluentWait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(3))
                .pollingEvery(Duration.ofMillis(5L))
                .ignoring(NoSuchElementException.class);

        try {
            getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            boolean isPresent = wait.until(loader -> getDriver().findElement(loadingLocator).isDisplayed());
            if (isPresent) {
                log.info("SPINNER FOUND");
                waitForElementInvisibility(getDriver().findElement(loadingLocator), Timeouts.ELEMENT_INVISIBILITY);
            }
        } catch (Exception ex) {
            log.info("Not Spinner: {}", ex.getMessage());
        } finally {
            getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            log.info("In FInally");
        }
    }

    public String getNotificationMessage() {
        try {
            WebElement notificationWindow = getElement(By.xpath(NOTIFICATION_WINDOW));
            if (!notificationWindow.isDisplayed()) {
                log.info("Notification window not displayed");
                waitForElementPresence(notificationWindow.findElement(By.xpath(NOTIFICATION_MESSAGE)));
                log.info("Notification window displayed");
                log().image("Notification window", takeScreenshot());
                return getText(notificationWindow.findElement(By.xpath(NOTIFICATION_MESSAGE)));
            }
        } catch (Exception ex) {
            log.info("No notification window found: {}", ex.getLocalizedMessage());
        }
        return null;
    }
}
