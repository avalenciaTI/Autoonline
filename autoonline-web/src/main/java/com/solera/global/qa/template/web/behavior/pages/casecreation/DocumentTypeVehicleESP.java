package com.solera.global.qa.template.web.behavior.pages.casecreation;

public enum DocumentTypeVehicleESP {
    MARKET_FILE_1("Artículo 140 ",
            "1_Mercado aseguradora_ 1 ARTICULO 140.pdf",
            "comment art 140",
            1,
            false),
    MARKET_FILE_2("Carta de pérdida total ",
            "1_Mercado aseguradora_ 2 Carta de Perdida Total.pdf",
            "comment carta perdida total",
            1,
            false),
    MARKET_FILE_3("Finiquito para indemnizar ",
            "1_Mercado aseguradora_3 Finiquito para indemnizar.pdf",
            "comment finiquito para indemnizar",1,false),
    MARKET_FILE_4("Formato deslinde responsabilidad ",
            "1_Mercado aseguradora_4  Formato deslinde responsabilidad.pdf",
            "comment formato deslinde responsabilidad",
            1,
            false),
    MARKET_FILE_5("Póliza ",
            "1_Mercado aseguradora_5 Poliza.pdf",
            "comment poliza",
            1,
            false),
    MARKET_FILE_6("Comprobante de pago ",
            "1_Mercado aseguradora_6 Comprobante de pago.pdf",
            "comment comprobante de pago",
            1,
            false),
    MARKET_FILE_7("Finiquito ",
            "1_Mercado aseguradora_7 Finiquito.pdf",
            "comment finiquito",
            1,
            false),
    INSURED_FILE_1("Acta constitutiva ",
            "2_Asegurado_1 Acta constitutiva.pdf",
            "comment acta constitutiva",
            2,
            false),
    INSURED_FILE_2("Identificación del apoderado ",
            "2_Asegurado_2 Identificacion del apoderado.pdf",
            "comment identificacion del apoderado",
            2,
            false),
    INSURED_FILE_3("Poder de representante legal ",
            "2_Asegurado_3 Poder de representante legal.pdf",
            "comment poder de representante legal",
            2,
            false),
    INSURED_FILE_4("Comprobante de domicilio ",
            "2_Asegurado_4 Comprobante de domicilio.pdf",
            "comment comprobante de domicilio",
            2,
            false),
    INSURED_FILE_5("Estado de cuenta bancario ",
            "2_Asegurado_5 Estado de cuenta bancario.pdf",
            "comment estado de cuenta bancario",
            2,
            false),
    INSURED_FILE_6("Identificación oficial ",
            "2_Asegurado_6 Identificacion oficial.pdf",
            "comment identificacion oficial",
            2,
            false),
    VEHICLE_FILE_1("Baja de placas ",
            "3_Vehículo_1 Baja de placas.pdf",
            "comment baja de placas",
            3,
            true),
    VEHICLE_FILE_2("Factura de origen ",
            "3_Vehiculo_2 Factura de origen.pdf",
            "comment factura de origen",
            3,
            false),
    VEHICLE_FILE_3("Facturas intermedias ",
            "3_Vehiculo_3 Facturas intermedias.pdf",
            "comment facturas intermedias",
            3,
            false),
    VEHICLE_FILE_4("Llaves ",
            "3_Vehiculo_4 Llaves .pdf",
            "comment llaves",
            3,
            true),
    VEHICLE_FILE_5("Recibo baja de placas ",
            "3_Vehiculo_5 Recibo baja de placas.pdf",
            "comment recibo baja de placas",
            3,
            false),
    VEHICLE_FILE_6("Tenencias ",
            "3_Vehiculo_6 Tenencia1.pdf",
            "comment tenencias",
            3,
            false),
    VEHICLE_FILE_7("Última factura",
            "3_Vehiculo_7 Ultima factura.pdf",
            "comment ultima factura",
            3,
            true),
    OTHER_FILE_1("Acta de robo ",
            "4_Otro_1 Acta de robo.pdf",
            "comment acta de robo",
            4,
            true),
    OTHER_FILE_2("Estatus reporte REPUVE ",
            "4_Otro2_  Estatus reporte REPUVE.pdf",
            "comment estatus reporte repuve",
            4,
            true),
    OTHER_FILE_3("Liberación ",
            "4_Otro3_ Liberacion.pdf",
            "comment liberacion",
            4,
            true),
    OTHER_FILE_4("Otros ",
            "4_Otro4_ Otros.pdf",
            "comment otros",
            4,
            false);

    private final String documentTypeVehicle;
    private final String fileLoad;
    private final String comment;
    private final Integer elementTab;
    private final Boolean isRequired;

    DocumentTypeVehicleESP(String documentTypeVehicle, String fileLoad, String comment,
            Integer elementTab, Boolean isRequired) {
        this.documentTypeVehicle = documentTypeVehicle;
        this.fileLoad = fileLoad;
        this.comment = comment;
        this.elementTab = elementTab;
        this.isRequired = isRequired;
    }

    public String getDocumentTypeVehicle() {
        return this.documentTypeVehicle;
    }

    public String getFileLoad() {
        return this.fileLoad;
    }

    public String getComment() {
        return this.comment;
    }

    public Integer getElementTab() {
        return this.elementTab;
    }

    public Boolean getIsRequired() {
        return this.isRequired;
    }
}
