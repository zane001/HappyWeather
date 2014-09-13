package com.zane001.happyweather.ui;

import android.graphics.Point;
import android.view.Display;

import com.zane001.happyweather.App;
import com.zane001.happyweather.R;
import com.zane001.happyweather.ui.adapter.MyCityAdapter;
import com.zane001.happyweather.util.ToastUtil;
import com.zane001.happyweather.widget.swipeback.SwipeBackActivity;
import com.zane001.happyweather.widget.swipelistview.SwipeListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 我的已选择的城市列表页面
 */

@EActivity(R.layout.activiy_mycity)
public class MyCityActivity extends SwipeBackActivity {

    @ViewById(R.id.lv_my_city)
    SwipeListView mListView;

    @Bean
    MyCityAdapter mCityAdapter;

    @AfterViews
    void initActivity() {

        initView();
    }

    private void initView() {

        // 获取屏幕宽度
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;

        // 设置模式支持滑动
        mListView.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
        mListView.setSwipeActionLeft(0);
        mListView.setSwipeActionRight(0);
        mListView.setOffsetLeft(screenWidth / 4 * 3);
        mListView.setOffsetRight(0);
        mListView.setAnimationTime(0);
        // 支持长按自动呼出
        mListView.setSwipeOpenOnLongPress(true);

        mListView.setAdapter(mCityAdapter);
        mCityAdapter.appendToList(App.getMyArea());
    }

    public void delectCity(int position) {
        if (App.getMyArea().size() <= 1) {
            ToastUtil.showShort(R.string.no_delete);
        } else {
            setResult(1);
            App.removeMyArea(position);
            mCityAdapter.appendToList(App.getMyArea());
            mListView.closeOpenedItems();
        }
    }
}
