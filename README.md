# Proyecto Maven - Backend con Java 17 y Spring Boot 3.4.5

Este proyecto es una aplicación backend desarrollada con Java 17 y Spring Boot 3.4.5. Está preparada para ser desplegada usando Docker y Docker Compose. Incluye una base de datos PostgreSQL inicializada con un archivo SQL y cuenta con colecciones para pruebas en Postman.

---

## Tecnologías usadas

- Java 17  
- Spring Boot 3.4.5  
- Maven  
- PostgreSQL  
- Docker  
- Docker Compose  

---

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- [Docker](https://docs.docker.com/get-docker/) (versión recomendada: >= 20.x)  
- [Docker Compose](https://docs.docker.com/compose/install/) (versión recomendada: >= 1.29.x)  

---

## Arquitectura de servicios con Docker Compose

El archivo `docker-compose.yml` define los siguientes servicios:

- **postgres**: Contenedor con la base de datos PostgreSQL 14.  
- **client-person-service**: Servicio backend principal para la gestión de clientes, expuesto en el puerto `8080`.  
- **account-transaction-service**: Servicio backend encargado de las cuentas y transacciones, expuesto en el puerto `8081`.  
- **frontend**: Aplicación frontend Angular corriendo en el puerto `4200`. (solo se muestra los clientes)

Todos los servicios están conectados mediante la red Docker `backend` y los servicios backend dependen del contenedor `postgres` para la base de datos.

---

## Archivos importantes

- **base_de_datos.sql**: Script para crear y poblar la base de datos PostgreSQL usada por la aplicación.  
- **technical-challenge.postman_collection.json**: Archivo JSON con la colección de Postman para probar los endpoints del backend.  

---

## Cómo desplegar el proyecto

1. Clona este repositorio en tu máquina local.

2. Asegúrate que Docker y Docker Compose estén corriendo.

3. Ejecuta el siguiente comando para levantar todos los servicios (base de datos, backend y frontend):

   ```bash
   docker compose -f docker-compose.yml up  --build
