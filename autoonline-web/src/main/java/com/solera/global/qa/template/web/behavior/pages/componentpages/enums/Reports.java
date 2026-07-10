package com.solera.global.qa.template.web.behavior.pages.componentpages.enums;

import java.util.ArrayList;
import java.util.List;

public enum Reports {
    REPORTS_PUBLICATION_RESULTS("target/test-classes/attachments/"
            + "reportCompare/Resultados_APRV2506000042.xlsx",
            ".xlsx",
            "Resultados_APRV2506000042",
            new ArrayList<Integer>() {{ add(14); }},
            new ArrayList<Integer>() {{ }}
                ),
    REPORTS_PUBLICATION_OFFERS("target/test-classes/attachments/"
            + "reportCompare/Reporte_de_ofertas_APRV2506000042.xlsx",
            ".xlsx",
            "Reporte_de_ofertas_APRV2506000042",
            new ArrayList<Integer>() {{ add(14); }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_CASES_DOCUMENTS("target/test-classes/attachments/"
            + "reportCompare/Reporte_casos_documentos_model2000.xlsx",
            ".xlsx",
            "Reporte_casos_documentos_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ add(2); }}
    ),
    REPORTS_CASES_VEHICLE("target/test-classes/attachments/"
            + "reportCompare/Reporte_casos_vehicle.xlsx",
            ".xlsx",
            "Reporte_casos",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ add(2); }}
    ),
    REPORTS_CASES_VARIOUS("target/test-classes/attachments/"
            + "reportCompare/Reporte_casos_Diversos.xlsx",
            ".xlsx",
            "Reporte_casos",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ add(2); }}
    ),
    REPORTS_PUBLICATION_VIEWS("target/test-classes/attachments/"
            + "reportCompare/Reporte_publicacion_vistas.xlsx",
            ".xlsx",
            "Reporte_vistas_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ add(2); }}
    ),
    REPORTS_INVENTORY_GENERAL("target/test-classes/attachments/"
            + "reportCompare/Reporte_entradas_al_inventario_general.xlsx",
            ".xlsx",
            "Reporte entradas al",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_INVENTORY_EXITS("target/test-classes/attachments/"
            + "reportCompare/Reporte_salidas_del_inventario.xlsx",
            ".xlsx",
            "Reporte salidas del",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_INVENTORY_PENDING_ENTRY_VEHICLES("target/test-classes/attachments/"
            + "reportCompare/Reporte_vehiculos_por_ingresar.xlsx",
            ".xlsx",
            "Reporte Vehículos por ingresar",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_PAYMENT_REPORT_ADMIN_VEH("target/test-classes/attachments/"
            + "reportCompare/Reporte_pagos_veh_admin.xlsx",
            ".xlsx",
            "Reporte_pagos",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ add(2); }}
    ),
    REPORTS_AWARDING_REPORT_BUYER_VEH("target/test-classes/attachments/"
            + "reportCompare/Reporte_adjudicaciones_Buyer_vehiculos.xlsx",
            ".xlsx",
            "Reporte_adjudicaciones_vehiculos",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ add(2); }}
    ),
    // Transfer reports: app download is always "Reporte de traslados_<ddMMyyyy>.xlsx" (expectedName below).
    // Baselines (fileNamePath) differ per TC because Excel columns/content change per filter.
    REPORTS_TRANSFERS_GLOBAL("target/test-classes/attachments/"
            + "reportCompare/Reporte_traslados_global.xlsx",
            ".xlsx",
            "Reporte de traslados_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_TRANSFERS_BY_INSURER("target/test-classes/attachments/"
            + "reportCompare/Reporte_traslados_por_aseguradora.xlsx",
            ".xlsx",
            "Reporte de traslados_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_TRANSFERS_BY_PROVIDER("target/test-classes/attachments/"
            + "reportCompare/Reporte_traslados_por_proveedor.xlsx",
            ".xlsx",
            "Reporte de traslados_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_TRANSFERS_BY_BRANCH("target/test-classes/attachments/"
            + "reportCompare/Reporte_traslados_por_sucursal.xlsx",
            ".xlsx",
            "Reporte de traslados_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_TRANSFERS_BY_CITY("target/test-classes/attachments/"
            + "reportCompare/Reporte_traslados_por_ciudad.xlsx",
            ".xlsx",
            "Reporte de traslados_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_TRANSFERS_BY_ORIGIN("target/test-classes/attachments/"
            + "reportCompare/Reporte_traslados_por_origen.xlsx",
            ".xlsx",
            "Reporte de traslados_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_TRANSFERS_BY_MANUFACTURER("target/test-classes/attachments/"
            + "reportCompare/Reporte_traslados_por_fabricante.xlsx",
            ".xlsx",
            "Reporte de traslados_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_TRANSFERS_BY_TYPE("target/test-classes/attachments/"
            + "reportCompare/Reporte_traslados_por_tipo.xlsx",
            ".xlsx",
            "Reporte de traslados_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_TRANSFERS_BY_MODEL("target/test-classes/attachments/"
            + "reportCompare/Reporte_traslados_por_modelo.xlsx",
            ".xlsx",
            "Reporte de traslados_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_TRANSFERS_BY_UNIT_TYPE("target/test-classes/attachments/"
            + "reportCompare/Reporte_traslados_por_tipo_unidad.xlsx",
            ".xlsx",
            "Reporte de traslados_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_TRANSFERS_BY_DATES("target/test-classes/attachments/"
            + "reportCompare/Reporte_traslados_por_fechas.xlsx",
            ".xlsx",
            "Reporte de traslados_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    ),
    REPORTS_TRANSFERS_BY_STATUS("target/test-classes/attachments/"
            + "reportCompare/Reporte_traslados_por_estatus.xlsx",
            ".xlsx",
            "Reporte de traslados_",
            new ArrayList<Integer>() {{ }},
            new ArrayList<Integer>() {{ }}
    );

    private final String fileNamePath;
    private final String extensionFile;
    private final String expectedName;
    private final List<Integer> columns;
    private final List<Integer> rows;

    Reports(String fileNamePath, String extensionFile, String expectedName, List<Integer> columns, List<Integer> rows) {
        this.fileNamePath = fileNamePath;
        this.extensionFile = extensionFile;
        this.expectedName = expectedName;
        this.columns = columns;
        this.rows = rows;
    }

    public String getFileNamePath() {
        return this.fileNamePath;
    }

    public String getExtensionFile() {
        return this.extensionFile;
    }

    public String getExpectedName() {
        return this.expectedName;
    }

    public List<Integer> getColumns() {
        return this.columns;
    }

    public List<Integer> getRows() {
        return this.rows;
    }
}
