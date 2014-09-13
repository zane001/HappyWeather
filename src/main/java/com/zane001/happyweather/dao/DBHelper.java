package com.zane001.happyweather.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zane001 on 2014/9/7.
 */
public class DBHelper extends SQLiteOpenHelper {

    protected static final String DB_NAME = "happyweather.db";
    protected static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        WeatherDataHelper.WeatherDBInfo.TABLE.create(db);
        TodayWeatherDataHelper.TodayWeatherDBInfo.TABLE.create(db);
        CurWeatherDataHelper.CurWeatherDBInfo.TABLE.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
