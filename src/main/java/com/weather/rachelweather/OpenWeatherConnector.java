package com.weather.rachelweather;

import com.weather.rachelweather.data.CurrentWeatherResponse;
import com.weather.rachelweather.data.ForecastWeatherSpecifications;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpenWeatherConnector {
    private static final String API_KEY = "1bcd32943724e795f45d778889c0beea";
    private static final String CURRENT_WEATHER_URL_PATTERN =
            "http://api.openweathermap.org/data/2.5/weather?zip=%s,us&appid="
                    + API_KEY + "&units=imperial";
    private static final String FORECAST_URL_PATTERN =
            "http://api.openweathermap.org/data/2.5/forecast/daily?zip=%s,us&appid="
                    + API_KEY + "&units=imperial&cnt=14";


    public CurrentWeatherResponse getCurrentWeatherByZip(String zip) throws Exception {
        URL url = new URL(String.format(CURRENT_WEATHER_URL_PATTERN, zip));
        String jsonReply = getApiResponse(url);

        JSONObject jsonObject = new JSONObject(jsonReply);
        jsonObject.remove("coord");
        JSONObject weatherInfo = jsonObject.getJSONArray("weather").getJSONObject(0);
        JSONObject main = jsonObject.getJSONObject("main");
        JSONObject wind = jsonObject.getJSONObject("wind");

        int temp = main.getInt("temp");
        String weather = weatherInfo.getString("main");
        double windSpeed = wind.getDouble("speed");
        int windDeg = wind.getInt("deg");

        return new CurrentWeatherResponse(temp, weather, windSpeed, windDeg);
    }

    public List<ForecastWeatherSpecifications> getForecastByZip(String zip) throws Exception {
        URL url = new URL(String.format(FORECAST_URL_PATTERN, zip));
        String jsonReply = getApiResponse(url);

        JSONObject jsonObject = new JSONObject(jsonReply);
        JSONArray forecastList = jsonObject.getJSONArray("list");
        List<ForecastWeatherSpecifications> completeForecast = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            JSONObject day = forecastList.getJSONObject(i);
            Date date = new Date(day.getLong("dt") * 1000);
            JSONObject temp = day.getJSONObject("temp");
            double high = temp.getDouble("max");
            double low = temp.getDouble("min");
            int humidity = day.getInt("humidity");
            double windSpeed = day.getDouble("speed");
            int windDeg = day.getInt("deg");
            completeForecast.add(new ForecastWeatherSpecifications(date, high, low, humidity, windSpeed, windDeg));
        }
        return completeForecast;
    }

    private String getApiResponse(URL url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200 && conn.getResponseCode() != 201) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode()
                    + " Message: "
                    + conn.getResponseMessage());
        }

        InputStream response = conn.getInputStream();
        String jsonReply = convertResponseStreamToString(response);
        conn.disconnect();

        return jsonReply;
    }

    private static String convertResponseStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}