package com.zane001.happyweather.model.city;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zane001 on 2014/9/7.
 * 省份天气model
 */
public class ProvinceModel extends BaseCityModel {
    private List<CityModel> cityModels;

    public ProvinceModel() {
        cityModels = new ArrayList<CityModel>();
    }

    public List<CityModel> getCityModels() {
        return cityModels;
    }

    public void setCityModelList(List<CityModel> cityModelList) {
        this.cityModels = cityModelList;
    }

    @Override
    public String toString() {
        return "ProvinceMode{cityModels'"
                + cityModels
                + "'}";
    }
}
