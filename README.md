# 🚀 DevHire API

**DevHire** es una API REST desarrollada en Java con Spring Boot que conecta empresas tecnológicas con desarrolladores. Permite la creación de ofertas laborales, postulación a empleos, gestión de usuarios, comentarios, habilidades, y más.  
Este proyecto fue realizado por **tres estudiantes universitarios** como trabajo integrador para la facultad, aplicando conocimientos de programación backend, diseño de APIs, seguridad y persistencia de datos.

---

## 🎓 Proyecto académico

**Materia:** Programacion Ⅲ  
**Universidad:** Universidad Tecnologica Nacional 
**Integrantes del equipo:**
- Ramos Lautaro  
- Odera Maica  
- Castro Lautaro

---

## 🧰 Tecnologías utilizadas

- Java 21
- Spring Boot 3  
- Spring Security  
- Spring Data JPA  
- Hibernate  
- MySQL  
- Swagger / OpenAPI  
- HATEOAS  
- Lombok  
- Maven

---

## ⚙️ Funcionalidades

### 🔐 Seguridad y autenticación
- Registro y login con validación.
- Autenticación mediante JWT.
- Roles y permisos: `ADMIN`, `COMPANY`, `DEVELOPER`.

### 🧑‍💼 Empresas
- Realizar, comentar y reaccionar(like) publicaciones.
- Publicar, modificar y eliminar ofertas de empleo.
- Visualizar y gestionar postulaciones recibidas.
- Añadir habilidades requeridas (hard y soft skills).
- Responder a comentarios de desarrolladores.

### 👨‍💻 Desarrolladores
- Ver ofertas laborales activas.
- Postularse a empleos.
- Realizar, comentar y reaccionar(like) publicaciones.
- Añadir habilidades personales.

### 🛡️ Administradores
- Acceso a estadísticas del sistema.
- Gestión global de entidades.

---
## 📁 Estructura del proyecto

```text
DevHire/
├── .env
├── .gitignore
├── mvnw
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── TP_Final/
│   │   │       └── devhire/
│   │   │           ├── Config/
│   │   │           ├── Controllers/
│   │   │           ├── DTO/
│   │   │           ├── Exceptions/
│   │   │           ├── Model/
│   │   │           ├── Repository/
│   │   │           ├── Security/
│   │   │           ├── Service/
│   │   │           └── DevHireApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
│       └── java/
│           └── TP_Final/
│               └── devhire/
│                   └── DevHireApplicationTests.java
```
## 🛠️ Configuración del entorno
### 🔧 Requisitos
- Java 21
- Maven
- MySQL
###  Instalación y ejecución

1. 📦 Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/devhire.git
cd devhire

2. ⚙️ Configurar las variables en el archivo `.env` o `application.properties`:
    ```properties

spring.datasource.url=jdbc:mysql://localhost:3306/devhire
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

3. 🗄️ Crear una base de datos MySQL llamada devhire_db.

4. 🚀 Ejecutar la aplicación:
    ```bash
    ./mvnw spring-boot:run
    ```
5. 📑 Acceder a Swagger:
    ```
    http://localhost:8080/swagger-ui/index.html
    ```

##🔑 Endpoints destacados

| Método | Endpoint         | Rol requerido | Descripción                 |
| ------ | ---------------- | ------------- | --------------------------- |
| POST   | `/register`      | Público       | Registro de nuevos usuarios |
| POST   | `/auth/login`    | Público       | Autenticación de usuario    |
| GET    | `/job`           | COMPANY/DEV/ADMIN| Ver empleos disponibles  |
| POST   | `/job/post`      | COMPANY       | Crear una nueva oferta      |
| POST   | `/job/apply`     | DEV           | Aplicar a un empleo         |
| GET    | `/stats`         | ADMIN         | Ver estadísticas generales  |

##💡 Ideas futuras
- Sistema de notificaciones por correo.
- Integracion API GitHub.

##📌 Diseño y arquitectura
- Se utilizan DTOs para entrada/salida de datos.
- Las respuestas utilizan HATEOAS (EntityModel, CollectionModel) para navegación semántica RESTful.
- Las habilidades se definen como enums: HardSkill, SoftSkill.
- Manejo centralizado de errores mediante @ControllerAdvice.

##👥 Autores
- Lautaro Ramos @RamosLaut
- Maica Odera @Katzehell
- Lautaro Castro @sklaucha

