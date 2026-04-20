# Guía de uso: Claude Code para este proyecto

Guía rápida para trabajar con Claude Code viniendo de Cursor.

---

## Equivalencias Cursor → Claude Code

| Cursor | Claude Code (VSCode) |
|---|---|
| `Ctrl+L` → seleccionar contexto | Seleccionar código en editor (Claude lo ve automáticamente) |
| `Ctrl+L` → adjuntar archivo | Escribir `@NombreArchivo` en el prompt |
| Instrucciones del proyecto | `CLAUDE.md` en la raíz (ya configurado) |

---

## Cómo dar contexto

### 1. Selección de código (equivalente a Ctrl+L)

Selecciona cualquier código en el editor → Claude ya lo ve en el chat automáticamente.
Para insertar la referencia con número de línea: **`Alt+K`**

```
[Seleccionas código en AdministratorMasterInter.java]
→ Claude ya tiene ese contexto visible en el chat
→ Presiona Alt+K para insertar: @AdministratorMasterInter.java#45-60
```

### 2. Menciones con `@` (adjuntar archivos)

Escribe `@` en el prompt para buscar y adjuntar archivos:

```
> Explica qué hace @AdministratorMasterInter.java
> ¿Cómo interactúan @LogInPage.java y @MenuPage.java?
> Muéstrame los tests en @autoonline-web/src/test/
> Revisa la suite @regressionSuite.xml
```

En VSCode: al escribir `@` aparece autocompletado con fuzzy search.

### 3. Referencias a líneas específicas

```
@AdministratorMasterInter.java#27       → línea 27
@AdministratorMasterInter.java#45-80    → líneas 45 a 80
```

### 4. Contexto de directorio

```
> Muéstrame los archivos en @autoonline-web/src/main/java/
> ¿Qué hay en @componentpages/?
```

---

## Contexto persistente: CLAUDE.md

El archivo `CLAUDE.md` en la raíz de este proyecto ya está configurado con:
- Stack del proyecto (Java 11, Maven, Selenium, TestNG)
- Estructura de directorios
- Comandos Maven para ejecutar tests
- Convenciones y patrones del código

Claude lo carga automáticamente en cada conversación — no necesitas repetir el contexto del proyecto cada vez.

---

## Atajos de teclado (VSCode Extension)

| Atajo | Función |
|---|---|
| `Alt+K` | Insertar @-mención con número de línea del código seleccionado |
| `@` en el prompt | Buscar y adjuntar archivo/carpeta con autocompletado |
| `Ctrl+V` | Pegar imagen como contexto (útil para capturas de error) |
| `/` en el prompt | Ver comandos disponibles |

---

## Comandos slash útiles

| Comando | Para qué sirve |
|---|---|
| `/memory` | Editar el CLAUDE.md o la memoria del proyecto |
| `/context` | Ver cuánto contexto se está usando (grid visual) |
| `/compact` | Compactar la conversación si se llena el contexto |
| `/cost` | Ver uso de tokens de la sesión |
| `/clear` | Limpiar el historial de la conversación |

---

## Ejemplos prácticos para este proyecto

```
# Entender una clase de página
> Explica el propósito de @AdministratorMasterInter.java y sus métodos principales

# Crear un nuevo Page Object
> Crea una clase de página para [NombrePagina] siguiendo el patrón de @LogInPage.java

# Revisar un test
> Analiza @regressionSuite.xml y dime qué tests incluye

# Debug de un problema
> El método en @MiClase.java#45-60 falla con este error: [pegar error]
> Compara la implementación de @Clase1.java con @Clase2.java

# Entender la estructura
> ¿Cómo se organiza @autoonline-web/src/main/java/?
```

---

## Flujo recomendado

**Para analizar/modificar código existente:**
1. Abre el archivo en el editor
2. Selecciona el método o bloque relevante
3. Escribe tu pregunta — Claude ya ve la selección
4. Si necesitas más contexto: agrega `@OtroArchivo` en el prompt

**Para crear código nuevo:**
1. Describe qué necesitas
2. Adjunta archivos de referencia con `@`: `> Crea X siguiendo el patrón de @ArchivoReferencia.java`

**Para entender el proyecto (ya no necesario, está en CLAUDE.md):**
- Claude ya sabe el stack, estructura y comandos desde el `CLAUDE.md`
