package com.zane001.happyweather.dao;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.zane001.happyweather.App;

/**
 * Created by zane001 on 2014/9/7.
 */
public class DataProvider extends ContentProvider {

    protected static final String TAG = "DataProvider";
    protected static final Object DBLock = new Object();
    protected static final String AUTHORITY = "com.zane001.happyweather.provider";
    public static final String SCHEME = "content://";

    //message
    public static final String PATH_WEATHER = "/weathers";
    public static final String PATH_TODAY_WEATHER = "/todayWeather";
    public static final String PATH_CUR_WEATHER = "/curWeather";

    public static final Uri WEATHER_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_WEATHER);
    public static final Uri WEATHER_CONTENT_TODAY_URI = Uri.parse(SCHEME + AUTHORITY + PATH_TODAY_WEATHER);
    public static final Uri WEATHER_CONTENT_CUR_URI = Uri.parse(SCHEME + AUTHORITY + PATH_CUR_WEATHER);

    private static final int WEATHERS = 0;
    private static final int TODAY_WEATHER = 1;
    private static final int CUR_WEATHER = 2;

    public static final String WEATHER_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.zane001.happyweather.weather";
    public static final String TODAY_WEATHER_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.zane001.happyweather.todayWeather";
    public static final String CUR_WEATHER_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.zane001.happyweather.curWeather";

    private static final UriMatcher matcher;

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "weathers", WEATHERS);  //com.zane001.happyweather.provider/weathers/0
        matcher.addURI(AUTHORITY, "todayWeather", TODAY_WEATHER);
        matcher.addURI(AUTHORITY, "curWeather", CUR_WEATHER);
    }

    private static DBHelper dbHelper;

    public static DBHelper getDBHelper() {
        if (dbHelper == null) {
            dbHelper = new DBHelper(App.getContext());
        }
        return dbHelper;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case WEATHERS:
                return WEATHER_CONTENT_TYPE;
            case TODAY_WEATHER:
                return TODAY_WEATHER_CONTENT_TYPE;
            case CUR_WEATHER:
                return CUR_WEATHER_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri" + uri);
        }
    }

    protected String matchTable(Uri uri) {
        String table = null;
        switch (matcher.match(uri)) {
            case WEATHERS:
                table = WeatherDataHelper.WeatherDBInfo.TABLE_NAME;
                break;
            case TODAY_WEATHER:
                table = TodayWeatherDataHelper.TodayWeatherDBInfo.TABLE_NAME;
                break;
            case CUR_WEATHER:
                table = CurWeatherDataHelper.CurWeatherDBInfo.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
        return table;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        synchronized (DBLock) {
            String table = matchTable(uri);
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            long rowId = 0;
            db.beginTransaction();
            try {
                rowId = db.insert(table, null, contentValues);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
            if (rowId > 0) {
                Uri returnUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(uri, null);
                return returnUri;
            }
            throw new SQLException("Failed to insert row into" + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        synchronized (DBLock) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();

            int count = 0;
            String table = matchTable(uri);
            db.beginTransaction();
            try {
                count = db.delete(table, selection, selectionArgs);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        synchronized (DBLock) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            String table = matchTable(uri);

            int count = 0;
            db.beginTransaction();
            try {
                count = db.update(table, contentValues, selection, selectionArgs);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        synchronized (DBLock) {
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            String table = matchTable(uri);
            queryBuilder.setTables(table);

            SQLiteDatabase db = getDBHelper().getReadableDatabase();
//            Cursor cursor = query(uri, projection, selection, selectionArgs, sortOrder);
            Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
    }
}
