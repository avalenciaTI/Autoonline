package com.solera.global.qa.template.web.behavior.pages.componentpages.enums;

public enum TypeOfDate {
    START_DATE_ELEMENT("search-adverts_start"),
    END_DATE_ELEMENT("search-adverts_end");

    private final String dateType;

    private TypeOfDate(String dateType) {
        this.dateType = dateType;
    }

    public String getDateType() {
        return this.dateType;
    }

}
