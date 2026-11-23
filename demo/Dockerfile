# Imagen de la API de la PR1 para la demo del Taller D. Dos fases: en la primera se compila con Maven y en la
# segunda solo queda Java y el .jar, que es lo único que hace falta para ejecutarla (el servidor va dentro).
FROM maven:3.9-eclipse-temurin-21 AS compilar
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src src
RUN mvn -q -B package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=compilar /app/target/smart-campus-api-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
