package com.solera.global.qa.template.web.behavior.pages.componentpages.enums;

public enum PaymentTicketType {
    BID_PAYMENT_TICKET("Monto de oferta",
            "bid_payment_ticket.pdf"),
    ADMINISTRATIVE_PAYMENT_TICKET("Monto Administrativo",
            "administrative_payment_ticket.pdf"),
    INSURER_PAYMENT_TICKET("Monto administrativo de aseguradora",
            "insurer_payment_ticket.pdf");

    private final String paymentType;
    private final String fileName;

    PaymentTicketType(String paymentType, String fileName) {
        this.paymentType = paymentType;
        this.fileName = fileName;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public String getFileName() {
        return this.fileName;
    }
}
