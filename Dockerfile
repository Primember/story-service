FROM eclipse-temurin:17-jre-alpine
ENV APP_FILE=story-service-0.0.1.jar
ENV APP_HOME=/app
WORKDIR ${APP_HOME}
COPY target/${APP_FILE} ${APP_HOME}
ENTRYPOINT [ "sh", "-c" ]
CMD [ "exec java -jar ${APP_FILE} --spring.profiles.active=local" ]