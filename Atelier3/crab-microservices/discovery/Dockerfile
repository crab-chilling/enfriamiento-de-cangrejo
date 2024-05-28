# use one image to build the app
FROM maven:3.8.6-amazoncorretto-17 AS discovery-build
ENV DISCOVERY_APP /opt/discoveryapp
WORKDIR $DISCOVERY_APP
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

# Run
FROM amazoncorretto:17

ENV USERAPP=/opt/discoveryapp
ENV POSTGRES_PASSWORD=${POSTGRES_PASSWORD}
ENV POSTGRES_USER=${POSTGRES_USER}
ENV POSTGRES_DB=${POSTGRES_DB}

WORKDIR $DISCOVERY_APP
COPY --from=discovery-build $DISCOVERY_APP/target/*.jar $DISCOVERY_APP/discovery-microservice.jar

ENTRYPOINT java -jar discovery-microservice.jar