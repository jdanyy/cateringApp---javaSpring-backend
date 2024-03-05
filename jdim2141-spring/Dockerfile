FROM gradle:8.3.0-jdk17-alpine AS base

ENV APP_HOME=/usr/app
WORKDIR $APP_HOME

COPY ./build.gradle ./build.gradle
COPY ./settings.gradle ./settings.gradle
COPY ./lint.gradle ./lint.gradle

WORKDIR $APP_HOME/jdim2141-spring

ARG profile
ENV SPRING_PROFILES_ACTIVE $profile

COPY ./jdim2141-spring/build.gradle ./build.gradle
COPY ./jdim2141-spring/build-$profile.gradle ./build-$profile.gradle
COPY ./jdim2141-spring/src ./src
COPY ./jdim2141-spring/.env.docker ./src/main/resources/.env

RUN gradle --no-daemon bootJar

FROM eclipse-temurin:17-jre-alpine

ENV APP_HOME=/usr/app
WORKDIR $APP_HOME

ENV ARTIFACT_NAME=jdim2141-spring.jar

COPY --from=base $APP_HOME/jdim2141-spring/build/libs/$ARTIFACT_NAME ./

EXPOSE 8080

CMD java -jar $ARTIFACT_NAME
