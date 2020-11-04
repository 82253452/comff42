FROM openjdk:8u181-jdk-alpine

ARG workdir=/app
ARG APPDIR
ARG PORT

VOLUME ${workdir}

WORKDIR ${workdir}

ADD ./target/app.jar app.jar
EXPOSE 8080


ENTRYPOINT ["java","-jar","-Xms512M","-Xmx1024M","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","app.jar"]
