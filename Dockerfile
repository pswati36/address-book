FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /tmp/app.jar
ENTRYPOINT exec java $JAVA_OPTS /tmp/app.jar --server.port=${serverPort} --spring.config.location=classpath:config/application.properties,file:/tmp/application.properties --logging.file.path=/tmp
HEALTHCHECK --interval=15s --timeout=1s --retries=15 CMD curl -k --fail http://localhost:${serverPort}/uptime || exit 1