# Cómo ejecutar el caso de prueba SmokeTest

## Opción 1: Ejecutar con Maven usando el perfil smoke

Desde el directorio raíz del proyecto:
```bash
cd autoonline-web
mvn clean test -Pautoonline:web:smoke
```

O desde el directorio raíz:
```bash
mvn clean test -Pautoonline:web:smoke -pl autoonline-web
```

## Opción 2: Ejecutar solo la clase SmokeTest específica

Desde el directorio `autoonline-web`:
```bash
mvn clean test -Dtest=SmokeTest
```

## Opción 3: Ejecutar un método específico del test

Para ejecutar solo el método `tc1_adjudicatePublicationToBuyer`:
```bash
mvn clean test -Dtest=SmokeTest#tc1_adjudicatePublicationToBuyer
```

## Opción 4: Ejecutar desde el IDE (IntelliJ IDEA / Eclipse)

1. Abre el archivo `SmokeTest.java`
2. Haz clic derecho en la clase o en el método `tc1_adjudicatePublicationToBuyer`
3. Selecciona "Run 'SmokeTest'" o "Run 'tc1_adjudicatePublicationToBuyer()'"

## Opción 5: Ejecutar usando TestNG directamente

Si tienes TestNG instalado:
```bash
java -cp "target/test-classes;target/classes;[todas las dependencias]" org.testng.TestNG src/test/resources/suites/smokeSuite.xml
```

## Configuración de Repositorios Maven

### Problema: Dependencias faltantes (taf-core, taf-web)

Las dependencias `taf-core` y `taf-web` son internas de Solera y están en el repositorio Nexus corporativo.

### Solución 1: Configurar settings.xml (Recomendado)

1. Copia el archivo `settings.xml.example` a tu directorio `.m2`:
   - Windows: `C:\Users\TU_USUARIO\.m2\settings.xml`
   - Linux/Mac: `~/.m2/settings.xml`

2. Edita el archivo y reemplaza:
   - `TU_USUARIO_NEXUS` con tu usuario de Nexus
   - `TU_PASSWORD_NEXUS` con tu contraseña de Nexus

3. Si no tienes credenciales, contacta al equipo de DevOps o revisa la documentación interna de Solera.

### Solución 2: Usar el POM con repositorios (Ya configurado)

El `pom.xml` principal ya tiene configurados los repositorios de Solera. Si aún tienes problemas:

1. Verifica que tienes acceso a la red de Solera/VPN
2. Verifica que puedes acceder a: `https://nexus.audatex.com/nexus`
3. Si el repositorio requiere autenticación, usa la Solución 1 (settings.xml)

### Verificar configuración

Para verificar que Maven puede acceder a los repositorios:
```bash
mvn dependency:resolve -U
```

Esto intentará descargar todas las dependencias y te mostrará si hay problemas de acceso.

