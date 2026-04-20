# CHANGELOG — sg-qa-autoonline-mx (autoonline-web)

Registro de todos los cambios realizados al proyecto de automatización.
Formato: `[Fecha] — Descripción — Archivos afectados`

---

## [2026-04-08] — CP022: Invite individual buyers

### Nuevos métodos en `AdministratorMasterInter.java`
- **`inviteIndividualBuyer(AolWebUser user)`** — flujo completo de invitación:
  navega a Users → abre página de invitación → llama `fastInviteBuyerIndividual()` → valida notificación de éxito
- **`fastInviteBuyerIndividual()`** — llena formulario de invitación individual:
  abre dropdown portal (tipo envío) → selecciona "Individual" → llena 5 campos con `fillField(..., 0)` → click Aceptar → confirma modal con `jsClickAcceptButton()`

### Nuevos tests en `TestLogInBasic.java`
- **`tc22_inviteIndividualBuyer()`** — `@Test(priority = 22)`, `tcId = 3936386`

### Cambios en `CommonComponents.java`
- **Nuevo overload `fillField(WebElement, String, List, int indexTab)`**
  Para páginas sin stepper `ant-steps`. Hace solo `sendKeys` + guarda en lista con `indexTab` fijo.
  Evita llamar a `getIndex()` → 2 round-trips al grid vs 4 del original. Usar con `0` en páginas sin pasos.
- **`getIndex()` corregido**: reemplazado `findElement` por `findElements` para `ant-steps-item-active`.
  Retorna `0` si no hay stepper en lugar de lanzar `NoSuchElementException`.
- **Eliminada línea de código muerto**: `getDriver().findElements(...)` sin usar resultado dentro de `fillField`

### Cambios en `CommonUsersFields.java`
Agregados constantes, `@FindBy` y getters para campos de invitación de compradores:
- `INVITATION_SEND_TYPE` = `"//div[@id='individual_option']"`
- `INVITATION_DROPDOWN_OPTION` = `"//li[contains(@class,'ant-select-dropdown-menu-item') and text()='?']"`
- `INVITATION_FIRSTNAME` = `"//input[@id='individual_firstname']"`
- `INVITATION_SURNAME` = `"//input[@id='individual_surname']"`
- `INVITATION_LASTNAME` = `"//input[@id='individual_lastname']"`
- `INVITATION_EMAIL` = `"//input[@id='individual_email']"`
- `INVITATION_PHONE` = `"//input[@id='individual_phone']"`
- `INVITATION_MODAL` = `"//div[@role='document']"`
- Getters: `getInvitationSendTypeField()`, `getInvitationFirstnameField()`, `getInvitationSurnameField()`,
  `getInvitationLastnameField()`, `getInvitationEmailField()`, `getInvitationPhoneField()`, `getInvitationModalField()`

### Cambios en `Buttons.java`
- **`getAcceptButton()`** — getter público del `WebElement` acceptButton para `waitForElementVisibility` externo
- **`jsClickAcceptButton()`** — `waitForElementToBeClickable` + `jsClick` + `sleep(LOADER)`.
  Necesario para botones Aceptar dentro de modales Ant Design (el overlay `ant-modal-wrap` intercepta `click()` normal con `ElementClickIntercepted`)

### Lecciones técnicas de esta iteración
- El dropdown `individual_option` es **portal** (opciones en `<body>`) → `selectFromDropdownText` no funciona aquí
- `fillField` sin `int` falla en páginas sin `ant-steps` porque `getIndex()` hace `findElement` (no `findElements`)
- `jsClick` es necesario para el segundo Aceptar dentro del modal de confirmación de invitación
- Remote grid: ~3-5 segundos por round-trip WebDriver → minimizar llamadas al DOM es crítico

---

## [2026-04-10] — CP023: Invite bulk buyers

### Nuevos métodos en `AdministratorMasterInter.java`
- **`inviteMassiveBuyers(AolWebUser user)`** — flujo completo: navega a Users → abre página de invitación → llama `fastInviteBuyerMassive()` → valida notificación de éxito
- **`fastInviteBuyerMassive()`** — selecciona tipo "Masivo" en dropdown portal → espera botones → envía path del archivo directamente al `input[type='file']` via `sendKeys` (sin click en botón) → confirma modal → click Enviar con `clickSendButton()`

### Nuevos tests en `TestLogInBasic.java`
- **`tc23_inviteMassiveBuyers()`** — `@Test(priority = 23)`, `tcId = 3936387`

### Cambios en `CommonUsersFields.java`
Agregadas constantes, `@FindBy` y getters para invitación masiva:
- `INVITATION_ATTACH_BUTTON` = `"//span[text()='Adjuntar documento']/parent::button"`
- `INVITATION_SEND_BUTTON` = `"//span[text()='Enviar']/parent::button"` (usado solo como referencia, el click va por `Buttons.java`)
- `INVITATION_DOWNLOAD_LAYOUT` = `"//span[text()='Descargar layout']/parent::button"`
- `INVITATION_FILE_INPUT` = `"//input[@type='file']"`
- `MASSIVE_CSV_FILE_PATH` = `"src/test/resources/attachments/reportCompare/Registro_Masivo_BuyersInvitation.xlsx"` (ruta relativa al módulo — se convierte a absoluta en runtime con `new File(...).getAbsolutePath()`)
- Getters: `getInvitationAttachButtonField()`, `getInvitationSendButtonField()`, `getInvitationDownloadLayoutField()`, `getInvitationFileInputField()`

### Cambios en `Buttons.java`
- **`SEND_BUTTON`** = `"//span[text()='Enviar']/parent::button[@type='submit']"` — diferenciado de otros botones Enviar por `@type='submit'`
- **`sendButton`** — `@FindBy` correspondiente
- **`getSendButton()`** — getter para `waitForElementVisibility` externo
- **`clickSendButton()`** — `waitForClickable` + `jsClick`

### Lecciones técnicas de esta iteración
- **Upload de archivos**: NO hacer click en el botón "Adjuntar documento" — abre el diálogo nativo del OS que Selenium no puede controlar. En su lugar, enviar el path directamente al `input[type='file']` con `sendKeys` sin click previo
- **Ruta de archivos de prueba**: usar ruta relativa al módulo (`src/test/resources/...`) + convertir a absoluta en runtime con `new java.io.File(relativePath).getAbsolutePath()` — evita hardcodear el path del equipo de cada desarrollador
- **Botón Enviar vs Aceptar**: el botón Enviar tiene `type="submit"` — distinguirlo en el XPath con `@type='submit'` para no confundirlo con otros botones que también tienen texto "Enviar"

---

## [Fecha anterior] — Setup inicial del proyecto

### Archivos de configuración creados
- **`.claude/launch.json`** — 6 configuraciones Maven para VSCode/Claude Code:
  Web Regression, Web Sanity, Web Debug, Web Debug Reports, REST Regression, Build (sin tests)

### Documentación creada en `Claude/`
- `readme-short.md` — guía rápida de estructura, tests y convenciones
- `readme-TestLogInBasic.md` — detalle de todos los tests en `TestLogInBasic.java`
- `readme-CommonSearch.md` — guía de uso de `CommonSearch` con ejemplos
- `readme-project-overview.md` — visión general del proyecto optimizada para LLMs
- `CHANGELOG.md` — este archivo

---

## Plantilla para futuros cambios

```
## [YYYY-MM-DD] — CP0XX: Nombre del caso

### Nuevos métodos en `[Clase].java`
- **`nombreMetodo(params)`** — descripción de qué hace

### Nuevos tests en `[ClaseTest].java`
- **`tcNN_nombreTest()`** — `@Test(priority = N)`, `tcId = XXXXXXX`

### Cambios en `[Clase].java`
- Descripción del cambio y por qué se hizo

### Lecciones técnicas
- Cualquier descubrimiento sobre la UI o el framework que afecte futuros casos
```
