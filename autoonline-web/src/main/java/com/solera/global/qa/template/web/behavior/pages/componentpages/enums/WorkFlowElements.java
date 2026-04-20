package com.solera.global.qa.template.web.behavior.pages.componentpages.enums;

public enum WorkFlowElements {
    PUBLICATION("adverts"),
    AWARDING("awardings"),
    PAYMENT("payments");

    private final String element;

    private WorkFlowElements(String status) {
        this.element = status;
    }

    public String getElement() {
        return this.element;
    }
}
