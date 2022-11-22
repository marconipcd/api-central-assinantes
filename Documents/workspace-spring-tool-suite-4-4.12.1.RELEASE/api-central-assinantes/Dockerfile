FROM openjdk
MAINTAINER Marconi
WORKDIR /app
COPY target/api-central-assinantes-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 5050
ENTRYPOINT ["java", "-jar", "app.jar"]