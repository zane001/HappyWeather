package com.zane001.happyweather;

import android.app.Application;
import android.content.Context;

import com.zane001.happyweather.domain.CitySaxParseHandler;
import com.zane001.happyweather.model.WeatherModel;
import com.zane001.happyweather.model.city.AreaModel;
import com.zane001.happyweather.model.city.ProvinceModel;
import com.zane001.happyweather.util.FileUtil;
import com.zane001.happyweather.util.LogUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@EApplication
public class App extends Application {
    protected static final String TAG = "App";
    private static Context context;

    private static List<ProvinceModel> provinceModels;
    private static List<AreaModel> areaModels;
    public static WeatherModel curWeatherModel;
    private static int curWeatherIndex;

    public static Context getContext() {
        return context;
    }

    /**
     * 进入界面，初始化城市列表，读取assets文件夹下的city.xml
     */
    private void initProvinceModels() {
        try {
            InputStream in = getAssets().open(Const.FILE_CITY_NAME);
            provinceModels = CitySaxParseHandler.getProvinceModel(in);
            LogUtil.i(TAG, provinceModels.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ProvinceModel> getProvinceModels() {
        return provinceModels;
    }

    /**
     * 初始化我的城市
     */
    private void initMyArea() {
        List<AreaModel> models = (List<AreaModel>) FileUtil.readObjsFromFile(Const.FILE_MY_AREA);
        if(models != null) {
            areaModels.addAll(models);
            LogUtil.i(TAG, areaModels.get(0).getCityName());
        }
    }

    public static void setCurCityIndex(int index) {
        curWeatherIndex = index;
    }

    public static int getCurCityIndex() {
        return curWeatherIndex;
    }

    /**
     * 添加我的城市
     */
    public static String addMyArea(AreaModel model) {
        if(model == null) {
            LogUtil.i(TAG, "null");
            return null;
        }
        if(areaModels.size() >= 5) {
            return getContext().getString(R.string.city_exceed_num);
        } else {
            for(AreaModel areaModel : areaModels) {
                if(areaModel.getCityId().equals(model.getCityId())) {
                    return getContext().getString(R.string.city_already_exists);
                }
            }
            areaModels.add(0, model);
            FileUtil.writeObjsToFile(areaModels, Const.FILE_MY_AREA, Context.MODE_PRIVATE);
            return getContext().getString(R.string.city_add_success);
        }
    }

    /**
     * 删除城市信息
     */
    public static AreaModel removeMyArea(int position) {
        AreaModel model = areaModels.remove(position);
        FileUtil.writeObjsToFile(areaModels, Const.FILE_MY_AREA, Context.MODE_PRIVATE);
        return model;
    }

    public static boolean removeMyArea(AreaModel model) {
        boolean is = areaModels.remove(model);
        FileUtil.writeObjsToFile(areaModels, Const.FILE_MY_AREA, Context.MODE_PRIVATE);
        return is;
    }

    public static List<AreaModel> getMyArea() {
        return areaModels;
    }

    @AfterInject
    void init() {

        this.context = getApplicationContext();
        this.areaModels = new ArrayList<AreaModel>();
        this.curWeatherIndex = 0;
        this.initMyArea();
        this.initProvinceModels();
    }
}
