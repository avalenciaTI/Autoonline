package com.solera.global.qa.template.web.behavior.pages.componentpages.enums;

public enum CaseType implements ICaseType {
    VARIOUS("DIVERSOS"),
    VEHICLES("VEHÍCULOS");
    private final String type;

    private CaseType(String caseType) {
        this.type = caseType;
    }

    public String getType() {
        return this.type;
    }
}
