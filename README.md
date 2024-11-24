
```bash
# Weather API

This project was created as per the requirements of the assignment provided by **Publicis Sapient**. The purpose of this project is to consume the **OpenWeatherMap API** and provide a weather forecast for the next 3 days for a given city, including temperature details and recommendations based on weather conditions.

### Design Principles
This API has been developed using **SOLID design principles**. For more information on SOLID, refer to [SOLID on Wikipedia](https://en.wikipedia.org/wiki/SOLID).

### Requirements
The assignment requires the development of a microservice to show the next 3 days' high and low temperatures for a city. The output should include recommendations based on weather conditions:
- If rain is predicted, include: **"Carry umbrella"**
- If the temperature exceeds 40°C, include: **"Use sunscreen lotion"**

## Tech Stack
- **Java** (Spring Boot framework)
- **Maven** for build automation
- **JUnit** for testing
- **Docker** for containerization
- **Swagger** for API documentation
- **Tomcat** for the servlet container

Since I am an experienced Java programmer and have been using Spring Framework for over 10 years, **Spring Boot** was the natural choice for developing this API.

## Installation

This is a Java-based project, so it requires **JDK 11** (or later) and **Maven 3.6.3** (or later) to run.

### 1. Build and Run the API locally

To build and run the API, use the following commands:

```bash
$ cd weather-api
$ mvn package
$ java -jar targe/tweather-0.0.1-SNAPSHOT.jar
```

### 2. Running the API with Docker

Alternatively, you can run the API using Docker. The latest Docker image is already pushed to Docker Hub.

To run the existing image:

```bash
$ docker run -p 8080:8080 yaswanth/weather-api:latest
```

Then, run the newly built Docker image:

```bash
$ docker run -p 8080:8080 yaswanth/weather-api:latest
```

## Running Tests

You can run the tests after making any changes to the codebase using Maven:

```bash
$ cd weather-api
$ mvn test
```

## REST API

This API exposes the following endpoint:

1. **GET /api/weather/{city}**

   **Parameters:**
   - `{city}`: The name of the city for which you want the weather forecast.

   **Response:**
   - Returns a 3-day weather forecast, including the high and low temperatures for each day, as well as recommendations if necessary (e.g., "Carry umbrella", "Use sunscreen lotion").

## Testing the API

You can test the API using **Postman** or **SoapUI**. Additionally, **Swagger** is integrated into the project for easy testing and documentation. Swagger provides an interactive UI to test the endpoints without additional setup.

To access the Swagger documentation, navigate to:

```
http://localhost:8080/swagger-ui/index.html
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
```

You can copy this directly into your `README.md` file. It includes all the necessary information such as installation instructions, API documentation, and the Swagger URL.
