FROM docker.io/maven:3.8.6-amazoncorretto-11 AS builder

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml -DskipTests=true clean package

FROM docker.io/amazoncorretto:11-alpine3.14 AS app

COPY --from=builder /home/app/target/planet-ota-0.1.0-SNAPSHOT.war /usr/local/lib/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
