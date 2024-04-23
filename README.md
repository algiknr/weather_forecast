# weather_forecast
Task : Utilizing openweathermap Web APIs in a Spring boot application to produce REST API
## Set Up
### Java app run
* First open the ing_weather project in an IDE(Eclipse,Intelligy etc.)
* Click right on ing_weather project and from Maven tab click update project tab.This will download the dependencies.
* Then, click right on IngWeatherApplication.java file and run as a Java application.
* The application will be running on localhost port 8080.
### Test run
* Click right on IngWeatherApplicationTests.java file and run as a JUnit Test.
### Swagger
* Run the swagger page from below link
```
http://localhost:8080/swagger-ui/index.html#/weather-controller/getWeatherInfo
```
![image](https://github.com/algiknr/weather_forecast/assets/44142761/57de6b10-f655-4f6d-a8ed-9d2d2c526e43)

# API OPERATION
* Get Weather Forecast By City Name
* Method : HTTP.GET
* URL example : http://localhost:8080/weather/Istanbul
* Curl Request :
```
curl -X 'GET' \
  'http://localhost:8080/weather/Istanbul' \
  -H 'accept: */*'
```
* Response
  * HTTP response code 200
  ```
  {
  "maximum_humidity": 90,
  "maximum_feels_like": 295.43
  }
  ```
  * HTTP response code 404
  ```
  CITY NOT FOUND.
  ```  
