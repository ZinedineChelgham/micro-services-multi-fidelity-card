FROM openjdk:19 as builder
WORKDIR /app/cli
ADD ./cli.jar /app/cli/app.jar
ARG SERVER_HOST
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]