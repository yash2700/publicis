package com.weather.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weather.weather.model.DailyForecast;
import com.weather.weather.response.OpenWeatherResponse;
import com.weather.weather.response.WeatherResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WeatherService {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd" +
                                                                                      " HH:mm:ss");
  private static final double WIND_THRESHOLD_MPH = 10.0;
  private static final double HIGH_TEMP_THRESHOLD_KELVIN = 313.15;
  private static final double KELVIN_TO_CELSIUS = 273.15;
  private static final double MPH_TO_MS_CONVERSION = 2.237;

  private final RestTemplate restTemplate;

  @Value("${weather_api_url}")
  private String weatherApiUrl;

  public WeatherService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public WeatherResponse getWeatherResponse(String city) {
    OpenWeatherResponse apiResponse = getOpenWeatherResponse(city);
    WeatherResponse response = new WeatherResponse();
    if (Objects.nonNull(apiResponse)) {
      response.setCity(apiResponse.getCity().getName());

      Map<LocalDate, List<OpenWeatherResponse.WeatherItem>> dailyWeatherMap = apiResponse.getList()
          .stream()
          .collect(Collectors.groupingBy(item ->
              LocalDateTime.parse(item.getDateText(), DATE_FORMATTER).toLocalDate()));


      List<DailyForecast> dailyForecasts = dailyWeatherMap.entrySet()
          .stream()
          .sorted(Map.Entry.comparingByKey())
          .filter(entry -> entry.getKey().isAfter(LocalDate.now()))
          .limit(3)
          .map(entry -> createDailyForecast(entry.getKey(), entry.getValue())).toList();
      response.setForecast(dailyForecasts);
    }
    return response;
  }

  private DailyForecast createDailyForecast(LocalDate date,
      List<OpenWeatherResponse.WeatherItem> dailyItems) {
    DailyForecast forecast = new DailyForecast();
    forecast.setDate(date);


    double highTempKelvin = dailyItems.stream()
        .mapToDouble(item -> item.getMain().getTemperature())
        .max()
        .orElse(0.0);

    double lowTempKelvin = dailyItems.stream()
        .mapToDouble(item -> item.getMain().getTemperature())
        .min()
        .orElse(0.0);


    double highTempCelsius = highTempKelvin - KELVIN_TO_CELSIUS;
    double lowTempCelsius = lowTempKelvin - KELVIN_TO_CELSIUS;


    forecast.setHighTemp(Math.round(highTempCelsius * 10.0) / 10.0);
    forecast.setLowTemp(Math.round(lowTempCelsius * 10.0) / 10.0);


    List<String> warnings = new ArrayList<>();


    if (highTempKelvin > HIGH_TEMP_THRESHOLD_KELVIN) {
      warnings.add("Use sunscreen lotion");
    }


    boolean hasRain = dailyItems.stream()
        .anyMatch(item -> item.getWeather().stream()
            .anyMatch(w -> w.getMain().equalsIgnoreCase("Rain")));
    if (hasRain) {
      warnings.add("Carry umbrella");
    }


    boolean hasHighWinds = dailyItems.stream()
        .anyMatch(item -> item.getWind().getSpeed() * MPH_TO_MS_CONVERSION > WIND_THRESHOLD_MPH);
    if (hasHighWinds) {
      warnings.add("It's too windy, watch out!");
    }


    boolean hasThunderstorm = dailyItems.stream()
        .anyMatch(item -> item.getWeather().stream()
            .anyMatch(w -> w.getMain().equalsIgnoreCase("Thunderstorm")));
    if (hasThunderstorm) {
      warnings.add("Don't step out! A Storm is brewing!");
    }


    forecast.setWarnings(warnings.stream().distinct().toList());
    return forecast;
  }

  private OpenWeatherResponse getOpenWeatherResponse(String city) {
    String url = String.format(weatherApiUrl, city);
    return restTemplate.getForObject(url, OpenWeatherResponse.class);
  }

}
