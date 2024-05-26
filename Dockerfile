FROM openjdk:21-jdk

# set the work directory
WORKDIR /app

# copy the jar to the docker container
COPY target/*.jar app.jar

# expose the port needed by the server
EXPOSE 8080

# start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
