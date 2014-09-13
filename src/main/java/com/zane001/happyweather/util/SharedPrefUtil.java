package com.zane001.happyweather.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;

import com.zane001.happyweather.App;
import com.zane001.happyweather.Const;

public class SharedPrefUtil {

    /**
     * 是否第一次进入程序
     */
    public static final String IS_FIRST = "isFirst";
    public static boolean isFirst() {
        //获取是否第一次进入
        return getBoolean(IS_FIRST, true);
    }

    public static void setFirst() {
        putBoolean(IS_FIRST, false);
    }

	public static boolean getBoolean(String key, boolean defaultValue) {
		SharedPreferences pref = App.getContext().getSharedPreferences(Const.CONFIG_NAME, Context.MODE_PRIVATE);
		return pref.getBoolean(key, defaultValue);
	}
	
	public static String getString(String key, String defaultValue) {
		SharedPreferences pref = App.getContext().getSharedPreferences(Const.CONFIG_NAME, Context.MODE_PRIVATE);
		return pref.getString(key, defaultValue);
	}
	
	public static int getInt(String key, int defaultValue) {
		SharedPreferences pref = App.getContext().getSharedPreferences(Const.CONFIG_NAME, Context.MODE_PRIVATE);
		return pref.getInt(key, defaultValue);
	}
	
	public static long getLong(String key, long defaultValue) {
		SharedPreferences pref = App.getContext().getSharedPreferences(Const.CONFIG_NAME, Context.MODE_PRIVATE);
		return pref.getLong(key, defaultValue);
	}
	
	public static float getFloat(String key, float defaultValue) {
		SharedPreferences pref = App.getContext().getSharedPreferences(Const.CONFIG_NAME, Context.MODE_PRIVATE);
		return pref.getFloat(key, defaultValue);
	}
	
	public static void putBoolean(String key, Boolean value) {
		SharedPreferences.Editor editor = App.getContext().getSharedPreferences(Const.CONFIG_NAME, Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static void putInt(String key, int value) {
		SharedPreferences.Editor editor = App.getContext().getSharedPreferences(Const.CONFIG_NAME, Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static void putString(String key, String value) {
		SharedPreferences.Editor editor = App.getContext().getSharedPreferences(Const.CONFIG_NAME, Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static void putLong(String key, long value) {
		SharedPreferences.Editor editor = App.getContext().getSharedPreferences(Const.CONFIG_NAME, Context.MODE_PRIVATE).edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public static void putFloat(String key, float value) {
		SharedPreferences.Editor editor = App.getContext().getSharedPreferences(Const.CONFIG_NAME, Context.MODE_PRIVATE).edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	
	public static void putMap(HashMap<String, Object> entrys) {
		SharedPreferences pref = App.getContext().getSharedPreferences(Const.CONFIG_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		
		Iterator<Entry<String, Object>> iter = entrys.entrySet().iterator();
		
		while (iter.hasNext()) {
			Entry<String, Object> set = iter.next();
			String key = set.getKey();
			Object val = set.getValue();
			
			if (val instanceof String) {
				editor.putString(key, (String) val);
			} else if (val instanceof Integer) {
				editor.putInt(key, (Integer) val);
			} else if (val instanceof Long) {
				editor.putLong(key, (Long) val);
			} else if (val instanceof Boolean) {
				editor.putBoolean(key, (Boolean) val);
			} else if (val instanceof Float) {
				editor.putFloat(key, (Float) val);
			} else if (pref.contains(key)) {
				editor.remove(key);
			}
		}
		
		editor.commit();
	}
}
