package com.solera.global.qa.template.web.behavior.pages.payments;

public enum Insurers {
    ALL("*****"),
    AIG("AAIG003"),
    ANA_INSURER("AANA002"),
    LFFS_INSURER("AASE191"),
    TEST_INSURER("AAPR011"),
    QA_REG_INSURER("AASR221"),
    QA_TEST_AUTOMATION("AQAT270"),
    ATLAS_INSURER("AATL001");

    private final String insurer;

    private Insurers(String insurer) {
        this.insurer = insurer;
    }

    public String getInsurer() {
        return this.insurer;
    }
}
