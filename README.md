# Spring Boot: análisis y demostración

Taller de la Unidad D de **Arquitectura y Servicios de Internet** (Máster, Universidad de Burgos, 2026).

Autor: Noel Del Río González

## Qué es

Análisis de Spring Boot como framework para aplicaciones web modernas y distribuidas. La demostración usa la API REST
del Smart Campus de la práctica 1: una petición seguida con el depurador, la validación con un 400, Swagger y dos
réplicas en Docker detrás de nginx.

## Contenido

| Carpeta | Descripción |
| --- | --- |
| `smart-campus-api/` | API REST de la práctica 1 (Java 21 y Spring Boot 3.5) |
| `sql/` | Tablas y datos de ejemplo de MySQL |
| `demo/` | MySQL, dos réplicas de la API y nginx con Docker Compose, y la colección de Postman |

## Cómo arrancar la demo

Hace falta Java 21 y Docker Desktop.

```bash
cd demo
docker compose up -d --build --wait
```

La API queda en http://localhost:8090/api/sensors y Swagger en http://localhost:8090/swagger-ui.html. Para pararla:
`docker compose down -v`.

## Licencia

MIT
