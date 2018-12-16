package com.android.base.view.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.base.R;

/**
 * 空试图
 * Created by YANGQIYUN on 2017/5/5.
 */

public class EmptyView extends LinearLayout {

    FrameLayout layoutProgress;
    LinearLayout layoutEmpty;
    LinearLayout layoutNetError;

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public EmptyView(Context context) {
        this(context, null);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

//        LayoutInflater.from(context).inflate(R.layout.layout_exceptional, this);
//        layoutProgress = (FrameLayout) findViewById(R.id.layout_progress);
//        layoutEmpty = (LinearLayout) findViewById(R.id.layout_empty);
//        layoutNetError = (LinearLayout) findViewById(R.id.layout_net_error);
    }
    /**
     *
     * 加载
     * */
    public void setInProgress(){
        layoutProgress.setVisibility(View.VISIBLE);
        layoutEmpty.setVisibility(View.GONE);
        layoutNetError.setVisibility(View.GONE);
    }

    /**
     *
     * 无数据
     * */
    public void setInEmptyView(){
        layoutProgress.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.VISIBLE);
        layoutNetError.setVisibility(View.GONE);
    }

    /**
     *
     * 网路错误
     * */
    public void setInNetError(){
        layoutProgress.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);
        layoutNetError.setVisibility(View.VISIBLE);
    }
}
