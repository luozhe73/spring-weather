package com.cloud.weather.service.impl;

import com.cloud.weather.service.CityDataService;
import com.cloud.weather.util.XmlBuilder;
import com.cloud.weather.vo.City;
import com.cloud.weather.vo.CityList;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CityDataServiceImpl implements CityDataService {

    @Override
    public List<City> listCity() throws Exception {
        //读取文件
        Resource resource = new ClassPathResource("citylist.xml");
        BufferedReader bf = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = bf.readLine()) != null){
            buffer.append(line);
        }
        bf.close();
        //xml转为java对象
        CityList cityList= (CityList) XmlBuilder.xmlStrToObject(CityList.class,buffer.toString());
        return cityList.getCityList();
    }
}
