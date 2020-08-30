FROM openjdk:8-jdk-alpine
COPY /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -jar app.jar -debug