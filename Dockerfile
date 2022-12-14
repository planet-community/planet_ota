FROM docker.io/maven:3.8-eclipse-temurin-17 AS build

WORKDIR /home/app
COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -DskipTests=true clean package

FROM docker.io/eclipse-temurin:17-jre AS app

WORKDIR /home/app
COPY --from=build /home/app/target/planet-ota-0.1.0.war /home/app/planet-ota.war

ENTRYPOINT ["java", "-jar", "/home/app/planet-ota.war"]
