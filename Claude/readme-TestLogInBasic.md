# TestLogInBasic — Guía Detallada

**Archivo:** `autoonline-web/src/test/java/.../cases/login/TestLogInBasic.java`

Esta clase cubre el flujo principal de la aplicación: login, creación de usuarios, creación de casos, búsqueda, carga de documentos/fotos y publicaciones.

---

## Estado compartido entre tests

```java
private static String vin;  // VIN único generado una sola vez para toda la clase

@BeforeClass
public static void initializeVin() {
    vin = IdGenerator.getNewVin();  // Ej: "1JKTS1739812345"
}
```

> **Importante:** varios tests usan el mismo `vin`. Si un test crea el caso con ese VIN, los tests posteriores (búsqueda, documentos, imágenes) dependen de que ese caso exista.

---

## Mapa de tests por orden de ejecución

| Priority | Método | Qué hace | Clases usadas |
|----------|--------|----------|---------------|
| 0 | `tc001_logInSuccessful` | Login básico | `LogInPage` |
| 1 | `tc01_userCreateUser` | Crear usuario Admin Intern | `LogInPage`, `AdministratorMasterInter` |
| 2 | `tc02_userCreateUser` | Crear usuario Admin Master | `LogInPage`, `AdministratorMasterInter` |
| 3 | `tc03_caseTransferCreation` | Crear caso vehículo (VIN compartido) | `LogInPage`, `IndividualRegistration` |
| 4 | `tc04_userInsuranceCreateUser` | Crear usuario Insurance Intern | `LogInPage`, `AdministratorMasterInter` |
| 5 | `tc05_userBuyerCreateUser` | Crear usuario Comprador Físico | `LogInPage`, `AdministratorMasterInter` |
| 6 | `tc06_userMoralBuyerCreateUser` | Crear usuario Comprador Moral | `LogInPage`, `AdministratorMasterInter` |
| 7 | `tc07_userVendorCreateUser` | Crear usuario Proveedor Grúa | `LogInPage`, `AdministratorMasterInter` |
| 8 | `tc08_userMasterAdministratorGeneralQuery` | Consulta general Admin Master | `LogInPage`, `AdministratorMasterInter` |
| 15 | `tc15_masterAdministratorGeneralQuery` | Consulta avanzada Admin Master | `LogInPage`, `AdministratorMasterInter` |
| 16 | `tc16_internalAdministratorGeneralQuery` | Consulta avanzada Admin Intern | `LogInPage`, `AdministratorMasterInter` |
| 17 | `tc17_insuredAdvancedConsultation` | Consulta avanzada Asegurado | `LogInPage`, `AdministratorMasterInter` |
| 18 | `tc18_insuranceAdvancedConsultation` | Consulta avanzada Aseguradora | `LogInPage`, `AdministratorMasterInter` |
| 19 | `tc19_BuyerAdvancedPysicalConsultation` | Consulta avanzada Comprador Físico | `LogInPage`, `AdministratorMasterInter` |
| 20 | `tc20_BuyerAdvancedMoralConsultation` | Consulta avanzada Comprador Moral | `LogInPage`, `AdministratorMasterInter` |
| 21 | `tc21_craneAdministratorGeneralQuery` | Consulta avanzada Proveedor Grúa | `LogInPage`, `AdministratorMasterInter` |
| 22 | `tc22_inviteIndividualBuyer` | Invitar comprador individual | `LogInPage`, `AdministratorMasterInter` |
| 23 | `tc23_inviteMassiveBuyers` | Invitar compradores de forma masiva (xlsx) | `LogInPage`, `AdministratorMasterInter` |
| 24 | `tc24_logInSuccessful` | Login con usuario comprador físico 3 | `LogInPage` |
| 26 | `tc26_caseTransferCreation` | Crear caso transferencia (VIN nuevo) | `LogInPage`, `TransferIndividualRegistration` |
| 27 | `tc27_caseVehicleCreation` | Crear caso vehículo (VIN compartido) | `LogInPage`, `IndividualRegistration` |
| 30 | `tc30_caseVehicleConsult` | Buscar caso por VIN | `LogInPage`, `MenuPage`, `RegistrationMenu`, `CaseSearch` |
| 38 | `tc38_caseVehicleLoadFiles` | Cargar documentos al caso | `LogInPage`, `Documents` |
| 39 | `tc39_caseVehicleValidateFiles` | Aprobar/validar documentos | `LogInPage`, `Documents` |
| 40 | `tc40_caseVehicleLoadImages` | Cargar imágenes al caso | `LogInPage`, `Photos` |
| 49 | `tc49_publicationCreationDiverse` | Crear publicación diversa | `LogInPage`, `PublicationCreation` |
| 53 | `tc53_caseAddImages` | Crear caso y agregar imágenes | `LogInPage`, `IndividualRegistration` |
| 76 | `tc76_logInBuyerUser` | Login comprador + validar botones visibles | `LogInPage` |
| 77 | `tc77_logInBuyerUser_ValidateButtons` | Igual que tc76 (duplicado explícito) | `LogInPage` |

---

## Detalle de cada test

---

### tc001 — Login básico (priority 0)

```java
public void tc001_logInSuccessful() {
    AolWebUser master = this.users.getMasterUser();
    assertions().assertThat(loginPage().logIn(master)).as("USER LOG IN OK").isTrue();
}
```

**Pasos internos de `logIn(user)`:**
1. Escribe el username en el campo de texto
2. Escribe la password
3. Hace clic en el checkbox de términos y condiciones
4. Hace clic en el botón de login
5. Si hay una sesión activa abierta, la cierra
6. Espera a que cargue el dashboard

**Assertion:** verifica que el método devuelve `true`.

---

### tc01 — Crear usuario Admin Intern (priority 1)

```java
public void tc01_userCreateUser() {
    AolWebUser master = this.users.getMasterUser();
    loginPage().logIn(master);
    assertions().assertThat(adminMasterInter().individualRegistrationAdminIntern(master))
        .as("USER CREATION SUCCESS").isTrue();
}
```

**Pasos internos de `individualRegistrationAdminIntern(master)`:**
1. Llama a `fastAdminInternUserCreation()` — llena el formulario de creación con datos predefinidos (nombre, email, etc.) y guarda los valores del formulario
2. Llama a `validateAllFields(searchElement)` — busca el usuario recién creado y verifica que los campos mostrados coincidan con los valores guardados
3. Retorna `true` si la validación es exitosa

---

### tc02 — Crear usuario Admin Master (priority 2)

```java
public void tc02_userCreateUser() {
    loginPage().logIn(master);
    assertions().assertThat(adminMasterInter().individualRegistrationAdminMaster(master))
        .as("USER CREATION SUCCESS").isTrue();
}
```

Igual a tc01, pero para el rol **Admin Master**. Internamente llama a `fastAdminMasterUserCreation()`.

---

### tc03 — Crear caso de vehículo (priority 3)

```java
public void tc03_caseTransferCreation() {
    log.info("Starting TC Creating vehicle case with VIN: {}", vin);
    loginPage().logIn(master);
    assertions().assertThat(
        caseIndividualRegistration().vehicleIndividualRegistration(vin, caseData)
    ).as("CASE CREATION SUCCESS").isTrue();
}
```

**Pasos internos de `vehicleIndividualRegistration(vin, caseData)`:**
1. `quickVehicleCaseTypeCreation(vin, nameCase, caseData)` — llena el formulario en 3 pestañas (datos del vehículo, datos del siniestro, documentos requeridos) y guarda los valores
2. Busca el caso recién creado por VIN usando `CaseSearch`
3. `validateCaseCreation(storedValues)` — abre el caso y verifica que los campos mostrados coincidan con lo guardado
4. Retorna `true` si todo coincide

> Este test establece el caso con el `vin` compartido que usan tc30, tc38, tc39, tc40.

---

### tc04 al tc07 — Crear diferentes tipos de usuarios (priority 4–7)

Mismo patrón que tc01/tc02. Cambia solo el método llamado:

| Test | Método | Rol creado |
|------|--------|-----------|
| tc04 | `individualRegistrationInsuranceCompanyIntern` | Aseguradora Interna |
| tc05 | `individualRegistrationBuyerPhysical` | Comprador Físico |
| tc06 | `individualRegistrationBuyerMoral` | Comprador Moral |
| tc07 | `individualRegistrationSupplierCrane` | Proveedor Grúa |

---

### tc08 — Consulta general Admin Master (priority 8)

```java
public void tc08_userMasterAdministratorGeneralQuery() {
    loginPage().logIn(master);
    assertions().assertThat(adminMasterInter().individualRegistrationAdminMaster(master))
        .as("USER CREATION SUCCESS").isTrue();
}
```

> Llama al mismo método que tc02. En práctica verifica que la consulta y validación del usuario Admin Master funciona correctamente.

---

### tc15 al tc21 — Consultas avanzadas por tipo de usuario (priority 15–21)

Estas pruebas usan métodos `advancedRegistration*` en lugar de `individualRegistration*`.

**Diferencia entre consulta general y avanzada:**
- **General** (`individualRegistration*`): crea el usuario y valida con búsqueda simple
- **Avanzada** (`advancedRegistration*`): usa el modo de búsqueda avanzada con filtros adicionales para encontrar y validar el usuario

| Test | Método | Tipo de usuario |
|------|--------|----------------|
| tc15 | `advancedRegistrationAdministratorMaster` | Admin Master |
| tc16 | `advancedRegistrationAdminIntern` | Admin Intern |
| tc17 | `advancedRegistrationAdminInsured` | Asegurado |
| tc18 | `advancedRegistrationAdminIntern` | (mismo método que tc16) |
| tc19 | `advancedRegistrationAdminIntern` | (mismo método que tc16) |
| tc20 | `advancedRegistrationAdminIntern` | (mismo método que tc16) |
| tc21 | `advancedRegistrationSupplierCrane` | Proveedor Grúa |

---

### tc22 — Invitar comprador individual (priority 22)

```java
@Test(priority = 22)
@TmsData.Tc(tcId = 3936386, tcName = "CP022_Invite individual buyers", tcType = TcType.REGRESSION)
public void tc22_inviteIndividualBuyer() {
    AolWebUser master = this.users.getMasterUser();
    loginPage().logIn(master);
    assertions().assertThat(adminMasterInter().inviteIndividualBuyer(master))
            .as("BUYER INVITATION SUCCESS").isTrue();
}
```

**Pasos internos de `inviteIndividualBuyer(user)`:**
1. `menuPage.clickUsers()` → navega a sección Users
2. `waitForElementPresence` + `click(INVITE_BUYERS)` → abre página de invitación (`/users/buyersInvitation`)
3. `fastInviteBuyerIndividual()` → llena el formulario de invitación:
   - Espera carga de la página (`waitForElementPresence` en dropdown portal)
   - Click en dropdown `individual_option` → selecciona tipo **"Individual"** usando `dynamicWebElement`
   - Llena 5 campos (nombre, apellido paterno, apellido materno, email dinámico, teléfono) con `fillField(..., 0)` — overload sin stepper
   - `waitForElementVisibility(acceptButton)` + `clickAcceptButton()` → envía el formulario
   - `waitForElementVisibility(invitationModalField)` → espera modal de confirmación
   - `jsClickAcceptButton()` → confirma (jsClick necesario por overlay `ant-modal-wrap`)
4. `waitForElementVisibility(NOTIFICATION_MESSAGE)` → valida mensaje de éxito
5. Retorna `true`

**Clases modificadas/creadas para este caso:**
- `AdministratorMasterInter` → métodos `inviteIndividualBuyer()` y `fastInviteBuyerIndividual()`
- `CommonUsersFields` → constantes y campos `INVITATION_*` + getters
- `CommonComponents` → nuevo overload `fillField(element, value, list, int indexTab)`
- `CommonComponents` → `getIndex()` corregido para páginas sin stepper
- `Buttons` → `jsClickAcceptButton()`, `getAcceptButton()`

**Notas técnicas importantes:**
- El dropdown de tipo envío (`individual_option`) es **portal** (opciones en body) → NO usar `selectFromDropdownText`, usar `dynamicWebElement` con XPath `//li[contains(@class,'ant-select-dropdown-menu-item') and text()='?']`
- Usar `fillField(..., 0)` porque la página NO tiene stepper `ant-steps` → evita `NoSuchElementException` y es 2x más rápido en remote grid
- El segundo botón Aceptar (dentro del modal) requiere `jsClickAcceptButton()` → el overlay `ant-modal-wrap` intercepta el click normal

---

### tc23 — Invitar compradores masivos (priority 23)

```java
@Test(priority = 23)
@TmsData.Tc(tcId = 3936387, tcName = "CP023_Invite bulk buyers", tcType = TcType.REGRESSION)
public void tc23_inviteMassiveBuyers() {
    AolWebUser master = this.users.getMasterUser();
    loginPage().logIn(master);
    assertions().assertThat(adminMasterInter().inviteMassiveBuyers(master))
            .as("MASSIVE BUYER INVITATION SUCCESS").isTrue();
}
```

**Pasos internos de `inviteMassiveBuyers(user)`:**
1. `menuPage.clickUsers()` → navega a sección Users
2. `waitForElementPresence` + `click(INVITE_BUYERS)` → abre página de invitación
3. `fastInviteBuyerMassive()` → llena flujo masivo:
   - Selecciona **"Masivo"** en dropdown portal usando `InvitationToBuyer.MASSIVE.getSendType()`
   - Espera que aparezcan los botones: `Adjuntar documento`, `Enviar`, `Descargar layout`
   - Construye la ruta absoluta: `new File(MASSIVE_CSV_FILE_PATH).getAbsolutePath()`
   - Envía el path **directamente** al `input[type='file']` con `sendKeys` — sin click en el botón
   - Confirma modal de carga exitosa con `jsClickAcceptButton()`
   - Click en **Enviar** con `buttons.clickSendButton()` (jsClick, `type="submit"`)
4. `waitForElementVisibility(NOTIFICATION_MESSAGE)` → valida notificación de éxito

**Archivo de prueba:**
- Path relativo: `src/test/resources/attachments/reportCompare/Registro_Masivo_BuyersInvitation.xlsx`
- Constante: `CommonUsersFields.MASSIVE_CSV_FILE_PATH`

**Notas técnicas:**
- **NO** hacer click en "Adjuntar documento" — abre diálogo nativo del OS que Selenium no controla
- Usar `sendKeys` directo al `input[type='file']` oculto para el upload
- El botón Enviar tiene `type="submit"` → XPath: `//span[text()='Enviar']/parent::button[@type='submit']`
- Reutiliza la misma navegación y dropdown portal que CP022 — solo cambia el tipo a "Masivo"

---

### tc24 — Login con comprador físico (priority 24)

```java
public void tc24_logInSuccessful() {
    AolWebUser master = this.users.getPhysicalBuyerUser3();
    assertions().assertThat(loginPage().logIn(master)).as("USER LOG IN OK").isTrue();
}
```

Verifica que un usuario **comprador físico** (rol diferente al master) puede iniciar sesión.

---

### tc26 — Crear caso de transferencia (priority 26)

```java
public void tc26_caseTransferCreation() {
    String newVin = IdGenerator.getNewVin();  // VIN NUEVO, no el compartido
    loginPage().logIn(master);
    assertions().assertThat(
        transferIndividualRegistration().transferIndividualRegistration(newVin)
    ).as("CASE CREATION SUCCESS").isTrue();
}
```

**Pasos internos de `transferIndividualRegistration(vin)`:**
1. `fillTransfer(vin, sinister)` — llena el formulario de transferencia con VIN, datos del vehículo y destino
2. Busca el caso de transferencia por VIN
3. `validateCaseCreation(storedValues)` — verifica campos
4. Retorna `true`

> Usa un VIN **local** generado dentro del test — no afecta el `vin` compartido de la clase.

---

### tc27 — Crear caso vehículo con VIN compartido (priority 27)

```java
public void tc27_caseVehicleCreation() {
    log.info("Starting TC Creating vehicle case with VIN: {}", vin);
    loginPage().logIn(master);
    assertions().assertThat(
        caseIndividualRegistration().vehicleIndividualRegistration(vin, caseData)
    ).as("CASE CREATION SUCCESS").isTrue();
}
```

Idéntico a tc03 — usa el mismo `vin` compartido.

---

### tc30 — Buscar caso por VIN (priority 30)

```java
public void tc30_caseVehicleConsult() {
    loginPage().logIn(master);
    mainMenu().clickCases();
    registrationMenu().consultCases();
    assertions().assertThat(caseSearch().generalSearch(CaseType.VEHICLES, vin))
        .as("CASE SEARCH SUCCESS?").isTrue();
}
```

**Pasos:**
1. Login
2. `mainMenu().clickCases()` — hace clic en el menú "Casos"
3. `registrationMenu().consultCases()` — navega a "Consultar Casos"
4. `caseSearch().generalSearch(CaseType.VEHICLES, vin)` — selecciona tipo VEHICLES, escribe el VIN en el campo de búsqueda y verifica que aparezca un resultado

**Depende de:** que el caso con `vin` exista (creado en tc03 o tc27).

---

### tc38 — Cargar documentos al caso (priority 38)

```java
public void tc38_caseVehicleLoadFiles() {
    loginPage().logIn(master);
    assertions().assertThat(documents().vehicleLoadFiles(master, vin))
        .as("FILES LOADED SUCCESS").isTrue();
}
```

**Pasos internos de `vehicleLoadFiles(user, vin)`:**
1. Busca el caso por VIN
2. `loadAllFiles()` — adjunta todos los tipos de documento requeridos con comentarios, navega entre pestañas
3. Retorna `true`

---

### tc39 — Aprobar documentos del caso (priority 39)

```java
public void tc39_caseVehicleValidateFiles() {
    loginPage().logIn(master);
    assertions().assertThat(documents().vehicleValidateAllFiles(vin))
        .as("FILES VALIDATED SUCCESS").isTrue();
}
```

**Pasos internos de `vehicleValidateAllFiles(vin)`:**
1. Busca el caso por VIN
2. Espera a que el botón de reporte esté disponible
3. `validateAllDocuments()` — aprueba todos los documentos uno por uno
4. `validateCaseStatus()` — va a la pestaña 3 y verifica que el estado del caso sea **"Documentado"**
5. Retorna `true`

**Depende de:** tc38 (los documentos deben estar cargados).

---

### tc40 — Cargar imágenes al caso (priority 40)

```java
public void tc40_caseVehicleLoadImages() {
    loginPage().logIn(master);
    assertions().assertThat(photos().vehicleLoadImages(master, vin))
        .as("CASE LOAD IMAGES SUCCESS").isTrue();
}
```

**Pasos internos de `vehicleLoadImages(user, vin)`:**
1. Busca el caso por VIN
2. `loadPhotos()` — abre la sección de fotos
3. `attachImages(false)` — sube imágenes desde una carpeta local, maneja paginación
4. `markAsFavorite()` — marca todas las imágenes como favoritas y cierra la ventana
5. Retorna `true`

---

### tc49 — Crear publicación diversa (priority 49)

```java
public void tc49_publicationCreationDiverse() {
    loginPage().logIn(master);
    assertions().assertThat(
        publicationCreation().fastVariousPublicationValidation(master)
    ).as("PUBLICATION CREATION SUCCESS").isTrue();
}
```

**Pasos internos de `fastVariousPublicationValidation(master)`:**
1. `quickVehicleCaseTypeCreation()` — crea una publicación de tipo "Various" con valores predefinidos
2. Retorna `true`

---

### tc53 — Crear caso y agregar imágenes en un solo flujo (priority 53)

```java
public void tc53_caseAddImages() {
    loginPage().logIn(master);
    assertions().assertThat(
        caseIndividualRegistration().vehicleIndividualRegistration_AddImages(vin, caseData)
    ).as("CASE CREATION SUCCESS").isTrue();
}
```

**Diferencia con tc03/tc27:**
Usa `vehicleIndividualRegistration_AddImages` que internamente llama a `quickVehicleCaseTypeCreation_AddImages()` — crea el caso e inicia directamente el flujo de agregar imágenes en el mismo método.

---

### tc76 y tc77 — Login comprador + validar botones (priority 76–77)

```java
public void tc76_logInBuyerUser() {
    AolWebUser master = this.users.getPhysicalBuyerUser3();
    assertions().assertThat(loginPage().logIn(master)).as("USER LOG IN OK").isTrue();
    assertions().assertThat(loginPage().areOnlineButtonsVisible())
        .as("ONLINE VEHICLE AND DIVERSE BUTTONS ARE VISIBLE").isTrue();
}
```

**Pasos:**
1. Login con usuario **comprador físico 3**
2. Verifica que los botones "Vehículo Online" y "Diverso" sean visibles en el dashboard

**Método `areOnlineButtonsVisible()`:** busca los dos botones de tipo de publicación online y retorna `true` si ambos están presentes.

> tc77 es idéntico a tc76 — verifica el mismo comportamiento de forma explícita.

---

## Clases usadas — resumen de métodos relevantes

### `LogInPage`

| Método | Qué hace | Retorna |
|--------|----------|---------|
| `logIn(AolWebUser user)` | Login completo (usuario, password, checkbox, botón) | `boolean` |
| `areOnlineButtonsVisible()` | Verifica botones de publicación online tras login | `boolean` |

### `AdministratorMasterInter`

| Método | Qué hace |
|--------|----------|
| `individualRegistrationAdminMaster(user)` | Crea + valida usuario Admin Master |
| `individualRegistrationAdminIntern(user)` | Crea + valida usuario Admin Intern |
| `individualRegistrationInsuranceCompanyIntern(user)` | Crea + valida usuario Aseguradora Interna |
| `individualRegistrationBuyerPhysical(user)` | Crea + valida Comprador Físico |
| `individualRegistrationBuyerMoral(user)` | Crea + valida Comprador Moral |
| `individualRegistrationSupplierCrane(user)` | Crea + valida Proveedor Grúa |
| `advancedRegistrationAdministratorMaster(user)` | Búsqueda avanzada Admin Master |
| `advancedRegistrationAdminIntern(user)` | Búsqueda avanzada Admin Intern |
| `advancedRegistrationAdminInsured(user)` | Búsqueda avanzada Asegurado |
| `advancedRegistrationSupplierCrane(user)` | Búsqueda avanzada Proveedor Grúa |

> Todos retornan `boolean`.

### `IndividualRegistration`

| Método | Qué hace | Retorna |
|--------|----------|---------|
| `vehicleIndividualRegistration(vin, caseData)` | Crea caso de vehículo en 3 pestañas + busca + valida | `boolean` |
| `vehicleIndividualRegistration_AddImages(vin, caseData)` | Igual + inicia flujo de imágenes | `boolean` |

### `TransferIndividualRegistration`

| Método | Qué hace | Retorna |
|--------|----------|---------|
| `transferIndividualRegistration(vin)` | Crea caso de transferencia + busca + valida | `boolean` |

### `CaseSearch`

| Método | Qué hace | Retorna |
|--------|----------|---------|
| `generalSearch(CaseType, vin)` | Busca casos por tipo y criterio | `boolean` |
| `advanceSearch(CaseType)` | Activa modo búsqueda avanzada | `void` |
| `fillFilterField(field, value)` | Llena filtro dinámico | `void` |

### `Documents`

| Método | Qué hace | Retorna |
|--------|----------|---------|
| `vehicleLoadFiles(user, vin)` | Busca caso + carga todos los documentos | `boolean` |
| `vehicleValidateAllFiles(vin)` | Busca caso + aprueba docs + valida estado "Documentado" | `boolean` |

### `Photos`

| Método | Qué hace | Retorna |
|--------|----------|---------|
| `vehicleLoadImages(user, vin)` | Busca caso + sube imágenes + marca favoritas | `boolean` |

### `PublicationCreation`

| Método | Qué hace | Retorna |
|--------|----------|---------|
| `fastVariousPublicationValidation(user)` | Crea publicación de tipo "Various" | `boolean` |

---

## Flujo típico de un test

```
1. Obtener usuario     →  this.users.getMasterUser()
2. Login               →  loginPage().logIn(user)
3. Navegar (si aplica) →  mainMenu().clickCases()
                          registrationMenu().consultCases()
4. Acción principal    →  caseSearch().generalSearch(...)
                          documents().vehicleLoadFiles(...)
5. Assertion           →  assertions().assertThat(resultado).as("MENSAJE").isTrue()
```

---

## Dependencias implícitas entre tests (misma ejecución de suite)

```
tc03 / tc27  →  crea el caso con "vin"
    ↓
tc30         →  busca ese caso por "vin"
    ↓
tc38         →  carga documentos al caso
    ↓
tc39         →  valida/aprueba documentos
tc40         →  carga imágenes (independiente de tc39)
```

> Si la suite corre completa, estas dependencias se cumplen por orden de `priority`.
> Si corres un test aislado (ej. solo tc30), el caso con ese VIN debe existir previamente en el ambiente.

---

---

# Flujo de Caso de Transferencia — Análisis detallado

## El test: tc25 / tc26

```java
@Test(priority = 25)
@TmsData.Tc(tcId = 160927, tcName = "CP025_Various individual case record", tcType = TcType.REGRESSION)
public void tc25_caseTransferCreation() {
    AolWebUser master = this.users.getMasterUser();
    String newVin = IdGenerator.getNewVin();   // VIN único local, no compartido con la clase
    loginPage().logIn(master);
    assertions().assertThat(
        transferIndividualRegistration().transferIndividualRegistration(newVin)
    ).as("CASE CREATION SUCCESS").isTrue();
}
```

> `tc26` es idéntico con `priority = 26`. Ambos crean un caso de transferencia con un VIN **nuevo generado en el momento**, sin reutilizar el `vin` de `@BeforeClass`.

---

## Arquitectura del flujo: qué llama a qué

```
Test (tc25)
 └─ transferIndividualRegistration().transferIndividualRegistration(vin)
       │
       ├─ MenuPage.clickCases()                         ← navega al menú Casos
       ├─ RegistrationMenu.clickIndividualRegistrationCase()  ← clic en "Registro Individual"
       │
       ├─ TransferIndividualRegistration.fillTransfer(vin, sinister)
       │     ├─ PASO 1 — datos del vehículo  (Pestaña 1)
       │     ├─ PASO 2 — datos de destino    (Pestaña 2)
       │     ├─ PASO 3 — datos financieros   (Pestaña 3)
       │     └─ PASO 4 — datos de transporte (Pestaña 4)
       │
       ├─ MenuPage.clickCases()                         ← vuelve al menú
       ├─ RegistrationMenu.clickSearchCases("Traslados", vin)  ← busca el caso creado
       ├─ CommonComponents.findHRefElement(...)          ← abre el resultado
       │
       └─ TransferIndividualRegistration.validateCaseCreation(storedValues)
             └─ compara los valores guardados con los valores en el DOM
```

---

## `fillTransfer()` — Los 4 pasos del formulario

### Paso 1 — Datos del vehículo (Pestaña 1)

El dropdown `caseType` se fija en **"Traslados"** (diferencia clave vs. vehículos que usa "vehículos").

| Campo | XPath ID | Valor hardcodeado |
|-------|----------|-------------------|
| Número de siniestro | `caseNumber` | `"TESTTRANSFERAUTOAMTED"` |
| Reporte (NIU) | `data_test_niu` | `"wreckSubtypeAutomated"` |
| Póliza de seguro | `insurancePolicy` | `"PLZ1620"` |
| Tipo de siniestro | `wreckType` | `"Incendio"` |
| Subtipo de siniestro | `wreckSubType` | `"Chatarra"` |
| Marca | `brand` / `marketName` | `"NISSAN"` |
| Tipo de vehículo | `_type` | `"SUV"` |
| Versión | `_version` | `"Luxury"` |
| Color | `color` | `"Red"` |
| Modelo (año) | `model` | `"2024"` |
| Serie de póliza | `policySerial` | `"PLZSERIES1623"` |
| VIN | `vehicleSerial` | **vin (parámetro dinámico)** |
| Placas | `vehiclePlate` | `"PLZ1620"` |
| Número de motor | `engineNumber` | `"engineNumberAutomated"` |
| Tipo de motor | `engineType` | `"Otro"` |
| Tipo de unidad | `vehicleType` | `"Autos"` |
| Kilometraje | `mileage` | `"15000"` |

→ `new Buttons().clickContinueBtn()` — avanza a la siguiente pestaña

### Paso 2 — Datos de destino (Pestaña 2)

| Campo | XPath ID | Valor hardcodeado |
|-------|----------|-------------------|
| Tipo de ubicación | `locationTypeId` | `"OTRO"` |
| Nombre destino | `destinationName` | `"destinationNameAutomated"` |
| País | `countryId` / `country` | `"Mexico"` |
| Estado | `stateId` / `state` | `"Jalisco"` |
| Calle | `roadName` | `"streetAutomated"` |
| Núm. exterior | `outNumber` | `"123"` |
| Núm. interior | `inNumber` | `"123"` |
| Código postal | `zipCode` | `"12345"` |
| Colonia | `neighborhood` | `"neighborhoodAutomated"` |
| Municipio | `town` | `"townAutomated"` |
| Ciudad | `city` | `"cityAutomated"` |
| Observaciones | `observations` | `"observationsAutomated"` |

→ `new Buttons().clickContinueBtn()` — avanza

### Paso 3 — Datos financieros / aseguradora (Pestaña 3)

| Campo | XPath ID | Valor hardcodeado |
|-------|----------|-------------------|
| Aseguradora | `insuranceCarrier` / `insuranceName` | `"QA TESTS AUTOMATION"` |
| Comprador | `buyer` | `"TESTER 1"` |
| C1 | `c1` | `"c1Automated"` |
| C2 | `c2` | `"c2Automated"` |
| C3 | `c3` | `"c3Automated"` |

→ `new Buttons().clickContinueBtn()` — avanza

### Paso 4 — Datos de transporte (Pestaña 4)

| Campo | XPath ID | Valor hardcodeado | ¿Se valida? |
|-------|----------|-------------------|-------------|
| Proveedor grúa | `craneProviderId` / `providerBusinessName` | `"FERNANDO REGRESION"` | Sí |
| Sucursal grúa | `craneBranchId` / `branchName` | `"QA REGRESION"` | Sí |
| Proveedor corralón | `provider` / `carPoundId` | `"FERNANDO REGRESION"` | **No** |
| Sucursal corralón | `branch` | `"QA REGRESION"` | **No** |
| Tipo de traslado | radio button `value='1'` | Local | — |
| Costo | `number` / `cost` | `"1000"` | Sí |
| Cargo | `step-form_charge` | `"1000"` | **No** |

→ `new Buttons().clickAcceptButton()` — guarda el caso
→ Espera la notificación de éxito (`ant-notification-notice-message`)

---

## `validateCaseCreation()` — cómo funciona la validación

Después de crear el caso, el flujo busca el caso por VIN y lo abre. Entonces:

1. Hace clic en el botón **Actualizar** (`data-testid='update'`)
2. Espera a que el campo `caseId` con valor tenga contenido (caso cargado)
3. Itera sobre `storedValues` — la lista de `CompleteWebElement` guardados durante `fillTransfer()`
4. Para cada elemento, si cambió de pestaña navega a la pestaña correcta
5. Lee el valor actual del DOM:
   - **Input:** lee `value` (o `aria-valuenow` si es numérico)
   - **Div (dropdown):** busca el elemento hijo con atributo `title`
6. Compara DOM vs. valor guardado (`equalsIgnoreCase`)
7. Loguea captura de pantalla en cada comparación

> **Nota:** los campos sin `storedValues` (corralón, cargo) **no se validan**. Solo se llenan.

---

## Diferencia entre caso de Vehículo y caso de Traslado

| Aspecto | `IndividualRegistration` (Vehículos) | `TransferIndividualRegistration` (Traslados) |
|---------|--------------------------------------|----------------------------------------------|
| Tipo seleccionado en dropdown | `"vehículos"` | `"Traslados"` |
| Datos de pestaña 2 | Taller (workshop): número, nombre, dirección | Destino: nombre, dirección completa |
| Pestaña 3 | Siniestro: valores económicos (compensación, comercial, base, repuestos, reparación) | Financiero: aseguradora, comprador, C1/C2/C3 |
| Pestaña 4 | No existe | Transporte: grúa, corralón, tipo de traslado, costo |
| Datos de entrada | Usa objeto `Case` (con `Vehicle`, `Workshop`, `Sinister`) | Todo hardcodeado dentro de `fillTransfer()` |
| Clase page object | `IndividualRegistration` | `TransferIndividualRegistration` |
| Método en `WebTestBase` | `caseIndividualRegistration()` | `transferIndividualRegistration()` |
| Búsqueda post-creación | `casesMenu.clickSearchCases("vehículos", vin)` | `casesMenu.clickSearchCases("Traslados", vin)` |

---

## Cómo crear un test con el mismo flujo pero diferente tipo

Si necesitas crear un test de transferencia con datos distintos, el flujo es exactamente el mismo. Solo cambia los valores dentro de `fillTransfer()` en la clase page object, o si ya existe un método para otro tipo de registro, llamas ese.

**Ejemplo de test nuevo siguiendo el patrón de tc25:**

```java
@Test(priority = 28)
@TmsData.Tc(tcId = 999099, tcName = "CP028_Transfer case with different destination", tcType = TcType.REGRESSION)
public void tc28_nuevoTestDeTransferencia() {
    // 1. Obtener usuario
    AolWebUser master = this.users.getMasterUser();

    // 2. Generar VIN único para este test
    String newVin = IdGenerator.getNewVin();
    log.info("Starting transfer case creation with VIN: {}", newVin);

    // 3. Login
    loginPage().logIn(master);

    // 4. Ejecutar flujo de transferencia y verificar resultado
    assertions().assertThat(
        transferIndividualRegistration().transferIndividualRegistration(newVin)
    ).as("TRANSFER CASE CREATION SUCCESS").isTrue();
}
```

**Qué ejecuta esto internamente:**

```
loginPage().logIn(master)
  → escribe usuario + password + checkbox + botón

transferIndividualRegistration().transferIndividualRegistration(newVin)
  → MenuPage.clickCases()
  → RegistrationMenu.clickIndividualRegistrationCase()
  → fillTransfer(newVin, "TESTTRANSFERAUTOAMTED")
       Pestaña 1: tipo="Traslados", vin=newVin, marca=NISSAN, etc.
       Pestaña 2: destino en Jalisco, México
       Pestaña 3: aseguradora="QA TESTS AUTOMATION", comprador="TESTER 1"
       Pestaña 4: grúa="FERNANDO REGRESION", traslado local, costo=1000
       → clickAcceptButton() → espera notificación
  → MenuPage.clickCases()
  → clickSearchCases("Traslados", newVin)
  → abre resultado en tabla
  → validateCaseCreation() → compara campos guardados con DOM
  → retorna true

assertions().assertThat(true).as("...").isTrue()  ← pasa
```

---

## `CompleteWebElement` — cómo funciona el patrón guardar/validar

Este patrón es la base de la validación en ambos tipos de caso:

```
fillTransfer() / quickVehicleCaseTypeCreation()
│
├─ Por cada campo que se llena:
│   1. Se escribe el valor en el campo (sendKeys o selectFromDropdown)
│   2. Se crea un CompleteWebElement con:
│      - referencia al WebElement del DOM
│      - valor que se escribió (desiredValue)
│      - índice de la pestaña activa en ese momento (indexTab)
│   3. Se agrega a la lista storedValues
│
└─ Retorna storedValues (la lista completa)

validateCaseCreation(storedValues)
│
└─ Por cada CompleteWebElement en storedValues:
    1. Si cambió de pestaña → navega a esa pestaña
    2. Lee el valor actual del elemento en el DOM
    3. Compara con desiredValue
    4. Loguea + captura de pantalla
```

> El campo NO falla el test si el valor no coincide — solo lo loguea. El método retorna `true` siempre. La validación real es que el flujo completo se ejecute sin excepciones.
