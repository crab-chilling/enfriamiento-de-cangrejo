# use one image to build the app
FROM maven:3.8.6-amazoncorretto-17 AS store-build
ENV STORE_APP /opt/storeapp
WORKDIR $STORE_APP
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

# Run
FROM amazoncorretto:17

ENV USERAPP=/opt/storeapp
ENV POSTGRES_PASSWORD=${POSTGRES_PASSWORD}
ENV POSTGRES_USER=${POSTGRES_USER}
ENV POSTGRES_DB=${POSTGRES_DB}

WORKDIR $STORE_APP
COPY --from=store-build $STORE_APP/target/*.jar $STORE_APP/store-microservice.jar

ENTRYPOINT java -jar store-microservice.jar -Dconf.database.username="sa" -Dconf.database.password="sa" -Dconf.database.url="jdbc:postgresql://card-db:5432/" -Dconf.database.dbName="card_game"