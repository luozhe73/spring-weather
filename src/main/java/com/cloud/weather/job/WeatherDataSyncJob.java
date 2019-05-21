package com.cloud.weather.job;

import com.cloud.weather.service.CityDataService;
import com.cloud.weather.service.WeatherDataService;
import com.cloud.weather.vo.City;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class WeatherDataSyncJob extends QuartzJobBean {

    private final static Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);

    @Autowired
    private CityDataService cityDataService;

    @Autowired
    private WeatherDataService weatherDataService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        logger.info("weather data sync job start");
        int i = 0;
        List<City> cityList = null;

        try {
            cityList = cityDataService.listCity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (City city : cityList) {
            String cityId = city.getCityId();
            logger.info("总共城市数:" + cityList.size() + "  正在获取第" + ++i + "个城市:" + city.getCityName() + cityId);
            weatherDataService.syncDataByCityId(cityId);
        }

        logger.info("weather data sync job end");
    }
}
