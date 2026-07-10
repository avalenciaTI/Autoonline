# Baselines de reportes Excel

Coloca aquí los archivos `.xlsx` de referencia para comparación.

## Reportes de traslados

| Archivo baseline | Test case |
|------------------|-----------|
| `Reporte_traslados_global.xlsx` | CP060 |
| `Reporte_traslados_por_aseguradora.xlsx` | CP061 |
| `Reporte_traslados_por_proveedor.xlsx` | CP062 |
| `Reporte_traslados_por_sucursal.xlsx` | CP063 |
| `Reporte_traslados_por_ciudad.xlsx` | CP064 |
| `Reporte_traslados_por_origen.xlsx` | CP065 |
| `Reporte_traslados_por_fabricante.xlsx` | CP066 |
| `Reporte_traslados_por_tipo.xlsx` | CP067 |
| `Reporte_traslados_por_modelo.xlsx` | CP068 |
| `Reporte_traslados_por_tipo_unidad.xlsx` | CP069 |
| `Reporte_traslados_por_fechas.xlsx` | CP070 |
| `Reporte_traslados_por_estatus.xlsx` | CP071 |

## Cómo crear un baseline

1. Ejecuta el test (sin baseline el test pasa pero solo valida descarga).
2. Abre la copia preservada en:
   `target/test-classes/downloads/preserved/REPORTS_TRANSFERS_BY_ORIGIN/Reporte de traslados_11062026.xlsx`
   (nombre real con fecha del día; carpeta según el enum del reporte).
3. Copia ese archivo aquí con el nombre de la tabla (ej. `Reporte_traslados_por_origen.xlsx`).
4. Vuelve a ejecutar el test para activar la comparación Excel.

## Importante

- **No** copies baselines en `target/test-classes/` — `mvn clean` borra esa carpeta.
- La app **siempre** descarga como `Reporte de traslados_DDMMYYYY.xlsx` (fecha del día, ej. `Reporte de traslados_11062026.xlsx`).
- Ese nombre **no cambia** según filtro ni TC; solo cambia el **contenido** del Excel.
- Los baselines en esta carpeta tienen **otro nombre** (ej. `Reporte_traslados_por_origen.xlsx`) porque guardan la referencia por TC.
