FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} gesp.jar
ENTRYPOINT ["java","-jar","/gesp.jar"]