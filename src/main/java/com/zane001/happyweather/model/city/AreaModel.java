package com.zane001.happyweather.model.city;

/**
 * Created by zane001 on 2014/9/7.
 * 县/区天气model
 */
public class AreaModel extends BaseCityModel {
    private String weatherCode;

    public AreaModel() {
    }

    public AreaModel(String cityId, String cityName, String weatherCode) {
        super(cityId, cityName);
        this.weatherCode = weatherCode;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    @Override
    public String toString() {
        return "AreaModel{weatherCode='"
                + weatherCode
                + "'}";
    }
}
