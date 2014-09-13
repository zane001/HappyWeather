package com.zane001.happyweather.ui;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zane001.happyweather.data.RequestManager;
import com.zane001.happyweather.util.ActivityUtil;
import com.zane001.happyweather.util.DialogUtil;
import com.zane001.happyweather.util.ToastUtil;

public class BaseActivity extends FragmentActivity {

    /**
     * 自定义加载对话框
     */
    protected Dialog mLoadingDialog;

    /**
     * ActionBar 动作菜单栏
     */
    protected ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.add(this);
        init();
    }

    private void init() {

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 显示加载对话框
     */
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogUtil.Instance().createLoadingDialog(this);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * 取消加载对话框
     */
    public void dismissLoading() {
        if (mLoadingDialog.isShowing() && mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 打开Activity，并跳转
     * 不带参数
     * @param clazz
     */
    protected void openActivity(Class clazz) {
        openActivity(clazz, null);
    }
    /**
     * 打开Activity，并跳转
     * 带参数
     * @param clazz
     * @param bundle
     */
    protected void openActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
        ActivityUtil.remove(this);
    }

    /**
     * 添加到网络请求队列
     * @param request
     */
    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    /**
     * 网络请求错误回调
     * @return
     */
    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.showShort(volleyError.getMessage());
            }
        };
    }
}
