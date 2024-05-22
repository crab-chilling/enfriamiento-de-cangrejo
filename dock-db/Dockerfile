FROM postgres:14.1-alpine

ENV POSTGRES_DB=card_game
ENV POSTGRES_USER=sa
ENV POSTGRES_PASSWORD=sa

# copy script to create db
COPY ./scripts /docker-entrypoint-initdb.d
