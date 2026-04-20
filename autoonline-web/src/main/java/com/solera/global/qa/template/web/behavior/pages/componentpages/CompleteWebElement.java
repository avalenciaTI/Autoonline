package com.solera.global.qa.template.web.behavior.pages.componentpages;

import org.openqa.selenium.WebElement;

public class CompleteWebElement {

    private WebElement webElement;
    private String desiredValue;
    private Integer indexTab;

    public WebElement getWebElement() {
        return webElement;
    }

    public WebElement clearWebElement() {
        webElement.clear();
        return webElement;
    }

    public String getDesiredValue() {
        return desiredValue;
    }

    public Integer getIndexTab() {
        return indexTab;
    }

    public void setDesiredValue(String desiredValue) {
        this.desiredValue = desiredValue;
    }

    public void setIndexTab(Integer indexTab) {
        this.indexTab = indexTab;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public CompleteWebElement(WebElement webElement) {
        this.webElement = webElement;
    }
}
