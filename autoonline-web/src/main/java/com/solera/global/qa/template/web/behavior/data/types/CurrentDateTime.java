package com.solera.global.qa.template.web.behavior.data.types;

public class CurrentDateTime {

    String date;
    String time;

    public CurrentDateTime(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

}
