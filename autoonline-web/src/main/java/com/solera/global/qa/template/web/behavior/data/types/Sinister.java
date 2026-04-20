package com.solera.global.qa.template.web.behavior.data.types;

import com.solera.global.qa.template.web.behavior.data.AolWebPropertiesReader;
import java.util.Properties;

public class Sinister {

    private final String insurer;
    private final String compensationValue;
    private final String commertialValue;
    private final String baseValue;
    private final String spareCost;
    private final String repairCost;
    private final String observations;
    private final String c1Field;
    private final String c2Field;
    private final String c3Field;

    public Sinister(String fileSinister) {
        AolWebPropertiesReader webPropertiesReader = new AolWebPropertiesReader();
        Properties pm = webPropertiesReader
                .getPropertiesFromPath(AolWebPropertiesReader.getSinisterFolder(fileSinister));
        this.insurer = pm.getProperty("insurer");
        this.compensationValue = pm.getProperty("compensation_value");
        this.commertialValue = pm.getProperty("commertial_value");
        this.baseValue = pm.getProperty("base_value");
        this.spareCost = pm.getProperty("spare_cost");
        this.repairCost = pm.getProperty("repair_cost");
        this.observations = pm.getProperty("observations");
        this.c1Field = pm.getProperty("c1_field");
        this.c2Field = pm.getProperty("c2_field");
        this.c3Field = pm.getProperty("c3_field");
    }

    public String getInsurer() {
        return insurer;
    }

    public String getCompensationValue() {
        return compensationValue;
    }

    public String getCommertialValue() {
        return commertialValue;
    }

    public String getBaseValue() {
        return baseValue;
    }

    public String getSpareCost() {
        return spareCost;
    }

    public String getRepairCost() {
        return repairCost;
    }

    public String getObservations() {
        return observations;
    }

    public String getC1Field() {
        return c1Field;
    }

    public String getC2Field() {
        return c2Field;
    }

    public String getC3Field() {
        return c3Field;
    }


}
