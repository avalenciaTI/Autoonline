---
name: TestRail context
description: Test Run FrontEnd activo en TestRail — secciones, casos y estado de avance
type: project
---

## Test Run activo
- **URL:** https://testrail.solera.com/index.php?/runs/view/52560
- **Nombre:** FrontEnd (R52560)
- **Plan:** AutoOnline_Cases2Automate (plan/52559)
- **Suite:** Suite 1182
- **Creado por:** Ernesto Zarate (21/11/2025)

## Estado global: 133 casos totales, 106 untested (80%)
- 18 Passed (14%), 1 Failed (1%), 8 COK (6%), 0 Blocked

## Secciones y casos (con estado)
| Sección | Total | Passed | Untested | Failed | COK |
|---------|-------|--------|----------|--------|-----|
| Regression | ~23 | 8 | 6 | 1 | 8 |
| Users | 3 | 3 | 0 | 0 | 0 |
| Login | ~23 | 7 | 16 | 0 | 0 |
| Cases | ~23 | 7 | 16 | 0 | 0 |
| Catalog | 5 | 0 | 5 | 0 | 0 |
| Transfers | 5 | 0 | 5 | 0 | 0 |
| Inventory | 6 | 0 | 6 | 0 | 0 |
| Publications | 6 | 0 | 6 | 0 | 0 |
| Awards | 3 | 0 | 3 | 0 | 0 |
| Payments | 4 | 0 | 4 | 0 | 0 |
| paymentFile | 30 | 0 | 30 | 0 | 0 |
| Reports > Cases | 8 | 0 | 8 | 0 | 0 |
| Reports > Publications | 2 | 0 | 2 | 0 | 0 |
| Reports > Payments | 2 | 0 | 2 | 0 | 0 |
| Awarding Buyer | 3 | 0 | 3 | 0 | 0 |
| Documents | ~3 | 0 | ~3 | 0 | 0 |

---

## Casos automatizados en esta sesión

### CP022 — Invite individual buyers ✅ COMPLETADO
- **TestRail ID:** 3936386
- **Clase de test:** `TestLogInBasic.java` → método `tc22_inviteIndividualBuyer()` (priority = 22)
- **Page Object principal:** `AdministratorMasterInter.java`
- **Métodos creados:**
  - `inviteIndividualBuyer(AolWebUser user)` → flujo completo (navega + invita + verifica notificación)
  - `fastInviteBuyerIndividual()` → llena formulario de invitación individual
- **Flujo implementado:**
  1. `menuPage.clickUsers()` → navega a sección Users
  2. `waitForElementPresence` + `click(INVITE_BUYERS)` → abre página de invitación
  3. `waitForElementPresence(INVITATION_SEND_TYPE)` → espera carga de la página
  4. Click en dropdown portal `individual_option` → abre opciones
  5. `dynamicWebElement(INVITATION_DROPDOWN_OPTION, "Individual")` → selecciona tipo Individual
  6. `fillField(..., 0)` × 5 campos (firstname, surname, lastname, email, phone)
  7. `waitForElementVisibility(acceptButton)` + `clickAcceptButton()` → envía formulario
  8. `waitForElementVisibility(invitationModalField)` → espera modal de confirmación
  9. `jsClickAcceptButton()` → confirma en modal (jsClick por ant-modal-wrap overlay)
  10. `waitForElementVisibility(NOTIFICATION_MESSAGE)` → valida mensaje de éxito
- **Constantes creadas en CommonUsersFields:**
  `INVITATION_SEND_TYPE`, `INVITATION_DROPDOWN_OPTION`, `INVITATION_FIRSTNAME`,
  `INVITATION_SURNAME`, `INVITATION_LASTNAME`, `INVITATION_EMAIL`, `INVITATION_PHONE`, `INVITATION_MODAL`
- **Métodos creados en Buttons:**
  `jsClickAcceptButton()` — para botones Aceptar dentro de modales Ant Design
  `getAcceptButton()` — getter del WebElement para waitForElementVisibility externo
- **Lecciones aprendidas:**
  - `individual_option` es dropdown tipo portal → opciones en body, NO siblings → usar `dynamicWebElement`
  - `fillField` sin `int` falla en páginas sin stepper (`ant-steps-item-active` no existe)
  - Overload `fillField(element, value, list, 0)` es 2x más rápido en remote grid (evita `getIndex()`)
  - Segundo botón Aceptar en modal necesita `jsClick` → `ElementClickIntercepted` con click normal

---

## Cambios a clases compartidas realizados en esta sesión

### CommonComponents.java
1. **`fillField` overload nuevo:** `fillField(WebElement, String, List, int indexTab)`
   - Salta `getTagName()` y `getIndex()` — solo hace `sendKeys` + guarda en lista
   - Usar cuando la página NO tiene stepper `ant-steps`
   - 2 round-trips al grid por campo vs 4 del original
2. **`getIndex()` corregido:** usa `findElements` (en lugar de `findElement`) para `ant-steps-item-active`
   - Devuelve `0` si no hay stepper en lugar de lanzar `NoSuchElementException`
3. **Línea de código muerto eliminada:** `getDriver().findElements(...)` sin usar resultado en `fillField`

### CommonUsersFields.java
- Agregadas 8 constantes del grupo `INVITATION_*`
- Agregados 7 `@FindBy` + 7 getters para campos de invitación de compradores

### Buttons.java
- `ACCEPT_BUTTON` actualizado a `"(//span[text()='Aceptar'])[1]"` (primer Aceptar)
- `ACCEPT_BUTTON_SECOND` nuevo = `"(//span[text()='Aceptar'])[2]"` (segundo Aceptar en modal)
- `getAcceptButton()` getter nuevo
- `getAcceptButton_Second()` getter nuevo
- `jsClickAcceptButton()` método nuevo — waitForClickable + jsClick + sleep(LOADER)

---

## Casos anteriores ya implementados (referencia)
- **CP001** (tc01) — `individualRegistrationAdminIntern` — Admin Interno
- **CP002–CP021** — varios flujos de usuarios, casos, publicaciones (ya existían antes de esta sesión)

## Why / How to apply
**Why:** El usuario trabaja directamente desde TestRail, leyendo los casos uno a uno para luego automatizarlos.
**How to apply:** Al recibir un nuevo caso CP0XX, verificar primero:
1. Si ya existe implementación en `TestLogInBasic.java` u otra clase de test
2. Si los elementos UI ya están en `CommonUsersFields`, `Buttons`, u otro componentpage
3. Si el flujo de navegación ya está en `AdministratorMasterInter` u otro Page Object
Solo crear código nuevo para lo que genuinamente no existe.
