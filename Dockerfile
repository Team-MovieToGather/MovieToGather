FROM openjdk:17

COPY build/libs/MovieToGather-0.0.1-SNAPSHOT.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]