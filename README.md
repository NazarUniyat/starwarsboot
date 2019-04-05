# starwarsboot application

This application gives you an opportunity to calculate the body mass index of Star Wars characters and compare them. Using this service you can input a name of characters and get the result of a comparison. Also, you will have information about their parameters and BMI. Service use external StarWarsAPI (swapi.co) to get information about characters.
## Getting Started

### Prerequisites

In order to run this application you will need

```
Git
JDK 8 or later
Maven 3.6.0 or later
```

### Clone

At first you should clone project using URL

```
https://github.com/NazarUniyat/starwarsboot.git
```

### Configuration

In application.properties file you should change application host for pagination 

```
characters.pagination.link=http://{app.host}/characters?page=
```
default app host in URL:
```
characters.pagination.link=http://localhost:8080/characters?page=
results.pagination.link=http://localhost:8080/results?page=
```

### Running the tests

After configuration you should run all tests
```
mvn test
```

### Build an executable WAR

You can run the application from the command line using:

```
mvn spring-boot:run
```
Or you can build a single executable WAR file that contains all the necessary dependencies, classes, and resources with:
```
mvn clean package
```
Then you can run the JAR file with:
```
java -jar target/*.jar
```

## Author

* **Nazar Uniyat** - [GitHub link](https://github.com/NazarUniyat)

