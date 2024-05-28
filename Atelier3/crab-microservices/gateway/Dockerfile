# use one image to build the app
FROM maven:3.8.6-amazoncorretto-17 AS gateway-build
ENV GATEWAY_APP /opt/gatewayeapp
WORKDIR $GATEWAY_APP
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

# Run
FROM amazoncorretto:17

ENV USERAPP=/opt/gatewayeapp
ENV POSTGRES_PASSWORD=${POSTGRES_PASSWORD}
ENV POSTGRES_USER=${POSTGRES_USER}
ENV POSTGRES_DB=${POSTGRES_DB}

WORKDIR $GATEWAY_APP
COPY --from=gateway-build $GATEWAY_APP/target/*.jar $GATEWAY_APP/gateway-microservice.jar

ENTRYPOINT java -jar gateway-microservice.jar