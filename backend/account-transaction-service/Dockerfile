# Usa imagen oficial de Java
FROM openjdk:17-jdk-slim

# Crea un directorio de trabajo
WORKDIR /app

# Copia el jar compilado (ajusta nombre si es diferente)
COPY target/account-transaction-service-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en que correrá la app
EXPOSE 8080

# Comando para correr la app
ENTRYPOINT ["java", "-jar", "app.jar"]
