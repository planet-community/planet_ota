FROM docker.io/maven:3.8-eclipse-temurin-17 AS build

WORKDIR /home/app
COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -DskipTests clean package

FROM docker.io/eclipse-temurin:17-jre AS app

ARG OTA_VERSION=0.1.0
WORKDIR /home/app
COPY --from=build /home/app/target/planet-ota-$OTA_VERSION.war /home/app/planet-ota.war

ENTRYPOINT ["java", "-jar", "/home/app/planet-ota.war"]
