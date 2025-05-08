# Etapa 1: Compilar usando Java 21
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY . .

RUN ./mvnw clean package -pl gestorpuestos-service -am -DskipTests

# Etapa 2: Imagen final con solo el .jar ejecutable
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/gestorpuestos-service/target/gestorpuestos-service-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
