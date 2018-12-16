package com.android.base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;

import com.android.base.R;
import com.android.base.view.load.RotateLoading;

/**
 * 作者：yangqiyun
 * 时间：2017/9/30
 * 1120389276@qq.com
 * 描述：
 */

public class LoadingDialog extends Dialog {

    private Context mContext;
    private RotateLoading layout;

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.loading_dialog);
        layout = (RotateLoading) findViewById(R.id.rload_loading);// 加载布局

        setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(layout != null && layout.isStart()){
                    layout.stop();
                }
            }
        });
        setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if(layout != null && !layout.isStart()){
                    layout.start();
                }
            }
        });
    }

}
