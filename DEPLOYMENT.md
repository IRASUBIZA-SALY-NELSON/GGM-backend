# GGM Backend Deployment Guide

## Docker Deployment

### Prerequisites
- Docker and Docker Compose installed
- Git repository access

### Local Development with Docker

1. **Build and run with Docker Compose:**
   ```bash
   docker-compose up --build
   ```

2. **Run in detached mode:**
   ```bash
   docker-compose up -d --build
   ```

3. **Stop services:**
   ```bash
   docker-compose down
   ```

4. **View logs:**
   ```bash
   docker-compose logs -f app
   ```

### Render Deployment Configuration

#### For Render Web Service:

1. **Service Settings:**
   - **Name:** `ggm-backend`
   - **Language:** `Docker`
   - **Branch:** `main`
   - **Build Command:** `docker build -t ggm-backend .`
   - **Start Command:** `docker run -p 8081:8081 ggm-backend`

2. **Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=docker
   SPRING_DATASOURCE_URL=jdbc:postgresql://[YOUR_DB_HOST]:5432/stock
   SPRING_DATASOURCE_USERNAME=[YOUR_DB_USER]
   SPRING_DATASOURCE_PASSWORD=[YOUR_DB_PASSWORD]
   MAIL_HOST=[YOUR_MAIL_HOST]
   MAIL_PORT=587
   MAIL_USERNAME=[YOUR_MAIL_USERNAME]
   MAIL_PASSWORD=[YOUR_MAIL_PASSWORD]
   MAIL_FROM=[YOUR_EMAIL]
   ```

3. **Database Setup:**
   - Create a PostgreSQL database on Render
   - Update the `SPRING_DATASOURCE_URL` with your database connection string

#### Alternative: Using Render Blueprint

Create a `render.yaml` file in your repository root:

```yaml
services:
  - type: web
    name: ggm-backend
    env: docker
    dockerfilePath: ./backend/Dockerfile
    dockerContext: ./backend
    plan: starter
    buildCommand: docker build -t ggm-backend .
    startCommand: docker run -p 8081:8081 ggm-backend
    envVars:
      - key: SPRING_PROFILES_ACTIVE
        value: docker
      - key: SPRING_DATASOURCE_URL
        fromDatabase:
          name: ggm-postgres
          property: connectionString
      - key: SPRING_DATASOURCE_USERNAME
        fromDatabase:
          name: ggm-postgres
          property: user
      - key: SPRING_DATASOURCE_PASSWORD
        fromDatabase:
          name: ggm-postgres
          property: password

databases:
  - name: ggm-postgres
    databaseName: stock
    user: postgres
    plan: starter
```

### Health Check Endpoints

- **Health Check:** `http://localhost:8081/actuator/health`
- **API Documentation:** `http://localhost:8081/swagger-ui/index.html`
- **API Docs JSON:** `http://localhost:8081/v3/api-docs`

### Troubleshooting

1. **Port Issues:**
   - Ensure port 8081 is available
   - Check Docker port mapping

2. **Database Connection:**
   - Verify PostgreSQL is running
   - Check connection string format
   - Ensure database exists

3. **Build Issues:**
   - Clear Docker cache: `docker system prune -a`
   - Rebuild without cache: `docker-compose build --no-cache`

### Production Considerations

1. **Security:**
   - Use environment variables for sensitive data
   - Update JWT secret key
   - Configure proper CORS origins

2. **Performance:**
   - Use production database
   - Configure connection pooling
   - Set appropriate JVM memory settings

3. **Monitoring:**
   - Enable application metrics
   - Set up logging aggregation
   - Configure health checks
