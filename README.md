
Visualizar desde : https://supermercado-frontend-dmyv.onrender.com
Se debe esperar sobre 1 minuto para que Render despierte la base de datos:
# Supermarket App 🛒

Aplicación de supermercado desarrollada con **Spring Boot** como proyecto de práctica y aprendizaje.

El objetivo del proyecto es aplicar conceptos de **API REST**, **JPA**, **DTOs** y **arquitectura por capas**.

---

## ¿Qué hace la aplicación?

La aplicación permite gestionar:

- Categorías
- Productos
- Sucursales
- Ventas
- Detalle de ventas

Cada módulo cuenta con sus endpoints para crear, consultar, actualizar y eliminar datos.

---

## ¿Cómo está organizada?

El proyecto está dividido en varias capas:

- **Controller**: define los endpoints REST
- **Service**: contiene la lógica del negocio
- **Repository**: acceso a base de datos con JPA
- **DTO**: objetos para enviar y recibir datos
- **Model**: entidades de la base de datos
- **Mapper**: convierte entidades a DTO y viceversa
- **Exception**: manejo de errores básicos

---

## Tecnologías usadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Lombok
- Maven
- Base de datos relacional

---

## Ejemplo de endpoints

La aplicación expone una API REST.  
Por ejemplo, para la gestión de productos se utiliza la siguiente ruta base:


Desde esta ruta se pueden realizar las operaciones básicas:

- `GET /api/productos` → listar todos los productos
- `GET /api/productos/{id}` → obtener un producto por id
- `POST /api/productos` → crear un nuevo producto
- `PUT /api/productos/{id}` → actualizar un producto
- `DELETE /api/productos/{id}` → eliminar un producto

## Frontend

Este backend está pensado para ser consumido por una aplicación frontend desarrollada en Angular.



