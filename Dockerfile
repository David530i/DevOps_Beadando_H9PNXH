# 1. lepes: build - Maven + JDK
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# 2. lepes: futtatás - csak a jar egy runtime image-ben
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/hello-devops-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
