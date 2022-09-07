# my-application

|                      | Version      | Configuration                                                                                                                                                               |
|----------------------|--------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Java                 | 17           | [build.gradle](/build.gradle?plain=1#L8-L12)                                                                                                                                |
| Quarkus!             | 2.11.2.Final | [gradle.properties](/gradle.properties?plain=1#L3-L7)                                                                                                                       |
| Postgres             |              | [application.properties](/src/main/resources/application.properties?plain=1#L7-L16), [DatabaseConfig.java](/src/main/java/com/me/my_application/config/DatabaseConfig.java) |
| Jackson              |              |                                                                                                                                                                             |
| Docker conf examples |              | [Dockerfile](/src/main/docker/)                                                                                                                                             |

## Setup using SDKMAN!

```shell script
curl -s "https://get.sdkman.io" | bash                              # Install SDKMAN!
sdk install quarkus 2.11.2.Final                                    # Install Quarkus
sdk install java 22.2.r17-grl                                       # Install GraalVM Java 17
export GRAALVM_HOME=$HOME/.sdkman/candidates/java/22.2.r17-grl/     # Setup environment variable for GraalVM
gu install native-image                                             # Necessary to build native java with GraalVM
sudo apt install build-essential libz-dev zlib1g-dev                # Necessary to build native java with GraalVM
```

## Build/run development mode

```shell script
# Setting up the database
mkdir -p /var/lib/postgresql/data/ # If doesn't exist
docker run --rm -d --name postgres -v /var/lib/postgresql/data:/var/lib/postgresql/data -p 5432:5432 -e "POSTGRES_PASSWORD=medb123" -e "POSTGRES_DB=my-application" postgres:14.4

# Build and launch
quarkus dev
```

## Build/run docker with native

```shell script
# Build
quarkus build --native
docker build -f deployments/docker/Dockerfile -t $WANTED_TAG .

# Setting up the database
mkdir -p /var/lib/postgresql/data/ # If doesn't exist

# Launch
docker-compose -f ./docker/docker-compose.yml up
```

It will create docker image: my-application:1.0.0-SNAPSHOT

# OpenAPI, Swagger UI

[Documentation](https://quarkus.io/guides/openapi-swaggerui)

|            | Access                             |
|------------|------------------------------------|
| OpenAPI    | http://localhost:8080/q/openapi    |
| Swagger UI | http://localhost:8080/q/swagger-ui |
