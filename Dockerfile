FROM openjdk:17-jdk-slim

COPY build/libs/Bubble_Server_v3-0.0.1-SNAPSHOT.jar /app/bubble-server.jar
COPY ./env/email.env /app/email.env

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/bubble-server.jar"]