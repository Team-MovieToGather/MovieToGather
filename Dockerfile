FROM openjdk:17

COPY build/libs/*.jar app.jar
COPY ./src/main/resources/.env .env
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "/app.jar"]