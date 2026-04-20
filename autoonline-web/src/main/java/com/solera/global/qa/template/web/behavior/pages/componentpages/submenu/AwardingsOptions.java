package com.solera.global.qa.template.web.behavior.pages.componentpages.submenu;

public enum AwardingsOptions {

    CONSULT_AWARDINGS("/awardings/search/1"),
    MASSIVE_AWARDINGS("/awardings/massive/award"),
    MASSIVE_REFERENCE_AWARDINGS("/awardings/massive/reference");


    private final String awardingSubMenuSelection;

    private AwardingsOptions(String awardingSubMenuOption) {
        this.awardingSubMenuSelection = awardingSubMenuOption;
    }

    public String getAwardingSubMenuSelection() {
        return this.awardingSubMenuSelection;
    }
}
