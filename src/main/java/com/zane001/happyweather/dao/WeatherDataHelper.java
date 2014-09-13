package com.zane001.happyweather.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.zane001.happyweather.model.WeatherModel;
import com.zane001.happyweather.util.db.Column;
import com.zane001.happyweather.util.db.SQLiteTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zane001 on 2014/9/7.
 */
public class WeatherDataHelper extends BaseDataHelper {

    public WeatherDataHelper(Context context) {
        super(context);
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.WEATHER_CONTENT_URI;
    }

    private ContentValues getContentValues(WeatherModel model) {
        ContentValues values = new ContentValues();
        values.put(WeatherDBInfo.ID, model.id);
        values.put(WeatherDBInfo.JSON, model.toJson());
        return values;
    }

    public void insert(WeatherModel model) {
        insert(getContentValues(model));
    }

    public void bulkInsert(List<WeatherModel> models) {
        List<ContentValues> contentValueses = new ArrayList<ContentValues>();
        for(WeatherModel model : models) {
            ContentValues values = getContentValues(model);
            contentValueses.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValueses.size()];
        bulkInsert(contentValueses.toArray(valueArray));
    }

    public int deleteAll() {
        synchronized (DataProvider.DBLock) {
            DBHelper dbHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int row = db.delete(WeatherDBInfo.TABLE_NAME, null, null);
            return row;
        }
    }

    public WeatherModel query(String id) {
        WeatherModel model = null;
        Cursor cursor = query(null, WeatherDBInfo.ID + "=?", new String[]{id}, null);
        if(cursor.moveToFirst()) {
            model = WeatherModel.fromCursor(cursor);
        }
        cursor.close();
        return model;
    }

    public static final class WeatherDBInfo implements BaseColumns {
        public static final String TABLE_NAME = "Weathers";
        public static final String ID = "id";
        public static final String JSON = "json";
        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.DataType.INTEGER)
                .addColumn(JSON, Column.DataType.TEXT);
    }
}
