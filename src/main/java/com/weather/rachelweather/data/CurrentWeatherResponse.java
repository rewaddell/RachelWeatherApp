package com.weather.rachelweather.data;

public class CurrentWeatherResponse {
    private final double temp;
    private final String weather;
    private final double windSpeed;
    private final int windDegree;

    public CurrentWeatherResponse(int temp, String weather, double windSpeed, int windDegree) {
        this.temp = temp;
        this.weather = weather;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
    }

    @Override
    public String toString() {
        return String.format("The current weather for the specified location is %s. "
                + "The temperature is %3.1f\u00B0F with wind of speed %3.1f mph at degree %d",
                weather.toLowerCase(),
                temp,
                windSpeed,
                windDegree);
    }
}
