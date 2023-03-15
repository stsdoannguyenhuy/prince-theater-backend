FROM gradle:6.9.4-jdk17-focal AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . /home/gradle/src
RUN ./gradlew build -x test

FROM openjdk:17.0.2-oraclelinux8
EXPOSE 8080
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/Prince-Theater-Backend-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
