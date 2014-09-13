package com.zane001.happyweather.model.city;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zane001 on 2014/9/7.
 * 城市天气model
 */
public class CityModel extends BaseCityModel {
    /**
     * 下级区/县
     */
    private List<AreaModel> areaModels;

    public CityModel() {
        areaModels = new ArrayList<AreaModel>();
    }

    public List<AreaModel> getAreaModels() {
        return areaModels;
    }

    public void setAreaModels(List<AreaModel> areaModels) {
        this.areaModels = areaModels;
    }

    @Override
    public String toString() {
        return "CityModel{areaModels'"
                + areaModels
                + "'}";
    }
}
