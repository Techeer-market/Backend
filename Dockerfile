#FROM adoptopenjdk/openjdk11
#
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#
#ENTRYPOINT ["java","-jar","/app.jar"]


FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} techeermarket-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/techeermarket-0.0.1-SNAPSHOT.jar"]
