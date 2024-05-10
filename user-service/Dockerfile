FROM openjdk:17-ea-slim
VOLUME /tmp
COPY target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java", "-jar", "user-service.jar"]
