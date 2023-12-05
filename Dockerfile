FROM openjdk:17-jdk

COPY target/pet-project-0.0.1-SNAPSHOT.jar /pet-project.jar

CMD ["java" , "-jar", "/pet-project.jar"]
