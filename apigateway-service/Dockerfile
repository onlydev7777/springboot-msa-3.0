FROM openjdk:17-ea-slim
VOLUME /tmp
COPY target/apigateway-service-0.0.1-SNAPSHOT.jar apigateway-service.jar
ENTRYPOINT ["java", "-jar", "apigateway-service.jar"]
