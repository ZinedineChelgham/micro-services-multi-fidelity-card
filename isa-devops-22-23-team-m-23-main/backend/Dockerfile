FROM openjdk:19 as builder
WORKDIR /app/backend
ARG POSTGRES_HOST
ADD ./backend.jar /app/backend/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar", "--postgres.host=${POSTGRES_HOST}"]