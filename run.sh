#!/bin/bash

# Mensaje de bienvenida
echo "========================================="
echo "   Iniciando ShopGTA - Backend "
echo "========================================="

# Verificar si existe Maven Wrapper (recomendado)
if [ -f "./mvnw" ]; then
    echo "Usando Maven Wrapper..."
    ./mvnw spring-boot:run

# Verificar si existe Gradle Wrapper
elif [ -f "./gradlew" ]; then
    echo "Usando Gradle Wrapper..."
    ./gradlew bootRun

# Si no hay wrappers, intentar con Maven global si está instalado
elif command -v mvn &> /dev/null; then
    echo "Usando Maven instalado en el sistema..."
    mvn spring-boot:run

else
    echo "Error: No se encontró Maven ni Gradle en el proyecto."
    echo "Por favor, asegúrate de tener Java y Maven/Gradle instalados."
    exit 1
fi