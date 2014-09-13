package com.zane001.happyweather.model;

import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.zane001.happyweather.dao.WeatherDataHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zane001 on 2014/9/7.
 * 对应六天内天气，http://113.108.239.119/atad/101010100.html
 * 原格式为{"weatherinfo":{"city":"北京","city_en":"beijing","date_y":"2014年9月11日",
 * "date":"八月十八","week":"星期四","fchh":"18","cityid":"101010100","temp1":"18℃~25℃",
 * "temp2":"17℃~26℃","temp3":"18℃~26℃","temp4":"16℃~25℃","temp5":"15℃~27℃",
 * "temp6":"15℃~28℃","tempF1":"64.4℉~77℉","tempF2":"62.6℉~78.8℉","tempF3":"64.4℉~78.8℉",
 * "tempF4":"60.8℉~77℉","tempF5":"59℉~80.6℉","tempF6":"59℉~82.4℉","weather1":"阵雨",
 * "weather2":"多云转阴","weather3":"阵雨转多云","weather4":"阴","weather5":"晴",
 * "weather6":"阴","img1":"3","img2":"99","img3":"1","img4":"2","img5":"3","img6":"1",
 * "img7":"2","img8":"99","img9":"0","img10":"99","img11":"2","img12":"99",
 * "img_single":"3","img_title1":"阵雨","img_title2":"阵雨","img_title3":"多云",
 * "img_title4":"阴","img_title5":"阵雨","img_title6":"多云","img_title7":"阴",
 * "img_title8":"阴","img_title9":"晴","img_title10":"晴","img_title11":"阴","img_title12":"阴",
 * "img_title_single":"阵雨","wind1":"微风","wind2":"微风","wind3":"微风","wind4":"微风",
 * "wind5":"微风","wind6":"微风","fx1":"微风","fx2":"微风","fl1":"小于3级","fl2":"小于3级",
 * "fl3":"小于3级","fl4":"小于3级","fl5":"小于3级","fl6":"小于3级","index":"舒适",
 * "index_d":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。",
 * "index48":"","index48_d":"","index_uv":"弱","index48_uv":"","index_xc":"不宜",
 * "index_tr":"适宜","index_co":"较舒适","st1":"23","st2":"16","st3":"27","st4":"17",
 * "st5":"26","st6":"16","index_cl":"较不宜","index_ls":"不太适宜","index_ag":"极易发"}}
 */
public class WeatherModel extends BaseModel {
    private static final Map<String, WeatherModel> CACHE = new HashMap<String, WeatherModel>();

    @SerializedName("cityid")   //城市ID
    public String id;

    public String city; // 城市名称

    /**
     * 发布日期
     */
    @SerializedName("date_y")
    public String date;

    /**
     * 星期
     */
    public String week;

    /**
     * 今天-以后6天温度
     */
    public String temp1;
    public String temp2;
    public String temp3;
    public String temp4;
    public String temp5;
    public String temp6;

    /**
     * 今天-以后6天天气
     */
    public String weather1;
    public String weather2;
    public String weather3;
    public String weather4;
    public String weather5;
    public String weather6;

    /**
     * 舒适度
     */
    public String index;
    /**
     * 穿衣建议
     */
    public String index_d;
    /**
     * 紫外线信息
     */
    public String index_uv;
    /**
     * 洗车指数
     */
    public String index_xc;
    /**
     * 旅游指数
     */
    public String index_tr;
    /**
     * 舒适指数
     */
    public String index_co;
    /**
     * 晨练指数
     */
    public String index_cl;
    /**
     * 晾晒指数
     */
    public String index_ls;
    /**
     * 感冒指数
     */
    public String index_ag;

    private static void addToCache(WeatherModel model) {
        CACHE.put(model.id, model);
    }

    private static WeatherModel getFromCache(String id) {
        return CACHE.get(id);
    }

    public static WeatherModel fromJson(String json) {
        return new Gson().fromJson(json, WeatherModel.class);
    }

    public static WeatherModel fromCursor(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(WeatherDataHelper.WeatherDBInfo.ID));
        WeatherModel model = getFromCache(id);
        if (model != null) {
            return model;
        }
        model = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(WeatherDataHelper.WeatherDBInfo.JSON)), WeatherModel.class);
        addToCache(model);
        return model;
    }

    public static class WeatherRequestData {
        public WeatherModel weatherinfo;
    }

    /**
     * 以List形式输出6天的天气
     *
     * @return
     */
    public List<SimpleWeatherModel> toSimpleWeatherList() {
        List<SimpleWeatherModel> list = new ArrayList<SimpleWeatherModel>();
        list.add(new SimpleWeatherModel(week, weather1, temp1));
        list.add(new SimpleWeatherModel(getWeek(getWeekInt(week) + 1), weather2, temp2));
        list.add(new SimpleWeatherModel(getWeek(getWeekInt(week) + 2), weather3, temp3));
        list.add(new SimpleWeatherModel(getWeek(getWeekInt(week) + 3), weather4, temp4));
        list.add(new SimpleWeatherModel(getWeek(getWeekInt(week) + 4), weather5, temp5));
        list.add(new SimpleWeatherModel(getWeek(getWeekInt(week) + 5), weather6, temp6));
        return list;
    }

    private String getWeek(int week) {
        switch (week % 7) {
            case 0:
                return "星期日";
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
        }
        return "星期一";
    }

    private int getWeekInt(String week) {
        if (week.equals("星期一")) {
            return 1;
        } else if (week.equals("星期二")) {
            return 2;
        } else if (week.equals("星期三")) {
            return 3;
        } else if (week.equals("星期四")) {
            return 4;
        } else if (week.equals("星期五")) {
            return 5;
        } else if (week.equals("星期六")) {
            return 6;
        } else if (week.equals("星期日")) {
            return 7;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", date='" + date + '\'' +
                ", week='" + week + '\'' +
                ", temp1='" + temp1 + '\'' +
                ", temp2='" + temp2 + '\'' +
                ", temp3='" + temp3 + '\'' +
                ", temp4='" + temp4 + '\'' +
                ", temp5='" + temp5 + '\'' +
                ", temp6='" + temp6 + '\'' +
                ", weather1='" + weather1 + '\'' +
                ", weather2='" + weather2 + '\'' +
                ", weather3='" + weather3 + '\'' +
                ", weather4='" + weather4 + '\'' +
                ", weather5='" + weather5 + '\'' +
                ", weather6='" + weather6 + '\'' +
                ", index='" + index + '\'' +
                ", index_d='" + index_d + '\'' +
                ", index_uv='" + index_uv + '\'' +
                ", index_xc='" + index_xc + '\'' +
                ", index_tr='" + index_tr + '\'' +
                ", index_co='" + index_co + '\'' +
                ", index_cl='" + index_cl + '\'' +
                ", index_ls='" + index_ls + '\'' +
                ", index_ag='" + index_ag + '\'' +
                '}';
    }
}
