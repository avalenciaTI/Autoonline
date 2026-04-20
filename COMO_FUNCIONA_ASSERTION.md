# Cómo funciona la Assertion en los Tests

Este documento explica cómo funciona la cadena de assertion en `TestUsersRegistration`,
por qué el test puede reportar FAILED aunque el flujo "parezca" completo,
y cómo leer el resultado correctamente.

---

## La línea completa

```java
// TestUsersRegistration.java — línea 165
assertions().assertThat(adminMasterInter().advancedRegistrationSupplierCrane(master))
        .as("USER CREATION SUCCESS")
        .isTrue();
```

---

## Quién es quién: los 4 participantes

| Pieza | Qué es | De dónde viene |
|---|---|---|
| `assertions()` | Objeto de AssertJ para hacer validaciones | `TestBase` del TAF framework |
| `adminMasterInter()` | Factory — devuelve `new AdministratorMasterInter()` | `WebTestBase.java:342` |
| `advancedRegistrationSupplierCrane(master)` | El flujo completo de UI — devuelve `boolean` | `AdministratorMasterInter.java:608` |
| `.as("USER CREATION SUCCESS").isTrue()` | La verificación final: exige que el valor sea `true` | AssertJ |

---

## Paso a paso: cómo se ejecuta

```
[1] JVM evalúa el argumento de assertThat(...)
    │
    ├─ adminMasterInter()
    │   └─ crea new AdministratorMasterInter()
    │
    └─ .advancedRegistrationSupplierCrane(master)
        ├─ Crea usuario en la UI
        ├─ Busca el usuario creado
        ├─ Valida campos en el detalle
        └─ return true;   ← siempre devuelve true (hardcoded)

[2] assertions().assertThat( true )
    └─ Construye un objeto AbstractBooleanAssert con el valor 'true'

[3] .as("USER CREATION SUCCESS")
    └─ Etiqueta descriptiva que aparece si el test falla

[4] .isTrue()
    └─ Verifica: ¿el valor es true?
       → SI  → test PASS ✓
       → NO  → lanza AssertionError → test FAIL ✗
```

---

## ¿Por qué falla si el método siempre devuelve `true`?

Esta es la parte más importante. Hay **dos formas distintas** en que un test puede fallar:

### Forma 1 — Assertion Failure (fallo de validación)
```
El método termina → devuelve false → .isTrue() lanza AssertionError
```
Mensaje en reporte: `AssertionError: [USER CREATION SUCCESS] expected true but was false`

### Forma 2 — Exception Failure (excepción en el flujo) ← LO QUE PASA AQUÍ
```
El método EXPLOTA con una excepción antes de llegar al return true
→ La excepción sube hasta TestNG
→ TestNG marca el test como FAILED
→ NUNCA se llega a ejecutar .isTrue()
```
Mensaje en reporte: `NoSuchElementException: //td[text()='Automatizacion RBDT40JZ']`

```
advancedRegistrationSupplierCrane(master)
  │
  ├─ crea usuario ✓
  ├─ busca en menú ✓
  ├─ swapAdvancedSearch ✓
  │
  └─ dynamicWebElement(SEARCH_ADVANCED, getLastGeneratedName())
      │
      └─ ¡BOOM! NoSuchElementException ← el flujo muere aquí
         → nunca llega a return true
         → nunca llega a .isTrue()
         → TestNG reporta FAILED con la excepción
```

---

## ¿Por qué el flujo parece "bien" pero falla?

En el reporte verás screenshots de cada paso anterior al error — el usuario
se creó, la notificación apareció, la búsqueda se ejecutó, el swapSearch se hizo.
Todo eso SE ejecutó correctamente. El problema es el **último paso**: localizar
el `<td>` con el nombre en la tabla de resultados.

```
Timeline del reporte results.html:

  3:29:50 → Nombre escrito en búsqueda, SearchBtn pulsado    ✓
  3:29:51 → swapAdvancedSearch tomó screenshot               ✓
  3:30:01 → NoSuchElementException (10 seg de espera agotados) ✗
```

---

## ¿Qué hace `validateCaseCreation` y por qué no afecta el resultado?

```java
Integer correct = new CommonComponents().validateCaseCreation(consultSearch);
// ↑ devuelve cuántos campos coinciden (ej. 3 de 3)

log().image("correct: " + correct, takeScreenshot());
// ↑ solo lo loguea, no lo usa para nada más

return true;  // ← SIEMPRE true, sin importar el valor de 'correct'
```

### Consecuencia importante
El método devuelve `true` aunque `correct = 0` (ningún campo validado).
`validateCaseCreation` sirve como **evidencia visual** en el reporte (screenshot),
no como criterio de éxito del test.

Si quisieras que el test falle cuando los campos no coincidan, el código debería ser:
```java
// Hipotético — cómo se verificaría el conteo de campos
Integer correct = new CommonComponents().validateCaseCreation(consultSearch);
log().image("correct: " + correct, takeScreenshot());
return correct == consultSearch.size();  // true solo si TODOS los campos coinciden
```
Pero eso es una decisión de diseño del equipo, no un bug.

---

## Resumen visual del flujo completo

```
Test tc21_craneAdministratorGeneralQuery
│
├─ [Login] loginPage().logIn(master)
│
└─ [Assertion] assertions().assertThat(...)
    │
    └─ adminMasterInter().advancedRegistrationSupplierCrane(master)
        │
        ├─ Crea usuario Supplier Crane  ──────────────────────── ✓
        ├─ Espera notificación           ──────────────────────── ✓
        ├─ Cierra notificación           ──────────────────────── ✓
        ├─ clickUsersConsultAdvance()    ──────────────────────── ✓
        │   └─ escribe nombre + click Search
        ├─ swapAdvancedSearch()          ──────────────────────── ✓
        │   └─ alterna búsqueda avanzada
        │
        └─ dynamicWebElement(SEARCH_DYNAMIC, getLastGeneratedName())
            └─ busca //td[text()='Automatizacion XXXXX']  ─────── ✗ NoSuchElementException
               ↓
               Excepción no capturada
               ↓
               TestNG → FAILED
               ↓
               .isTrue() NUNCA SE EJECUTA
```

---

## Tabla de escenarios posibles

| Lo que pasa en el flujo | Resultado en el test | Por qué |
|---|---|---|
| Todo OK, `return true` | **PASS** | `isTrue()` recibe `true` |
| `NoSuchElementException` en medio | **FAILED** (excepción) | Nunca llega al `return` |
| `return false` (si lo hubiera) | **FAILED** (assertion) | `isTrue()` recibe `false` |
| `NullPointerException` en medio | **FAILED** (excepción) | Nunca llega al `return` |
| `correct = 0` pero llega al `return true` | **PASS** | `isTrue()` recibe `true` |
