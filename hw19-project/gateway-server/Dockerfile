FROM bellsoft/liberica-openjdk-alpine-musl:17.0.7-7
COPY target/gateway-server.jar /app/app.jar
#COPY cert/sso.crt.pem /app/sso.crt.pem
ENV TZ=Europe/Moscow
#ENTRYPOINT ["keytool", "-import", "-alias", "sso", "-file", "/app/sso.crt.pem", "-keystore", "cacerts", "-storepass", "changeit", "-noprompt"]
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
