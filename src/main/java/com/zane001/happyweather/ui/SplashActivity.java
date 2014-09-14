package com.zane001.happyweather.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.zane001.happyweather.App;
import com.zane001.happyweather.Const;
import com.zane001.happyweather.R;
import com.zane001.happyweather.domain.CitySaxParseHandler;
import com.zane001.happyweather.model.city.AreaModel;
import com.zane001.happyweather.model.city.CityModel;
import com.zane001.happyweather.model.city.ProvinceModel;
import com.zane001.happyweather.util.SharedPrefUtil;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.umeng.analytics.MobclickAgent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

import java.io.InputStream;
import java.util.List;

/**
 * Created by zane001 on 2014/9/7.
 */
@Fullscreen
@EActivity(R.layout.activity_splash)
public class SplashActivity extends Activity {

    protected static final int START_TIME = 2 * 1000;
    public LocationClient mLocationClient = null;
    private static List<ProvinceModel> provinceModels;
    private static List<CityModel> cityModels;
    private static List<AreaModel> areaModels;

    @AfterViews
    void initActivity() {
        isFirst();
    }

    private void isFirst() {
        if (SharedPrefUtil.isFirst()) {
            /**
             * 百度地图地位
             */
            mLocationClient = new LocationClient(this); // 声明LocationClient类
            // 注册监听函数
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
            option.setOpenGps(true);
            option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
            option.setScanSpan(500000);// 设置发起定位请求的间隔时间为5000ms
            option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
            mLocationClient.setLocOption(option);

            mLocationClient.registerLocationListener(new BDLocationListener() {

                @Override
                public void onReceiveLocation(BDLocation location) {
                    if (location == null) {
                        return;
                    }
                    String province = location.getProvince();
                    String city = location.getCity();
                    String area = location.getDistrict();
                    String city_id = "";
                    String city_name = "";
                    String weather_code = "";
                    Log.i("获取省、市、县信息", province + ":" + city + ":" + area);
                    InputStream in = null;
                    try {
                        in = getAssets().open(Const.FILE_CITY_NAME);
                        provinceModels = CitySaxParseHandler.getProvinceModel(in);
                        in = getAssets().open(Const.FILE_CITY_NAME);
                        cityModels = CitySaxParseHandler.getCityModel(in);
                        in = getAssets().open(Const.FILE_CITY_NAME);
                        areaModels = CitySaxParseHandler.getAreaModel(in);
                        for (ProvinceModel provinceModel : provinceModels) {
                            if (province.contains(provinceModel.getCityName())) {
                                for(CityModel cityModel : cityModels) {
                                    if(city.contains(cityModel.getCityName())) {
                                        for(AreaModel areaModel : areaModels) {
                                            if(area.contains(areaModel.getCityName())) {
                                                city_id = areaModel.getCityId();
                                                city_name = areaModel.getCityName();
                                                weather_code = areaModel.getWeatherCode();
                                                App.addMyArea(new AreaModel(city_id, city_name, weather_code)); //根据定位自动获取所在县/区的天气
                                            }
                                        }
                                    }
                                }
                              }
                        }
                    } catch (Exception e) {    //如果没有及时定位城市信息，默认使用北京天气
                        e.printStackTrace();
                        App.addMyArea(new AreaModel(Const.DEF_CITY_ID, Const.DEF_CITY_NAME, Const.DEF_WEATHER_CODE));
                    }
                }

                public void onReceivePoi(BDLocation poiLocation) {
                }

            });
            mLocationClient.start();
            mLocationClient.requestLocation();
//            App.addMyArea(new AreaModel(Const.DEF_CITY_ID, Const.DEF_CITY_NAME, Const.DEF_WEATHER_CODE));
            start(GuideActivity_.class);
        } else {
            start(MainActivity_.class);
        }
    }

    /**
     * 启动应用
     */
    private void start(final Class c) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, c);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, START_TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            mLocationClient = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
