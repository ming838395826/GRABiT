package com.android.base.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.R;
import com.android.base.base.application.BaseApplication;
import com.android.base.view.load.RotateLoading;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yangqiyun
 * 时间：2017/6/11
 * 1120389276@qq.com
 * 描述：baseactivity,统计，异常捕获、规范、主要处理基类
 */
public class BaseActivity extends AppCompatActivity {

    protected Dialog defDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.mAppHandle.onAllActivityCreate(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        BaseApplication.mAppHandle.activeActivity = this;
        super.onResume();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        BaseApplication.mAppHandle.onAllActivityDestroy(this);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return super.onWindowStartingActionMode(callback);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }

    protected List<Object> getTestDate(){
        List<Object> list = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            list.add(i);
        }
        return list;
    }

    /**
     * 获取默认加载dialog
     * @return
     */
    public ProgressDialog getDefaultLoadDialog(){
        ProgressDialog mProgressDialog = new ProgressDialog(this);;
        return mProgressDialog;
    }

    /**
     * 显示加载
     */
    public void showLoadDialog(){
        if(defDialog == null){
            defDialog = createLoadingDialog(BaseActivity.this);
        }
        if(!defDialog.isShowing()){
            defDialog.show();
        }
    }

    /**
     * 隐藏加载dialog
     */
    public void dismissLoadDialog(){
        if(defDialog == null){
            return;
        }
        if(defDialog.isShowing()){
            defDialog.dismiss();
        }
    }

    /**
     * 得到自定义的progressDialog
     * @param context
     * @return
     */
    public static Dialog createLoadingDialog(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        final RotateLoading layout = (RotateLoading) v.findViewById(R.id.rload_loading);// 加载布局
        layout.start();

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(layout != null && layout.isStart()){
                    layout.stop();
                }
            }
        });
        loadingDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if(layout != null && !layout.isStart()){
                    layout.start();
                }
            }
        });

        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
        loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;

    }

}
