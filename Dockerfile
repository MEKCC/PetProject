FROM openjdk:17-jdk

ADD  target/PetProject.jar /PetProject.jar

ENTRYPOINT  ["java" , "-jar", "/PetProject.jar"]
