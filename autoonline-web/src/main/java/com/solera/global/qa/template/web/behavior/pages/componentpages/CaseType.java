package com.solera.global.qa.template.web.behavior.pages.componentpages;

public enum CaseType {
    VARIOUS("DIVERSOS"),
    TRANSFERS("TRASLADOS"),
    VEHICLES("VEHÍCULOS");
    private final String caseTypes;

    private CaseType(String caseType) {
        this.caseTypes = caseType;
    }

    public String getCaseType() {
        return this.caseTypes;
    }

}
