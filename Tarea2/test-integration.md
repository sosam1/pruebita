# Guía de Pruebas de Integración - Tarea 2

## Prerequisitos

1. **Backend de Tarea 1 ejecutándose** en `http://localhost:8080`
2. **Tarea 2 desplegada** en `http://localhost:8081/biblioteca-web`

## Pasos para Probar la Integración

### 1. Verificar que el Backend esté funcionando

Abre una terminal y ejecuta:
```bash
cd Tarea1
mvn tomcat7:run
```

Verifica que veas estos mensajes:
```
http://localhost:8080/materiales
http://localhost:8080/usuarios
http://localhost:8080/prestamos
```

### 2. Ejecutar la Tarea 2

En otra terminal:
```bash
cd Tarea2
mvn tomcat7:run
```

La aplicación estará disponible en: `http://localhost:8081/biblioteca-web`

### 3. Pruebas de Funcionalidad

#### 3.1 Página Principal
- [ ] Acceder a `http://localhost:8081/biblioteca-web`
- [ ] Verificar que se carga la página principal
- [ ] Probar la navegación entre secciones

#### 3.2 Gestión de Materiales
- [ ] Ir a "Ver Materiales"
- [ ] Verificar que se cargan los materiales del backend
- [ ] Probar el filtro por rango de fechas
- [ ] Ir a "Agregar Material"
- [ ] Probar agregar un libro nuevo
- [ ] Probar agregar un artículo especial nuevo

#### 3.3 Gestión de Usuarios
- [ ] Ir a "Ver Usuarios"
- [ ] Verificar que se cargan los usuarios del backend
- [ ] Ir a "Agregar Usuario"
- [ ] Probar agregar un lector nuevo
- [ ] Probar agregar un bibliotecario nuevo

#### 3.4 Gestión de Préstamos
- [ ] Ir a "Ver Préstamos"
- [ ] Verificar que se cargan los préstamos del backend
- [ ] Probar el filtro de préstamos pendientes
- [ ] Probar el filtro por zona
- [ ] Probar el filtro por lector
- [ ] Ir a "Nuevo Préstamo"
- [ ] Probar crear un préstamo nuevo

### 4. Pruebas de Error

#### 4.1 Backend No Disponible
- [ ] Detener el backend de Tarea 1
- [ ] Intentar cargar materiales
- [ ] Verificar que se muestra un mensaje de error apropiado

#### 4.2 Datos Inválidos
- [ ] Intentar agregar un material sin datos requeridos
- [ ] Verificar que se muestran mensajes de validación
- [ ] Intentar agregar un usuario con correo inválido
- [ ] Verificar manejo de errores

### 5. Verificación de Logs

Revisar los logs del servidor para:
- [ ] Conexiones exitosas al backend
- [ ] Errores de comunicación
- [ ] Operaciones CRUD completadas

## Comandos Útiles

### Compilar y Ejecutar Tarea 2
```bash
cd Tarea2
mvn clean compile
mvn tomcat7:run
```

### Generar WAR
```bash
mvn clean package
```

### Ver Logs
```bash
tail -f target/tomcat/logs/catalina.out
```

## Solución de Problemas Comunes

### Error: "No se pudo conectar con el servicio"
- Verificar que el backend esté ejecutándose
- Comprobar que los puertos no estén ocupados
- Revisar la configuración de URLs en los clientes

### Error 404 en páginas JSP
- Verificar que el archivo WAR se haya desplegado
- Comprobar la estructura de directorios
- Revisar el archivo web.xml

### Errores de compilación
- Verificar que Java 17 esté instalado
- Ejecutar `mvn clean` antes de compilar
- Revisar las dependencias en pom.xml

## Resultados Esperados

Al completar todas las pruebas, deberías poder:
1. ✅ Navegar por todas las páginas sin errores
2. ✅ Ver datos del backend en las listas
3. ✅ Agregar nuevos materiales, usuarios y préstamos
4. ✅ Filtrar datos según diferentes criterios
5. ✅ Manejar errores de manera elegante
6. ✅ Tener una interfaz responsiva y moderna

## Notas Adicionales

- La aplicación está diseñada para funcionar con el backend de Tarea 1
- Todas las operaciones se realizan a través de servicios SOAP
- La interfaz es completamente responsiva
- Los errores se manejan de manera user-friendly

