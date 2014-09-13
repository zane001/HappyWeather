package com.zane001.happyweather.model;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.zane001.happyweather.dao.CurWeatherDataHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zane001 on 2014/9/7.
 * 对应http://www.weather.com.cn/data/cityinfo/101010100.html
 * 原格式为{"weatherinfo":{"city":"北京","cityid":"101010100","temp1":"18℃","temp2":"25℃",
 * "weather":"阵雨","img1":"n3.gif","img2":"d3.gif","ptime":"18:00"}}
 */
public class CurWeatherModel extends BaseModel {
    private static final Map<String, CurWeatherModel> CACHE = new HashMap<String, CurWeatherModel>();

    @SerializedName("cityid")
    public String id;

    @SerializedName("city")
    public String cityName;

    public String weather;

    @SerializedName("ptime")
    public String time;

    private static void addToCache(CurWeatherModel model) {
        CACHE.put(model.id, model);
    }

    private static CurWeatherModel getFromCache(String id) {
        return CACHE.get(id);
    }

    public static WeatherModel fromJson(String json) {
        return new Gson().fromJson(json, WeatherModel.class);
    }

    public static CurWeatherModel fromCursor(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(CurWeatherDataHelper.CurWeatherDBInfo.ID));
        CurWeatherModel model = getFromCache(id);
        if (model != null) {
            return model;
        }
        model = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(CurWeatherDataHelper.CurWeatherDBInfo.JSON)), CurWeatherModel.class);
        addToCache(model);
        return model;
    }

    public static class CurWeatherRequestData {
        public CurWeatherModel weatherInfo;
    }
}
