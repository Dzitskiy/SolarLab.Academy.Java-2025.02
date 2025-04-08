# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы Gradle Wrapper и конфигурации
COPY gradlew gradlew.bat gradle/ ./gradle/
COPY build.gradle settings.gradle ./

# Копируем исходный код проекта
COPY src ./src

# Сборка проекта
RUN ./gradlew build --no-daemon

# Создаем финальный образ
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл из предыдущего этапа
COPY --from=build /app/build/libs/*.jar app.jar

# Указываем порт, который будет использовать приложение
EXPOSE 9080

# Указываем команду для запуска приложения
CMD ["java", "-jar", "app.jar"]