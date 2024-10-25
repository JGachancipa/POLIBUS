FROM openjdk:21-jdk-slim
LABEL mantainers="polibus.com"
ADD target/backend-0.0.1-SNAPSHOT.jar polibus-backend.jar
ENTRYPOINT [ "java", "-jar", "polibus-backend.jar" ]
