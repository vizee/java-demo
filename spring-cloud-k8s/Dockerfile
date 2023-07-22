FROM eclipse-temurin:17-jre-alpine
ADD ./app.jar /app/
WORKDIR /app
CMD java -jar app.jar --spring.config.location=./config/application.yml
