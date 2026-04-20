package com.solera.global.qa.template.web.behavior.pages.componentpages.enums;

public enum AdjudicationStatus implements ICaseType {
    ADJUDICATED("Adjudicado"),
    TO_BE_ADJUDICATED("Por adjudicar"),
    TO_VALIDATE_ADJUDICATION("Por validar adjudicación");
    private final String type;

    private AdjudicationStatus(String caseType) {
        this.type = caseType;
    }

    public String getType() {
        return this.type;
    }
}
