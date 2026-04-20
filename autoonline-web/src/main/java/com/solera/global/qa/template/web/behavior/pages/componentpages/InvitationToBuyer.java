package com.solera.global.qa.template.web.behavior.pages.componentpages;

public enum InvitationToBuyer {
    INDIVIDUAL("Individual", ""),
    MASSIVE("Masivo", "");

    private final String sendType;
    private final String fileMassiveLoad;

    private InvitationToBuyer(String sendType, String fileMassiveLoad) {
        this.sendType = sendType;
        this.fileMassiveLoad = fileMassiveLoad;
    }

    public String getSendType() {
        return this.sendType;
    }

    public String getFileMassiveLoad() {
        return this.fileMassiveLoad;
    }
}
