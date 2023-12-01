FROM openjdk:11
COPY target/*.jar reportverse.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "reportverse.jar"]