# Script de Sesión — Knowledge Transfer AutoOnline QA

**Duración estimada:** 2.5 a 3 horas
**Formato:** Pantalla compartida — el instructor muestra, el suplente pregunta y luego replica
**Prerequisito:** El suplente ya tiene todos los accesos del checklist de onboarding

---

## Bloque 1 — Contexto del negocio (15 min)

### Objetivo
Que el suplente entienda QUÉ automatizamos antes de ver código.

### Guión

> *"Primero vamos a ver la aplicación en vivo para que entiendas qué hace AutoOnline y qué es lo que estamos automatizando."*

**Demostración en vivo** (abrir `https://autoonline-cae.audatex.com.mx/`):
1. Mostrar el login y el dashboard principal
2. Navegar por el menú: Users → Cases → Publications → Payments
3. Abrir un caso existente y mostrar sus pestañas (datos, documentos, fotos)
4. Mostrar una publicación y explicar el flujo de adjudicación

> *"Todo este flujo que acabas de ver — login, crear usuarios, crear casos, publicar, adjudicar, pagar — eso es lo que automatizamos con Selenium. Cada paso de TestRail se convierte en código Java."*

**Preguntas de verificación:**
- ¿Entiendes qué hace la plataforma en líneas generales?
- ¿Tienes clara la diferencia entre un caso y una publicación?

---

## Bloque 2 — TestRail: cómo leemos los casos (15 min)

### Objetivo
Que el suplente sepa navegar TestRail y leer un caso de prueba.

### Guión

> *"Vamos a abrir TestRail. Aquí es donde están definidos todos los casos que tenemos que automatizar."*

**Demostración en vivo** (abrir `https://testrail.solera.com`):
1. Navegar al Test Run FrontEnd (R52560)
2. Mostrar el plan `AutoOnline_Cases2Automate`
3. Explicar los estados: Passed ✅, Untested ⬜, Failed ❌
4. Abrir un caso "Untested" y leer los pasos en voz alta
5. Explicar que `tcId` (el número en la URL del caso) es el que va en `@TmsData.Tc`

> *"Tu trabajo es tomar estos casos Untested, uno por uno, entender los pasos, ir a la app a explorarlos manualmente, y luego traducirlos a código Java."*

**Ejercicio rápido:**
- El suplente abre TestRail, encuentra un caso Untested y lee los pasos en voz alta

---

## Bloque 3 — Estructura del proyecto (20 min)

### Objetivo
Que el suplente navegue el proyecto sin perderse.

### Guión

> *"Abre el proyecto en IntelliJ. Vamos a recorrer las carpetas clave."*

**Demostración en vivo** (IDE abierto):

1. **`componentpages/`** — abrir cada archivo y explicar su rol:
   - `CommonComponents.java` → "Aquí está `fillField` y `selectFromDropdown`. Son las funciones que más vas a usar."
   - `CommonUsersFields.java` → "Aquí guardamos todos los XPaths de campos de usuario. Antes de crear un XPath nuevo, busca aquí."
   - `Buttons.java` → "Todos los botones reutilizables. `clickAcceptButton`, `jsClickAcceptButton`, etc."

2. **`usercreation/individualregistration/AdministratorMasterInter.java`**
   > *"Este es el Page Object más completo del proyecto. Si quieres ver cómo se hace cualquier cosa, busca aquí un ejemplo."*
   - Mostrar una constante XPath
   - Mostrar un `@FindBy` y su getter correspondiente
   - Mostrar un método `fast*()` que llena un formulario
   - Mostrar un método `individualRegistration*()` que orquesta el flujo completo

3. **`TestLogInBasic.java`**
   > *"Esta es la clase de tests principal. Cada método es un caso de prueba de TestRail."*
   - Mostrar la estructura de un test: `@Test`, `@TmsData.Tc`, login, assertion
   - Mostrar cómo `priority` define el orden de ejecución

**Preguntas de verificación:**
- ¿Sabes dónde buscar un XPath existente antes de crear uno nuevo?
- ¿Entiendes la diferencia entre un Page Object y una clase de test?

---

## Bloque 4 — Patrones de código: los 4 que necesitas dominar (30 min)

### Objetivo
Que el suplente reconozca y sepa aplicar los 4 patrones principales.

---

### Patrón 1 — `fillField` con y sin stepper (10 min)

> *"Este es el patrón más importante. Hay dos versiones y usar la incorrecta rompe el test."*

**Mostrar en código:**
```java
// CON stepper (páginas con pasos 1-2-3 como creación de casos)
storedValues = components.fillField(field.getNameField(), "AutoTest", storedValues);

// SIN stepper (páginas simples como invitación de compradores)
storedValues = components.fillField(field.getInvitationFirstnameField(), "AutoTest", storedValues, 0);
```

> *"¿Cómo sabes cuál usar? Si la página tiene pasos numerados visualmente (un stepper), usa el primero. Si es un formulario simple sin pasos, usa el segundo con el 0 al final."*

> *"¿Por qué importa? Porque en remote grid, la versión sin 0 hace 4 llamadas al servidor por cada campo. Con 5 campos eso son 20 llamadas extra — puede tardar hasta 20 segundos por campo."*

**Ejercicio:** mostrar la pantalla de creación de usuarios (con stepper) vs la de invitación (sin stepper) en la app. El suplente decide cuál usar para cada una.

---

### Patrón 2 — Dropdowns portal vs inline (10 min)

> *"Ant Design tiene dos tipos de dropdown. Si usas el método incorrecto, simplemente no selecciona nada y el test falla."*

**Mostrar en DevTools de Chrome:**
1. Abrir la app, ir a la página de invitación de compradores
2. Hacer clic en el dropdown de tipo de envío
3. Inspeccionar el DOM y mostrar que las opciones aparecen pegadas al `<body>` con clase `ant-select-dropdown` → **portal**
4. Ir a otra página con dropdown inline y mostrar que las opciones son siblings del trigger → **inline**

**Mostrar en código:**
```java
// Inline → selectFromDropdownText
components.selectFromDropdownText(field.getMiDropdown(), "Opción");

// Portal → click en trigger + dynamicWebElement con XPath del body
click(field.getMiDropdownPortal());
sleep(Timeouts.SHORT_TIME);
click(new CommonComponents().dynamicWebElement(MI_DROPDOWN_OPTION, "Opción"));
// donde MI_DROPDOWN_OPTION = "//li[contains(@class,'ant-select-dropdown-menu-item') and text()='?']"
```

---

### Patrón 3 — jsClick para modales (5 min)

> *"Cuando un botón está dentro de un modal de Ant Design, el click normal de Selenium falla con `ElementClickIntercepted`. Esto es porque el div envolvente del modal tiene mayor z-index y bloquea el click."*

**Mostrar en código:**
```java
buttons.clickAcceptButton();     // Fuera del modal → funciona normal
// ... aparece el modal ...
buttons.jsClickAcceptButton();   // Dentro del modal → jsClick obligatorio
```

> *"Regla fácil: si hay un modal de confirmación visible y el Aceptar no hace nada, probablemente necesitas jsClick."*

---

### Patrón 4 — Esperas antes de interactuar (5 min)

> *"En remote grid, la página puede tardar más en cargar que lo que tarda el código en ejecutarse. Si haces click antes de que el elemento esté listo, falla con `NoSuchElementException`."*

**Mostrar en código:**
```java
// MAL — puede fallar si la página no cargó
click(getElement(By.xpath(MI_ELEMENTO)));

// BIEN — espera a que el elemento esté en el DOM antes de hacer click
waitForElementPresence(By.xpath(MI_ELEMENTO), Timeouts.LOAD_ELEMENT);
click(getElement(By.xpath(MI_ELEMENTO)));
```

> *"Nunca uses solo `sleep(3000)` como espera. Es frágil — en un ambiente lento igual puede fallar. Usa siempre `waitForElementPresence` o `waitForElementVisibility`."*

---

## Bloque 5 — Demo completa: automatizar un caso de cero (45 min)

### Objetivo
El suplente ve en vivo el proceso completo de automatización.

### Guión

> *"Ahora vamos a hacer todo el proceso juntos. Voy a automatizar un caso real de TestRail paso a paso para que veas exactamente cómo trabajamos."*

**Proceso en vivo:**

**Paso 1 — Leer el caso en TestRail** (5 min)
- Abrir TestRail, seleccionar un caso Untested
- Leer los pasos en voz alta
- Identificar: ¿qué módulo es? ¿qué acción hay que realizar?

**Paso 2 — Explorar manualmente en la app** (10 min)
- Ir a la app y realizar los pasos manualmente
- Mientras se hace, comentar en voz alta:
  - "Aquí hay un dropdown — voy a inspeccionar si es portal o inline"
  - "Este es el XPath que voy a usar: `//div[@id='...']`"
  - "Hay un modal de confirmación al final — necesitaré jsClick"

**Paso 3 — Buscar en el código qué ya existe** (5 min)
- Buscar si el Page Object ya existe
- Buscar si los XPaths ya están en `CommonUsersFields`
- Buscar si hay métodos similares en `AdministratorMasterInter`

**Paso 4 — Implementar** (15 min)
- Agregar constantes en `CommonUsersFields` si son campos de usuario
- Agregar `@FindBy` + getter
- Crear método `fast*()` en el Page Object
- Crear método de flujo completo
- Agregar test en `TestLogInBasic.java`

**Paso 5 — Ejecutar y verificar** (10 min)
- Correr el test con Maven (perfil debug)
- Ver el log y las capturas de pantalla
- Si falla: leer el error, diagnosticar, corregir

**Paso 6 — Actualizar CHANGELOG.md** (2 min)
- Documentar qué se hizo y qué se aprendió

---

## Bloque 6 — Práctica supervisada (30 min)

### Objetivo
El suplente automatiza un caso por su cuenta mientras el instructor observa.

### Guión

> *"Ahora te toca a ti. Voy a estar aquí si tienes dudas, pero quiero que hagas todo el proceso solo: leer el caso, explorar la app, buscar en el código, implementar y ejecutar."*

**El instructor:**
- No da respuestas directas — hace preguntas que guíen al suplente
- Si el suplente se bloquea por más de 5 minutos, da una pista (no la solución)
- Toma nota de los errores o dudas recurrentes para reforzarlos

**Preguntas de verificación al terminar:**
- ¿Cómo supiste qué tipo de dropdown era?
- ¿Por qué usaste `fillField` con `0`?
- ¿Qué harías si el test falla con `ElementClickIntercepted`?

---

## Bloque 7 — Cierre y acuerdos (15 min)

### Checklist final de la sesión

- [ ] El suplente sabe navegar TestRail y leer casos de prueba
- [ ] El suplente conoce la estructura del proyecto y sabe dónde buscar
- [ ] El suplente entiende los 4 patrones principales
- [ ] El suplente ejecutó al menos un test exitosamente
- [ ] El suplente automatizó un caso de forma supervisada
- [ ] El suplente sabe dónde está toda la documentación (`Claude/`)

### Acuerdos operativos

> *"Antes de que me vaya, quiero dejar claros estos acuerdos:"*

1. **TestRail:** avanzar los casos Untested en orden, uno por uno
2. **Código:** siempre buscar antes de crear — si algo ya existe, reutilizarlo
3. **Documentación:** actualizar `CHANGELOG.md` con cada caso que se automatice
4. **Dudas:** primero revisar `Claude/` — si no está documentado, contactarme por el canal acordado
5. **Errores en regresión:** si un test que antes pasaba empieza a fallar, documentarlo y notificar inmediatamente

### Canal de comunicación
- **Canal:** [completar — Slack / Teams / WhatsApp]
- **Horario de respuesta:** [completar]
- **Para bloqueos críticos:** [completar]

---

## Referencia rápida de errores comunes

| Error en consola | Qué significa | Cómo resolverlo |
|-----------------|--------------|-----------------|
| `NoSuchElementException: ant-steps-item-active` | `fillField` sin `int` en página sin stepper | Cambiar a `fillField(..., 0)` |
| `ElementClickIntercepted: ant-modal-wrap` | Click normal en botón dentro de modal | Cambiar a `jsClickAcceptButton()` |
| `NoSuchElementException` al navegar | Página no cargó a tiempo | Agregar `waitForElementPresence` antes del click |
| `StaleElementReferenceException` | El elemento cambió en el DOM | Usar `components.updateElement(element)` |
| Dropdown no selecciona la opción | Dropdown tipo portal, no inline | Usar click + `dynamicWebElement` |
| Test tarda 20+ segundos por campo | `fillField` sin `int` en remote grid | Usar `fillField(..., 0)` |
