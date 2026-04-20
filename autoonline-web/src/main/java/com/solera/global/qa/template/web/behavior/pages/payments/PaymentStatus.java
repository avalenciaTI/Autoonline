package com.solera.global.qa.template.web.behavior.pages.payments;

public enum PaymentStatus {

    ALL("*****"),
    PENDING_ATTACHMENT("1"),
    PENDING_VALIDATION("2"),
    REJECTED("3"),
    APPROVED("4"),
    CANCELLED("5");

    private final String status;

    private PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
