package com.zane001.happyweather.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import com.zane001.happyweather.model.CurWeatherModel;
import com.zane001.happyweather.util.db.Column;
import com.zane001.happyweather.util.db.SQLiteTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zane001 on 2014/9/7.
 */
public class CurWeatherDataHelper extends BaseDataHelper {
    public CurWeatherDataHelper(Context context) {
        super(context);
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.WEATHER_CONTENT_CUR_URI;
    }

    private ContentValues getContentValues(CurWeatherModel model) {
        ContentValues values = new ContentValues();
        values.put(CurWeatherDBInfo.ID, model.id);
        values.put(CurWeatherDBInfo.JSON, model.toJson());
        return values;
    }

    public void insert(CurWeatherModel model) {
        insert(getContentValues(model));
    }

    public void bulkInsert(List<CurWeatherModel> models) {
        List<ContentValues> contentValueses = new ArrayList<ContentValues>();
        for (CurWeatherModel model : models) {
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
            int row = db.delete(CurWeatherDBInfo.TABLE_NAME, null, null);
            return row;
        }
    }

    public CurWeatherModel query(String id) {
        CurWeatherModel model = null;
        Cursor cursor = query(null, CurWeatherDBInfo.ID + " = ?", new String[] {id}, null);
        if (cursor.moveToFirst()) {
            model = CurWeatherModel.fromCursor(cursor);
        }
        cursor.close();
        return model;
    }

    public static final class CurWeatherDBInfo implements BaseColumns {

        /** 表名 */
        public static final String TABLE_NAME = "CurWeather";

        /** ID */
        public static final String ID = "id";

        /** JSON */
        public static final String JSON = "json";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.DataType.INTEGER)
                .addColumn(JSON, Column.DataType.TEXT);
    }
}
