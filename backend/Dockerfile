FROM openjdk:17
LABEL maintainer="Gabriel Ferreira <ferreiragabriel2612@gmail.com>"
WORKDIR /app
EXPOSE 8080
COPY target/eventos-0.0.1-SNAPSHOT.jar /app/eventos.jar
ENTRYPOINT ["java","-jar","eventos.jar"]