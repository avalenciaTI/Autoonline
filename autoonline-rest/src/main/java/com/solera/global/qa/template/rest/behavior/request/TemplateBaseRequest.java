package com.solera.global.qa.template.rest.behavior.request;

import com.solera.global.qa.taf.core.config.TafConfig;

public class TemplateBaseRequest {

    private static final String TEMPLATE_REST_ENDPOINT = "template.rest.endpoint";

    private TemplateBaseRequest() {
        throw new IllegalStateException("Utility class");
    }

    private static final String HTTP_DOMAIN = "https://";

    public static String getBaseUrl() {
        return TafConfig.AppConfig.SERVICES_CUSTOM_API_URL.getValue("typicode");
    }

}
