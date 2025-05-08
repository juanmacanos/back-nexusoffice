# 🏢 Nexus Office – Backend

Backend de Nexus Office, una solución para la gestión de puestos de trabajo en oficinas.
🔗 Repositorio del frontend: [front-nexusoffice](https://github.com/juanmacanos/front-nexusoffice)

---

## 🚀 Tecnologías usadas

- Java 21
- Spring Boot 3
- Spring Security (JWT)
- Spring Data JPA + Hibernate
- PostgreSQL / H2 (dev)
- Lombok
- MapStruct
- Swagger / OpenAPI

---

## ⚙️ Funcionalidades principales

### 🔐 Autenticación
- Login con JWT.
- Registro de nuevos usuarios.
- Roles: `USER` y `ADMIN`.

### 👤 Perfil
- Acceso al perfil del usuario autenticado.

### 🪑 Puestos
- Reserva automática de puestos.
- Cancelación de reservas.
- Reasignación inteligente si hay prioridad.
- Visualización tipo grid editable por admins.

### 📅 Historial
- Consultar historial propio.
- Vista por mes y semana.
- Estadísticas personales.

### 🛠️ Administración
- Crear o editar puestos en plano.
- Ver todos los usuarios y su historial.
- Asignar puestos preferentes.

---

## ▶️ Instrucciones de uso

bash
# Clona el repositorio
git clone https://github.com/juanmacanos/back-nexusoffice.git

# Accede al directorio
cd back-nexusoffice

# Ejecuta con Maven
./mvnw spring-boot:run

El servidor corre por defecto en localhost:8080
---

## 🔗 Endpoints clave

| Método | Endpoint                      | Descripción                       |
|--------|-------------------------------|-----------------------------------|
| POST   | `/api/v1/auth/login`         | Login de usuario                  |
| POST   | `/api/v1/auth/register`      | Registro                          |
| PUT    | `api/v1/auth/change-password`| Cambio de contraseña
| GET    | `/api/v1/profile`            | Perfil del usuario autenticado    |
| POST   | `/api/v1/bookings/assist`    | Reservar un puesto                |
| POST   | `/api/v1/bookings/cancel`    | Cancelar reserva                  |
| GET    | `/api/v1/bookings/history`   | Historial del usuario autenticado |
| GET    | `/api/v1/bookings/history/{userid}`| Historial del usuario especifico (admin) |
| GET    | `/api/v1/bookings/availability` | Lista de puestos con detalles de disponibilidad |
| GET    | `/api/v1/users`              | Lista de usuarios (admin)         |
| POST   | `/api/v1/places`             | Crear puesto (admin)       |
| GET    | `/api/v1/places`             | Obtener lista de puestos   |
| PATCH  | `/api/v1/places/{placeId}/preferreduser` | Cambiar usuario prioritario de puesto |



---

## 🛠️ Posibles mejoras técnicas

- Cookies seguras HttpOnly para guardar el JWT.
- Confirmación por correo electrónico al registrarse.
- Notificaciones por email para reservas y cancelaciones.
- Soporte para reservas múltiples (rango de fechas).
- Registro de actividad (auditoría de cambios).
- Roles más granularizados (responsables por equipo).
- Validación por departamento o turnos.
- Tests de integración automáticos con Postgres real.
---

## 📄 Licencia

MIT © 2025 – Juanma Canó
