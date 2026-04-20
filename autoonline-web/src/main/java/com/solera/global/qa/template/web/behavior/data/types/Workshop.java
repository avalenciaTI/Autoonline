package com.solera.global.qa.template.web.behavior.data.types;

import com.solera.global.qa.template.web.behavior.data.AolWebPropertiesReader;
import java.util.Properties;

public class Workshop {

    private final String location;
    private final String workshopNumber;
    private final String name;
    private final String country;
    private final String state;
    private final String street;
    private final String externalNumber;
    private final String internalNumber;
    private final String zipCode;
    private final String neighborhood;
    private final String town;
    private final String city;

    public Workshop(String fileName) {
        AolWebPropertiesReader webPropertiesReader = new AolWebPropertiesReader();
        Properties pm = webPropertiesReader.getPropertiesFromPath(AolWebPropertiesReader.getWorkshopFolder(fileName));
        this.location = pm.getProperty("location_type");
        this.workshopNumber = pm.getProperty("ws_number");
        this.name = pm.getProperty("ws_name");
        this.country = pm.getProperty("ws_country");
        this.state = pm.getProperty("ws_state");
        this.street = pm.getProperty("ws_street");
        this.externalNumber = pm.getProperty("ws_street_number");
        this.internalNumber = pm.getProperty("ws_street_interior_number");
        this.zipCode = pm.getProperty("ws_zip_code");
        this.neighborhood = pm.getProperty("ws_neighborhood");
        this.town = pm.getProperty("ws_town");
        this.city = pm.getProperty("ws_city");
    }

    public String getLocation() {
        return location;
    }

    public String getWorkshopNumber() {
        return workshopNumber;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }

    public String getExternalNumber() {
        return externalNumber;
    }

    public String getInternalNumber() {
        return internalNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getTown() {
        return town;
    }

    public String getCity() {
        return city;
    }

}
