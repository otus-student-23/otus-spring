FROM bellsoft/liberica-openjdk-alpine-musl:17.0.7-7
COPY target/booklibrary-microservice.jar /app/app.jar
ENV TZ=Europe/Moscow
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
