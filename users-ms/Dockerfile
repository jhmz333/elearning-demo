FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs

ENV MS_NAME users-ms
ENV MS_VERSION 0.0.1-SNAPSHOT

COPY ./build/libs/$MS_NAME-$MS_VERSION.jar .

ENV PORT 8001

EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "users-ms-0.0.1-SNAPSHOT.jar"]