# use one image to build the app
FROM maven:3.8.6-amazoncorretto-17 AS card-build
ENV CARD_APP /opt/cardapp
WORKDIR $CARD_APP
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

# Run
FROM amazoncorretto:17

ENV USERAPP=/opt/cardapp
ENV POSTGRES_PASSWORD=${POSTGRES_PASSWORD}
ENV POSTGRES_USER=${POSTGRES_USER}
ENV POSTGRES_DB=${POSTGRES_DB}

WORKDIR $CARD_APP
COPY --from=card-build $CARD_APP/target/*.jar $CARD_APP/card-microservice.jar

ENTRYPOINT java -jar card-microservice.jar