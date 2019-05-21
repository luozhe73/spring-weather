package com.cloud.weather.service.impl;

import com.cloud.weather.service.WeatherDataService;
import com.cloud.weather.service.WeatherReportService;
import com.cloud.weather.vo.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherReportServiceImpl implements WeatherReportService {

    @Autowired
    private WeatherDataService weatherDataService;

    @Override
    public Weather getDataByCityId(String cityId) {
        return weatherDataService.getDataByCityId(cityId).getData();
    }
}
