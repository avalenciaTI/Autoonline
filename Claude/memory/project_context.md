---
name: Project context
description: Stack, estructura y patrones clave del proyecto sg-qa-autoonline-mx (autoonline-web)
type: project
---

## Stack
- Java 11, Maven, TestNG, Selenium WebDriver vía TAF (framework interno Solera)
- Lombok (@Slf4j), AssertJ para assertions
- Remote grid: `-Dexec.env=remote.grid` — cada WebDriver call tiene latencia de red (~3-5s en grid lento)

## Estructura clave
- Page Objects: `src/main/java/.../behavior/pages/`
- Tests: `src/test/java/.../cases/login/TestLogInBasic.java` (clase principal de referencia)
- Clases de test heredan de `WebTestBase`
- Componentes reutilizables en `componentpages/` (CommonComponents, CommonUsersFields, Buttons, etc.)
- Datos de prueba en `behavior/data/` (tipos: AolWebUser, Case, Vehicle...)

## Patrones fundamentales
- **XPath dinámico**: `"//td[text()='?']"` con `dynamicWebElement(xpath, param)` — reemplaza `?` en runtime
- **CompleteWebElement**: guarda webElement + desiredValue + indexTab durante fill, para validar posterior contra DOM
- **Assertion patrón**: `assertions().assertThat(pageObject().metodo()).as("MENSAJE").isTrue()`
- Los métodos de page object retornan `boolean` — casi siempre `true` hardcodeado. El test falla por excepción, no por `false`
- `@BeforeClass` para generar VIN compartido entre tests de la misma clase
- `dependsOnMethods` para encadenar tests dependientes

## Framework TAF (interno Solera) — métodos disponibles en BrowserPage
```java
// Esperas
waitForElementPresence(By locator, int timeout)      // espera que exista en DOM
waitForElementVisibility(WebElement element)          // espera que sea visible
waitForElementVisibility(WebElement element, int ms)  // con timeout custom
waitForElementToBeClickable(WebElement, int timeout)  // espera clickable
waitForElementInvisibility(WebElement, int ms)        // espera que desaparezca

// Clicks
click(WebElement)            // click normal Selenium
jsClick(WebElement)          // click via JavaScript — bypass de overlays/modales
scrollTo(WebElement)         // scroll hasta el elemento

// Interacción
sendKeys(WebElement, value)  // escribe texto — llama WebElement.sendKeys() directamente (sin delay)
setValue(WebElement, value)  // escribe texto con 50ms de delay por carácter — NO usar para tests rápidos

// Queries
getElement(By locator)       // findElement con logging
getSpecificAttributeValue(WebElement, String attr) // getAttribute con logging
jsExecuteScript(String script, Object... args)     // ejecuta JS en el browser

// Utilidades
takeScreenshot()             // captura pantalla
log().image(String, screenshot)  // loguea imagen en reporte
sleep(int ms)                // pausa — usar solo cuando no hay otra opción
```

**IMPORTANTE sobre sendKeys vs setValue:**
- `sendKeys` → llama `WebElement.sendKeys()` directamente, sin delay — usar siempre
- `setValue` → 50ms de delay por carácter (simula humano) — NO usar en flujos automáticos

## Page Objects principales (en WebTestBase)
- `loginPage()` → LogInPage
- `mainMenu()` → MenuPage
- `registrationMenu()` → RegistrationMenu
- `caseSearch()` → CaseSearch
- `caseIndividualRegistration()` → IndividualRegistration
- `transferIndividualRegistration()` → TransferIndividualRegistration
- `adminMasterInter()` → AdministratorMasterInter
- `publicationCreation()` → PublicationCreation
- `awardings()` → Awardings
- `documents()` → Documents
- `photos()` → Photos
- `reportsPublications()`, `reportsPayments()`

## Usuarios disponibles
- `users.getMasterUser()` — Admin/Master
- `users.getPhysicalBuyerUser()` / `getPhysicalBuyerUser3()` — Comprador físico
- `users.getCraneUser()` — Proveedor grúa

## Generación de datos únicos
- `IdGenerator.getNewVin()` → "1JKTS" + timestamp
- `IdGenerator.getNewPublicationId()` → "TestAutomation DIV YYMMDDHHMMSS"
- `generateDynamicEmail()` → email único por ejecución (disponible en AdministratorMasterInter)

## Convenciones de nombres
- Clase test: `Test[Feature].java`
- Método test: `tc[N]_[descripcionCamelCase]()`
- Annotation: `@TmsData.Tc(tcId = X, tcName = "CP0XX_...", tcType = TcType.REGRESSION)`
- Assertion message: MAYÚSCULAS descriptivas — ej. `"BUYER INVITATION SUCCESS"`

## Comandos Maven de ejecución
```bash
# Debug (ejecutar test específico localmente)
mvn clean test -Denv=development -Dapp.config=development -Dexec.env=remote.grid \
  -Pautoonline:web:debug -Dtestrail.enabled=false -Daws.s3.enabled=false \
  -DtestRunName=AUTOONLINE_MX_AUTO_REGRESSION_TEST -Dbrowser=chrome -f pom.xml

# Regression completa
mvn clean test -Denv=development -Dapp.config=development -Dexec.env=remote.grid \
  -Pautoonline:web:regression -Dtestrail.enabled=false -Daws.s3.enabled=false \
  -DtestRunName=AUTOONLINE_MX_AUTO_REGRESSION_TEST -Dbrowser=chrome -f pom.xml
```

## Why / How to apply
**Why:** El framework TAF es interno de Solera y no está documentado públicamente — toda la lógica de browser, esperas y utilidades viene de ahí. El JAR está en `.m2/repository/com/solera/global/qa/taf/taf-web/2.0.0/`.
**How to apply:** Seguir siempre los patrones existentes en `TestLogInBasic.java` como referencia canónica. Antes de escribir cualquier método, verificar si ya existe en `CommonComponents`, `Buttons`, `CommonUsersFields`, `CommonSearch` o `MenuPage`.
