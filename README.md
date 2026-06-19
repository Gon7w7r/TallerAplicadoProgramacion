# Taller Aplicado de Programacion
Repositorio general contiene frontend backend y api en desarrollo
## Requisitos

- Java 17+
- Maven (o usar el `mvnw` incluido)
- MySQL (XAMPP recomendado)
- Node.js 18+ y pnpm
- Python 3.10+

---

## Pasos para ejecutar localmente

### 1. Base de datos

Inicia XAMPP y activa el módulo MySQL. Luego crea la base de datos:

```sql
CREATE DATABASE db_tomadehorarios;
```

### 2. Backend

Edita `BACKEND/src/main/resources/application.properties` y ajusta si es necesario (por defecto ya apunta a XAMPP):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_tomadehorarios
spring.datasource.username=root
spring.datasource.password=
```

Luego ejecuta:

```bash
cd BACKEND
./mvnw spring-boot:run
```

Disponible en `http://localhost:8080`

### 3. Frontend

El archivo `FRONTEND/.env` ya está configurado para local:

```env
VITE_API_URL=http://localhost:8080
```

Ejecuta:

```bash
cd FRONTEND
npm install
npm run dev
```

Disponible en `http://localhost:5173`

### 4. API
###En proceso
