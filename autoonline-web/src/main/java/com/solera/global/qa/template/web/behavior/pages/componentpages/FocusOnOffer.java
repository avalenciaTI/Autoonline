package com.solera.global.qa.template.web.behavior.pages.componentpages;

public enum FocusOnOffer {
    YES("Sí"),
    NOT("No"),
    ALL("Todos");
    private final String focus;

    private FocusOnOffer(String caseType) {
        this.focus = caseType;
    }

    public String getFocusType() {
        return this.focus;
    }
}
