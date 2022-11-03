FROM ghcr.io/graalvm/native-image:ol7-java17 AS builder

COPY src /home/app/src
COPY pom.xml /home/app
COPY mvnw /home/app
COPY .mvn /home/app/.mvn/
WORKDIR /home/app

RUN ./mvnw -f /home/app/pom.xml -DskipTests=true -Pnative clean package

FROM docker.io/amazoncorretto:11-alpine3.14 AS app

WORKDIR /app
COPY --from=builder /home/app/target/planet-ota /app/planet-ota

ENTRYPOINT ["/app/planet-ota"]
