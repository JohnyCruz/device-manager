
# Device Manager

REST service that supports the management of a device database

## Supported operations
1. Add device;
2. Get device by identifier;
3. List all devices;
4. Update device (full and partial);
5. Delete a device;
6. Search device by brand;

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install before starting the project

```
Java 17
Apache Maven 3.6.3
Docker
```

### Installing

A step by step series of commands that show you how to get a development env running

from the terminal clone the project from my github repository

```
git clone git@github.com:JohnyCruz/device-manager.git
```
go inside the device-manager folder

```
cd device-manager
```
run the docker container which contains the PostgreSQL database

```
docker compose build
docker compose up
```
in another terminal, and still inside teh device-manager folder, start the application 

```
mvn spring-boot:run
```

Validate that the application is running as expected by opening the swagger http://localhost:8080/swagger-ui/index.html

## Running the tests

you can run the tests of the project with the command below or in your IDE

```
mvn test
```

## cURL list

create device
```
curl --location 'http://localhost:8080/devices/' \
--header 'Content-Type: application/json' \
--header 'X-API-KEY: key' \
--data '{
    "id":"12",
    "name": "Device Name",
    "brand": "Device Brand"
}'
```

get All Devices
```
curl --location 'http://localhost:8080/devices/' \
--header 'X-API-KEY: key'
```


get device by id
```
curl --location 'http://localhost:8080/devices/1' \
--header 'X-API-KEY: key'
```

get devices by brand
```
curl --location 'http://localhost:8080/devices/brand/Device Brand' \
--header 'X-API-KEY: key'
```

update device - if full=true it will reset the creationTime to the updated time
```
curl --location --request PUT 'http://localhost:8080/devices/?full=true' \
--header 'Content-Type: application/json' \
--header 'X-API-KEY: key' \
--data '    {
        "id": 1,
        "name": "Device Name",
        "brand": "Device Brand 2",
        "creationTime": "2024-08-26T16:05:50.135244"
    }'
```

Delete Devices
```
curl --location --request DELETE 'http://localhost:8080/devices/40' \
--header 'X-API-KEY: key'
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Docker](https://www.docker.com/) - Container Management
* [Postgres](https://maven.apache.org/) - Docker Image of the PostgreSQL object-relational database 

## Author

* **Johny Cruz** - [Johny Cruz](https://www.linkedin.com/in/johnycruz/)

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE.md](LICENSE.md) file for details