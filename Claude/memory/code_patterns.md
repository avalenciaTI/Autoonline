---
name: Code patterns and conventions
description: Nomenclatura, estructura de Page Objects, patrones de código y métodos existentes — para no duplicar ni inventar
type: project
---

## Jerarquía de clases
- `BrowserPage` (TAF) → todos los Page Objects heredan de aquí
- `WebTestBase` → todos los Tests heredan de aquí (provee factory methods)
- Tests NO instancian Page Objects directamente — usan los factory methods de WebTestBase

## Separación de responsabilidades (CRÍTICO)
- **Locators (XPaths/IDs)**: constantes `public/private static final String` en la clase Page Object o en `CommonUsersFields`
- **Elementos UI**: `@FindBy` en la clase correspondiente + getter público `getXxx()` que retorna `WebElement`
- **Lógica de flujo**: métodos del Page Object que orquestan los pasos
- **Tests**: solo llaman métodos de page objects + assertions, NO lógica UI directa

## Nomenclatura de constantes XPath
```java
private static final String BUTTON_LOGIN = "btnLogin";                   // por id
private static final String ACCEPT_BUTTON = "//span[text()='Aceptar']/parent::button";
private static final String SEARCH_DYNAMIC = "//td[text()='?']";         // con placeholder
private static final String USER_EMAIL = "//input[contains(@id,'user_email')]";
```
- Siempre MAYÚSCULAS con _ (SCREAMING_SNAKE_CASE)
- XPath dinámico usa `?` como placeholder → `dynamicWebElement(xpath, param)`
- Constantes de campos de usuario/invitación van en `CommonUsersFields.java`, NO en la clase que las usa

## Dónde poner los XPaths (CRÍTICO — decidir antes de escribir)
| Tipo de elemento | Dónde va la constante | Dónde va el @FindBy y getter |
|---|---|---|
| Campo de formulario de usuario | `CommonUsersFields.java` | `CommonUsersFields.java` |
| Botón reutilizable (Aceptar, Cancelar, etc.) | `Buttons.java` | `Buttons.java` |
| Elemento específico de un flujo | Clase Page Object que lo usa | Misma clase Page Object |
| Menú/navegación | `MenuPage.java` | `MenuPage.java` |

## Nomenclatura de métodos
```
get[Element]()            → retorna WebElement sin interactuar
click[Element]()          → hace clic normal
jsClick[Element]()        → hace clic por JavaScript (para elementos en modales/overlays)
is[Condition]()           → retorna boolean (validación de estado)
fast[X]()                 → llena un formulario, retorna List<CompleteWebElement>
individual[X](user)       → flujo completo: navega + llena + valida (retorna boolean)
```

## Nomenclatura de tests
```java
public class Test[Feature] extends WebTestBase {
    @Test(priority = N)
    @TmsData.Tc(tcId = XXXXX, tcName = "CP0XX_Descripcion", tcType = TcType.REGRESSION)
    public void tcNN_[descripcionCamelCase]() { ... }
}
```

## Patrón de un test completo
```java
AolWebUser master = this.users.getMasterUser();
loginPage().logIn(master);
assertions().assertThat(adminMasterInter().miMetodo(master))
        .as("MENSAJE EN MAYUSCULAS").isTrue();
```

## Patrón de fill con stepper (flujos CON ant-steps — ej. creación de casos/usuarios)
```java
CommonComponents components = new CommonComponents();
CommonUsersFields field = new CommonUsersFields();
List<CompleteWebElement> storedValues = new ArrayList<>();

storedValues = components.fillField(field.getNameField(), "valor", storedValues);
storedValues = components.fillField(field.getSurNameField(), "valor", storedValues);
// fillField() detecta tipo input/div, llena, y guarda indexTab del stepper activo
```

## Patrón de fill SIN stepper (flujos sin ant-steps — ej. invitación de compradores)
```java
CommonComponents components = new CommonComponents();
CommonUsersFields field = new CommonUsersFields();
List<CompleteWebElement> storedValues = new ArrayList<>();

// Usar el overload con int indexTab para NO llamar getIndex() — evita round-trips al grid
storedValues = components.fillField(field.getInvitationFirstnameField(), "valor", storedValues, 0);
storedValues = components.fillField(field.getInvitationSurnameField(), "valor", storedValues, 0);
// El 0 es el indexTab fijo — no busca stepper en el DOM
```
**¿Cuándo usar cuál?** Si la página tiene pasos visuales (numerados 1-2-3), usa el sin `int`. Si no tiene stepper, usa el overload con `0`. Usar el overload erróneo en página sin stepper lanza `NoSuchElementException`.

## Patrón de click en botones dentro de modales Ant Design
```java
// Ant Design ant-modal-wrap intercepta clicks normales — usar jsClick
Buttons buttons = new Buttons();
waitForElementVisibility(buttons.getAcceptButton());
buttons.clickAcceptButton();            // primer Aceptar (fuera del modal) — click normal OK
waitForElementVisibility(field.getInvitationModalField());
buttons.jsClickAcceptButton();          // segundo Aceptar (dentro del modal) — requiere jsClick
```
**Por qué:** El `div.ant-modal-wrap` tiene z-index alto y bloquea `click()` normal de Selenium con `ElementClickIntercepted`. `jsClick` ejecuta directamente en el elemento via JavaScript, bypaseando el overlay.

## Patrón de espera antes de interactuar (para páginas con carga lenta en remote grid)
```java
// SIEMPRE waitForElementPresence antes del primer click en una página nueva
waitForElementPresence(By.xpath(ALGUNA_CONSTANTE), Timeouts.LOAD_ELEMENT);
click(getElement(By.xpath(ALGUNA_CONSTANTE)));

// Para navegación: esperar el link antes de clickearlo
waitForElementPresence(By.xpath(MassiveRegistrationUsers.INVITE_BUYERS), Timeouts.LOAD_ELEMENT);
click(getElement(By.xpath(MassiveRegistrationUsers.INVITE_BUYERS)));
```
**Nunca usar `sleep(N)` como única espera** — es frágil en remote grid. Usar `waitForElementPresence` o `waitForElementVisibility` con `Timeouts.*`.

## Dropdowns Ant Design — dos tipos (CRÍTICO distinguirlos)
### Inline dropdown (options como `following-sibling::div > li`)
```java
// Usar selectFromDropdownText(element, text) — funciona solo aquí
components.selectFromDropdownText(field.getMoralRegimeTurnField(), "valor");
```
### Portal dropdown (options en body level — `.ant-select-dropdown`)
```java
// NO usar selectFromDropdownText — NO encuentra las opciones
// Usar: click para abrir + dynamicWebElement para seleccionar la opción
click(field.getInvitationSendTypeField());
sleep(Timeouts.SHORT_TIME);
click(new CommonComponents().dynamicWebElement(
    CommonUsersFields.INVITATION_DROPDOWN_OPTION, "Individual"));
// Donde INVITATION_DROPDOWN_OPTION = "//li[contains(@class,'ant-select-dropdown-menu-item') and text()='?']"
```
**Cómo distinguirlos:** Inspeccionar el DOM en Chrome. Si las opciones aparecen como hijos/siblings del trigger → inline. Si aparecen pegadas al `<body>` con clase `ant-select-dropdown` → portal.

## CommonUsersFields — campos disponibles (leer antes de crear nuevos)
### Campos generales de usuario
`USER_NAME`, `USER_SURNAME`, `USER_LAST_NAME`, `USER_EMAIL`, `USER_RFC`, `USER_CURP`, `USER_FOLIO`
`USER_COUNTRY`, `USER_LANGUAGE`, `USER_TIMEZONE`, `USER_DEPARTMENT`, `USER_JOB`, `USER_COMMENTS`
`USER_PHONE_NUMBER`, `USER_CELL_PHONE_NUMBER`, `USER_OTHER_NUMBER`, `USER_OFFICE_PHONE_NUMBER`
`USER_VALID_FROM`, `USER_END_DATE`, `USER_CALENDAR_INPUT`, `USER_EMPLOYER_ID`, `USER_PROVIDER_ID`

### Campos asegurado
`INSURED_ACCIDENT_SERIAL`, `INSURED_CASE_ID`, `INSURED_VEHICLE_SERIAL`

### Campos comprador personal
`PERSONAL_BIRTH_DATE`, `PERSONAL_ADDRESS_TYPE`, `PERSONAL_COUNTRY`, `PERSONAL_STATE`
`PERSONAL_STREET`, `PERSONAL_NUMBER_EXT`, `PERSONAL_POSTAL_CODE`, `PERSONAL_COLONY`
`PERSONAL_DELEGATION`, `PERSONAL_CITY`, `PERSONAL_START_DATE`, `PERSONAL_END_DATE`
`PERSONAL_REGIME`, `PERSONAL_GRADE`, `PERSONAL_OCCUPATION`, `PERSONAL_JOB`

### Campos comprador moral
`MORAL_ACTIVITY_TURN`, `MORAL_REGIME_TURN`, `MORAL_SERIE_FIEL`, `MORAL_FOLIO_MERCANTIL`
`MORAL_CONSTITUTION_DATE`, `MORAL_BIRTH_DATE`, `MORAL_ADDRESS_TYPE`, `MORAL_COUNTRY`
`MORAL_STATE`, `MORAL_STREET`, `MORAL_NUMBER_EXT`, `MORAL_POSTAL_CODE`, `MORAL_COLONY`
`MORAL_DELEGATION`, `MORAL_CITY`, `MORAL_START_DATE`, `MORAL_END_DATE`

### Campos de invitación de compradores (agregados en CP022)
`INVITATION_SEND_TYPE`        → `"//div[@id='individual_option']"` — dropdown portal
`INVITATION_DROPDOWN_OPTION`  → `"//li[contains(@class,'ant-select-dropdown-menu-item') and text()='?']"` — opción portal
`INVITATION_FIRSTNAME`        → `"//input[@id='individual_firstname']"`
`INVITATION_SURNAME`          → `"//input[@id='individual_surname']"`
`INVITATION_LASTNAME`         → `"//input[@id='individual_lastname']"`
`INVITATION_EMAIL`            → `"//input[@id='individual_email']"`
`INVITATION_PHONE`            → `"//input[@id='individual_phone']"`
`INVITATION_MODAL`            → `"//div[@role='document']"` — modal de confirmación

Getters: `getInvitationSendTypeField()`, `getInvitationFirstnameField()`, `getInvitationSurnameField()`,
`getInvitationLastnameField()`, `getInvitationEmailField()`, `getInvitationPhoneField()`, `getInvitationModalField()`

### Aseguradoras
`USER_INSURANCE_EMAIL`, `USER_INSURANCE_PHONE`, `USER_INSURANCE_CARRIER`

## Buttons — métodos disponibles (leer antes de crear nuevos)
```java
clickAcceptButton()          // Aceptar — click normal, con waitForClickable interno
jsClickAcceptButton()        // Aceptar — jsClick, para botones dentro de modales Ant Design
getAcceptButton()            // retorna WebElement acceptButton para waitForElementVisibility externo
scrollAndClickAcceptButton() // Aceptar con scroll previo (cuando el botón está fuera del viewport)
clickContinueBtn()           // Continuar
clickCancelBtn()             // Cancelar
clickCloseBtn()              // Cerrar (X del modal)
clickSearchBtn()             // Submit/Buscar — usa jsClick
clickBuscarBtn()             // Buscar
clickRestartBtn()            // Reiniciar
clickUpdateBtn()             // Actualizar
clickApprovalButton()        // Aprobar
clickActivateButton()        // Activar
clickAssignTransferButton()  // Asignar traslado
clickAdjudicateBtn()         // Adjudicar
clickAddImage()              // Agregar imagen
```
**Buttons.java tiene dos XPaths de Aceptar:**
- `ACCEPT_BUTTON` = `"(//span[text()='Aceptar'])[1]"` — primer botón Aceptar visible
- `ACCEPT_BUTTON_SECOND` = `"(//span[text()='Aceptar'])[2]"` — segundo botón Aceptar (en modal)

## CommonComponents — métodos clave
```java
fillField(WebElement, String, List<CompleteWebElement>)           // CON stepper — llama getIndex()
fillField(WebElement, String, List<CompleteWebElement>, int)      // SIN stepper — indexTab fijo, más rápido
selectFromDropdownText(WebElement, String)                        // solo inline dropdowns
dynamicWebElement(String xpath, String param)                     // reemplaza ? en XPath
validateCaseCreation(List<CompleteWebElement>)                    // compara stored vs DOM
setCalendarDatesText(calendarBtn, inputField, "DD/MM/YYYY")
uploadFileToWebApp(element, fileName)
updateElement(element)                                            // retry para StaleElementReferenceException
```
**Nota importante sobre fillField:** `getIndex()` hace 2 `findElements` al DOM por llamada. En remote grid con 5 campos = ~10 round-trips extra. Siempre usar el overload con `int 0` en páginas sin stepper.

## IndividualRegistration — flujos de caso ya implementados
```java
vehicleIndividualRegistration(vin, caseData)            // Crear caso vehículo + validar
vehicleIndividualRegistration_AddImages(vin, caseData)  // Crear caso + subir imágenes (CP053)
miscellaneousGeneralConsultation()                      // Consulta casos DIVERSOS (CP028)
generalInquiryTransfer()                                // Consulta casos TRASLADOS (CP029)
```

## Patrón de upload de archivos (input[type='file'] oculto)
```java
// NO hacer click en el botón "Adjuntar" — abre el diálogo nativo del OS
// NO usar waitForElementToBeClickable — el input oculto nunca es "clickable"
// CORRECTO: sendKeys directo al input oculto
getElement(By.xpath("//input[@type='file']")).sendKeys(absoluteFilePath);
```
**Construcción del path sin encoding:**
```java
// MAL: getAttachmentsFolderFilePath() → ClassLoader.getResource().getPath() → "...%c3%ad..."
// BIEN: File.getAbsolutePath() desde ruta relativa → path nativo sin encoding
String basePath = new File("src/test/resources/attachments/images15/").getAbsolutePath() + File.separator;
```

## Patrón de consulta/búsqueda de casos (CP028/CP029)
```java
// Validar que los resultados cargaron → usar REPORT_BUTTON, NO generalSearch() boolean
casesMenu.consultCases();  // navega a /cases/search
new CommonComponents().selectFromDropdownText(
        getElement(By.xpath(CaseSearch.CASE_TYPE_FIELD)), CaseType.VARIOUS.getCaseType());
sendKeys(getElement(By.id(CaseSearch.GENERAL_SEARCH_FIELD)), "criteria");
new Buttons().clickSearchBtn();
waitForElementPresence(By.xpath(CaseSearch.REPORT_BUTTON), Timeouts.LOAD_RESULTS);
return true;
// CaseSearch.generalSearch() tiene bug: contains(text(),'X') no encuentra texto anidado en <span>/<a>
```

## AdministratorMasterInter — flujos de usuario ya implementados
```java
individualRegistrationAdminMaster(user)           // Admin Master
individualRegistrationAdminIntern(user)           // Admin Interno
individualRegistrationInsuranceCompanyIntern(user) // Aseguradora Interna
individualRegistrationBuyerPhysical(user)         // Comprador Físico
individualRegistrationBuyerMoral(user)            // Comprador Moral
individualRegistrationSupplierCrane(user)         // Proveedor Grúa
advancedRegistration*()                           // versiones con búsqueda avanzada
inviteIndividualBuyer(user)                       // Invitación individual de comprador (CP022)
fastInviteBuyerIndividual()                       // sub-método: llena formulario de invitación
```

## Timeouts — siempre usar constantes, nunca valores hardcodeados
```java
Timeouts.LOAD_ELEMENT        // espera elemento en DOM
Timeouts.LOAD_PAGE           // espera carga de página
Timeouts.LOAD_RESULTS        // espera resultados de búsqueda
Timeouts.LOAD_BUTTON         // espera botón clickable
Timeouts.SHORT_TIME          // espera corta entre acciones
Timeouts.LOADER              // espera después de click (loader de UI)
Timeouts.NOTIFICATION_DISPLAYED  // espera notificación de éxito/error
```

## MassiveRegistrationUsers — constantes públicas reutilizables
```java
MassiveRegistrationUsers.INVITE_BUYERS = "//a[@href='/users/buyersInvitation']"
```

## InvitationToBuyer enum (ya existe — usar para el tipo de envío)
```java
InvitationToBuyer.INDIVIDUAL.getSendType()  // → "Individual"
InvitationToBuyer.MASSIVE.getSendType()     // → "Masivo"
```

## Factory methods en WebTestBase
```java
loginPage()                    // LogInPage
mainMenu()                     // MenuPage
registrationMenu()             // RegistrationMenu
caseSearch()                   // CaseSearch
caseIndividualRegistration()   // IndividualRegistration
transferIndividualRegistration() // TransferIndividualRegistration
adminMasterInter()             // AdministratorMasterInter
publicationCreation()          // PublicationCreation
awardings()                    // Awardings
documents()                    // Documents
photos()                       // Photos
reportsPublications()          // ReportsPublications
reportsPayments()              // ReportsPayments
```

## Rendimiento en remote grid — reglas para no degradar velocidad
1. **Nunca `sleep()` como única espera** — usar `waitForElementPresence` / `waitForElementVisibility`
2. **Usar `fillField(..., 0)`** en páginas sin stepper — evita 2 `findElements` extra por campo
3. **Reutilizar instancias** de `Buttons`, `CommonUsersFields`, etc. dentro del mismo método — no crear `new Buttons()` en cada línea
4. **Usar `jsClick`** para elementos dentro de modales — evita reintentos por `ElementClickIntercepted`
