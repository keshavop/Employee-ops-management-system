#
# Build stage
#
FROM maven:3.8.2-openjdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/auth-backend-0.0.1-SNAPSHOT.jar auth-backend.jar
# ENV PORT=8080
EXPOSE process.env.PORT
ENTRYPOINT ["java","-jar","auth-backend.jar"]


