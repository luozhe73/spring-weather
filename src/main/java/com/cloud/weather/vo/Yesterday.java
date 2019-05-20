package com.cloud.weather.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Yesterday implements Serializable {

    private String date;

    private String high;

    private String fx;

    private String low;

    private String fl;

    private String type;
}
