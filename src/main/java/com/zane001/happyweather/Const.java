package com.zane001.happyweather;

/**
 * Created by zane001 on 2014/9/7.
 */

public class Const {

    /** 是否调试 */
    public static final boolean DEBUG = false;

    /** 配置文件名 */
    public static final String CONFIG_NAME = "HappyWeather.cof";
    /** 是否退出时杀死进程 */
    public static final String CONFIG_EXIT_KILL = "exit_kill";
    /** 是否发送通知栏通知 */
    public static final String CONFIG_NOTIFY = "notify";

    /** 我的城市文件名 */
    protected final static String FILE_MY_AREA = "MyArea";

    /** 默认城市信息 */
    public static final String DEF_CITY_ID = "010101";
    public static final String DEF_CITY_NAME = "北京";
    public static final String DEF_WEATHER_CODE = "101010100";


    /** 城市信息xml文件 */
    public static final String FILE_CITY_NAME = "city.xml";

    /** 六天天气 */
    public static final String WEATHER = "http://113.108.239.119/atad/";

    /** 实时天气 */
    public static final String WEATHER_NOW = "http://www.weather.com.cn/data/sk/";
    public static final String WEATHER_CUR = "http://www.weather.com.cn/data/cityinfo/";

    /** 测试URL */
    public static final String TEST_URL = "http://113.108.239.119/atad/101110101.html";
}

