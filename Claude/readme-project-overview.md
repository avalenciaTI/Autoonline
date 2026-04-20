# sg-qa-autoonline-mx — Visión General del Proyecto

## Qué es este proyecto

Framework de automatización QA para la aplicación web **AutoOnline México** de Solera Inc.
AutoOnline es una plataforma de gestión de vehículos siniestrados: permite crear casos de vehículos, publicarlos, adjudicarlos a compradores, gestionar pagos y transferencias.

Este proyecto automatiza el flujo completo de la aplicación usando Selenium WebDriver con el patrón **Page Object Model**, integrándose con TestRail para gestión de casos de prueba.

---

## Stack tecnológico

| Capa | Tecnología | Notas |
|------|-----------|-------|
| Lenguaje | Java 11 | |
| Build | Maven | módulos separados: web y rest |
| Testing | TestNG | anotaciones `@Test`, `@BeforeClass`, `dependsOnMethods` |
| Browser automation | Selenium WebDriver | vía framework interno TAF (Solera) |
| REST testing | TAF REST | módulo `autoonline-rest/` |
| Utilidades | Lombok, AssertJ | `@Slf4j` para logs, `assertThat` para assertions |
| Reportes | ReportPortal + TestRail | integración opcional por flags Maven |
| Ejecución | Remote Selenium Grid | `-Dexec.env=remote.grid`, alta latencia por call |

---

## Módulos del proyecto

```
sg-qa-autoonline-mx/
├── autoonline-web/       ← Tests UI (Selenium + Page Object Model)  ← MÓDULO PRINCIPAL
└── autoonline-rest/      ← Tests de API REST
```

---

## Estructura interna de `autoonline-web/`

```
src/
├── main/java/.../behavior/
│   ├── pages/                          ← Todos los Page Objects
│   │   ├── WebTestBase.java            ← Clase base de todos los tests (factory methods)
│   │   ├── loginpage/                  ← LogInPage, LogOffPage
│   │   ├── menupage/                   ← MenuPage, CraneMenuPage
│   │   ├── casecreation/               ← IndividualRegistration, Documents, Photos
│   │   │   └── transfercase/           ← TransferIndividualRegistration, TransfersPage
│   │   ├── usercreation/               ← MassiveRegistrationUsers
│   │   │   └── individualregistration/ ← AdministratorMasterInter  ← CLASE PRINCIPAL DE USUARIOS
│   │   ├── publications/               ← PublicationCreation, Awardings, PublicationOnline
│   │   ├── payments/                   ← PaymentPage, PaymentStatus
│   │   ├── reports/                    ← ReportsCases, ReportsPublications, ReportsPayments
│   │   └── componentpages/             ← Helpers reutilizables
│   │       ├── CommonComponents.java   ← fillField, selectFromDropdown, dynamicWebElement
│   │       ├── CommonUsersFields.java  ← XPaths + @FindBy + getters de campos de usuario
│   │       ├── Buttons.java            ← click/jsClick de botones reutilizables
│   │       ├── CommonSearch.java       ← búsquedas avanzadas
│   │       ├── CompleteWebElement.java ← modelo: element + value + indexTab
│   │       ├── InvitationToBuyer.java  ← enum: INDIVIDUAL, MASSIVE
│   │       └── enums/                  ← CaseType, WorkFlowElements, AdjudicationStatus...
│   └── data/
│       ├── types/                      ← AolWebUser, Case, Vehicle, Workshop, Sinister
│       ├── tools/                      ← IdGenerator, TestDateGenerator
│       └── timeouts/                   ← Timeouts.java (constantes de espera)
│
└── test/java/.../cases/login/          ← Clases de test
    ├── TestLogInBasic.java             ← CLASE PRINCIPAL — login, usuarios, casos, docs, fotos
    ├── TestPublication.java            ← publicaciones
    ├── TestAwards.java                 ← adjudicaciones
    ├── TestPayments.java               ← pagos
    ├── TestTransfers.java              ← transferencias
    ├── TestReports.java                ← reportes
    └── SmokeTest.java                  ← smoke suite

test/resources/suites/
    ├── regressionSuite.xml             ← perfil: autoonline:web:regression
    ├── smokeSuite.xml                  ← perfil: autoonline:web:smoke (sanity)
    ├── paymentSuite.xml
    ├── publicationSuite.xml
    ├── reportsSuite.xml
    ├── transferSuite.xml
    └── awardsSuite.xml
```

---

## Cómo fluye una ejecución de test

```
1. Maven ejecuta la suite XML correspondiente al perfil
2. TestNG instancia la clase de test (extiende WebTestBase)
3. WebTestBase inicializa el browser via TAF (local o remote grid)
4. Cada @Test:
   a. Obtiene usuario   → this.users.getMasterUser()
   b. Login             → loginPage().logIn(user)
   c. Navega            → mainMenu().clickUsers()  /  mainMenu().clickCases() / etc.
   d. Ejecuta acción    → adminMasterInter().inviteIndividualBuyer(user)
   e. Assertion         → assertions().assertThat(result).as("MENSAJE").isTrue()
5. TAF captura screenshot en cada log().image(...)
6. Si está habilitado: reporta resultado a TestRail y/o ReportPortal
```

---

## Framework TAF (interno Solera) — lo más importante

TAF provee `BrowserPage` — clase base de todos los Page Objects. Sus métodos clave:

```java
// Esperas (siempre usar estas, nunca sleep solo)
waitForElementPresence(By locator, int timeout)       // espera en DOM
waitForElementVisibility(WebElement element)           // espera visible
waitForElementToBeClickable(WebElement, int timeout)   // espera clickable
waitForElementInvisibility(WebElement, int ms)         // espera que desaparezca

// Interacción
click(WebElement)                    // click normal
jsClick(WebElement)                  // click via JS — para botones en modales/overlays
sendKeys(WebElement, value)          // escribe texto DIRECTAMENTE (sin delay) — USAR ESTE
setValue(WebElement, value)          // escribe con 50ms/carácter — NO usar en automation
scrollTo(WebElement)                 // scroll hasta el elemento
jsExecuteScript(String, Object...)   // ejecuta JavaScript

// Queries
getElement(By locator)               // findElement con logging
```

> **Importante:** `sendKeys` vs `setValue`: sendKeys es directo (rápido), setValue tiene 50ms delay por carácter (lento). Siempre usar `sendKeys`.

---

## Clases Page Object más importantes

### `AdministratorMasterInter` — flujos de creación/gestión de usuarios
Maneja todos los flujos relacionados con usuarios: creación individual (Admin Master, Admin Interno, Aseguradora, Compradores Físicos/Morales, Proveedores Grúa) y la invitación de compradores.

Patrón interno:
- `fast*()` → llena formulario, retorna `List<CompleteWebElement>` con los valores guardados
- `individualRegistration*()` → orquesta: navega + llama fast*() + valida

### `CommonComponents` — helper central de formularios
- `fillField(element, value, list)` → detecta tipo input/div, llena, guarda en lista **con stepper**
- `fillField(element, value, list, 0)` → igual pero **sin stepper** (más rápido en remote grid)
- `selectFromDropdownText(element, text)` → solo para inline dropdowns
- `dynamicWebElement(xpath, "valor")` → reemplaza `?` en XPath en runtime

### `CommonUsersFields` — repositorio central de elementos de formularios de usuario
Contiene todas las constantes XPath, `@FindBy` y getters para campos de usuario. Siempre buscar aquí antes de crear nuevas constantes.

### `Buttons` — botones reutilizables
- `clickAcceptButton()` → click normal con wait
- `jsClickAcceptButton()` → click por JS para botones dentro de modales Ant Design
- `clickContinueBtn()`, `clickCancelBtn()`, `clickCloseBtn()`, etc.

---

## CompleteWebElement — patrón guardar/validar

Durante el fill de un formulario, cada campo se guarda en un `CompleteWebElement`:
```
WebElement  → referencia al elemento en el DOM
desiredValue → valor que se escribió
indexTab    → número de pestaña activa (del stepper) en ese momento
```

Después de crear el registro, se valida iterando la lista: para cada elemento se lee el valor actual del DOM y se compara con `desiredValue`. Si hay stepper, se navega a la pestaña correcta (`indexTab`) antes de leer.

En páginas **sin stepper** (ej. invitación), `indexTab = 0` y no hay navegación entre pestañas.

---

## Ant Design v3 — patrones específicos de la UI

AutoOnline usa Ant Design v3. Dos comportamientos críticos para automatización:

### Dropdowns
- **Inline**: opciones como `following-sibling::div > li` del trigger → usar `selectFromDropdownText`
- **Portal**: opciones en `<body>` con clase `ant-select-dropdown` → click en trigger + `dynamicWebElement` con `//li[contains(@class,'ant-select-dropdown-menu-item') and text()='?']`

### Modales
- El `div.ant-modal-wrap` tiene z-index alto → intercepta `click()` normal en botones dentro del modal
- Solución: `jsClick()` o `jsClickAcceptButton()` para el segundo Aceptar dentro del modal

---

## Flujos de negocio automatizados (estado actual)

| Módulo | Flujo | Clase Test | Estado |
|--------|-------|-----------|--------|
| Login | Login básico | TestLogInBasic | ✅ |
| Usuarios | Crear Admin Master/Interno, Aseguradora, Comprador Físico/Moral, Proveedor Grúa | TestLogInBasic | ✅ |
| Usuarios | Invitar comprador individual (CP022) | TestLogInBasic | ✅ |
| Casos | Crear caso vehículo, buscar, cargar docs, cargar imágenes | TestLogInBasic | ✅ |
| Casos | Crear caso transferencia | TestLogInBasic | ✅ |
| Publicaciones | Crear publicación diversa | TestLogInBasic | ✅ |
| Adjudicaciones | Flujo completo | TestAwards | ✅ |
| Pagos | Flujo completo | TestPayments | ✅ |
| Reportes | Reportes de casos, publicaciones, pagos | TestReports | ✅ |

---

## Usuarios de prueba disponibles

| Método | Rol | Cuándo usar |
|--------|-----|------------|
| `users.getMasterUser()` | Admin Master | Flujo principal (admin) |
| `users.getPhysicalBuyerUser()` | Comprador físico 1 | Flujos de comprador |
| `users.getPhysicalBuyerUser3()` | Comprador físico 3 | Login comprador, validar botones online |
| `users.getCraneUser()` | Proveedor grúa | Flujos de grúa/traslado |

Definidos en: `src/test/resources/data/users/default.properties`

---

## Generación de datos únicos

```java
IdGenerator.getNewVin()            // "1JKTS" + timestamp → VIN único por ejecución
IdGenerator.getNewPublicationId()  // "TestAutomation DIV YYMMDDHHMMSS"
generateDynamicEmail()             // email único (disponible en AdministratorMasterInter)
```

Usar en `@BeforeClass` para compartir entre tests de la misma clase.

---

## Comandos Maven principales

```bash
# Build sin tests
mvn clean install -DskipTests

# Debug (ejecutar un test específico, modo local)
mvn clean test -Denv=development -Dapp.config=development -Dexec.env=remote.grid \
  -Pautoonline:web:debug -Dtestrail.enabled=false -Daws.s3.enabled=false \
  -DtestRunName=AUTOONLINE_MX_AUTO_REGRESSION_TEST -Dbrowser=chrome -f pom.xml

# Regression completa
mvn clean test -Denv=development -Dapp.config=development -Dexec.env=remote.grid \
  -Pautoonline:web:regression -Dtestrail.enabled=false -Daws.s3.enabled=false \
  -DtestRunName=AUTOONLINE_MX_AUTO_REGRESSION_TEST -Dbrowser=chrome -f pom.xml
```
