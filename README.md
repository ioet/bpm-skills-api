# bpm-skills-api

## Swagger

Swagger is integrated and available in this URL:

```
http://IP_ADDRESS:8082/swagger-ui.html
```

## Docker

The project has integrated a docker plugin so you can generate a docker image using the following Gradle task:

```
$ ./gradlew build docker
```

##Developer

Don't forget to pass the `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY` and `AWS_REGION` to make it work locally.
For any other environment the credentials should be provided by the CI server.

