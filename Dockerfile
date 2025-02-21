FROM openjdk:17-jdk
LABEL author=marcelobatista.dev
COPY .env /.env
COPY target/helpingPets-0.0.1-SNAPSHOT.jar helping_pets_app.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","helping_pets_app.jar" ]