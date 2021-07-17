FROM maven:3.8-jdk-8 as maven
WORKDIR /app
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src

RUN mvn package && cp target/phoneNumberValidator-0.0.1.jar app.jar

# Rely on Docker's multi-stage build to get a smaller image based on JRE
FROM openjdk:8-jre-alpine
LABEL maintainer="xxxxx@xxx.com"
WORKDIR /app
COPY --from=maven /app/app.jar ./app.jar

# VOLUME /tmp  # optional
EXPOSE 3333

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]