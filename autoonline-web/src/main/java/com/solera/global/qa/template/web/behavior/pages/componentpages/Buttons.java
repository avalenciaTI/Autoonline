package com.solera.global.qa.template.web.behavior.pages.componentpages;

import com.solera.global.qa.taf.web.tools.webdriver.BrowserPage;
import com.solera.global.qa.template.web.behavior.data.timeouts.Timeouts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class Buttons extends BrowserPage {
    private static final String SEARCH_BUTTON = "//button[@type='submit']";
    private static final String ACCEPT_BUTTON = "(//span[text()='Aceptar'])[1]";
    private static final String ACCEPT_BUTTON_SECOND = "(//span[text()='Aceptar'])[2]";
    private static final String ACCEPT_BUTTON_SECOND_BUYER = "(//span[text()='Aceptar'])[2]";

    private static final String APPROVAL_BUTTON = "//span[text()='Aprobar']/parent::button[@type='button']";
    private static final String ACTIVATE_BUTTON = "//span[text()='Activar']/parent::button[@type='button']";
    private static final String CLOSE_BUTTON = "//button[@type='button' and @aria-label='Close']";
    private static final String RESTART_BUTTON
            = "//button[@type='submit' and (span[text()='Buscar'] or span[text()='Enviar'])]";
    private static final String CANCEL_BUTTON = "//span[text()='Cancelar']/parent::button[@type='button']";
    private static final String CONTINUE_BUTTON = "//span[text()='Continuar']"
            + "/parent::button[@type='button' or @type='submit']";
    private static final String UPDATE_BUTTON = "//a[contains(@href, 'update')] | //button[@data-testid='update']"
            + " | //span[text()='Actualizar']/parent::button[@type='button']";
    private static final String ASSIGN_TRANSFER_BUTTON = "//span[text()='Asignar traslado']"
            + "/parent::button[@type='button']";
    private static final String ADJUDICATE_BUTTON = "//a[text()='Adjudicar']";
    public static final String REPORT_BUTTON = "//button[@jest-id='reportButton']";
    private static final String INSURANCE_CARRIER_FIELD = "//input[@value='*****']";
    private static final String BUSCAR_BUTTON = "//button[span[text()='Buscar']]";
    private static final String ADD_IMAGE = "//button[@id='test-image-uploader-modal']";
    private static final String SEND_BUTTON = "//span[text()='Enviar']/parent::button[@type='submit']";
    
    



    @FindBy(xpath = SEARCH_BUTTON)
    WebElement searchButton;
    @FindBy(xpath = RESTART_BUTTON)
    WebElement restartButton;
    @FindBy(xpath = ACTIVATE_BUTTON)
    WebElement activateButton;
    @FindBy(xpath = CANCEL_BUTTON)
    WebElement cancelButton;
    @FindBy(xpath = CONTINUE_BUTTON)
    WebElement continueButton;
    @FindBy(xpath = ACCEPT_BUTTON)
    WebElement acceptButton;
    @FindBy(xpath = CLOSE_BUTTON)
    WebElement closeButton;
    @FindBy(xpath = UPDATE_BUTTON)
    WebElement updateButton;
    @FindBy(xpath = ASSIGN_TRANSFER_BUTTON)
    WebElement assignTransferButton;
    @FindBy(xpath = INSURANCE_CARRIER_FIELD)
    WebElement insuranceCarrierField;
    @FindBy(xpath = ADD_IMAGE)
    WebElement addImage;
    @FindBy(xpath = BUSCAR_BUTTON)
    WebElement buscarButton;
    @FindBy(xpath = ACCEPT_BUTTON_SECOND)
    WebElement acceptButton_Second;
    @FindBy(xpath = ACCEPT_BUTTON_SECOND_BUYER)
    WebElement acceptButton_SecondBuyer;
    @FindBy(xpath = SEND_BUTTON)
    WebElement sendButton;
    
    

    public Buttons() {
        super();
    }

    public void clickSearchBtn() {
        jsClick(searchButton);
    }

    public void clickBuscarBtn() {
        click(buscarButton);
    }

    public void clickRestartBtn() {
        click(restartButton);
    }

    public void clickAddImage() {
        click(addImage);
    }

    public void clickCancelBtn() {
        click(cancelButton);
    }

    public void clickContinueBtn() {
        sleep(Timeouts.LOAD_BUTTON);
        //scrollTo(acceptButton);
        waitForElementToBeClickable(getElement(By.xpath(CONTINUE_BUTTON)), Timeouts.LOAD_BUTTON);
        click(getElement(By.xpath(CONTINUE_BUTTON)));
    }

    public WebElement getAcceptButton() {
        return acceptButton;
    }

    public WebElement getAcceptButton_Second() {
        return acceptButton_Second;
    }

    public void clickAcceptButton() {
        waitForElementToBeClickable(acceptButton, Timeouts.LOAD_BUTTON);
        click(acceptButton);
        //sleep(Timeouts.LOADER);
    }

    public void jsClickAcceptButton() {
        waitForElementToBeClickable(acceptButton, Timeouts.LOAD_BUTTON);
        jsClick(acceptButton);
        //sleep(Timeouts.LOADER);
    }
    
    public WebElement getSendButton() {
        return sendButton;
    }

    public void clickSendButton() {
        waitForElementToBeClickable(sendButton, Timeouts.LOAD_BUTTON);
        jsClick(sendButton);
    }

    public void jsClickAcceptButtonBuyer() {
        waitForElementToBeClickable(acceptButton_SecondBuyer, Timeouts.LOAD_BUTTON);
        jsClick(acceptButton_SecondBuyer);
        //sleep(Timeouts.LOADER);
    }
    


    
    


    /**
     * Scrolls the page until the Accept button is in view and then clicks it.
     * Useful when the button is below the visible area.
     */
    public void scrollAndClickAcceptButton() {
        scrollTo(acceptButton);
        waitForElementToBeClickable(acceptButton, Timeouts.LOAD_BUTTON);
        click(acceptButton);
    }

    public void clickApprovalButton() {
        waitForElementToBeClickable(getElement(By.xpath(APPROVAL_BUTTON)), Timeouts.LOAD_BUTTON);
        click(getElement(By.xpath(APPROVAL_BUTTON)));
    }
    
    public void clickInsuranceCarrierField() {
        click(insuranceCarrierField);
    }

    // Method used for wait to pre-cases activation page load
    public void waitForPreCaseActivationLoad() {
        waitForElementToBeClickable(activateButton, Timeouts.LOAD_BUTTON);
    }

    public void clickActivateButton() {
        click(activateButton);
    }

    public void clickCloseBtn() {
        click(closeButton);
        waitForElementInvisibility(closeButton, Timeouts.SHORT_TIME);
    }

    public void clickUpdateBtn() {
        waitForElementToBeClickable(updateButton, Timeouts.SHORT_TIME);
        click(updateButton);
    }


    public void clickAssignTransferButton() {
        waitForElementToBeClickable(assignTransferButton, 10000);
        click(assignTransferButton);
    }

    public void clickAdjudicateBtn() {
        WebElement adjudicateBtb = getElement(By.xpath(ADJUDICATE_BUTTON));
        click(adjudicateBtb);
    }
}
