# roman-numeral-api

### Introduction

This is a Spring Boot application that offers a REST API for converting a range of Integer numbers into Roman Numerals.

### Documentation

More information about the project can be found inside the [docs](docs/README.md) folder.

### Run the application locally

The application provides a `Dockerfile` which can be used to run the application locally.

Make sure you have Docker installed.<br>
You can get Docker from: https://docs.docker.com/get-docker/ 

To build and run the Docker image you can execute the following steps:
```shell
cd <PATH_TO_APP_ROOT_DIR>
./mvnw clean install
docker build -t roman-numerals-api .
docker run -dp 8080:8080 roman-numerals-api
```

To check the application started correctly you can execute:
```shell
bash tools/run_example.sh
```

If you need to inspect the logs of the application, you can run:
```shell
docker logs -f $(docker ps -q)
```
