package com.solera.global.qa.template.web.behavior.data.types;

import com.solera.global.qa.template.web.behavior.data.AolWebPropertiesReader;
import java.util.Properties;

public class Vehicle {

    private final String fieldReport;
    private final String policy;
    private final String wreckType;
    private final String wreckSubtype;
    private final String vehicleBrand;
    private final String vehicleType;
    private final String vehicleVersion;
    private final String vehicleColor;
    private final String modelYear;
    private final String policySerial;
    private final String vin;
    private final String plate;
    private final String engineNumber;
    private final String engineType;
    private final String unitType;

    public Vehicle(String fileName) {
        AolWebPropertiesReader webPropertiesReader = new AolWebPropertiesReader();
        Properties car = webPropertiesReader.getPropertiesFromPath(AolWebPropertiesReader.getVehicleFolder(fileName));

        this.fieldReport = car.getProperty("report");
        this.policy = car.getProperty("policy");
        this.wreckType = car.getProperty("wreck_type");
        this.wreckSubtype = car.getProperty("wreck_subtype");
        this.vehicleBrand = car.getProperty("vehicle_brand");
        this.vehicleType = car.getProperty("vehicle_type");
        this.vehicleVersion = car.getProperty("vehicle_version");
        this.vehicleColor = car.getProperty("vehicle_color");
        this.modelYear = car.getProperty("model_year");
        this.policySerial = car.getProperty("policy_serial");
        this.vin = car.getProperty("vin");
        this.plate = car.getProperty("vehicle_plate");
        this.engineNumber = car.getProperty("engine_number");
        this.engineType = car.getProperty("engine_type");
        this.unitType = car.getProperty("unit_type");
    }

    public String getFieldReport() {
        return fieldReport;
    }

    public String getPolicy() {
        return policy;
    }

    public String getWreckType() {
        return wreckType;
    }

    public String getWreckSubtype() {
        return wreckSubtype;
    }

    public String getVehicleBrand() {
        return System.getenv("JENKINS_HOME") != null ? "AUTOMATION JENKINS QA" : "AUTOMATION LOCAL QA";
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleVersion() {
        return vehicleVersion;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public String getModelYear() {
        return modelYear;
    }

    public String getPolicySerial() {
        return policySerial;
    }

    public String getVin() {
        return vin;
    }

    public String getPlate() {
        return plate;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public String getEngineType() {
        return engineType;
    }

    public String getUnitType() {
        return unitType;
    }

}
