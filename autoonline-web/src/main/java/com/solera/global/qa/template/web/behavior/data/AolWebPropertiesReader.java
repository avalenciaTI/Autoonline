package com.solera.global.qa.template.web.behavior.data;

import com.solera.global.qa.taf.utils.PropertiesUtils;
import java.util.Properties;


public final class AolWebPropertiesReader {

    private static final String RESOURCES_FOLDER = "src/test/resources/";
    private static final String DATA_FOLDER = RESOURCES_FOLDER + "data";
    public static final String USERS_FOLDER = DATA_FOLDER + "/users/";
    public static final String VEHICLE_FOLDER = DATA_FOLDER + "/vehicle/";
    public static final String WORKSHOP_FOLDER = DATA_FOLDER + "/workshop/";
    public static final String SINISTER_FOLDER = DATA_FOLDER + "/sinister/";
    private static final String PROPERTIES_EXTENSSION = ".properties";

    private static final String ATTACHMENTS_FOLDER = RESOURCES_FOLDER + "attachments/";


    public static final String TEMPLATE_WEB_PROPERTYNAME = "template.web.propertyname";


    public Properties getPropertiesFromPath(String path) {
        return PropertiesUtils.loadProperties(path);
    }

    public static String getFileUserName(String fileUserName) {
        return USERS_FOLDER + fileUserName + PROPERTIES_EXTENSSION;
    }

    public static String getVehicleFolder(String fileVehicle) {
        return VEHICLE_FOLDER + fileVehicle + PROPERTIES_EXTENSSION;
    }

    public static String getWorkshopFolder(String fileWorkshop) {
        return WORKSHOP_FOLDER + fileWorkshop + PROPERTIES_EXTENSSION;
    }

    public static String getSinisterFolder(String fileSinister) {
        return SINISTER_FOLDER + fileSinister + PROPERTIES_EXTENSSION;
    }

    public static String getAttachmentsFolder(String folder) {
        return ATTACHMENTS_FOLDER + folder;
    }

    public static String getAttachmentsPath() {
        return ATTACHMENTS_FOLDER;
    }

}
