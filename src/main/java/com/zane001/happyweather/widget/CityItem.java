package com.zane001.happyweather.widget;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zane001.happyweather.R;
import com.zane001.happyweather.model.city.BaseCityModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by zane001 on 2014/9/7.
 */

@EViewGroup(R.layout.layout_city_item)
public class CityItem extends LinearLayout {

    @ViewById(R.id.tv_city_name_layout)
    TextView tvCityName;

    CityItem(Context context) {
        super(context);
    }

    public void bind(BaseCityModel model) {
        tvCityName.setText(model.getCityName());
    }
}
