package com.cloud.weather.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 天气信息
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Weather implements Serializable {

    private String city;

    private String aqi;

    private String ganmao;

    private String wendu;

    private Yesterday yesterday;

    private List<Forecast> forecast;

}
