package com.solera.global.qa.template.web.behavior.pages.payments;

public enum Supplier {
    ALL("*****"),
    FERNANDO_REGRESION("PFIA037"),
    GRUAS_AUTOONLINE("PGAO035"),
    CHAVARRIA("PCHA008");

    private final String supplier;

    private Supplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplier() {
        return this.supplier;
    }
}
