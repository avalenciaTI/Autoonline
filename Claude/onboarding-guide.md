# Guía de Onboarding — AutoOnline QA Automation

Bienvenido al proyecto de automatización QA de **AutoOnline México (Solera Inc)**.
Esta guía te llevará paso a paso desde cero hasta poder automatizar un caso de prueba de TestRail de forma autónoma.

---

## Antes de empezar — Checklist de accesos

Asegúrate de tener todo esto antes de arrancar:

- [ ] VPN configurada y funcionando
- [ ] Credenciales de AutoOnline (ambiente development): `https://autoonline-cae.audatex.com.mx/`
- [ ] Acceso a TestRail: `https://testrail.solera.com`
- [ ] Acceso al repositorio del proyecto
- [ ] Java 11 instalado (`java -version`)
- [ ] Maven instalado (`mvn -version`)
- [ ] IDE configurado (IntelliJ IDEA recomendado)
- [ ] Proyecto importado como proyecto Maven

---

## Paso 1 — Entiende qué automatizamos

AutoOnline es una plataforma de gestión de vehículos siniestrados. El flujo principal es:

```
Crear caso de vehículo
       ↓
Cargar documentos e imágenes
       ↓
Publicar el caso
       ↓
Adjudicar a un comprador
       ↓
Gestionar pago
       ↓
Transferencia del vehículo
```

**Nuestro trabajo:** automatizar cada uno de estos pasos como tests de regresión, leyendo los casos de prueba desde TestRail y traduciéndolos a código Java.

---

## Paso 2 — Conoce la estructura del proyecto

```
autoonline-web/
├── src/main/java/.../behavior/pages/
│   ├── componentpages/           ← Helpers reutilizables (leer PRIMERO)
│   │   ├── CommonComponents.java ← fillField, selectFromDropdown, dynamicWebElement
│   │   ├── CommonUsersFields.java← XPaths y getters de campos de usuario
│   │   └── Buttons.java          ← Botones reutilizables (Aceptar, Cancelar, etc.)
│   ├── usercreation/
│   │   └── individualregistration/
│   │       └── AdministratorMasterInter.java ← Flujos de usuario (referencia clave)
│   ├── casecreation/             ← Flujos de casos
│   ├── publications/             ← Flujos de publicaciones
│   └── menupage/MenuPage.java    ← Navegación principal
│
└── src/test/java/.../cases/login/
    └── TestLogInBasic.java       ← CLASE PRINCIPAL — leer de arriba a abajo
```

**Regla de oro:** antes de escribir cualquier cosa nueva, busca si ya existe en `componentpages/`.

---

## Paso 3 — Lee el código de referencia

Abre estos archivos en orden y léelos completos:

### 3.1 `TestLogInBasic.java`
Es la clase de test principal. Observa:
- Cómo se hereda de `WebTestBase`
- Cómo se anota cada test (`@Test`, `@TmsData.Tc`)
- Cómo se obtiene el usuario, se hace login y se hace la assertion
- Cómo los tests están ordenados por `priority`

### 3.2 `AdministratorMasterInter.java`
Es el Page Object más completo. Observa:
- Las constantes `private static final String` para XPaths
- Los métodos `fast*()` que llenan formularios
- Los métodos `individualRegistration*()` que orquestan flujos completos
- Cómo se usan `CommonComponents`, `CommonUsersFields` y `Buttons`

### 3.3 `CommonComponents.java`
Observa los dos overloads de `fillField`:
```java
// CON stepper (páginas con pasos 1-2-3)
fillField(WebElement, String value, List<CompleteWebElement>)

// SIN stepper (páginas simples — más rápido en remote grid)
fillField(WebElement, String value, List<CompleteWebElement>, int indexTab)
```

---

## Paso 4 — Entiende los patrones clave

### Patrón de un test completo
```java
@Test(priority = 22)
@TmsData.Tc(tcId = 3936386, tcName = "CP022_Invite individual buyers", tcType = TcType.REGRESSION)
public void tc22_inviteIndividualBuyer() {
    // 1. Obtener usuario
    AolWebUser master = this.users.getMasterUser();
    // 2. Login
    loginPage().logIn(master);
    // 3. Acción + Assertion
    assertions().assertThat(adminMasterInter().inviteIndividualBuyer(master))
            .as("BUYER INVITATION SUCCESS").isTrue();
}
```

### Patrón de un Page Object (método de flujo)
```java
public boolean miNuevoFlujo(AolWebUser user) {
    // 1. Navegar
    MenuPage menuPage = new MenuPage();
    menuPage.clickUsers();

    // 2. Esperar elemento antes de interactuar (SIEMPRE en remote grid)
    waitForElementPresence(By.xpath(MI_CONSTANTE), Timeouts.LOAD_ELEMENT);

    // 3. Llenar campos
    CommonComponents components = new CommonComponents();
    CommonUsersFields field = new CommonUsersFields();
    List<CompleteWebElement> storedValues = new ArrayList<>();
    storedValues = components.fillField(field.getNameField(), "valor", storedValues, 0);

    // 4. Confirmar
    new Buttons().clickAcceptButton();

    // 5. Validar notificación de éxito
    waitForElementVisibility(getElement(By.xpath(NOTIFICATION_MESSAGE)), Timeouts.NOTIFICATION_DISPLAYED);
    return true;
}
```

### Dropdown portal vs inline (crítico)
```java
// Inline → usar selectFromDropdownText
components.selectFromDropdownText(field.getMiDropdownField(), "Opción");

// Portal (opciones en body, clase ant-select-dropdown) → click + dynamicWebElement
click(field.getMiDropdownField());
sleep(Timeouts.SHORT_TIME);
click(new CommonComponents().dynamicWebElement(MI_DROPDOWN_OPTION, "Opción"));
```

### Click en modales Ant Design
```java
buttons.clickAcceptButton();               // Fuera del modal → click normal OK
buttons.jsClickAcceptButton();             // Dentro del modal → jsClick obligatorio
```

---

## Paso 5 — Configura y ejecuta el proyecto

### Compilar sin ejecutar tests
```bash
mvn clean install -DskipTests -f autoonline-web/pom.xml
```

### Ejecutar un test en modo debug
```bash
mvn clean test -Denv=development -Dapp.config=development -Dexec.env=remote.grid \
  -Pautoonline:web:debug -Dtestrail.enabled=false -Daws.s3.enabled=false \
  -DtestRunName=AUTOONLINE_MX_AUTO_REGRESSION_TEST -Dbrowser=chrome \
  -f autoonline-web/pom.xml
```

### Ejecutar regresión completa
```bash
mvn clean test -Denv=development -Dapp.config=development -Dexec.env=remote.grid \
  -Pautoonline:web:regression -Dtestrail.enabled=false -Daws.s3.enabled=false \
  -DtestRunName=AUTOONLINE_MX_AUTO_REGRESSION_TEST -Dbrowser=chrome \
  -f autoonline-web/pom.xml
```

> **Nota:** el perfil `debug` usa `transferSuite.xml`. Para ejecutar un test específico, ajusta el suite XML o usa el filtro de TestNG en tu IDE.

---

## Paso 6 — Flujo de trabajo diario

Este es el proceso que seguirás cada vez que automatices un nuevo caso:

```
1. Abrir TestRail → Test Run FrontEnd (R52560)
         ↓
2. Seleccionar el próximo caso "Untested"
         ↓
3. Leer los pasos del caso en TestRail
         ↓
4. Explorar manualmente esos pasos en la app web
   (https://autoonline-cae.audatex.com.mx/)
         ↓
5. Identificar:
   - ¿Qué página/sección es?
   - ¿Qué elementos hay? ¿Son dropdowns portal o inline?
   - ¿Hay modal de confirmación?
   - ¿Qué Page Object ya existe para ese flujo?
         ↓
6. Buscar en el código:
   - ¿El Page Object ya existe?
   - ¿Los XPaths ya están en CommonUsersFields o Buttons?
   - ¿Hay métodos fast*() o individualRegistration*() similares?
         ↓
7. Implementar:
   - Constantes en CommonUsersFields (si son campos de usuario)
   - @FindBy + getter en CommonUsersFields
   - Método fast*() en el Page Object
   - Método de flujo completo en el Page Object
   - Test en TestLogInBasic.java (o clase correspondiente)
         ↓
8. Ejecutar el test y verificar que pasa
         ↓
9. Actualizar CHANGELOG.md con los cambios realizados
```

---

## Paso 7 — Errores comunes y cómo resolverlos

| Error | Causa | Solución |
|-------|-------|----------|
| `NoSuchElementException` en `getIndex()` | Usar `fillField` sin `int` en página sin stepper | Usar `fillField(..., 0)` |
| `ElementClickIntercepted` | Botón dentro de modal Ant Design | Usar `jsClickAcceptButton()` |
| `NoSuchElementException` al navegar | Page no cargó antes del click | Agregar `waitForElementPresence` antes del click |
| `StaleElementReferenceException` | El elemento cambió en el DOM | Usar `components.updateElement(element)` para refrescar |
| Dropdown no selecciona | Dropdown tipo portal, no inline | Usar click + `dynamicWebElement` con XPath de `.ant-select-dropdown` |
| Test muy lento (20+ seg/campo) | Usando `fillField` sin `int` en remote grid | Usar `fillField(..., 0)` → 2 round-trips vs 4 |

---

## Paso 8 — Documentación disponible

Toda la documentación está en la carpeta `Claude/` del proyecto:

| Archivo | Contenido |
|---------|-----------|
| `readme-project-overview.md` | Visión general completa del proyecto |
| `readme-short.md` | Guía rápida y patrones de código |
| `readme-TestLogInBasic.md` | Detalle de todos los tests existentes |
| `readme-CommonSearch.md` | Guía de búsquedas avanzadas |
| `CHANGELOG.md` | Historial de cambios y lecciones aprendidas |
| `onboarding-guide.md` | Esta guía |
| `kt-session-script.md` | Script de la sesión de knowledge transfer |

---

## Contacto durante la ausencia

Para dudas durante el período de cobertura, usar el canal acordado.
Priorizar siempre revisar la documentación en `Claude/` antes de preguntar.
