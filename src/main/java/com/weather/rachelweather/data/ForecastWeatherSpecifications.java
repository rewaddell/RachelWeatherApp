package com.weather.rachelweather.data;

import java.util.Date;

public class ForecastWeatherSpecifications {
    private final Date date;
    private final double high;
    private final double low;
    private final int humidity;
    private final double windSpeed;
    private final int windDeg;

    public ForecastWeatherSpecifications(
            Date date,
            double high,
            double low,
            int humidity,
            double windSpeed,
            int windDeg) {
        this.date = date;
        this.high = high;
        this.low = low;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
    }

    @Override
    public String toString() {
        return String.format(
                "Date (UTC): %s\nHigh: %3.1f, Low: %3.1f, Humidity: %d%%\n Wind: Speed: %3.1f, Degree: %d",
                date.toString(),
                high,
                low,
                humidity,
                windSpeed,
                windDeg);
    }
}
