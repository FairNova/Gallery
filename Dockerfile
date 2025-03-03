# Используем официальный образ OpenJDK 17 на базе Alpine Linux
FROM openjdk:17-jdk-slim

# Аргумент для передачи пути к собранному jar-файлу
ARG JAR_FILE=target/*.jar

# Копируем jar-файл в контейнер
COPY ${JAR_FILE} app.jar

# Указываем точку входа для запуска приложения
ENTRYPOINT ["java", "-jar", "/app.jar"]
