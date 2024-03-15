FROM openjdk:17

COPY build/libs/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "/app.jar"]