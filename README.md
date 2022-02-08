# RachelWeatherApp
This app consists of two simple GET endpoints to access weather data for a given zip code. To access data from either endpoint, a Bearer token consisting of 20 alphanumeric characters is required.

- The first endpoint - `getCurrentWeather` takes a US 5-digit zip code as a path parameter and finds the current weather at that zip code. It provides the user with a description of current weather conditions, temperature in Farenheit, wind speed in miles per hour, and the degree representation of the direction of the wind.
    - When run locally, this endpoint can be accessed at "http://localhost:8080/currentweather/{zip}" when used with a Bearer token authorization header
- The second endpoint - `getWeatherForecast` also takes a US 5-digit zip code as a path parameter and finds the forecast for the next 14 days. For each day, it returns the relevant date, forecasted high and low temperatures in Farenheit, humidity, wind speed in miles per hour, and degree of the wind.
    - When run locally, this endpoint can be accessed at "http://localhost:8080/weatherforecast/{zip}" when used with a Bearer token authorization header
