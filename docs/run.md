# Application information

### Run the application locally

The application provides a `Dockerfile` which can be used to run the application locally.

Make sure you have Docker installed.<br>
You can get Docker from: https://docs.docker.com/get-docker/

To build and run the Docker image you can execute the following steps:
```shell
cd <PATH_TO_APP_ROOT_DIR>
./mvnw clean install
docker build -t roman-numerals-api .
docker run -d -p 8080:8080 -p 9090:9090 roman-numerals-api
```

To check the application started correctly you can execute:
```shell
bash tools/run_example.sh
```

To check the metrics regarding duration and call count:
```shell
bash tools/get_metric.sh
```

If you need to inspect the logs of the application, you can run:
```shell
docker logs -f $(docker ps -q)
```

### Open API

Swagger UI documentation is available at:
http://localhost:8080/swagger-ui/index.html

### Spring Boot actuator

Spring Boot Actuator information is available at:
http://localhost:9090/actuator
