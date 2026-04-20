# Actualizar Credenciales en settings.xml

## ✅ Archivos copiados correctamente
Los archivos `settings.xml` y `settings-security.xml` ya están en `C:\Users\AndresValenciaMartín\.m2\`

## 🔐 Pasos para actualizar credenciales

### 1. Editar settings.xml
Abre el archivo: `C:\Users\AndresValenciaMartín\.m2\settings.xml`

### 2. Reemplazar las credenciales
Busca y reemplaza en TODAS las secciones `<server>`:
- `Your.Email@solera.com` → **Tu email real de Solera**
- `YOUR SOLERA ENCRYPTED PASSWORD` → **Tu contraseña encriptada de Solera**

**Importante:** Las contraseñas deben estar encriptadas. Si no tienes la contraseña encriptada:
1. Usa la herramienta de Maven para encriptar: `mvn --encrypt-password TU_PASSWORD`
2. O contacta al equipo que te compartió los archivos para obtener las credenciales correctas

### 3. Editar settings-security.xml (si es necesario)
Si tienes un master password, actualiza:
`C:\Users\AndresValenciaMartín\.m2\settings-security.xml`
- `YOUR OWN MASTER ENCRYPTED PASSWORD` → Tu master password encriptado

### 4. Probar la configuración
Después de actualizar, ejecuta:
```powershell
cd autoonline-web
mvn dependency:resolve -U
```

Si funciona, deberías ver que descarga `taf-core` y `taf-web` correctamente.

## 📝 Nota
Si no tienes las credenciales encriptadas, contacta al equipo de DevOps o al que te compartió los archivos para obtenerlas.

