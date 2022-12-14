FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs

ENV MS_NAME courses-ms
ENV MS_VERSION 0.0.1-SNAPSHOT

COPY ./build/libs/$MS_NAME-$MS_VERSION.jar .

ENV PORT 8002

EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "courses-ms-0.0.1-SNAPSHOT.jar"]