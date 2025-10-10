# Biblioteca Comunitaria - Sistema Web (Tarea 2)

Este proyecto es una aplicación web que consume los servicios SOAP del backend de la Tarea 1 para gestionar una biblioteca comunitaria.

## Características

- **Gestión de Materiales**: Administración de libros y artículos especiales
- **Gestión de Usuarios**: Manejo de lectores y bibliotecarios
- **Gestión de Préstamos**: Control de préstamos y devoluciones
- **Interfaz Web Moderna**: Diseño responsivo con Bootstrap 5
- **Consumo de Servicios SOAP**: Integración con el backend de Tarea 1

## Tecnologías Utilizadas

- **Java 17**
- **Jakarta Servlets**
- **JSP (JavaServer Pages)**
- **JAX-WS** (para consumo de servicios SOAP)
- **Bootstrap 5** (para la interfaz de usuario)
- **Maven** (para gestión de dependencias)
- **Tomcat** (servidor de aplicaciones)

## Estructura del Proyecto

```
Tarea2/
├── src/main/java/
│   └── com/biblioteca/
│       ├── client/              # Clientes SOAP
│       │   ├── MaterialServiceClient.java
│       │   ├── UsuarioServiceClient.java
│       │   └── PrestamoServiceClient.java
│       ├── datatypes/           # Clases de datos
│       │   ├── DtMaterial.java
│       │   ├── DtUsuario.java
│       │   ├── DtPrestamo.java
│       │   └── ...
│       └── servlets/            # Servlets
│           ├── MaterialServlet.java
│           ├── UsuarioServlet.java
│           └── PrestamoServlet.java
├── src/main/webapp/
│   ├── WEB-INF/
│   │   └── web.xml
│   ├── index.jsp               # Página principal
│   ├── materiales.jsp          # Lista de materiales
│   ├── agregar-material.jsp    # Formulario para agregar material
│   ├── usuarios.jsp            # Lista de usuarios
│   ├── agregar-usuario.jsp     # Formulario para agregar usuario
│   ├── prestamos.jsp           # Lista de préstamos
│   ├── agregar-prestamo.jsp    # Formulario para agregar préstamo
│   └── error.jsp               # Página de error
├── pom.xml                     # Configuración de Maven
└── README.md
```

## Requisitos Previos

1. **Java 17** o superior
2. **Maven 3.6** o superior
3. **Tomcat 9** o superior
4. **Backend de Tarea 1** ejecutándose en `http://localhost:8080`

## Instalación y Configuración

### 1. Clonar o descargar el proyecto

```bash
cd Tarea2
```

### 2. Compilar el proyecto

```bash
mvn clean compile
```

### 3. Generar el archivo WAR

```bash
mvn clean package
```

### 4. Desplegar en Tomcat

Copia el archivo `target/biblioteca-web.war` al directorio `webapps` de Tomcat.

### 5. Ejecutar con Maven (desarrollo)

```bash
mvn tomcat7:run
```

La aplicación estará disponible en: `http://localhost:8081/biblioteca-web`

## Configuración del Backend

Asegúrate de que el backend de la Tarea 1 esté ejecutándose en `http://localhost:8080` con los siguientes servicios:

- **Materiales**: `http://localhost:8080/materiales`
- **Usuarios**: `http://localhost:8080/usuarios`
- **Préstamos**: `http://localhost:8080/prestamos`

## Uso de la Aplicación

### Página Principal
- Acceso a todas las funcionalidades
- Navegación intuitiva entre secciones

### Gestión de Materiales
- **Ver materiales**: Lista todos los materiales disponibles
- **Filtrar por rango**: Filtra materiales por fecha de registro
- **Agregar material**: Formulario para registrar nuevos libros o artículos especiales

### Gestión de Usuarios
- **Ver usuarios**: Lista todos los usuarios (lectores y bibliotecarios)
- **Agregar usuario**: Formulario para registrar nuevos usuarios

### Gestión de Préstamos
- **Ver préstamos**: Lista todos los préstamos
- **Filtrar pendientes**: Muestra solo préstamos pendientes
- **Filtrar por zona**: Filtra préstamos por zona de la biblioteca
- **Filtrar por lector**: Muestra préstamos activos de un lector específico
- **Nuevo préstamo**: Formulario para registrar nuevos préstamos

## API Endpoints

### Materiales
- `GET /materiales?action=list` - Listar todos los materiales
- `GET /materiales?action=listByRange&fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD` - Filtrar por rango
- `POST /materiales?action=add` - Agregar nuevo material

### Usuarios
- `GET /usuarios?action=list` - Listar todos los usuarios
- `POST /usuarios?action=add` - Agregar nuevo usuario

### Préstamos
- `GET /prestamos?action=list` - Listar todos los préstamos
- `GET /prestamos?action=pendientes` - Listar préstamos pendientes
- `GET /prestamos?action=porZona&zona=ZONA` - Filtrar por zona
- `GET /prestamos?action=activosLector&correoLector=email` - Filtrar por lector
- `POST /prestamos?action=add` - Agregar nuevo préstamo

## Solución de Problemas

### Error de Conexión con el Backend
- Verifica que el backend de Tarea 1 esté ejecutándose
- Confirma que los servicios estén disponibles en `http://localhost:8080`
- Revisa los logs del servidor para más detalles

### Error 404 en Páginas
- Asegúrate de que el archivo WAR se haya desplegado correctamente
- Verifica que estés accediendo a la URL correcta

### Errores de Compilación
- Verifica que tengas Java 17 instalado
- Ejecuta `mvn clean` antes de compilar
- Revisa que todas las dependencias estén disponibles

## Desarrollo

### Agregar Nuevas Funcionalidades
1. Crea el cliente SOAP en el paquete `client`
2. Implementa el servlet correspondiente
3. Crea las páginas JSP necesarias
4. Actualiza la navegación en `index.jsp`

### Modificar Estilos
- Los estilos están definidos en cada página JSP
- Utiliza Bootstrap 5 para mantener consistencia
- Las clases CSS personalizadas están en las etiquetas `<style>`

## Licencia

Este proyecto es parte de un ejercicio académico para el curso de Programación Web.

## Contacto

Para consultas o problemas, contacta al desarrollador del proyecto.

