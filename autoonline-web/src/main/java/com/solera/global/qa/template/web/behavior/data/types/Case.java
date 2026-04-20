package com.solera.global.qa.template.web.behavior.data.types;

public class Case {

    private final AolWebUser user;

    private final Vehicle vehicle;

    private final Workshop workshop;

    private final Sinister sinister;

    public Case(AolWebUser user, Vehicle car, Workshop workshop, Sinister sinister) {
        this.user = user;
        this.vehicle = car;
        this.workshop = workshop;
        this.sinister = sinister;
    }

    public AolWebUser getUser() {
        return user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public Sinister getSinister() {
        return sinister;
    }
}
