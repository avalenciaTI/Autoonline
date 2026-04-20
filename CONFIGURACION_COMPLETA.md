# Configuración Completa para Ejecutar Tests

## ✅ Cambios Realizados

### 1. Repositorios Maven Agregados al POM
He agregado la configuración de repositorios al `pom.xml` principal:
- ✅ Maven Central (para dependencias públicas)
- ✅ Solera Nexus Releases (para taf-core, taf-web, etc.)
- ✅ Solera Nexus Snapshots (para versiones de desarrollo)

### 2. Archivo settings.xml de Ejemplo
Creado `settings.xml.example` con la configuración necesaria para autenticación con Nexus.

## 🔧 Pasos para Ejecutar el Test

### Paso 1: Verificar Acceso a Nexus

El repositorio está en: `https://nexus.audatex.com/nexus`

**Opciones:**
- Si estás en la red de Solera: Deberías poder acceder directamente
- Si estás fuera: Necesitas conectarte a la VPN de Solera
- Verifica el hostname correcto con tu equipo de DevOps

### Paso 2: Configurar Autenticación (si es necesario)

Si el repositorio requiere autenticación:

1. Copia `settings.xml.example` a `C:\Users\TU_USUARIO\.m2\settings.xml`
2. Edita y reemplaza:
   - `TU_USUARIO_NEXUS` → Tu usuario de Nexus
   - `TU_PASSWORD_NEXUS` → Tu contraseña

### Paso 3: Ejecutar el Test

#### Opción A: Desde el IDE (Más fácil)
1. Abre `SmokeTest.java` en IntelliJ IDEA o Eclipse
2. Clic derecho en la clase o método `tc1_adjudicatePublicationToBuyer()`
3. Selecciona "Run"

#### Opción B: Desde Maven (Línea de comandos)

**Ejecutar suite completa de smoke:**
```powershell
cd autoonline-web
mvn clean test -Pautoonline:web:smoke
```

**Ejecutar solo la clase SmokeTest:**
```powershell
cd autoonline-web
mvn clean test -Dtest=SmokeTest
```

**Ejecutar solo un método específico:**
```powershell
cd autoonline-web
mvn clean test -Dtest=SmokeTest#tc1_adjudicatePublicationToBuyer
```

## 🔍 Verificar Configuración

Para verificar que Maven puede resolver las dependencias:

```powershell
cd autoonline-web
mvn dependency:resolve -U
```

Si ves errores de "No such host is known":
- ✅ Verifica tu conexión a la VPN de Solera
- ✅ Verifica que puedes acceder a `https://nexus.audatex.com/nexus` en el navegador
- ✅ Contacta al equipo de DevOps para verificar el hostname correcto

## 📋 Información del Test

- **Clase:** `SmokeTest`
- **Método:** `tc1_adjudicatePublicationToBuyer()`
- **TC ID:** 160970
- **Nombre:** CP055_Adjudicate a unit.
- **Tipo:** SMOKE
- **Suite:** `smokeSuite.xml`

## 🆘 Solución de Problemas

### Error: "Could not resolve dependencies"
- Verifica acceso a VPN de Solera
- Verifica configuración de `settings.xml`
- Verifica que el hostname de Nexus es correcto

### Error: "No such host is known"
- Conéctate a la VPN de Solera
- Verifica el hostname con el equipo de DevOps

### Error: "401 Unauthorized"
- Configura las credenciales en `settings.xml`
- Verifica que tu usuario tiene acceso al repositorio

## 📞 Contacto

Si necesitas ayuda adicional:
- Revisa `EJECUTAR_TEST.md` para más opciones
- Contacta al equipo de DevOps de Solera
- Revisa la documentación interna de Solera sobre acceso a Nexus


