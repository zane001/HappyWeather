package com.zane001.happyweather.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.zane001.happyweather.model.TodayWeatherModel;
import com.zane001.happyweather.util.db.Column;
import com.zane001.happyweather.util.db.SQLiteTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zane001 on 2014/9/7.
 */
public class TodayWeatherDataHelper extends BaseDataHelper {

    public TodayWeatherDataHelper(Context context) {
        super(context);
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.WEATHER_CONTENT_TODAY_URI;
    }

    private ContentValues getContentValues(TodayWeatherModel model) {
        ContentValues values = new ContentValues();
        values.put(TodayWeatherDBInfo.ID, model.id);
        values.put(TodayWeatherDBInfo.JSON, model.toJson());
        return values;
    }

    public void insert(TodayWeatherModel model) {
        insert(getContentValues(model));
    }

    public void bulkInsert(List<TodayWeatherModel> models) {
        List<ContentValues> contentValueses = new ArrayList<ContentValues>();
        for (TodayWeatherModel model : models) {
            ContentValues values = getContentValues(model);
            contentValueses.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValueses.size()];
        bulkInsert(contentValueses.toArray(valueArray));
    }

    public int deleteAll() {
        synchronized (DataProvider.DBLock) {
            DBHelper mDbHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            int row = db.delete(TodayWeatherDBInfo.TABLE_NAME, null, null);
            return row;
        }
    }

    public TodayWeatherModel query(String id) {
        TodayWeatherModel model = null;
        Cursor cursor = query(null, TodayWeatherDBInfo.ID + " = ?", new String[] {id}, null);
        if (cursor.moveToFirst()) {
            model = TodayWeatherModel.fromCursor(cursor);
        }
        cursor.close();
        return model;
    }

    public static final class TodayWeatherDBInfo implements BaseColumns {

        /** 表名 */
        public static final String TABLE_NAME = "TodayWeather";

        /** ID */
        public static final String ID = "id";

        /** JSON */
        public static final String JSON = "json";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.DataType.INTEGER)
                .addColumn(JSON, Column.DataType.TEXT);
    }
}
