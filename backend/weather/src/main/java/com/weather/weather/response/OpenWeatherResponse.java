package com.weather.weather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse {
  private List<WeatherItem> list;
  private City city;

  @Data
  public static class WeatherItem {
    private Main main;
    private List<Weather> weather;
    private Wind wind;
    @JsonProperty("dt_txt")
    private String dateText;
  }

  @Data
  public static class Main {
    @JsonProperty("temp")
    private double temperature;
    @JsonProperty("temp_min")
    private double tempMin;
    @JsonProperty("temp_max")
    private double tempMax;
    private double humidity;
  }

  @Data
  public static class Weather {
    private int id;
    private String main;
    private String description;
  }

  @Data
  public static class Wind {
    private double speed;
  }

  @Data
  public static class City {
    private String name;
    private String country;
  }
}