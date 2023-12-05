FROM openjdk:17-jdk

ADD  target/PetProject-0.0.1-SNAPSHOT.jar /PetProject.jar

ENTRYPOINT  ["java" , "-jar", "/PetProject.jar"]
