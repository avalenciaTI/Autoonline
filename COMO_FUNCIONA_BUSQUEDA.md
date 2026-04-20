# Cómo funciona la búsqueda y validación en CommonComponents

Este documento explica los 3 métodos que participan en el bloque de búsqueda
y validación de un usuario recién creado.

---

## El bloque completo

```java
new CommonComponents().findHRefElement(new CommonComponents()
        .dynamicWebElement(SEARCH_DYNAMIC, getLastGeneratedName()));

Integer correct = new CommonComponents().validateCaseCreation(consultSearch);

log().image("correct: " + correct, takeScreenshot());
```

---

## 1. `dynamicWebElement(xpath, params)`

### Qué hace
Reemplaza el comodín `?` en un XPath con el valor real que le pasas,
y devuelve el `WebElement` encontrado en el DOM.

### Código
```java
public WebElement dynamicWebElement(String xpath, String params) {
    String newxPath = xpath.replace("?", params);
    return getBrowser().getDriver().findElement(By.xpath(newxPath));
}
```

### Cómo funciona paso a paso
```
SEARCH_DYNAMIC = "//td[text()='?']"
getLastGeneratedName() = "Automatizacion A1BK3X9M"

1. xpath.replace("?", "Automatizacion A1BK3X9M")
   → "//td[text()='Automatizacion A1BK3X9M']"

2. findElement(By.xpath("//td[text()='Automatizacion A1BK3X9M']"))
   → Devuelve el <td> de la tabla cuyo texto exacto es ese nombre
```

### Ejemplo visual en el DOM
```html
<table>
  <tbody>
    <tr>
      <td><a href="/users/123">VER</a></td>   ← esto es preceding-sibling
      <td>Automatizacion A1BK3X9M</td>        ← esto es lo que encuentra
      <td>supplier_crane@test.com</td>
    </tr>
  </tbody>
</table>
```

---

## 2. `findHRefElement(searchCriteria)`

### Qué hace
Recibe el `<td>` encontrado por `dynamicWebElement`, navega hacia el link
de acción de esa misma fila, y hace click en él usando el `<tBody>` correcto
para evitar conflictos si hay más de una tabla en la página.

### Código
```java
public void findHRefElement(WebElement searchCriteria) {
    // PASO 1: desde el <td> del nombre, busca el <a> en el <td> anterior
    WebElement href2Select2 = searchCriteria
            .findElement(By.xpath("preceding-sibling::td//a"));
    String valueHref = href2Select2.getText();

    // PASO 2: cuenta cuántos <tBody> hay en la página
    Integer index = getDriver().findElements(By.xpath("//tBody")).size();

    // PASO 3: busca el link en el tBody correcto y hace click
    WebElement hrefTable1 = new CommonComponents()
            .dynamicWebElement("(//tBODY)[" + index + "]//td//a[contains(text(),'?')]", valueHref);
    click(hrefTable1);
}
```

### Cómo funciona paso a paso

```
PASO 1 — encontrar el link de la fila
──────────────────────────────────────
searchCriteria = <td>Automatizacion A1BK3X9M</td>

preceding-sibling::td//a
→ busca hacia atrás en los <td> hermanos de esa fila
→ encuentra: <td><a href="/users/123">VER</a></td>
→ valueHref = "VER"  (el texto del link)


PASO 2 — contar tablas
───────────────────────
Si la página tiene 2 <tBody> → index = 2
Si tiene 1 → index = 1


PASO 3 — click en el link correcto
────────────────────────────────────
Construye: "(//tBODY)[2]//td//a[contains(text(),'VER')]"
→ Busca el link "VER" dentro del último tBody
→ Hace click → abre el detalle del usuario
```

### Por qué se usa el índice del tBody
> En AutoOnline a veces la página renderiza 2 tablas simultáneamente
> (una de cabecera o filtros y otra de resultados). Usar `(//tBODY)[n]`
> garantiza que se hace click en la tabla correcta (la última, la de resultados).

---

## 3. `validateCaseCreation(completeWebElements)`

### Qué hace
Compara los valores que se guardaron durante la creación del usuario
(`consultSearch`) contra lo que realmente aparece en pantalla en el DOM.
Devuelve cuántos campos coinciden correctamente.

### Código simplificado
```java
public Integer validateCaseCreation(List<CompleteWebElement> completeWebElements) {
    int numberOfFieldsCorrect = 0;

    for (CompleteWebElement element : completeWebElements) {
        String valueDOM    = getWebElementDOMValues(element.getWebElement());
        String valueStored = element.getDesiredValue();

        if (valueDOM.equalsIgnoreCase(valueStored)) {
            numberOfFieldsCorrect++;  // ✓ coincide
        } else {
            log.error("Mismatch: DOM=" + valueDOM + " | Stored=" + valueStored);
        }
    }
    return numberOfFieldsCorrect;
}
```

### Qué es `consultSearch` / `CompleteWebElement`
`consultSearch` es la lista que devuelve `fastSupplierCraneUserCreation()`.
Cada vez que se llama a `components.fillField(campo, valor, storedValues)`,
ese método guarda un `CompleteWebElement` con:

| Propiedad       | Contenido                                      |
|----------------|------------------------------------------------|
| `webElement`   | Referencia al campo del formulario en el DOM   |
| `desiredValue` | El valor que se escribió (ej. "Automatizacion A1BK3X9M") |
| `indexTab`     | En qué pestaña/step del formulario estaba      |

### Ejemplo del flujo completo

```
Creación:
  fillField(nameField, "Automatizacion A1BK3X9M", list)
  fillField(surnameField, "Zarate Delete", list)
  fillField(emailField, "testautomation1a2b@test.com", list)
  → consultSearch = [ {nameField, "Automatizacion A1BK3X9M", tab1},
                      {surnameField, "Zarate Delete", tab1},
                      {emailField, "testautomation1a2b@test.com", tab2} ]

Validación (validateCaseCreation):
  Lee nameField del DOM   → "Automatizacion A1BK3X9M" ✓ (+1)
  Lee surnameField del DOM → "Zarate Delete"          ✓ (+1)
  Lee emailField del DOM   → "testautomation1a2b@test.com" ✓ (+1)

  correct = 3  → todos los campos coinciden
```

---

## Flujo completo del bloque

```
[1] getLastGeneratedName()
    → "Automatizacion A1BK3X9M"

[2] dynamicWebElement(SEARCH_DYNAMIC, "Automatizacion A1BK3X9M")
    → XPath: "//td[text()='Automatizacion A1BK3X9M']"
    → Devuelve el <td> con ese nombre en la tabla de resultados

[3] findHRefElement(<td>)
    → Encuentra el link de acción en la misma fila
    → Hace click → abre el detalle del usuario

[4] validateCaseCreation(consultSearch)
    → Compara campo por campo lo guardado vs lo que hay en el DOM
    → Devuelve número de campos correctos

[5] log().image("correct: " + correct, screenshot)
    → Guarda evidencia del resultado en el reporte
```

---

## Resumen rápido

| Método | Entrada | Salida | Para qué |
|---|---|---|---|
| `dynamicWebElement(xpath, valor)` | XPath con `?` + valor real | `WebElement` encontrado | Localizar un elemento dinámicamente |
| `findHRefElement(td)` | Un `<td>` de la tabla | void (hace click) | Abrir el detalle del resultado |
| `validateCaseCreation(list)` | Lista de campos guardados | `int` campos correctos | Verificar que los datos se guardaron bien |
