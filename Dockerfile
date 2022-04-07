FROM openjdk:11.0.7-jdk-slim

COPY target/PetProject-0.0.1-SNAPSHOT.jar /PetProject.jar

CMD ["java" , "-jar", "/PetProject.jar"]