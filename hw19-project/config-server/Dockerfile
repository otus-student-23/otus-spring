FROM bellsoft/liberica-openjdk-alpine-musl:17.0.7-7
COPY target/config-server.jar /app/app.jar
ENV TZ=Europe/Moscow
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
