### TEMPLATE

This automation framework is composed by two modules: template-web and template-rest.

#### Run config used:

- Build command:
- clean install -DskipTests
- Web Regression:
- clean test -Denv=development -Dapp.config=development -Dexec.env=remote.grid -Pautoonline:web:regression -Dtestrail.enabled=false -Daws.s3.enabled=false -DtestRunName=AUTOONLINE_MX_AUTO_REGRESSION_TEST -Dbrowser=chrome -f pom.xml
- Web Sanity:
- clean test -Denv=development -Dapp.config=development -Dexec.env=remote.grid -Pautoonline:web:sanity -Dtestrail.enabled=false -Daws.s3.enabled=false -DtestRunName=AUTOONLINE_MX_AUTO_REGRESSION_TEST -Dbrowser=chrome -f pom.xml
- Web Debug:
- clean test -Denv=development -Dapp.config=development -Dexec.env=remote.grid -Pautoonline:web:debug -Dtestrail.enabled=false -Daws.s3.enabled=false -DtestRunName=AUTOONLINE_MX_AUTO_REGRESSION_TEST -Dbrowser=chrome -f pom.xml
- Rest Regression
- clean test -Ptemplate:rest:regression -DtestRunName=LocalExecution -f pom.xml
