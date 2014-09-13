package com.zane001.happyweather.ui.fragment;

import android.widget.ImageView;
import android.widget.TextView;

import com.zane001.happyweather.App;
import com.zane001.happyweather.Const;
import com.zane001.happyweather.R;
import com.zane001.happyweather.model.WeatherModel;
import com.zane001.happyweather.ui.CityActivity_;
import com.zane001.happyweather.ui.MainActivity;
import com.zane001.happyweather.ui.MyCityActivity_;
import com.zane001.happyweather.ui.SettingActivity_;
import com.zane001.happyweather.util.ActivityUtil;
import com.zane001.happyweather.util.SharedPrefUtil;
import com.zane001.happyweather.util.WeatherUtil;
import com.zane001.happyweather.widget.TodayWeatherItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


@EFragment(R.layout.fragment_drawer)
public class DrawerFragment extends BaseFragment {

    ////////////////////////////////////////////
    //温馨提示
    @ViewById(R.id.drawer_weather_img)
    ImageView ivWeatherImg;

    @ViewById(R.id.drawer_weather)
    ImageView ivWeather;

    @ViewById(R.id.drawer_tv_weather)
    TextView tvWeather;

    @ViewById(R.id.drawer_temp)
    TextView tvTemp;

    @ViewById(R.id.drawer_tigan)
    TextView tvTigan;

    @ViewById(R.id.drawer_chuanyi)
    TextView tvChuanyi;

    @ViewById(R.id.drawer_ganmao)
    TextView tvGanmao;

    @ViewById(R.id.drawer_ziwaixian)
    TextView tvZhiwaixian;

    @ViewById(R.id.drawer_yundong)
    TextView tvYundong;

    @ViewById(R.id.drawer_xiche)
    TextView tvXiche;

    @AfterViews
    void initFragment() {
    }

    /**
     * 更新温馨提示
     */
    public void updateReminder(WeatherModel model) {
        if (model != null) {
            App.curWeatherModel = model;
            ivWeatherImg.setImageResource(WeatherUtil.getImg(model.weather1));
            ivWeather.setImageResource(WeatherUtil.getIcon(model.weather1));
            tvWeather.setText(model.weather1);
            tvTemp.setText(model.temp1);
            tvTigan.setText(model.index_co);
            tvChuanyi.setText(model.index_d);
            tvZhiwaixian.setText(model.index_uv);
            tvYundong.setText(model.index_cl);
            tvGanmao.setText(model.index_ag);
            tvXiche.setText(model.index_xc);
        }
    }

    @Click(R.id.drawer_add_city)
    void clickAddCity() {
        CityActivity_.intent(((MainActivity) getActivity()).homeFragment).startForResult(TodayWeatherItem.REQUEST_CODE);
        ((MainActivity) getActivity()).closeDrawer();
    }

    @Click(R.id.drawer_del_city)
    void clickDelCity() {
        MyCityActivity_.intent(((MainActivity) getActivity()).homeFragment).startForResult(TodayWeatherItem.REQUEST_CODE);
        ((MainActivity) getActivity()).closeDrawer();
    }

    @Click(R.id.drawwer_setting)
    void clickSetting() {
        openActivity(SettingActivity_.class);
        ((MainActivity) getActivity()).closeDrawer();
    }

    @Click(R.id.drawer_exit)
    void clickExit() {
        ((MainActivity) getActivity()).closeDrawer();

        if (SharedPrefUtil.getBoolean(Const.CONFIG_EXIT_KILL, false))
            ActivityUtil.finishKill();
        else
            ActivityUtil.finishAll();
    }
}