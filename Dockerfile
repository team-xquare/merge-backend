FROM openjdk:17
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} application.jar

RUN apt-get update && \
    apt-get install -y awscli

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]