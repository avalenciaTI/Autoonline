package com.solera.global.qa.template.web.behavior.data.timeouts;

public class Timeouts {

    public static final int SHORT_TIME = 1000;
    public static final int LOAD_ELEMENT = 5000;
    public static final int ELEMENT_INVISIBILITY = 5000;
    public static final int CLOSE_SESSION = 5000;
    public static final int NOTIFICATION_CLOSED = 5000;
    public static final int LOAD_PAGE = 10000;
    public static final int LOADER = 10000;
    public static final int LOAD_BUTTON = 10000;
    public static final int NOTIFICATION_DISPLAYED = 15000;
    public static final int WAIT_FOR_NOTIFICATION = 25000;
    public static final int STATUS_VALIDATION = 15000;
    public static final int LOAD_RESULTS = 20000;
    public static final int LOAD_HEAVY_RESULTS = 30000;
    public static final int NOTIFICATION_FADED = 20000;
    public static final int LOAD_HEAVY_PAGE = 25000;
    public static final int IMAGE_SKELETON_FADE = 60000;

    private Timeouts() {
        throw new IllegalStateException("Utility class");
    }

}
