package com.weather.weather.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DailyForecast {
  private LocalDate date;
  private double highTemp;
  private double lowTemp;
  private List<String> warnings = new ArrayList<>();
}