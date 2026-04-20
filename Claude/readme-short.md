# AutoOnline Web — Guía Rápida para Tests

## Stack

| Capa | Tecnología |
|------|-----------|
| Lenguaje | Java |
| Framework de Tests | TestNG |
| Automatización Browser | Selenium (via TAF interno de Solera) |
| Build | Maven |
| Reportes | ReportPortal + TestRail |

---

## Estructura del proyecto

```
autoonline-web/
├── src/main/java/.../behavior/
│   ├── pages/                  ← Page Objects (lógica de UI)
│   │   ├── WebTestBase.java    ← Clase base de todos los tests
│   │   ├── loginpage/
│   │   ├── casecreation/
│   │   ├── publications/
│   │   ├── payments/
│   │   ├── reports/
│   │   └── componentpages/     ← Helpers reutilizables (búsqueda, campos, etc.)
│   └── data/
│       ├── types/              ← Modelos: AolWebUser, Case, Vehicle...
│       └── tools/              ← Utilerias: IdGenerator, TestDateGenerator
│
└── src/test/java/.../cases/login/
    ├── TestLogInBasic.java     ← Login + creación de casos
    ├── TestPublication.java    ← Publicaciones
    ├── TestAwards.java         ← Adjudicaciones
    ├── TestPayments.java       ← Pagos
    ├── TestTransfers.java      ← Transferencias
    ├── TestReports.java        ← Reportes
    └── SmokeTest.java          ← Smoke suite
```

---

## Cómo se escribe un test

### 1. Heredar de `WebTestBase`
Da acceso a: browser, page objects, datos de prueba y assertions.

```java
public class TestMiFeature extends WebTestBase {
```

### 2. Anotar el test
```java
@Test(priority = 10)
@TmsData.Tc(tcId = 160896, tcName = "Descripción del caso", tcType = TcType.REGRESSION)
public void tc10_miTest() {
```
- `priority` → orden de ejecución (menor = primero)
- `tcId` → ID en TestRail
- `tcType` → `REGRESSION` o `SMOKE`

### 3. Obtener usuario y hacer login
```java
AolWebUser master = this.users.getMasterUser();
loginPage().logIn(master);
```

### 4. Navegar con page objects
```java
mainMenu().clickCases();
registrationMenu().consultCases();
```

### 5. Verificar con assertions
```java
assertions().assertThat(caseSearch().generalSearch(CaseType.VEHICLES, vin))
    .as("CASE SEARCH SUCCESS").isTrue();
```

---

## Test completo de ejemplo

```java
@Slf4j
public class TestMiFeature extends WebTestBase {

    private static String vin;

    @BeforeClass
    public static void setup() {
        vin = IdGenerator.getNewVin();  // genera: "1JKTS" + timestamp
    }

    @Test(priority = 1)
    @TmsData.Tc(tcId = 999001, tcName = "Crear caso de vehículo", tcType = TcType.REGRESSION)
    public void tc01_crearCaso() {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);

        assertions().assertThat(
            caseIndividualRegistration().vehicleIndividualRegistration(vin, caseData)
        ).as("CASO CREADO OK").isTrue();
    }

    @Test(priority = 2, dependsOnMethods = "tc01_crearCaso")
    @TmsData.Tc(tcId = 999002, tcName = "Buscar caso creado", tcType = TcType.REGRESSION)
    public void tc02_buscarCaso() {
        AolWebUser master = this.users.getMasterUser();
        loginPage().logIn(master);
        mainMenu().clickCases();
        registrationMenu().consultCases();

        assertions().assertThat(caseSearch().generalSearch(CaseType.VEHICLES, vin))
            .as("CASO ENCONTRADO OK").isTrue();
    }
}
```

---

## Usuarios disponibles

| Método | Rol |
|--------|-----|
| `users.getMasterUser()` | Administrador / Master |
| `users.getPhysicalBuyerUser()` | Comprador físico 1 |
| `users.getPhysicalBuyerUser3()` | Comprador físico 3 |
| `users.getCraneUser()` | Usuario grúa |

Definidos en: `src/test/resources/data/users/default.properties`

---

## Page Objects principales

| Método en WebTestBase | Qué hace |
|-----------------------|----------|
| `loginPage()` | Login / Logout |
| `mainMenu()` | Menú principal (clickCases, clickPayments…) |
| `registrationMenu()` | Submenú de registro |
| `caseSearch()` | Búsqueda de casos |
| `caseIndividualRegistration()` | Crear caso de vehículo |
| `photos()` | Cargar fotos al caso |
| `documents()` | Cargar/validar documentos |
| `publicationCreation()` | Crear publicación |
| `publicationOnline()` | Publicación en línea |
| `awardings()` | Adjudicaciones |
| `generationofTransfer()` | Generar transferencia |
| `reportsPublications()` | Reporte de publicaciones |
| `reportsPayments()` | Reporte de pagos |
| `adminMasterInter()` | Crear usuarios admin |

---

## Generación de datos únicos

```java
String vin           = IdGenerator.getNewVin();             // "1JKTS1234567890"
String publicationId = IdGenerator.getNewPublicationId();   // "TestAutomation DIV 170226120000"
```

Usar en `@BeforeClass` para compartir entre métodos del mismo test.

---

## Dependencias entre tests

```java
@Test(priority = 2, dependsOnMethods = "tc01_crearCaso")
public void tc02_buscarCaso() { ... }
// Si tc01 falla → tc02 se salta automáticamente
```

---

## Suites disponibles

| Perfil Maven | Archivo | Contenido |
|---|---|---|
| `autoonline:web:regression` | regressionSuite.xml | Todos los tests de regresión |
| `autoonline:web:smoke` | smokeSuite.xml | Solo SmokeTest |
| `autoonline:web:debug` | transferSuite.xml | Solo transferencias |

### Cómo correr

```bash
# Regression
mvn clean test -Pautoonline:web:regression -Denv=development \
  -Dapp.config=development -Dexec.env=remote.grid \
  -Dtestrail.enabled=false -Daws.s3.enabled=false \
  -Dbrowser=chrome -f autoonline-web/pom.xml

# Smoke
mvn clean test -Pautoonline:web:smoke -Denv=development \
  -Dapp.config=development -Dexec.env=remote.grid \
  -Dtestrail.enabled=false -Daws.s3.enabled=false \
  -Dbrowser=chrome -f autoonline-web/pom.xml
```

---

## Convenciones de nombres

- Clase: `Test[Feature].java` → `TestPayments`, `TestAwards`
- Método: `tc[N]_[descripcionCamelCase]()` → `tc01_crearCaso`, `tc30_consultarVehiculo`
- Assertion message: mayúsculas descriptivas → `"CASO CREADO OK"`
- Logs: `log.info("Mensaje con variable: {}", valor)`

---

## Patrón de fill + validate (Page Objects)

### CON stepper (páginas con pasos numerados 1-2-3, ej. creación de casos/usuarios)
```java
CommonComponents components = new CommonComponents();
CommonUsersFields field = new CommonUsersFields();
List<CompleteWebElement> storedValues = new ArrayList<>();

storedValues = components.fillField(field.getNameField(), "valor", storedValues);
storedValues = components.fillField(field.getSurNameField(), "valor", storedValues);
new Buttons().clickContinueBtn();
// Validación posterior:
Integer correct = new CommonComponents().validateCaseCreation(storedValues);
```

### SIN stepper (páginas sin pasos, ej. invitación de compradores)
```java
// Usar overload con int indexTab = 0 — no busca ant-steps en el DOM
// 2x más rápido en remote grid (evita 2 findElements extra por campo)
storedValues = components.fillField(field.getInvitationFirstnameField(), "AutomationBuyer", storedValues, 0);
storedValues = components.fillField(field.getInvitationSurnameField(), "Automation", storedValues, 0);
```
> ⚠️ Usar el overload sin `int` en página sin stepper lanza `NoSuchElementException` en `getIndex()`

---

## Dropdowns Ant Design — dos tipos (distinguirlos antes de automatizar)

### Inline dropdown — opciones como siblings del trigger
```java
// Usar selectFromDropdownText — funciona para este tipo
components.selectFromDropdownText(field.getMoralRegimeTurnField(), "valor");
```

### Portal dropdown — opciones renderizadas en el `<body>` (clase `.ant-select-dropdown`)
```java
// NO usar selectFromDropdownText — no encuentra las opciones
// Patrón: click para abrir + dynamicWebElement para seleccionar
click(field.getInvitationSendTypeField());
sleep(Timeouts.SHORT_TIME);
click(new CommonComponents().dynamicWebElement(
    CommonUsersFields.INVITATION_DROPDOWN_OPTION, "Individual"));
```
> Para identificar el tipo: inspeccionar DOM en Chrome → si las opciones aparecen pegadas al `<body>` con clase `ant-select-dropdown` → es portal.

---

## Click en botones dentro de modales Ant Design

```java
// El ant-modal-wrap intercepta click() normal → usar jsClick
Buttons buttons = new Buttons();
waitForElementVisibility(buttons.getAcceptButton());
buttons.clickAcceptButton();              // fuera del modal — click normal OK
waitForElementVisibility(field.getInvitationModalField());
buttons.jsClickAcceptButton();            // dentro del modal — requiere jsClick
```

---

## Esperas antes de interactuar (remote grid — regla crítica)

```java
// SIEMPRE esperar presencia/visibilidad antes del primer click en página nueva
waitForElementPresence(By.xpath(ALGUNA_CONSTANTE), Timeouts.LOAD_ELEMENT);
click(getElement(By.xpath(ALGUNA_CONSTANTE)));

// Para links de navegación
waitForElementPresence(By.xpath(MassiveRegistrationUsers.INVITE_BUYERS), Timeouts.LOAD_ELEMENT);
click(getElement(By.xpath(MassiveRegistrationUsers.INVITE_BUYERS)));
```
> Nunca usar solo `sleep(N)` — es frágil. Siempre `waitForElementPresence` o `waitForElementVisibility`.

---

## Upload de archivos (input[type='file'])

```java
// MAL — abre diálogo nativo del OS que Selenium no puede controlar
click(field.getInvitationAttachButtonField());

// BIEN — sendKeys directo al input[type='file'] sin click previo
String absolutePath = new java.io.File(CommonUsersFields.MASSIVE_CSV_FILE_PATH).getAbsolutePath();
getElement(By.xpath(CommonUsersFields.INVITATION_FILE_INPUT)).sendKeys(absolutePath);
```

**Ruta de archivos de prueba:** usar ruta relativa al módulo en la constante, convertir a absoluta en runtime:
```java
// En CommonUsersFields (constante corta y portable)
public static final String MASSIVE_CSV_FILE_PATH =
    "src/test/resources/attachments/reportCompare/Registro_Masivo_BuyersInvitation.xlsx";

// En el método (conversión en runtime)
String absolutePath = new java.io.File(CommonUsersFields.MASSIVE_CSV_FILE_PATH).getAbsolutePath();
```

---

## Dónde poner los XPaths y elementos UI (regla antes de escribir código)

| Tipo de elemento | Constante XPath | `@FindBy` + getter |
|---|---|---|
| Campo de formulario de usuario/invitación | `CommonUsersFields.java` | `CommonUsersFields.java` |
| Botón reutilizable (Aceptar, Cancelar, etc.) | `Buttons.java` | `Buttons.java` |
| Elemento específico de un flujo de negocio | Clase Page Object que lo usa | Misma clase Page Object |
| Menú / navegación | `MenuPage.java` | `MenuPage.java` |
