# Ejecutar Tests en Modo Debug

## Opción 1: Debug con Maven (Espera conexión del debugger)

```powershell
cd autoonline-web
mvn clean test "-Denv=development" "-Dapp.config=development" "-Dexec.env=local" "-Dtest=TestLogInBasic#tc4_caseVehicleLoadFiles" "-Dtestrail.enabled=false" "-Daws.s3.enabled=false" "-DtestRunName=TEST_LOAD_FILES" "-Dbrowser=chrome" "-Dmaven.surefire.debug"
```

Este comando:
- Pausa la ejecución y espera que te conectes con el debugger
- Escucha en el puerto **5005** por defecto
- En tu IDE, configura una "Remote Debug" connection apuntando a `localhost:5005`

## Opción 2: Debug con puerto personalizado

```powershell
cd autoonline-web
mvn clean test "-Denv=development" "-Dapp.config=development" "-Dexec.env=local" "-Dtest=TestLogInBasic#tc4_caseVehicleLoadFiles" "-Dtestrail.enabled=false" "-Daws.s3.enabled=false" "-DtestRunName=TEST_LOAD_FILES" "-Dbrowser=chrome" "-Dmaven.surefire.debug=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```

## Opción 3: Desde el IDE (Más fácil)

1. Abre `TestLogInBasic.java`
2. Pon un breakpoint en la línea que quieras (ej: línea 75)
3. Clic derecho en el método `tc4_caseVehicleLoadFiles()` → **Debug 'tc4_caseVehicleLoadFiles()'**

## Configurar Remote Debug en IntelliJ IDEA

1. Run → Edit Configurations
2. Click "+" → Remote JVM Debug
3. Host: `localhost`
4. Port: `5005`
5. Click Debug

## Configurar Remote Debug en Eclipse

1. Run → Debug Configurations
2. Remote Java Application → New
3. Host: `localhost`
4. Port: `5005`
5. Click Debug


