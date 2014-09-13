package com.zane001.happyweather.ui.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zane001.happyweather.data.RequestManager;
import com.zane001.happyweather.util.DialogUtil;
import com.zane001.happyweather.util.ToastUtil;

public class BaseFragment extends Fragment {

    /**
     * 自定义加载对话框
     */
    protected Dialog mLoadingDialog;

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
        Intent intent = new Intent(getActivity(), clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 显示加载对话框
     */
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogUtil.Instance().createLoadingDialog(getActivity());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
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
