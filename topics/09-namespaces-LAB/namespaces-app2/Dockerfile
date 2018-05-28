FROM openjdk:8-jre
RUN apt-get update -y && apt-get upgrade -y
ADD build/libs/namespaces-app2-1.0.0.jar app.jar
ARG JAVA_OPTS="-server -Xms256m -Xmx512m"
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar