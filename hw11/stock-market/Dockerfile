FROM openjdk:8-jre-slim

EXPOSE 8080

RUN mkdir /app

COPY stock-market-1.0-SNAPSHOT-all.jar /app/stock-market.jar

ENTRYPOINT ["java", "-jar", "/app/stock-market.jar"]