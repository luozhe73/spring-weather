package com.cloud.weather.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 天气数据相应对象
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse implements Serializable {

    private Weather data;

    private Integer status;

    private String desc;
}
