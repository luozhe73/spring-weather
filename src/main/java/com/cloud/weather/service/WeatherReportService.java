package com.cloud.weather.service;

import com.cloud.weather.vo.Weather;

public interface WeatherReportService {

    Weather getDataByCityId(String cityId);
}
