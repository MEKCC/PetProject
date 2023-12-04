FROM openjdk:17-jdk

COPY target/PetProject-0.0.1-SNAPSHOT.jar /PetProject.jar

CMD ["java" , "-jar", "/PetProject.jar"]
