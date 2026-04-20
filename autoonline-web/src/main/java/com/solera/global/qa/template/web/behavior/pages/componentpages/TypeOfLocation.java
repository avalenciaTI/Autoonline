package com.solera.global.qa.template.web.behavior.pages.componentpages;

public enum TypeOfLocation {
    BODY_SHOP("TALLER"),
    OTHER("OTROS"),
    IMPOUND("CORRALON");

    private final String locationSelected;


    private TypeOfLocation(String locationSelected) {
        this.locationSelected = locationSelected;
    }

    public String getLocationSelected() {
        return this.locationSelected;
    }
}
