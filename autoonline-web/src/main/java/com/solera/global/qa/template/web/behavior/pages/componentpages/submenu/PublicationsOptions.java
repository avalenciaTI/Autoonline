package com.solera.global.qa.template.web.behavior.pages.componentpages.submenu;

public enum PublicationsOptions {
    REGISTER("/adverts/add"),
    CONSULT("/adverts/search");

    private final String subMenuSelection;

    private PublicationsOptions(String subMenuSelection) {
        this.subMenuSelection = subMenuSelection;
    }

    public String getSubMenuSelection() {
        return this.subMenuSelection;
    }


}
