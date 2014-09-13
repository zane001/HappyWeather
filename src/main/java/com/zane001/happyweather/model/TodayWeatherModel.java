package com.zane001.happyweather.model;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.zane001.happyweather.dao.TodayWeatherDataHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zane001 on 2014/9/7.
 * 对应http://www.weather.com.cn/data/sk/101010100.html
 * 原格式为{"weatherinfo":{"city":"海淀","cityid":"101010200","temp":"20","WD":"北风","WS":"0级",
 * "SD":"100%","WSE":"0","time":"22:45","isRadar":"1","Radar":"JC_RADAR_AZ9010_JB",
 * "njd":"暂无实况","qy":"1006"}}
 */
public class TodayWeatherModel extends BaseModel {
    private static final Map<String, TodayWeatherModel> CACHE = new HashMap<String, TodayWeatherModel>();

    /** 城市ID */
    @SerializedName("cityid")
    public String id;

    /** 城市名称 */
    @SerializedName("city")
    public String cityName;

    /** 温度 */
    public String temp;

    /** 天气 */
    public String weather;

    /** 风向 */
    @SerializedName("WD")
    public String wind;

    /** 风力 */
    @SerializedName("WS")
    public String ws;

    /** 湿度 */
    @SerializedName("SD")
    public String sd;

    /** 发布时间 */
    public String time;

    private static void addToCache(TodayWeatherModel model) {
        CACHE.put(model.id, model);
    }

    private static TodayWeatherModel getFromCache(String id) {
        return CACHE.get(id);
    }

    public static TodayWeatherModel fromJson(String json) {
        return new Gson().fromJson(json, TodayWeatherModel.class);
    }

    public static TodayWeatherModel fromCursor(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(TodayWeatherDataHelper.TodayWeatherDBInfo.ID));
        TodayWeatherModel model = getFromCache(id);
        if (model != null) {
            return model;
        }
        model = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(TodayWeatherDataHelper.TodayWeatherDBInfo.JSON)), TodayWeatherModel.class);
        addToCache(model);
        return model;
    }

    public static class TodayWeatherRequestData {
        public TodayWeatherModel weatherinfo;
    }
}
