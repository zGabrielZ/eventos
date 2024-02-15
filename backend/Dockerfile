FROM openjdk:17
LABEL maintainer="Gabriel Ferreira <ferreiragabriel2612@gmail.com>"
WORKDIR /backend
EXPOSE 8080
COPY backend/target/eventos-0.0.1-SNAPSHOT.jar /backend/eventos.jar
ENTRYPOINT ["java","-jar","eventos.jar"]