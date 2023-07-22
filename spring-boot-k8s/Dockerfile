FROM maven:3.9-eclipse-temurin-17-alpine AS builder
ADD . /app
WORKDIR /app
RUN mvn -s settings.xml -f pom.xml -Dmaven.test.skip=true package

FROM eclipse-temurin:17-jre-alpine
COPY --from=builder /app/target/*.jar /app/
WORKDIR /app
CMD java -jar app.jar --spring.config.location=./config/application.yml
