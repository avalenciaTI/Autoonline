package com.solera.global.qa.template.web.behavior.pages.componentpages.submenu;

public enum ReportsOptions {
    TRANSFERS("/reports/transfers"),
    INVENTORY("/reports/inventory");

    private final String subMenuSelection;

    private ReportsOptions(String subMenuSelection) {
        this.subMenuSelection = subMenuSelection;
    }

    public String getSubMenuSelection() {
        return this.subMenuSelection;
    }


}
