package com.cloud.weather.service.impl;

import com.cloud.weather.service.WeatherDataCollectionService;
import com.cloud.weather.vo.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WeatherDataCollectionServiceImpl implements WeatherDataCollectionService {

    private final static Logger logger = LoggerFactory.getLogger(WeatherDataCollectionServiceImpl.class);

    private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";

    private static final Long TIME_OUT = 1800L;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void syncDataByCityId(String cityId) {
        String uri = WEATHER_URI + "citykey=" + cityId;
        this.saveWeatherData(uri);
    }

    private void saveWeatherData(String uri) {

        String strBody;
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

        if (respString.getStatusCodeValue() == 200) {
            strBody = respString.getBody();
            //数据写入缓存
            opsForValue.set(uri, strBody, TIME_OUT, TimeUnit.SECONDS);
        }
    }

    private WeatherResponse doGetWeather(String uri) {

        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse weatherResponse = null;
        String strBody = null;

        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();

        //先查缓存,缓存中没有再调用第三方服务
        if (stringRedisTemplate.hasKey(uri)) {

            logger.info("redis has data");
            strBody = opsForValue.get(uri);
        } else {

            logger.info("data write to redis");
            ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);

            if (respString.getStatusCodeValue() == 200) {
                strBody = respString.getBody();
            }

            //数据写入缓存
            opsForValue.set(uri, strBody, TIME_OUT, TimeUnit.SECONDS);
        }

        try {
            weatherResponse = mapper.readValue(strBody, WeatherResponse.class);
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error("ERROR", e);
        }

        return weatherResponse;
    }
}
