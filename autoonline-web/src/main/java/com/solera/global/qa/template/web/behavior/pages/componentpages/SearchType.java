package com.solera.global.qa.template.web.behavior.pages.componentpages;

public enum SearchType {
    ADVANCED_SEARCH("Búsqueda avanzada"),
    GENERAL_SEARCH("Búsqueda general");

    private final String searchingType;

    private SearchType(String searchingType) {
        this.searchingType = searchingType;
    }

    public String getSearchingType() {
        return this.searchingType;
    }
}
