# Proyecto: sg-qa-autoonline-mx

Framework de automatización QA para la aplicación AutoOnline México (Solera Inc).

## Stack
- **Lenguaje:** Java 11
- **Build:** Maven
- **Testing:** TestNG
- **Web automation:** Selenium WebDriver (Page Object Model)
- **REST testing:** TAF (Solera QA Test Automation Framework)
- **Utilidades:** Lombok, SLF4J

## Módulos
- `autoonline-web/` → Tests UI/Selenium (Page Object Model)
- `autoonline-rest/` → Tests de API REST

## Estructura de autoonline-web
```
src/main/java/.../behavior/pages/
  ├── loginpage/          → Página de login
  ├── menupage/           → Navegación/menú principal
  ├── usercreation/       → Creación de usuarios (individual, masiva)
  ├── casecreation/       → Creación de casos
  ├── publications/       → Publicaciones
  ├── payments/           → Pagos
  ├── reports/            → Reportes
  └── componentpages/     → Componentes reutilizables (Buttons, CommonComponents, etc.)

src/test/java/           → Clases de test (TestNG)
src/test/resources/suites/
  ├── regressionSuite.xml
  ├── smokeSuite.xml
  ├── paymentSuite.xml
  ├── publicationSuite.xml
  ├── reportsSuite.xml
  ├── transferSuite.xml
  └── awardsSuite.xml
```

## Comandos Maven principales

### Build (sin tests)
```bash
mvn clean install -DskipTests
```

### Web - Regression
```bash
mvn clean test -Denv=development -Dapp.config=development -Dexec.env=remote.grid \
  -Pautoonline:web:regression -Dtestrail.enabled=false -Daws.s3.enabled=false \
  -DtestRunName=AUTOONLINE_MX_AUTO_REGRESSION_TEST -Dbrowser=chrome -f pom.xml
```

### Web - Sanity
```bash
mvn clean test -Denv=development -Dapp.config=development -Dexec.env=remote.grid \
  -Pautoonline:web:sanity -Dtestrail.enabled=false -Daws.s3.enabled=false \
  -DtestRunName=AUTOONLINE_MX_AUTO_REGRESSION_TEST -Dbrowser=chrome -f pom.xml
```

### Web - Debug (ejecución local)
```bash
mvn clean test -Denv=development -Dapp.config=development -Dexec.env=remote.grid \
  -Pautoonline:web:debug -Dtestrail.enabled=false -Daws.s3.enabled=false \
  -DtestRunName=AUTOONLINE_MX_AUTO_REGRESSION_TEST -Dbrowser=chrome -f pom.xml
```

### REST - Regression
```bash
mvn clean test -Ptemplate:rest:regression -DtestRunName=LocalExecution -f pom.xml
```

## Convenciones y patrones
- **Page Object Model**: cada página/sección tiene su propia clase en `behavior/pages/`
- Las clases de página extienden `BrowserPage` (del TAF framework)
- Usar `@Slf4j` de Lombok para logging (`log.info(...)`, `log.error(...)`)
- XPath dinámico con `?` como placeholder (e.g., `"//td[text()='?']"`)
- Los datos de prueba y tipos se definen en `behavior/data/`
- Componentes reutilizables en `componentpages/` (Buttons, CommonComponents, CommonSearch, etc.)

## Documentación adicional
- `README.md` → Comandos de ejecución principales
- `CONFIGURACION_COMPLETA.md` → Configuración del entorno
- `EJECUTAR_DEBUG.md` → Ejecución en modo debug
- `EJECUTAR_TEST.md` → Ejecución de tests
- `ACTUALIZAR_CREDENCIALES.md` → Gestión de credenciales
