FROM maven:3.9.4-eclipse-temurin-17 as build

WORKDIR /build

COPY src src
COPY pom.xml pom.xml

RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM bellsoft/liberica-openjdk-debian:17

RUN addgroup spring-boot-group && adduser --ingroup spring-boot-group spring-boot
USER spring-boot:spring-boot-group

VOLUME /tmp

ARG JAR_FILE=webcurrency-0.0.1-SNAPSHOT.jar

WORKDIR /application

COPY --from=build /build/target/${JAR_FILE} application.jar

ENTRYPOINT exec java ${JAVA_OPTS} -jar application.jar