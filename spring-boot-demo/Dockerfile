FROM maven:3.9-eclipse-temurin-17 as builder
ADD . /app
WORKDIR /app
RUN mvn -B -f /app/pom.xml -s /app/settings.xml clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre-alpine
COPY --from=builder /app/target/demo.jar /app/
WORKDIR /app
CMD java -jar demo.jar --spring.config.location=/app/config/application.properties
