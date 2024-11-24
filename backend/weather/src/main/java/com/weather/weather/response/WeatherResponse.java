package com.weather.weather.response;

import com.weather.weather.model.DailyForecast;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherResponse {
  private String city;
  private List<DailyForecast> forecast;
}