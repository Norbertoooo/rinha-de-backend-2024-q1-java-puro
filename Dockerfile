FROM bellsoft/liberica-runtime-container:jre-21-slim-musl
VOLUME /tmp
ARG JAR_FILE=target/rinha-de-backend-2024-q1-java-puro-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]