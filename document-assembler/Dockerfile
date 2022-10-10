FROM gradle:7-jdk17-alpine as build
WORKDIR /app
COPY / /app

RUN chown -R gradle /app

RUN ./gradlew bootJar --stacktrace --no-daemon

FROM amazoncorretto:17 as app

EXPOSE 8080

RUN mkdir /app

COPY --from=build /app/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]