# Solución: Error "The import com.solera.global.qa.taf cannot be resolved"

## Problema
El IDE no puede resolver los imports porque las dependencias `taf-core` y `taf-web` no están disponibles.

## Soluciones (en orden de preferencia)

### 1. Conectar VPN de Solera y refrescar proyecto Maven
```powershell
# Desde el directorio raíz del proyecto
mvn clean install -DskipTests
```
Luego en tu IDE (IntelliJ/Eclipse):
- **IntelliJ**: Click derecho en `pom.xml` → Maven → Reload Project
- **Eclipse**: Click derecho en proyecto → Maven → Update Project

### 2. Si tienes los JARs localmente
Instala las dependencias manualmente:
```powershell
mvn install:install-file -Dfile=taf-core-2.0.0.jar -DgroupId=com.solera.global.qa.taf -DartifactId=taf-core -Dversion=2.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=taf-web-2.0.0.jar -DgroupId=com.solera.global.qa.taf -DartifactId=taf-web -Dversion=2.0.0 -Dpackaging=jar
```

### 3. Verificar configuración de repositorio
Si el hostname de Nexus es diferente, actualiza `pom.xml` línea 110 con el URL correcto.

### 4. Configurar settings.xml con credenciales
Copia `settings.xml.example` a `C:\Users\TU_USUARIO\.m2\settings.xml` y agrega tus credenciales.


