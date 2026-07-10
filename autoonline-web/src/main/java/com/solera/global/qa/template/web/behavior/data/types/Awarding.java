package com.solera.global.qa.template.web.behavior.data.types;

public class Awarding {
    private String publicationName;
    private String sinister;
    private String vendor;
    private String insuranceCompany;
    private String caseStatus;
    private String bidAmount;
    private String vin;
    private String publicationId;

    public Awarding() {
        // Default constructor
    }

    public Awarding(String publicationName, String sinister, String vendor, String insuranceCompany, String caseStatus,
            String bidAmount) {
        this.publicationName = publicationName;
        this.sinister = sinister;
        this.vendor = vendor;
        this.insuranceCompany = insuranceCompany;
        this.caseStatus = caseStatus;
        this.bidAmount = bidAmount;
    }

    public String getPublicationName() {
        return publicationName;
    }

    public void setPublicationName(String publicationName) {
        this.publicationName = publicationName;
    }

    public String getSinister() {
        return sinister;
    }

    public void setSinister(String sinister) {
        this.sinister = sinister;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }



}
