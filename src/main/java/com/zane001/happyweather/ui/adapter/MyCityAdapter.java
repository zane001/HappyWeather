package com.zane001.happyweather.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zane001.happyweather.model.city.AreaModel;
import com.zane001.happyweather.model.city.BaseCityModel;
import com.zane001.happyweather.widget.MyCityItem;
import com.zane001.happyweather.widget.MyCityItem_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class MyCityAdapter extends BaseAdapter {

    @RootContext
    Context mContext;

    List<AreaModel> mList = new ArrayList<AreaModel>();

    public void appendToList(List<AreaModel> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        try {
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseCityModel model = mList.get(position);
        MyCityItem item;

        if (convertView == null) {
            item = MyCityItem_.build(mContext);
        } else {
            item = (MyCityItem) convertView;
        }
        item.bind(model, position);
        return item;
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

}

