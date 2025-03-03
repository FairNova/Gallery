# Используем актуальный образ Maven с OpenJDK 17 для сборки
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы проекта в контейнер
COPY . .

# Собираем приложение, пропуская тесты
RUN mvn clean package -DskipTests

# Используем официальный образ OpenJDK 17 на базе Alpine Linux для выполнения
FROM openjdk:17-jdk-slim

# Копируем собранный jar-файл из предыдущего образа
COPY --from=build /app/target/*.jar app.jar

# Указываем точку входа для запуска приложения
ENTRYPOINT ["java", "-jar", "/app.jar"]
