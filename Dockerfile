FROM openjdk:17

WORKDIR /app

COPY src ./src
COPY target//emplois-0.0.1-SNAPSHOT.jar /app/emplois-0.0.1-SNAPSHOT.jar
COPY src/main/resources/application.properties /app/src/main/resources/application.properties


EXPOSE 8986


CMD ["java", "-jar", "emplois-0.0.1-SNAPSHOT.jar"]
