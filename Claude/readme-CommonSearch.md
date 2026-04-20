# CommonSearch — Guía de uso en test cases

**Archivo:** `autoonline-web/src/main/java/.../componentpages/CommonSearch.java`

`CommonSearch` es una clase helper para **búsquedas avanzadas** en publicaciones, adjudicaciones y pagos. No está expuesta como factory en `WebTestBase`, por lo que se instancia directamente.

---

## Métodos disponibles

| Método | Para qué sirve |
|--------|----------------|
| `swapAdvancedSearch(SearchType)` | Alterna entre búsqueda general y avanzada |
| `selectCaseType(ICaseType, WorkFlowElements)` | Selecciona el tipo de caso en el filtro (con contexto de flujo) |
| `selectCaseTypeTst(ICaseType)` | Selecciona tipo de caso (versión simple, sin contexto) |
| `selectAwardingStatus(String status, String role)` | Filtra adjudicaciones por estado |
| `selectInsurer(Insurers insurer)` | Selecciona aseguradora por checkbox |
| `setPublicationName(String name)` | Escribe el nombre de publicación en el filtro |
| `setSinister(String sinister)` | Escribe el número de siniestro en el filtro |
| `setStartDate(String date)` | Pone fecha de inicio |
| `setEndDate(String date)` | Pone fecha de fin |
| `search()` | Hace clic en el botón Buscar |

---

## Enums requeridos

### `SearchType` — modo de búsqueda

```java
SearchType.ADVANCED_SEARCH   // texto del botón: "Búsqueda avanzada"  → cambia a avanzada
SearchType.GENERAL_SEARCH    // texto del botón: "Búsqueda general"   → cambia a general
```

> **Cómo funciona `swapAdvancedSearch`:** lee el texto actual del botón `swap-search-button`.
> Si el texto coincide con el `SearchType` que pasas → hace clic para cambiar el modo.
> Si ya está en ese modo → no hace nada.

### `WorkFlowElements` — identifica en qué pantalla estás

```java
WorkFlowElements.PUBLICATION   // pantalla de Publicaciones  (id contiene "adverts")
WorkFlowElements.AWARDING      // pantalla de Adjudicaciones (id contiene "awardings")
WorkFlowElements.PAYMENT       // pantalla de Pagos          (id contiene "payments")
```

### `CaseType` (enum de la carpeta `enums/`) — tipo de caso

```java
CaseType.VEHICLES   // "VEHÍCULOS"
CaseType.VARIOUS    // "DIVERSOS"
```

> **Ojo:** hay DOS clases `CaseType` en el proyecto:
> - `componentpages.CaseType` — usada en `CaseSearch` (búsqueda de casos)
> - `componentpages.enums.CaseType` — implementa `ICaseType`, usada en `CommonSearch`
>
> Para `CommonSearch` usa la de `enums`.

---

## Cómo instanciar

`CommonSearch` **no está en `WebTestBase`** como factory method.
Se crea directamente dentro del test o del page object:

```java
CommonSearch commonSearch = new CommonSearch();
```

---

## Ejemplo de test case usando `CommonSearch`

### Escenario: buscar una publicación de tipo "Diversos" con búsqueda avanzada

```java
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.SearchType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.CaseType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.WorkFlowElements;

@Test(priority = 50)
@TmsData.Tc(tcId = 999050, tcName = "Buscar publicación Diversos con búsqueda avanzada", tcType = TcType.REGRESSION)
public void tc50_searchPublicationAdvanced() {
    AolWebUser master = this.users.getMasterUser();
    loginPage().logIn(master);

    // 1. Navegar al menú de publicaciones
    mainMenu().clickPublications();    // abre el menú de publicaciones
    // (o el método que lleve a la pantalla de búsqueda de publicaciones)

    CommonSearch commonSearch = new CommonSearch();

    // 2. Cambiar a búsqueda avanzada
    commonSearch.swapAdvancedSearch(SearchType.ADVANCED_SEARCH);

    // 3. Seleccionar tipo de caso en la pantalla de Publicaciones
    commonSearch.selectCaseType(CaseType.VARIOUS, WorkFlowElements.PUBLICATION);

    // 4. Llenar filtros adicionales
    commonSearch.setSinister("TESTTRANSFERAUTOAMTED");

    // 5. Ejecutar búsqueda
    commonSearch.search();
}
```

---

## Ejemplo: cambiar de avanzada a general

Si en algún punto necesitas volver al modo de búsqueda simple:

```java
CommonSearch commonSearch = new CommonSearch();
commonSearch.swapAdvancedSearch(SearchType.GENERAL_SEARCH);
```

---

## Ejemplo: filtrar adjudicaciones por estado

```java
CommonSearch commonSearch = new CommonSearch();

// Para usuario master usa un selector distinto que para comprador
commonSearch.selectAwardingStatus("Pendiente por adjuntar", "master");

// Para usuario comprador:
commonSearch.selectAwardingStatus("Pendiente por adjuntar", "buyer");

commonSearch.search();
```

---

## Ejemplo: filtrar por nombre de publicación y fechas

```java
CommonSearch commonSearch = new CommonSearch();

commonSearch.swapAdvancedSearch(SearchType.ADVANCED_SEARCH);
commonSearch.setPublicationName("TestAutomation DIV 250226120000");
commonSearch.setStartDate("01/01/2025");
commonSearch.setEndDate("31/12/2025");
commonSearch.search();
```

---

## `swapAdvancedSearch` vs `advanceSearch` en `CaseSearch`

| | `CommonSearch.swapAdvancedSearch` | `CaseSearch.advanceSearch` |
|---|---|---|
| Dónde se usa | Publicaciones, adjudicaciones, pagos | Búsqueda de casos |
| Cómo se activa | `new CommonSearch().swapAdvancedSearch(SearchType.ADVANCED_SEARCH)` | `caseSearch().advanceSearch(CaseType.VEHICLES)` |
| Diferencia interna | Lee el texto del botón con `By.id("swap-search-button")` | Hace lo mismo pero internamente llama a `CommonSearch` |
| Selecciona tipo de caso | No (se hace con `selectCaseType` por separado) | Sí, lo selecciona antes de cambiar el modo |

> **Nota:** desde la refactorización, `CaseSearch.advanceSearch()` ya usa `CommonSearch.swapAdvancedSearch()` internamente. Son equivalentes para búsqueda de casos.

---

## Flujo completo típico con `CommonSearch`

```
1. Navegar a la pantalla de búsqueda
       mainMenu().clickPublications() / clickAwardings() / clickPayments()

2. Cambiar a búsqueda avanzada (si necesitas filtros extra)
       new CommonSearch().swapAdvancedSearch(SearchType.ADVANCED_SEARCH)

3. Seleccionar tipo de caso
       commonSearch.selectCaseType(CaseType.VARIOUS, WorkFlowElements.PUBLICATION)

4. Llenar filtros
       commonSearch.setSinister("...")
       commonSearch.setPublicationName("...")
       commonSearch.setStartDate("...")
       commonSearch.setEndDate("...")

5. Buscar
       commonSearch.search()

6. Verificar resultado con assertions
       assertions().assertThat(...).isTrue()
```

---

## Imports necesarios en tu test

```java
import com.solera.global.qa.template.web.behavior.pages.componentpages.CommonSearch;
import com.solera.global.qa.template.web.behavior.pages.componentpages.SearchType;
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.CaseType;       // para VARIOUS/VEHICLES
import com.solera.global.qa.template.web.behavior.pages.componentpages.enums.WorkFlowElements; // para PUBLICATION/AWARDING/PAYMENT
```
