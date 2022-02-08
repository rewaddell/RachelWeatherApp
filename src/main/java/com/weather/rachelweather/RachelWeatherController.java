package com.weather.rachelweather;

import com.weather.rachelweather.data.ForecastWeatherSpecifications;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;

@RestController
public class RachelWeatherController {
    private final OpenWeatherConnector openWeatherConnector;

    public RachelWeatherController() {
        openWeatherConnector = new OpenWeatherConnector();
    }

    @GetMapping("/currentweather/{zip}")
    public String getCurrentWeather(@PathVariable String zip, @RequestHeader Map<String, String> headers)
            throws Exception {
        authenticate(headers);
        if (zip.length() != 5) {
            throw new IllegalArgumentException(
                    String.format("%d is not a valid zip code, please enter a 5 digit numeric zip code.", zip)
            );
        }
        return openWeatherConnector.getCurrentWeatherByZip(zip).toString();
    }

    @GetMapping("/weatherforecast/{zip}")
    public String getWeatherForecast(@PathVariable String zip, @RequestHeader Map<String, String> headers)
            throws Exception {
        authenticate(headers);
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

    private void authenticate(Map<String, String> headers) {
        if (headers.containsKey("authorization") && headers.get("authorization").contains("Bearer")) {
            String authorizationHeader = headers.get("authorization");
            int indexOfBearer = authorizationHeader.indexOf("Bearer") + 7;
            int endOfBearer = authorizationHeader.indexOf("[^\\W_]+", indexOfBearer);
            String bearerToken;
            if (endOfBearer == -1) {
                bearerToken = authorizationHeader.substring(indexOfBearer);
            } else {
                bearerToken = authorizationHeader.substring(indexOfBearer, endOfBearer);
            }
            if (bearerToken.length() == 20) {
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
