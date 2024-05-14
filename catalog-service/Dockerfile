FROM openjdk:17-ea-slim
VOLUME /tmp
COPY target/catalog-service-0.0.1-SNAPSHOT.jar catalog-service.jar
ENTRYPOINT ["java", "-jar", "catalog-service.jar"]
