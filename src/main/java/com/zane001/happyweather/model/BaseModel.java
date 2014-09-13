package com.zane001.happyweather.model;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * Created by zane001 on 2014/9/7.
 */
public abstract class BaseModel implements Serializable {

    public String toJson() {
        return new Gson().toJson(this);
    }
}
