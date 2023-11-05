FROM openjdk:17-jdk-slim

COPY target/PetProject-0.0.1-SNAPSHOT.jar /PetProject.jar

CMD ["java" , "-jar", "/PetProject.jar"]

#FROM alpine:3.13
#RUN apk add openjdk11
#COPY build/libs/PetProject.jar /PetProject.jar
#CMD ["java" , "-jar", "/PetProject.jar"]
