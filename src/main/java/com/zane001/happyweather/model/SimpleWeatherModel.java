package com.zane001.happyweather.model;

/**
 * Created by zane001 on 2014/9/7.
 * 对应六天内天气，http://113.108.239.119/atad/101010100.html
 */
public class SimpleWeatherModel extends BaseModel {

    private String temp;    //温度
    private String weather; //天气
    private String week;    //星期

    public SimpleWeatherModel(String temp, String weather, String week) {
        this.temp = temp;
        this.weather = weather;
        this.week = week;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return "SimpleWeatherModel{temp'"
                + temp
                + "', weather'"
                + weather
                + "', week'"
                + "'}";
    }
}
