# RedNorte – Gestión Hospitalaria Inteligente

RedNorte es una plataforma desarrollada para optimizar la gestión de listas de espera hospitalarias en el sistema público de salud.

El proyecto fue desarrollado bajo arquitectura de microservicios utilizando Spring Boot, React, Docker y AWS.

---

# Arquitectura del Proyecto

El sistema está compuesto por:

- Frontend React + Vite
- Microservicio Gestión Clínica
- Microservicio Gestión Soporte
- Backend For Frontend (BFF)
- Base de datos MySQL en AWS RDS
- Contenedores Docker
- Despliegue en AWS EC2

---

# Tecnologías Utilizadas

## Frontend
- React
- Vite
- JavaScript
- CSS

## Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Maven

## Infraestructura
- Docker
- Docker Compose
- AWS EC2
- AWS RDS MySQL

---

# Funcionalidades Principales

- Registro e inicio de sesión
- Gestión de pacientes
- Gestión de doctores
- Registro de horas médicas
- Reserva de citas
- Lista de espera
- Gestión de reportes
- Eliminación de citas
- Roles de usuario:
  - ADMIN_CLINICA
  - DOCTOR
  - PACIENTE

---

# Estructura del Proyecto

```txt
proyecto-rednorte/
│
├── rednorte-frontend/
├── gestionclinica/
├── gestionsoporte/
├── bff-rednote/
└── docker-compose.yml
Requisitos
Java 17
Maven
Node.js
Docker
Docker Compose
Variables de Entorno Frontend

Crear archivo .env dentro de:

rednorte-frontend/

Contenido:

VITE_API_BFF_URL=http://IP_PUBLICA:8090/api/bff
Ejecución Local
1. Clonar repositorio
git clone URL_DEL_REPOSITORIO
2. Compilar microservicios
Gestión Clínica
cd gestionclinica
mvn clean package -DskipTests
Gestión Soporte
cd gestionsoporte
mvn clean package -DskipTests
BFF
cd bff-rednote
mvn clean package -DskipTests
3. Levantar Docker

Desde la raíz:

docker-compose up --build -d
Puertos Utilizados
Servicio	Puerto
Frontend	5173
BFF	8090
Gestión Clínica	8081
Gestión Soporte	8082
Despliegue AWS

Infraestructura desplegada utilizando:

AWS EC2
AWS RDS
Docker Compose
Elastic IP
Inicio en Producción

Conectarse vía SSH:

ssh -i "rednorte-key.pem" ec2-user@IP_ELASTICA

Levantar servicios:

cd ~/proyecto-rednorte
docker-compose up -d
Equipo de Desarrollo

Proyecto académico desarrollado para la asignatura:

Desarrollo Fullstack III

Duoc UC
