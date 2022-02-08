package com.weather.rachelweather;

import com.weather.rachelweather.data.ForecastWeatherSpecifications;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RachelWeatherController {
    private final OpenWeatherConnector openWeatherConnector;

    public RachelWeatherController() {
        openWeatherConnector = new OpenWeatherConnector();
    }

    @GetMapping("/currentweather/{zip}")
    public String getCurrentWeather(@PathVariable String zip) throws Exception {
        if (zip.length() != 5) {
            throw new IllegalArgumentException(
                    String.format("%d is not a valid zip code, please enter a 5 digit numeric zip code.", zip)
            );
        }
        return openWeatherConnector.getCurrentWeatherByZip(zip).toString();
    }

    @GetMapping("/weatherforecast/{zip}")
    public String getWeatherForecast(@PathVariable String zip) throws Exception {
        if (zip.length() != 5) {
            throw new IllegalArgumentException(
                    String.format("%s is not a valid zip code, please enter a 5 digit numeric zip code.", zip)
            );
        }
        List<ForecastWeatherSpecifications> forecast = openWeatherConnector.getForecastByZip(zip);
        StringBuilder response = new StringBuilder(String.format("The 14-day forecast for the zip code %s: ", zip));
        for (ForecastWeatherSpecifications day : forecast) {
            response.append(System.getProperty("line.separator")).append(day.toString());
        }
        return response.toString();
    }
}
