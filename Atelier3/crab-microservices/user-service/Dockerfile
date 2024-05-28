# use one image to build the app
FROM maven:3.8.6-amazoncorretto-17 AS user-build
ENV USERAPP /opt/userapp
WORKDIR $USERAPP
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

# Run
FROM amazoncorretto:17

ENV USERAPP=/opt/userapp
ENV POSTGRES_PASSWORD=${POSTGRES_PASSWORD}
ENV POSTGRES_USER=${POSTGRES_USER}
ENV POSTGRES_DB=${POSTGRES_DB}

WORKDIR $USERAPP
COPY --from=user-build $USERAPP/target/*.jar $USERAPP/user-microservice.jar

ENTRYPOINT java -jar user-microservice.jar -Dconf.database.username="sa" -Dconf.database.password="sa" -Dconf.database.url="jdbc:postgresql://card-db:5432/" -Dconf.database.dbName="card_game"