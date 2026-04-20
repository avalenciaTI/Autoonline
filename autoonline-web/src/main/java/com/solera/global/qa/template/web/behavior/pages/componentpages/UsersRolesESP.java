package com.solera.global.qa.template.web.behavior.pages.componentpages;

public enum UsersRolesESP {
    ADMINISTRATOR_MASTER("Administrador","Master","1-RegistroMasivo_Administrador_Master.xlsx"),
    ADMINISTRATOR_INTERN("Administrador","Interno","2-RegistroMasivo_Administrador_Interno.xlsx"),
    INSURED("Asegurado","","3-RegistroMasivo_Asegurado.xlsx"),
    INSURANCE_COMPANY_MASTER("Aseguradora","Master","4-RegistroMasivo_Aseguradora_Master.xlsx"),
    INSURANCE_COMPANY_INTERN("Aseguradora","Interno","5-RegistroMasivo_Aseguradora_Interno.xlsx"),
    BUYER_PHYSICAL("Comprador","Comprador (Persona Física)","6-RegistroMasivo_Comprador_(PersonaFísica).xlsx"),
    BUYER_MORAL("Comprador","Comprador (Persona Moral)","7-RegistroMasivo_Comprador_(PersonaMoral).xlsx"),
    SUPPLIER_MANAGEMENT(getProvider(),"Gestoría","8-RegistroMasivo_Proveedor.xlsx"),
    SUPPLIER_CRANE(getProvider(),"Grúa",""),
    SUPPLIER_CORRALON(getProvider(),"Corralón","");


    private final String userSelected;
    private final String typeUser;
    private final String fileMassiveLoad;

    private UsersRolesESP(String userSelected,String typeUser, String fileMassiveLoad) {
        this.userSelected = userSelected;
        this.typeUser = typeUser;
        this.fileMassiveLoad = fileMassiveLoad;
    }

    public String getUserSelected() {
        return this.userSelected;
    }

    public String getTypeUser() {
        return this.typeUser;
    }

    public String getFileMassiveLoad() {
        return this.fileMassiveLoad;
    }

    private static String getProvider() {
        return "Proveedor";
    }
}